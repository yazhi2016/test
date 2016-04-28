package com.example.onebuy;

import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.example.newonebuy.R;
import com.example.util.GlobalVariable;
import com.pgyersdk.crash.PgyCrashManager;
import com.pgyersdk.update.PgyUpdateManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class StartActivity extends ActionBarActivity {

	// 声明AMapLocationClient类对象
	public AMapLocationClient mLocationClient = null;
	// 声明定位回调监听器
	public AMapLocationListener mLocationListener = new AMapLocationListener() {

		@Override
		public void onLocationChanged(AMapLocation amapLocation) {
			if (amapLocation != null) {
				if (amapLocation.getErrorCode() == 0) {
					// 定位成功回调信息，设置相关消息
					amapLocation.getLocationType();// 获取当前定位结果来源，如网络定位结果，详见定位类型表
					amapLocation.getLatitude();// 获取纬度
					amapLocation.getLongitude();// 获取经度
					amapLocation.getAccuracy();// 获取精度信息
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date date = new Date(amapLocation.getTime());
					df.format(date);// 定位时间
					amapLocation.getAddress();// 地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
					amapLocation.getCountry();// 国家信息
					amapLocation.getProvince();// 省信息
					amapLocation.getCity();// 城市信息
					city = amapLocation.getCity();
					// Toast.makeText(MainActivity.this, "定位成功：" + city,
					// Toast.LENGTH_SHORT).show();
					amapLocation.getDistrict();// 城区信息
					amapLocation.getStreet();// 街道信息
					amapLocation.getStreetNum();// 街道门牌号信息
					amapLocation.getCityCode();// 城市编码
					amapLocation.getAdCode();// 地区编码

					GlobalVariable.getInstance().setSpValue("city", city.substring(0, city.length() - 1));

					mLocationClient.stopLocation();// 停止定位
					mLocationClient.onDestroy();// 销毁定位客户端。

				} else {
					// 显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
					Log.e("AmapError", "location Error, ErrCode:" + amapLocation.getErrorCode() + ", errInfo:"
							+ amapLocation.getErrorInfo());
					Toast.makeText(StartActivity.this, "location Error, ErrCode:" + amapLocation.getErrorCode()
							+ ", errInfo:" + amapLocation.getErrorInfo(), Toast.LENGTH_SHORT).show();
				}

				startActivity(new Intent(StartActivity.this, MainActivity.class));
				finish();
			}
		}
	};
	// 声明mLocationOption对象
	public AMapLocationClientOption mLocationOption = null;
	String city;
	SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);

		// 定位
		location();
	
		sp = getSharedPreferences("token", 0);
		GlobalVariable instance = GlobalVariable.getInstance();
		instance.setSp(sp);
	}

	private void location() {
		// 初始化定位
		mLocationClient = new AMapLocationClient(getApplicationContext());
		// 设置定位回调监听
		mLocationClient.setLocationListener(mLocationListener);
		// 初始化定位参数
		mLocationOption = new AMapLocationClientOption();
		// 设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
		mLocationOption.setLocationMode(AMapLocationMode.Battery_Saving);
		// 设置是否返回地址信息（默认返回地址信息）
		mLocationOption.setNeedAddress(true);
		// 设置是否只定位一次,默认为false
		mLocationOption.setOnceLocation(true);
		// 设置是否强制刷新WIFI，默认为强制刷新
		mLocationOption.setWifiActiveScan(true);
		// 设置是否允许模拟位置,默认为false，不允许模拟位置
		mLocationOption.setMockEnable(false);
		// 设置定位间隔,单位毫秒,默认为2000ms
		mLocationOption.setInterval(2000);
		// 给定位客户端对象设置定位参数
		mLocationClient.setLocationOption(mLocationOption);
		// 启动定位
		mLocationClient.startLocation();
	}

}
