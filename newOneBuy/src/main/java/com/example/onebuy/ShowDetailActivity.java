package com.example.onebuy;

import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.android.volley.VolleyError;
import com.example.newonebuy.R;
import com.example.homemodel.SignInSuccessByToken;
import com.example.model.SignOut;
import com.example.showmodel.Shaidan;
import com.example.showmodel.ShaidanHuifu;
import com.example.showmodel.Showdetail;
import com.example.util.Content;
import com.example.util.GlobalVariable;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;
import com.xinbo.utils.GsonUtils;
import com.xinbo.utils.HTTPUtils;
import com.xinbo.utils.UILUtils;
import com.xinbo.utils.VolleyListener;
import com.xinbo.widget.ListView4ScrollView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class ShowDetailActivity extends ActionBarActivity implements OnClickListener {

	private ImageView ima_like_showdetail;
	private String sid;
	private ImageView ima;
	private TextView text_name;
	private TextView text_buynum;
	private TextView text_title;
	private TextView text_content;
	private ListView4ScrollView lv_showdetail;
	ArrayList<String> imalist = new ArrayList<String>();
	ArrayList<ShaidanHuifu> commentList = new ArrayList<ShaidanHuifu>();
	private LvAdapter lvAdapter;
	String token;
	String uid;
	private SharedPreferences sp;
	private TextView text_commentnum;
	private TextView text_likenum;
	boolean isLike = false;
	String likeNum;
	private ScrollView scrollView1;
	private TextView text_showtime;
	private RelativeLayout rela_hascomment;
	private TextView text_commentnum2;
	private ListView4ScrollView lv_comment;
	private CommentAdapter commentAdapter;
	boolean isLogIn;
	private RelativeLayout rela_comment_edit;
	private EditText edit_comment;
	private int startMode;

	// 分享
	final SHARE_MEDIA[] displaylist = new SHARE_MEDIA[] { SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,
			SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE };

	private ShareBoardlistener shareBoardlistener = new ShareBoardlistener() {

		@Override
		public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
			if (share_media == null) {
				if (snsPlatform.mKeyword.equals("11")) {
					Toast.makeText(ShowDetailActivity.this, "add button success", Toast.LENGTH_LONG).show();
				}

			} else {
				new ShareAction(ShowDetailActivity.this).setPlatform(share_media).setCallback(umShareListener)
						.withText("赶快下载吧！").withTitle("一元妙购").withTargetUrl("http://pc88.gotoip4.com/index.php/mobile/shaidan/detail/" + sid)
						.withMedia(new UMImage(ShowDetailActivity.this,
								BitmapFactory.decodeResource(getResources(), R.drawable.logo)))
						.share();
			}
		}
	};

	UMShareListener umShareListener = new UMShareListener() {
		@Override
		public void onResult(SHARE_MEDIA platform) {
			Toast.makeText(ShowDetailActivity.this, "分享成功啦", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onError(SHARE_MEDIA platform, Throwable t) {
			Toast.makeText(ShowDetailActivity.this, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onCancel(SHARE_MEDIA platform) {
			Toast.makeText(ShowDetailActivity.this, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_detail);
		sp = getSharedPreferences("token", 0);
		uid = sp.getString("uid", "");
		Intent intent = getIntent();
		sid = intent.getStringExtra("sid");
		startMode = intent.getIntExtra("comment", -1);
		findViewById(R.id.back).setOnClickListener(this);
		findViewById(R.id.rela_share).setOnClickListener(this);
		ima_like_showdetail = (ImageView) findViewById(R.id.ima_like_showdetail);
		findViewById(R.id.rela_like).setOnClickListener(this);
		initUI();
		if (startMode == 1) { // 点击评论进来的
			rela_comment_edit.setVisibility(View.VISIBLE);
		}
	}

	@Override
	protected void onResume() { // 判断登陆状态
		super.onResume();
		token = sp.getString("token", "");
		uid = sp.getString("uid", "");
		if (token.equals("")) { // 未登录
			isLogIn = false;
			initData();
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
				if (parseJSON.getTag() == 2) { // 未登录
					isLogIn = false;
					return;
				} else {
					isLogIn = true;
				}
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
				if (parseJSON.getTag() == 2) { // 未登录
					isLogIn = false;
					return;
				} else {
					isLogIn = true;
				}
				initData();
			}

			@Override
			public void onErrorResponse(VolleyError arg0) {

			}
		});
	}

	private void initUI() {
		text_showtime = (TextView) findViewById(R.id.text_showtime);
		scrollView1 = (ScrollView) findViewById(R.id.scrollView1);
		ima = (ImageView) findViewById(R.id.imageView2);
		text_name = (TextView) findViewById(R.id.textView2);
		text_likenum = (TextView) findViewById(R.id.textView5);
		text_commentnum = (TextView) findViewById(R.id.textView6);
		text_buynum = (TextView) findViewById(R.id.textView8);
		text_title = (TextView) findViewById(R.id.textView7);
		text_content = (TextView) findViewById(R.id.text_content);
		lv_showdetail = (ListView4ScrollView) findViewById(R.id.lv_showdetail);
		lvAdapter = new LvAdapter();
		lv_showdetail.setAdapter(lvAdapter);
		rela_hascomment = (RelativeLayout) findViewById(R.id.rela_hascomment);
		text_commentnum2 = (TextView) findViewById(R.id.text_commentnum2);
		lv_comment = (ListView4ScrollView) findViewById(R.id.lv_comment);
		commentAdapter = new CommentAdapter();
		lv_comment.setAdapter(commentAdapter);
		findViewById(R.id.rela_comment).setOnClickListener(this);
		rela_comment_edit = (RelativeLayout) findViewById(R.id.rela_comment_edit);
		edit_comment = (EditText) findViewById(R.id.edit_comment);
		findViewById(R.id.btn_cancel).setOnClickListener(this);
		findViewById(R.id.btn_ccomment).setOnClickListener(this);
	}

	class LvAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return imalist.size();
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
			View view = getLayoutInflater().inflate(R.layout.item_showdetail, null);
			ImageView ima_allshow = (ImageView) view.findViewById(R.id.ima_allshow);
			GlobalVariable.displayImageNoAnim(Content.URL.IMA_HOST + imalist.get(position), ima_allshow);
			return view;
		}

	}

	class CommentAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return commentList.size();
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
			View view = getLayoutInflater().inflate(R.layout.item_comment, null);
			TextView text_name = (TextView) view.findViewById(R.id.textView2);
			TextView text_content = (TextView) view.findViewById(R.id.textView3);
			TextView text_time = (TextView) view.findViewById(R.id.textView4);
			ImageView ima = (ImageView) view.findViewById(R.id.imageView2);
			ShaidanHuifu shaidanHuifu = commentList.get(position);
			GlobalVariable.displayImageNoAnim(Content.URL.IMA_HOST + shaidanHuifu.getImg(), ima);
			text_name.setText(shaidanHuifu.getUsername());
			text_content.setText(shaidanHuifu.getSdhfContent());
			text_time.setText(shaidanHuifu.getSdhfTime());
			return view;
		}

	}

	private void initData() {
		String url;
		if (isLogIn) { // 登陆状态
			url = Content.URL.SHOWDETAIL + sid + "&uid=" + uid + "&token=" + token;
		} else {
			url = Content.URL.SHOWDETAIL + sid + "&uid=0";
		}
		// String url = Content.URL.SHOWDETAIL + sid + "&uid=" + uid;
		HTTPUtils.get(this, url, new VolleyListener() {

			@Override
			public void onResponse(String arg0) {
				Showdetail parseJSON = GsonUtils.parseJSON(arg0, Showdetail.class);
				Shaidan shaidan = parseJSON.getShaidan();
				Integer tag22 = parseJSON.getTag();
				if (tag22 == 1) {
					Integer tag2 = parseJSON.getTagZan();
					Log.e("==", tag2 + "1赞过-登陆状态" + isLogIn);
					if (tag2 == 0) { // 未赞过
						isLike = false;
						ima_like_showdetail.setImageDrawable(getResources().getDrawable(R.drawable.like1));
					} else if (tag2 == 1) { // 赞过
						isLike = true;
						ima_like_showdetail.setImageDrawable(getResources().getDrawable(R.drawable.like2));
					}

					GlobalVariable.displayImageNoAnim(Content.URL.IMA_HOST + parseJSON.getUser().getImg(), ima);
					text_name.setText(parseJSON.getUser().getUsername());
					text_buynum.setText(parseJSON.getGouNum() + ""); // 购买次数
					text_title.setText(parseJSON.getGoods().getTitle()); // 商品名字
					text_content.setText(shaidan.getSdContent());
					List<String> sdPhotolist = shaidan.getSdPhotolist();
					imalist.clear();
					imalist.addAll(sdPhotolist);
					lvAdapter.notifyDataSetChanged();
					text_likenum.setText(shaidan.getSdZhan() + "");
					likeNum = shaidan.getSdZhan() + "";
					text_commentnum.setText(shaidan.getSdPing() + "");
					text_showtime.setText(shaidan.getSdTime());

					scrollView1.smoothScrollBy(0, 0);

					commentList.clear();
					List<ShaidanHuifu> shaidanHuifu = parseJSON.getShaidanHuifu();
					commentList.addAll(shaidanHuifu);
					if (shaidanHuifu.size() == 0) { // 没有回复
						rela_hascomment.setVisibility(View.GONE);
					} else {
						rela_hascomment.setVisibility(View.VISIBLE);
						text_commentnum2.setText(shaidanHuifu.size() + "");
						commentAdapter.notifyDataSetChanged();
					}
					scrollView1.smoothScrollBy(0, 0);
				} else {
					Toast.makeText(ShowDetailActivity.this, parseJSON.getMessage(), Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void onErrorResponse(VolleyError arg0) {

			}
		});
	}

	public void like(String goodid, final TextView t, final String num) {
		// String url = Content.URL.SHOWLIKE + goodid + "&uid=" + uid;
		String url = Content.URL.SHOWLIKE + sid + "&uid=" + uid + "&token=" + token;
		HTTPUtils.get(ShowDetailActivity.this, url, new VolleyListener() {

			@Override
			public void onResponse(String arg0) {
				Spanned fromHtml = Html.fromHtml(arg0);
				SignOut parseJSON = GsonUtils.parseJSON(fromHtml + "", SignOut.class);
				if (parseJSON.getTag() == 1) { // 点赞成功
					Toast.makeText(ShowDetailActivity.this, "点赞成功", Toast.LENGTH_SHORT).show();
					int parseInt = Integer.parseInt(num);
					parseInt++;
					t.setText(parseInt + "");
					ima_like_showdetail.setImageDrawable(getResources().getDrawable(R.drawable.like2));
				} else if (parseJSON.getTag() == 2) { // 已点赞过
					Toast.makeText(ShowDetailActivity.this, "已点过赞", Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void onErrorResponse(VolleyError arg0) {

			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rela_share: // 分享
			new ShareAction(ShowDetailActivity.this).setDisplayList(displaylist).withText("呵呵").withTitle("title")
					.withTargetUrl("http://pc88.gotoip4.com/index.php/mobile/shaidan/detail/" + sid)
					.withMedia(new UMImage(ShowDetailActivity.this,
							BitmapFactory.decodeResource(getResources(), R.drawable.like1)))
					.setListenerList(umShareListener).setShareboardclickCallback(shareBoardlistener).open();

			break;
		case R.id.rela_comment: // 点击评论
			rela_comment_edit.setVisibility(View.VISIBLE);
			break;
		case R.id.back:
			finish();
			break;
		case R.id.btn_cancel: // 点击取消
			rela_comment_edit.setVisibility(View.GONE);
			break;
		case R.id.btn_ccomment: // 点击评论
			String string = edit_comment.getText().toString();
			if (string.isEmpty()) {
				edit_comment.setError("评论不能为空");
				return;
			}
			signInByToken();
			if (isLogIn) {
				comment(string);
			} else { // 未登录
				Toast.makeText(ShowDetailActivity.this, "请登陆", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(ShowDetailActivity.this, LogInActivity.class);
				intent.putExtra("state", "notsafe");
				startActivity(intent);
			}
			break;
		case R.id.rela_like: // 点击喜欢按钮
			signInByToken(); // 判断登陆状态防止token失效
			if (isLogIn) {
				if (!isLike) {
					like(sid, text_likenum, likeNum);
				} else {
					Toast.makeText(ShowDetailActivity.this, "已赞过", Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(ShowDetailActivity.this, "请登陆", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(ShowDetailActivity.this, LogInActivity.class);
				intent.putExtra("state", "notsafe");
				startActivity(intent);
			}
			break;
		default:
			break;
		}
	}

	private void comment(String str) {
		String encode;
		try {
			encode = URLEncoder.encode(str, "utf-8");
			String url = Content.URL.COMMENT + token + "&uid=" + uid + "&token=" + token + "&content=" + encode
					+ "&sd_id=" + sid;
			HTTPUtils.get(ShowDetailActivity.this, url, new VolleyListener() {

				@Override
				public void onResponse(String arg0) {
					SignOut parseJSON = GsonUtils.parseJSON(arg0, SignOut.class);
					Integer tag2 = parseJSON.getTag();
					if (tag2 == 1) {
						Toast.makeText(ShowDetailActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
						initData();
						rela_comment_edit.setVisibility(View.GONE);
						edit_comment.setText("");
					} else {
						Toast.makeText(ShowDetailActivity.this, "评论失败:" + parseJSON.getMessage(), Toast.LENGTH_SHORT)
								.show();
					}
				}

				@Override
				public void onErrorResponse(VolleyError arg0) {
					// TODO Auto-generated method stub

				}
			});
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		UMShareAPI.get(ShowDetailActivity.this).onActivityResult(arg0, arg1, arg2);
	}

}
