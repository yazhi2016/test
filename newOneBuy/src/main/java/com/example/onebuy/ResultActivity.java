package com.example.onebuy;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.VolleyError;
import com.example.newonebuy.R;
import com.example.model.ProductShare;
import com.example.model.ResultShare;
import com.example.model.Shaidan;
import com.example.model.SignOut;
import com.example.model.Windetail2;
import com.example.usermodel.Data;
import com.example.usermodel.Goods;
import com.example.usermodel.QUser;
import com.example.usermodel.Qishu;
import com.example.usermodel.Windetail;
import com.example.util.BaseActivity;
import com.example.util.Content;
import com.example.util.GlobalVariable;
import com.xinbo.utils.GsonUtils;
import com.xinbo.utils.HTTPUtils;
import com.xinbo.utils.UILUtils;
import com.xinbo.utils.VolleyListener;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ResultActivity extends BaseActivity {
	// 揭晓结果
	private ReAdapter reAdapter; // 顶栏期数
	ArrayList<Qishu> numList = new ArrayList<Qishu>();
	private String goodid;
	private ImageView ima_good;
	private TextView text_title1_resultact;
	private TextView text_money_resultact;
	private TextView text_innum_resultact;
	private TextView text_surplus_resultact;
	private TextView text_all_resultact;
	private TextView text_luckynum_resultact;
	private ProgressBar progressbar_resultact;
	private ImageView ima_winner;
	private TextView text_username;
	private TextView text_buynum_resultact;
	private TextView text_endtime_resultact;
	private TextView text_buytime_resultact;
	private TextView text_buytime_resultact2;
	private RelativeLayout rela_win_resultact;
	private TextView text_num;
	private Button button_state;
	// private TextView text_showNum2;
	String allShowData;
	String showNum;
	private String stringExtra;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);

		Intent intent = getIntent();
		goodid = intent.getStringExtra("goodid");
		stringExtra = intent.getStringExtra("from");
		initViews();
		initEvents();
		initData();
	}

	private void initData() {
		String url = Content.URL.RESULT + goodid;
		HTTPUtils.get(ResultActivity.this, url, new VolleyListener() {

			@Override
			public void onResponse(String arg0) {
				Windetail parseJSON = GsonUtils.parseJSON(arg0, Windetail.class);
				Data data = parseJSON.getData();
				if (parseJSON.getTag() == 1) {
					List<Qishu> qishu = data.getQishu();
					numList.addAll(qishu);
				}
				reAdapter.notifyDataSetChanged();
				refrashInfo(parseJSON);
			}

			@Override
			public void onErrorResponse(VolleyError arg0) {

			}
		});
		// initAllShow();
	}

	private void reflashData() {
		Log.e("==", "" + goodid);
		String url = Content.URL.RESULT + goodid;
		HTTPUtils.get(ResultActivity.this, url, new VolleyListener() {
			@Override
			public void onResponse(String arg0) {
				Spanned fromHtml = Html.fromHtml(arg0);
				SignOut parseJSON = GsonUtils.parseJSON(fromHtml + "", SignOut.class);
				if (parseJSON.getTag() == 1) {
					Windetail parseJSON2 = GsonUtils.parseJSON(fromHtml + "", Windetail.class);
					refrashInfo(parseJSON2);
				} else if (parseJSON.getTag() == 3) { // 正在进行中的data不一样，所以要用不同的model
					Windetail2 parseJSON2 = GsonUtils.parseJSON(fromHtml + "", Windetail2.class);
					refrashInfo2(parseJSON2);

				}
				reAdapter.notifyDataSetChanged();
			}

			@Override
			public void onErrorResponse(VolleyError arg0) {

			}
		});
		// initAllShow();
	}

	public void initAllShow() {
		String url;
		url = Content.URL.PRODUCTSHARE + goodid + "&page=" + 0;
		// url = Content.URL.PRODUCTSHARE + "1440" + "&page=" + 0;

		HTTPUtils.get(ResultActivity.this, url, new VolleyListener() {

			@Override
			public void onResponse(String arg0) {
				Spanned fromHtml = Html.fromHtml(arg0);
				allShowData = fromHtml + "";
				ProductShare parseJSON = GsonUtils.parseJSON(fromHtml + "", ProductShare.class);
				List<Shaidan> shaidan = parseJSON.getShaidan();
				showNum = shaidan.size() + "";
				// text_showNum2.setText(showNum);
			}

			@Override
			public void onErrorResponse(VolleyError arg0) {

			}
		});

	}

	private void refrashInfo(Windetail parseJSON) {
		Integer tag2 = parseJSON.getTag();
		if (tag2 == 1) { // 已开奖
			Data data = parseJSON.getData();
			List<String> pic = data.getPic();
			QUser qUser = data.getQUser();
			if (pic.size() != 0) {
				GlobalVariable.displayImageNoAnim(Content.URL.IMA_HOST + pic.get(0), ima_good);
			}
			GlobalVariable.displayImageNoAnim(Content.URL.IMA_HOST + qUser.getImg(), ima_winner);
			text_username.setText(qUser.getUsername());
			text_buynum_resultact.setText(data.getUserShopNumber() + "");
			Goods goods = data.getGoods();
			text_title1_resultact.setText("第" + goods.getQishu() + "期" + goods.getTitle());
			text_money_resultact.setText("价值：￥" + goods.getMoney());
			text_innum_resultact.setText(goods.getCanyurenshu() + "");
			text_surplus_resultact.setText(goods.getShenyurenshu() + "");
			text_all_resultact.setText(goods.getZongrenshu() + "");
			text_luckynum_resultact.setText(goods.getQUserCode());
			int stringDivide = Content.StringDivide(goods.getCanyurenshu(), goods.getZongrenshu());
			progressbar_resultact.setProgress(stringDivide);
			String qEndTime = goods.getQEndTime();
			String replace = qEndTime.replace(" ", "\n");
			text_endtime_resultact.setText("揭晓时间\n" + replace);
			String userShopTime = data.getUserShopTime();
			String replace2 = userShopTime.replace(" ", "\n");
			text_buytime_resultact2.setText("妙购时间\n" + replace2);
			rela_win_resultact.setVisibility(View.VISIBLE);
			text_num.setText("第" + goods.getQishu() + "期  已揭晓");
			button_state.setText("本期已揭晓");
		} else if (tag2 == 2) { // 无商品

		}

	}

	private void refrashInfo2(Windetail2 parseJSON) {
		com.example.model.Data data = parseJSON.getData();
		text_num.setText("第" + data.getQishu() + "期  正在进行中");
		rela_win_resultact.setVisibility(View.GONE);
		button_state.setText("正在进行中");
		GlobalVariable.displayImageNoAnim(Content.URL.IMA_HOST + data.getThumb(), ima_good);
		text_title1_resultact.setText("第" + data.getQishu() + "期" + data.getTitle());
		text_money_resultact.setText("价值：￥" + data.getMoney());
		text_innum_resultact.setText(data.getCanyurenshu() + "");
		text_surplus_resultact.setText(data.getShenyurenshu() + "");
		text_all_resultact.setText(data.getZongrenshu() + "");
	}

	class ReAdapter extends Adapter<MyViewHolder> {

		@Override
		public int getItemCount() {
			return numList.size();
		}

		@Override
		public void onBindViewHolder(MyViewHolder arg0, final int arg1) {
			String num = numList.get(arg1).getQishu();
			TextView text_title_itemlvleft_allfra = (TextView) arg0.itemView
					.findViewById(R.id.text_title_itemlvleft_allfra);
			RelativeLayout rela_bg_readapter = (RelativeLayout) arg0.itemView.findViewById(R.id.rela_bg_readapter);
			text_title_itemlvleft_allfra.setText("第" + num + "期");
			arg0.itemView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					goodid = numList.get(arg1).getId();
					reflashData();
				}
			});

			if (goodid.equals(numList.get(arg1).getId())) {
				rela_bg_readapter.setBackgroundDrawable(getResources().getDrawable(R.drawable.gray));
			} else {
				rela_bg_readapter.setBackgroundDrawable(getResources().getDrawable(R.drawable.white));
			}
		}

		@Override
		public MyViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
			View view = getLayoutInflater().inflate(R.layout.item_lvleft_allproductfra, null);
			MyViewHolder myViewHolder = new MyViewHolder(view);
			return myViewHolder;
		}

	}

	class MyViewHolder extends ViewHolder {

		public MyViewHolder(View itemView) {
			super(itemView);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.text_lookResult: // 计算结果
			Intent intent1 = new Intent(this, CalculateResultActivity.class);
			intent1.putExtra("id", goodid);
			startActivity(intent1);
			break;
		case R.id.back:
			finish();
			break;
		case R.id.rela_allbuyrecorder: // 妙购记录
			Intent intent2 = new Intent(this, AllBuyActivity.class);
			intent2.putExtra("id", goodid);
			startActivity(intent2);
			break;
		case R.id.rela_imaAndText: // 图文详情
			Intent intent = new Intent(this, ImaTextActivity.class);
			intent.putExtra("id", goodid);
			startActivity(intent);
			break;
		case R.id.rela_shownum: // 已有晒单
			Intent intent3 = new Intent(this, AllShowActivity.class);
			intent3.putExtra("goodid", goodid);
			startActivity(intent3);
			break;
		case R.id.rela_lookdetail: // 查看详情
			Intent intent4 = new Intent(this, DetailActivity.class);
			intent4.putExtra("id", goodid);
			startActivity(intent4);
			break;

		default:
			break;
		}
	}

	@Override
	protected void initViews() {
		RecyclerView re = (RecyclerView) findViewById(R.id.recyclerView2);
		re.setHasFixedSize(true);
		LinearLayoutManager lm = new LinearLayoutManager(ResultActivity.this, LinearLayoutManager.HORIZONTAL, false);
		re.setLayoutManager(lm);
		reAdapter = new ReAdapter();
		re.setAdapter(reAdapter);

		ima_good = (ImageView) findViewById(R.id.ima_good);
		text_title1_resultact = (TextView) findViewById(R.id.text_title1_resultact);
		text_money_resultact = (TextView) findViewById(R.id.text_money_resultact);
		text_innum_resultact = (TextView) findViewById(R.id.text_innum_resultact);
		text_surplus_resultact = (TextView) findViewById(R.id.text_surplus_resultact);
		text_all_resultact = (TextView) findViewById(R.id.text_all_resultact);
		text_luckynum_resultact = (TextView) findViewById(R.id.text_luckynum_resultact);
		progressbar_resultact = (ProgressBar) findViewById(R.id.progressbar_resultact);
		text_username = (TextView) findViewById(R.id.text_username);
		text_buytime_resultact = (TextView) findViewById(R.id.text_buytime_resultact);
		text_buytime_resultact2 = (TextView) findViewById(R.id.text_buytime_resultact);

		ima_winner = (ImageView) findViewById(R.id.ima_winner);
		text_buynum_resultact = (TextView) findViewById(R.id.text_buynum_resultact);
		text_endtime_resultact = (TextView) findViewById(R.id.text_endtime_resultact);

		text_num = (TextView) findViewById(R.id.text_num);
		button_state = (Button) findViewById(R.id.button_state);
		rela_win_resultact = (RelativeLayout) findViewById(R.id.rela_win_resultact);
		// text_showNum2 = (TextView) findViewById(R.id.text_showNum2); // 几人晒单

	}

	@Override
	protected void initEvents() {
		findViewById(R.id.text_lookResult).setOnClickListener(this);
		findViewById(R.id.back).setOnClickListener(this);
		findViewById(R.id.rela_allbuyrecorder).setOnClickListener(this);
		findViewById(R.id.rela_imaAndText).setOnClickListener(this);
		findViewById(R.id.rela_shownum).setOnClickListener(this);
		findViewById(R.id.rela_lookdetail).setOnClickListener(this);
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub

	}

}
