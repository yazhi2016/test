package com.example.onebuy;

import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.VolleyError;
import com.example.newonebuy.R;
import com.example.homemodel.SignInSuccessByToken;
import com.example.onebuy.AllOrderActivity.showCodeBroad;
import com.example.showmodel.BuyList;
import com.example.showmodel.Data;
import com.example.showmodel.GoodsInfo;
import com.example.showmodel.Mywindetail;
import com.example.util.Content;
import com.example.util.GlobalVariable;
import com.xinbo.utils.GsonUtils;
import com.xinbo.utils.HTTPUtils;
import com.xinbo.utils.UILUtils;
import com.xinbo.utils.VolleyListener;
import com.xinbo.widget.ListView4ScrollView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MyWinDetailActivity extends ActionBarActivity implements OnClickListener {

	private ImageView ima_winner_mywindetailact;
	private TextView text_name_mywindetailact;
	private TextView text_winner;
	private TextView text_time_mywindetailact;
	private TextView text_state;
	String goodid;
	String token;
	String uid;
	private ListView4ScrollView lv_mywindetail;
	ArrayList<BuyList> list = new ArrayList<BuyList>();
	private LvAdapter lvAdapter;
	private ScrollView scrollView1;
	private TextView text_myCodeNum2;
	private Button btn_mywindetailact;
	boolean isLogIn = false;
	int state = 0; // 按钮状态：0晒单，1收货，2消费
	String orderid;
	String formatStr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_win_detail);

		Intent intent = getIntent();
		goodid = intent.getStringExtra("goodid");
		initUI();
	}

	@Override
	protected void onResume() {
		super.onResume();
		GlobalVariable instance = GlobalVariable.getInstance();
		token = instance.getSpToken();
		uid = instance.getSpUid();
		if (token.equals("")) { // 未登录，弹登陆窗口
			isLogIn = false;
			startLogInActivity();
			return;
		} else { // 有token，判断token是否有效
			signInByToken();
		}
	}

	private void startLogInActivity() {
		Toast.makeText(MyWinDetailActivity.this, "登陆失效，请重新登陆", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(MyWinDetailActivity.this, LogInActivity.class);
		intent.putExtra("state", "safe");
		startActivity(intent);
	}

	public void signInByToken() {
		String url = Content.URL.SIGNINBYTOKEN + token + "&uid=" + uid;
		HTTPUtils.get(this, url, new VolleyListener() {

			@Override
			public void onResponse(String arg0) {
				Spanned fromHtml = Html.fromHtml(arg0);
				SignInSuccessByToken parseJSON = GsonUtils.parseJSON(fromHtml + "", SignInSuccessByToken.class);
				if (parseJSON.getTag() == 2) { // 未登录
					isLogIn = false;
					startLogInActivity();
					return;
				} else {
					isLogIn = true;
					initData();
				}
			}

			@Override
			public void onErrorResponse(VolleyError arg0) {

			}
		});
	}

	private void initData() {
		String url = Content.URL.MYWINDETAIL + token + "&uid=" + uid + "&goods_id=" + goodid;
		HTTPUtils.get(MyWinDetailActivity.this, url, new VolleyListener() {

			@Override
			public void onResponse(String arg0) {
				Mywindetail parseJSON = GsonUtils.parseJSON(arg0, Mywindetail.class);
				Data data = parseJSON.getData();
				Integer have_shaidan = data.getHave_shaidan();
				GoodsInfo goodsInfo = data.getGoodsInfo();
				GlobalVariable.displayImageNoAnim(Content.URL.IMA_HOST + goodsInfo.getThumb(), ima_winner_mywindetailact);
				text_name_mywindetailact.setText("（第" + goodsInfo.getQiShu() + "期）" + goodsInfo.getTitle());
				text_winner.setText(goodsInfo.getName());
				text_time_mywindetailact.setText(goodsInfo.getQEndTime());
				text_state.setText(data.getOrderStatus().getStatus());
				text_myCodeNum2.setText(goodsInfo.getBuy_num() + "");
				if (goodsInfo.getNeedHexiao().equals("1")) {
					btn_mywindetailact.setText("我要消费");
					state = 2;
				}
				if (data.getOrderStatus().getStatus().contains("已发货")) {
					btn_mywindetailact.setText("我要收货");
					state = 1;
				}
				if (data.getOrderStatus().getStatus().contains("已完成")) {
					if(have_shaidan == 1) { //已晒单
						btn_mywindetailact.setEnabled(false);
						btn_mywindetailact.setText("已晒过单");
					} else {
						btn_mywindetailact.setEnabled(true);
						btn_mywindetailact.setText("我要晒单");
					}
					state = 0;
				}
				List<BuyList> buyList = data.getBuyList();
				list.clear();
				list.addAll(buyList);
				lvAdapter.notifyDataSetChanged();
				orderid = data.getOrderStatus().getId();

				scrollView1.smoothScrollTo(0, 0);
			}

			@Override
			public void onErrorResponse(VolleyError arg0) {

			}
		});
	}

	private void initUI() {
		text_myCodeNum2 = (TextView) findViewById(R.id.text_myCodeNum2);
		ima_winner_mywindetailact = (ImageView) findViewById(R.id.ima_winner_mywindetailact);
		text_name_mywindetailact = (TextView) findViewById(R.id.text_name_mywindetailact);
		text_winner = (TextView) findViewById(R.id.text_winner);
		text_time_mywindetailact = (TextView) findViewById(R.id.text_time_mywindetailact);
		text_state = (TextView) findViewById(R.id.text_state);

		lv_mywindetail = (ListView4ScrollView) findViewById(R.id.lv_mywindetail);
		lvAdapter = new LvAdapter();
		lv_mywindetail.setAdapter(lvAdapter);

		btn_mywindetailact = (Button) findViewById(R.id.btn_mywindetailact);
		btn_mywindetailact.setOnClickListener(this);
		findViewById(R.id.back).setOnClickListener(this);

		scrollView1 = (ScrollView) findViewById(R.id.scrollView1);
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
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = getLayoutInflater().inflate(R.layout.item_mywindetail, null);
			TextView text_time = (TextView) view.findViewById(R.id.text_time);
			TextView text_num = (TextView) view.findViewById(R.id.text_num);
			TextView text_mycode = (TextView) view.findViewById(R.id.text_mycode);

			BuyList buyList = list.get(position);
			text_time.setText(buyList.getTime());
			text_num.setText(buyList.getGonumber() + "");
			String goucode = buyList.getGoucode();
			StringBuffer buffer = new StringBuffer(goucode);
			int num = 0;
			int index = buffer.indexOf(",");
			if (index != -1) {
				num = 1;
			}
			while (true) {
				if (num % 3 == 0 && num != 0) {
					// buffer.insert(index + 1, "\n");
					buffer.replace(index, index + 1, "\n");
				} else if (num % 3 != 0 && num != 0) {
					buffer.insert(index + 1, "   ");
				}
				index = buffer.indexOf(",", index + 1);
				if (index != -1) {
					num++;
				} else {
					break;
				}
			}
			text_mycode.setText(buffer.toString());
			return view;
		}
	}

	public void editString(final String str, final int position) { // 每4个逗号，逗号替换为换行符
		new Thread(new Runnable() {
			public void run() {
				StringBuffer buffer = new StringBuffer(str);
				int num = 0;
				int index = buffer.indexOf(",");
				if (index != -1) {
					num = 1;
				}
				while (true) {
					if (num % 3 == 0 && num != 0) {
						// buffer.insert(index + 1, "\n");
						buffer.replace(index, index + 1, "\n");
					} else if (num % 3 != 0 && num != 0) {
						buffer.insert(index + 1, "   ");
					}
					index = buffer.indexOf(",", index + 1);
					if (index != -1) {
						num++;
					} else {
						break;
					}
				}
				list.get(position).setGoucode(buffer.toString());

				Intent intent = new Intent();
				intent.setAction("showcode");
				sendBroadcast(intent);
			}
		}).start();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.btn_mywindetailact:
			if (state == 0) { // 晒单
				Intent intent = new Intent(MyWinDetailActivity.this, ShowActivity.class);
				intent.putExtra("goodid", goodid);
				startActivity(intent);
			} else if(state == 2) { //消费
				Intent intent = new Intent(MyWinDetailActivity.this, TwoCodeActivity.class);
				intent.putExtra("url", Content.URL.TWOCODE + goodid);
				startActivity(intent);
			} else if(state == 1) { //收货
				String url = Content.URL.GETGOODS + token + "&uid=" + uid + "&order_id=" + orderid;
				HTTPUtils.get(MyWinDetailActivity.this, url, new VolleyListener() {
					
					@Override
					public void onResponse(String arg0) {
						//收货成功刷新界面
						initData();
					}
					
					@Override
					public void onErrorResponse(VolleyError arg0) {
						
					}
				});
				
			}
			break;

		default:
			break;
		}
	}

}
