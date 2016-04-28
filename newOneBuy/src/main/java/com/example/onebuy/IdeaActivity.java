package com.example.onebuy;

import android.support.v7.app.ActionBarActivity;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.VolleyError;
import com.example.newonebuy.R;
import com.example.model.SignOut;
import com.example.util.BaseActivity;
import com.example.util.Content;
import com.xinbo.utils.GsonUtils;
import com.xinbo.utils.HTTPUtils;
import com.xinbo.utils.VolleyListener;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class IdeaActivity extends BaseActivity {

	private EditText editText2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_idea);
		initViews();
		initEvents();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.button1: // 意见反馈
			String string = editText2.getText().toString();
			if (string.isEmpty()) {
				editText2.setError("请填写反馈意见");
				return;
			}
			String url = Content.URL.IDEA;
			Map<String, String> params = new HashMap<String, String>();
			params.put("opinion", string);
			HTTPUtils.post(IdeaActivity.this, url, params, new VolleyListener() {
				
				@Override
				public void onResponse(String arg0) {
					SignOut parseJSON = GsonUtils.parseJSON(arg0, SignOut.class);
					if(parseJSON.getTag() == 1) {
						showShortToast(parseJSON.getMessage());
						finish();
					} else {
						showShortToast(parseJSON.getMessage());
					}
				}
				
				@Override
				public void onErrorResponse(VolleyError arg0) {
					
				}
			});
			

			break;

		default:
			break;
		}
	}

	@Override
	protected void initViews() {
		editText2 = (EditText) findViewById(R.id.editText2);

	}

	@Override
	protected void initEvents() {
		findViewById(R.id.back).setOnClickListener(this);
		findViewById(R.id.button1).setOnClickListener(this);
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub

	}

}
