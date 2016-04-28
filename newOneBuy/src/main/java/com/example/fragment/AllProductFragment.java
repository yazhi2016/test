package com.example.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.homemodel.AllProduct;
import com.example.homemodel.Category;
import com.example.homemodel.Datum;
import com.example.homemodel.ShoplistNew;
import com.example.model.SignOut;
import com.example.newonebuy.R;
import com.example.onebuy.DetailActivity;
import com.example.onebuy.LogInActivity;
import com.example.onebuy.SearchActivity;
import com.example.util.Content;
import com.example.util.GlobalVariable;
import com.example.util.MyShop;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.xinbo.utils.GsonUtils;
import com.xinbo.utils.HTTPUtils;
import com.xinbo.utils.VolleyListener;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 *
 */
@SuppressLint("ViewHolder")
public class AllProductFragment extends Fragment implements OnClickListener {

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
	private RadioButton price_fast;
	private RadioButton price_hot;
	private RelativeLayout bar;
	private TextView text_bar;
	// private PopupWindow popupWindow;
	private LvPopAdapter lvPopAdapter;
	private TextView text_leftHenxian_popsearch;
	private TextView text_rightHenxian_popsearch;
	private GridView gv;
	private GvPopAdapter gvPopAdapter;
	private RelativeLayout rela_hot_popsearch;
	private RelativeLayout rela_new_popsearch;
	private RelativeLayout rela_back;
	ArrayList<Datum> categoryList = new ArrayList<Datum>();
	String category = "0000";

	ArrayList<ShoplistNew> newListData = new ArrayList<ShoplistNew>();
	ArrayList<ShoplistNew> newList = new ArrayList<ShoplistNew>();
	ArrayList<ShoplistNew> faseList = new ArrayList<ShoplistNew>();
	ArrayList<ShoplistNew> hotist = new ArrayList<ShoplistNew>();
	ArrayList<ShoplistNew> priceUpList = new ArrayList<ShoplistNew>();
	ArrayList<ShoplistNew> priceDownList = new ArrayList<ShoplistNew>();

	int mode = 0;
	private PullToRefreshListView mPullRefreshListView;

	private int mPage = 1;
	private Bundle arguments;
	private int bundle_position;

	boolean isZero = false;
	private String token;
	private String uid;
	private RelativeLayout rela_nothing;

	public AllProductFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			this.inflater = inflater;
			arguments = getArguments();
			initUI(inflater, container);
		}
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		bundle_position = arguments.getInt("tabhost");
		if (bundle_position == 44) { // 地区推荐
			editPosition = 0;
		} else {
			editPosition = bundle_position;
		}
		// category = categoryList.get(editPosition).getCateid();
		initData();
		initCategoryData();

		GlobalVariable instance = GlobalVariable.getInstance();
		token = instance.getSpToken();
		uid = instance.getSpUid();
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

	private void initUI(LayoutInflater inflater, ViewGroup container) {
		view = inflater.inflate(R.layout.fragment_all_product, container, false);

		rela_nothing = (RelativeLayout) view.findViewById(R.id.rela_nothing);
		
		// 增加筛选栏目
		ima_priceup = (ImageView) view.findViewById(R.id.ima_priceup_header6_homefra);
		ima_pricedown = (ImageView) view.findViewById(R.id.ima_pricedown_header6_homefra);
		price_new = (RadioButton) view.findViewById(R.id.radio0);
		price_fast = (RadioButton) view.findViewById(R.id.radio1);
		price_hot = (RadioButton) view.findViewById(R.id.radio2);
		price_up = (RadioButton) view.findViewById(R.id.radio3);
		price_down = (RadioButton) view.findViewById(R.id.radio4);
		price_new.setOnClickListener(this);
		price_fast.setOnClickListener(this);
		price_hot.setOnClickListener(this);
		price_up.setOnClickListener(this);
		price_down.setOnClickListener(this);

		mPullRefreshListView = (PullToRefreshListView) view.findViewById(R.id.ptrlv_allproduce);
		lv_right = mPullRefreshListView.getRefreshableView();

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

		view.findViewById(R.id.search).setOnClickListener(this);
		bar = (RelativeLayout) view.findViewById(R.id.bar);
		lv_left = (ListView) view.findViewById(R.id.lv1_allprofra);
		lvLeftAdapter = new LvLeftAdapter();
		lvRightAdapter = new LvRightAdapter();
		lv_left.setAdapter(lvLeftAdapter);
		text_bar = (TextView) view.findViewById(R.id.text_bar_allproductfra);

		lv_right.setAdapter(lvRightAdapter);

		lv_left.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				bundle_position = 0;
				editPosition = arg2;
				if (categoryList.get(arg2).getCateid().equals("2222")) { // 如果点击0元专区
					isZero = true;
				} else {
					isZero = false;
				}
				lvLeftAdapter.notifyDataSetChanged();
				category = categoryList.get(editPosition).getCateid();
				initData();
			}
		});

		price_new.setOnClickListener(this);

		// 搜索pop
		View pop = inflater.inflate(R.layout.pop_search, null);
		ListView lv = (ListView) pop.findViewById(R.id.lv_popsearch);
		pop.findViewById(R.id.check_new).setOnClickListener(this);
		pop.findViewById(R.id.check_hot).setOnClickListener(this);
		text_leftHenxian_popsearch = (TextView) pop.findViewById(R.id.text_leftHenxian_popsearch);
		text_rightHenxian_popsearch = (TextView) pop.findViewById(R.id.text_rightHenxian_popsearch);
		rela_hot_popsearch = (RelativeLayout) pop.findViewById(R.id.rela_hot_popsearch);
		rela_new_popsearch = (RelativeLayout) pop.findViewById(R.id.rela_new_popsearch);

		gv = (GridView) pop.findViewById(R.id.gv_popsearch);
		gvPopAdapter = new GvPopAdapter();
		gv.setAdapter(gvPopAdapter);

		lvPopAdapter = new LvPopAdapter();
		lv.setAdapter(lvPopAdapter);

		// popupWindow = new PopupWindow(pop, LayoutParams.MATCH_PARENT,
		// LayoutParams.MATCH_PARENT, true);
		// BitmapDrawable b = new BitmapDrawable();
		// popupWindow.setBackgroundDrawable(b);
		// popupWindow.setOnDismissListener(new OnDismissListener() {
		//
		// @Override
		// public void onDismiss() {
		// text_bar.setText("所有商品");
		// rela_back.setVisibility(View.INVISIBLE);
		// }
		// });

		rela_back = (RelativeLayout) view.findViewById(R.id.back);

		// initCategoryData();
		// initData();
	}

	public void initCategoryData() { // 网络请求数据更新列表
		String url = Content.URL.CATEGORY;
		HTTPUtils.get(getActivity(), url, new VolleyListener() {

			@Override
			public void onResponse(String arg0) {
				categoryList.clear();
				if (bundle_position == 44) {
					categoryList.add(new Datum(category, "地区推荐"));
				}
				Category parseJSON = GsonUtils.parseJSON(arg0, Category.class);
				List<Datum> data = parseJSON.getData();
				categoryList.addAll(data);
				lvLeftAdapter.notifyDataSetChanged();
			}

			@Override
			public void onErrorResponse(VolleyError arg0) {

			}
		});
	}

	public void initData() {
		price_new.setChecked(true);
		ima_priceup.setImageDrawable(getResources().getDrawable(R.drawable.price_up));
		ima_pricedown.setImageDrawable(getResources().getDrawable(R.drawable.price_down));

		if (bundle_position == 3) { // 十元
			category = "3333";
		} else if (bundle_position == 4) { // 百元
			category = "4444";
		} else if (bundle_position == 1) { // 全国
			category = "7777";
		} else if (bundle_position == 44) { // 地区推荐
			String myCity = GlobalVariable.getInstance().getSpValue("city", "龙岩");
			String encode = null;
			try {
				encode = URLEncoder.encode(myCity, "utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			category = "6666&city=" + encode;
			editPosition = 0;
		}

		String url;
		url = Content.URL.ALLPRODUCT + category + "&page=" + 0;
		mPage = 1;
		HTTPUtils.get(getActivity(), url, new VolleyListener() {

			@Override
			public void onResponse(String arg0) {
				Spanned fromHtml = Html.fromHtml(arg0);

				newList.clear();
				newListData.clear();
				faseList.clear();
				hotist.clear();
				priceUpList.clear();
				priceDownList.clear();

				AllProduct parseJSON = GsonUtils.parseJSON(arg0, AllProduct.class);
				List<ShoplistNew> shoplistNew = parseJSON.getShoplistNew();
				newList.addAll(shoplistNew);
				newListData.addAll(shoplistNew);
				if(newList.size() == 0) {
					rela_nothing.setVisibility(View.VISIBLE);
				} else {
					rela_nothing.setVisibility(View.GONE);
				}
				lvRightAdapter.notifyDataSetChanged();

				List<ShoplistNew> shoplistFast = parseJSON.getShoplistFast();
				faseList.addAll(shoplistFast);

				List<ShoplistNew> shoplistHot = parseJSON.getShoplistHot();
				hotist.addAll(shoplistHot);

				List<ShoplistNew> shoplistPricdesc = parseJSON.getShoplistPricdesc();
				priceUpList.addAll(shoplistPricdesc);

				List<ShoplistNew> shoplistPriceasc = parseJSON.getShoplistPriceasc();
				priceDownList.addAll(shoplistPriceasc);
			}

			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void refreshData(final boolean isPullUp) {
		String url;
		if (isPullUp) { // 上拉刷新
			url = Content.URL.ALLPRODUCT + category + "&page=" + mPage;
			Log.e("==", "page" + mPage);
		} else { // 下拉刷新，加载第0页
			url = Content.URL.ALLPRODUCT + category + "&page=" + 0;
			mPage = 1;
		}
		HTTPUtils.get(getActivity(), url, new VolleyListener() {

			@Override
			public void onResponse(String arg0) {

				Spanned fromHtml = Html.fromHtml(arg0);

				if (!isPullUp) { // 下拉刷新
					newList.clear();
					newListData.clear();
					faseList.clear();
					hotist.clear();
					priceUpList.clear();
					priceDownList.clear();
				}

				AllProduct parseJSON = GsonUtils.parseJSON(fromHtml + "", AllProduct.class);
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
				case 2:
					newList.addAll(shoplistHot);
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
				
				if(newList.size() == 0) {
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
				// TODO Auto-generated method stub

			}
		});
	}

	class LvPopAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return 3;
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
			View view = inflater.inflate(R.layout.iten_lv_popsearch, null);
			return view;
		}

	}

	class GvPopAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return 15;
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
			View view = inflater.inflate(R.layout.item_gv_popsearch, null);
			return view;
		}

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
			ViewHolder viewHolder;
			if (convertView == null) {
				view = inflater.inflate(R.layout.item_lvright_allprofra, null);

				viewHolder = new ViewHolder();
				viewHolder.rela_nothing_allproductact = (RelativeLayout) view
						.findViewById(R.id.rela_nothing_allproductact);
				viewHolder.rela_hasthing_allproductact = (RelativeLayout) view
						.findViewById(R.id.rela_hasthing_allproductact);
				viewHolder.ima_gvitem_homefra = (ImageView) view.findViewById(R.id.ima_gvitem_homefra);
				viewHolder.text_name_gvitem_homefra = (TextView) view.findViewById(R.id.text_name_gvitem_homefra);
				viewHolder.textView1 = (TextView) view.findViewById(R.id.text_surplus_allprodect);
				viewHolder.progress_now1 = (ProgressBar) view.findViewById(R.id.progressbar_allproduct);
				viewHolder.ima_gvitem_homefra2 = (ImageView) view.findViewById(R.id.ima_gvitem_homefra2);
				viewHolder.text_name_gvitem_homefra2 = (TextView) view.findViewById(R.id.text_name_gvitem_homefra2);
				viewHolder.textView2 = (TextView) view.findViewById(R.id.text_surplus_allprodect2);
				viewHolder.progress_now2 = (ProgressBar) view.findViewById(R.id.progressbar_allproduct2);

				viewHolder.ima_tagten_homefra = (ImageView) view.findViewById(R.id.ima_tagten_homefra);
				viewHolder.ima_taghundred_homefra = (ImageView) view.findViewById(R.id.ima_taghundred_homefra);
				viewHolder.ima_tagten_homefra2 = (ImageView) view.findViewById(R.id.ima_tagten_homefra2);
				viewHolder.ima_taghundred_homefra2 = (ImageView) view.findViewById(R.id.ima_taghundred_homefra2);

				viewHolder.btn_zero = (Button) view.findViewById(R.id.btn_zero);
				viewHolder.btn_zero2 = (Button) view.findViewById(R.id.btn_zero2);
				viewHolder.ima_addtoshopcar = (ImageView) view.findViewById(R.id.ima_addtoshopcar);
				viewHolder.ima_addtoshopcar2 = (ImageView) view.findViewById(R.id.ima_addtoshopcar2);

				view.setTag(viewHolder);

			} else {
				view = convertView;

				viewHolder = (ViewHolder) view.getTag();

			}
			viewHolder.ima_gvitem_homefra2.setTag(position);

			// 设置左边的
			ShoplistNew areaFast1;
			ShoplistNew areaFast2;

			// 如果为奇数且为最后一行，则右边为空，隐藏
			if (position == (newList.size() / 2) && ((newList.size() % 2) != 0)) { // 最后一行和奇数
				viewHolder.rela_nothing_allproductact.setVisibility(View.VISIBLE);
				viewHolder.rela_hasthing_allproductact.setVisibility(View.GONE);
				areaFast2 = newList.get(0);
			} else {
				viewHolder.rela_nothing_allproductact.setVisibility(View.GONE);
				viewHolder.rela_hasthing_allproductact.setVisibility(View.VISIBLE);

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

			// 十元特区，百元特区旗子是否出现
			if ((int) Double.parseDouble(areaFast1.getYunjiage()) == 10) {
				viewHolder.ima_tagten_homefra.setVisibility(View.VISIBLE);
				viewHolder.ima_taghundred_homefra.setVisibility(View.INVISIBLE);
			} else if ((int) Double.parseDouble(areaFast1.getYunjiage()) == 100) {
				viewHolder.ima_tagten_homefra.setVisibility(View.INVISIBLE);
				viewHolder.ima_taghundred_homefra.setVisibility(View.VISIBLE);
			} else {
				viewHolder.ima_tagten_homefra.setVisibility(View.INVISIBLE);
				viewHolder.ima_taghundred_homefra.setVisibility(View.INVISIBLE);
			}
			if ((int) Double.parseDouble(areaFast2.getYunjiage()) == 10) {
				viewHolder.ima_tagten_homefra2.setVisibility(View.VISIBLE);
				viewHolder.ima_taghundred_homefra2.setVisibility(View.GONE);
			} else if ((int) Double.parseDouble(areaFast2.getYunjiage()) == 100) {
				viewHolder.ima_tagten_homefra2.setVisibility(View.GONE);
				viewHolder.ima_taghundred_homefra2.setVisibility(View.VISIBLE);
			} else {
				viewHolder.ima_tagten_homefra2.setVisibility(View.GONE);
				viewHolder.ima_taghundred_homefra2.setVisibility(View.GONE);
			}

			viewHolder.text_name_gvitem_homefra.setText("(第" + areaFast1.getQishu() + "期)" + areaFast1.getTitle());

			int stringDivide = 0;
			// if (isZero) {
			stringDivide = Content.StringDivide2(areaFast1.getShenyurenshu(), areaFast1.getZongrenshu());
			// } else {
			// stringDivide = Content.StringDivide(areaFast1.getCanyurenshu(),
			// areaFast1.getZongrenshu());
			// }
			viewHolder.progress_now1.setProgress(stringDivide);
			viewHolder.textView1.setText(stringDivide + "%");

			GlobalVariable.displayImageNoAnim(Content.URL.IMA_HOST + areaFast2.getThumb(),
					viewHolder.ima_gvitem_homefra2);
			GlobalVariable.displayImageNoAnim(Content.URL.IMA_HOST + areaFast1.getThumb(),
					viewHolder.ima_gvitem_homefra);

			viewHolder.text_name_gvitem_homefra2.setText("(第" + areaFast2.getQishu() + "期)" + areaFast2.getTitle());

			int stringDivide2 = 0;
			// if (isZero) {
			stringDivide2 = Content.StringDivide2(areaFast2.getShenyurenshu(), areaFast2.getZongrenshu());
			// } else {
			// stringDivide2 = Content.StringDivide(areaFast2.getCanyurenshu(),
			// areaFast2.getZongrenshu());
			// }
			viewHolder.progress_now2.setProgress(stringDivide2);
			viewHolder.textView2.setText(stringDivide2 + "%");

			final String id1 = areaFast1.getId();
			final String id2 = areaFast2.getId();

			view.findViewById(R.id.ima_gvitem_homefra).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (!isZero) {
						startDetailAct(id1);
					}
				}
			});
			view.findViewById(R.id.ima_gvitem_homefra2).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (!isZero) {
						startDetailAct(id2);
					}
				}
			});

			if (isZero) {
				viewHolder.ima_addtoshopcar.setVisibility(View.GONE);
				viewHolder.ima_addtoshopcar2.setVisibility(View.GONE);
				viewHolder.btn_zero.setVisibility(View.VISIBLE);
				viewHolder.btn_zero2.setVisibility(View.VISIBLE);
			} else {
				viewHolder.ima_addtoshopcar.setVisibility(View.VISIBLE);
				viewHolder.ima_addtoshopcar2.setVisibility(View.VISIBLE);
				viewHolder.btn_zero.setVisibility(View.GONE);
				viewHolder.btn_zero2.setVisibility(View.GONE);
			}
			// 0元专区立刻抢购
			viewHolder.btn_zero.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					String url = Content.URL.BUGZERO + token + "&uid=" + uid + "&goods_id=" + id1;
					HTTPUtils.get(getActivity(), url, new VolleyListener() {

						@Override
						public void onResponse(String arg0) {
							SignOut parseJSON = GsonUtils.parseJSON(arg0, SignOut.class);
							if (parseJSON.getTag() == 1) {
								Toast.makeText(getActivity(), parseJSON.getMessage(), Toast.LENGTH_SHORT).show();
							} else {
								if (parseJSON.getMessage().equals("参数缺少")) {
									Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
									Intent intent = new Intent(getActivity(), LogInActivity.class);
									intent.putExtra("state", "safe");
									startActivity(intent);
								} else {
									Toast.makeText(getActivity(), parseJSON.getMessage(), Toast.LENGTH_SHORT).show();
								}
							}
						}

						@Override
						public void onErrorResponse(VolleyError arg0) {

						}
					});
				}
			});
			viewHolder.btn_zero2.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					String url = Content.URL.BUGZERO + token + "&uid=" + uid + "&goods_id=" + id2;
					HTTPUtils.get(getActivity(), url, new VolleyListener() {

						@Override
						public void onResponse(String arg0) {
							SignOut parseJSON = GsonUtils.parseJSON(arg0, SignOut.class);
							Toast.makeText(getActivity(), parseJSON.getMessage(), Toast.LENGTH_SHORT).show();
						}

						@Override
						public void onErrorResponse(VolleyError arg0) {

						}
					});
				}
			});

			final ShoplistNew areaFast11 = areaFast1;
			final ShoplistNew areaFast21 = areaFast2;

			// 添加到购物车
			viewHolder.ima_addtoshopcar.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					List<MyShop> dataList = new Select().from(MyShop.class).queryList();

					for (int i = 0; i < dataList.size(); i++) {
						String goodid = dataList.get(i).getGoodid();
						if (id1.equals(goodid)) {
							Toast.makeText(getActivity(), "购物车已有该商品", Toast.LENGTH_SHORT).show();
							return;
						}
					}
					Content.addShop(areaFast11.getId(), Content.URL.IMA_HOST + areaFast11.getThumb(),
							areaFast11.getQishu(), "1", areaFast11.getTitle(), areaFast11.getShenyurenshu(),
							areaFast11.getYunjiage(), areaFast11.getYunjiage(), false);
					Toast.makeText(getActivity(), "已添加到购物车", Toast.LENGTH_SHORT).show();
				}
			});
			viewHolder.ima_addtoshopcar2.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					List<MyShop> dataList = new Select().from(MyShop.class).queryList();
					for (int i = 0; i < dataList.size(); i++) {
						String goodid = dataList.get(i).getGoodid();
						if (id2.equals(goodid)) {
							Toast.makeText(getActivity(), "购物车已有该商品", Toast.LENGTH_SHORT).show();
							return;
						}
					}
					Content.addShop(areaFast21.getId(), Content.URL.IMA_HOST + areaFast21.getThumb(),
							areaFast21.getQishu(), "1", areaFast21.getTitle(), areaFast21.getShenyurenshu(),
							areaFast21.getYunjiage(), areaFast21.getYunjiage(), false);
					Toast.makeText(getActivity(), "已添加到购物车", Toast.LENGTH_SHORT).show();

				}
			});

			return view;
		}

	}

	public void startDetailAct(String id) {
		Intent intent = new Intent(getActivity(), DetailActivity.class);
		intent.putExtra("id", id);
		startActivity(intent);
	}

	class ViewHolder {
		public ImageView ima_tagten_homefra;
		public ImageView ima_taghundred_homefra;
		public ImageView ima_tagten_homefra2;
		public ImageView ima_taghundred_homefra2;
		public ImageView ima_addtoshopcar;
		public ImageView ima_addtoshopcar2;

		ImageView ima_gvitem_homefra;
		TextView text_name_gvitem_homefra;
		TextView textView1;
		ProgressBar progress_now1;
		ImageView ima_gvitem_homefra2;
		TextView text_name_gvitem_homefra2;
		TextView textView2;
		ProgressBar progress_now2;
		RelativeLayout rela_nothing_allproductact;
		RelativeLayout rela_hasthing_allproductact;

		Button btn_zero;
		Button btn_zero2;

	}

	class MyTag {
		ImageView ima_gvitem_homefra;
		TextView text_name_gvitem_homefra;
		TextView textView1;
		ProgressBar progress_now1;
		ImageView ima_gvitem_homefra2;
		TextView text_name_gvitem_homefra2;
		TextView textView2;
		ProgressBar progress_now2;
		RelativeLayout rela_nothing_allproductact;
		RelativeLayout rela_hasthing_allproductact;

		public MyTag(ImageView ima_gvitem_homefra, TextView text_name_gvitem_homefra, TextView textView1,
				ProgressBar progress_now1, ImageView ima_gvitem_homefra2, TextView text_name_gvitem_homefra2,
				TextView textView2, ProgressBar progress_now2, RelativeLayout rela_nothing_allproductact,
				RelativeLayout rela_hasthing_allproductact) {
			super();
			this.ima_gvitem_homefra = ima_gvitem_homefra;
			this.text_name_gvitem_homefra = text_name_gvitem_homefra;
			this.textView1 = textView1;
			this.progress_now1 = progress_now1;
			this.ima_gvitem_homefra2 = ima_gvitem_homefra2;
			this.text_name_gvitem_homefra2 = text_name_gvitem_homefra2;
			this.textView2 = textView2;
			this.progress_now2 = progress_now2;
			this.rela_nothing_allproductact = rela_nothing_allproductact;
			this.rela_hasthing_allproductact = rela_hasthing_allproductact;
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.search: // 点击顶栏搜索
			// popupWindow.showAsDropDown(bar);
			// text_bar.setText("搜索");
			// rela_back.setVisibility(View.VISIBLE);

			startActivity(new Intent(getActivity(), SearchActivity.class));
			break;
		case R.id.radio0:
			ima_priceup.setImageDrawable(getResources().getDrawable(R.drawable.price_up));
			ima_pricedown.setImageDrawable(getResources().getDrawable(R.drawable.price_down));
			if (mode != 0) {
				mode = 0;
				newList.clear();
				newList.addAll(newListData);
				if(newList.size() == 0) {
					rela_nothing.setVisibility(View.VISIBLE);
				} else {
					rela_nothing.setVisibility(View.GONE);
				}
				lvRightAdapter.notifyDataSetChanged();
			}
			break;
		case R.id.radio1:
			ima_priceup.setImageDrawable(getResources().getDrawable(R.drawable.price_up));
			ima_pricedown.setImageDrawable(getResources().getDrawable(R.drawable.price_down));
			if (mode != 1) {
				mode = 1;
				newList.clear();
				newList.addAll(faseList);
				if(newList.size() == 0) {
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
			if (mode != 2) {
				mode = 2;
				newList.clear();
				newList.addAll(hotist);
				if(newList.size() == 0) {
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
				if(newList.size() == 0) {
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
				if(newList.size() == 0) {
					rela_nothing.setVisibility(View.VISIBLE);
				} else {
					rela_nothing.setVisibility(View.GONE);
				}
				lvRightAdapter.notifyDataSetChanged();
			}
			break;
		case R.id.check_new: // 点击最近搜索
			text_leftHenxian_popsearch.setVisibility(View.VISIBLE);
			text_rightHenxian_popsearch.setVisibility(View.INVISIBLE);
			rela_new_popsearch.setVisibility(View.VISIBLE);
			rela_hot_popsearch.setVisibility(View.INVISIBLE);
			break;
		case R.id.check_hot: // 点击热门搜索
			text_rightHenxian_popsearch.setVisibility(View.VISIBLE);
			text_leftHenxian_popsearch.setVisibility(View.INVISIBLE);
			rela_hot_popsearch.setVisibility(View.VISIBLE);
			rela_new_popsearch.setVisibility(View.INVISIBLE);
			break;

		default:
			break;
		}
	}
}
