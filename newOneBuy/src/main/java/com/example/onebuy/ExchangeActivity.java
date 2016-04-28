package com.example.onebuy;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.model.All;
import com.example.model.Data2;
import com.example.model.Exchanged;
import com.example.newonebuy.R;
import com.example.util.BaseActivity;
import com.example.util.Content;
import com.example.util.GlobalVariable;
import com.xinbo.utils.GsonUtils;
import com.xinbo.utils.HTTPUtils;
import com.xinbo.utils.VolleyListener;

import java.util.ArrayList;

/**
 * 抢购记录
 */
public class ExchangeActivity extends BaseActivity {

	ArrayList<All> listShow = new ArrayList<All>();
	ArrayList<All> listAll = new ArrayList<All>();
	ArrayList<All> listPre = new ArrayList<All>();
	ArrayList<All> listComple = new ArrayList<All>();
	int Mode = 0; // 0全部，1,待发货，2已发货
	private RelativeLayout rela_noinfo_mywinact;
	private ListView lv;
	String token;
	String uid;
	private LvAdapter lvAdapter;
	private RelativeLayout rela_loading;
	private String fromWhere; //标识是从兑换进来的还是抢购进来的
	private TextView textView1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exchange);
		
		fromWhere = getIntent().getStringExtra("fromWhere");
		
		initViews();
		initEvents();
		init();
		iniData();
	}

	private void iniData() {
		GlobalVariable instance = GlobalVariable.getInstance();
		String url = null;
		if(fromWhere.equals("zero")) { //抢购记录进来的
			url = Content.URL.MYZERO + instance.getSpToken() + "&uid=" + instance.getSpUid();
		} else { //兑换记录
			url = Content.URL.EXCHANGED + instance.getSpToken() + "&uid=" + instance.getSpUid();
		}
		HTTPUtils.get(ExchangeActivity.this, url, new VolleyListener() {

			@Override
			public void onResponse(String arg0) {
				Exchanged parseJSON = GsonUtils.parseJSON(arg0, Exchanged.class);
				if (parseJSON.getTag() == 1) {
					Data2 data = parseJSON.getData();
					listAll.addAll(data.getAll());
					listPre.addAll(data.getPre());
					listComple.addAll(data.getComplete());
					listShow.addAll(listAll);
					lvAdapter.notifyDataSetChanged();
					if (listShow.size() == 0) {
						rela_noinfo_mywinact.setVisibility(View.VISIBLE);
					} else {
						rela_noinfo_mywinact.setVisibility(View.GONE);
					}
					rela_loading.setVisibility(View.GONE);
				} else {
					showShortToast(parseJSON.getMessage());
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
		case R.id.radio0:
			Mode = 0;
			listShow.clear();
			listShow.addAll(listAll);
			lvAdapter.notifyDataSetChanged();
			if (listShow.size() == 0) {
				rela_noinfo_mywinact.setVisibility(View.VISIBLE);
			} else {
				rela_noinfo_mywinact.setVisibility(View.GONE);
			}
			break;
		case R.id.radio1:
			listShow.clear();
			listShow.addAll(listPre);
			lvAdapter.notifyDataSetChanged();
			if (listShow.size() == 0) {
				rela_noinfo_mywinact.setVisibility(View.VISIBLE);
			} else {
				rela_noinfo_mywinact.setVisibility(View.GONE);
			}
			break;
		case R.id.radio2:
			listShow.clear();
			listShow.addAll(listComple);
			lvAdapter.notifyDataSetChanged();
			if (listShow.size() == 0) {
				rela_noinfo_mywinact.setVisibility(View.VISIBLE);
			} else {
				rela_noinfo_mywinact.setVisibility(View.GONE);
			}
			break;
		default:
			break;
		}
	}

	@Override
	protected void initViews() {
		rela_noinfo_mywinact = (RelativeLayout) findViewById(R.id.rela_noinfo_mywinact);
		lv = (ListView) findViewById(R.id.lv_mywin);
		lvAdapter = new LvAdapter();
		lv.setAdapter(lvAdapter);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

			}
		});
		rela_loading = (RelativeLayout) findViewById(R.id.rela_loading);
		textView1 = (TextView) findViewById(R.id.textView1);
	}

	@Override
	protected void initEvents() {
		findViewById(R.id.radio0).setOnClickListener(this);
		findViewById(R.id.radio1).setOnClickListener(this);
		findViewById(R.id.radio2).setOnClickListener(this);
		findViewById(R.id.back).setOnClickListener(this);
	}

	@Override
	protected void init() {
		if(fromWhere.equals("zero")) { //抢购记录进来的
			textView1.setText("抢购记录");
		} else {
			textView1.setText("兑换记录");
		}
	}

	class LvAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return listShow.size();
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
				view = getLayoutInflater().inflate(R.layout.item_lv_exchanged, null);
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

			All record = listShow.get(position);
			text_name_mywinact.setText(record.getTitle());
			text_luckynum_mywinact.setText("" + record.getCode());
			text_time_mywinact.setText(record.getStatus());

			GlobalVariable.displayImageNoAnim(Content.URL.IMA_HOST + record.getGoodsImg(), ima_winner_mywinact);
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

}
