package com.example.onebuy;

import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.VolleyError;
import com.example.newonebuy.R;
import com.example.homemodel.SignInSuccessByToken;
import com.example.model.SignOut;
import com.example.util.ActionSheetDialogOne;
import com.example.util.ActionSheetDialogTwo;
import com.example.util.ActionSheetDialogOne.OnSheetItemClickListenerOne;
import com.example.util.ActionSheetDialogOne.SheetItemColorOne;
import com.example.util.ActionSheetDialogTwo.OnBtnSureClickListener;
import com.example.util.ActionSheetDialogTwo.OnSheetItemClickListener;
import com.example.util.ActionSheetDialogTwo.SheetItemColor;
import com.example.util.AddressData;
import com.example.util.BaseActivity;
import com.example.util.Content;
import com.example.util.GlobalVariable;
import com.example.util.ImageUp;
import com.example.util.ImageUp2;
import com.example.wheelcity.AbstractWheelTextAdapter;
import com.example.wheelcity.ArrayWheelAdapter;
import com.example.wheelcity.OnWheelChangedListener;
import com.example.wheelcity.WheelView;
import com.example.wheelview.JudgeDate;
import com.example.wheelview.MyAlertDialog;
import com.example.wheelview.ScreenInfo;
import com.example.wheelview.WheelMain;
import com.xinbo.utils.GsonUtils;
import com.xinbo.utils.HTTPUtils;
import com.xinbo.utils.UILUtils;
import com.xinbo.utils.VolleyListener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MyInfoActivity extends BaseActivity {

	WheelMain wheelMain;
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private String cityTxt;
	private TextView text_name_myinfoact;
	private TextView text_sex_myinfoact;
	private TextView txttime;
	private TextView text_qq_myinfoact;
	private TextView text_live_myinfoact;
	private TextView text_from_myinfoact;
	private TextView text_sign_myinfoact;
	String token;
	String uid;
	SharedPreferences sp;
	private ImageView ima_user_mefra;
	public static final int TO_SELECT_PHOTO = 3;
	String picPath = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_info);
		sp = getSharedPreferences("token", 0);
		token = sp.getString("token", "");
		uid = sp.getString("uid", "");
		initViews();
		initEvents();
		initUserInfo();
	}

	private void initUserInfo() {
		String url = Content.URL.SIGNINBYTOKEN + token + "&uid=" + uid;
		HTTPUtils.get(MyInfoActivity.this, url, new VolleyListener() {

			@Override
			public void onResponse(String arg0) {
				SignInSuccessByToken parseJSON = GsonUtils.parseJSON(arg0, SignInSuccessByToken.class);
				com.example.homemodel.User user2 = parseJSON.getUser();
				text_name_myinfoact.setText(user2.getUsername());
				text_sex_myinfoact.setText(user2.getSex());
				txttime.setText(user2.getBirthday());
				text_qq_myinfoact.setText(user2.getQq());
				text_live_myinfoact.setText(user2.getAddressNow());
				text_from_myinfoact.setText(user2.getAddressHome());
				text_sign_myinfoact.setText(user2.getQianming());
				GlobalVariable.displayImageNoAnim(Content.URL.IMA_HOST + user2.getImg(), ima_user_mefra);
			}

			@Override
			public void onErrorResponse(VolleyError arg0) {

			}
		});

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rela_userIma: // 点击头像栏目
			startActivityForResult(new Intent(MyInfoActivity.this, ChoosePicActivity.class), TO_SELECT_PHOTO);
			break;
		case R.id.back: // 点击返回
			finish();
			break;
		case R.id.rela_sign_myinfoact: // 点击修改签名
			startActivityForResult(new Intent(this, ChangeSignActivity.class),
					Content.RequstCode.myinfractToChangeSignact);
			break;
		case R.id.rela_qq_myinfoact: // 点击修改QQ
			startActivityForResult(new Intent(this, ChangeQQActivity.class), Content.RequstCode.myinfractToChangeQQact);
			break;
		case R.id.rela_name_myinfoact: // 点击修改昵称
			startActivityForResult(new Intent(this, ChangeNameActivity.class),
					Content.RequstCode.myinfractToChangeNameact);
			break;
		case R.id.rela_sex_myinfoact: // 点击修改性别
			// 弹窗
			new ActionSheetDialogTwo(MyInfoActivity.this).setOnBtnSureClickListener(new OnBtnSureClickListener() {

				@Override
				public void onBtnSureClick(String str) {
					Toast.makeText(MyInfoActivity.this, "item" + str, Toast.LENGTH_SHORT).show();
				}
			}).builder().setCancelable(false).setCanceledOnTouchOutside(true)
					.addSheetItem("女", SheetItemColor.Blue, new OnSheetItemClickListener() {
						@Override
						public void onClick(int which) {
							text_sex_myinfoact.setText("女");
						}
					}).addSheetItem("男", SheetItemColor.Blue, new OnSheetItemClickListener() {
						@Override
						public void onClick(int which) {
							text_sex_myinfoact.setText("男");
						}
					}).addSheetItem("保密", SheetItemColor.Blue, new OnSheetItemClickListener() {
						@Override
						public void onClick(int which) {
							text_sex_myinfoact.setText("保密");
						}
					}).show();
			break;
		case R.id.rela_birthday_myinfoact: // 点击修改生日
			LayoutInflater inflater1 = LayoutInflater.from(MyInfoActivity.this);
			final View timepickerview1 = inflater1.inflate(R.layout.timepicker, null);
			ScreenInfo screenInfo1 = new ScreenInfo(MyInfoActivity.this);
			wheelMain = new WheelMain(timepickerview1);
			wheelMain.screenheight = screenInfo1.getHeight();
			String time1 = txttime.getText().toString();
			Calendar calendar1 = Calendar.getInstance();
			if (JudgeDate.isDate(time1, "yyyy-MM-dd")) {
				try {
					calendar1.setTime(dateFormat.parse(time1));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}

			int year1 = calendar1.get(Calendar.YEAR);
			int month1 = calendar1.get(Calendar.MONTH);
			int day1 = calendar1.get(Calendar.DAY_OF_MONTH);
			wheelMain.initDateTimePicker(year1, month1, day1);
			final MyAlertDialog dialog = new MyAlertDialog(MyInfoActivity.this).builder().setTitle("修改生日")
					// .setMsg("")
					// .setEditText("1111111111111")
					.setView(timepickerview1).setNegativeButton("取消", new OnClickListener() {
						@Override
						public void onClick(View v) {

						}
					});
			dialog.setPositiveButton("保存", new OnClickListener() {
				@Override
				public void onClick(View v) {
					txttime.setText(wheelMain.getTime());
				}
			});
			dialog.show();
			break;
		case R.id.rela_live_myinfoact: // 点击现居地
			View view = dialogm();
			final MyAlertDialog dialog1 = new MyAlertDialog(MyInfoActivity.this).builder().setTitle("修改现居地")
					.setView(view).setNegativeButton("取消", new OnClickListener() {
						@Override
						public void onClick(View v) {

						}
					});
			dialog1.setPositiveButton("保存", new OnClickListener() {
				@Override
				public void onClick(View v) {
					text_live_myinfoact.setText(cityTxt);
				}
			});
			dialog1.show();
			break;
		case R.id.rela_from_myinfoact: // 修改家乡
			View view2 = dialogm();
			final MyAlertDialog dialog3 = new MyAlertDialog(MyInfoActivity.this).builder().setTitle("修改家乡")
					.setView(view2).setNegativeButton("取消", new OnClickListener() {
						@Override
						public void onClick(View v) {

						}
					});
			dialog3.setPositiveButton("保存", new OnClickListener() {
				@Override
				public void onClick(View v) {
					text_from_myinfoact.setText(cityTxt);
				}
			});
			dialog3.show();
			break;
		case R.id.checkbox_shopfra_edit: // 保存个人资料
			String url = Content.URL.CHANGEMYINFO;
			if (picPath.isEmpty()) {
				Map<String, String> params = new HashMap<String, String>();
				params.put("token", token);
				params.put("uid", uid);
				params.put("nick_name", text_name_myinfoact.getText().toString());
				params.put("sex", text_sex_myinfoact.getText().toString());
				params.put("birthday", txttime.getText().toString());
				params.put("qq", text_qq_myinfoact.getText().toString());
				params.put("address_now", text_live_myinfoact.getText().toString());
				params.put("address_home", text_from_myinfoact.getText().toString());
				params.put("qianming", text_sign_myinfoact.getText().toString());
				HTTPUtils.post(MyInfoActivity.this, url, params, new VolleyListener() {

					@Override
					public void onResponse(String arg0) {
						SignOut parseJSON = GsonUtils.parseJSON(arg0, SignOut.class);
						Integer tag2 = parseJSON.getTag();
						if (tag2 == 1) {
							Toast.makeText(MyInfoActivity.this, "修改成功！", Toast.LENGTH_SHORT).show();
						} else {
							String message = parseJSON.getMessage();
							Toast.makeText(MyInfoActivity.this, "修改失败:" + message, Toast.LENGTH_SHORT).show();
						}
						finish();
					}

					@Override
					public void onErrorResponse(VolleyError arg0) {
						Toast.makeText(MyInfoActivity.this, "个人资料修改失败！", Toast.LENGTH_SHORT).show();
					}
				});
			} else {
				ArrayList<String> al = new ArrayList<String>();
				al.add(picPath);
				Map<String, String> params = new HashMap<String, String>();
				params.put("token", token);
				params.put("uid", uid);
				params.put("nick_name", text_name_myinfoact.getText().toString());
				params.put("sex", text_sex_myinfoact.getText().toString());
				params.put("birthday", txttime.getText().toString());
				params.put("qq", text_qq_myinfoact.getText().toString());
				params.put("address_now", text_live_myinfoact.getText().toString());
				params.put("address_home", text_from_myinfoact.getText().toString());
				params.put("qianming", text_sign_myinfoact.getText().toString());
				
				String[] str = { "token", "uid", "nick_name" , "sex", "birthday", "qq", "address_now", "address_home", "qianming"};
				
				new ImageUp(MyInfoActivity.this, al, params, true, imageuplistener, "头像上传中...", str).execute(url);
			}

			break;
		default:
			break;
		}
	}

	private View dialogm() {
		View contentView = LayoutInflater.from(this).inflate(R.layout.wheelcity_cities_layout, null);
		final WheelView country = (WheelView) contentView.findViewById(R.id.wheelcity_country);
		country.setVisibleItems(3);
		country.setViewAdapter(new CountryAdapter(this));

		final String cities[][] = AddressData.CITIES;
		final String ccities[][][] = AddressData.COUNTIES;
		final WheelView city = (WheelView) contentView.findViewById(R.id.wheelcity_city);
		city.setVisibleItems(0);

		// 地区选择
		final WheelView ccity = (WheelView) contentView.findViewById(R.id.wheelcity_ccity);
		ccity.setVisibleItems(0);// 不限城市

		country.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				updateCities(city, cities, newValue);
				cityTxt = AddressData.PROVINCES[country.getCurrentItem()] + " "
						+ AddressData.CITIES[country.getCurrentItem()][city.getCurrentItem()] + " "
						+ AddressData.COUNTIES[country.getCurrentItem()][city.getCurrentItem()][ccity.getCurrentItem()];
			}
		});

		city.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				updatecCities(ccity, ccities, country.getCurrentItem(), newValue);
				cityTxt = AddressData.PROVINCES[country.getCurrentItem()] + " "
						+ AddressData.CITIES[country.getCurrentItem()][city.getCurrentItem()] + " "
						+ AddressData.COUNTIES[country.getCurrentItem()][city.getCurrentItem()][ccity.getCurrentItem()];
			}
		});

		ccity.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				cityTxt = AddressData.PROVINCES[country.getCurrentItem()] + " "
						+ AddressData.CITIES[country.getCurrentItem()][city.getCurrentItem()] + " "
						+ AddressData.COUNTIES[country.getCurrentItem()][city.getCurrentItem()][ccity.getCurrentItem()];
			}
		});

		country.setCurrentItem(1);// 设置北京
		city.setCurrentItem(1);
		ccity.setCurrentItem(1);
		return contentView;
	}

	public class CountryAdapter extends AbstractWheelTextAdapter {
		// Countries names
		private String countries[] = AddressData.PROVINCES;

		/**
		 * Constructor
		 */
		protected CountryAdapter(Context context) {
			super(context, R.layout.wheelcity_country_layout, NO_RESOURCE);
			setItemTextResource(R.id.wheelcity_country_name);
		}

		@Override
		public View getItem(int index, View cachedView, ViewGroup parent) {
			View view = super.getItem(index, cachedView, parent);
			return view;
		}

		@Override
		public int getItemsCount() {
			return countries.length;
		}

		@Override
		protected CharSequence getItemText(int index) {
			return countries[index];
		}
	}

	private void updateCities(WheelView city, String cities[][], int index) {
		ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(this, cities[index]);
		adapter.setTextSize(18);
		city.setViewAdapter(adapter);
		city.setCurrentItem(0);
	}

	private void updatecCities(WheelView city, String ccities[][][], int index, int index2) {
		ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(this, ccities[index][index2]);
		adapter.setTextSize(18);
		city.setViewAdapter(adapter);
		city.setCurrentItem(0);
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);

		if (arg1 == Content.ResultCode.changeNameactctToMyinfra) {
			String stringExtra = arg2.getStringExtra("name");
			text_name_myinfoact.setText(stringExtra);
		}
		if (arg1 == Content.ResultCode.changeSignactctToMyinfra) {
			String stringExtra = arg2.getStringExtra("sign");
			text_sign_myinfoact.setText(stringExtra);
		}
		if (arg1 == Content.ResultCode.changeQQactctToMyinfra) {
			String stringExtra = arg2.getStringExtra("qq");
			text_qq_myinfoact.setText(stringExtra);
		}

		if (arg1 == Activity.RESULT_OK && arg0 == TO_SELECT_PHOTO) {
			picPath = arg2.getStringExtra("photo_path");

			if (picPath.equals(""))
				return;
			// 缩放图片, width, height 按相同比例缩放图片
			BitmapFactory.Options options = new BitmapFactory.Options();
			// options 设为true时，构造出的bitmap没有图片，只有一些长宽等配置信息，但比较快，设为false时，才有图片
			options.inJustDecodeBounds = true;
			Bitmap bitmap = BitmapFactory.decodeFile(picPath, options);
			int scale = (int) (options.outWidth / (float) 300);
			if (scale <= 0)
				scale = 1;
			options.inSampleSize = scale;
			options.inJustDecodeBounds = false;
			bitmap = BitmapFactory.decodeFile(picPath, options);

			ima_user_mefra.setImageBitmap(bitmap);
			// ima1.setMaxHeight(350);
		}
	}

	VolleyListener imageuplistener = new VolleyListener() {

		@Override
		public void onResponse(String arg0) {
			finish();
			showShortToast("修改成功");
		}

		@Override
		public void onErrorResponse(VolleyError arg0) {

		}
	};

	@Override
	protected void initViews() {
		ima_user_mefra = (ImageView) findViewById(R.id.ima_user_mefra);
		txttime = (TextView) findViewById(R.id.text_birth_myinfoact);
		text_live_myinfoact = (TextView) findViewById(R.id.text_live_myinfoact);
		text_from_myinfoact = (TextView) findViewById(R.id.text_from_myinfoact);

		text_name_myinfoact = (TextView) findViewById(R.id.text_name_myinfoact);
		text_sex_myinfoact = (TextView) findViewById(R.id.text_sex_myinfoact);
		text_sign_myinfoact = (TextView) findViewById(R.id.text_sign_myinfoact);
		text_qq_myinfoact = (TextView) findViewById(R.id.text_qq_myinfoact);

	}

	@Override
	protected void initEvents() {
		findViewById(R.id.back).setOnClickListener(this);
		findViewById(R.id.rela_sign_myinfoact).setOnClickListener(this);
		findViewById(R.id.rela_userIma).setOnClickListener(this);
		findViewById(R.id.rela_qq_myinfoact).setOnClickListener(this);
		findViewById(R.id.rela_name_myinfoact).setOnClickListener(this);
		findViewById(R.id.rela_sex_myinfoact).setOnClickListener(this);
		findViewById(R.id.checkbox_shopfra_edit).setOnClickListener(this);
		findViewById(R.id.rela_birthday_myinfoact).setOnClickListener(this);
		findViewById(R.id.rela_live_myinfoact).setOnClickListener(this);
		findViewById(R.id.rela_from_myinfoact).setOnClickListener(this);
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub

	}

}
