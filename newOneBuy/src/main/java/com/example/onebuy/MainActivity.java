package com.example.onebuy;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;
import com.android.volley.VolleyError;
import com.example.newonebuy.R;
import com.example.fragment.AllProductFragment;
import com.example.fragment.HomeFragment;
import com.example.fragment.MeFragment;
import com.example.fragment.ScoreShopFragment;
import com.example.fragment.ShopFragment;
import com.example.homemodel.SignInSuccessByToken;
import com.example.model.Appbean;
import com.example.model.Data9;
import com.example.util.Content;
import com.example.util.GlobalVariable;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;
import com.umeng.analytics.MobclickAgent;
import com.xinbo.utils.GsonUtils;
import com.xinbo.utils.HTTPUtils;
import com.xinbo.utils.TextViewUtils;
import com.xinbo.utils.UILUtils;
import com.xinbo.utils.VolleyListener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {
	private FragmentTabHost mTabHost;
	private LayoutInflater inflater;
	setTabHostBroadCast setTabHostBroadCast;
	private Bundle bundle;
	private Bundle bundle_score;
	private Bundle bundle_me;
	private String stringExtra;
	private String stringExtra2;
	private String scoreCityCategory;
	private String scoreCityAllProduct;
	String token;
	String uid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// 蒲公英检查更新
		// PgyUpdateManager.register(this);
		PgyUpdateManager.register(MainActivity.this, new UpdateManagerListener() {

			@Override
			public void onUpdateAvailable(final String result) {

				// 将新版本信息封装到AppBean中
				Appbean parseJSON = GsonUtils.parseJSON(result, Appbean.class);
				final Data9 data = parseJSON.getData();
				new AlertDialog.Builder(MainActivity.this).setTitle("更新").setMessage(data.getReleaseNote())
						.setNegativeButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						startDownloadTask(MainActivity.this, data.getDownloadURL());
					}
				}).show();
			}

			@Override
			public void onNoUpdateAvailable() {
			}
		});

		MobclickAgent.setCatchUncaughtExceptions(true);

		GlobalVariable instance = GlobalVariable.getInstance();
		boolean spShowIma = instance.getSpShowIma();
		GlobalVariable.IsShowIma = spShowIma;
		token = instance.getSpToken();
		uid = instance.getSpUid();

		setTabHostBroadCast = new setTabHostBroadCast();
		IntentFilter intentFilter2 = new IntentFilter();
		intentFilter2.addAction("tabhost");
		registerReceiver(setTabHostBroadCast, intentFilter2);

		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

		inflater = getLayoutInflater();
		addTab("妙购", R.drawable.selector_tab_home, HomeFragment.class);

		View tabItem2 = inflater.inflate(R.layout.tab_item_2, null);
		TextView textView2 = (TextView) tabItem2.findViewById(R.id.item_name);
		textView2.setText("所有商品");
		bundle = new Bundle(); // 预加载传递数据的桥梁
		mTabHost.addTab(mTabHost.newTabSpec("所有商品").setIndicator(tabItem2), AllProductFragment.class, bundle);

		// 购物车
		View tabItem3 = inflater.inflate(R.layout.tab_item2, null);
		TextView textView3 = (TextView) tabItem3.findViewById(R.id.item_name);
		textView3.setText("");
		mTabHost.addTab(mTabHost.newTabSpec("").setIndicator(tabItem3), ShopFragment.class, null);

		// ImageView mid_ima = (ImageView) findViewById(R.id.mid_ima);
		// mid_ima.setImageDrawable(getResources().getDrawable(R.drawable.tab_mid));

		View tabItem4 = inflater.inflate(R.layout.tab_item_3, null);
		TextView textView4 = (TextView) tabItem4.findViewById(R.id.item_name);
		textView4.setText("积分商城");
		bundle_score = new Bundle();
		mTabHost.addTab(mTabHost.newTabSpec("所有积分").setIndicator(tabItem4), ScoreShopFragment.class, bundle_score);

		View tabItem5 = inflater.inflate(R.layout.tab_item_4, null);
		TextView textView5 = (TextView) tabItem5.findViewById(R.id.item_name);
		textView5.setText("我的金库");
		bundle_me = new Bundle();
		mTabHost.addTab(mTabHost.newTabSpec("我的金库").setIndicator(tabItem5), MeFragment.class, bundle_me);

		needSignIn();

		Intent intent = getIntent();
		if (intent.getIntExtra("item", -1) != -1) {
			int intExtra = intent.getIntExtra("item", -1);
			mTabHost.setCurrentTab(intExtra);
			Log.e("==", "" + intExtra);
		}
	}

	private void needSignIn() {
		if (token.equals("")) {
			bundle_me.putString("isSignIn", "no");
			return;
		}
		String url = Content.URL.SIGNINBYTOKEN + token + "&uid=" + uid;
		HTTPUtils.get(MainActivity.this, url, new VolleyListener() {

			@Override
			public void onResponse(String arg0) {
				Spanned fromHtml = Html.fromHtml(arg0);
				SignInSuccessByToken parseJSON = GsonUtils.parseJSON(fromHtml + "", SignInSuccessByToken.class);
				Integer tag2 = parseJSON.getTag();
				if (tag2 == 1) { // token还有效
				} else {
					GlobalVariable.getInstance().setSpToken("");
					GlobalVariable.getInstance().setSpUid("");
				}
			}

			@Override
			public void onErrorResponse(VolleyError arg0) {

			}
		});
	}

	private void addTab(String title, int drawableRes, Class fragmentClass) {
		View tabItem1 = inflater.inflate(R.layout.tab_item, null);
		TextView textView = (TextView) tabItem1.findViewById(R.id.item_name);
		textView.setText(title);
		mTabHost.addTab(mTabHost.newTabSpec(title).setIndicator(tabItem1), fragmentClass, null);
	}

	class setTabHostBroadCast extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			int intExtra = intent.getIntExtra("tabhost", 0);
			bundle.putInt("tabhost", intExtra);
			mTabHost.setCurrentTab(1);
			handle.postDelayed(new Runnable() {

				@Override
				public void run() {
					bundle.putInt("tabhost", 0);
				}
			}, 500); // 延时0.5s后设置回默认进去全国商品
		}
	}

	Handler handle = new Handler();

	@Override
	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(setTabHostBroadCast);
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("SplashScreen");
		MobclickAgent.onResume(this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("SplashScreen");
		MobclickAgent.onPause(this);
	}
}
