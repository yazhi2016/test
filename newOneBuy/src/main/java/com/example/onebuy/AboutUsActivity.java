package com.example.onebuy;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.model.Companydetail;
import com.example.model.Data8;
import com.example.newonebuy.R;
import com.example.util.BaseActivity;
import com.example.util.Content;
import com.xinbo.utils.GsonUtils;
import com.xinbo.utils.HTTPUtils;
import com.xinbo.utils.VolleyListener;

public class AboutUsActivity extends BaseActivity {

	private TextView text4;
	private TextView text3;
	private TextView text2;
	private TextView text1;
	private TextView textView3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_us);

		initViews();
		initEvents();
		initData();
	}

	private void initData() {
		String url = Content.URL.COMPANYDETAIL;
		HTTPUtils.get(AboutUsActivity.this, url, new VolleyListener() {

			@Override
			public void onResponse(String arg0) {
				Companydetail parseJSON = GsonUtils.parseJSON(arg0, Companydetail.class);
				if (parseJSON.getTag() == 1) {
					Data8 data = parseJSON.getData();
					text4.setText("微信服务号：" + data.getWecharService());
					text3.setText("企业QQ：" + data.getQQService());
					text2.setText("新浪微博：" + data.getWeiboService());
					text1.setText("官方网址：" + data.getTSupport());
					textView3.setText("技术支持：" + data.getAddress());
				}
			}


			@Override
			public void onErrorResponse(VolleyError arg0) {

			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;

		default:
			break;
		}
	}

	@Override
	protected void initViews() {
		textView3 = (TextView) findViewById(R.id.textView3);
		text1 = (TextView) findViewById(R.id.text1);
		text2 = (TextView) findViewById(R.id.text2);
		text3 = (TextView) findViewById(R.id.text3);
		text4 = (TextView) findViewById(R.id.text4);
	}

	@Override
	protected void initEvents() {
		findViewById(R.id.back).setOnClickListener(this);
	}

	@Override
	protected void init() {

	}

}
