package com.example.onebuy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.model.AllOrder;
import com.example.model.OrderAll;
import com.example.newonebuy.R;
import com.example.util.Content;
import com.xinbo.utils.GsonUtils;
import com.xinbo.utils.HTTPUtils;
import com.xinbo.utils.VolleyListener;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 妙购记录--我的妙购记录
 */
public class AllOrderActivity extends ActionBarActivity implements OnClickListener {

	private ListView lv;
	private LvAdapter lvAdapter;
	String token;
	String uid;
	ArrayList<OrderAll> orderAllList_lv = new ArrayList<OrderAll>();
	ArrayList<OrderAll> orderAllList = new ArrayList<OrderAll>();
	ArrayList<OrderAll> orderOnGoingList = new ArrayList<OrderAll>();
	ArrayList<OrderAll> orderCompleteList = new ArrayList<OrderAll>();
	int mode = 0; // 0全部，1进行中，2，已揭晓
	private SharedPreferences sp;
	private RelativeLayout rela_noinfo_allorder;
	private TextView checkbox_shopfra_edit;
	int lookPosition;
	String showCode;
	TextView showCoedText;
	private showCodeBroad showCodeBroad;
	private RelativeLayout rela_loading;
	ImageOptions imageOptions;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all_order);

		imageOptions = new ImageOptions.Builder().setSize(DensityUtil.dip2px(50), DensityUtil.dip2px(50))
				// .setRadius(DensityUtil.dip2px(5))
				// 如果ImageView的大小不是定义为wrap_content, 不要crop.
				.setCrop(true) // 很多时候设置了合适的scaleType也不需要它.
				// 加载中或错误图片的ScaleType
				// .setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
				.setImageScaleType(ImageView.ScaleType.CENTER_CROP).setLoadingDrawableId(R.drawable.ic_stub)
				.setFailureDrawableId(R.drawable.ic_error)
				// 设置使用缓存
				.setUseMemCache(true).build();

		showCodeBroad = new showCodeBroad();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("showcode");
		registerReceiver(showCodeBroad, intentFilter);

		sp = getSharedPreferences("token", 0);
		token = sp.getString("token", "");
		uid = sp.getString("uid", "");

		lv = (ListView) findViewById(R.id.lv_allOrderAct);
		lvAdapter = new LvAdapter();
		lv.setAdapter(lvAdapter);

		rela_loading = (RelativeLayout) findViewById(R.id.rela_loading);
		findViewById(R.id.back).setOnClickListener(this);
		findViewById(R.id.pop_allorderact).setOnClickListener(this);
		checkbox_shopfra_edit = (TextView) findViewById(R.id.checkbox_shopfra_edit);
		rela_noinfo_allorder = (RelativeLayout) findViewById(R.id.rela_noinfo_allorder);

		initData();

	}

	private void initData() {
		String url = Content.URL.ALLORDER + token + "&uid=" + uid;
		HTTPUtils.get(AllOrderActivity.this, url, new VolleyListener() {

			@Override
			public void onResponse(String arg0) {
				orderAllList_lv.clear();
				AllOrder parseJSON = GsonUtils.parseJSON(arg0, AllOrder.class);
				List<OrderAll> orderAll = parseJSON.getOrderAll();
				orderAllList_lv.addAll(orderAll);
				orderAllList.addAll(orderAll);
				if (orderAllList_lv.size() != 0) {
					rela_noinfo_allorder.setVisibility(View.GONE);
				} else {
					rela_noinfo_allorder.setVisibility(View.VISIBLE);
				}
				lvAdapter.notifyDataSetChanged();

				List<OrderAll> orderComple = parseJSON.getOrderComple();
				orderCompleteList.addAll(orderComple);

				List<OrderAll> orderOngoing = parseJSON.getOrderOngoing();
				orderOnGoingList.addAll(orderOngoing);

				rela_loading.setVisibility(View.GONE);
			}

			@Override
			public void onErrorResponse(VolleyError arg0) {

			}
		});
	}

	class LvAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return orderAllList_lv.size();
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
		public View getView(final int position, View convertView, ViewGroup parent) {
			View view;
			Button btn_show;
			ImageView ima_win;
			TextView text_title_allorder;
			TextView text_allneed_allorder;
			TextView text_luckyman_allorder;
			TextView text_luckynum_allorder;
			TextView text_time_allorder;
			TextView text_buynum_allorder;
			TextView text_hepay_allorder;
			ImageView ima_product_allorder;
			final TextView text_mycode_allorderact;
			final TextView text_lookcode_allorderact;

			if (convertView == null) {
				view = getLayoutInflater().inflate(R.layout.item_lv_allorderact, null);
				text_title_allorder = (TextView) view.findViewById(R.id.text_title_allorder);
				text_mycode_allorderact = (TextView) view.findViewById(R.id.text_mycode_allorderact);
				text_allneed_allorder = (TextView) view.findViewById(R.id.text_allneed_allorder);
				text_luckyman_allorder = (TextView) view.findViewById(R.id.text_luckyman_allorder);
				text_luckynum_allorder = (TextView) view.findViewById(R.id.text_luckynum_allorder);
				text_time_allorder = (TextView) view.findViewById(R.id.text_time_allorder);
				text_buynum_allorder = (TextView) view.findViewById(R.id.text_buynum_allorder);
				text_hepay_allorder = (TextView) view.findViewById(R.id.text_hepay_allorder);
				text_lookcode_allorderact = (TextView) view.findViewById(R.id.text_lookcode_allorderact);
				ima_product_allorder = (ImageView) view.findViewById(R.id.ima_product_allorder);
				btn_show = (Button) view.findViewById(R.id.btn_show_allorderact);
				ima_win = (ImageView) view.findViewById(R.id.ima_win_allorderact);
				view.setTag(new LvTag(btn_show, ima_win, text_title_allorder, text_allneed_allorder,
						text_luckyman_allorder, text_luckynum_allorder, text_time_allorder, text_buynum_allorder,
						text_hepay_allorder, ima_product_allorder, text_mycode_allorderact, text_lookcode_allorderact));
			} else {
				view = convertView;
				LvTag tag2 = (LvTag) view.getTag();
				btn_show = tag2.btn_show;
				ima_win = tag2.ima_win;
				text_title_allorder = tag2.text_title_allorder;
				text_allneed_allorder = tag2.text_allneed_allorder;
				text_luckyman_allorder = tag2.text_luckyman_allorder;
				text_luckynum_allorder = tag2.text_luckynum_allorder;
				text_time_allorder = tag2.text_time_allorder;
				text_buynum_allorder = tag2.text_buynum_allorder;
				text_hepay_allorder = tag2.text_hepay_allorder;
				ima_product_allorder = tag2.ima_product_allorder;
				text_mycode_allorderact = tag2.text_mycode_allorderact;
				text_lookcode_allorderact = tag2.text_lookcode_allorderact;
			}

			OrderAll orderAll = orderAllList_lv.get(position);
			text_title_allorder.setText("第" + orderAll.getQishu() + "期" + orderAll.getShopname());
			text_allneed_allorder.setText("总需：" + orderAll.getZongrenshu() + "人次");
			text_luckyman_allorder.setText(orderAll.getQUser());
			text_luckynum_allorder.setText(orderAll.getQUserCode());
			text_time_allorder.setText(orderAll.getQEndTime());
			text_buynum_allorder.setText(orderAll.getGonumber());
			x.image().bind(ima_product_allorder, Content.URL.IMA_HOST + orderAll.getThumb(), imageOptions);
			// GlobalVariable.displayImageNoAnim(Content.URL.IMA_HOST +
			// orderAll.getThumb(), ima_product_allorder);

			final String id = orderAll.getId();
			btn_show.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(AllOrderActivity.this, ShowActivity.class);
					intent.putExtra("goodid", id);
					startActivity(intent);
				}
			});

			if (orderAll.getQUid() != null) {
				if (orderAll.getQUid().equals(uid)) { // 中奖者UID和我相同，即中奖
					// btn_show.setVisibility(View.VISIBLE);
					ima_win.setImageDrawable(getResources().getDrawable(R.drawable.win));
				} else {
					btn_show.setVisibility(View.INVISIBLE);
					ima_win.setImageDrawable(getResources().getDrawable(R.drawable.nowin));
				}
			} else {
				btn_show.setVisibility(View.INVISIBLE);
				ima_win.setImageDrawable(getResources().getDrawable(R.drawable.lucency));
			}
			// if(position == 2) {
			// btn_show.setVisibility(View.INVISIBLE);
			// ima_win.setImageDrawable(getResources().getDrawable(R.drawable.nowin));
			// }

			int fp = lv.getFirstVisiblePosition();
			int lp = lv.getLastVisiblePosition();
			if (!(position >= fp && position <= lp)) { // 如果不是可见行，就把号码收起
				text_lookcode_allorderact.setText("查看我的号码");
				text_mycode_allorderact.setVisibility(View.GONE);
			}

			final String goucode = orderAll.getGoucode();
			text_lookcode_allorderact.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (text_lookcode_allorderact.getText().equals("收起")) {
						text_lookcode_allorderact.setText("查看我的号码");
						text_mycode_allorderact.setVisibility(View.GONE);
					} else {
						showCoedText = text_mycode_allorderact;
						text_lookcode_allorderact.setText("收起");
						text_mycode_allorderact.setVisibility(View.VISIBLE);
						editString(goucode);
					}
				}
			});

			return view;
		}
	}

	public void editString(final String str) { // 每三个逗号，逗号替换为换行符
		new Thread(new Runnable() {
			public void run() {
				showCode = "加载中...";
				// 发送广播
				Intent intent = new Intent();
				intent.setAction("showcode");
				sendBroadcast(intent);

				StringBuffer buffer = new StringBuffer(str);
				int num = 0;
				int index = buffer.indexOf(",");
				if (index != -1) {
					num = 1;
				}
				while (true) {
					if (num % 3 == 0 && num != 0) {
						// buffer.insert(index + 1, "\n");
						buffer.replace(index, index + 1, "\n");
					} else if (num % 3 != 0 && num != 0) {
						buffer.insert(index + 1, "   ");
					}
					index = buffer.indexOf(",", index + 1);
					if (index != -1) {
						num++;
					} else {
						break;
					}
				}
				showCode = buffer.toString();
				sendBroadcast(intent);
			}
		}).start();
	}

	class LvTag {
		Button btn_show;
		ImageView ima_win;
		TextView text_title_allorder;
		TextView text_allneed_allorder;
		TextView text_luckyman_allorder;
		TextView text_luckynum_allorder;
		TextView text_time_allorder;
		TextView text_buynum_allorder;
		TextView text_hepay_allorder;
		ImageView ima_product_allorder;
		TextView text_mycode_allorderact;
		TextView text_lookcode_allorderact;

		public LvTag(Button btn_show, ImageView ima_win, TextView text_title_allorder, TextView text_allneed_allorder,
				TextView text_luckyman_allorder, TextView text_luckynum_allorder, TextView text_time_allorder,
				TextView text_buynum_allorder, TextView text_hepay_allorder, ImageView ima_product_allorder,
				TextView text_mycode_allorderact, TextView text_lookcode_allorderact) {
			super();
			this.btn_show = btn_show;
			this.ima_win = ima_win;
			this.text_title_allorder = text_title_allorder;
			this.text_allneed_allorder = text_allneed_allorder;
			this.text_luckyman_allorder = text_luckyman_allorder;
			this.text_luckynum_allorder = text_luckynum_allorder;
			this.text_time_allorder = text_time_allorder;
			this.text_buynum_allorder = text_buynum_allorder;
			this.text_hepay_allorder = text_hepay_allorder;
			this.ima_product_allorder = ima_product_allorder;
			this.text_mycode_allorderact = text_mycode_allorderact;
			this.text_lookcode_allorderact = text_lookcode_allorderact;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.pop_allorderact: // 点击右上角
			PopupMenu popupMenu = new PopupMenu(this, v);
			popupMenu.getMenuInflater().inflate(R.menu.allorderact, popupMenu.getMenu());
			// 菜单按钮的点击事件
			popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

				@Override
				public boolean onMenuItemClick(MenuItem arg0) {
					switch (arg0.getItemId()) {
					case R.id.all_menu_allorderact: // 点击了全部
						if (mode != 0) {
							mode = 0;
							orderAllList_lv.clear();
							orderAllList_lv.addAll(orderAllList);
							if (orderAllList_lv.size() != 0) {
								rela_noinfo_allorder.setVisibility(View.GONE);
							} else {
								rela_noinfo_allorder.setVisibility(View.VISIBLE);
							}
							lvAdapter.notifyDataSetChanged();
							checkbox_shopfra_edit.setText("全部");
						}
						break;
					case R.id.playing_menu_allorderact: // 点击了进行中
						if (mode != 1) {
							mode = 1;
							orderAllList_lv.clear();
							orderAllList_lv.addAll(orderOnGoingList);
							if (orderAllList_lv.size() != 0) {
								rela_noinfo_allorder.setVisibility(View.GONE);
							} else {
								rela_noinfo_allorder.setVisibility(View.VISIBLE);
							}
							lvAdapter.notifyDataSetChanged();
							checkbox_shopfra_edit.setText("进行中");
						}
						break;
					case R.id.done_menu_allorderact: // 点击了已揭晓
						if (mode != 2) {
							mode = 2;
							orderAllList_lv.clear();
							orderAllList_lv.addAll(orderCompleteList);
							if (orderAllList_lv.size() != 0) {
								rela_noinfo_allorder.setVisibility(View.GONE);
							} else {
								rela_noinfo_allorder.setVisibility(View.VISIBLE);
							}
							lvAdapter.notifyDataSetChanged();
							checkbox_shopfra_edit.setText("已揭晓");
						}
						break;

					default:
						break;
					}
					return false;
				}
			});
			popupMenu.show();
			break;

		default:
			break;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(showCodeBroad);
	}

	class showCodeBroad extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			showCoedText.setText(showCode);
		}

	}
}
