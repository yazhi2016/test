package com.example.onebuy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.model.SignOut;
import com.example.newonebuy.R;
import com.example.util.BaseActivity;
import com.example.util.Content;
import com.example.util.GlobalVariable;
import com.xinbo.utils.FileUtils;
import com.xinbo.utils.GsonUtils;
import com.xinbo.utils.HTTPUtils;
import com.xinbo.utils.VolleyListener;

import java.io.File;

public class SetActivity extends BaseActivity {

	// String token = Content.token;
	// String uid = Content.uid;
	String token;
	String uid;
	private SharedPreferences sp;
	private TextView textView82; // 缓存大小
	private File filePath;
	private CheckBox check_ima;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set);
		sp = getSharedPreferences("token", 0);
		Intent intent = getIntent();
		token = intent.getStringExtra("token");
		uid = intent.getStringExtra("uid");

		initViews();
		initEvents();

		// filePath = getCacheDir(); // 获得SD卡中的缓存路径
		filePath = getExternalCacheDir();// 获得SD卡中的缓存路径
		String size = "";
		try {
			size =  FileUtils.size(filePath);
		} catch (Exception e) {
			if (e instanceof NullPointerException) {
				textView82.setText("0");
			} else{
				textView82.setText(size);
			}
		}
	}

	private void signOut() {
		String url = Content.URL.SIGNOUT + token + "&uid=" + uid;

		HTTPUtils.get(SetActivity.this, url, new VolleyListener() {

			@Override
			public void onResponse(String arg0) {
				SignOut parseJSON = GsonUtils.parseJSON(arg0, SignOut.class);
				Toast.makeText(SetActivity.this, "已退出！", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent();
				intent.putExtra("isSignOut", true);
				setResult(Content.ResultCode.SetactToMefra, intent);

				Editor edit = sp.edit(); // 退出登陆，清空token
				edit.putString("token", "");
				edit.putString("uid", "");
				edit.commit();
				finish();
			}

			@Override
			public void onErrorResponse(VolleyError arg0) {

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.set, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rela_idea_setact: // 意见反馈
			startActivity(IdeaActivity.class);
			break;
		case R.id.rela_aboutUs_setact: // 关于我们
			startActivity(AboutUsActivity.class);
			break;
		case R.id.rela_edit_setact: // 点击编辑个人资料
			startActivity(new Intent(this, MyInfoActivity.class));
			break;
		case R.id.back:
			finish();
			break;
		case R.id.rela_safe_setact: // 点击安全设置
			startActivity(new Intent(this, ChangePwdActivity.class));
			break;
		case R.id.btn_signOut: // 点击退出登陆
			signOut();
			break;
		case R.id.rela_agreement_setact: // 服务协议
			startActivity(new Intent(SetActivity.this, AgreementActivity.class));
			break;
		case R.id.rela_service_setact: // 客服电话
			Intent intentPhone = new Intent();
			intentPhone.setAction(Intent.ACTION_DIAL); // android.intent.action.DIAL
			intentPhone.setData(Uri.parse("tel:4008508080"));
			startActivity(intentPhone);
			break;
		case R.id.rela_clear_setact: // 清理缓存
			FileUtils.delFilesFromPath(filePath);
			String size = FileUtils.size(filePath);
			textView82.setText(size);
			showShortToast("清除缓存成功！");
			break;
		default:
			break;
		}
	}

	@Override
	protected void initViews() {
		textView82 = (TextView) findViewById(R.id.textView82);
		check_ima = (CheckBox) findViewById(R.id.check_ima);
		boolean spShowIma = GlobalVariable.getInstance().getSpShowIma();
		check_ima.setChecked(!spShowIma);
		Log.e("==", "GlobalVariable" + spShowIma);
		check_ima.setOnClickListener (new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean checked = check_ima.isChecked();
				Log.e("==", "check_ima" + checked);
				GlobalVariable.getInstance().setSpShowIma(!checked);
				GlobalVariable.IsShowIma = !checked;
			}
		});
	}

	@Override
	protected void initEvents() {
		findViewById(R.id.rela_edit_setact).setOnClickListener(this);
		findViewById(R.id.back).setOnClickListener(this);
		findViewById(R.id.rela_safe_setact).setOnClickListener(this);
		findViewById(R.id.btn_signOut).setOnClickListener(this);
		findViewById(R.id.rela_agreement_setact).setOnClickListener(this);
		findViewById(R.id.rela_service_setact).setOnClickListener(this);
		findViewById(R.id.rela_clear_setact).setOnClickListener(this);
		findViewById(R.id.rela_aboutUs_setact).setOnClickListener(this);
		findViewById(R.id.rela_idea_setact).setOnClickListener(this);
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub

	}
}
