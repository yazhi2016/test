package com.example.onebuy;

import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.newonebuy.R;
import com.example.model.Forgetpwd;
import com.example.model.Register;
import com.example.model.SignOut;
import com.example.util.BaseActivity;
import com.example.util.Content;
import com.example.util.GlobalVariable;
import com.xinbo.utils.GsonUtils;
import com.xinbo.utils.HTTPUtils;
import com.xinbo.utils.VolleyListener;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class ForgetPwdActivity extends BaseActivity {

	private EditText edit_iphone_signupact;
	private Integer smscode;
	private EditText edit_sms;
	private RelativeLayout rele_verifyphone;
	private LinearLayout rele_signup;
	private String string;
	private EditText edit_pwd_mefra;
	private EditText edit_pwd2_mefra;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forget_pwd);
		initViews();
		init();
		initEvents();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_getsms: // 发送验证码
			string = edit_iphone_signupact.getText().toString();
			if (string.equals("")) {
				edit_iphone_signupact.setError("请输入正确的手机号");
				return;
			}
			Log.e("==", string);
			// 发送验证码
			String url = Content.URL.SENDSMS + string;
			HTTPUtils.get(ForgetPwdActivity.this, url, new VolleyListener() {

				@Override
				public void onResponse(String arg0) {
					Forgetpwd parseJSON = GsonUtils.parseJSON(arg0, Forgetpwd.class);
					if (parseJSON.getTag() == 1) {
						smscode = parseJSON.getCode();
						showShortToast(parseJSON.getMessage());
					} else {
						showShortToast(parseJSON.getMessage());
					}
				}

				@Override
				public void onErrorResponse(VolleyError arg0) {

				}
			});
			break;
		case R.id.back:
			finish();
			break;
		case R.id.btn_next_signupact: // 下一步
			String sms = edit_sms.getText().toString();
			if (sms.isEmpty()) {
				edit_sms.setError("请输入验证码");
				showShortToast("请输入验证码");
				return;
			} else {
				if (sms.equals(smscode + "")) {
					rele_verifyphone.setVisibility(View.GONE);
					rele_signup.setVisibility(View.VISIBLE);
				} else {
					edit_sms.setError("验证码不正确");
					showShortToast("验证码不正确");
					return;
				}
			}
			break;
		case R.id.btn_signin_mefra:
			//修改密码
			signUp();
			break;
		default:
			break;
		}
	}

	@Override
	protected void initViews() {
		edit_iphone_signupact = (EditText) findViewById(R.id.edit_iphone_signupact);
		edit_sms = (EditText) findViewById(R.id.edit_sms);

		rele_verifyphone = (RelativeLayout) findViewById(R.id.rele_verifyphone);
		rele_signup = (LinearLayout) findViewById(R.id.rele_signup);

		edit_pwd_mefra = (EditText) findViewById(R.id.edit_pwd_mefra);
		edit_pwd2_mefra = (EditText) findViewById(R.id.edit_pwd2_mefra);
	}

	@Override
	protected void initEvents() {
		findViewById(R.id.btn_next_signupact).setOnClickListener(this);
		findViewById(R.id.btn_getsms).setOnClickListener(this);
		findViewById(R.id.back).setOnClickListener(this);
		findViewById(R.id.btn_signin_mefra).setOnClickListener(this);
	}

	public void signUp() {
		String pwd1 = edit_pwd_mefra.getText().toString();
		String pwd2 = edit_pwd2_mefra.getText().toString();
		if (pwd1.isEmpty()) {
			edit_pwd_mefra.setError("请输入密码");
			return;
		}
		if (pwd2.isEmpty()) {
			edit_pwd2_mefra.setError("请输入密码");
			return;
		}
		if (pwd1.equals(pwd2)) {
			String url = Content.URL.FORGETPWD + string + "&pwd=" + pwd1;
			HTTPUtils.get(ForgetPwdActivity.this, url, new VolleyListener() {

				@Override
				public void onResponse(String arg0) {
					SignOut parseJSON = GsonUtils.parseJSON(arg0, SignOut.class);
					if (parseJSON.getTag() == 1) {
						Toast.makeText(ForgetPwdActivity.this, "修改成功！", Toast.LENGTH_SHORT).show();
						finish();
					}
				}

				@Override
				public void onErrorResponse(VolleyError arg0) {

				}
			});
		} else {
			edit_pwd2_mefra.setError("请确认密码输入无误");
		}
	}

	@Override
	protected void init() {
	}

}
