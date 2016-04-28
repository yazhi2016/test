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

public class ChangeSignActivity extends ActionBarActivity implements OnClickListener {

	private EditText edit_sign_changesignact;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_sign);
		findViewById(R.id.back).setOnClickListener(this);
		findViewById(R.id.checkbox_shopfra_edit).setOnClickListener(this);
		edit_sign_changesignact = (EditText) findViewById(R.id.edit_sign_changesignact);
		
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.checkbox_shopfra_edit:
			String string = edit_sign_changesignact.getText().toString();
			if(string.isEmpty()) {
				edit_sign_changesignact.setError("昵称不能为空");
				return;
			}
			Intent intent = new Intent();
			intent.putExtra("sign", string);
			setResult(Content.ResultCode.changeSignactctToMyinfra, intent);
			finish();
			break;

		default:
			break;
		}
	}

}
