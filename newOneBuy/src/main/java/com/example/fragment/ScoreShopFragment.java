package com.example.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.homemodel.AllProduct;
import com.example.homemodel.Category;
import com.example.homemodel.Datum;
import com.example.model.ScoreCity;
import com.example.model.ShoplistNew;
import com.example.model.SignOut;
import com.example.model._0;
import com.example.newonebuy.R;
import com.example.newonebuy.R.color;
import com.example.newonebuy.R.drawable;
import com.example.newonebuy.R.id;
import com.example.newonebuy.R.layout;
import com.example.util.Content;
import com.example.util.GlobalVariable;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.xinbo.utils.GsonUtils;
import com.xinbo.utils.HTTPUtils;
import com.xinbo.utils.UILUtils;
import com.xinbo.utils.VolleyListener;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class ScoreShopFragment extends Fragment implements OnClickListener {

	private View view;
	private ListView lv_left;
	private ListView lv_right;
	LayoutInflater inflater;
	int editPosition;
	private LvLeftAdapter lvLeftAdapter;
	private LvRightAdapter lvRightAdapter;
	private ImageView ima_priceup;
	private RadioButton price_up;
	private ImageView ima_pricedown;
	private RadioButton price_down;
	private RadioButton price_new;
	private RadioButton price_hot;
	ArrayList<Datum> categoryList = new ArrayList<Datum>();
	String category = "0000";

	ArrayList<ShoplistNew> newListData = new ArrayList<ShoplistNew>();
	ArrayList<ShoplistNew> newList = new ArrayList<ShoplistNew>();
	ArrayList<ShoplistNew> faseList = new ArrayList<ShoplistNew>();
	ArrayList<ShoplistNew> hotist = new ArrayList<ShoplistNew>();
	ArrayList<ShoplistNew> priceUpList = new ArrayList<ShoplistNew>();
	ArrayList<ShoplistNew> priceDownList = new ArrayList<ShoplistNew>();

	int mode = 0;
	private String scoreCityCategory;
	private String scoreCityAllProduct;

	String token;
	String uid;
	SharedPreferences sp;
	private PullToRefreshListView mPullRefreshListView;
	private int mPage = 1;
	private RelativeLayout rela_nothing;

	public ScoreShopFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			this.inflater = inflater;
			view = inflater.inflate(R.layout.fragment_score_shop, container, false);

			rela_nothing = (RelativeLayout) view.findViewById(R.id.rela_nothing);

			// 增加筛选栏目
			ima_priceup = (ImageView) view.findViewById(R.id.ima_priceup_header6_homefra);
			ima_pricedown = (ImageView) view.findViewById(R.id.ima_pricedown_header6_homefra);
			price_new = (RadioButton) view.findViewById(R.id.radio0);
			price_hot = (RadioButton) view.findViewById(R.id.radio2);
			price_up = (RadioButton) view.findViewById(R.id.radio3);
			price_down = (RadioButton) view.findViewById(R.id.radio4);
			price_new.setOnClickListener(this);
			price_hot.setOnClickListener(this);
			price_up.setOnClickListener(this);
			price_down.setOnClickListener(this);

			sp = getActivity().getSharedPreferences("token", 0);
			token = sp.getString("token", "");
			uid = sp.getString("uid", "");

			mPullRefreshListView = (PullToRefreshListView) view.findViewById(R.id.ptrlv_allscore);
			// 分别监听上拉下拉事件
			mPullRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

				// 下拉刷新
				@Override
				public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
					new GetDataTask(false).execute();
				}

				// 上拉刷新
				@Override
				public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
					new GetDataTask(true).execute();
				}
			});

			lv_left = (ListView) view.findViewById(R.id.lv1_scoreshopfra);
			lv_right = mPullRefreshListView.getRefreshableView();
			lvLeftAdapter = new LvLeftAdapter();
			lvRightAdapter = new LvRightAdapter();
			lv_left.setAdapter(lvLeftAdapter);

			lv_right.setAdapter(lvRightAdapter);

			lv_left.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					editPosition = arg2;
					lvLeftAdapter.notifyDataSetChanged();

					category = categoryList.get(editPosition).getCateid();
					initData();
				}
			});

			initCateforyBuHttp();
			initData();
		}
		return view;
	}

	private class GetDataTask extends AsyncTask<Void, Void, String[]> {
		boolean isPullUp;

		public GetDataTask(boolean isPullUp) {
			super();
			this.isPullUp = isPullUp;
		}

		@Override
		protected String[] doInBackground(Void... params) {
			// Simulates a background job.
			try {
				Thread.sleep(500);
				refreshData(isPullUp);
			} catch (InterruptedException e) {
			}
			return new String[] { "", "" };
		}

		@Override
		protected void onPostExecute(String[] result) {
			// mPullRefreshListView.onRefreshComplete();
			super.onPostExecute(result);
		}
	}

	private void initAllProduct() {
		price_new.setChecked(true);
		ima_priceup.setImageDrawable(getResources().getDrawable(R.drawable.price_up));
		ima_pricedown.setImageDrawable(getResources().getDrawable(R.drawable.price_down));

		ScoreCity parseJSON = GsonUtils.parseJSON(scoreCityAllProduct, ScoreCity.class);

		List<ShoplistNew> shoplistNew = parseJSON.getShoplistNew();
		newList.clear();
		newListData.clear();
		newList.addAll(shoplistNew);
		newListData.addAll(shoplistNew);
		if (newList.size() == 0) {
			rela_nothing.setVisibility(View.VISIBLE);
		} else {
			rela_nothing.setVisibility(View.GONE);
		}
		lvRightAdapter.notifyDataSetChanged();

		faseList.clear();
		List<ShoplistNew> shoplistFast = parseJSON.getShoplistFast();
		faseList.addAll(shoplistFast);

		hotist.clear();
		List<ShoplistNew> shoplistHot = parseJSON.getShoplistHot();
		hotist.addAll(shoplistHot);

		priceUpList.clear();
		List<ShoplistNew> shoplistPricdesc = parseJSON.getShoplistPricdesc();
		priceUpList.addAll(shoplistPricdesc);

		priceDownList.clear();
		List<ShoplistNew> shoplistPriceasc = parseJSON.getShoplistPriceasc();
		priceDownList.addAll(shoplistPriceasc);

	}

	private void refreshData(final boolean isPullUp) {
		String url;
		if (isPullUp) { // 上拉刷新
			url = Content.URL.SCORECITY + category + "&page=" + mPage;
			Log.e("==", "page" + mPage);
		} else { // 下拉刷新，加载第0页
			url = Content.URL.SCORECITY + category + "&page=" + 0;
			mPage = 1;
		}

		HTTPUtils.get(getActivity(), url, new VolleyListener() {

			@Override
			public void onResponse(String arg0) {
				if (!isPullUp) { // 下拉刷新
					newList.clear();
					newListData.clear();
					faseList.clear();
					hotist.clear();
					priceUpList.clear();
					priceDownList.clear();
				}

				ScoreCity parseJSON = GsonUtils.parseJSON(arg0, ScoreCity.class);
				List<ShoplistNew> shoplistNew = parseJSON.getShoplistNew();
				newListData.addAll(shoplistNew);

				List<ShoplistNew> shoplistFast = parseJSON.getShoplistFast();
				faseList.addAll(shoplistFast);

				List<ShoplistNew> shoplistHot = parseJSON.getShoplistHot();
				hotist.addAll(shoplistHot);

				List<ShoplistNew> shoplistPricdesc = parseJSON.getShoplistPricdesc();
				priceUpList.addAll(shoplistPricdesc);

				List<ShoplistNew> shoplistPriceasc = parseJSON.getShoplistPriceasc();
				priceDownList.addAll(shoplistPriceasc);

				switch (mode) {
				case 0:
					newList.addAll(shoplistNew);
					break;
				case 1:
					newList.addAll(shoplistFast);
					break;
				case 3:
					newList.addAll(shoplistPricdesc);
					break;
				case 4:
					newList.addAll(shoplistPriceasc);
					break;
				default:
					break;
				}
				if (newList.size() == 0) {
					rela_nothing.setVisibility(View.VISIBLE);
				} else {
					rela_nothing.setVisibility(View.GONE);
				}
				lvRightAdapter.notifyDataSetChanged();
				if (isPullUp) { // 上拉刷新
					mPage++;
				}

				mPullRefreshListView.onRefreshComplete();
			}

			@Override
			public void onErrorResponse(VolleyError arg0) {

			}
		});

	}

	private void initData() {
		price_new.setChecked(true);
		ima_priceup.setImageDrawable(getResources().getDrawable(R.drawable.price_up));
		ima_pricedown.setImageDrawable(getResources().getDrawable(R.drawable.price_down));

		String url = Content.URL.SCORECITY + category;

		HTTPUtils.get(getActivity(), url, new VolleyListener() {

			@Override
			public void onResponse(String arg0) {

				ScoreCity parseJSON = GsonUtils.parseJSON(arg0, ScoreCity.class);

				List<ShoplistNew> shoplistNew = parseJSON.getShoplistNew();
				newList.clear();
				newListData.clear();
				newList.addAll(shoplistNew);
				newListData.addAll(shoplistNew);
				if (newList.size() == 0) {
					rela_nothing.setVisibility(View.VISIBLE);
				} else {
					rela_nothing.setVisibility(View.GONE);
				}
				lvRightAdapter.notifyDataSetChanged();

				faseList.clear();
				List<ShoplistNew> shoplistFast = parseJSON.getShoplistFast();
				faseList.addAll(shoplistFast);

				hotist.clear();
				List<ShoplistNew> shoplistHot = parseJSON.getShoplistHot();
				hotist.addAll(shoplistHot);

				priceUpList.clear();
				List<ShoplistNew> shoplistPricdesc = parseJSON.getShoplistPricdesc();
				priceUpList.addAll(shoplistPricdesc);

				priceDownList.clear();
				List<ShoplistNew> shoplistPriceasc = parseJSON.getShoplistPriceasc();
				priceDownList.addAll(shoplistPriceasc);

			}

			@Override
			public void onErrorResponse(VolleyError arg0) {

			}
		});
	}

	private void initCategory() {
		Category parseJSON = GsonUtils.parseJSON(scoreCityCategory, Category.class);
		List<Datum> get7 = parseJSON.getData();
		categoryList.addAll(get7);
		lvLeftAdapter.notifyDataSetChanged();
	}

	public void initCateforyBuHttp() {
		String url = Content.URL.CATEGORY;
		HTTPUtils.get(getActivity(), url, new VolleyListener() {

			@Override
			public void onResponse(String arg0) {
				Category parseJSON = GsonUtils.parseJSON(arg0, Category.class);
				List<Datum> get7 = parseJSON.getData();
				categoryList.addAll(get7);
				// 积分商城要去掉全国推荐-百元专区
				for (int i = 0; i < 4; i++) {
					categoryList.remove(1);
				}
				lvLeftAdapter.notifyDataSetChanged();
			}

			@Override
			public void onErrorResponse(VolleyError arg0) {

			}
		});
	}

	class LvLeftAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return categoryList.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@SuppressLint("NewApi")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = inflater.inflate(R.layout.item_lvleft_allproductfra, null);
			TextView text_title = (TextView) view.findViewById(R.id.text_title_itemlvleft_allfra);
			text_title.setText(categoryList.get(position).getCatename());
			if (position == editPosition) {
				view.setBackground(getResources().getDrawable(R.drawable.ligntgray2));
				text_title.setTextColor(getResources().getColor(R.color.onebuy));
			}
			return view;
		}
	}

	class LvRightAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			if (newList.size() == 0) {
				return 0;
			}
			if ((newList.size() % 2) == 0) { // 偶数
				return (newList.size() / 2);
			} else {
				return (newList.size() / 2) + 1;
			}
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view;
			ImageView ima_gvitem_homefra;
			TextView text_name_gvitem_homefra;
			TextView text_allneed_gvitem_homefra;
			TextView text_score_scorecity;
			ImageView ima_gvitem_homefra2;
			TextView text_name_gvitem_homefra2;
			TextView text_allneed_gvitem_homefra2;
			TextView text_score_scorecity2;
			Button btn_exchange_scorefra;
			Button btn_exchange_scorefra2;

			RelativeLayout rela_nothing_scorecity;
			RelativeLayout rela_hasthing_scorecity;

			if (convertView == null) {
				view = inflater.inflate(R.layout.item_lvright_scorefra, null);
				ima_gvitem_homefra = (ImageView) view.findViewById(R.id.ima_gvitem_homefra);
				text_name_gvitem_homefra = (TextView) view.findViewById(R.id.text_name_gvitem_homefra);
				text_allneed_gvitem_homefra = (TextView) view.findViewById(R.id.text_allneed_gvitem_homefra);
				text_score_scorecity = (TextView) view.findViewById(R.id.text_score_scorecity);
				btn_exchange_scorefra = (Button) view.findViewById(R.id.btn_exchange_scorefra);

				ima_gvitem_homefra2 = (ImageView) view.findViewById(R.id.ima_gvitem_homefra2);
				text_name_gvitem_homefra2 = (TextView) view.findViewById(R.id.text_name_gvitem_homefra2);
				text_allneed_gvitem_homefra2 = (TextView) view.findViewById(R.id.text_allneed_gvitem_homefra2);
				text_score_scorecity2 = (TextView) view.findViewById(R.id.text_score_scorecity2);
				btn_exchange_scorefra2 = (Button) view.findViewById(R.id.btn_exchange_scorefra2);

				rela_nothing_scorecity = (RelativeLayout) view.findViewById(R.id.rela_nothing_scorecity);
				rela_hasthing_scorecity = (RelativeLayout) view.findViewById(R.id.rela_hasthing_scorecity);
				view.setTag(new MyTag(ima_gvitem_homefra, text_name_gvitem_homefra, text_allneed_gvitem_homefra,
						text_score_scorecity, ima_gvitem_homefra2, text_name_gvitem_homefra2,
						text_allneed_gvitem_homefra2, text_score_scorecity2, btn_exchange_scorefra,
						btn_exchange_scorefra2, rela_nothing_scorecity, rela_hasthing_scorecity));
			} else {
				view = convertView;
				MyTag tag = (MyTag) view.getTag();
				ima_gvitem_homefra = tag.ima_gvitem_homefra;
				text_name_gvitem_homefra = tag.text_name_gvitem_homefra;
				text_allneed_gvitem_homefra = tag.text_allneed_gvitem_homefra;
				text_score_scorecity = tag.text_score_scorecity;
				ima_gvitem_homefra2 = tag.ima_gvitem_homefra2;
				text_name_gvitem_homefra2 = tag.text_name_gvitem_homefra2;
				text_allneed_gvitem_homefra2 = tag.text_allneed_gvitem_homefra2;
				text_score_scorecity2 = tag.text_score_scorecity2;
				rela_hasthing_scorecity = tag.rela_hasthing_scorecity;
				rela_nothing_scorecity = tag.rela_nothing_scorecity;
				btn_exchange_scorefra = tag.btn_exchange_scorefra;
				btn_exchange_scorefra2 = tag.btn_exchange_scorefra2;
			}

			// 设置左边的
			ShoplistNew areaFast1;
			ShoplistNew areaFast2;

			// 如果为奇数且为最后一行，则右边为空，隐藏
			if (position == (newList.size() / 2) && ((newList.size() % 2) != 0)) { // 最后一行和奇数
				rela_nothing_scorecity.setVisibility(View.VISIBLE);
				rela_hasthing_scorecity.setVisibility(View.GONE);
				areaFast2 = newList.get(0);
			} else {
				rela_nothing_scorecity.setVisibility(View.GONE);
				rela_hasthing_scorecity.setVisibility(View.VISIBLE);

			}

			if (position == 0) { // 1行
				areaFast1 = newList.get(0);
				if ((newList.size() % 2) == 0) { // 偶数，例如2个，则右边是get（1）
					areaFast2 = newList.get(1);
				} else { // 个数为1，右边为空，取左边防止空指针
					if (newList.size() == 1) {
						areaFast2 = newList.get(0);
					} else {
						areaFast2 = newList.get(1);
					}
				}
			} else { // >1行
				areaFast1 = newList.get(position * 2);
				if ((newList.size() % 2) == 0) { // 偶数，例如4个，2行，则右边是get（3）
					areaFast2 = newList.get((position * 2) + 1);
				} else { // 奇数
					if (position == (newList.size() / 2)) { // 最后一行右边为空，最后一行取左边防止空指针
						areaFast2 = newList.get(position * 2);
					} else {
						areaFast2 = newList.get((position * 2) + 1);
					}
				}
			}

			final String goods1 = areaFast1.getId();
			final String goods2 = areaFast2.getId();

			GlobalVariable.displayImageNoAnim(Content.URL.IMA_HOST + areaFast1.getGoodsImg(), ima_gvitem_homefra);
			text_name_gvitem_homefra.setText(areaFast1.getTitle());
			text_allneed_gvitem_homefra.setText("￥" + areaFast1.getGoodsNum());
			text_score_scorecity.setText(areaFast1.getGoodsJifen());

			GlobalVariable.displayImageNoAnim(Content.URL.IMA_HOST + areaFast2.getGoodsImg(), ima_gvitem_homefra2);
			text_name_gvitem_homefra2.setText(areaFast2.getTitle());
			text_allneed_gvitem_homefra2.setText("￥" + areaFast2.getGoodsNum());
			text_score_scorecity2.setText(areaFast2.getGoodsJifen());

			btn_exchange_scorefra.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					scoreChange(goods1);
				}
			});

			btn_exchange_scorefra2.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					scoreChange(goods2);
				}
			});

			return view;
		}
	}

	public void scoreChange(String goodsId) {
		Log.e("==", token);
		if (token.isEmpty()) {
			Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
			return;
		}
		String url = Content.URL.SCORECHANGE + token + "&uid=" + uid + "&goods_id=" + goodsId;
		HTTPUtils.get(getActivity(), url, new VolleyListener() {

			@Override
			public void onResponse(String arg0) {
				SignOut parseJSON = GsonUtils.parseJSON(arg0, SignOut.class);
				Integer tag = parseJSON.getTag();
				if (tag == 1) {
					Toast.makeText(getActivity(), parseJSON.getMessage(), Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getActivity(), parseJSON.getMessage(), Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void onErrorResponse(VolleyError arg0) {

			}
		});
	}

	class MyTag {
		ImageView ima_gvitem_homefra;
		TextView text_name_gvitem_homefra;
		TextView text_allneed_gvitem_homefra;
		TextView text_score_scorecity;
		ImageView ima_gvitem_homefra2;
		TextView text_name_gvitem_homefra2;
		TextView text_allneed_gvitem_homefra2;
		TextView text_score_scorecity2;
		Button btn_exchange_scorefra;
		Button btn_exchange_scorefra2;

		RelativeLayout rela_nothing_scorecity;
		RelativeLayout rela_hasthing_scorecity;

		public MyTag(ImageView ima_gvitem_homefra, TextView text_name_gvitem_homefra,
				TextView text_allneed_gvitem_homefra, TextView text_score_scorecity, ImageView ima_gvitem_homefra2,
				TextView text_name_gvitem_homefra2, TextView text_allneed_gvitem_homefra2,
				TextView text_score_scorecity2, Button btn_exchange_scorefra, Button btn_exchange_scorefra2,
				RelativeLayout rela_nothing_scorecity, RelativeLayout rela_hasthing_scorecity) {
			super();
			this.ima_gvitem_homefra = ima_gvitem_homefra;
			this.text_name_gvitem_homefra = text_name_gvitem_homefra;
			this.text_allneed_gvitem_homefra = text_allneed_gvitem_homefra;
			this.text_score_scorecity = text_score_scorecity;
			this.ima_gvitem_homefra2 = ima_gvitem_homefra2;
			this.text_name_gvitem_homefra2 = text_name_gvitem_homefra2;
			this.text_allneed_gvitem_homefra2 = text_allneed_gvitem_homefra2;
			this.text_score_scorecity2 = text_score_scorecity2;
			this.btn_exchange_scorefra = btn_exchange_scorefra;
			this.btn_exchange_scorefra2 = btn_exchange_scorefra2;
			this.rela_nothing_scorecity = rela_nothing_scorecity;
			this.rela_hasthing_scorecity = rela_hasthing_scorecity;
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.radio0:
			ima_priceup.setImageDrawable(getResources().getDrawable(R.drawable.price_up));
			ima_pricedown.setImageDrawable(getResources().getDrawable(R.drawable.price_down));

			if (mode != 0) {
				mode = 0;
				newList.clear();
				newList.addAll(newListData);
				if (newList.size() == 0) {
					rela_nothing.setVisibility(View.VISIBLE);
				} else {
					rela_nothing.setVisibility(View.GONE);
				}
				lvRightAdapter.notifyDataSetChanged();
			}
			break;
		case R.id.radio2:
			ima_priceup.setImageDrawable(getResources().getDrawable(R.drawable.price_up));
			ima_pricedown.setImageDrawable(getResources().getDrawable(R.drawable.price_down));

			if (mode != 1) {
				mode = 1;
				newList.clear();
				newList.addAll(hotist);
				if (newList.size() == 0) {
					rela_nothing.setVisibility(View.VISIBLE);
				} else {
					rela_nothing.setVisibility(View.GONE);
				}
				lvRightAdapter.notifyDataSetChanged();
			}
			break;
		case R.id.radio3:
			if (price_up.isChecked()) {
				ima_priceup.setImageDrawable(getResources().getDrawable(R.drawable.price_up_selected));
				ima_pricedown.setImageDrawable(getResources().getDrawable(R.drawable.price_down));
			} else {
				ima_priceup.setImageDrawable(getResources().getDrawable(R.drawable.price_up));
				ima_pricedown.setImageDrawable(getResources().getDrawable(R.drawable.price_down_selected));
			}

			if (mode != 3) {
				mode = 3;
				newList.clear();
				newList.addAll(priceUpList);
				if (newList.size() == 0) {
					rela_nothing.setVisibility(View.VISIBLE);
				} else {
					rela_nothing.setVisibility(View.GONE);
				}
				lvRightAdapter.notifyDataSetChanged();
			}
			break;
		case R.id.radio4:
			if (price_down.isChecked()) {
				ima_priceup.setImageDrawable(getResources().getDrawable(R.drawable.price_up));
				ima_pricedown.setImageDrawable(getResources().getDrawable(R.drawable.price_down_selected));
			} else {
				ima_priceup.setImageDrawable(getResources().getDrawable(R.drawable.price_up_selected));
				ima_pricedown.setImageDrawable(getResources().getDrawable(R.drawable.price_down));
			}

			if (mode != 4) {
				mode = 4;
				newList.clear();
				newList.addAll(priceDownList);
				if (newList.size() == 0) {
					rela_nothing.setVisibility(View.VISIBLE);
				} else {
					rela_nothing.setVisibility(View.GONE);
				}
				lvRightAdapter.notifyDataSetChanged();
			}
			break;
		default:
			break;
		}
	}
}
