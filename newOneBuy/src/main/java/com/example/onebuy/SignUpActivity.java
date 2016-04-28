package com.example.onebuy;

import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.newonebuy.R;
import com.example.homemodel.SignUpVerify;
import com.example.model.Agreement;
import com.example.model.Data6;
import com.example.model.Register;
import com.example.model.Sms;
import com.example.util.Content;
import com.example.util.GlobalVariable;
import com.loc.r;
import com.xinbo.utils.GsonUtils;
import com.xinbo.utils.HTTPUtils;
import com.xinbo.utils.RegexValidateUtil;
import com.xinbo.utils.VolleyListener;

import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.PopupWindow.OnDismissListener;

/**
 * 注册界面
 */
public class SignUpActivity extends ActionBarActivity implements OnClickListener {

	private RelativeLayout rela_IHaveRead;
	private TextView text_bar;
	private RelativeLayout bar;
	private String stringExtra;
	private TextView text_agreement;
	int mode = 0; // 0注册，1忘记密码
	private EditText edit_iphone_signupact;
	private CheckBox checkbox_read;
	private LinearLayout rele_signup;
	private RelativeLayout rele_verifyphone;
	private EditText edit_pwd2_mefra;
	private EditText edit_pwd_mefra;
	String registerPhone;
	private TextView text_signupphone;
	private Integer randCode;
	private EditText edit_sms;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		Intent intent = getIntent();
		stringExtra = intent.getStringExtra("for");

		bar = (RelativeLayout) findViewById(R.id.bar);
		findViewById(R.id.back).setOnClickListener(this);
		findViewById(R.id.btn_next_signupact).setOnClickListener(this);
		checkbox_read = (CheckBox) findViewById(R.id.checkbox_read);
		rela_IHaveRead = (RelativeLayout) findViewById(R.id.rela_IHaveRead);
		text_bar = (TextView) findViewById(R.id.text_bar_signupact);
		edit_iphone_signupact = (EditText) findViewById(R.id.edit_iphone_signupact);

		if (!stringExtra.equals("signup")) { // 点击忘记密码进来的
			text_bar.setText("找回密码");
			rela_IHaveRead.setVisibility(View.INVISIBLE);
			mode = 1;
		}

		findViewById(R.id.text_agreement_signupact).setOnClickListener(this);
		View pop = getLayoutInflater().inflate(R.layout.pop_agreement_signupact, null);
		text_agreement = (TextView) pop.findViewById(R.id.text_agreement);

		rele_signup = (LinearLayout) findViewById(R.id.rele_signup);
		rele_verifyphone = (RelativeLayout) findViewById(R.id.rele_verifyphone);
		edit_pwd_mefra = (EditText) findViewById(R.id.edit_pwd_mefra);
		edit_pwd2_mefra = (EditText) findViewById(R.id.edit_pwd2_mefra);
		findViewById(R.id.btn_signin_mefra).setOnClickListener(this);
		text_signupphone = (TextView) findViewById(R.id.text_signupphone);

		edit_sms = (EditText) findViewById(R.id.edit_sms);

		findViewById(R.id.btn_getsms).setOnClickListener(this);
	}

	public void verifyMoblie() {
		String string = edit_iphone_signupact.getText().toString();
		if (string.equals("")) {
			edit_iphone_signupact.setError("请输入正确的手机号");
			return;
		} else {
			if (RegexValidateUtil.checkMobileNumber(string)) {
				registerPhone = string;
			} else {
				edit_iphone_signupact.setError("请输入正确的手机号");
				Toast.makeText(SignUpActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
				return;
			}
			String url = Content.URL.SIGNINVERIFY + string;
			HTTPUtils.get(SignUpActivity.this, url, new VolleyListener() {

				@Override
				public void onResponse(String arg0) {
					Sms parseJSON = GsonUtils.parseJSON(arg0, Sms.class);
					Integer tag2 = parseJSON.getTag();
					if (tag2 == 1) {
						Toast.makeText(SignUpActivity.this, parseJSON.getMassage(), Toast.LENGTH_SHORT).show();
						Data6 data = parseJSON.getData();
						randCode = data.getRandCode();
					} else {
						Toast.makeText(SignUpActivity.this, parseJSON.getMassage(), Toast.LENGTH_SHORT).show();
					}
				}

				@Override
				public void onErrorResponse(VolleyError arg0) {

				}
			});
		}

	}

	public void signUp() {
		String pwd1 = edit_pwd_mefra.getText().toString();
		String pwd2 = edit_pwd2_mefra.getText().toString();
		if (pwd1.equals(pwd2)) {
			String url = Content.URL.REGISTER + registerPhone + "&pwd=" + pwd1;
			HTTPUtils.get(SignUpActivity.this, url, new VolleyListener() {

				@Override
				public void onResponse(String arg0) {
					Register parseJSON = GsonUtils.parseJSON(arg0, Register.class);
					if (parseJSON.getTag() == 1) {
						Toast.makeText(SignUpActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
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
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.text_agreement_signupact: // 点击阅读协议
			startActivity(new Intent(SignUpActivity.this, AgreementActivity.class));
			break;
		case R.id.btn_next_signupact: // 点击下一步
			// String string = edit_sms.getText().toString();
			// if (string.isEmpty()) {
			// edit_sms.setError("请输入验证码");
			// Toast.makeText(SignUpActivity.this, "请输入验证码",
			// Toast.LENGTH_SHORT).show();
			// return;
			// } else {
			// if (string.equals(randCode + "")) {
			// rele_signup.setVisibility(View.VISIBLE);
			// rele_verifyphone.setVisibility(View.GONE);
			// text_signupphone.setText("注册账号：" + registerPhone);
			// } else {
			// edit_sms.setError("验证码不正确");
			// Toast.makeText(SignUpActivity.this, "验证码不正确",
			// Toast.LENGTH_SHORT).show();
			// return;
			// }
			// }

			registerPhone = edit_iphone_signupact.getText().toString();
			if (registerPhone.isEmpty()) {
				edit_iphone_signupact.setError("请输入手机号");
				return;
			}
			if (!checkbox_read.isChecked()) {
				Toast.makeText(SignUpActivity.this, "请阅读并同意协议", Toast.LENGTH_SHORT).show();
				return;
			}
			rele_signup.setVisibility(View.VISIBLE);
			rele_verifyphone.setVisibility(View.GONE);
			text_signupphone.setText("注册账号：" + registerPhone);
			break;
		case R.id.btn_signin_mefra: // 已验证手机， 点击注册
			signUp();
			break;
		case R.id.btn_getsms: // 发送验证码
			if (checkbox_read.isChecked()) {
				verifyMoblie();
			} else {
				Toast.makeText(SignUpActivity.this, "请阅读并同意协议", Toast.LENGTH_SHORT).show();
			}
			break;
		default:
			break;
		}
	}
}
