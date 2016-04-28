package com.example.onebuy;

import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import java.util.Map;
import com.example.newonebuy.R;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.xinbo.utils.UILUtils;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class SocialSignUpActivity extends ActionBarActivity {

	private SHARE_MEDIA platform;
	private UMShareAPI mShareAPI;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_social_sign_up);
		PlatformConfig.setQQZone("1105049891", "sZhOnMSnUgYzKDmx");
		mShareAPI = UMShareAPI.get(this);
		
		Intent intent = getIntent();
		String stringExtra = intent.getStringExtra("login");
		if ("QQ".equals(stringExtra)) { //如果是QQ登陆
			LogInQQ();
		}
	}

	private void LogInQQ() {
		platform = SHARE_MEDIA.QQ;
		mShareAPI.doOauthVerify(this, platform, umAuthListener);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		mShareAPI.onActivityResult(requestCode, resultCode, data);
	}
	
	private UMAuthListener umAuthListener = new UMAuthListener() {

		@Override
		public void onError(SHARE_MEDIA arg0, int arg1, Throwable arg2) {
			Log.e("==", "onError");
		}

		@Override
		public void onComplete(SHARE_MEDIA arg0, int arg1, Map<String, String> arg2) {
			if (arg1 == 0) {
				mShareAPI.getPlatformInfo(SocialSignUpActivity.this, platform, umAuthListener);
			}
			if (arg1 == 2) {
				Log.e("==", arg2.toString());
				finish();
//				final String ima_url = arg2.get("profile_image_url");
//				String name1 = arg2.get("screen_name");
//				name.setText(name1);
//				UILUtils.displayImageNoAnim(arg2.get("profile_image_url"), ima);
			}
		}

		@Override
		public void onCancel(SHARE_MEDIA arg0, int arg1) {
			Log.e("==", "onCancel");
			finish();
		}
	};
	
}
