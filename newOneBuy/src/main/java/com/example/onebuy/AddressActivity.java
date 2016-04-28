package com.example.onebuy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;
import com.example.model.Address;
import com.example.model.Address_;
import com.example.newonebuy.R;
import com.example.util.Content;
import com.xinbo.utils.GsonUtils;
import com.xinbo.utils.HTTPUtils;
import com.xinbo.utils.VolleyListener;

import java.util.ArrayList;
import java.util.List;

public class AddressActivity extends ActionBarActivity implements OnClickListener {

	private SwipeMenuListView lv;
	private MyAdapter myAdapter;
	ArrayList<Address_> addressList = new ArrayList<Address_>();
	String token;
	String uid;
	private SharedPreferences sp;
	private TextView mText_nothing;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_address);

		sp = getSharedPreferences("token", 0);
		token = sp.getString("token", "");
		uid = sp.getString("uid", "");

		initUI();
	}

	private void initUI() {
		lv = (SwipeMenuListView) findViewById(R.id.adress_lv);
		myAdapter = new MyAdapter();
		lv.setAdapter(myAdapter);

		SwipeMenuCreator creator = new SwipeMenuCreator() {

			@Override
			public void create(SwipeMenu menu) {
				// create "open" item
				SwipeMenuItem openItem = new SwipeMenuItem(AddressActivity.this.getApplicationContext());
				// set item background
				openItem.setBackground(R.drawable.drawable_address_edit);
				// set item width
				openItem.setWidth(dp2px(45));
				// set item title
				openItem.setTitle("编辑");
				// set item title fontsize
				openItem.setTitleSize(16);
				// set item title font color
				openItem.setTitleColor(Color.BLACK);
				// add to menu
				menu.addMenuItem(openItem);

				SwipeMenuItem openItem1 = new SwipeMenuItem(AddressActivity.this.getApplicationContext());
				openItem1.setBackground(R.color.onebuy);
				openItem1.setWidth(dp2px(45));
				openItem1.setTitle("删除");
				openItem1.setTitleSize(16);
				openItem1.setTitleColor(Color.WHITE);
				menu.addMenuItem(openItem1);
			}
		};
		// set creator
		lv.setMenuCreator(creator);

		lv.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public void onMenuItemClick(int position, SwipeMenu menu, int index) {
				// ApplicationInfo item = mAppList.get(position);
				switch (index) {
				case 0:
					// open
					// open(item);
					Intent intent = new Intent(AddressActivity.this, AddAddressActivity.class);
					intent.putExtra("address", addressList.get(position));
					startActivity(intent);
					break;
				case 1:
					// open
					String id = addressList.get(position).getId();
					deleteAddress(id);
					initAddress();
					// open(item);
					break;
				}
			}
		});

		findViewById(R.id.add_address).setOnClickListener(this);
		findViewById(R.id.back).setOnClickListener(this);
		mText_nothing = (TextView) findViewById(R.id.text_nothing);

		initAddress();
	}

	public void deleteAddress(String addressId) {
		String deleteUrl = Content.URL.DELETEADDRESS + token + "&uid=" + uid + "&address_id=" + addressId;
		HTTPUtils.get(AddressActivity.this, deleteUrl, new VolleyListener() {

			@Override
			public void onResponse(String arg0) {
				Toast.makeText(AddressActivity.this, "删除成功！", Toast.LENGTH_SHORT).show();
				initAddress();
			}

			@Override
			public void onErrorResponse(VolleyError arg0) {

			}
		});
	}

	private void initAddress() {
		mText_nothing.setVisibility(View.GONE);
		String url = Content.URL.ADDRESS + token + "&uid=" + uid;
		HTTPUtils.get(AddressActivity.this, url, new VolleyListener() {

			@Override
			public void onResponse(String arg0) {
				Address parseJSON = GsonUtils.parseJSON(arg0, Address.class);
				List<Address_> address = parseJSON.getAddress();
				addressList.clear();
				addressList.addAll(address);
				if(address.size() == 0) {
					mText_nothing.setVisibility(View.VISIBLE);
				}
				myAdapter.notifyDataSetChanged();
			}

			@Override
			public void onErrorResponse(VolleyError arg0) {

			}
		});
	}

	private int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
	}

	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return addressList.size();
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
			TextView text_address_name;
			TextView text_address_phone;
			TextView text_address_location;
			TextView text_alwaysthis;
			Address_ address_ = addressList.get(position);
			if (convertView == null) {
				view = getLayoutInflater().inflate(R.layout.address_item, null);
				text_address_name = (TextView) view.findViewById(R.id.text_address_name);
				text_address_phone = (TextView) view.findViewById(R.id.text_address_phone);
				text_address_location = (TextView) view.findViewById(R.id.text_address_location);
				text_alwaysthis = (TextView) view.findViewById(R.id.text_alwaysthis);
				view.setTag(new MyTag(text_address_name, text_address_phone, text_address_location, text_alwaysthis));
			} else {
				view = convertView;
				MyTag tag = (MyTag) view.getTag();
				text_address_name = tag.text_address_name;
				text_address_phone = tag.text_address_phone;
				text_address_location = tag.text_address_location;
				text_alwaysthis = tag.text_alwaysthis;
				text_alwaysthis.setTag(position);
			}

			text_address_name.setText(address_.getShouhuoren());
			text_address_phone.setText(address_.getMobile());
			text_address_location
					.setText(address_.getSheng() + address_.getXian() + address_.getShi() + address_.getJiedao());
			if (address_.getDefault().equals("Y")) { // 默认地址
				text_alwaysthis.setVisibility(View.VISIBLE);
			} else {
				text_alwaysthis.setVisibility(View.INVISIBLE);
			}

			return view;
		}

	}

	class MyTag {
		TextView text_address_name;
		TextView text_address_phone;
		TextView text_address_location;
		TextView text_alwaysthis;

		public MyTag(TextView text_address_name, TextView text_address_phone, TextView text_address_location,
				TextView text_alwaysthis) {
			super();
			this.text_address_name = text_address_name;
			this.text_address_phone = text_address_phone;
			this.text_address_location = text_address_location;
			this.text_alwaysthis = text_alwaysthis;
		}

	}

	@Override
	protected void onResume() {
		super.onResume();
		initAddress();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.add_address:
			startActivity(new Intent(this, AddAddressActivity.class));
			break;
		case R.id.back:
			finish();
			break;

		default:
			break;
		}
	}

}
