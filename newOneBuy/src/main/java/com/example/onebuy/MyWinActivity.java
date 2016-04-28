package com.example.onebuy;

import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.VolleyError;
import com.example.newonebuy.R;
import com.example.model.MyWin;
import com.example.model.Record;
import com.example.onebuy.AllOrderActivity.showCodeBroad;
import com.example.util.Content;
import com.example.util.GlobalVariable;
import com.xinbo.utils.GsonUtils;
import com.xinbo.utils.HTTPUtils;
import com.xinbo.utils.UILUtils;
import com.xinbo.utils.VolleyListener;

import android.R.string;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 中奖记录
 */
public class MyWinActivity extends ActionBarActivity implements OnClickListener {

	private RelativeLayout rela_noinfo_mywinact;
	private ListView lv;
	private SharedPreferences sp;
	String token;
	String uid;
	ArrayList<Record> list = new ArrayList<Record>();
	private LvAdapter lvAdapter;
	private RelativeLayout rela_loading;
	ArrayList<String> string = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_win);
		sp = getSharedPreferences("token", 0);
		token = sp.getString("token", "");
		uid = sp.getString("uid", "");

		initUI();
		initData();
	}

	private void initData() {
		String url = Content.URL.MYWIN + token + "&uid=" + uid;
		HTTPUtils.get(MyWinActivity.this, url, new VolleyListener() {

			@Override
			public void onResponse(String arg0) {
				MyWin parseJSON = GsonUtils.parseJSON(arg0, MyWin.class);
				List<Record> record = parseJSON.getRecord();
				list.clear();
				list.addAll(record);
				for (int i = 0; i < record.size(); i++) {
					string.add(record.get(i).getHuode());
				}
				lvAdapter.notifyDataSetChanged();
				if (record.size() == 0) {
					rela_noinfo_mywinact.setVisibility(View.VISIBLE);
				} else {
					rela_noinfo_mywinact.setVisibility(View.GONE);
				}
				rela_loading.setVisibility(View.GONE);
			}

			@Override
			public void onErrorResponse(VolleyError arg0) {
			}
		});

	}

	private void initUI() {

		findViewById(R.id.back).setOnClickListener(this);
		rela_noinfo_mywinact = (RelativeLayout) findViewById(R.id.rela_noinfo_mywinact);

		lv = (ListView) findViewById(R.id.lv_mywin);
		lvAdapter = new LvAdapter();
		lv.setAdapter(lvAdapter);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Intent intent = new Intent(MyWinActivity.this, MyWinDetailActivity.class);
				intent.putExtra("goodid", list.get(arg2).getShopid());
				startActivity(intent);
			}
		});

		rela_loading = (RelativeLayout) findViewById(R.id.rela_loading);
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
			View view = null;
			TextView text_name_mywinact;
			TextView text_luckynum_mywinact;
			TextView text_time_mywinact;
			ImageView ima_winner_mywinact;
			if (view == null) {
				view = getLayoutInflater().inflate(R.layout.item_lv_mywin, null);
				text_name_mywinact = (TextView) view.findViewById(R.id.text_name_mywinact);
				text_luckynum_mywinact = (TextView) view.findViewById(R.id.text_luckynum_mywinact);
				text_time_mywinact = (TextView) view.findViewById(R.id.text_time_mywinact);
				ima_winner_mywinact = (ImageView) view.findViewById(R.id.ima_winner_mywinact);
				view.setTag(
						new MyTat(text_name_mywinact, text_luckynum_mywinact, text_time_mywinact, ima_winner_mywinact));
			} else {
				view = convertView;
				MyTat tag2 = (MyTat) view.getTag();
				text_name_mywinact = tag2.text_name_mywinact;
				text_luckynum_mywinact = tag2.text_luckynum_mywinact;
				text_time_mywinact = tag2.text_time_mywinact;
				ima_winner_mywinact = tag2.ima_winner_mywinact;
			}

			Record record = list.get(position);
			text_name_mywinact.setText("（第" + record.getShopqishu() + "期）" + record.getShopname());
			text_time_mywinact.setText(record.getTime());
			text_luckynum_mywinact.setText(record.getHuode());

			GlobalVariable.displayImageNoAnim(Content.URL.IMA_HOST + record.getThumb(), ima_winner_mywinact);
			return view;
		}
	}

	class MyTat {
		TextView text_name_mywinact;
		TextView text_luckynum_mywinact;
		TextView text_time_mywinact;
		ImageView ima_winner_mywinact;

		public MyTat(TextView text_name_mywinact, TextView text_luckynum_mywinact, TextView text_time_mywinact,
				ImageView ima_winner_mywinact) {
			super();
			this.text_name_mywinact = text_name_mywinact;
			this.text_luckynum_mywinact = text_luckynum_mywinact;
			this.text_time_mywinact = text_time_mywinact;
			this.ima_winner_mywinact = ima_winner_mywinact;
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
