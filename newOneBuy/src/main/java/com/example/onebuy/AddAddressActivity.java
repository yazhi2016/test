package com.example.onebuy;

import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.VolleyError;
import com.example.newonebuy.R;
import com.example.model.Address_;
import com.example.model.SignOut;
import com.example.util.Content;
import com.example.util.GlobalVariable;
import com.xinbo.utils.GsonUtils;
import com.xinbo.utils.HTTPUtils;
import com.xinbo.utils.RegexValidateUtil;
import com.xinbo.utils.VolleyListener;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddAddressActivity extends ActionBarActivity implements OnClickListener {

	private CheckBox checkBox1;
	private EditText edit_postCode;
	private EditText edit_detailAddress;
	private EditText edit_street;
	private EditText edit_location;
	private EditText edit_friend_phone;
	private EditText edit_phone;
	private EditText edit_name;
	private EditText edit_streetlAddress;
	String token;
	String uid;
	SharedPreferences sp;
	private Address_ address;
	int mode = 0; // 0添加地址，1修改地址
	private TextView text_editaddress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_address);
		findViewById(R.id.back).setOnClickListener(this);
		findViewById(R.id.checkbox_shopfra_edit).setOnClickListener(this);

		sp = getSharedPreferences("token", 0);
		token = sp.getString("token", "");
		uid = sp.getString("uid", "");

		text_editaddress = (TextView) findViewById(R.id.text_editaddress);
		edit_name = (EditText) findViewById(R.id.edit_name);
		edit_phone = (EditText) findViewById(R.id.edit_phone);
		edit_friend_phone = (EditText) findViewById(R.id.edit_friend_phone);
		edit_location = (EditText) findViewById(R.id.edit_location);
		edit_street = (EditText) findViewById(R.id.edit_street);
		edit_detailAddress = (EditText) findViewById(R.id.edit_detailAddress);
		edit_postCode = (EditText) findViewById(R.id.edit_postCode);
		edit_streetlAddress = (EditText) findViewById(R.id.edit_streetlAddress);
		checkBox1 = (CheckBox) findViewById(R.id.checkBox1);

		Intent intent = getIntent();
		if (intent.getSerializableExtra("address") != null) {
			mode = 1;
			address = (Address_) intent.getSerializableExtra("address");
			edit_name.setText(address.getShouhuoren());
			edit_phone.setText(address.getMobile());
			edit_location.setText(address.getSheng());
			edit_street.setText(address.getShi());
			edit_detailAddress.setText(address.getXian());
			edit_postCode.setText(address.getYoubian());
			Log.e("==", address.getTell());
			edit_friend_phone.setText(address.getTell());
			edit_streetlAddress.setText(address.getJiedao());
			text_editaddress.setText("编辑收货地址");
			checkBox1.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back: // 点击返回
			finish();
			break;
		case R.id.checkbox_shopfra_edit: // 保存地址

			if (edit_name.getText().toString().isEmpty()) {
				edit_name.setError("姓名不能为空");
				return;
			}
			if (edit_location.getText().toString().isEmpty()) {
				edit_location.setError("所在省不能为空");
				return;
			}
			if (edit_street.getText().toString().isEmpty()) {
				edit_street.setError("所在市不能为空");
				return;
			}
			if (edit_detailAddress.getText().toString().isEmpty()) {
				edit_detailAddress.setError("所在县不能为空");
				return;
			}
			if (edit_streetlAddress.getText().toString().isEmpty()) {
				edit_streetlAddress.setError("所在街道不能为空");
				return;
			}
			if (edit_phone.getText().toString().isEmpty()) {
				edit_phone.setError("手机不能为空");
				return;
			}
			if (edit_friend_phone.getText().toString().isEmpty()) {
				edit_friend_phone.setError("常用联系方式不能为空");
				return;
			}
			if (!RegexValidateUtil.checkMobileNumber(edit_phone.getText().toString())) {
				edit_phone.setError("请填写正确的手机");
				return;
			}

			if (mode == 1) { // 修改地址
			
				String url = Content.URL.CHANGEADDRESS;
				Map<String, String> params = new HashMap<String, String>();
				params.put("token", token);
				params.put("uid", uid);
				params.put("address_id", address.getId());
				params.put("shouhuoren", edit_name.getText().toString());
				params.put("sheng", edit_location.getText().toString());
				params.put("shi", edit_street.getText().toString());
				params.put("xian", edit_detailAddress.getText().toString());
				params.put("jiedao", edit_streetlAddress.getText().toString());
				params.put("youbian", edit_postCode.getText().toString());
//				params.put("tell", "15611111111");
//				params.put("mobile", "15611111112");
				params.put("tell", edit_friend_phone.getText().toString());
				params.put("mobile", edit_phone.getText().toString());
				// if (checkBox1.isChecked()) {
				// params.put("default", "Y");
				// } else {
				// params.put("default", "N");
				// }
				

				HTTPUtils.post(AddAddressActivity.this, url, params, new VolleyListener() {

					@Override
					public void onResponse(String arg0) {
						Log.e("==", "jinru1");
						SignOut parseJSON = GsonUtils.parseJSON(arg0, SignOut.class);
						Integer tag2 = parseJSON.getTag();
						if (tag2 == 1) {
							Toast.makeText(AddAddressActivity.this, "地址修改成功！", Toast.LENGTH_SHORT).show();
							finish();
							if (checkBox1.isChecked()) { // 选为默认地址
								setDefautAddress();
							}
						} else {
							String message = parseJSON.getMessage();
							Toast.makeText(AddAddressActivity.this, "地址修改失败:" + message, Toast.LENGTH_SHORT).show();
						}
					}

					@Override
					public void onErrorResponse(VolleyError arg0) {

					}
				});

				break;
			} else { // 添加地址
				String url = Content.URL.ADDADDRESS;
				Map<String, String> params = new HashMap<String, String>();
				params.put("token", token);
				params.put("uid", uid);
				params.put("shouhuoren", edit_name.getText().toString());
				params.put("sheng", edit_location.getText().toString());
				params.put("shi", edit_street.getText().toString());
				params.put("xian", edit_detailAddress.getText().toString());
				params.put("jiedao", edit_streetlAddress.getText().toString());
				params.put("youbian", edit_postCode.getText().toString());
				params.put("tell", edit_friend_phone.getText().toString());
				params.put("mobile", edit_phone.getText().toString());
				if (checkBox1.isChecked()) {
					params.put("default", "Y");
				} else {
					params.put("default", "N");
				}

				HTTPUtils.post(AddAddressActivity.this, url, params, new VolleyListener() {

					@Override
					public void onResponse(String arg0) {
						SignOut parseJSON = GsonUtils.parseJSON(arg0, SignOut.class);
						Integer tag2 = parseJSON.getTag();
						if (tag2 == 1) {
							Toast.makeText(AddAddressActivity.this, "地址添加成功！", Toast.LENGTH_SHORT).show();
							finish();
						} else {
							Toast.makeText(AddAddressActivity.this, parseJSON.getMessage(), Toast.LENGTH_SHORT).show();
						}
					}

					@Override
					public void onErrorResponse(VolleyError arg0) {

					}
				});
			}

		default:
			break;
		}
	}

	private void setDefautAddress() {
		String defaultUrl = Content.URL.DEFAULTADDRESS + token + "&uid=" + uid + "&address_id=" + address.getId();
		Log.e("==", address.getId());
		HTTPUtils.get(AddAddressActivity.this, defaultUrl, new VolleyListener() {

			@Override
			public void onResponse(String arg0) {
				Log.e("==", arg0);
				Toast.makeText(AddAddressActivity.this, "地址保存成功！", Toast.LENGTH_SHORT).show();
				finish();
			}

			@Override
			public void onErrorResponse(VolleyError arg0) {

			}
		});
	}

}
