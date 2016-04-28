package com.example.onebuy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.model.Account;
import com.example.model.AccountDetail;
import com.example.newonebuy.R;
import com.example.util.Content;
import com.xinbo.utils.GsonUtils;
import com.xinbo.utils.HTTPUtils;
import com.xinbo.utils.VolleyListener;

import java.util.ArrayList;
import java.util.List;

public class AccountDetailActivity extends ActionBarActivity implements OnClickListener {

	private RadioButton check_charge;
	private RadioButton check_pay;
	private ListView lv;
	private ListView lv_pay;
	private LvAdapter lvAdapter;
	private TextView text_item_accountact;
	private RelativeLayout rela_charge_accountact;
	private RelativeLayout rela_pay_accountact;
	private LvPayAdapter lvPayAdapter;
	String token;
	String uid;
	SharedPreferences sp;
	ArrayList<Account> list = new ArrayList<Account>();
	ArrayList<Account> inList = new ArrayList<Account>();
	ArrayList<Account> payList = new ArrayList<Account>();
	TextView text_num_lvin;
	int inMoney;
	int outMoney;
	int mode = 0;
	TextView text;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_detail);
		sp = getSharedPreferences("token", 0);
		token = sp.getString("token", "");
		uid = sp.getString("uid", "");

		initUI();
		initData();
	}

	private void initData() {
		String url = Content.URL.ACCOUNTDETAIL + token + "&uid=" + uid;
		HTTPUtils.get(AccountDetailActivity.this, url, new VolleyListener() {

			@Override
			public void onResponse(String arg0) {
				AccountDetail parseJSON = GsonUtils.parseJSON(arg0, AccountDetail.class);
				List<Account> account = parseJSON.getAccount();
				inMoney = parseJSON.getAccountIn();
				outMoney = parseJSON.getAccountOut();
				if (mode == 0) {
					text_num_lvin.setText("￥" + inMoney);
				} else {
					text_num_lvin.setText("￥" + outMoney);
				}
				chooseList(account);
			}

			@Override
			public void onErrorResponse(VolleyError arg0) {

			}
		});
	}

	public void chooseList(List<Account> account) {
		for (int i = 0; i < account.size(); i++) {
			String type = account.get(i).getType();
			if (type.equals("-1")) {
				payList.add(account.get(i));
			} else {
				inList.add(account.get(i));
			}
		}
		lvAdapter.notifyDataSetChanged();
		lvPayAdapter.notifyDataSetChanged();
	}

	private void initUI() {

		text_item_accountact = (TextView) findViewById(R.id.text_item_accountact);
		rela_charge_accountact = (RelativeLayout) findViewById(R.id.rela_charge_accountact);
		rela_pay_accountact = (RelativeLayout) findViewById(R.id.rela_pay_accountact);

		check_charge = (RadioButton) findViewById(R.id.check_charge);
		check_pay = (RadioButton) findViewById(R.id.check_pay);
		check_charge.setOnClickListener(this);
		check_pay.setOnClickListener(this);
		findViewById(R.id.back).setOnClickListener(this);
		lv = (ListView) findViewById(R.id.lv_accountact);
		lvAdapter = new LvAdapter();
		lv.setAdapter(lvAdapter);

		lv_pay = (ListView) findViewById(R.id.lv_pay_accountact);
		lvPayAdapter = new LvPayAdapter();
		lv_pay.setAdapter(lvPayAdapter);

		Intent intent = getIntent();
		String stringExtra = intent.getStringExtra("fromcharge");
		text_num_lvin = (TextView) findViewById(R.id.text_allmoney_accountdetailact);
		if ("charge".equals(stringExtra)) {
			check_charge.setChecked(true);
			text_item_accountact.setText("总充值金额");
			rela_charge_accountact.setVisibility(View.VISIBLE);
			rela_pay_accountact.setVisibility(View.INVISIBLE);
			mode = 0;
		} else {
			check_pay.setChecked(true);
			text_item_accountact.setText("总消费金额");
			rela_charge_accountact.setVisibility(View.INVISIBLE);
			rela_pay_accountact.setVisibility(View.VISIBLE);
			mode = 1;
		}

	}

	class LvAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return inList.size();
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
			View view = getLayoutInflater().inflate(R.layout.item_lv_charge_accountdetail, null);
			TextView text_time_lvin = (TextView) view.findViewById(R.id.text_time_lvin);
			TextView text_num_lvin = (TextView) view.findViewById(R.id.text_num_lvin);

			Account account = inList.get(position);
			text_time_lvin.setText(account.getTime());
			text_num_lvin.setText(account.getMoney());
			return view;
		}

	}

	class LvPayAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return payList.size();
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
			View view = getLayoutInflater().inflate(R.layout.item_lv_pay_accountdetail, null);
			TextView text_time_pay = (TextView) view.findViewById(R.id.text_time_pay);
			TextView text_paymoney = (TextView) view.findViewById(R.id.text_paymoney);

			Account account = payList.get(position);
			text_time_pay.setText(account.getTime());
			text_paymoney.setText(account.getMoney());
			return view;
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.check_charge: // 点击充值明细
			text_item_accountact.setText("总充值金额");
			rela_charge_accountact.setVisibility(View.VISIBLE);
			rela_pay_accountact.setVisibility(View.INVISIBLE);
			text_num_lvin.setText("￥" + inMoney);
			break;
		case R.id.check_pay: // 点击消费明细
			text_item_accountact.setText("总消费金额");
			rela_charge_accountact.setVisibility(View.INVISIBLE);
			rela_pay_accountact.setVisibility(View.VISIBLE);
			text_num_lvin.setText("￥" + outMoney);
			break;
		case R.id.back:
			finish();
			break;

		default:
			break;
		}
	}

}
