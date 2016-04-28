package com.example.onebuy;

import android.support.v7.app.ActionBarActivity;
import com.example.newonebuy.R;
import com.example.util.BaseActivity;
import com.xinbo.utils.UILUtils;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

public class TwoCodeActivity extends BaseActivity {

	private WebView webView1;
	private WebSettings settings;
	private String url;
	private ImageView imageView2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_two_code);
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
		imageView2 = (ImageView) findViewById(R.id.imageView2);
		UILUtils.displayImageNoAnim(url, imageView2);
	}

	@Override
	protected void initEvents() {
		findViewById(R.id.back).setOnClickListener(this);
	}

	@Override
	protected void init() {

	}

}
