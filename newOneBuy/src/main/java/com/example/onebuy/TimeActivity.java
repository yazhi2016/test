package com.example.onebuy;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.PopupMenu;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xutils.x;

import com.android.volley.VolleyError;
import com.example.newonebuy.R;
import com.example.homemodel.AllProduct;
import com.example.homemodel.Category;
import com.example.homemodel.Datum;
import com.example.homemodel.ShoplistNew;
import com.example.util.Content;
import com.example.util.GlobalVariable;
import com.example.util.RushBuyCountDownTimerView2;
import com.xinbo.utils.GsonUtils;
import com.xinbo.utils.HTTPUtils;
import com.xinbo.utils.VolleyListener;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class TimeActivity extends ActionBarActivity implements OnClickListener {

	private GridView gv;
	private GvAdapter gvAdapter;
	Handler handler1 = new Handler();
	ArrayList<ShoplistNew> newList = new ArrayList<ShoplistNew>();
	String category = "0000";
	ArrayList<Datum> categoryList = new ArrayList<Datum>();

	Map<Integer, RushBuyCountDownTimerView2> timeList = new HashMap<Integer, RushBuyCountDownTimerView2>();

	ArrayList<Integer> runList = new ArrayList<Integer>();

	Menu menu;
	boolean isFinish = false;
	int number = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_time);

		initUI();
		initData();
		initCategory();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		isFinish = true;
	}

	private void initCategory() {
		String url = Content.URL.CATEGORY;
		HTTPUtils.get(TimeActivity.this, url, new VolleyListener() {

			@Override
			public void onResponse(String arg0) {
				Category parseJSON = GsonUtils.parseJSON(arg0, Category.class);
				List<Datum> data = parseJSON.getData();
				categoryList.clear();
				categoryList.addAll(data);
			}

			@Override
			public void onErrorResponse(VolleyError arg0) {

			}
		});
	}

	private void initData() {
		String url;
		url = Content.URL.ALLPRODUCT + category + "&page=" + 0;
		HTTPUtils.get(TimeActivity.this, url, new VolleyListener() {

			@Override
			public void onResponse(String arg0) {
				Spanned fromHtml = Html.fromHtml(arg0);
				newList.clear();
				AllProduct parseJSON = GsonUtils.parseJSON(fromHtml + "", AllProduct.class);
				List<ShoplistNew> shoplistNew = parseJSON.getShoplistNew();
				newList.addAll(shoplistNew);
				runList.clear();
				number = 0;
				gvAdapter.notifyDataSetChanged();
			}

			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void initUI() {
		gv = (GridView) findViewById(R.id.gv_timeact);
		gvAdapter = new GvAdapter();
		gv.setAdapter(gvAdapter);

		findViewById(R.id.pop_timeact).setOnClickListener(this);
		findViewById(R.id.back).setOnClickListener(this);

		gv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Intent intent = new Intent(TimeActivity.this, DetailActivity.class);
				intent.putExtra("id", newList.get(arg2).getId());
				startActivity(intent);
			}
		});
	}

	class GvAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return newList.size();
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
			View view;
			RushBuyCountDownTimerView2 timerView;
			ImageView imageView1;
			TextView textView1;
			TextView textView2;
			if (convertView == null) {
				view = getLayoutInflater().inflate(R.layout.item_gv_timeact, null);
				timerView = (RushBuyCountDownTimerView2) view.findViewById(R.id.timerView);
				imageView1 = (ImageView) view.findViewById(R.id.imageView1);
				textView1 = (TextView) view.findViewById(R.id.textView1);
				textView2 = (TextView) view.findViewById(R.id.textView2);
				
				timerView.setTime(5, 0, 0);
				timerView.start();
				
				view.setTag(new GvTag(imageView1, textView1, textView2, timerView));
			} else {
				view = convertView;
				GvTag tag2 = (GvTag) view.getTag();
				imageView1 = tag2.imageView1;
				textView1 = tag2.textView1;
				textView2 = tag2.textView2;
				timerView = tag2.timerView;
			}

//			timeList.put(position, timerView);
//			if (timeList.size() == newList.size()) {
//				for (int i = 0; i < timeList.size(); i++) {
//					RushBuyCountDownTimerView2 timer = timeList.get(i);
//					timer.setTime(5, 0, 0);
//					timer.start();
//				}
//			}

			ShoplistNew shoplistNew = newList.get(position);
			GlobalVariable.displayImageNoAnim(Content.URL.IMA_HOST + shoplistNew.getThumb(), imageView1);
			textView1.setText("第" + shoplistNew.getQishu() + "期");
			textView2.setText(shoplistNew.getTitle());
			return view;
		}
	}

	boolean timeStop = false;

	class GvTag {
		ImageView imageView1;
		TextView textView1;
		TextView textView2;
		RushBuyCountDownTimerView2 timerView;

		public GvTag(ImageView imageView1, TextView textView1, TextView textView2,
				RushBuyCountDownTimerView2 timerView) {
			super();
			this.imageView1 = imageView1;
			this.textView1 = textView1;
			this.textView2 = textView2;
			this.timerView = timerView;
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pop_timeact: // 点击分类
			PopupMenu popupMenu = new PopupMenu(this, v);
			Menu menu = popupMenu.getMenu();
			for (int i = 0; i < categoryList.size(); i++) {
				Datum datum = categoryList.get(i);
				if (datum.getCatename().equals("0元专区")) {
					continue;
				}
				menu.add(Menu.NONE, i, 0, datum.getCatename());
			}
			popupMenu.getMenuInflater().inflate(R.menu.time, popupMenu.getMenu());
			// 菜单按钮的点击事件
			popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

				@Override
				public boolean onMenuItemClick(MenuItem arg0) {
					int itemId = arg0.getItemId();
					category = categoryList.get(itemId).getCateid();
					initData();
					return false;
				}
			});
			popupMenu.show();
			break;
		case R.id.back:
			finish();
			break;

		default:
			break;
		}
	}

}
