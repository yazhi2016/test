package com.example.onebuy;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.PopupMenu;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.newonebuy.R;

/**
 *  充值界面
 */
public class ChargeActivity extends ActionBarActivity implements OnClickListener {

	private EditText edit_money;
	private ImageView ima_wxpaychoosed_accountact;
	private ImageView ima_ylpaychoosed_accountact;
	private ImageView ima_alipaychoosed_accountact;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_charge2);
		initUI();
	}

	private void initUI() {
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); 
		edit_money = (EditText) findViewById(R.id.edit_money);
		
		findViewById(R.id.back).setOnClickListener(this);
		findViewById(R.id.pop_accountact).setOnClickListener(this);
		findViewById(R.id.rela_wxpay_accountact).setOnClickListener(this);
		findViewById(R.id.rela_ylpay_accountact).setOnClickListener(this);
		findViewById(R.id.rela_alipay_accountact).setOnClickListener(this);
		ima_wxpaychoosed_accountact = (ImageView) findViewById(R.id.ima_wxpaychoosed_accountact);
		ima_ylpaychoosed_accountact = (ImageView) findViewById(R.id.ima_ylpaychoosed_accountact);
		ima_alipaychoosed_accountact = (ImageView) findViewById(R.id.ima_alipaychoosed_accountact);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.rela_wxpay_accountact: //点击微信支付
			ima_wxpaychoosed_accountact.setVisibility(View.VISIBLE);
			ima_ylpaychoosed_accountact.setVisibility(View.INVISIBLE);
			ima_alipaychoosed_accountact.setVisibility(View.INVISIBLE);
			break;
		case R.id.rela_ylpay_accountact: //点击银联支付
			ima_wxpaychoosed_accountact.setVisibility(View.INVISIBLE);
			ima_ylpaychoosed_accountact.setVisibility(View.VISIBLE);
			ima_alipaychoosed_accountact.setVisibility(View.INVISIBLE);
			break;
		case R.id.rela_alipay_accountact: //点击支付宝支付
			ima_wxpaychoosed_accountact.setVisibility(View.INVISIBLE);
			ima_ylpaychoosed_accountact.setVisibility(View.INVISIBLE);
			ima_alipaychoosed_accountact.setVisibility(View.VISIBLE);
			break;
			
		case R.id.pop_accountact: //点击顶栏pop
			
			PopupMenu popupMenu = new PopupMenu(this, v);
			popupMenu.getMenuInflater().inflate(R.menu.charge, popupMenu.getMenu());
			//菜单按钮的点击事件
			popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
				
				@Override
				public boolean onMenuItemClick(MenuItem arg0) {
					switch (arg0.getItemId()) {
					case R.id.charge_chargeact: //点击了充值明细
						Intent intent = new Intent(ChargeActivity.this, AccountDetailActivity.class);
						intent.putExtra("fromcharge", "charge");
						startActivity(intent);
						break;
					case R.id.pay_chargeact: //点击了消费明细
						Intent intent2 = new Intent(ChargeActivity.this, AccountDetailActivity.class);
						intent2.putExtra("fromcharge", "pay");
						startActivity(intent2);
						break;

					default:
						break;
					}
					return false;
				}
			});
			popupMenu.show();

		default:
			break;
		}
	}

}
