package com.example.onebuy;

import android.support.v7.app.ActionBarActivity;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.VolleyError;
import com.example.newonebuy.R;
import com.example.model.ImaDetail;
import com.example.util.Content;
import com.example.util.GlobalVariable;
import com.example.util.SquareImageView;
import com.umeng.socialize.utils.Log;
import com.xinbo.utils.GsonUtils;
import com.xinbo.utils.HTTPUtils;
import com.xinbo.utils.UILUtils;
import com.xinbo.utils.VolleyListener;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class ImaTextActivity extends ActionBarActivity implements OnClickListener {
	String goodId;
	ArrayList<String> imaList = new ArrayList<String>();
	private ListView lv;
	private LvAdapter lvAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ima_text);
		findViewById(R.id.back).setOnClickListener(this);

		Intent intent = getIntent();
		goodId = intent.getStringExtra("id");

		lv = (ListView) findViewById(R.id.lv_imadetailact);
		lvAdapter = new LvAdapter();
		lv.setAdapter(lvAdapter);

		initData();
	}

	class LvAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return imaList.size();
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
			View view = getLayoutInflater().inflate(R.layout.item_lv_imadetailact, null);
			ImageView ima = (ImageView) view.findViewById(R.id.imageView11);
			GlobalVariable.displayImageNoAnim(Content.URL.IMA_HOST + imaList.get(position), ima);
			return view;
		}

	}

	private void initData() {
		String url = Content.URL.IMADETAIL + goodId;
		HTTPUtils.get(this, url, new VolleyListener() {

			@Override
			public void onResponse(String arg0) {
				ImaDetail parseJSON = GsonUtils.parseJSON(arg0, ImaDetail.class);
				List<String> picarr = parseJSON.getGoods().getPicarr();
				imaList.addAll(picarr);
				if (picarr.size() == 0) {
					Toast makeText = Toast.makeText(ImaTextActivity.this, "没有图片", Toast.LENGTH_SHORT);
					makeText.setGravity(Gravity.CENTER, 0, 0);
					makeText.show();
					return;
				}
				lvAdapter.notifyDataSetChanged();
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

		default:
			break;
		}
	}

}
