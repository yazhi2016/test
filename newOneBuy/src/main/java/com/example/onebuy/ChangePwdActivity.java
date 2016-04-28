package com.example.onebuy;

import android.support.v7.app.ActionBarActivity;

import com.android.volley.VolleyError;
import com.example.newonebuy.R;
import com.example.model.SignOut;
import com.example.util.Content;
import com.xinbo.utils.GsonUtils;
import com.xinbo.utils.HTTPUtils;
import com.xinbo.utils.VolleyListener;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ChangePwdActivity extends ActionBarActivity implements OnClickListener {

	private Button btn_signin_mefra;
	private TextView text_nowaccount_changepwdact;
	private EditText edit_newpwd2_mefra;
	private EditText edit_newpwd_mefra;
	private EditText edit_oldpwd_mefra;
	SharedPreferences sp;
	String userName;
	String token;
	String uid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_pwd);
		
		sp = getSharedPreferences("token", 0);
		token = sp.getString("token", "");
		uid = sp.getString("uid", "");
		userName = sp.getString("userName", "");
		
		edit_oldpwd_mefra = (EditText) findViewById(R.id.edit_oldpwd_mefra);
		edit_newpwd_mefra = (EditText) findViewById(R.id.edit_newpwd_mefra);
		edit_newpwd2_mefra = (EditText) findViewById(R.id.edit_newpwd2_mefra);
		text_nowaccount_changepwdact = (TextView) findViewById(R.id.text_nowaccount_changepwdact);
		btn_signin_mefra = (Button) findViewById(R.id.btn_signin_mefra);
		btn_signin_mefra.setOnClickListener(this);
		
		
		text_nowaccount_changepwdact.setText("当前账号：" + userName);
		findViewById(R.id.back).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_signin_mefra: //点击修改
			changePwd();
			break;
		case R.id.back:
			finish();
			break;
		default:
			break;
		}
		
	}

	private void changePwd() {
		String oldPwd = edit_oldpwd_mefra.getText().toString();
		String newPwd1 = edit_newpwd_mefra.getText().toString();
		String newPwd2 = edit_newpwd2_mefra.getText().toString();
		
		if(oldPwd.isEmpty()) {
			edit_oldpwd_mefra.setError("密码不能为空");
			return;
		}
		if(newPwd1.isEmpty()) {
			edit_newpwd_mefra.setError("密码不能为空");
			return;
		}
		if(newPwd2.isEmpty()) {
			edit_newpwd2_mefra.setError("密码不能为空");
			return;
		}
		if(!newPwd2.equals(newPwd1)) {
			edit_newpwd2_mefra.setError("请确认密码无误");
		}
		
		String url = Content.URL.CHANGEPWD + token + "&uid=" + uid + "&new_pwd=" + newPwd1 + "&old_pwd=" + oldPwd; 
		HTTPUtils.get(ChangePwdActivity.this, url, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				SignOut parseJSON = GsonUtils.parseJSON(arg0, SignOut.class);
				Integer tag2 = parseJSON.getTag();
				if(tag2 == 1) { //更改成功
					Toast.makeText(ChangePwdActivity.this, "密码修改成功！", Toast.LENGTH_SHORT).show();
					finish();
				} else {
					Toast.makeText(ChangePwdActivity.this, "密码错误！", Toast.LENGTH_SHORT).show();
				}
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				
			}
		});
	}

}
