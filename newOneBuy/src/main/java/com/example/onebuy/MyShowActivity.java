package com.example.onebuy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.model.Allmyshow;
import com.example.model.Data5;
import com.example.model.ListItem2;
import com.example.newonebuy.R;
import com.example.util.Content;
import com.example.util.GlobalVariable;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.xinbo.utils.GsonUtils;
import com.xinbo.utils.HTTPUtils;
import com.xinbo.utils.VolleyListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的晒单
 */
public class MyShowActivity extends ActionBarActivity implements OnClickListener {

    private ListView lv; // 未晒单
    private ListView lvShow;
    String token;
    String uid;
    SharedPreferences sp;
    private PullToRefreshListView mPullRefreshListView;
    private PullToRefreshListView mPullRefreshListViewShow;
    private int mPage = 1;
    ArrayList<ListItem2> list = new ArrayList<ListItem2>(); //未晒
    ArrayList<ListItem2> listShow = new ArrayList<ListItem2>(); //已晒
    private MyAdapter myAdapter;
    private RelativeLayout rela_noinfo_myshowact;
    private RelativeLayout rela_noinfo_myshowact2;
    private TextView choose11;
    private TextView choose12;
    private TextView choose22;
    private TextView choose21;
    int mode = 0; // 0已晒单，1未晒单
    private HasShowAdapter hasShowAdapter;
    private RelativeLayout rela_hasshow;
    private RelativeLayout rela_noshow;
    boolean showHaveData = false; //已晒单是否有数据
    boolean noShowHaveData = false; //未晒单是否有数据

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_show);

        sp = getSharedPreferences("token", 0);
        token = sp.getString("token", "");
        uid = sp.getString("uid", "");

        findViewById(R.id.back).setOnClickListener(this);

        rela_noinfo_myshowact = (RelativeLayout) findViewById(R.id.rela_noinfo_myshowact);
        rela_noinfo_myshowact2 = (RelativeLayout) findViewById(R.id.rela_noinfo_myshowact2);

        rela_hasshow = (RelativeLayout) findViewById(R.id.rela_hasshow);
        rela_noshow = (RelativeLayout) findViewById(R.id.rela_noshow);
        choose11 = (TextView) findViewById(R.id.choose11);
        choose12 = (TextView) findViewById(R.id.choose12);
        choose21 = (TextView) findViewById(R.id.choosed21);
        choose22 = (TextView) findViewById(R.id.choosed22);
        findViewById(R.id.radio0).setOnClickListener(this);
        findViewById(R.id.radio1).setOnClickListener(this);

        mPullRefreshListViewShow = (PullToRefreshListView) findViewById(R.id.ptrlv_myshow);
        mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.ptrlv_noshow);
        // 分别监听上拉下拉事件
        mPullRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

            // 下拉刷新
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                rela_noinfo_myshowact.setVisibility(View.GONE);
                rela_noinfo_myshowact2.setVisibility(View.GONE);
                new GetDataTask(false).execute();
            }

            // 上拉刷新
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                new GetDataTask(true).execute();
            }
        });
        mPullRefreshListViewShow.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

            // 下拉刷新
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                rela_noinfo_myshowact.setVisibility(View.GONE);
                rela_noinfo_myshowact2.setVisibility(View.GONE);
                new GetDataTaskShow(false).execute();
            }

            // 上拉刷新
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                new GetDataTaskShow(true).execute();
            }
        });

        lv = mPullRefreshListView.getRefreshableView();
        lvShow = mPullRefreshListViewShow.getRefreshableView();
        myAdapter = new MyAdapter();
        hasShowAdapter = new HasShowAdapter();
        lvShow.setAdapter(hasShowAdapter);
        lv.setAdapter(myAdapter);

        // initData();
        rela_noinfo_myshowact.setVisibility(View.GONE);
        rela_noinfo_myshowact2.setVisibility(View.GONE);
        refreshDataShow(false);
        refreshDataNoShow(false);
    }

    private class GetDataTask extends AsyncTask<Void, Void, String[]> {
        boolean isPullUp;

        public GetDataTask(boolean isPullUp) {
            super();
            this.isPullUp = isPullUp;
        }

        @Override
        protected String[] doInBackground(Void... params) {
            // Simulates a background job.
            try {
                Thread.sleep(500);
                refreshDataNoShow(isPullUp);
            } catch (InterruptedException e) {
            }
            return new String[]{"", ""};
        }

        @Override
        protected void onPostExecute(String[] result) {
            // mPullRefreshListView.onRefreshComplete();
            super.onPostExecute(result);
        }
    }

    private class GetDataTaskShow extends AsyncTask<Void, Void, String[]> {
        boolean isPullUp;

        public GetDataTaskShow(boolean isPullUp) {
            super();
            this.isPullUp = isPullUp;
        }

        @Override
        protected String[] doInBackground(Void... params) {
            // Simulates a background job.
            try {
                Thread.sleep(500);
                refreshDataShow(isPullUp);
            } catch (InterruptedException e) {
            }
            return new String[]{"", ""};
        }

        @Override
        protected void onPostExecute(String[] result) {
            // mPullRefreshListView.onRefreshComplete();
            super.onPostExecute(result);
        }
    }

    private void refreshDataNoShow(final boolean isPullUp) {
        noShowHaveData = true;
        String url;
        if (isPullUp) { // 上拉刷新
            url = Content.URL.MYNOSHOW + token + "&uid=" + uid + "&page=" + mPage;
        } else { // 下拉刷新，加载第0页
            url = Content.URL.MYNOSHOW + token + "&uid=" + uid + "&page=" + 0;
            mPage = 1;
        }

        HTTPUtils.get(MyShowActivity.this, url, new VolleyListener() {

            @Override
            public void onResponse(String arg0) {

                if (!isPullUp) { // 下拉刷新
                    list.clear();
                }

                Spanned fromHtml = Html.fromHtml(arg0);
                Allmyshow parseJSON = GsonUtils.parseJSON(fromHtml + "", Allmyshow.class);
                if (parseJSON.getTag() == 1) {
                    Data5 data = parseJSON.getData();
                    Integer code = data.getCode();
                    if (code == 1 && isPullUp) { // 没有数据
                        mPullRefreshListViewShow.onRefreshComplete();
                        Toast.makeText(MyShowActivity.this, "没有数据了", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (code == 1 && !isPullUp) {

                        mPullRefreshListViewShow.onRefreshComplete();
                        return;
                    }
                    List<ListItem2> listItems = data.getListItems();
                    if (listItems.size() == 0) {
                        rela_noinfo_myshowact2.setVisibility(View.VISIBLE);
                        noShowHaveData = false;
                    } else {
                        rela_noinfo_myshowact2.setVisibility(View.GONE);
                        noShowHaveData = true;
                    }
                    list.addAll(listItems);
                    myAdapter.notifyDataSetChanged();

                    if (isPullUp) { // 上拉刷新
                        mPage++;
                    }
                }

                mPullRefreshListView.onRefreshComplete();

            }

            @Override
            public void onErrorResponse(VolleyError arg0) {

            }
        });

    }

    private void refreshDataShow(final boolean isPullUp) {
        String url;
        if (isPullUp) { // 上拉刷新
            url = Content.URL.MYHASSHOW + token + "&uid=" + uid + "&page=" + mPage;
        } else { // 下拉刷新，加载第0页
            url = Content.URL.MYHASSHOW + token + "&uid=" + uid + "&page=" + 0;
            mPage = 1;
        }

        HTTPUtils.get(MyShowActivity.this, url, new VolleyListener() {

            @Override
            public void onResponse(String arg0) {

                if (!isPullUp) { // 下拉刷新
                    listShow.clear();
                }

                Spanned fromHtml = Html.fromHtml(arg0);
                Allmyshow parseJSON = GsonUtils.parseJSON(fromHtml + "", Allmyshow.class);
                if (parseJSON.getTag() == 1) {
                    Data5 data = parseJSON.getData();
                    Integer code = data.getCode();
                    if (code == 1 && isPullUp) { // 没有数据
                        mPullRefreshListViewShow.onRefreshComplete();
                        Toast.makeText(MyShowActivity.this, "没有数据了", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (code == 1 && !isPullUp) {
                        mPullRefreshListViewShow.onRefreshComplete();
                        showHaveData = false;
                        rela_noinfo_myshowact.setVisibility(View.VISIBLE);
                        return;
                    }
                    List<ListItem2> listItems = data.getListItems();
                    if (listItems.size() != 0) {
                        showHaveData = true;
                        rela_noinfo_myshowact.setVisibility(View.GONE);
                    }
                    rela_noinfo_myshowact2.setVisibility(View.GONE);
                    listShow.addAll(listItems);
                    hasShowAdapter.notifyDataSetChanged();

                    if (isPullUp) { // 上拉刷新
                        mPage++;
                    }
                }

                mPullRefreshListViewShow.onRefreshComplete();
            }

            @Override
            public void onErrorResponse(VolleyError arg0) {

            }
        });

    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.item_lv_mywin, null);
            TextView text_name_mywinact = (TextView) view.findViewById(R.id.text_name_mywinact);
            TextView text_luckynum_mywinact = (TextView) view.findViewById(R.id.text_luckynum_mywinact);
            TextView text_time_mywinact = (TextView) view.findViewById(R.id.text_time_mywinact);
            ImageView ima_winner_mywinact = (ImageView) view.findViewById(R.id.ima_winner_mywinact);

            ListItem2 listItem = list.get(position);
            text_name_mywinact.setText("（第" + listItem.getQishu() + "期）" + listItem.getTitle2());
            text_luckynum_mywinact.setText("" + listItem.getQUserCode());
            text_time_mywinact.setText(listItem.getQEndTime());

            GlobalVariable.displayImageNoAnim(Content.URL.IMA_HOST + listItem.getThumb(), ima_winner_mywinact);

            view.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MyShowActivity.this, MyWinDetailActivity.class);
                    intent.putExtra("goodid", list.get(position).getId());
                    startActivity(intent);
//					Intent intent = new Intent(MyShowActivity.this, ResultActivity.class);
//					intent.putExtra("goodid", list.get(position).getId());
//					startActivity(intent);
                }
            });

            return view;
        }

    }

    class HasShowAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return listShow.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.item_lv_myshow, null);
            TextView text_name_mywinact = (TextView) view.findViewById(R.id.text_name_myshowact);
            TextView text_text_myshowact = (TextView) view.findViewById(R.id.text_text_myshowact);
            TextView text_time_mywinact = (TextView) view.findViewById(R.id.text_time_myshowact);
            ImageView ima_winner_mywinact = (ImageView) view.findViewById(R.id.ima_winner_myshowact);

            ListItem2 listItem = listShow.get(position);
            text_name_mywinact.setText(listItem.getSdTitle());
            text_text_myshowact.setText(listItem.getSdContent());
            text_time_mywinact.setText(listItem.getSdTime());

            GlobalVariable.displayImageNoAnim(Content.URL.IMA_HOST + listItem.getThumb(), ima_winner_mywinact);

            view.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    Log.e("==", listShow.get(position).getSdId() + "--" + position);
                    // 第1行，position为1
                    Intent intent = new Intent(MyShowActivity.this, ShowDetailActivity.class);
                    intent.putExtra("sid", listShow.get(position).getSdId());
                    startActivity(intent);
                }
            });
            return view;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.radio0: // 已晒单
                choose11.setVisibility(View.INVISIBLE);
                choose12.setVisibility(View.VISIBLE);
                choose21.setVisibility(View.VISIBLE);
                choose22.setVisibility(View.INVISIBLE);
                if (mode != 0) {
                    mode = 0;
                    mPage = 1;
                    rela_hasshow.setVisibility(View.VISIBLE);
                    rela_noshow.setVisibility(View.INVISIBLE);
                }
                if (showHaveData) { //有数据
                    rela_noinfo_myshowact.setVisibility(View.GONE);
                } else {
                    rela_noinfo_myshowact.setVisibility(View.VISIBLE);
                }
                rela_noinfo_myshowact2.setVisibility(View.GONE);
                break;
            case R.id.radio1:
                choose11.setVisibility(View.VISIBLE);
                choose12.setVisibility(View.INVISIBLE);
                choose21.setVisibility(View.INVISIBLE);
                choose22.setVisibility(View.VISIBLE);
                if (mode != 1) {
                    mode = 1;
                    mPage = 1;
                    rela_hasshow.setVisibility(View.INVISIBLE);
                    rela_noshow.setVisibility(View.VISIBLE);
                }
                if (noShowHaveData) { //有数据
                    rela_noinfo_myshowact2.setVisibility(View.GONE);
                } else {
                    rela_noinfo_myshowact2.setVisibility(View.VISIBLE);
                }
                rela_noinfo_myshowact.setVisibility(View.GONE);
                break;

            default:
                break;
        }
    }
}
