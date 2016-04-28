package com.example.onebuy;

import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.VolleyError;
import com.example.newonebuy.R;
import com.example.util.ActionSheetDialogOne;
import com.example.util.BaseActivity;
import com.example.util.Content;
import com.example.util.GlobalVariable;
import com.example.util.ImageUp;
import com.example.util.ActionSheetDialogOne.OnSheetItemClickListenerOne;
import com.example.util.ActionSheetDialogOne.SheetItemColorOne;
import com.xinbo.utils.HTTPUtils;
import com.xinbo.utils.VolleyListener;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ShowActivity extends BaseActivity {

	private ImageView ima1;
	public static final int TO_SELECT_PHOTO = 3;
	private String picPath = "";
	private EditText editText1;
	private EditText editText2;
	private String goodId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show);
		goodId = getIntent().getStringExtra("goodid");

		initViews();
		initEvents();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imageView2: // 点击第一张图片添加图片
			startActivityForResult(new Intent(ShowActivity.this, ChoosePicActivity.class), TO_SELECT_PHOTO);
			break;
		case R.id.back:
			finish();
			break;
		case R.id.button1: // 发表
			String title = editText1.getText().toString();
			if (title.isEmpty()) {
				editText1.setError("标题不能为空");
				return;
			}
			String content = editText2.getText().toString();
			if (content.isEmpty()) {
				editText2.setError("内容不能为空");
				return;
			}
			String url = Content.URL.SHOW;
			Map<String, String> params = new HashMap<String, String>();
			GlobalVariable instance = GlobalVariable.getInstance();
			params.put("token", instance.getSpToken());
			params.put("uid", instance.getSpUid());
			params.put("goods_id", goodId);
			params.put("title", title);
			params.put("content", content);
			if (picPath.isEmpty()) {
				showShortToast("图片不能为空");
				return;
			}
			ArrayList<String> al = new ArrayList<String>();
			al.add(picPath);
			String[] str = { "token", "uid", "goods_id", "title", "content"};
			new ImageUp(ShowActivity.this, al, params, true, imageuplistener, "头像上传中...", str).execute(url);
			break;
		default:
			break;
		}
	}

	VolleyListener imageuplistener = new VolleyListener() {

		@Override
		public void onResponse(String arg0) {
			finish();
			Log.e("==", "=晒单返回=" + arg0);
			showShortToast("晒单成功");
		}

		@Override
		public void onErrorResponse(VolleyError arg0) {

		}
	};

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
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

			ima1.setImageBitmap(bitmap);
			// ima1.setMaxHeight(350);
		}
	}

	@Override
	protected void initViews() {
		ima1 = (ImageView) findViewById(R.id.imageView2);
		editText1 = (EditText) findViewById(R.id.editText1);
		editText2 = (EditText) findViewById(R.id.editText2);
	}

	@Override
	protected void initEvents() {
		findViewById(R.id.back).setOnClickListener(this);
		findViewById(R.id.button1).setOnClickListener(this);
		ima1.setOnClickListener(this);

	}

	@Override
	protected void init() {

	}

}
