package com.example.util;

import com.raizlabs.android.dbflow.sql.language.Delete;

import java.math.BigDecimal;

public class Content {
	public static final boolean isWrite = true;
	// public static final String token = "fd7a92d5cbd8aeab8775c8199f931621";
	// public static final String uid = "21719";

	public static class URL {
		public static final String CITIES = "http://7xpso2.com1.z0.glb.clouddn.com/cities.txt";

		public static final String HOST = "http://pc88.gotoip4.com/index.php/app/";
		public static final String IMA_HOST = "http://pc88.gotoip4.com/statics/uploads/";
		public static final String HOME = HOST + "home?city=";
		public static final String IMADETAIL = HOST + "home/goodsdesc?goods_id=";
		public static final String GOODDETAIL = HOST + "home/item?goods_id=";
		public static final String CATEGORY = HOST + "home/get_category";
		public static final String AGREENMENT = HOST + "home/terms";
		public static final String SIGNIN = HOST + "user/login?mobile=";
		public static final String SCORECITY = HOST + "user/integral?cateid=";
		public static final String ALLPRODUCT = HOST + "home/get_glist?cateid=";
		public static final String SIGNINBYTOKEN = HOST + "/user/my_gou?token=";
		public static final String SIGNINVERIFY = HOST + "user/register?mobile="; // 注册验证码
		public static final String SEARCH = HOST + "home/search?keywords=";
		public static final String HOTKEYWORDS = HOST + "home/hot_search";
		public static final String ADDRESS = HOST + "my_home/address?token=";
		public static final String ALLORDER = HOST + "my_home/userbuylist?token=";
		public static final String REGISTER = HOST + "user/do_regist?mobile=";
		public static final String SIGNOUT = HOST + "user/logout?token=";
		public static final String CHANGEPWD = HOST + "user/change_new_pwd?token=";
		public static final String SCORECHANGE = HOST + "user/buy_integral?token=";
		public static final String CHANGEMYINFO = HOST + "user/do_edit_user";
		public static final String DELETEADDRESS = HOST + "my_home/deladdress?token=";
		public static final String DEFAULTADDRESS = HOST + "my_home/default_address?token=";
		public static final String CHANGEADDRESS = HOST + "my_home/do_change_address";
		public static final String ADDADDRESS = HOST + "my_home/do_add_address";
		public static final String ACCOUNTDETAIL = HOST + "my_home/userbalance?token=";
		public static final String ALLBUY = HOST + "home/buyrecords?goods_id=";
		public static final String SHARE = HOST + "shaidan/shaidanajax?type=";
		public static final String PRODUCTSHARE = HOST + "home/goodspost/?goods_sid=";
		public static final String SHOWLIKE = HOST + "shaidan/xianmu?sd_id=";
		public static final String MYWIN = HOST + "my_home/orderlist?token=";
		public static final String MYHASSHOW = HOST + "shopajax/getUserPostList?token=";
		public static final String MYNOSHOW = HOST + "shopajax/getUserUnPostList?token=";
		public static final String PAY = HOST + "cart/paysubmit?token=";
		public static final String RESULT = HOST + "home/dataserver?goods_id=";
		public static final String CALCULATE = HOST + "home/calResult?goods_id=";
		public static final String SHOWDETAIL = HOST + "shaidan/detail?sd_id=";
		public static final String COMMENT = HOST + "shaidan/plajax?token=";
		public static final String MYWINDETAIL = HOST + "user/buyDetail?token=";
		public static final String EXCHANGED = HOST + "user/my_integral?token="; // 兑换记录
		public static final String MYZERO = HOST + "user/my_zero?token="; // 抢购记录
		public static final String BUGZERO = HOST + "user/buy_zero?token="; // 抢购记录
		public static final String GETORDERID = HOST + "cart/get_order?token="; // 获得订单
		public static final String CLOUDPAY = HOST + "cart/yunpay"; // 云支付
		public static final String ORDERSTATE = HOST + "cart/get_order_state?token="; // 查询订单是否支付完成
		public static final String SENDSMS = HOST + "user/check_mobile?mobile="; // 忘记密码发送验证码
		public static final String FORGETPWD = HOST + "user/do_forgot_pwd?mobile="; // 忘记密码-修改密码
		public static final String IDEA = HOST + "home/opinion"; // 意见反馈
		public static final String SHOW = HOST + "user/comment"; // 晒单
		public static final String COMPANYDETAIL = HOST + "home/get_infomation"; // 公司信息
		public static final String TWOCODE = "http://m.yiyuanjg.com/system/modules/mobile/hexiao.php?goods_id="; // 二维码
		public static final String QQLOGIN = HOST + "user/third_login_callback"; // 第三方登陆
		public static final String GETGOODS = HOST + "user/complete?token="; // 确认收货
	}

	public static class RequstCode {
		public static final int mefraToSetact = 1;
		public static final int myinfractToChangeSignact = 2;
		public static final int myinfractToChangeNameact = 3;
		public static final int myinfractToChangeQQact = 4;
	}

	public static class ResultCode {
		public static final int SetactToMefra = 1;
		public static final int changeSignactctToMyinfra = 2;
		public static final int changeNameactctToMyinfra = 3;
		public static final int changeQQactctToMyinfra = 4;
	}

	/**
	 * 输入两个String类型的数，得到两个数相除的结果
	 */
	public static int StringDivide(String small, String big) {
		int nowNum = Integer.parseInt(small);
		int allNum = Integer.parseInt(big);
		float nowPercent = nowNum / (float) allNum;
		BigDecimal b = new BigDecimal(nowPercent);
		float f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
		int nowPercent2 = (int) (f1 * 100);
		return nowPercent2;
	}
	public static int StringDivide2(String small, String big) {
		int nowNum = Integer.parseInt(small);
		int allNum = Integer.parseInt(big);
		float nowPercent = (allNum-nowNum) / (float) allNum;
		BigDecimal b = new BigDecimal(nowPercent);
		float f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
		int nowPercent2 = (int) (f1 * 100);
		return nowPercent2;
	}

//	public static void writeToLocal(String arg0, Context activity) {
//		if (isWrite) {
//			try {
//				FileOutputStream openFileOutput = activity.openFileOutput("123.txt", 0);
//				// 0代表MODE_PRIVATE
//				openFileOutput.write(arg0.getBytes());
//				openFileOutput.close();
//				Log.e("==", "writeSuccess" + arg0);
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	}

	public static void addShop(String goodid, String ima, String num, String buynum, String title, String remaind,
			String price, String allprice, boolean isCheck) {
		MyShop shop = new MyShop();
		shop.goodid = goodid;
		shop.ima = ima;
		shop.num = num;
		shop.buynum = buynum;
		shop.title = title;
		shop.remaind = remaind;
		shop.price = price;
		shop.allprice = allprice;
		shop.isCheck = isCheck;
		shop.save();
	}

	public static void deleteShop(String goodid) {
		new Delete().from(MyShop.class).where(MyShop_Table.goodid.eq(goodid)).querySingle();
	}
}