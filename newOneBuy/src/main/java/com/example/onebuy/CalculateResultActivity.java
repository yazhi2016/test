package com.example.onebuy;

import android.support.v7.app.ActionBarActivity;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.VolleyError;
import com.example.newonebuy.R;
import com.example.usermodel.Calculate;
import com.example.usermodel.Datum;
import com.example.util.Content;
import com.xinbo.utils.GsonUtils;
import com.xinbo.utils.HTTPUtils;
import com.xinbo.utils.VolleyListener;
import com.xinbo.widget.ListView4ScrollView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ScrollView;
import android.widget.TextView;

public class CalculateResultActivity extends ActionBarActivity implements OnClickListener {

	private ListView4ScrollView lv;
	private TextView text1;
	private TextView text2;
	private TextView text3;
	String goodid;
	ArrayList<Datum> list = new ArrayList<Datum>();
	private Adapter adapter;
	private ScrollView scrollView1;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calculate_result);
		Intent intent = getIntent();
		goodid = intent.getStringExtra("id");

		initUI();
		initData();
	}

	private void initData() {
		String url = Content.URL.CALCULATE + goodid;
		HTTPUtils.get(CalculateResultActivity.this, url, new VolleyListener() {

			@Override
			public void onResponse(String arg0) {
				Calculate parseJSON = GsonUtils.parseJSON(arg0, Calculate.class);
				Integer sumNum = parseJSON.getSumNum();
				long sumTime = parseJSON.getSumTime();
				Integer result = parseJSON.getResult();
				Integer code = parseJSON.getCode();
				String canyurenshu = parseJSON.getCanyurenshu();

				text1.setText("1、求和" + sumTime + "（下面" + sumNum + "条妙购记入时间取值相加之和）");
				text2.setText("2、取余：" + sumTime + "（下面" + sumNum + "条妙购记入时间取值相加之和）%" + canyurenshu + "（本商品总需参与人数）="
						+ result + "（余数）");
				text3.setText("3、结果：" + result + "（余数）+10000001=" + code);

				List<Datum> data = parseJSON.getData();
				list.addAll(data);
				adapter.notifyDataSetChanged();
				scrollView1.smoothScrollTo(0, 0);
			}

			@Override
			public void onErrorResponse(VolleyError arg0) {

			}
		});

	}

	private void initUI() {
		lv = (ListView4ScrollView) findViewById(R.id.lv_calculate);
		text1 = (TextView) findViewById(R.id.textView3);
		text2 = (TextView) findViewById(R.id.textView4);
		text3 = (TextView) findViewById(R.id.textView5);
		scrollView1 = (ScrollView) findViewById(R.id.scrollView1);
		adapter = new Adapter();
		lv.setAdapter(adapter);
		findViewById(R.id.back).setOnClickListener(this);
	}

	class Adapter extends BaseAdapter {

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
			TextView text_time;
			TextView text_data;
			TextView text_name;
			if (convertView == null) {
				view = getLayoutInflater().inflate(R.layout.item_calculate, null);
				text_time = (TextView) view.findViewById(R.id.text_time);
				text_data = (TextView) view.findViewById(R.id.text_data);
				text_name = (TextView) view.findViewById(R.id.text_name);
				view.setTag(new Tag(text_time, text_data, text_name));
			} else {
				view = convertView;
				Tag tag2 = (Tag) view.getTag();
				text_time = tag2.text_time;
				text_data = tag2.text_data;
				text_name = tag2.text_name;
			}
			Datum datum = list.get(position);
			String replace = datum.getTime().replace(" ", "\n");
			text_time.setText(replace);
			text_data.setText(datum.getData() + "");
			text_name.setText(datum.getUsername());
			return view;
		}

	}

	class Tag {
		TextView text_time;
		TextView text_data;
		TextView text_name;

		public Tag(TextView text_time, TextView text_data, TextView text_name) {
			super();
			this.text_time = text_time;
			this.text_data = text_data;
			this.text_name = text_name;
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
