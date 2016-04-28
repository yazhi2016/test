package com.example.onebuy;

import android.support.v7.app.ActionBarActivity;
import com.example.newonebuy.R;
import com.example.util.Content;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class ChangeQQActivity extends ActionBarActivity implements OnClickListener {

	private EditText edit_qq;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_qq);
		findViewById(R.id.back).setOnClickListener(this);
		findViewById(R.id.checkbox_shopfra_edit).setOnClickListener(this);
		edit_qq = (EditText) findViewById(R.id.edit_qq);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.checkbox_shopfra_edit:
			String string = edit_qq.getText().toString();
			if(string.isEmpty()) {
				edit_qq.setError("昵称不能为空");
				return;
			}
			
			Intent intent = new Intent();
			intent.putExtra("qq", string);
			setResult(Content.ResultCode.changeQQactctToMyinfra, intent);
			finish();
			break;

		default:
			break;
		}
	}

}
