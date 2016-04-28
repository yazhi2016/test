package com.example.util;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.widget.ImageView;

import com.xinbo.utils.UILUtils;

/*
 * 全局变量
 */
public final class GlobalVariable {

//	public static void writeToLocal(String arg0, Context activity) {
//		try {
//			FileOutputStream openFileOutput = activity.openFileOutput("123.txt", 0); // 0代表MODE_PRIVATE
//			openFileOutput.write(arg0.getBytes());
//			openFileOutput.close();
//			Log.e("==", "writeSuccess" + arg0);
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

	public static void displayImageNoAnim(String imageUrls, ImageView mImageView) {
		if(IsShowIma) { //有图模式
			UILUtils.displayImageNoAnim(imageUrls, mImageView);
		} else { //无图模式
//			mImageView.setBackgroundResource(R.drawable.black);
		}
	}
	
	// 是否触发了被登陆
	public static boolean ISlogin = false;
	public static boolean IsShowIma = true;
	// 微信支付全局变量
	public static int wxpaycode = 10;
	// 注册成功判定
	public static boolean ISregisterus = true;
	// sp name
	public String spname = "com.togethermarried";
	// UID KEY
	public String SPUIDKEY = "login_uid";
	// 存储SID
	public SharedPreferences sp;
	// 用户积分
	String userScore;

	private static GlobalVariable objectClient = new GlobalVariable();

	public static GlobalVariable getInstance() {
		return objectClient;
	}

	public void setSp(SharedPreferences sp) {
		this.sp = sp;
	}

	// 得到sp存储
	public String getSpValue(String key, String defValue) {
		return sp.getString(key, defValue);
	}

	// sp存储
	public void setSpValue(String key, String value) {
		Editor edit = sp.edit();
		edit.putString(key, value);
		edit.commit();
	}

	// sp存储用户sid
	public void setSpUid(String uid) {
		Editor edit = sp.edit();
		edit.putString("uid", uid);
		edit.commit();
	}
	
	// sp存储是否显示图片
	public void setSpShowIma(boolean showIma) {
		Editor edit = sp.edit();
		edit.putBoolean("showIma", showIma);
		edit.commit();
	}

	// 得到sp存储的是否显示图片
	public boolean getSpShowIma() {
		if(sp != null) {
			return sp.getBoolean("showIma", true);
		} else {
			return true;
		}
	}
	
	// 得到sp存储的是否显示图片
	public String getSpUid() {
		return sp.getString("uid", "");
	}
	
	// sp存储用户token
	public void setSpToken(String token) {
		Editor edit = sp.edit();
		edit.putString("token", token);
		edit.commit();
	}
	
	// 得到sp存储的sid
	public String getSpToken() {
		return sp.getString("token", "");
	}

	// sp存储用户状态，0为客户，1为配送商，登陆后点击用户中心就默认进入该页面
	public void setSpLoginState(String loginState) {
		Editor edit = sp.edit();
		edit.putString("loginState", loginState);
		edit.commit();
	}

	// 得到sp存储的用户默认进入的页面，默认进入客户
	public String getSpLoginState() {
		return sp.getString("loginState", "0");
	}

	// 被登陆后是否重新的登录
	Boolean ISRESTART = false;

	public Boolean getISRESTART() {
		return ISRESTART;
	}

	public void setISRESTART(Boolean iSRESTART) {
		ISRESTART = iSRESTART;
	}

	// 登录获得的uid
	String uid;
	// 是否需要wifi才下载图片
	Boolean iswifidome;

	public String getUid() {
		if (TextUtils.isEmpty(uid)) {
			return "";
		}
		return uid;
	}

	public Boolean getIswifidome() {
		if (iswifidome == null) {
			return false;
		}
		return iswifidome;
	}

	public void setIswifidome(Boolean iswifidome) {
		this.iswifidome = iswifidome;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	// 定位的经纬度
	private double Latitude;
	private double longitude;

	public double getLatitude() {
		return Latitude;
	}

	public void setLatitude(double latitude) {
		Latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	// 是否自己选中城市
	boolean isseleortcity = false;

	public boolean getIsseleortcity() {
		return isseleortcity;
	}

	public void setIsseleortcity(boolean isseleortcity) {
		this.isseleortcity = isseleortcity;
	}

	// 选中的城市
	private String mycity = "厦门市";

	public String getMycity() {
		return mycity;
	}

	public void setMycity(String mycity) {
		this.mycity = mycity;
	}

}
