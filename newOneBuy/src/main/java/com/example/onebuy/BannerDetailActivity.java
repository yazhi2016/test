package com.example.onebuy;

import android.support.v7.app.ActionBarActivity;
import com.example.newonebuy.R;
import com.example.util.BaseActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class BannerDetailActivity extends BaseActivity {

	private WebView webView1;
	private WebSettings settings;
	private String url;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_banner_detail);
		url = getIntent().getStringExtra("url");
		initViews();
		initEvents();
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
		// 网页
		webView1 = (WebView) findViewById(R.id.webView1);
		settings = webView1.getSettings();
		settings.setJavaScriptEnabled(true);
		
		webView1.setWebChromeClient(new WebChromeClient());
		webView1.setWebViewClient(new WebViewClient());

		webView1.loadUrl(url);
	}

	@Override
	protected void initEvents() {
		findViewById(R.id.back).setOnClickListener(this);
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub

	}

}
