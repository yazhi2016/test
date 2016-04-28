package com.example.onebuy;

import android.content.Intent;
import android.graphics.BitmapFactory;
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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.homemodel.SignInSuccessByToken;
import com.example.model.Allshow;
import com.example.model.Datum2;
import com.example.model.SignOut;
import com.example.newonebuy.R;
import com.example.util.Content;
import com.example.util.GlobalVariable;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;
import com.xinbo.utils.GsonUtils;
import com.xinbo.utils.HTTPUtils;
import com.xinbo.utils.VolleyListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 首页晒单分享界面
 */
public class ShareActivity extends ActionBarActivity implements OnClickListener {

    private ListView lv;
    private LvAdapter lvAdapter;
    private TextView text1;
    private TextView text2;
    private TextView text3;
    private PullToRefreshListView mPullRefreshListView;
    int mPage = 1;
    String mode = "new";
    ArrayList<Datum2> list = new ArrayList<Datum2>();
    boolean isLogIn;
    String isSignIn;
    String token;
    String uid;
    String editSid = "";

    // 分享
    final SHARE_MEDIA[] displaylist = new SHARE_MEDIA[]{SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,
            SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE};

    private ShareBoardlistener shareBoardlistener = new ShareBoardlistener() {

        @Override
        public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
            if (share_media == null) {
                if (snsPlatform.mKeyword.equals("11")) {
                    Toast.makeText(ShareActivity.this, "add button success", Toast.LENGTH_LONG).show();
                }

            } else {
                new ShareAction(ShareActivity.this).setPlatform(share_media).setCallback(umShareListener)
                        .withText("赶快下载吧！").withTitle("一元妙购").withTargetUrl("http://pc88.gotoip4.com/index.php/mobile/shaidan/detail/" + editSid)
                        .withMedia(new UMImage(ShareActivity.this,
                                BitmapFactory.decodeResource(getResources(), R.drawable.logo)))
                        .share();
            }
        }
    };

    UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(ShareActivity.this, "分享成功啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(ShareActivity.this, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(ShareActivity.this, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };
    private TextView mTextNothing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        initUI();
    }

    private void initUI() {
        findViewById(R.id.back).setOnClickListener(this);
        mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.ptrlv_share);

        lv = mPullRefreshListView.getRefreshableView();

        // 分别监听上拉下拉事件
        mPullRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

            // 下拉刷新
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                new GetDataTask(false).execute();
            }

            // 上拉刷新
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                new GetDataTask(true).execute();
            }
        });
        View header = getLayoutInflater().inflate(R.layout.header_shareact, null);
        header.findViewById(R.id.radio0).setOnClickListener(this);
        header.findViewById(R.id.radio1).setOnClickListener(this);
        header.findViewById(R.id.radio2).setOnClickListener(this);
        text1 = (TextView) header.findViewById(R.id.text1);
        text2 = (TextView) header.findViewById(R.id.text2);
        text3 = (TextView) header.findViewById(R.id.text3);

        lv.addHeaderView(header);
        lvAdapter = new LvAdapter();
        lv.setAdapter(lvAdapter);

        mTextNothing = (TextView) findViewById(R.id.text_nothing);
    }

    class LvAdapter extends BaseAdapter {

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
            View view;
            final TextView text_likenum;
            final ImageView ima_like;
            ImageView ima_product;
            TextView text_name_productshareact;
            TextView text_time_productshareact;
            TextView text_title_productshare;
            TextView text_content_productshare;
            TextView text_commentnum;
            TextView text_sharenum;
            CircleImageView ima_productshare;
            if (convertView == null) {
                view = getLayoutInflater().inflate(R.layout.item_lv_allshowact, null);
                ima_productshare = (CircleImageView) view.findViewById(R.id.ima_people);
                text_likenum = (TextView) view.findViewById(R.id.text_likenum);
                ima_like = (ImageView) view.findViewById(R.id.ima_like_allshowact);
                ima_product = (ImageView) view.findViewById(R.id.ima_detail1);
                text_name_productshareact = (TextView) view.findViewById(R.id.text_name2);
                text_time_productshareact = (TextView) view.findViewById(R.id.text_time);
                text_title_productshare = (TextView) view.findViewById(R.id.text_title);
                text_content_productshare = (TextView) view.findViewById(R.id.text_content);
                text_commentnum = (TextView) view.findViewById(R.id.text_commentnum);
                text_sharenum = (TextView) view.findViewById(R.id.text_sharenum);
                view.setTag(new MyTag(text_likenum, ima_like, ima_product, text_name_productshareact,
                        text_time_productshareact, text_title_productshare, text_content_productshare, text_commentnum,
                        text_sharenum, ima_productshare));
            } else {
                view = convertView;
                MyTag tag2 = (MyTag) view.getTag();
                ima_productshare = tag2.ima_productshare; // 作者头像
                text_likenum = tag2.text_likenum; // 点赞人数
                ima_like = tag2.ima_like;
                ima_product = tag2.ima_product; // 晒单图片
                text_name_productshareact = tag2.text_name_productshareact; // 作者
                text_time_productshareact = tag2.text_time_productshareact; // 时间
                text_title_productshare = tag2.text_title_productshare; // 标题
                text_content_productshare = tag2.text_content_productshare; // 评论内容
                text_commentnum = tag2.text_commentnum; // 评论人数
                text_sharenum = tag2.text_sharenum;

            }
            final Datum2 shaidan = list.get(position);
            text_name_productshareact.setText(shaidan.getUser());
            text_time_productshareact.setText(shaidan.getSdTime());
            text_title_productshare.setText(shaidan.getSdTitle());
            text_content_productshare.setText(shaidan.getSdContent());
            text_likenum.setText(shaidan.getSdZhan());
            text_commentnum.setText(shaidan.getSdPing());
            // text_sharenum.setText(shaidan.getSdPing());

            final Integer isZan = shaidan.getIsZan();

            Log.e("==", isLogIn + "LvAdapter");
            if (isLogIn) {
                if (isZan == 1) { // 赞过
                    Log.e("==", position + "赞过");
                    ima_like.setImageDrawable(getResources().getDrawable(R.drawable.like2));
                } else {
                    Log.e("==", position + "未赞");
                    ima_like.setImageDrawable(getResources().getDrawable(R.drawable.like1));
                }
            }

            // 点赞
            view.findViewById(R.id.rela_like_allshowfra).setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    signInByToken(); // 判断登陆状态防止token失效
                    if (isLogIn) { // 登陆状态
                        if (isZan == 1) { // 赞过
                            Toast.makeText(ShareActivity.this, "已点过赞", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        ima_like.setImageDrawable(getResources().getDrawable(R.drawable.like2));
                        like(shaidan.getSdId(), text_likenum, shaidan.getSdZhan());
                    } else {
                        Toast.makeText(ShareActivity.this, "请登陆", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ShareActivity.this, LogInActivity.class);
                        // intent.putExtra("state", "notsafe");
                        startActivity(intent);
                    }
                }
            });
            final String sdId = shaidan.getSdId();
            editSid = sdId;
            // 点击评论
            view.findViewById(R.id.rela_comment_item_allshowfra).setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    signInByToken(); // 判断登陆状态防止token失效
                    if (isLogIn) { // 登陆状态
                        Intent intent = new Intent(ShareActivity.this, ShowDetailActivity.class);
                        intent.putExtra("sid", sdId);
                        intent.putExtra("comment", 1);
                        startActivity(intent);
                    } else {
                        Toast.makeText(ShareActivity.this, "请登陆", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ShareActivity.this, LogInActivity.class);
                        // intent.putExtra("state", "notsafe");
                        startActivity(intent);
                    }

                }
            });
            // 点击转发
            view.findViewById(R.id.rela_share_allshowfra).setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    new ShareAction(ShareActivity.this).setDisplayList(displaylist).withText("呵呵").withTitle("title")
                            .withTargetUrl("http://pc88.gotoip4.com/index.php/mobile/shaidan/detail/" + editSid)
                            .withMedia(new UMImage(ShareActivity.this,
                                    BitmapFactory.decodeResource(getResources(), R.drawable.like1)))
                            .setListenerList(umShareListener).setShareboardclickCallback(shareBoardlistener).open();

                }
            });

            view.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ShareActivity.this, ShowDetailActivity.class);
                    intent.putExtra("sid", list.get(position).getSdId());
                    startActivity(intent);
                }
            });

            // 头像和晒单图片
            GlobalVariable.displayImageNoAnim(Content.URL.IMA_HOST + shaidan.getSdThumbs(), ima_product);
            GlobalVariable.displayImageNoAnim(Content.URL.IMA_HOST + shaidan.getPic(), ima_productshare);
            return view;
        }

    }

    class MyTag {
        TextView text_likenum;
        ImageView ima_like;
        ImageView ima_product;
        TextView text_name_productshareact;
        TextView text_time_productshareact;
        TextView text_title_productshare;
        TextView text_content_productshare;
        TextView text_commentnum;
        TextView text_sharenum;
        CircleImageView ima_productshare;

        public MyTag(TextView text_likenum, ImageView ima_like, ImageView ima_product,
                     TextView text_name_productshareact, TextView text_time_productshareact,
                     TextView text_title_productshare, TextView text_content_productshare, TextView text_commentnum,
                     TextView text_sharenum, CircleImageView ima_productshare) {
            super();
            this.text_likenum = text_likenum;
            this.ima_like = ima_like;
            this.ima_product = ima_product;
            this.text_name_productshareact = text_name_productshareact;
            this.text_time_productshareact = text_time_productshareact;
            this.text_title_productshare = text_title_productshare;
            this.text_content_productshare = text_content_productshare;
            this.text_commentnum = text_commentnum;
            this.text_sharenum = text_sharenum;
            this.ima_productshare = ima_productshare;
        }

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
                refreshData(isPullUp);
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

    public void refreshData(final boolean isPullUp) {
        String url;
        GlobalVariable instance = GlobalVariable.getInstance();
        if (isPullUp) { // 上拉刷新
            url = Content.URL.SHARE + mode + "&page=" + mPage + "&uid=" + instance.getSpUid() + "&token="
                    + instance.getSpToken();
        } else { // 下拉刷新，加载第0页
            url = Content.URL.SHARE + mode + "&page=" + 0 + "&uid=" + instance.getSpUid() + "&token="
                    + instance.getSpToken();
            mPage = 1;
        }

        HTTPUtils.get(ShareActivity.this, url, new VolleyListener() {

            @Override
            public void onResponse(String arg0) {
                Spanned fromHtml = Html.fromHtml(arg0);
                Allshow parseJSON = GsonUtils.parseJSON(fromHtml + "", Allshow.class);
                if (parseJSON.getTag() == 1) {
                    List<Datum2> data = parseJSON.getData();
                    if (data.size() == 0) {
                        Toast.makeText(ShareActivity.this, "没有内容了", Toast.LENGTH_SHORT).show();
                        mPullRefreshListView.onRefreshComplete();
                    } else {
                        if (!isPullUp) {
                            list.clear();
                        }
                        list.addAll(data);
                        lvAdapter.notifyDataSetChanged();
                        if (isPullUp) { // 上拉刷新
                            mPage++;
                        }
                        mPullRefreshListView.onRefreshComplete();

                    }
                } else {
                    Toast.makeText(getApplicationContext(), parseJSON.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onErrorResponse(VolleyError arg0) {

            }
        });

    }

    private void initData() {
        mTextNothing.setVisibility(View.GONE);
        String url;
        GlobalVariable instance = GlobalVariable.getInstance();
        url = Content.URL.SHARE + mode + "&page=" + 0 + "&uid=" + instance.getSpUid() + "&token="
                + instance.getSpToken();

        HTTPUtils.get(ShareActivity.this, url, new VolleyListener() {

            @Override
            public void onResponse(String arg0) {
                Spanned fromHtml = Html.fromHtml(arg0);
                Allshow parseJSON = GsonUtils.parseJSON(fromHtml + "", Allshow.class);
                if (parseJSON.getTag() == 1) {
                    List<Datum2> data = parseJSON.getData();
                    list.clear();
                    list.addAll(data);
                    if (data.size() == 0) {
                        mTextNothing.setVisibility(View.VISIBLE);
                    }
                    lvAdapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(ShareActivity.this, parseJSON.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onErrorResponse(VolleyError arg0) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.radio0: // 最新晒单
                text1.setVisibility(View.VISIBLE);
                text2.setVisibility(View.INVISIBLE);
                text3.setVisibility(View.INVISIBLE);
                if (!mode.equals("new")) {
                    mode = "new";
                }
                refreshData(false);
                break;
            case R.id.radio1: // 人气晒单
                text1.setVisibility(View.INVISIBLE);
                text2.setVisibility(View.VISIBLE);
                text3.setVisibility(View.INVISIBLE);
                if (!mode.equals("renqi")) {
                    mode = "renqi";
                }
                refreshData(false);

                break;
            case R.id.radio2: // 评论最多
                text1.setVisibility(View.INVISIBLE);
                text2.setVisibility(View.INVISIBLE);
                text3.setVisibility(View.VISIBLE);
                if (!mode.equals("pinglun")) {
                    mode = "pinglun";
                }
                refreshData(false);
                break;

            default:
                break;
        }
    }

    public void signInByToken() {
        GlobalVariable instance = GlobalVariable.getInstance();
        String url = Content.URL.SIGNINBYTOKEN + instance.getSpToken() + "&uid=" + instance.getSpUid();
        HTTPUtils.get(this, url, new VolleyListener() {

            @Override
            public void onResponse(String arg0) {
                Spanned fromHtml = Html.fromHtml(arg0);
                SignInSuccessByToken parseJSON = GsonUtils.parseJSON(fromHtml + "", SignInSuccessByToken.class);
                if (parseJSON.getTag() == 1) { // 已登录
                    isLogIn = true;
                    initData();
                } else {
                    isLogIn = false;
                    Intent intent = new Intent(ShareActivity.this, LogInActivity.class);
                    intent.putExtra("state", "safe");
                    startActivity(intent);
                }
                return;
            }

            @Override
            public void onErrorResponse(VolleyError arg0) {

            }
        });
    }

    public void like(String goodid, final TextView t, final String num) {
        GlobalVariable instance = GlobalVariable.getInstance();
        String url = Content.URL.SHOWLIKE + goodid + "&uid=" + instance.getSpUid() + "&token=" + instance.getSpToken();
        HTTPUtils.get(ShareActivity.this, url, new VolleyListener() {

            @Override
            public void onResponse(String arg0) {
                Spanned fromHtml = Html.fromHtml(arg0);
                SignOut parseJSON = GsonUtils.parseJSON(fromHtml + "", SignOut.class);
                if (parseJSON.getTag() == 1) { // 点赞成功
                    Toast.makeText(ShareActivity.this, "点赞成功", Toast.LENGTH_SHORT).show();
                    int parseInt = Integer.parseInt(num);
                    parseInt++;
                    t.setText(parseInt + "");
                } else { // 已点赞过
                    Toast.makeText(ShareActivity.this, parseJSON.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onErrorResponse(VolleyError arg0) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        GlobalVariable instance = GlobalVariable.getInstance();
        token = instance.getSpToken();
        uid = instance.getSpUid();
        if (token.equals("")) {
            isSignIn = "no";
        } else {
            isSignIn = "yes";
        }
        if (isSignIn.equals("yes")) { // 有token,判断是否失效
            signInByToken();
        } else if (isSignIn.equals("no")) { // 退出状态
            Intent intent = new Intent(ShareActivity.this, LogInActivity.class);
            intent.putExtra("state", "safe");
            startActivity(intent);
        }
    }

}
