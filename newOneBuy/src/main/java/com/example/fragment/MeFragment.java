package com.example.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ScrollingView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.homemodel.SignInSuccessByToken;
import com.example.model.User;
import com.example.newonebuy.R;
import com.example.onebuy.AccountDetailActivity;
import com.example.onebuy.AddressActivity;
import com.example.onebuy.AgreementActivity;
import com.example.onebuy.AllOrderActivity;
import com.example.onebuy.ChargeActivity;
import com.example.onebuy.ExchangeActivity;
import com.example.onebuy.LogInActivity;
import com.example.onebuy.MyShowActivity;
import com.example.onebuy.MyWinActivity;
import com.example.onebuy.SetActivity;
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
import com.xinbo.utils.VolleyListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class MeFragment extends Fragment implements OnClickListener {

	private View view;
	private ScrollingView scrollview;
	private TextView text_bar;
	private RelativeLayout bar;
	LayoutInflater inflater;
	private RelativeLayout rela_back;
	private RelativeLayout rela_set;
	ArrayList<User> user = new ArrayList<User>();
	String token;
	String uid;
	private TextView text_name_mefra;
	private ImageView ima_user_mefra;
	private TextView text_score_mefra;
	String userInfo;
	String isSignIn;
	private TextView text_balance_mefra;
	private SharedPreferences sp;
	private RelativeLayout rela_wait_mafra;
	private RelativeLayout rela_readinfo;
	String score;

	public MeFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			this.inflater = inflater;
			view = inflater.inflate(R.layout.fragment_me2, container, false);
			sp = getActivity().getSharedPreferences("token", 0);

			bar = (RelativeLayout) view.findViewById(R.id.bar);
			view.findViewById(R.id.rela_address_recoder_mefra).setOnClickListener(this);
			rela_set = (RelativeLayout) view.findViewById(R.id.set);
			rela_set.setOnClickListener(this);
			view.findViewById(R.id.btn_pay_mefra).setOnClickListener(this);
			view.findViewById(R.id.btn_pay_mefra2).setOnClickListener(this);
			view.findViewById(R.id.rela_buy_recoder_mefra).setOnClickListener(this);
			view.findViewById(R.id.rela_recharge_recoder_mefra).setOnClickListener(this);
			view.findViewById(R.id.rela_pay_recoder_mefra).setOnClickListener(this);
			view.findViewById(R.id.rela_agreement_recoder_mefra).setOnClickListener(this);
			view.findViewById(R.id.rela_win_recoder_mefra).setOnClickListener(this);
			view.findViewById(R.id.rela_myorder_recoder_mefra).setOnClickListener(this);
			view.findViewById(R.id.rela_wexchange_recoder_mefra).setOnClickListener(this);
			view.findViewById(R.id.rela_zero_recoder_mefra).setOnClickListener(this);
			view.findViewById(R.id.rela_myinvite_recoder_mefra).setOnClickListener(this);
			rela_wait_mafra = (RelativeLayout) view.findViewById(R.id.rela_wait_mafra);
			rela_readinfo = (RelativeLayout) view.findViewById(R.id.rela_readinfo);
			text_balance_mefra = (TextView) view.findViewById(R.id.text_balance_mefra);
			text_name_mefra = (TextView) view.findViewById(R.id.text_name_mefra);
			ima_user_mefra = (ImageView) view.findViewById(R.id.ima_user_mefra);
			text_score_mefra = (TextView) view.findViewById(R.id.text_score_mefra);
			text_bar = (TextView) view.findViewById(R.id.text_bar);
			rela_back = (RelativeLayout) view.findViewById(R.id.back);

			// Bundle arguments = getArguments();
			// isSignIn = arguments.getString("isSignIn");
			// if (isSignIn != null) {
			// if (isSignIn.equals("yes")) { //登陆状态
			// userInfo = arguments.getString("userInfo");
			// Spanned fromHtml = Html.fromHtml(userInfo);
			// SignInSuccessByToken parseJSON = GsonUtils.parseJSON(fromHtml +
			// "", SignInSuccessByToken.class);
			// com.example.homemodel.User user2 = parseJSON.getUser();
			// rela_wait_mafra.setVisibility(View.GONE);
			// rela_set.setVisibility(View.VISIBLE);
			// text_bar.setText("我的金库");
			// text_name_mefra.setText(user2.getUsername());
			// UILUtils.displayImageNoAnim(Content.URL.IMA_HOST +
			// user2.getImg(), ima_user_mefra);
			// text_score_mefra.setText("积分：" + user2.getFscore() + "分");
			// text_balance_mefra.setText("余额：" + user2.getMoney() + "金币");
			// rela_readinfo.setVisibility(View.INVISIBLE);
			// } else {
			// Log.e("==", "login2");
			// Intent intent = new Intent(getActivity(), LogInActivity.class);
			// intent.putExtra("state", "safe");
			// startActivity(intent);
			// }
			// }
		}
		return view;
	}

	@Override
	public void onResume() { // 进入界面都要判断是否需要登陆
		super.onResume();
		token = sp.getString("token", "");
		uid = sp.getString("uid", "");
		if (token.equals("")) {
			isSignIn = "no";
		} else {
			isSignIn = "yes";
		}
		rela_readinfo.setVisibility(View.VISIBLE);
		if (isSignIn.equals("yes")) { // 有token,判断是否失效
			text_score_mefra.setText("积分：分");
			text_balance_mefra.setText("余额：金币");
			rela_wait_mafra.setVisibility(View.GONE);
			signInByToken();
		} else if (isSignIn.equals("no")) { // 退出状态
			Intent intent = new Intent(getActivity(), LogInActivity.class);
			intent.putExtra("state", "safe");
			startActivity(intent);
			rela_wait_mafra.setVisibility(View.GONE);
			rela_set.setVisibility(View.GONE);
			text_bar.setText("登陆");
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Content.ResultCode.SetactToMefra) {
			boolean booleanExtra = data.getBooleanExtra("isSignOut", false);
			if (booleanExtra) {
				isSignIn = "no";
				token = "";
				uid = "";
			}
		}
		UMShareAPI.get(getActivity()).onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rela_myinvite_recoder_mefra: // 邀请好友
			new ShareAction(getActivity()).setDisplayList(displaylist).withText("呵呵").withTitle("title")
					.withTargetUrl("http://pc88.gotoip4.com?=" + GlobalVariable.getInstance().getSpUid())
					.withMedia(
							new UMImage(getActivity(), BitmapFactory.decodeResource(getResources(), R.drawable.like1)))
					.setListenerList(umShareListener).setShareboardclickCallback(shareBoardlistener).open();
			break;
		case R.id.rela_zero_recoder_mefra: // 抢购记录
			Intent intent = new Intent(getActivity(), ExchangeActivity.class);
			intent.putExtra("fromWhere", "zero");
			startActivity(intent);
			break;
		case R.id.rela_wexchange_recoder_mefra: // 点击兑换记录
			Intent intent2 = new Intent(getActivity(), ExchangeActivity.class);
			intent2.putExtra("fromWhere", "exchange");
			startActivity(intent2);
			break;
		case R.id.rela_address_recoder_mefra: // 点击我的地址
			startActivity(new Intent(getActivity(), AddressActivity.class));
			break;
		case R.id.rela_myorder_recoder_mefra: // 点击我的晒单
			startActivity(new Intent(getActivity(), MyShowActivity.class));
			break;
		case R.id.rela_win_recoder_mefra: // 点击中奖记录
			startActivity(new Intent(getActivity(), MyWinActivity.class));
			break;
		case R.id.set: // 点击个人资料
			Intent intent6 = new Intent(getActivity(), SetActivity.class);
			intent6.putExtra("token", token);
			intent6.putExtra("uid", uid);
			startActivityForResult(intent6, Content.RequstCode.mefraToSetact);
			break;
		case R.id.rela_buy_recoder_mefra: // 点击妙购记录
			startActivity(new Intent(getActivity(), AllOrderActivity.class));
			break;
		case R.id.rela_recharge_recoder_mefra: // 点击充值记录
			Intent intent3 = new Intent(getActivity(), AccountDetailActivity.class);
			intent3.putExtra("fromcharge", "charge");
			startActivity(intent3);
			break;
		case R.id.rela_pay_recoder_mefra: // 点击消费记录
			Intent intent4 = new Intent(getActivity(), AccountDetailActivity.class);
			intent4.putExtra("fromcharge", "pay");
			startActivity(intent4);
			break;
		case R.id.btn_pay_mefra: // 点击充值
		case R.id.btn_pay_mefra2:
			startActivity(new Intent(getActivity(), ChargeActivity.class));
			break;
		case R.id.rela_agreement_recoder_mefra: // 点击服务协议
			startActivity(new Intent(getActivity(), AgreementActivity.class));
			break;
		default:
			break;
		}
	}

	public void signInByToken() {
		String url = Content.URL.SIGNINBYTOKEN + token + "&uid=" + uid;
		HTTPUtils.get(getActivity(), url, new VolleyListener() {

			@Override
			public void onResponse(String arg0) {
				Spanned fromHtml = Html.fromHtml(arg0);
				SignInSuccessByToken parseJSON = GsonUtils.parseJSON(fromHtml + "", SignInSuccessByToken.class);
				if (parseJSON.getTag() == 2) { // 未登录
					Toast.makeText(getActivity(), "请重新登陆", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(getActivity(), LogInActivity.class);
					intent.putExtra("state", "safe");
					startActivity(intent);
					rela_set.setVisibility(View.GONE);
					text_bar.setText("登陆");
					return;
				}
				com.example.homemodel.User user2 = parseJSON.getUser();
				rela_wait_mafra.setVisibility(View.GONE);
				rela_set.setVisibility(View.VISIBLE);
				text_bar.setText("我的金库");
				text_name_mefra.setText(user2.getUsername());
				
				if(!(user2.getImg() == null)) {
					GlobalVariable.displayImageNoAnim(Content.URL.IMA_HOST + user2.getImg(), ima_user_mefra);
				}
				if (user2.getFscore() == null) {
					text_score_mefra.setText("积分：0分");
				} else {
					text_score_mefra.setText("积分：" + user2.getFscore() + "分");
				}
				text_balance_mefra.setText("余额：" + user2.getMoney() + "金币");
				rela_readinfo.setVisibility(View.INVISIBLE);
				Editor edit = sp.edit();
				edit.putString("score", user2.getScore());
				edit.commit();
			}

			@Override
			public void onErrorResponse(VolleyError arg0) {

			}
		});
	}

	// 分享
	final SHARE_MEDIA[] displaylist = new SHARE_MEDIA[] {SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,
			SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE };

	private ShareBoardlistener shareBoardlistener = new ShareBoardlistener() {

		@Override
		public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
			if (share_media == null) {
				if (snsPlatform.mKeyword.equals("11")) {
					Toast.makeText(getActivity(), "add button success", Toast.LENGTH_LONG).show();
				}

			} else {
				new ShareAction(getActivity()).setPlatform(share_media).setCallback(umShareListener).withText("赶快下载吧！")
						.withTitle("一元妙购").withTargetUrl("http://pc88.gotoip4.com/?=" + GlobalVariable.getInstance().getSpUid()).withMedia(new UMImage(getActivity(),
								BitmapFactory.decodeResource(getResources(), R.drawable.logo)))
						.share();
			}
		}
	};

	UMShareListener umShareListener = new UMShareListener() {
		@Override
		public void onResult(SHARE_MEDIA platform) {
			Toast.makeText(getActivity(), "分享成功啦", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onError(SHARE_MEDIA platform, Throwable t) {
			Toast.makeText(getActivity(), platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onCancel(SHARE_MEDIA platform) {
			Toast.makeText(getActivity(), platform + " 分享取消了", Toast.LENGTH_SHORT).show();
		}
	};

}
