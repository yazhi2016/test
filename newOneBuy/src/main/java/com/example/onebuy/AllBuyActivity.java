package com.example.onebuy;

import android.support.v7.app.ActionBarActivity;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.VolleyError;
import com.example.newonebuy.R;
import com.example.model.AllBuy;
import com.example.model.Cord;
import com.example.util.Content;
import com.example.util.GlobalVariable;
import com.xinbo.utils.GsonUtils;
import com.xinbo.utils.HTTPUtils;
import com.xinbo.utils.UILUtils;
import com.xinbo.utils.VolleyListener;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AllBuyActivity extends ActionBarActivity implements OnClickListener {

	private ListView lv;
	private LvAdapter lvAdapter;
	private ImageView fromup_allbuyact;
	String goodsId;
	ArrayList<Cord> list = new ArrayList<Cord>();
	private RelativeLayout rela_noinfo_allbuyact;
	private RelativeLayout rela_loading;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all_buy);

		Intent intent = getIntent();
		goodsId = intent.getStringExtra("id");

		findViewById(R.id.back).setOnClickListener(this);
		fromup_allbuyact = (ImageView) findViewById(R.id.fromup_allbuyact);
		fromup_allbuyact.setOnClickListener(this);
		lv = (ListView) findViewById(R.id.lv_allbuyact);
		lvAdapter = new LvAdapter();
		lv.setAdapter(lvAdapter);
		rela_noinfo_allbuyact = (RelativeLayout) findViewById(R.id.rela_noinfo_allbuyact);
		rela_loading = (RelativeLayout) findViewById(R.id.rela_loading);
		initData();
	}

	private void initData() {
		String url = Content.URL.ALLBUY + goodsId;
		// String url = Content.URL.ALLBUY + "1440";
		HTTPUtils.get(AllBuyActivity.this, url, new VolleyListener() {

			@Override
			public void onResponse(String arg0) {
				AllBuy parseJSON = GsonUtils.parseJSON(arg0, AllBuy.class);
				List<Cord> cords = parseJSON.getCords();
				if (cords.size() != 0) { //有数据
					rela_noinfo_allbuyact.setVisibility(View.GONE); 
				} else {
					rela_noinfo_allbuyact.setVisibility(View.VISIBLE); 
				}
				list.addAll(cords);
				lvAdapter.notifyDataSetChanged();
				rela_loading.setVisibility(View.GONE); 
			}

			@Override
			public void onErrorResponse(VolleyError arg0) {

			}
		});
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
			View view;
			ImageView ima_buyer_allbuyact;
			TextView text_buyname_allbuyact;
			TextView text_buyerfrom_allbuyact;
			TextView text_buytime_allbuydetail;
			TextView text_time_allbuyact;
			if (convertView == null) {
				view = getLayoutInflater().inflate(R.layout.item_lv_allbuyact, null);
				ima_buyer_allbuyact = (ImageView) view.findViewById(R.id.ima_buyer_allbuyact);
				text_buyname_allbuyact = (TextView) view.findViewById(R.id.text_buyname_allbuyact);
				text_buyerfrom_allbuyact = (TextView) view.findViewById(R.id.text_buyerfrom_allbuyact);
				text_buytime_allbuydetail = (TextView) view.findViewById(R.id.text_buytime_allbuydetail);
				text_time_allbuyact = (TextView) view.findViewById(R.id.text_time_allbuyact);
				view.setTag(new MyTag(ima_buyer_allbuyact, text_buyname_allbuyact, text_buyerfrom_allbuyact,
						text_buytime_allbuydetail, text_time_allbuyact));
			} else {
				view = convertView;
				MyTag tag2 = (MyTag) view.getTag();
				ima_buyer_allbuyact = tag2.ima_buyer_allbuyact;
				text_buyname_allbuyact = tag2.text_buyname_allbuyact;
				text_buyerfrom_allbuyact = tag2.text_buyerfrom_allbuyact;
				text_buytime_allbuydetail = tag2.text_buytime_allbuydetail;
				text_time_allbuyact = tag2.text_time_allbuyact;
			}
			Cord cord = list.get(position);
			GlobalVariable.displayImageNoAnim(Content.URL.IMA_HOST + cord.getUphoto(), ima_buyer_allbuyact);
			text_buyname_allbuyact.setText(cord.getUsername());
			String ip = cord.getIp();
			int indexOf = ip.indexOf(",");
			if (indexOf != -1) {
				String substring = ip.substring(0, indexOf);
				text_buyerfrom_allbuyact.setText("(" + substring + ")");
			}
			text_buytime_allbuydetail.setText(cord.getGonumber());
			text_time_allbuyact.setText(cord.getTime());
			return view;
		}

	}

	class MyTag {
		ImageView ima_buyer_allbuyact;
		TextView text_buyname_allbuyact;
		TextView text_buyerfrom_allbuyact;
		TextView text_buytime_allbuydetail;
		TextView text_time_allbuyact;

		public MyTag(ImageView ima_buyer_allbuyact, TextView text_buyname_allbuyact, TextView text_buyerfrom_allbuyact,
				TextView text_buytime_allbuydetail, TextView text_time_allbuyact) {
			super();
			this.ima_buyer_allbuyact = ima_buyer_allbuyact;
			this.text_buyname_allbuyact = text_buyname_allbuyact;
			this.text_buyerfrom_allbuyact = text_buyerfrom_allbuyact;
			this.text_buytime_allbuydetail = text_buytime_allbuydetail;
			this.text_time_allbuyact = text_time_allbuyact;
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.fromup_allbuyact: // 点击顶栏按钮
			fromup_allbuyact.setImageDrawable(getResources().getDrawable(R.drawable.fromdown));
			break;
		default:
			break;
		}
	}

}
