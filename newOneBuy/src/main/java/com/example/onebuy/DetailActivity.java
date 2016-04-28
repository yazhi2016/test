package com.example.onebuy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.homemodel.SignInSuccessByToken;
import com.example.model.Detail2;
import com.example.model.HuodeShang2;
import com.example.model.Item2;
import com.example.model.Productshare3;
import com.example.model.QishuInfo;
import com.example.model.Shaidan3;
import com.example.newonebuy.R;
import com.example.util.BaseActivity;
import com.example.util.Content;
import com.example.util.GlobalVariable;
import com.example.util.MyShop;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.xinbo.utils.GsonUtils;
import com.xinbo.utils.HTTPUtils;
import com.xinbo.utils.VolleyListener;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends BaseActivity {

	String goodId;
	private TextView text_num_bar_detail;
	private TextView text_title1_detailact;
	private TextView text_title2_detailact;
	private TextView text_money_detailact;
	private TextView text_innum_detailact;
	private TextView text_all_detailact;
	private TextView text_surplus_detailact;
	private ImageView ima_detailact;
	private RelativeLayout rela_winner_detailact;
	private ImageView ima_winner_detailact;
	private TextView text_luckycode_detailact;
	private TextView text_buytime_detailact;
	private TextView text_endtime_detailact;
	private TextView text_winnerfrom_detailact;
	private TextView text_name_detailact;
	private ProgressBar progressbar1;
	String showNum;
	private TextView text_showNum;
	String allShowData;
	private String sid;
	private String shopid;

	String token;
	String uid;
	boolean isLogIn = false;
	private Item2 item;
	private RecyclerView recyclerView2;
	ArrayList<QishuInfo> numList = new ArrayList<QishuInfo>();
	private ReAdapter reAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		initViews();
		initEvents();
		initUI();
		initData(goodId);
	}

	private void initUI() {

		Intent intent = getIntent();
		goodId = intent.getStringExtra("id");
	}

	private void initData(String id) {
		String url = Content.URL.GOODDETAIL + id;
		// String url = Content.URL.GOODDETAIL + "1445";
		HTTPUtils.get(this, url, new VolleyListener() {

			@Override
			public void onResponse(String arg0) {
				Spanned fromHtml = Html.fromHtml(arg0);
				Detail2 parseJSON = GsonUtils.parseJSON(fromHtml + "", Detail2.class);
				item = parseJSON.getItem();
				text_num_bar_detail.setText("第" + item.getQishu() + "期");
				text_title1_detailact.setText("(第" + item.getQishu() + "期)" + item.getTitle());
				Log.e("==", item.getTitle2());
				text_title2_detailact.setText(item.getTitle2());
				text_money_detailact.setText("价值：￥" + item.getMoney());
				text_innum_detailact.setText(item.getCanyurenshu());
				text_all_detailact.setText(item.getZongrenshu());
				text_surplus_detailact.setText(item.getShenyurenshu());
				GlobalVariable.displayImageNoAnim(Content.URL.IMA_HOST + item.getThumb(), ima_detailact);
				sid = item.getSid();
				HuodeShang2 huodeShang = parseJSON.getHuodeShang();
				if (huodeShang.getUsername().equals("")) { // 还没开奖
					rela_winner_detailact.setVisibility(View.GONE);
				} else {
					shopid = huodeShang.getShopid();
					rela_winner_detailact.setVisibility(View.VISIBLE);
					text_name_detailact.setText(huodeShang.getUsername());
					text_winnerfrom_detailact.setText("(" + huodeShang.getIp() + ")");
					text_buytime_detailact.setText(huodeShang.getTime());
					text_luckycode_detailact.setText(huodeShang.getHuode());
					GlobalVariable.displayImageNoAnim(Content.URL.IMA_HOST + huodeShang.getUphoto(),
							ima_winner_detailact);
				}

				int stringDivide = Content.StringDivide2(item.getShenyurenshu(), item.getZongrenshu());
				progressbar1.setProgress(stringDivide);

				List<QishuInfo> qishuInfo = parseJSON.getQishuInfo();
				numList.clear();
				numList.addAll(qishuInfo);
				reAdapter.notifyDataSetChanged();
			}

			@Override
			public void onErrorResponse(VolleyError arg0) {

			}
		});

	}

	public void initAllShow() {
		String url;
		// url = Content.URL.PRODUCTSHARE + goodId + "&page=" + 0;

		if (isLogIn) { // 登陆状态还要传UID
			url = Content.URL.PRODUCTSHARE + "1440" + "&page=" + 0 + "&uid=" + uid;
		} else {
			url = Content.URL.PRODUCTSHARE + "1440" + "&page=" + 0;
		}

		HTTPUtils.get(DetailActivity.this, url, new VolleyListener() {

			@Override
			public void onResponse(String arg0) {
				Spanned fromHtml = Html.fromHtml(arg0);
				allShowData = fromHtml + "";
				if (isLogIn) { // 登陆状态还要传UID
					Productshare3 parseJSON = GsonUtils.parseJSON(fromHtml + "", Productshare3.class);
					List<Shaidan3> shaidan = parseJSON.getShaidan();
					showNum = shaidan.size() + "";
				} else {
					Productshare3 parseJSON = GsonUtils.parseJSON(fromHtml + "", Productshare3.class);
					List<Shaidan3> shaidan = parseJSON.getShaidan();
					showNum = shaidan.size() + "";
				}
				text_showNum.setText("( " + showNum + " )");
			}

			@Override
			public void onErrorResponse(VolleyError arg0) {

			}
		});

	}

	@Override
	protected void onResume() {
		super.onResume();
		GlobalVariable instance = GlobalVariable.getInstance();
		token = instance.getSpToken();
		uid = instance.getSpUid();
		if (token.equals("")) { // 未登录，弹登陆窗口
			isLogIn = false;
			// initAllShow();
		} else { // 有token，判断token是否有效
			signInByToken();
		}
	}

	public void signInByToken() {
		String url = Content.URL.SIGNINBYTOKEN + token + "&uid=" + uid;
		HTTPUtils.get(this, url, new VolleyListener() {

			@Override
			public void onResponse(String arg0) {
				Spanned fromHtml = Html.fromHtml(arg0);
				SignInSuccessByToken parseJSON = GsonUtils.parseJSON(fromHtml + "", SignInSuccessByToken.class);
				if (parseJSON.getTag() == 1) { // 登录成功
					isLogIn = true;
				} else {
					isLogIn = false;
					// showShortToast(parseJSON.getMessage());
				}
				// initAllShow();
				return;
			}

			@Override
			public void onErrorResponse(VolleyError arg0) {

			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rela_winner_detailact: // 点击上一期获得者
			Intent intent0 = new Intent(DetailActivity.this, ResultActivity.class);
			intent0.putExtra("goodid", shopid);
			startActivityForResult(intent0, 0);
			break;
		case R.id.text_num_bar_detail: // 点击第几期
			recyclerView2.setVisibility(View.VISIBLE);
			break;
		case R.id.rela_detail_detailact: // 点击图文详情
			Intent intent = new Intent(this, ImaTextActivity.class);
			intent.putExtra("id", goodId);
			startActivity(intent);
			break;
		case R.id.rela_allorder_detailact: // 点击所有妙购记录
			Intent intent2 = new Intent(this, AllBuyActivity.class);
			intent2.putExtra("id", goodId);
			startActivity(intent2);
			break;
		case R.id.back:
			finish();
			break;
		case R.id.rela_allShow_detailact: // 点击所有晒单
			Intent intent3 = new Intent(this, AllShowActivity.class);
			intent3.putExtra("goodid", sid);
			startActivity(intent3);
			break;
		case R.id.btn_shopcar_detailact: // 添加到购物车
			List<MyShop> dataList = new Select().from(MyShop.class).queryList();
			for (int i = 0; i < dataList.size(); i++) {
				String goodid = dataList.get(i).getGoodid();
				if (goodId.equals(goodid)) {
					Toast.makeText(DetailActivity.this, "购物车已有该商品", Toast.LENGTH_SHORT).show();
					return;
				}
			}

			Content.addShop(item.getId(), Content.URL.IMA_HOST + item.getThumb(), item.getQishu(), "1", item.getTitle(),
					item.getShenyurenshu(), item.getYunjiage(), item.getYunjiage(), false);
			Toast.makeText(DetailActivity.this, "已添加到购物车", Toast.LENGTH_SHORT).show();
			break;
		case R.id.imageView5: // 购物车图标，跳转到购物车
			Intent intent4 = new Intent(DetailActivity.this, MainActivity.class);
			intent4.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent4.putExtra("item", 2);
			startActivity(intent4);
			break;
		case R.id.btn_buy_detailact: // 立刻妙购,添加到购物车并跳转到购物车
			List<MyShop> dataList2 = new Select().from(MyShop.class).queryList();
			for (int i = 0; i < dataList2.size(); i++) {
				String goodid = dataList2.get(i).getGoodid();
				if (item.getId().equals(goodid)) {
					Intent intent5 = new Intent(DetailActivity.this, MainActivity.class);
					intent5.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					intent5.putExtra("item", 2);
					startActivity(intent5);
					return;
				}
			}

			Content.addShop(item.getId(), Content.URL.IMA_HOST + item.getThumb(), item.getQishu(), "1", item.getTitle(),
					item.getShenyurenshu(), item.getYunjiage(), item.getYunjiage(), false);

			Intent intent5 = new Intent(DetailActivity.this, MainActivity.class);
			intent5.putExtra("item", 2);
			startActivity(intent5);
			break;
		default:
			break;
		}

	}

	@Override
	protected void initViews() {
		progressbar1 = (ProgressBar) findViewById(R.id.progressbar1);
		text_num_bar_detail = (TextView) findViewById(R.id.text_num_bar_detail);
		text_title1_detailact = (TextView) findViewById(R.id.text_title1_detailact);
		text_title2_detailact = (TextView) findViewById(R.id.text_title2_detailact);
		text_money_detailact = (TextView) findViewById(R.id.text_money_detailact);
		text_innum_detailact = (TextView) findViewById(R.id.text_innum_detailact);
		text_all_detailact = (TextView) findViewById(R.id.text_all_detailact);
		text_surplus_detailact = (TextView) findViewById(R.id.text_surplus_detailact);
		ima_detailact = (ImageView) findViewById(R.id.ima_detailact);
		rela_winner_detailact = (RelativeLayout) findViewById(R.id.rela_winner_detailact);
		text_showNum = (TextView) findViewById(R.id.text_showNum);

		ima_winner_detailact = (ImageView) findViewById(R.id.ima_winner_detailact);
		text_name_detailact = (TextView) findViewById(R.id.text_name_detailact);
		text_winnerfrom_detailact = (TextView) findViewById(R.id.text_winnerfrom_detailact);
		text_endtime_detailact = (TextView) findViewById(R.id.text_endtime_detailact);
		text_buytime_detailact = (TextView) findViewById(R.id.text_buytime_detailact);
		text_luckycode_detailact = (TextView) findViewById(R.id.text_luckycode_detailact);

		recyclerView2 = (RecyclerView) findViewById(R.id.recyclerView2);
		recyclerView2.setHasFixedSize(true);
		LinearLayoutManager lm = new LinearLayoutManager(DetailActivity.this, LinearLayoutManager.HORIZONTAL, false);
		recyclerView2.setLayoutManager(lm);
		reAdapter = new ReAdapter();
		recyclerView2.setAdapter(reAdapter);
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
					String good_Id = numList.get(arg1).getId();
					initData(good_Id);
					goodId = good_Id;
				}
			});

			if (goodId.equals(numList.get(arg1).getId())) {
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
	protected void initEvents() {
		findViewById(R.id.rela_detail_detailact).setOnClickListener(this);
		findViewById(R.id.back).setOnClickListener(this);
		findViewById(R.id.rela_allShow_detailact).setOnClickListener(this);
		findViewById(R.id.rela_allorder_detailact).setOnClickListener(this);
		findViewById(R.id.btn_shopcar_detailact).setOnClickListener(this);
		findViewById(R.id.imageView5).setOnClickListener(this);
		findViewById(R.id.btn_buy_detailact).setOnClickListener(this);
		findViewById(R.id.text_num_bar_detail).setOnClickListener(this);
		findViewById(R.id.rela_winner_detailact).setOnClickListener(this);
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub

	}

}
