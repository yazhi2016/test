package com.example.onebuy;

import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.VolleyError;
import com.example.newonebuy.R;
import com.example.model.Allcity;
import com.example.model.Cities;
import com.example.util.APIClient;
import com.example.util.LetterSideBar;
import com.example.util.LetterSideBar.OnLetterChangedListener;
import com.xinbo.utils.GsonUtils;
import com.xinbo.utils.VolleyListener;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LocationActivity extends ActionBarActivity implements OnClickListener {

	private ListView lv;
	private LayoutInflater inflater;
	private LetterSideBar letterbar;
	private TextView nowLetter;
	ArrayList<Allcity> allcityList = new ArrayList<Allcity>();
	ArrayList<Allcity> searchCityList = new ArrayList<Allcity>();
	private MyAdapter myAdapter;
	private GvAdapter1 gvAdapter1;
	private GvAdapter2 gvAdapter2;
	private EditText edittext;
	private ListView lv_search;
	private searchLvAdapter searchLvAdapter;
	private RelativeLayout rela_lv_locationact;
	private TextView nointernet;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_location);

		initUI();
		initData();
	}

	private void initData() {
		APIClient.requestCities(LocationActivity.this, new VolleyListener() {

			@Override
			public void onResponse(String arg0) {
				Cities cities = GsonUtils.parseJSON(arg0, Cities.class);
				List<Allcity> allcity = cities.getAllcity();
				allcityList.addAll(allcity);
				nointernet.setText("数据加载成功!");
				myAdapter.notifyDataSetChanged();
				nointernet.setVisibility(View.GONE);
				lv.setVisibility(View.VISIBLE);
				letterbar.setVisibility(View.VISIBLE);
			}

			@Override
			public void onErrorResponse(VolleyError arg0) {
			}
		});
	}

	private void initUI() {

		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		rela_lv_locationact = (RelativeLayout) findViewById(R.id.rela_lv_locationact);
		nowLetter = (TextView) findViewById(R.id.nowletter);
		letterbar = (LetterSideBar) findViewById(R.id.letterSideBar1);
		letterbar.setOnLetterChangedListener(new OnLetterChangedListener() {

			@Override
			public void onTouchAction(int action) {
				switch (action) {
				case MotionEvent.ACTION_DOWN: // 按下
					nowLetter.setVisibility(View.VISIBLE);
					break;
				case MotionEvent.ACTION_MOVE: // 滑动
					break;
				case MotionEvent.ACTION_UP: // 松开
					nowLetter.setVisibility(View.GONE);
					break;

				default:
					break;
				}
			}

			@Override
			public void onLetterChanged(String letter) {
				nowLetter.setText(letter);
				for (int i = 0; i < allcityList.size(); i++) {
					String cityFirst = allcityList.get(i).getPinyin().substring(0, 1);
					String lowerCase = letter.toLowerCase();
					if (lowerCase.equals(cityFirst)) {
						lv.setSelection(i);
						break;
					}
				}
			}
		});
		lv = (ListView) findViewById(R.id.lv_locationact);

		nointernet = (TextView) findViewById(R.id.nointernet);
		nointernet.setText("正在下载数据...");
		
		inflater = getLayoutInflater();
		View now_header = inflater.inflate(R.layout.header_location_now, null);
		lv.addHeaderView(now_header);

		View city_header = inflater.inflate(R.layout.header_location_city, null);
		GridView gv1 = (GridView) city_header.findViewById(R.id.gv_locationheader);
		gvAdapter1 = new GvAdapter1();
		gv1.setAdapter(gvAdapter1);
		lv.addHeaderView(city_header);

		View city_header2 = inflater.inflate(R.layout.header_location_city, null);
		TextView text_header = (TextView) city_header2.findViewById(R.id.text_locationheader);
		text_header.setText("开通城市");
		GridView gv2 = (GridView) city_header2.findViewById(R.id.gv_locationheader);
		gvAdapter2 = new GvAdapter2();
		gv2.setAdapter(gvAdapter2);
		lv.addHeaderView(city_header2);

		myAdapter = new MyAdapter();
		lv.setAdapter(myAdapter);
		lv.setDividerHeight(0);

		lv_search = (ListView) findViewById(R.id.lv_search);
		lv_search.setDividerHeight(0);
		searchLvAdapter = new searchLvAdapter();
		lv_search.setAdapter(searchLvAdapter);
		edittext = (EditText) findViewById(R.id.editText1);
		edittext.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				String key = s.toString();
				if ("".equals(key)) {
					lv_search.setVisibility(View.GONE);
					rela_lv_locationact.setVisibility(View.VISIBLE);
				} else {
					rela_lv_locationact.setVisibility(View.GONE);
					lv_search.setVisibility(View.VISIBLE);
				}

				searchCityList.clear();
				searchLvAdapter.notifyDataSetChanged();

				for (int i = 0; i < allcityList.size(); i++) {
					Allcity allcity = allcityList.get(i);
					if (allcity.getPinyin().toLowerCase().startsWith(key) || allcity.getName().startsWith(key)) {
						searchCityList.add(allcity);
						searchLvAdapter.notifyDataSetChanged();
					}
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

		final RelativeLayout Rela_out = (RelativeLayout) findViewById(R.id.Rela_out);
		Rela_out.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

			@Override
			public void onGlobalLayout() {
				// 比较Activity根布局与当前布局的大小
				int heightDiff = Rela_out.getRootView().getHeight() - Rela_out.getHeight();
				if (heightDiff > 100) {
					// 大小超过100时，一般为显示虚拟键盘事件
					letterbar.setVisibility(View.INVISIBLE);
				} else {
					// 大小小于100时，为不显示虚拟键盘或虚拟键盘隐藏
					letterbar.setVisibility(View.VISIBLE);
				}
			}
		});

		Context context = getApplicationContext();
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
		if(activeNetworkInfo != null) { //有网络连接
			nointernet.setVisibility(View.GONE);
			lv.setVisibility(View.VISIBLE);
			letterbar.setVisibility(View.VISIBLE);
		} else { //没有网络连接
			nointernet.setText("请检查网络连接");
			nointernet.setVisibility(View.VISIBLE);
			lv.setVisibility(View.INVISIBLE);
			letterbar.setVisibility(View.INVISIBLE);
		}
		
		findViewById(R.id.back).setOnClickListener(this);
	}

	class GvAdapter1 extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 1;
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
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = inflater.inflate(R.layout.item_gv_locationheader, null);
			return view;
		}

	}

	class GvAdapter2 extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 6;
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
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = inflater.inflate(R.layout.item_gv_locationheader, null);
			return view;
		}

	}

	class searchLvAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return searchCityList.size();
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
			View view = getLayoutInflater().inflate(R.layout.item_lvsearch_locationact, null);
			TextView text = (TextView) view.findViewById(R.id.text_city);
			text.setText(searchCityList.get(position).getName());
			return view;
		}

	}

	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return allcityList.size();
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
			View view = getLayoutInflater().inflate(R.layout.item_lv_locationact, null);
			TextView t1 = (TextView) view.findViewById(R.id.text_letter);
			TextView t2 = (TextView) view.findViewById(R.id.text_city);
			RelativeLayout rela_letter = (RelativeLayout) view.findViewById(R.id.rela_letter);
			Allcity allcity = allcityList.get(position);

			String string = allcity.getPinyin();
			// 得到首字母
			String substring = string.substring(0, 1);
			if (position == 0) {
				t1.setVisibility(View.VISIBLE);
				t2.setVisibility(View.VISIBLE);
				t1.setText(substring.toUpperCase());
				t2.setText(allcity.getName());
			} else {
				// 上一个字符串
				String string2 = allcityList.get(position - 1).getPinyin();
				// 上一个字符串的首字母
				String substring2 = string2.substring(0, 1);
				// 如果两个字母一样
				if (substring2.equals(substring)) {
					rela_letter.setVisibility(View.GONE);
					t2.setText(allcityList.get(position).getName());
				} else { // 如果首字母不一样
					t1.setText(substring.toUpperCase());
					t2.setText(allcityList.get(position).getName());
				}
			}
			return view;
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
