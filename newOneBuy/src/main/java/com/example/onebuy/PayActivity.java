package com.example.onebuy;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.homemodel.SignInSuccessByToken;
import com.example.model.Getorderid;
import com.example.model.Getorderurl;
import com.example.model.SignOut;
import com.example.newonebuy.R;
import com.example.util.Content;
import com.example.util.GlobalVariable;
import com.example.util.MyShop;
import com.example.util.MyShop_Table;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.xinbo.utils.GsonUtils;
import com.xinbo.utils.HTTPUtils;
import com.xinbo.utils.VolleyListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PayActivity extends ActionBarActivity {

	private ListView listView1;
	ArrayList<MyShop> list = new ArrayList<MyShop>();
	private LvAdapter lvAdapter;
	double allMoney;
	int choosedNum;
	private TextView text_num_shopfra2;
	private TextView text_num_shopfra;
	String token;
	String uid;
	String score;
	int Mode = 0; // 0余额支付，1云支付
	private TextView text_money;
	private String orderId; // 云支付订单
	private RelativeLayout rela_web;
	private WebView webView1;
	private WebSettings settings;
	Handler Handler = new Handler();
	boolean search = false;
	private String userMoney = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pay);

		text_num_shopfra = (TextView) findViewById(R.id.text_num_shopfra);
		text_num_shopfra2 = (TextView) findViewById(R.id.text_num_shopfra2);
		text_money = (TextView) findViewById(R.id.text_money);
		rela_web = (RelativeLayout) findViewById(R.id.rela_web);
		// 网页
		webView1 = (WebView) findViewById(R.id.webView1);
		settings = webView1.getSettings();
		settings.setJavaScriptEnabled(true);

		listView1 = (ListView) findViewById(R.id.listView1);
		lvAdapter = new LvAdapter();
		listView1.setAdapter(lvAdapter);
		searchSql();

		RadioButton radio0 = (RadioButton) findViewById(R.id.radio0);
		RadioButton radio1 = (RadioButton) findViewById(R.id.radio1);

		// 余额支付
		radio0.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					Mode = 0;
				} else {
					Mode = 1;
				}
			}
		});
		// 云支付
		radio1.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					Mode = 1;
				} else {
					Mode = 0;
				}
			}
		});

		// 支付
		findViewById(R.id.btn_signOut).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (token.isEmpty()) {
					Toast.makeText(PayActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
					return;
				} else {
					if (Mode == 0) { // 余额支付
						if ((allMoney > Double.parseDouble(userMoney)) == true) {
							Toast.makeText(PayActivity.this, "余额不足，请使用云支付", Toast.LENGTH_SHORT).show();
							return;
						} else{
							signInByToken(); // 获得积分
						}
					} else {
						cloudPay();
					}
				}
			}
		});

		findViewById(R.id.back).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	public void cloudPay() {
		getOrderId();
	}

	public void orderState() {
		String url = Content.URL.ORDERSTATE + token + "&uid=" + uid + "&order_id=" + orderId;
		HTTPUtils.get(PayActivity.this, url, new VolleyListener() {

			@Override
			public void onResponse(String arg0) {
				SignOut parseJSON = GsonUtils.parseJSON(arg0, SignOut.class);
				if (parseJSON.getTag() == 1) { // 查询到该订单已支付完成
					finish();
				} else {
				}
			}

			@Override
			public void onErrorResponse(VolleyError arg0) {

			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		search = false;
	}

	private void getPayUrl() {
		String url = Content.URL.CLOUDPAY;
		Map<String, String> params = new HashMap<String, String>();
		params.put("token", token);
		params.put("uid", uid);
		params.put("order_id", orderId);
		String jsonStirng = listTojSON();
		params.put("car_list", jsonStirng);
		HTTPUtils.post(PayActivity.this, url, params, new VolleyListener() {

			@Override
			public void onResponse(String arg0) {
				Log.e("==", arg0);
				Getorderurl parseJSON = GsonUtils.parseJSON(arg0, Getorderurl.class);
				if (parseJSON.getTag() == 1) {
					String payUrl = parseJSON.getData().getUrl();
					Log.e("==", payUrl);
					rela_web.setVisibility(View.VISIBLE);

					webView1.setWebChromeClient(new WebChromeClient());
					webView1.setWebViewClient(new WebViewClient());

					webView1.loadUrl("http://" + payUrl);
					search = true;

					Handler.postDelayed(new Runnable() {

						@Override
						public void run() {
							if (search) {
								orderState();
								Handler.postDelayed(this, 5000);
							}
						}
					}, 5000);
				}

			}

			@Override
			public void onErrorResponse(VolleyError arg0) {
			}
		});
	}

	private void getOrderId() {
		String url = Content.URL.GETORDERID + token + "&uid=" + uid;
		HTTPUtils.get(PayActivity.this, url, new VolleyListener() {

			@Override
			public void onResponse(String arg0) {
				Getorderid parseJSON = GsonUtils.parseJSON(arg0, Getorderid.class);
				if (parseJSON.getTag() == 1) {
					orderId = parseJSON.getData().getOrderId();
					getPayUrl();
				}

			}

			@Override
			public void onErrorResponse(VolleyError arg0) {

			}
		});
	}

	public void signInByToken() {
		String url = Content.URL.SIGNINBYTOKEN + token + "&uid=" + uid;
		HTTPUtils.get(PayActivity.this, url, new VolleyListener() {

			@Override
			public void onResponse(String arg0) {
				Spanned fromHtml = Html.fromHtml(arg0);
				SignInSuccessByToken parseJSON = GsonUtils.parseJSON(fromHtml + "", SignInSuccessByToken.class);
				com.example.homemodel.User user2 = parseJSON.getUser();
				score = user2.getFscore();
				pay();
			}

			@Override
			public void onErrorResponse(VolleyError arg0) {

			}
		});
	}

	public String listTojSON() {
		String json = "{";
		for (int i = 0; i < list.size(); i++) {
			MyShop shop = list.get(i);
			json += shop.toString();
			if (i != (list.size() - 1)) {
				json += ",";
			}
		}
		json += "}";
		return json;
	}

	public void pay() {
		String jsonStirng = listTojSON();
		String url = Content.URL.PAY + token + "&uid=" + uid + "&checkpay=" + "money" + "&banktype=" + "money"
				+ "&money=" + allMoney + "&jifen=" + score + "&car_list=" + jsonStirng;
		
		HTTPUtils.get(PayActivity.this, url, new VolleyListener() {

			@Override
			public void onResponse(String arg0) {
				SignOut parseJSON = GsonUtils.parseJSON(arg0, SignOut.class);
				if (parseJSON.getTag() == 1) { // 支付成功
					Toast.makeText(PayActivity.this, parseJSON.getMessage(), Toast.LENGTH_SHORT).show();
					delPaySucceed();
					finish();
				} else {
					Toast.makeText(PayActivity.this, parseJSON.getMessage(), Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void onErrorResponse(VolleyError arg0) {
			}
		});
	}

	public void delPaySucceed() {
		for (int i = 0; i < list.size(); i++) {
			new Delete().from(MyShop.class).where(MyShop_Table.goodid.eq(list.get(i).getGoodid())).query();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		GlobalVariable instance = GlobalVariable.getInstance();
		token = instance.getSpToken();
		uid = instance.getSpUid();

		String url = Content.URL.SIGNINBYTOKEN + token + "&uid=" + uid;
		HTTPUtils.get(PayActivity.this, url, new VolleyListener() {

			@Override
			public void onResponse(String arg0) {
				Spanned fromHtml = Html.fromHtml(arg0);
				SignInSuccessByToken parseJSON = GsonUtils.parseJSON(fromHtml + "", SignInSuccessByToken.class);
				if (parseJSON.getTag() == 1) { // 登陆成功
					com.example.homemodel.User user2 = parseJSON.getUser();
					userMoney = user2.getMoney();
					text_money.setText("使用余额支付（余额：" + user2.getMoney() + "元）");
				} else {
					Toast.makeText(PayActivity.this, parseJSON.getMessage(), Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void onErrorResponse(VolleyError arg0) {

			}
		});
	}

	/**
	 * 计算数据库中的选中个数和总价并在text上显示
	 */
	private void calculateMoneyAndNum() {
		choosedNum = 0;
		allMoney = 0;
		List<MyShop> dataList = new Select().from(MyShop.class).queryList();
		for (int i = 0; i < dataList.size(); i++) {
			MyShop shop = dataList.get(i);
			if (shop.getIsCheck()) { // 选中状态
				choosedNum++;
				allMoney += Double.parseDouble(shop.getAllprice());
			}
		}
		text_num_shopfra.setText(choosedNum + "");
		text_num_shopfra2.setText(allMoney + "元");
	}

	public void searchSql() {
		list.clear();
		List<MyShop> dataList = new Select().from(MyShop.class).queryList();
		for (int i = 0; i < dataList.size(); i++) {
			MyShop shop = dataList.get(i);
			if (shop.getIsCheck()) {
				list.add(shop);
			}
		}
		lvAdapter.notifyDataSetChanged();

		calculateMoneyAndNum();
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
			View view = getLayoutInflater().inflate(R.layout.item_lv_pay, null);
			TextView text_title_shopfra = (TextView) view.findViewById(R.id.text_title_shopfra);
			TextView text_price_shopfra = (TextView) view.findViewById(R.id.text_price_shopfra);
			ImageView ima_shopfra = (ImageView) view.findViewById(R.id.ima_shopfra);
			TextView editText1 = (TextView) view.findViewById(R.id.editText1);

			MyShop shop = list.get(position);
			String goodid = shop.getGoodid();
			GlobalVariable.displayImageNoAnim(shop.getIma(), ima_shopfra);
			text_title_shopfra.setText("(第" + shop.getNum() + "期)" + shop.getTitle());
			text_price_shopfra.setText("￥" + shop.getPrice());
			editText1.setText(list.get(position).getBuynum());
			return view;
		}

	}

}
