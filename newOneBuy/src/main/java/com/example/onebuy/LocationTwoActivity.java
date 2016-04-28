package com.example.onebuy;

import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.VolleyError;
import com.example.newonebuy.R;
import com.example.model.Allcity;
import com.example.model.Cities;
import com.example.util.APIClient;
import com.example.util.GlobalVariable;
import com.xinbo.utils.GsonUtils;
import com.xinbo.utils.VolleyListener;
import com.xinbo.widget.GridView4ScrollView;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LocationTwoActivity extends ActionBarActivity implements OnClickListener {

	private ListView lv;
	ArrayList<Allcity> allcityList = new ArrayList<Allcity>();
	ArrayList<Allcity> hotCity = new ArrayList<Allcity>();
	private MyAdapter myAdapter;
	private GvAdapter gvAdapter;
	private GridView4ScrollView gv;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_location_two);

		initUI();
		initData();
	}

	private void initData() {
		APIClient.requestCities(LocationTwoActivity.this, new VolleyListener() {

			@Override
			public void onResponse(String arg0) {
				Cities cities = GsonUtils.parseJSON(arg0, Cities.class);
				List<Allcity> allcity = cities.getAllcity();
				editList(allcity);
				// myAdapter.notifyDataSetChanged();
				// nointernet.setText("数据加载成功!");
				// nointernet.setVisibility(View.GONE);
				// lv.setVisibility(View.VISIBLE);
				// letterbar.setVisibility(View.VISIBLE);
			}

			@Override
			public void onErrorResponse(VolleyError arg0) {
			}
		});
	}

	public void editList(List<Allcity> list) {
		for (int i = 0; i < list.size(); i++) {
			if (i == 0) {
				continue;
			}
			if (list.get(i).getName().isEmpty()) {
				continue;
			}
			Allcity allcity = list.get(i);
			String string = allcity.getPinyin();
			// 得到首字母
			String substring = string.substring(0, 1);
			// 上一个字符串
			String string2;
			if (list.get(i - 1).getName().isEmpty()) {
				string2 = string;
			} else {
				string2 = list.get(i - 1).getPinyin();
			}
			// 上一个字符串的首字母
			String substring2 = string2.substring(0, 1);

			if (!substring2.equals(substring)) { // 如果两个字母不一样
				if ((i % 4) != 0) { // 不一样的字母不在第一个
					int num = (4 - (i % 4));
					for (int j = 0; j < num; j++) {
						list.add(i, new Allcity("", "", "", "", "", "", "", ""));
					}
				}
			}
		}

		allcityList.addAll(list);
		myAdapter.notifyDataSetChanged();
	}

	private void initUI() {
		findViewById(R.id.back).setOnClickListener(this);

		hotCity.add(new Allcity("", "", "", "", "北京", "", "", ""));
		hotCity.add(new Allcity("", "", "", "", "上海", "", "", ""));
		hotCity.add(new Allcity("", "", "", "", "广州", "", "", ""));
		hotCity.add(new Allcity("", "", "", "", "杭州", "", "", ""));
		hotCity.add(new Allcity("", "", "", "", "厦门", "", "", ""));
		hotCity.add(new Allcity("", "", "", "", "深圳", "", "", ""));
		hotCity.add(new Allcity("", "", "", "", "福州", "", "", ""));
		hotCity.add(new Allcity("", "", "", "", "龙岩", "", "", ""));
		hotCity.add(new Allcity("", "", "", "", "天津", "", "", ""));

		lv = (ListView) findViewById(R.id.lv_locationact);
		myAdapter = new MyAdapter();

		View header = getLayoutInflater().inflate(R.layout.item_header_location2, null);
		gv = (GridView4ScrollView) header.findViewById(R.id.gv_location2);
		gvAdapter = new GvAdapter();
		gv.setAdapter(gvAdapter);
		
		gv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				writeTpSp(hotCity.get(arg2).getName());
			}
		});
		
		lv.addHeaderView(header);

		lv.setAdapter(myAdapter);
	}

	class GvAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return hotCity.size();
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
			View view = getLayoutInflater().inflate(R.layout.item_gv_location2, null);
			TextView text = (TextView) view.findViewById(R.id.textView1);
			text.setText(hotCity.get(position).getName());
			return view;
		}

	}

	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return allcityList.size() / 4;
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
			RelativeLayout rela_letter;
			RelativeLayout rela_location1;
			RelativeLayout rela_location2;
			RelativeLayout rela_location3;
			RelativeLayout rela_location4;
			TextView text_letter;
			TextView text1;
			TextView text2;
			TextView text3;
			TextView text4;
			if (convertView == null) {
				view = getLayoutInflater().inflate(R.layout.item_lv_location2, null);
				rela_letter = (RelativeLayout) view.findViewById(R.id.relativeLayout1);
				rela_location1 = (RelativeLayout) view.findViewById(R.id.rela_location1);
				rela_location2 = (RelativeLayout) view.findViewById(R.id.rela_location2);
				rela_location3 = (RelativeLayout) view.findViewById(R.id.rela_location3);
				rela_location4 = (RelativeLayout) view.findViewById(R.id.rela_location4);
				text_letter = (TextView) view.findViewById(R.id.textView0);
				text1 = (TextView) view.findViewById(R.id.textView1);
				text2 = (TextView) view.findViewById(R.id.textView2);
				text3 = (TextView) view.findViewById(R.id.textView3);
				text4 = (TextView) view.findViewById(R.id.textView4);
				view.setTag(new MyTag(rela_letter, rela_location1, rela_location2, rela_location3, rela_location4,
						text_letter, text1, text2, text3, text4));
			} else {
				view = convertView;
				MyTag tag2 = (MyTag) view.getTag();
				rela_letter = tag2.rela_letter;
				rela_location1 = tag2.rela_location1;
				rela_location2 = tag2.rela_location2;
				rela_location3 = tag2.rela_location3;
				rela_location4 = tag2.rela_location4;
				text_letter = tag2.text_letter;
				text1 = tag2.text1;
				text2 = tag2.text2;
				text3 = tag2.text3;
				text4 = tag2.text4;
			}

			Allcity allcity1 = null;
			Allcity allcity2 = null;
			Allcity allcity3 = null;
			Allcity allcity4 = null;
			switch (allcityList.size() % 4) {
			case 0: // 最后一行只有1个
				if (position == (allcityList.size() / 4)) { // 最后一行
					allcity1 = allcityList.get(position * 4);
					allcity2 = new Allcity("", "", "", "", "", "", "", "");
					allcity3 = new Allcity("", "", "", "", "", "", "", "");
					allcity4 = new Allcity("", "", "", "", "", "", "", "");
				} else {
					allcity1 = allcityList.get(position * 4);
					allcity2 = allcityList.get(position * 4 + 1);
					allcity3 = allcityList.get(position * 4 + 2);
					allcity4 = allcityList.get(position * 4 + 3);
				}
				break;
			case 1: // 最后一行只有2个
				if (position == (allcityList.size() / 4)) { // 最后一行
					allcity1 = allcityList.get(position * 4);
					allcity2 = allcityList.get(position * 4 + 1);
					allcity3 = new Allcity("", "", "", "", "", "", "", "");
					allcity4 = new Allcity("", "", "", "", "", "", "", "");
				} else {
					allcity1 = allcityList.get(position * 4);
					allcity2 = allcityList.get(position * 4 + 1);
					allcity3 = allcityList.get(position * 4 + 2);
					allcity4 = allcityList.get(position * 4 + 3);
				}
				break;
			case 2: // 最后一行只有3个
				if (position == (allcityList.size() / 4)) { // 最后一行
					allcity1 = allcityList.get(position * 4);
					allcity2 = allcityList.get(position * 4 + 1);
					allcity3 = allcityList.get(position * 4 + 2);
					allcity4 = new Allcity("", "", "", "", "", "", "", "");
				} else {
					allcity1 = allcityList.get(position * 4);
					allcity2 = allcityList.get(position * 4 + 1);
					allcity3 = allcityList.get(position * 4 + 2);
					allcity4 = allcityList.get(position * 4 + 3);
				}
				break;
			case 3: // 最后一行4个
				allcity1 = allcityList.get(position * 4);
				allcity2 = allcityList.get(position * 4 + 1);
				allcity3 = allcityList.get(position * 4 + 2);
				allcity4 = allcityList.get(position * 4 + 3);
				break;

			default:
				break;
			}

			String string = allcity1.getPinyin();
			String substring = string.substring(0, 1); // 首字母
			text1.setText(allcity1.getName());

			if (allcity2.getPinyin().isEmpty()) {
				rela_location2.setVisibility(View.INVISIBLE);
			} else {
				rela_location2.setVisibility(View.VISIBLE);
				text2.setText(allcity2.getName());
			}
			if (allcity3.getPinyin().isEmpty()) {
				rela_location3.setVisibility(View.INVISIBLE);
			} else {
				rela_location3.setVisibility(View.VISIBLE);
				text3.setText(allcity3.getName());
			}
			if (allcity4.getPinyin().isEmpty()) {
				rela_location4.setVisibility(View.INVISIBLE);
			} else {
				rela_location4.setVisibility(View.VISIBLE);
				text4.setText(allcity4.getName());
			}

			if (position == 0) { // 第0行，显示字母
				rela_letter.setVisibility(View.VISIBLE);
				text_letter.setText("A");
			} else {
				if (position != (allcityList.size() / 4)) { // 最后一行是有人为修改，所以不参考
					if (allcityList.get((position - 1) * 4 + 3).getPinyin().isEmpty()) { // 为空
						rela_letter.setVisibility(View.VISIBLE);
						text_letter.setText(substring.toUpperCase());
					} else {
						rela_letter.setVisibility(View.GONE);
					}
				}
			}

			final String name = allcity1.getName();
			rela_location1.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (!name.isEmpty()) {
						writeTpSp(name);
					}
				}
			});
			final String name2 = allcity2.getName();
			rela_location2.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (!name2.isEmpty()) {
						writeTpSp(name2);
					}
				}
			});
			final String name3 = allcity3.getName();
			rela_location3.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (!name3.isEmpty()) {
						writeTpSp(name3);
					}
				}
			});
			final String name4 = allcity4.getName();
			rela_location4.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (!name4.isEmpty()) {
						writeTpSp(name4);
					}
				}
			});

			return view;
		}
	}

	private void writeTpSp(final String name) {
		GlobalVariable.getInstance().setSpValue("city", name);
		finish();
	}

	class MyTag {
		RelativeLayout rela_letter;
		RelativeLayout rela_location1;
		RelativeLayout rela_location2;
		RelativeLayout rela_location3;
		RelativeLayout rela_location4;
		TextView text_letter;
		TextView text1;
		TextView text2;
		TextView text3;
		TextView text4;

		public MyTag(RelativeLayout rela_letter, RelativeLayout rela_location1, RelativeLayout rela_location2,
				RelativeLayout rela_location3, RelativeLayout rela_location4, TextView text_letter, TextView text1,
				TextView text2, TextView text3, TextView text4) {
			super();
			this.rela_letter = rela_letter;
			this.rela_location1 = rela_location1;
			this.rela_location2 = rela_location2;
			this.rela_location3 = rela_location3;
			this.rela_location4 = rela_location4;
			this.text_letter = text_letter;
			this.text1 = text1;
			this.text2 = text2;
			this.text3 = text3;
			this.text4 = text4;
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;

		default:
			break;
		}
	}
}
