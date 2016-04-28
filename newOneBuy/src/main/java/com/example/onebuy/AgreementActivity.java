package com.example.onebuy;

import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.text.Spanned;

import com.android.volley.VolleyError;
import com.example.newonebuy.R;
import com.example.model.Agreement;
import com.example.util.BaseActivity;
import com.example.util.Content;
import com.loc.v;
import com.xinbo.utils.GsonUtils;
import com.xinbo.utils.HTTPUtils;
import com.xinbo.utils.VolleyListener;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class AgreementActivity extends BaseActivity {

	private TextView text_agreement;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initViews();
		initEvents();
		init();
		initData();
	}
	
	private void initData() {
		String url = Content.URL.AGREENMENT;
		HTTPUtils.get(this, url, new VolleyListener() {

			@Override
			public void onResponse(String arg0) {
				Spanned fromHtml = Html.fromHtml(arg0);
				Agreement parseJSON = GsonUtils.parseJSON(fromHtml + "", Agreement.class);
				String content = parseJSON.getContent();
				String trim = content.trim();
				text_agreement.setText(trim);
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
		setContentView(R.layout.activity_agreement);
		text_agreement = (TextView) findViewById(R.id.text_agreement);
	}

	@Override
	protected void initEvents() {
		findViewById(R.id.back).setOnClickListener(this);
	}

	@Override
	protected void init() {
		
	}

}
