package com.example.onebuy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.model.Qqlogin;
import com.example.model.SignSuccess;
import com.example.model.Tag;
import com.example.newonebuy.R;
import com.example.util.BaseActivity;
import com.example.util.Content;
import com.example.util.GlobalVariable;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.xinbo.utils.GsonUtils;
import com.xinbo.utils.HTTPUtils;
import com.xinbo.utils.VolleyListener;

import java.util.HashMap;
import java.util.Map;

/**
 * 登陆界面
 */
public class LogInActivity extends BaseActivity {

	private EditText edit_name_mefra;
	private EditText edit_pwd_mefra;
	String userName;
	String token;
	String uid;
	private RelativeLayout rela_login;
	private String stringExtra;
	UMShareAPI mShareAPI;
	SHARE_MEDIA platformSINA = SHARE_MEDIA.SINA;
	SHARE_MEDIA platformWEIXIN = SHARE_MEDIA.WEIXIN;
	SHARE_MEDIA platformQQ = SHARE_MEDIA.QQ;
	int logInMode = 0; //0--qq,1--微信

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_log_in);
		Intent intent = getIntent();
		stringExtra = intent.getStringExtra("state");

		mShareAPI = UMShareAPI.get(this);

		initViews();
		initEvents();
	}

	private UMAuthListener umAuthListener = new UMAuthListener() {
		@Override
		public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
			Log.e("==", data.toString() + action);
			if(action == 0) { //授权成功，获取用户信息
				mShareAPI.getPlatformInfo(LogInActivity.this, platformQQ, umAuthListener);
			} else if(action == 2) { //获取用户信息成功
				String url = Content.URL.QQLOGIN;
				Map<String, String> params = new HashMap<String, String>();
				params.put("uthirdaccount", data.get("openid"));
				params.put("uname", data.get("screen_name"));
				if(logInMode == 0) {
					params.put("type", "qq");
				}
				HTTPUtils.post(LogInActivity.this, url, params, new VolleyListener() {
					
					@Override
					public void onResponse(String arg0) {
						Qqlogin parseJSON = GsonUtils.parseJSON(arg0, Qqlogin.class);
						if(parseJSON.getTag() == 1) {
							GlobalVariable instance = GlobalVariable.getInstance();
							instance.setSpToken(parseJSON.getToken());
							instance.setSpUid(parseJSON.getUid());
							finish();
						}
					}
					
					@Override
					public void onErrorResponse(VolleyError arg0) {
						
					}
				});
			}
			
		}

		@Override
		public void onError(SHARE_MEDIA platform, int action, Throwable t) {
			Toast.makeText(getApplicationContext(), "Authorize fail", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onCancel(SHARE_MEDIA platform, int action) {
			Toast.makeText(getApplicationContext(), "Authorize cancel", Toast.LENGTH_SHORT).show();
		}
	};

	private void signIn() {
		String name = edit_name_mefra.getText().toString();
		String pwd = edit_pwd_mefra.getText().toString();

		if (name.isEmpty()) {
			edit_name_mefra.setError("帐户名不能为空");
			return;
		}

		if (pwd.isEmpty()) {
			edit_pwd_mefra.setError("密码不能为空");
			return;
		}

		userName = name;
		String url = Content.URL.SIGNIN + name + "&pwd=" + pwd;
		HTTPUtils.get(LogInActivity.this, url, new VolleyListener() {

			@Override
			public void onResponse(String arg0) {
				Spanned fromHtml = Html.fromHtml(arg0);
				SignSuccess parseJSON = GsonUtils.parseJSON(fromHtml + "", SignSuccess.class);
				Tag tag = parseJSON.getTag();
				Integer get0 = tag.get0();
				if (get0 == 1) { // 登陆成功

					// 1.得到InputMethodManager对象
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					// 2.调用hideSoftInputFromWindow方法隐藏软键盘
					imm.hideSoftInputFromWindow(rela_login.getWindowToken(), 0); // 强制隐藏键盘

					Toast.makeText(LogInActivity.this, "登陆成功！", Toast.LENGTH_SHORT).show();
					token = parseJSON.getToken();
					uid = parseJSON.getUser().getUid();

					GlobalVariable instance = GlobalVariable.getInstance();
					instance.setSpToken(token);
					instance.setSpUid(uid);
					instance.setSpValue("userName", userName);

					finish();
				} else { // 登陆失败
					Toast.makeText(LogInActivity.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void onErrorResponse(VolleyError arg0) {

			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) { // 是否在个人信息等页面，如果是，又token失效，则如果不登陆应该返回主页
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// if("safe".equals(stringExtra)) {
			// Intent intent = new Intent(LogInActivity.this,
			// MainActivity.class);
			// intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			// startActivity(intent);
			// finish();
			// } else {
			// finish();
			// }
			// 点击返回，回到主页
			Intent intent = new Intent(LogInActivity.this, MainActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_signin_mefra: // 登陆
			signIn();
			break;
		case R.id.ima_weixinSignIn_mefra: // 微信登陆
			mShareAPI.doOauthVerify(this, platformWEIXIN, umAuthListener);
			break;
		case R.id.ima_weiboSignIn_mefra: // 微博登陆
			mShareAPI.doOauthVerify(this, platformSINA, umAuthListener);
			break;
		case R.id.text_signup_mefra: // 点击新用户注册
			Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
			intent.putExtra("for", "signup");
			startActivity(intent);
			break;
		case R.id.ima_QqSignIn_mefra: // QQ登陆
			// Intent intent5 = new Intent(LogInActivity.this,
			// SocialSignUpActivity.class);
			// intent5.putExtra("login", "QQ");
			// startActivity(intent5);
//			mShareAPI.getPlatformInfo(LogInActivity.this, platformQQ, umAuthListener);
			mShareAPI.doOauthVerify(this, platformQQ, umAuthListener);
			break;
		case R.id.text_forgetPwd_mefra: // 点击忘记密码
			Intent intent2 = new Intent(LogInActivity.this, ForgetPwdActivity.class);
			startActivity(intent2);
			break;
		case R.id.back: // 返回
			Intent intent3 = new Intent(LogInActivity.this, MainActivity.class);
			intent3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent3);
			finish();
			break;
		default:
			break;
		}
	}

	@Override
	protected void initViews() {
		rela_login = (RelativeLayout) findViewById(R.id.rela_login);
		edit_name_mefra = (EditText) findViewById(R.id.edit_name_mefra);
		edit_pwd_mefra = (EditText) findViewById(R.id.edit_pwd_mefra);
	}

	@Override
	protected void initEvents() {
		findViewById(R.id.btn_signin_mefra).setOnClickListener(this);
		findViewById(R.id.ima_weiboSignIn_mefra).setOnClickListener(this);
		findViewById(R.id.ima_QqSignIn_mefra).setOnClickListener(this);
		findViewById(R.id.text_signup_mefra).setOnClickListener(this);
		findViewById(R.id.text_forgetPwd_mefra).setOnClickListener(this);
		findViewById(R.id.ima_weixinSignIn_mefra).setOnClickListener(this);
		findViewById(R.id.back).setOnClickListener(this);
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, arg2);
		mShareAPI.onActivityResult(arg0, arg1, arg2);
	}

}
