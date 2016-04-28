package com.example.onebuy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.homemodel.SignInSuccessByToken;
import com.example.model.Shaidan5;
import com.example.model.Shaidan6;
import com.example.model.SignOut;
import com.example.newonebuy.R;
import com.example.util.BaseActivity;
import com.example.util.Content;
import com.example.util.GlobalVariable;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;
import com.xinbo.utils.GsonUtils;
import com.xinbo.utils.HTTPUtils;
import com.xinbo.utils.VolleyListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AllShowActivity extends BaseActivity {

	private ListView lv;
	private LvAdapter lvAdapter;
	int editPosition;
	PullToRefreshListView mPullRefreshListView;
	int mPage = 1;
	String goods_sid = "";
	ArrayList<Shaidan6> list = new ArrayList<Shaidan6>();
	private String stringExtra;
	private RelativeLayout rela_noinfo_allshow;
	String token;
	String uid;
	boolean isLogIn;
	String editSid = "";

	// 分享
	final SHARE_MEDIA[] displaylist = new SHARE_MEDIA[] { SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,
			SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE };

	private ShareBoardlistener shareBoardlistener = new ShareBoardlistener() {

		@Override
		public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
			if (share_media == null) {
				if (snsPlatform.mKeyword.equals("11")) {
					Toast.makeText(AllShowActivity.this, "add button success", Toast.LENGTH_LONG).show();
				}

			} else {
				new ShareAction(AllShowActivity.this).setPlatform(share_media).setCallback(umShareListener)
						.withText("赶快下载吧！").withTitle("一元妙购")
						.withTargetUrl("http://pc88.gotoip4.com/index.php/mobile/shaidan/detail/" + editSid)
						.withMedia(new UMImage(AllShowActivity.this,
								BitmapFactory.decodeResource(getResources(), R.drawable.logo)))
						.share();
			}
		}
	};

	UMShareListener umShareListener = new UMShareListener() {
		@Override
		public void onResult(SHARE_MEDIA platform) {
			Toast.makeText(AllShowActivity.this, "分享成功啦", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onError(SHARE_MEDIA platform, Throwable t) {
			Toast.makeText(AllShowActivity.this, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onCancel(SHARE_MEDIA platform) {
			Toast.makeText(AllShowActivity.this, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all_show);
		goods_sid = getIntent().getStringExtra("goodid");
		initViews();
		initEvents();
	}

	@Override
	protected void onResume() {
		super.onResume();
		GlobalVariable instance = GlobalVariable.getInstance();
		token = instance.getSpToken();
		uid = instance.getSpUid();
		if (token.equals("")) { // 未登录
			isLogIn = false;
			refreshData(false);
		} else { // 有token，判断token是否有效
			signInByTokenOnResume();
		}
	}

	public void signInByToken() {
		String url = Content.URL.SIGNINBYTOKEN + token + "&uid=" + uid;
		HTTPUtils.get(this, url, new VolleyListener() {

			@Override
			public void onResponse(String arg0) {
				Spanned fromHtml = Html.fromHtml(arg0);
				SignInSuccessByToken parseJSON = GsonUtils.parseJSON(fromHtml + "", SignInSuccessByToken.class);
				if (parseJSON.getTag() == 1) { // 已登录
					isLogIn = true;
				} else {
					isLogIn = false;
				}
				return;
			}

			@Override
			public void onErrorResponse(VolleyError arg0) {

			}
		});
	}

	public void signInByTokenOnResume() {
		String url = Content.URL.SIGNINBYTOKEN + token + "&uid=" + uid;
		HTTPUtils.get(this, url, new VolleyListener() {

			@Override
			public void onResponse(String arg0) {
				Spanned fromHtml = Html.fromHtml(arg0);
				SignInSuccessByToken parseJSON = GsonUtils.parseJSON(fromHtml + "", SignInSuccessByToken.class);
				if (parseJSON.getTag() == 1) { // 已登录
					isLogIn = true;
				} else {
					isLogIn = false;
					// showShortToast(parseJSON.getMessage());
				}
				refreshData(false);
			}

			@Override
			public void onErrorResponse(VolleyError arg0) {

			}
		});
	}

	public void like(String goodid, final TextView t, final String num) {
		String url = Content.URL.SHOWLIKE + goodid + "&uid=" + uid + "&token=" + token;
		HTTPUtils.get(AllShowActivity.this, url, new VolleyListener() {

			@Override
			public void onResponse(String arg0) {
				Spanned fromHtml = Html.fromHtml(arg0);
				SignOut parseJSON = GsonUtils.parseJSON(fromHtml + "", SignOut.class);
				if (parseJSON.getTag() == 1) { // 点赞成功
					Toast.makeText(AllShowActivity.this, "点赞成功", Toast.LENGTH_SHORT).show();
					int parseInt = Integer.parseInt(num);
					parseInt++;
					t.setText(parseInt + "");
				} else { // 已点赞过
					Toast.makeText(AllShowActivity.this, parseJSON.getMessage(), Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void onErrorResponse(VolleyError arg0) {

			}
		});
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

	public void refreshData(final boolean isPullUp) {
		String url;
		if (isLogIn) { // 登陆状态还要传UID
			if (isPullUp) { // 上拉刷新
				url = Content.URL.PRODUCTSHARE + goods_sid + "&page=" + mPage + "&uid=" + uid;
			} else { // 下拉刷新，加载第0页
				url = Content.URL.PRODUCTSHARE + goods_sid + "&page=" + 0 + "&uid=" + uid;
				mPage = 1;
			}
		} else {
			if (isPullUp) { // 上拉刷新
				url = Content.URL.PRODUCTSHARE + goods_sid + "&page=" + mPage;
			} else { // 下拉刷新，加载第0页
				url = Content.URL.PRODUCTSHARE + goods_sid + "&page=" + 0;
				mPage = 1;
			}
		}

		HTTPUtils.get(AllShowActivity.this, url, new VolleyListener() {

			@Override
			public void onResponse(String arg0) {
				Log.e("==", isLogIn + "--refrash");
				Spanned fromHtml = Html.fromHtml(arg0);
				Shaidan5 parseJSON = GsonUtils.parseJSON(fromHtml + "", Shaidan5.class);
				List<Shaidan6> shaidan = parseJSON.getShaidan();

				if (shaidan.isEmpty()) {
					Toast.makeText(AllShowActivity.this, "没有内容了", Toast.LENGTH_SHORT).show();
					mPullRefreshListView.onRefreshComplete();
				} else {
					if (!isPullUp) {
						list.clear();
					}
					list.addAll(shaidan);
					lvAdapter.notifyDataSetChanged();
					if (isPullUp) { // 上拉刷新
						mPage++;
					}
					mPullRefreshListView.onRefreshComplete();
					if (list.size() == 0) {
						rela_noinfo_allshow.setVisibility(View.VISIBLE);
					} else {
						rela_noinfo_allshow.setVisibility(View.GONE);
					}
				}
			}

			@Override
			public void onErrorResponse(VolleyError arg0) {

			}
		});

	}

	@SuppressLint("ViewHolder")
	class LvAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return list.size();
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
			View view = getLayoutInflater().inflate(R.layout.item_lv_allshowact, null);
			final Shaidan6 shaidan = list.get(position);
			final TextView text_likenum = (TextView) view.findViewById(R.id.text_likenum);
			final ImageView ima_like = (ImageView) view.findViewById(R.id.ima_like_allshowact);
			final Integer isZan = shaidan.getIsZan();
			Log.e("==", isLogIn + "LvAdapter");
			if (isLogIn) {
				if (isZan == 1) { // 赞过
					Log.e("==", position + "赞过");
					ima_like.setImageDrawable(getResources().getDrawable(R.drawable.like2));
				} else {
					Log.e("==", position + "未赞");
					ima_like.setImageDrawable(getResources().getDrawable(R.drawable.like1));
				}
			}
			// 点赞
			view.findViewById(R.id.rela_like_allshowfra).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Log.e("==", isLogIn + "1赞过--" + isZan);
					signInByToken(); // 判断登陆状态防止token失效
					if (isLogIn) { // 登陆状态
						if (isZan == 1) { // 赞过
							Toast.makeText(AllShowActivity.this, "已点过赞", Toast.LENGTH_SHORT).show();
							return;
						}
						ima_like.setImageDrawable(getResources().getDrawable(R.drawable.like2));
						like(shaidan.getSdId(), text_likenum, shaidan.getSdZhan());
					} else {
						Toast.makeText(AllShowActivity.this, "请登陆", Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(AllShowActivity.this, LogInActivity.class);
						// intent.putExtra("state", "notsafe");
						startActivity(intent);
					}
				}
			});
			final String sdId = shaidan.getSdId();
			editSid = sdId;
			// 点击评论
			view.findViewById(R.id.rela_comment_item_allshowfra).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					signInByToken(); // 判断登陆状态防止token失效
					if (isLogIn) { // 登陆状态
						Intent intent = new Intent(AllShowActivity.this, ShowDetailActivity.class);
						intent.putExtra("sid", sdId);
						intent.putExtra("comment", 1);
						startActivity(intent);
					} else {
						Toast.makeText(AllShowActivity.this, "请登陆", Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(AllShowActivity.this, LogInActivity.class);
						// intent.putExtra("state", "notsafe");
						startActivity(intent);
					}

				}
			});
			// 点击转发
			view.findViewById(R.id.rela_share_allshowfra).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					new ShareAction(AllShowActivity.this).setDisplayList(displaylist).withText("呵呵").withTitle("title")
							.withTargetUrl("http://pc88.gotoip4.com/index.php/mobile/shaidan/detail/" + editSid)
							.withMedia(new UMImage(AllShowActivity.this,
									BitmapFactory.decodeResource(getResources(), R.drawable.like1)))
							.setListenerList(umShareListener).setShareboardclickCallback(shareBoardlistener).open();

				}
			});

			CircleImageView ima_productshare = (CircleImageView) view.findViewById(R.id.ima_people);
			ImageView ima_product = (ImageView) view.findViewById(R.id.ima_detail1);
			TextView text_name_productshareact = (TextView) view.findViewById(R.id.text_name2);
			TextView text_time_productshareact = (TextView) view.findViewById(R.id.text_time);
			TextView text_title_productshare = (TextView) view.findViewById(R.id.text_title);
			TextView text_content_productshare = (TextView) view.findViewById(R.id.text_content);
			TextView text_commentnum = (TextView) view.findViewById(R.id.text_commentnum);
			TextView text_sharenum = (TextView) view.findViewById(R.id.text_sharenum);

			text_name_productshareact.setText(shaidan.getUsername());
			text_time_productshareact.setText(shaidan.getSdTime());
			text_title_productshare.setText(shaidan.getSdTitle());
			text_content_productshare.setText(shaidan.getSdContent());
			text_likenum.setText(shaidan.getSdZhan());
			text_commentnum.setText(shaidan.getSdPing());
			// text_sharenum.setText(shaidan.getSdPing());

			GlobalVariable.displayImageNoAnim(Content.URL.IMA_HOST + shaidan.getSdThumbs(), ima_product);
			GlobalVariable.displayImageNoAnim(Content.URL.IMA_HOST + shaidan.getImg(), ima_productshare);
			return view;
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.pop: // 点击顶栏的选项
			PopupMenu popupMenu = new PopupMenu(this, v);
			popupMenu.getMenuInflater().inflate(R.menu.allshowact, popupMenu.getMenu());
			// 菜单按钮的点击事件
			popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

				@Override
				public boolean onMenuItemClick(MenuItem arg0) {
					switch (arg0.getItemId()) {
					case R.id.new_menu_allshowact: // 点击了最新晒单
						Toast.makeText(AllShowActivity.this, "最新晒单", Toast.LENGTH_SHORT).show();
						break;
					case R.id.mostcomment_menu_allshowact: // 点击了评论最多
						Toast.makeText(AllShowActivity.this, "评论最多", Toast.LENGTH_SHORT).show();
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
	protected void initViews() {
		rela_noinfo_allshow = (RelativeLayout) findViewById(R.id.rela_noinfo_allshow);
		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.ptrlv_productshare);
		lv = mPullRefreshListView.getRefreshableView();
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
		lvAdapter = new LvAdapter();
		lv.setAdapter(lvAdapter);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Intent intent = new Intent(AllShowActivity.this, ShowDetailActivity.class);
				intent.putExtra("sid", list.get(arg2 - 1).getSdId());
				startActivity(intent);
			}
		});

		// Productshare3 parseJSON = GsonUtils.parseJSON(stringExtra,
		// Productshare3.class);
		// List<Shaidan3> shaidan = parseJSON.getShaidan();
		//
		// list.clear();
		// list.addAll(shaidan);
		// lvAdapter.notifyDataSetChanged();
		// if (list.size() == 0) {
		// rela_noinfo_allshow.setVisibility(View.VISIBLE);
		// } else {
		// rela_noinfo_allshow.setVisibility(View.GONE);
		// }
	}

	@Override
	protected void initEvents() {
		findViewById(R.id.pop).setOnClickListener(this);
		findViewById(R.id.back).setOnClickListener(this);
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		UMShareAPI.get(AllShowActivity.this).onActivityResult(arg0, arg1, arg2);
	}

}
