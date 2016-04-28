package com.example.onebuy;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.model.Datum3;
import com.example.model.Datum4;
import com.example.model.Hotsearch;
import com.example.model.SearchList;
import com.example.newonebuy.R;
import com.example.util.BaseActivity;
import com.example.util.Content;
import com.example.util.GlobalVariable;
import com.example.util.MySearch;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.xinbo.utils.GsonUtils;
import com.xinbo.utils.HTTPUtils;
import com.xinbo.utils.VolleyListener;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchActivity extends BaseActivity {

	private TextView text_leftHenxian_popsearch;
	private TextView text_rightHenxian_popsearch;
	private RelativeLayout rela_hot_popsearch;
	private RelativeLayout rela_new_popsearch;
	private GridView gv;
	private GvPopAdapter gvPopAdapter;
	private LvPopAdapter lvPopAdapter;
	private RelativeLayout rela_nosearch;
	private RelativeLayout rela_hassearch;
	private GridView gv_search;
	private GvAdapter gvAdapter;
	private EditText edit_keywords_searchact;
	ArrayList<Datum4> list = new ArrayList<Datum4>();
	ArrayList<Datum3> hotKeywordList = new ArrayList<Datum3>();
	private ListView lv;
	ArrayList<MySearch> SearchLogList = new ArrayList<MySearch>();
	private Button btn_popsearch;
	private TextView mText_nothing;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		initViews();
		init();
		initEvents();
		initSearchLog();
		initHorKeywords();
	}

	// 读取数据库中搜索历史
	private void initSearchLog() {

		List<MySearch> dataList = new Select().from(MySearch.class).queryList();

		SearchLogList.clear();
		SearchLogList.addAll(dataList);
		if (SearchLogList.size() == 0) {
			btn_popsearch.setVisibility(View.INVISIBLE);
		} else {
			btn_popsearch.setVisibility(View.VISIBLE);
		}
		lvPopAdapter.notifyDataSetChanged();
	}

	private void initHorKeywords() {
		String url = Content.URL.HOTKEYWORDS;
		HTTPUtils.get(SearchActivity.this, url, new VolleyListener() {

			@Override
			public void onResponse(String arg0) {
				Hotsearch parseJSON = GsonUtils.parseJSON(arg0, Hotsearch.class);
				if (parseJSON.getTag() == 1) {
					List<Datum3> data = parseJSON.getData();
					hotKeywordList.clear();
					hotKeywordList.addAll(data);
					gvPopAdapter.notifyDataSetChanged();
				} else {
					showShortToast(parseJSON.getMessage());
				}

			}

			@Override
			public void onErrorResponse(VolleyError arg0) {

			}
		});
	}

	class GvAdapter extends BaseAdapter {

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
			View view;
			ImageView ima_product_gv;
			TextView text_num_gvitem;
			TextView text_title_gvitem;
			if (convertView == null) {
				view = getLayoutInflater().inflate(R.layout.item_gv_searchact, null);
				ima_product_gv = (ImageView) view.findViewById(R.id.ima_product_gv);
				text_num_gvitem = (TextView) view.findViewById(R.id.text_num_gvitem);
				text_title_gvitem = (TextView) view.findViewById(R.id.text_title_gvitem);
				view.setTag(new MyTag(ima_product_gv, text_num_gvitem, text_title_gvitem));
			} else {
				view = convertView;
				MyTag tag2 = (MyTag) view.getTag();
				ima_product_gv = tag2.ima_product_gv;
				text_num_gvitem = tag2.text_num_gvitem;
				text_title_gvitem = tag2.text_title_gvitem;
			}

			Datum4 product = list.get(position);
			text_title_gvitem.setText(product.getTitle());
			text_num_gvitem.setText("第" + product.getQishu() + "期");
			GlobalVariable.displayImageNoAnim(Content.URL.IMA_HOST + product.getThumb(), ima_product_gv);
			final String id = product.getId();
			view.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(SearchActivity.this, DetailActivity.class);
					intent.putExtra("id", id);
					startActivity(intent);
				}
			});
			return view;
		}
	}

	class MyTag {
		ImageView ima_product_gv;
		TextView text_num_gvitem;
		TextView text_title_gvitem;

		public MyTag(ImageView ima_product_gv, TextView text_num_gvitem, TextView text_title_gvitem) {
			super();
			this.ima_product_gv = ima_product_gv;
			this.text_num_gvitem = text_num_gvitem;
			this.text_title_gvitem = text_title_gvitem;
		}

	}

	class LvPopAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return SearchLogList.size();
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
			View view = getLayoutInflater().inflate(R.layout.iten_lv_popsearch, null);
			TextView text_name_lvitem_popsearch = (TextView) view.findViewById(R.id.text_name_lvitem_popsearch);
			text_name_lvitem_popsearch.setText(SearchLogList.get(position).keywords);
			return view;
		}

	}

	class GvPopAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return hotKeywordList.size();
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
			View view = getLayoutInflater().inflate(R.layout.item_gv_popsearch, null);
			TextView text_name_gvpopadpter = (TextView) view.findViewById(R.id.text_name_gvpopadpter);
			text_name_gvpopadpter.setText(hotKeywordList.get(position).getKeywords());
			return view;
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_popsearch: // 清除历史记录
			new Delete().from(MySearch.class).execute();
			initSearchLog();
			break;
		case R.id.check_new: // 点击最近搜索
			text_leftHenxian_popsearch.setVisibility(View.VISIBLE);
			text_rightHenxian_popsearch.setVisibility(View.INVISIBLE);
			rela_new_popsearch.setVisibility(View.VISIBLE);
			rela_hot_popsearch.setVisibility(View.INVISIBLE);
			break;
		case R.id.check_hot: // 点击热门搜索
			text_rightHenxian_popsearch.setVisibility(View.VISIBLE);
			text_leftHenxian_popsearch.setVisibility(View.INVISIBLE);
			rela_hot_popsearch.setVisibility(View.VISIBLE);
			rela_new_popsearch.setVisibility(View.INVISIBLE);
			break;
		case R.id.ima_search_seatchact: // 点击搜索
			rela_nosearch.setVisibility(View.INVISIBLE);
			rela_hassearch.setVisibility(View.VISIBLE);
			String string = edit_keywords_searchact.getText().toString();
			if (string.isEmpty()) {
				edit_keywords_searchact.setError("关键词不能为空");
				return;
			}
			search(string);
			break;
		case R.id.back:
			finish();
			break;
		default:
			break;
		}
	}

	private void search(String string) {
		String regEx = "[^a-zA-Z0-9\u4e00-\u9fa5]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(string);
		String trim = m.replaceAll("").trim(); // 过滤特殊符号,保留中文，数字，字母

		String encode = null;
		try {
			encode = URLEncoder.encode(trim, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		String url = Content.URL.SEARCH + encode;

		HTTPUtils.get(SearchActivity.this, url, new VolleyListener() {

			@Override
			public void onResponse(String arg0) {
				mText_nothing.setVisibility(View.GONE);
				Spanned fromHtml = Html.fromHtml(arg0);
				SearchList parseJSON = GsonUtils.parseJSON(fromHtml + "", SearchList.class);
				if (parseJSON.getTag() == 1) {
					List<Datum4> data = parseJSON.getData();
					list.clear();
					list.addAll(data);
					gvAdapter.notifyDataSetChanged();
					if(list.size() == 0) {
						mText_nothing.setVisibility(View.VISIBLE);
					}
					initSearchLog();
				} else {
					showShortToast(parseJSON.getMessage());
				}
			}

			@Override
			public void onErrorResponse(VolleyError arg0) {

			}
		});

		// 关键词存入本地数据库
		List<MySearch> dataList = new Select().from(MySearch.class).queryList();
		for (int i = 0; i < dataList.size(); i++) {
			String keyword = dataList.get(i).keywords;
			if (string.equals(keyword)) {
				return;
			}
		}


		MySearch mySearch = new MySearch();
		mySearch.keywords = string;
		mySearch.save();
	}

	@Override
	protected void initViews() {
		lv = (ListView) findViewById(R.id.lv_popsearch);
		text_leftHenxian_popsearch = (TextView) findViewById(R.id.text_leftHenxian_popsearch);
		text_rightHenxian_popsearch = (TextView) findViewById(R.id.text_rightHenxian_popsearch);
		rela_hot_popsearch = (RelativeLayout) findViewById(R.id.rela_hot_popsearch);
		rela_new_popsearch = (RelativeLayout) findViewById(R.id.rela_new_popsearch);
		rela_nosearch = (RelativeLayout) findViewById(R.id.rela_nosearch);
		rela_hassearch = (RelativeLayout) findViewById(R.id.rela_hassearch);
		mText_nothing = (TextView) findViewById(R.id.text_nothing);
		edit_keywords_searchact = (EditText) findViewById(R.id.edit_keywords_searchact);
		gv_search = (GridView) findViewById(R.id.gv_searchact);
		gvAdapter = new GvAdapter();
		gv_search.setAdapter(gvAdapter);

		gv = (GridView) findViewById(R.id.gv_popsearch);
		gvPopAdapter = new GvPopAdapter();
		gv.setAdapter(gvPopAdapter);
		gv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				String keywords = hotKeywordList.get(arg2).getKeywords();
				rela_nosearch.setVisibility(View.INVISIBLE);
				rela_hassearch.setVisibility(View.VISIBLE);
				edit_keywords_searchact.setText(keywords);
				search(keywords);
			}
		});

		lvPopAdapter = new LvPopAdapter();
		lv.setAdapter(lvPopAdapter);

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				rela_nosearch.setVisibility(View.INVISIBLE);
				rela_hassearch.setVisibility(View.VISIBLE);
				String keywords = SearchLogList.get(arg2).keywords;
				search(keywords);
				edit_keywords_searchact.setText(keywords);
			}
		});

		btn_popsearch = (Button) findViewById(R.id.btn_popsearch);

		edit_keywords_searchact.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if ("".equals((s + ""))) { // 输入栏为空
					mText_nothing.setVisibility(View.GONE);
					rela_nosearch.setVisibility(View.VISIBLE);
					rela_hassearch.setVisibility(View.INVISIBLE);
					list.clear();
					gvAdapter.notifyDataSetChanged();
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
	}

	@Override
	protected void initEvents() {
		findViewById(R.id.check_new).setOnClickListener(this);
		btn_popsearch.setOnClickListener(this);
		findViewById(R.id.back).setOnClickListener(this);
		findViewById(R.id.check_hot).setOnClickListener(this);
		findViewById(R.id.ima_search_seatchact).setOnClickListener(this);
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub

	}

}
