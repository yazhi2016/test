package com.example.fragment;

import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.homemodel.AreaNew;
import com.example.homemodel.Country;
import com.example.homemodel.HomeData;
import com.example.homemodel.Lists1;
import com.example.homemodel.NewShopList;
import com.example.model.Allcity;
import com.example.newonebuy.R;
import com.example.onebuy.DetailActivity;
import com.example.onebuy.LocationTwoActivity;
import com.example.onebuy.SearchActivity;
import com.example.onebuy.ShareActivity;
import com.example.onebuy.TimeActivity;
import com.example.util.Content;
import com.example.util.GlobalVariable;
import com.example.util.ListAdapter;
import com.example.util.MyShop;
import com.example.util.RushBuyCountDownTimerView;
import com.example.util.ViewPagerIndicator;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
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
public class HomeFragment extends Fragment implements OnClickListener {

	// private Handler mHandler = new Handler();
	private View view;
	LayoutInflater inflater;
	ListView lv;
	private PullToRefreshListView mPullRefreshListView;
	private LvAdapter gvAdapter; // 地区推荐适配器
	private ViewPager pagerBanner;
	protected boolean isDrag;
	private ViewPagerIndicator indicator;
	Handler handler1 = new Handler();
	private ImageView ima_priceup;
	private RadioButton price_up;
	private ImageView ima_pricedown;
	private RadioButton price_down;
	private RadioButton price_new;
	private RadioButton price_fast;
	private RadioButton price_hot;
	private TextView text_location_homefra;
	private RelativeLayout rela_back;
	private TextView text_bar;
	private RelativeLayout bar;
	private MyReAdapter myReAdapter;
	ArrayList<ImageView> imageViews = new ArrayList<ImageView>();
	private MyAdapter myAdapter; // 全国推荐适配器
	ArrayList<Lists1> imaList = new ArrayList<Lists1>();
	boolean hasBannerPhoto = false;
	private TextView text_title_banner_homefra;
	private TextView text_new_homefra1;
	private TextView text_new_homefra2;
	int nowPosition = 0;
	private ImageView ima_header4_1;
	private ImageView ima_header4_21;
	private ImageView ima_header4_12;
	private ImageView ima_header4_212;
	private TextView text_title_header41;
	private TextView text_title2_header41;
	ArrayList<Country> ReList = new ArrayList<Country>();
	ArrayList<AreaNew> fastList = new ArrayList<AreaNew>();
	ArrayList<AreaNew> newList = new ArrayList<AreaNew>();
	ArrayList<AreaNew> newList_data = new ArrayList<AreaNew>();
	ArrayList<AreaNew> hotList = new ArrayList<AreaNew>();
	ArrayList<AreaNew> priceUpList = new ArrayList<AreaNew>();
	ArrayList<AreaNew> PriceDownList = new ArrayList<AreaNew>();
	ArrayList<Lists1> bannerList = new ArrayList<Lists1>();

	private PopupWindow popupWindow;
	ArrayList<Allcity> popCity = new ArrayList<Allcity>();

	int mode = 0; // 0最新，1最快，2最热，3价格升，4价格降
	private RelativeLayout search;
	private GridView gv_poplocation;
	private PopLocationGvAdapter popLocationGvAdapter;
	private ImageView ima_bar;

	String id1; // 倒计时对应商品id.
	String id2;
	String id3;
	String id4;
	String city;
	private TextView text_nowcity_homefra;

	ListView lv_allpeople1;
	ListView lv_allpeople2;
	ListView lv_allpeople3;
	ListView lv_allpeople4;
	ListView lv_allpeople5;
	ListView lv_allpeople6;
	ListView lv_allpeople7;
	ListView lv_allpeople8;
	ListView lv_allpeople9;
	ListView lv_allpeople10;
	private List<String> list1;
	private List<String> list2;
	private List<String> list3;
	private List<String> list4;
	private List<String> list5;
	private List<String> list6;
	private List<String> list7;
	private List<String> list8;
	private List<String> list9;
	private List<String> list10;
	private ListAdapter lv_allpeople_adapter1;
	private ListAdapter lv_allpeople_adapter2;
	private ListAdapter lv_allpeople_adapter3;
	private ListAdapter lv_allpeople_adapter4;
	private ListAdapter lv_allpeople_adapter5;
	private ListAdapter lv_allpeople_adapter6;
	private ListAdapter lv_allpeople_adapter7;
	private ListAdapter lv_allpeople_adapter8;
	private ListAdapter lv_allpeople_adapter9;
	private ListAdapter lv_allpeople_adapter10;
	boolean autorunStop = false;
	boolean timeStop = false;
	private String registNum = "";

	int num1 = 9; // 显示的是8
	int num2 = 9;
	int num3 = 9;
	int num4 = 9;
	int num5 = 9;
	int num6 = 9;

	int currentView = 0;
	int BANNER_COUNT = 4 * 100000;

	ArrayList<String> imaUrlList = new ArrayList<String>();
	ArrayList<ListView> peopleList = new ArrayList<ListView>();

	int index;
	int index2;
	int index3;
	int index4;
	int index5;
	int index6;
	int index7;
	int index8;
	int index9;
	int index10;
	private PagerBannerAdapter pagerBannerAdapter;
	boolean finish = false;
	
	private RushBuyCountDownTimerView timerView;
	private RushBuyCountDownTimerView timerView2;
	private RushBuyCountDownTimerView timerView3;
	private RushBuyCountDownTimerView timerView4;

	public HomeFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			this.inflater = inflater;
			view = inflater.inflate(R.layout.fragment_home, container, false);
			initUI();
			initData();
			// initCategory();
			// initScoreCityData();
			autoScroll();
		}
		return view;
	}

	private void initUI() {
		ima_bar = (ImageView) view.findViewById(R.id.ima_bar);
		search = (RelativeLayout) view.findViewById(R.id.search);
		search.setOnClickListener(this);

		rela_back = (RelativeLayout) view.findViewById(R.id.back);
		mPullRefreshListView = (PullToRefreshListView) view.findViewById(R.id.listview1);
		lv = mPullRefreshListView.getRefreshableView();
		gvAdapter = new LvAdapter();

		// 监听下拉事件
		mPullRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				new GetDataTask().execute();
			}
		});

		// 初始化HeaderView
		View headerView = inflater.inflate(R.layout.headerview_pageritem, null);
		addDynamicView(); // 加载4张空白banne图片
		myAdapter = new MyAdapter();
		pagerBanner = (ViewPager) headerView.findViewById(R.id.pager_banner);

		FragmentManager fm = getChildFragmentManager();
		pagerBannerAdapter = new PagerBannerAdapter(fm);
		pagerBanner.setAdapter(pagerBannerAdapter);
		pagerBanner.setCurrentItem(BANNER_COUNT / 2);
		pagerBanner.setOnPageChangeListener(new MyPageChangeListener());

		indicator = (ViewPagerIndicator) headerView.findViewById(R.id.home_headertype_pagerindicator);

		// 在设置适配器之前添加HeaderView
		lv.addHeaderView(headerView);

		// 注册人数
		View allPeople = inflater.inflate(R.layout.header_allpeople, null);
		lv.addHeaderView(allPeople);

		lv_allpeople1 = (ListView) view.findViewById(R.id.lv_allpeople1);
		list1 = getList();
		lv_allpeople2 = (ListView) view.findViewById(R.id.lv_allpeople2);
		list2 = getList();
		lv_allpeople3 = (ListView) view.findViewById(R.id.lv_allpeople3);
		list3 = getList();
		lv_allpeople4 = (ListView) view.findViewById(R.id.lv_allpeople4);
		list4 = getList();
		lv_allpeople5 = (ListView) view.findViewById(R.id.lv_allpeople5);
		list5 = getList();
		lv_allpeople6 = (ListView) view.findViewById(R.id.lv_allpeople6);
		list6 = getList();
		lv_allpeople7 = (ListView) view.findViewById(R.id.lv_allpeople7);
		list7 = getList();
		lv_allpeople8 = (ListView) view.findViewById(R.id.lv_allpeople8);
		list8 = getList();
		lv_allpeople9 = (ListView) view.findViewById(R.id.lv_allpeople9);
		list9 = getList();
		lv_allpeople10 = (ListView) view.findViewById(R.id.lv_allpeople10);
		list10 = getList();
		peopleList.add(lv_allpeople1);
		peopleList.add(lv_allpeople2);
		peopleList.add(lv_allpeople3);
		peopleList.add(lv_allpeople4);
		peopleList.add(lv_allpeople5);
		peopleList.add(lv_allpeople6);
		peopleList.add(lv_allpeople7);
		peopleList.add(lv_allpeople8);
		peopleList.add(lv_allpeople9);
		peopleList.add(lv_allpeople10);

		// 全国商品等栏目
		View header2 = inflater.inflate(R.layout.header2_homefra, null);
		lv.addHeaderView(header2);
		header2.findViewById(R.id.rela_china).setOnClickListener(this);
		header2.findViewById(R.id.rela_ten).setOnClickListener(this);
		header2.findViewById(R.id.rela_hundred).setOnClickListener(this);
		header2.findViewById(R.id.rela_share).setOnClickListener(this);

		// 最新揭晓更多标题
		View header3 = inflater.inflate(R.layout.header3_homefra, null);
		header3.findViewById(R.id.text_top).setVisibility(View.GONE);
		header3.findViewById(R.id.text_bottom).setVisibility(View.GONE);
		header3.findViewById(R.id.textview3).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(), TimeActivity.class));
				// Intent intent2 = new Intent();
				// intent2.setAction("tabhost");
				// intent2.putExtra("tabhost", 0);
				// getActivity().sendBroadcast(intent2);
			}
		});
		header3.findViewById(R.id.rela_time_mefra).setOnClickListener(this);
		lv.addHeaderView(header3);
		TextView text_header3 = (TextView) header3.findViewById(R.id.text_title_header3);
		

		// 最新揭晓商品第一栏
		View header4 = inflater.inflate(R.layout.header4_homefra, null);
		lv.addHeaderView(header4);
		header4.findViewById(R.id.countTime11).setOnClickListener(this);
		header4.findViewById(R.id.counttime12).setOnClickListener(this);
		text_new_homefra1 = (TextView) header4.findViewById(R.id.text_new_homefra1);
		text_new_homefra2 = (TextView) header4.findViewById(R.id.text_new_homefra2);
		ima_header4_1 = (ImageView) header4.findViewById(R.id.ima_header4_1);
		ima_header4_21 = (ImageView) header4.findViewById(R.id.ima_header4_21);

		timerView = (RushBuyCountDownTimerView) header4.findViewById(R.id.timerView);
		timerView2 = (RushBuyCountDownTimerView) header4.findViewById(R.id.timerView2);
		// 设置时间(hour,min,sec)
		timerView.setTime(5, 0, 0);
		timerView2.setTime(5, 0, 0);
		// 开始倒计时
		timerView.start();
		timerView2.start();
		
		// 最新揭晓商品第二栏
		View header41 = inflater.inflate(R.layout.header4_homefra21, null);
		lv.addHeaderView(header41);
		header41.findViewById(R.id.counttime21).setOnClickListener(this);
		header41.findViewById(R.id.counttime22).setOnClickListener(this);
		ima_header4_12 = (ImageView) header41.findViewById(R.id.ima_header4_1);
		ima_header4_212 = (ImageView) header41.findViewById(R.id.ima_header4_21);
		text_title_header41 = (TextView) header41.findViewById(R.id.text_title_header41);
		text_title2_header41 = (TextView) header41.findViewById(R.id.text_title2_header41);
		
		timerView3 = (RushBuyCountDownTimerView) header41.findViewById(R.id.timerView);
		timerView4 = (RushBuyCountDownTimerView) header41.findViewById(R.id.timerView2);
		// 设置时间(hour,min,sec)
		timerView3.setTime(5, 0, 0);
		timerView4.setTime(5, 0, 0);
		// 开始倒计时
		timerView3.start();
		timerView4.start();

		// 全国推荐更多标题
		View header5 = inflater.inflate(R.layout.header3_homefra, null);
		// header5.findViewById(R.id.text_bottom).setVisibility(View.VISIBLE);
		// header5.findViewById(R.id.text_top).setVisibility(View.INVISIBLE);
		// 点击更多
		header5.findViewById(R.id.textview3).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent2 = new Intent();
				intent2.setAction("tabhost");
				intent2.putExtra("tabhost", 1);
				getActivity().sendBroadcast(intent2);
			}
		});
		lv.addHeaderView(header5);
		TextView text_header5 = (TextView) header5.findViewById(R.id.text_title_header3);
		text_header5.setText("全国推荐");

		// 全国推荐item
		View header6 = inflater.inflate(R.layout.header_recommend_homefra_, null);
		RecyclerView re = (RecyclerView) header6.findViewById(R.id.recyclerView1);
		re.setHasFixedSize(true);
		LinearLayoutManager lm = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
		re.setLayoutManager(lm);
		myReAdapter = new MyReAdapter();
		re.setAdapter(myReAdapter);
		lv.addHeaderView(header6);

		// 地区推荐更多标题
		View header7 = inflater.inflate(R.layout.header3_homefra, null);
		// lv.addHeaderView(header7);
		TextView text_header6 = (TextView) header7.findViewById(R.id.text_title_header3);
		text_header6.setText("地区推荐");

		header7.findViewById(R.id.textview3).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setAction("tabhost");
				intent.putExtra("tabhost", 44);
				getActivity().sendBroadcast(intent);
			}
		});

		// 筛选栏目
		View header8 = inflater.inflate(R.layout.header6_homegra, null);
		lv.addHeaderView(header8);
		ima_priceup = (ImageView) header8.findViewById(R.id.ima_priceup_header6_homefra);
		ima_pricedown = (ImageView) header8.findViewById(R.id.ima_pricedown_header6_homefra);
		price_new = (RadioButton) header8.findViewById(R.id.radio0);
		price_fast = (RadioButton) header8.findViewById(R.id.radio1);
		price_hot = (RadioButton) header8.findViewById(R.id.radio2);
		price_up = (RadioButton) header8.findViewById(R.id.radio3);
		price_down = (RadioButton) header8.findViewById(R.id.radio4);
		price_new.setOnClickListener(this);
		price_fast.setOnClickListener(this);
		price_hot.setOnClickListener(this);
		price_up.setOnClickListener(this);
		price_down.setOnClickListener(this);

		lv.setAdapter(gvAdapter);
		// autoScroll();

		// 地区弹窗
		View pop_location = inflater.inflate(R.layout.item_search_pop_homefra, null);
		text_nowcity_homefra = (TextView) pop_location.findViewById(R.id.text_nowcity_homefra);
		gv_poplocation = (GridView) pop_location.findViewById(R.id.gv_pop_homefra);
		pop_location.findViewById(R.id.rela_empty_poplocation).setOnClickListener(this);
		pop_location.findViewById(R.id.rela_changecity_poplocation).setOnClickListener(this);
		popupWindow = new PopupWindow(pop_location, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, true);

		popCity.add(new Allcity("", "", "", "", "漳州", "", "", ""));
		popCity.add(new Allcity("", "", "", "", "厦门", "", "", ""));
		popCity.add(new Allcity("", "", "", "", "泉州", "", "", ""));
		popCity.add(new Allcity("", "", "", "", "福州", "", "", ""));
		popCity.add(new Allcity("", "", "", "", "北京", "", "", ""));
		popCity.add(new Allcity("", "", "", "", "上海", "", "", ""));
		popCity.add(new Allcity("", "", "", "", "广州", "", "", ""));
		popCity.add(new Allcity("", "", "", "", "杭州", "", "", ""));
		popCity.add(new Allcity("", "", "", "", "深圳", "", "", ""));

		popLocationGvAdapter = new PopLocationGvAdapter();
		gv_poplocation.setAdapter(popLocationGvAdapter);
		gv_poplocation.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				String city = popCity.get(arg2).getName();
				
				GlobalVariable.getInstance().setSpValue("city", city);
				text_location_homefra.setText(city);
				text_nowcity_homefra.setText("当前城市：" + city);

				popupWindow.dismiss();
				initData();
			}
		});
		// 设置点击窗体以外区域关闭窗体
		BitmapDrawable b = new BitmapDrawable();
		popupWindow.setBackgroundDrawable(b);
		popupWindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				ima_bar.setImageDrawable(getResources().getDrawable(R.drawable.bar_right));
			}
		});


		text_location_homefra = (TextView) view.findViewById(R.id.text_location_homefra);
		text_location_homefra.setOnClickListener(this);
		text_bar = (TextView) view.findViewById(R.id.text_bar_homefra);
		bar = (RelativeLayout) view.findViewById(R.id.bar);

	}

	private void autoScrollAllPeopel() {
		// if(registNum.isEmpty()) {
		//
		// }
		// int length = registNum.length();
		if (registNum.isEmpty()) {
			Log.e("==", "registnum  null");
			return;
		} else {
			index = 0;
			lv_allpeople1.smoothScrollToPosition(index);
			index2 = 0;
			lv_allpeople2.smoothScrollToPosition(index2);
			index3 = 0;
			lv_allpeople3.smoothScrollToPosition(index3);
			index4 = 0;
			lv_allpeople4.smoothScrollToPosition(index4);
			index5 = 0;
			lv_allpeople5.smoothScrollToPosition(index5);
			index6 = 0;
			lv_allpeople6.smoothScrollToPosition(index6);
			index7 = 0;
			lv_allpeople7.smoothScrollToPosition(index7);
			index8 = 0;
			lv_allpeople8.smoothScrollToPosition(index8);
			index9 = 0;
			lv_allpeople9.smoothScrollToPosition(index9);
			index10 = 0;
			lv_allpeople10.smoothScrollToPosition(index10);
		}
		lv_allpeople_adapter1 = new ListAdapter(getActivity(), list1, Integer.parseInt(registNum.charAt(0) + "") + 1);
		// 显示的是9
		lv_allpeople1.setAdapter(lv_allpeople_adapter1);
		lv_allpeople_adapter2 = new ListAdapter(getActivity(), list2, Integer.parseInt(registNum.charAt(1) + "") + 1);
		lv_allpeople2.setAdapter(lv_allpeople_adapter2);
		lv_allpeople_adapter3 = new ListAdapter(getActivity(), list3, Integer.parseInt(registNum.charAt(2) + "") + 1);
		lv_allpeople3.setAdapter(lv_allpeople_adapter3);
		lv_allpeople_adapter4 = new ListAdapter(getActivity(), list4, Integer.parseInt(registNum.charAt(3) + "") + 1);
		lv_allpeople4.setAdapter(lv_allpeople_adapter4);
		lv_allpeople_adapter5 = new ListAdapter(getActivity(), list5, Integer.parseInt(registNum.charAt(4) + "") + 1);
		lv_allpeople5.setAdapter(lv_allpeople_adapter5);
		lv_allpeople_adapter6 = new ListAdapter(getActivity(), list6, Integer.parseInt(registNum.charAt(5) + "") + 1);
		lv_allpeople6.setAdapter(lv_allpeople_adapter6);
		lv_allpeople_adapter7 = new ListAdapter(getActivity(), list7, Integer.parseInt(registNum.charAt(6) + "") + 1);
		lv_allpeople7.setAdapter(lv_allpeople_adapter7);
		lv_allpeople_adapter8 = new ListAdapter(getActivity(), list8, Integer.parseInt(registNum.charAt(7) + "") + 1);
		lv_allpeople8.setAdapter(lv_allpeople_adapter8);

		handler1.postDelayed(new Runnable() {

			@Override
			public void run() {
				// 越大越慢，
				// lv_allpeople1.smoothScrollBy(num1 * 2, 0); // 乘以2
				// lv_allpeople2.smoothScrollBy(num2 * 2, 0);
				// lv_allpeople3.smoothScrollBy(num3 * 2, 0);
				// lv_allpeople4.smoothScrollBy(num4 * 2, 0);
				// lv_allpeople5.smoothScrollBy(num5 * 2, 0);
				// lv_allpeople6.smoothScrollBy(num6 * 2, 0);

				index += 1;
				if (index >= lv_allpeople1.getCount()) {
					return;
				}
				lv_allpeople1.smoothScrollByOffset(index);

				handler1.postDelayed(this, 80);
			}
		}, 80);
		handler1.postDelayed(new Runnable() {

			@Override
			public void run() {
				// 越大越慢，
				// lv_allpeople1.smoothScrollBy(num1 * 2, 0); // 乘以2
				// lv_allpeople2.smoothScrollBy(num2 * 2, 0);
				// lv_allpeople3.smoothScrollBy(num3 * 2, 0);
				// lv_allpeople4.smoothScrollBy(num4 * 2, 0);
				// lv_allpeople5.smoothScrollBy(num5 * 2, 0);
				// lv_allpeople6.smoothScrollBy(num6 * 2, 0);

				index2 += 1;
				if (index2 >= lv_allpeople2.getCount()) {
					return;
				}
				lv_allpeople2.smoothScrollToPosition(index2);

				handler1.postDelayed(this, 80);
			}
		}, 80);
		handler1.postDelayed(new Runnable() {

			@Override
			public void run() {
				// 越大越慢，
				// lv_allpeople1.smoothScrollBy(num1 * 2, 0); // 乘以2
				// lv_allpeople2.smoothScrollBy(num2 * 2, 0);
				// lv_allpeople3.smoothScrollBy(num3 * 2, 0);
				// lv_allpeople4.smoothScrollBy(num4 * 2, 0);
				// lv_allpeople5.smoothScrollBy(num5 * 2, 0);
				// lv_allpeople6.smoothScrollBy(num6 * 2, 0);

				index3 += 1;
				if (index3 >= lv_allpeople3.getCount()) {
					return;
				}
				lv_allpeople3.smoothScrollToPosition(index3);

				handler1.postDelayed(this, 80);
			}
		}, 80);
		handler1.postDelayed(new Runnable() {

			@Override
			public void run() {
				// 越大越慢，
				// lv_allpeople1.smoothScrollBy(num1 * 2, 0); // 乘以2
				// lv_allpeople2.smoothScrollBy(num2 * 2, 0);
				// lv_allpeople3.smoothScrollBy(num3 * 2, 0);
				// lv_allpeople4.smoothScrollBy(num4 * 2, 0);
				// lv_allpeople5.smoothScrollBy(num5 * 2, 0);
				// lv_allpeople6.smoothScrollBy(num6 * 2, 0);

				index4 += 1;
				if (index4 >= lv_allpeople4.getCount()) {
					return;
				}
				lv_allpeople4.smoothScrollToPosition(index4);

				handler1.postDelayed(this, 80);
			}
		}, 80);
		handler1.postDelayed(new Runnable() {

			@Override
			public void run() {
				// 越大越慢，
				// lv_allpeople1.smoothScrollBy(num1 * 2, 0); // 乘以2
				// lv_allpeople2.smoothScrollBy(num2 * 2, 0);
				// lv_allpeople3.smoothScrollBy(num3 * 2, 0);
				// lv_allpeople4.smoothScrollBy(num4 * 2, 0);
				// lv_allpeople5.smoothScrollBy(num5 * 2, 0);
				// lv_allpeople6.smoothScrollBy(num6 * 2, 0);

				index5 += 1;
				if (index5 >= lv_allpeople5.getCount()) {
					return;
				}
				lv_allpeople5.smoothScrollToPosition(index5);

				handler1.postDelayed(this, 80);
			}
		}, 80);
		handler1.postDelayed(new Runnable() {

			@Override
			public void run() {
				index6 += 1;
				if (index6 >= lv_allpeople6.getCount()) {
					return;
				}
				lv_allpeople6.smoothScrollToPosition(index6);

				handler1.postDelayed(this, 80);
			}
		}, 80);
		handler1.postDelayed(new Runnable() {

			@Override
			public void run() {
				index7 += 1;
				if (index7 >= lv_allpeople7.getCount()) {
					return;
				}
				lv_allpeople7.smoothScrollToPosition(index7);

				handler1.postDelayed(this, 80);
			}
		}, 80);
		handler1.postDelayed(new Runnable() {

			@Override
			public void run() {
				index8 += 1;
				if (index8 >= lv_allpeople8.getCount()) {
					return;
				}
				lv_allpeople8.smoothScrollToPosition(index8);

				handler1.postDelayed(this, 80);
			}
		}, 80);
	}


	private Handler mHandler = new Handler();

	private void autoScroll() {
		// 自动滚动Pager
		mHandler.postDelayed(new Runnable() {
			public void run() {
				int item = pagerBanner.getCurrentItem();
				// 设置自动滚动，可以设置是否有动画
				if (!isDrag) {
					// 设置ViewPager滑动
					if (finish) {
						return;
					}
					pagerBanner.setCurrentItem(item + 1);
				}
				mHandler.postDelayed(this, 3000);
			}
		}, 3000);
	}

	private void initData() {
		// 根据所在地得到url地址
		String location = text_location_homefra.getText().toString();
		String myCity = GlobalVariable.getInstance().getSpValue("city", "漳州");
		String encode = null;
		try {
			encode = URLEncoder.encode(myCity, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String url = Content.URL.HOME + encode;
		HTTPUtils.get(getActivity(), url, new VolleyListener() {

			@Override
			public void onResponse(String arg0) {
				// banner
				imaList.clear();
				Spanned fromHtml = Html.fromHtml(arg0);
				HomeData parseJSON = GsonUtils.parseJSON(fromHtml + "", HomeData.class);
				registNum = parseJSON.getRegistNum();
				autoScrollAllPeopel();
				List<Lists1> lists1 = parseJSON.getLists1();
				bannerList.clear();
				bannerList.addAll(lists1);
				imaUrlList.clear();
				for (int i = 0; i < lists1.size(); i++) {
					GlobalVariable.displayImageNoAnim(Content.URL.IMA_HOST + lists1.get(i).getImg(), imageViews.get(i));
					imaUrlList.add(Content.URL.IMA_HOST + lists1.get(i).getImg());
				}
				pagerBanner.setCurrentItem(BANNER_COUNT / 2 + 400);
				// text_title_banner_homefra.setText(lists1.get(0).getTitle());
				hasBannerPhoto = true;
				imaList.addAll(lists1);

				// 更新最新揭晓
				List<NewShopList> newShopList = parseJSON.getNewShopList();
				id1 = newShopList.get(0).getId();
				id2 = newShopList.get(1).getId();
				id3 = newShopList.get(2).getId();
				id4 = newShopList.get(3).getId();
				text_new_homefra1.setText("(第" + newShopList.get(0).getQishu() + "期)" + newShopList.get(0).getTitle());
				text_new_homefra2.setText("(第" + newShopList.get(1).getQishu() + "期)" + newShopList.get(1).getTitle());
				text_title_header41
						.setText("(第" + newShopList.get(2).getQishu() + "期)" + newShopList.get(2).getTitle());
				text_title2_header41
						.setText("(第" + newShopList.get(3).getQishu() + "期)" + newShopList.get(3).getTitle());

				Log.e("==", "main" + GlobalVariable.IsShowIma);
				GlobalVariable.displayImageNoAnim(Content.URL.IMA_HOST + newShopList.get(0).getThumb(), ima_header4_1);
				GlobalVariable.displayImageNoAnim(Content.URL.IMA_HOST + newShopList.get(1).getThumb(), ima_header4_21);
				GlobalVariable.displayImageNoAnim(Content.URL.IMA_HOST + newShopList.get(2).getThumb(), ima_header4_12);
				GlobalVariable.displayImageNoAnim(Content.URL.IMA_HOST + newShopList.get(3).getThumb(),
						ima_header4_212);

				// long t = Long.parseLong(timeStr);
				// long time = System.currentTimeMillis() - (t*1000);
				// long mill = (long) Math.ceil(time /1000);//秒前
				//
				// long minute = (long) Math.ceil(time/60/1000.0f);// 分钟前
				//
				// long hour = (long) Math.ceil(time/60/60/1000.0f);// 小时

				newList_data.clear();
				newList.clear();
				fastList.clear();
				hotList.clear();
				priceUpList.clear();
				PriceDownList.clear();

				// 最新最快最热
				List<Country> countries = parseJSON.getCountries();
				ReList.clear();
				ReList.addAll(countries);
				myReAdapter.notifyDataSetChanged();

				List<AreaNew> areaNew = parseJSON.getAreaNew();
				newList_data.addAll(areaNew);
				newList.addAll(areaNew);
				gvAdapter.notifyDataSetChanged();

				List<AreaNew> areaFast = parseJSON.getAreaFast();
				fastList.addAll(areaFast);

				List<AreaNew> areaHot = parseJSON.getAreaHot();
				hotList.addAll(areaHot);

				List<AreaNew> areaPriceAsc = parseJSON.getAreaPriceAsc();
				priceUpList.addAll(areaPriceAsc);

				List<AreaNew> areaNewDesc = parseJSON.getAreaNewDesc();
				PriceDownList.addAll(areaNewDesc);

			}

			@Override
			public void onErrorResponse(VolleyError arg0) {

			}
		});

	}

	private void addDynamicView() {
		// 动态添加图片
		// 初始化图片资源
		for (int i = 0; i < 4; i++) {
			ImageView imageView = new ImageView(getActivity());
			// 异步加载图片
			imageView.setScaleType(ScaleType.CENTER_CROP);
			imageViews.add(imageView);
			// UILUtils.displayImageNoAnim(imaList.get(i), imageView);
		}

		// myAdapter.notifyDataSetChanged();
	}

	private class MyAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return imageViews.size();
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			ImageView iv = imageViews.get(position);
			((ViewPager) container).addView(iv);
			// 在这个方法里面设置图片的点击事件
			// iv.setOnClickListener(new OnClickListener() {
			//
			// @Override
			// public void onClick(View v) {
			// // 处理跳转逻辑
			// }
			// });
			return iv;
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView((View) arg2);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {

		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {

		}

		@Override
		public void finishUpdate(View arg0) {

		}

	}

	class MyViewHolder extends ViewHolder {

		public MyViewHolder(View itemView) {
			super(itemView);
		}
	}

	class MyReAdapter extends Adapter<MyViewHolder> {

		@Override
		public int getItemCount() {
			return ReList.size();
		}

		@Override
		public void onBindViewHolder(MyViewHolder arg0, final int arg1) {
			final Country country = ReList.get(arg1);
			ImageView iv_home_film_preview = (ImageView) arg0.itemView.findViewById(R.id.iv_home_film_preview);
			GlobalVariable.displayImageNoAnim(Content.URL.IMA_HOST + country.getThumb(), iv_home_film_preview);
			TextView text_name_gvitem_homefra = (TextView) arg0.itemView.findViewById(R.id.text_name_gvitem_homefra);
			TextView progress_now1 = (TextView) arg0.itemView.findViewById(R.id.progress_now1);
			text_name_gvitem_homefra.setText("(第" + country.getQishu() + "期)" + country.getTitle());
			int stringDivide = Content.StringDivide(country.getCanyurenshu(), country.getZongrenshu());
			progress_now1.setText(stringDivide + "%");
			ProgressBar progressbar1 = (ProgressBar) arg0.itemView.findViewById(R.id.progressbar1);
			progressbar1.setProgress(stringDivide);

			// 添加到购物车
			arg0.itemView.findViewById(R.id.ima_addtoshopcar).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					String id = country.getId();
					List<MyShop> dataList = new Select().from(MyShop.class).queryList();
					for (int i = 0; i < dataList.size(); i++) {
						String goodid = dataList.get(i).getGoodid();
						if (id.equals(goodid)) {
							Toast.makeText(getActivity(), "购物车已有该商品", Toast.LENGTH_SHORT).show();
							return;
						}
					}

					Content.addShop(country.getId(), Content.URL.IMA_HOST + country.getThumb(), country.getQishu(), "1",
							country.getTitle(), country.getShenyurenshu(), country.getYunjiage(), country.getYunjiage(),
							false);
					Toast.makeText(getActivity(), "已添加到购物车", Toast.LENGTH_SHORT).show();
				}
			});
			final String id = country.getId();
			arg0.itemView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					startDetailAct(id);
				}
			});
		}

		@Override
		public MyViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
			View view = getActivity().getLayoutInflater().inflate(R.layout.item_recycleview_homefra, null);
			MyViewHolder myViewHolder = new MyViewHolder(view);
			return myViewHolder;
		}
	}


	class PopLocationGvAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return popCity.size();
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
			View view = inflater.inflate(R.layout.item_gv_pop_homefra, null);
			TextView text = (TextView) view.findViewById(R.id.textView1);
			text.setText(popCity.get(position).getName());
			return view;
		}

	}

	class MyPageChangeListener implements OnPageChangeListener {
		public void onPageScrollStateChanged(int state) {
			switch (state) {
			case ViewPager.SCROLL_STATE_DRAGGING:
				isDrag = true;
				break;
			case ViewPager.SCROLL_STATE_IDLE:
				isDrag = false;
				break;
			case ViewPager.SCROLL_STATE_SETTLING:
				isDrag = false;
				break;

			default:
				break;
			}
		}

		public void onPageScrolled(int position, float arg1, int arg2) {
			indicator.move(arg1, position %= 4);
			currentView = position;
		}

		public void onPageSelected(int position) {
			if (imaList.size() != 0) {
			}
		}
	}

	// private void autoScroll() {
	// // 自动滚动Pager
	// mHandler.postDelayed(new Runnable() {
	// public void run() {
	// // 设置ViewPager滑动
	// int item = pagerBanner.getCurrentItem();
	// // 设置自动滚动，可以设置是否有动画
	// if (!isDrag && hasBannerPhoto) {
	// nowPosition = (item + 1) % imageViews.size();
	// pagerBanner.setCurrentItem(nowPosition);
	// }
	// mHandler.postDelayed(this, 5000);
	// }
	// }, 5000);
	// }

	private class GetDataTask extends AsyncTask<Void, Void, String[]> {

		@Override
		protected String[] doInBackground(Void... params) {
			// Simulates a background job.
			try {
				Thread.sleep(500);
				initData();
				// initCategory();
				// initScoreCityData();
			} catch (InterruptedException e) {
			}
			return new String[] { "", "" };
		}

		@Override
		protected void onPostExecute(String[] result) {
			mPullRefreshListView.onRefreshComplete();
			super.onPostExecute(result);
		}
	}

	class LvAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			if (newList.size() == 0) {
				return 1;
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
			TextView text_nothing;
			RelativeLayout rela_nothing_item_homefra;
			RelativeLayout rela_two_item_homefra;
			RelativeLayout rela_one_item_homefra;
			ImageView ima_gvitem_homefra;
			TextView text_name_gvitem_homefra;
			TextView progress_now1;
			ProgressBar progressbar1;

			ImageView ima_gvitem_homefra2;
			TextView text_name_gvitem_homefra2;
			TextView progress_now2;
			ProgressBar progressbar2;

			ImageView ima_taghundred_homefra2;
			ImageView ima_tagten_homefra2;
			ImageView ima_taghundred_homefra;
			ImageView ima_tagten_homefra;

			ImageView ima_addtoshopcar;
			ImageView ima_addtoshopcar2;

			if (convertView == null) {
				view = inflater.inflate(R.layout.item_lv_homefra, null);

				text_nothing = (TextView) view.findViewById(R.id.text_nothing);
				rela_nothing_item_homefra = (RelativeLayout) view.findViewById(R.id.rela_nothing_item_homefra);
				rela_one_item_homefra = (RelativeLayout) view.findViewById(R.id.rela_one_item_homefra);
				rela_two_item_homefra = (RelativeLayout) view.findViewById(R.id.rela_two_item_homefra);

				ima_gvitem_homefra = (ImageView) view.findViewById(R.id.ima_gvitem_homefra);
				text_name_gvitem_homefra = (TextView) view.findViewById(R.id.text_name_gvitem_homefra);
				progress_now1 = (TextView) view.findViewById(R.id.progress_now1);
				progressbar1 = (ProgressBar) view.findViewById(R.id.progressbar1);

				ima_tagten_homefra = (ImageView) view.findViewById(R.id.ima_tagten_homefra);
				ima_taghundred_homefra = (ImageView) view.findViewById(R.id.ima_taghundred_homefra);
				ima_tagten_homefra2 = (ImageView) view.findViewById(R.id.ima_tagten_homefra2);
				ima_taghundred_homefra2 = (ImageView) view.findViewById(R.id.ima_taghundred_homefra2);
				ima_addtoshopcar = (ImageView) view.findViewById(R.id.ima_addtoshopcar);
				ima_addtoshopcar2 = (ImageView) view.findViewById(R.id.ima_addtoshopcar2);

				ima_gvitem_homefra2 = (ImageView) view.findViewById(R.id.ima_gvitem_homefra2);
				text_name_gvitem_homefra2 = (TextView) view.findViewById(R.id.text_name_gvitem_homefra2);
				progress_now2 = (TextView) view.findViewById(R.id.progress_now2);
				progressbar2 = (ProgressBar) view.findViewById(R.id.progressbar2);
				view.setTag(new MyLvTag(text_nothing, rela_one_item_homefra, rela_nothing_item_homefra, rela_two_item_homefra,
						ima_gvitem_homefra, text_name_gvitem_homefra2, progress_now1, progressbar1, ima_gvitem_homefra2,
						text_name_gvitem_homefra2, progress_now2, progressbar2, ima_taghundred_homefra2,
						ima_tagten_homefra2, ima_taghundred_homefra, ima_tagten_homefra, ima_addtoshopcar,
						ima_addtoshopcar2));
			} else {
				view = convertView;
				MyLvTag tag = (MyLvTag) view.getTag();
				rela_one_item_homefra = tag.rela_one_item_homefra;
				rela_nothing_item_homefra = tag.rela_nothing_item_homefra;
				text_nothing = tag.text_nothing;
				rela_two_item_homefra = tag.rela_two_item_homefra;
				ima_gvitem_homefra = tag.ima_gvitem_homefra;
				text_name_gvitem_homefra = tag.text_name_gvitem_homefra;
				progress_now1 = tag.progress_now1;
				progressbar1 = tag.progressbar1;
				ima_gvitem_homefra2 = tag.ima_gvitem_homefra2;
				text_name_gvitem_homefra2 = tag.text_name_gvitem_homefra2;
				progress_now2 = tag.progress_now2;
				progressbar2 = tag.progressbar2;

				ima_tagten_homefra = tag.ima_tagten_homefra;
				ima_taghundred_homefra = tag.ima_taghundred_homefra;
				ima_tagten_homefra2 = tag.ima_tagten_homefra2;
				ima_taghundred_homefra2 = tag.ima_taghundred_homefra2;
				ima_addtoshopcar = tag.ima_addtoshopcar;
				ima_addtoshopcar2 = tag.ima_addtoshopcar2;

			}

			if (newList.size() != 0) {
				rela_one_item_homefra.setVisibility(View.VISIBLE);
				text_nothing.setVisibility(View.GONE);
				// 设置左边的
				AreaNew areaFast1;
				AreaNew areaFast2;

				// 如果为奇数且为最后一行，则右边为空，隐藏
				if (position == (newList.size() / 2) && ((newList.size() % 2) != 0)) { // 最后一行和奇数
					rela_nothing_item_homefra.setVisibility(View.VISIBLE);
					rela_two_item_homefra.setVisibility(View.GONE);
					areaFast2 = newList.get(0);
				} else {
					rela_nothing_item_homefra.setVisibility(View.GONE);
					rela_two_item_homefra.setVisibility(View.VISIBLE);
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

				if ((int) Double.parseDouble(areaFast1.getYunjiage()) == 10) {
					ima_tagten_homefra.setVisibility(View.VISIBLE);
					ima_taghundred_homefra.setVisibility(View.INVISIBLE);
				} else if ((int) Double.parseDouble(areaFast1.getYunjiage()) == 100) {
					ima_tagten_homefra.setVisibility(View.INVISIBLE);
					ima_taghundred_homefra.setVisibility(View.VISIBLE);
				} else {
					ima_tagten_homefra.setVisibility(View.INVISIBLE);
					ima_taghundred_homefra.setVisibility(View.INVISIBLE);
				}
				if ((int) Double.parseDouble(areaFast2.getYunjiage()) == 10) {
					ima_tagten_homefra2.setVisibility(View.VISIBLE);
					ima_taghundred_homefra2.setVisibility(View.INVISIBLE);
				} else if ((int) Double.parseDouble(areaFast2.getYunjiage()) == 100) {
					ima_tagten_homefra2.setVisibility(View.INVISIBLE);
					ima_taghundred_homefra2.setVisibility(View.VISIBLE);
				} else {
					ima_tagten_homefra2.setVisibility(View.INVISIBLE);
					ima_taghundred_homefra2.setVisibility(View.INVISIBLE);
				}

				GlobalVariable.displayImageNoAnim(Content.URL.IMA_HOST + areaFast1.getThumb(), ima_gvitem_homefra);
				text_name_gvitem_homefra.setText("(第" + areaFast1.getQishu() + "期)" + areaFast1.getTitle());
				int stringDivide = Content.StringDivide(areaFast1.getCanyurenshu(), areaFast1.getZongrenshu());
				progress_now1.setText(stringDivide + "%");
				progressbar1.setProgress(stringDivide);

				// 设置右边的
				GlobalVariable.displayImageNoAnim(Content.URL.IMA_HOST + areaFast2.getThumb(), ima_gvitem_homefra2);
				text_name_gvitem_homefra2.setText("(第" + areaFast2.getQishu() + "期)" + areaFast2.getTitle());
				int stringDivide2 = Content.StringDivide(areaFast2.getCanyurenshu(), areaFast2.getZongrenshu());
				progress_now2.setText(stringDivide2 + "%");
				progressbar2.setProgress(stringDivide2);

				final String id1 = areaFast1.getId();
				final String id2 = areaFast2.getId();
				view.findViewById(R.id.rela_one_item_homefra).setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						startDetailAct(id1);
					}
				});
				view.findViewById(R.id.rela_two_item_homefra).setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						startDetailAct(id2);
					}
				});

				final AreaNew areaFast11 = areaFast1;
				final AreaNew areaFast21 = areaFast2;

				// 添加到购物车
				view.findViewById(R.id.ima_addtoshopcar).setOnClickListener(new OnClickListener() {

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
				// 添加到购物车
				view.findViewById(R.id.ima_addtoshopcar2).setOnClickListener(new OnClickListener() {

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
			} else { // 如果没有数据
				text_nothing.setVisibility(View.VISIBLE);
				rela_two_item_homefra.setVisibility(View.GONE);
				rela_one_item_homefra.setVisibility(View.GONE);
			}

			return view;
		}

	}

	public void startDetailAct(String id) {
		Intent intent = new Intent(getActivity(), DetailActivity.class);
		intent.putExtra("id", id);
		startActivity(intent);
	}

	class MyLvTag {
		TextView text_nothing;
		RelativeLayout rela_nothing_item_homefra;
		RelativeLayout rela_two_item_homefra;
		RelativeLayout rela_one_item_homefra;
		ImageView ima_gvitem_homefra;
		TextView text_name_gvitem_homefra;
		TextView progress_now1;
		ProgressBar progressbar1;

		ImageView ima_gvitem_homefra2;
		TextView text_name_gvitem_homefra2;
		TextView progress_now2;
		ProgressBar progressbar2;

		ImageView ima_taghundred_homefra2;
		ImageView ima_tagten_homefra2;
		ImageView ima_taghundred_homefra;
		ImageView ima_tagten_homefra;
		ImageView ima_addtoshopcar;
		ImageView ima_addtoshopcar2;

		public MyLvTag(TextView text_nothing, RelativeLayout rela_one_item_homefra, RelativeLayout rela_nothing_item_homefra,
				RelativeLayout rela_two_item_homefra, ImageView ima_gvitem_homefra, TextView text_name_gvitem_homefra,
				TextView progress_now1, ProgressBar progressbar1, ImageView ima_gvitem_homefra2,
				TextView text_name_gvitem_homefra2, TextView progress_now2, ProgressBar progressbar2,
				ImageView ima_taghundred_homefra2, ImageView ima_tagten_homefra2, ImageView ima_taghundred_homefra,
				ImageView ima_tagten_homefra, ImageView ima_addtoshopcar, ImageView ima_addtoshopcar2) {
			super();
			this.text_nothing = text_nothing;
			this.rela_one_item_homefra = rela_one_item_homefra;
			this.rela_nothing_item_homefra = rela_nothing_item_homefra;
			this.rela_two_item_homefra = rela_two_item_homefra;
			this.ima_gvitem_homefra = ima_gvitem_homefra;
			this.text_name_gvitem_homefra = text_name_gvitem_homefra;
			this.progress_now1 = progress_now1;
			this.progressbar1 = progressbar1;
			this.ima_gvitem_homefra2 = ima_gvitem_homefra2;
			this.text_name_gvitem_homefra2 = text_name_gvitem_homefra2;
			this.progress_now2 = progress_now2;
			this.progressbar2 = progressbar2;
			this.ima_taghundred_homefra2 = ima_taghundred_homefra2;
			this.ima_tagten_homefra2 = ima_tagten_homefra2;
			this.ima_taghundred_homefra = ima_taghundred_homefra;
			this.ima_tagten_homefra = ima_tagten_homefra;
			this.ima_addtoshopcar = ima_addtoshopcar;
			this.ima_addtoshopcar2 = ima_addtoshopcar2;
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.search: // 点击搜索
			startActivity(new Intent(getActivity(), SearchActivity.class));
			break;
		case R.id.text_location_homefra: // 点击所在地
			popupWindow.showAsDropDown(bar);
			ima_bar.setImageDrawable(getResources().getDrawable(R.drawable.bar_down));
			break;
		case R.id.rela_empty_poplocation: // 点击popolocation空白
			popupWindow.dismiss();
			break;
		case R.id.rela_changecity_poplocation: // 点击更换地址
			popupWindow.dismiss();
			startActivity(new Intent(getActivity(), LocationTwoActivity.class));
			break;
		case R.id.countTime11: // 点击倒计时左上
			startDetailAct(id1);
			break;
		case R.id.counttime12: // 点击倒计时右上
			startDetailAct(id2);
			break;
		case R.id.counttime21: // 点击倒计时左上
			startDetailAct(id3);
			break;
		case R.id.counttime22: // 点击倒计时右上
			startDetailAct(id4);
			break;
		case R.id.radio0: // 最新
			ima_priceup.setImageDrawable(getResources().getDrawable(R.drawable.price_up));
			ima_pricedown.setImageDrawable(getResources().getDrawable(R.drawable.price_down));
			if (mode != 0) {
				mode = 0;
				newList.clear();
				newList.addAll(newList_data);
				gvAdapter.notifyDataSetChanged();
			}
			break;
		case R.id.radio1: // 最快
			ima_priceup.setImageDrawable(getResources().getDrawable(R.drawable.price_up));
			ima_pricedown.setImageDrawable(getResources().getDrawable(R.drawable.price_down));
			if (mode != 1) {
				mode = 1;
				newList.clear();
				newList.addAll(fastList);
				gvAdapter.notifyDataSetChanged();
			}
			break;
		case R.id.radio2: // 最热
			ima_priceup.setImageDrawable(getResources().getDrawable(R.drawable.price_up));
			ima_pricedown.setImageDrawable(getResources().getDrawable(R.drawable.price_down));
			if (mode != 2) {
				mode = 2;
				newList.clear();
				newList.addAll(hotList);
				gvAdapter.notifyDataSetChanged();
			}
			break;
		case R.id.radio3: // 价格上
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
				gvAdapter.notifyDataSetChanged();
			}
			break;
		case R.id.radio4: // 价格下
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
				newList.addAll(PriceDownList);
				gvAdapter.notifyDataSetChanged();
			}
			break;
		case R.id.rela_china: // 点击全国专区
			Intent intent = new Intent();
			intent.setAction("tabhost");
			intent.putExtra("tabhost", 1);
			getActivity().sendBroadcast(intent);
			break;
		case R.id.rela_ten: // 点击十元专区
			Intent intent2 = new Intent();
			intent2.setAction("tabhost");
			intent2.putExtra("tabhost", 3);
			getActivity().sendBroadcast(intent2);
			break;
		case R.id.rela_share: // 点击晒单专区
			startActivity(new Intent(getActivity(), ShareActivity.class));
			break;
		case R.id.rela_hundred: // 点击百元专区
			Intent intent3 = new Intent();
			intent3.setAction("tabhost");
			intent3.putExtra("tabhost", 4);
			getActivity().sendBroadcast(intent3);
			break;
		case R.id.rela_time_mefra: // 点击最新揭晓栏
			startActivity(new Intent(getActivity(), TimeActivity.class));
			break;
		default:
			break;
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		city = GlobalVariable.getInstance().getSpValue("city", "漳州");
		text_location_homefra.setText(city);
		text_nowcity_homefra.setText("当前城市：" + city);
		autoScrollAllPeopel();
		finish = false;
	}

	@Override
	public void onPause() {
		super.onPause();
		finish = true;
	}

	/**
	 * 获取数据
	 * 
	 * @return
	 */
	public List<String> getList() {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < 10; i++) {
			list.add(String.valueOf(i));
		}
		return list;
	}

	class PagerBannerAdapter extends FragmentPagerAdapter {
		public PagerBannerAdapter(FragmentManager fm) {
			super(fm);
		}

		public Fragment getItem(int position) {
			position %= 4;
			if (imaUrlList.size() == 0) {
				return new BannerItemFragment(position, "", "");
			} else {
				return new BannerItemFragment(position, imaUrlList.get(position), bannerList.get(position).getLink());
			}
		}

		@Override
		public int getCount() {
			return BANNER_COUNT;
		}
	}
}
