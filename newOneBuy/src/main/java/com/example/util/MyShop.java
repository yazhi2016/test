package com.example.util;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ModelContainer;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@ModelContainer
@Table(database = AppDatabase.class)
public class MyShop extends BaseModel {
	@PrimaryKey(autoincrement = true)
	public Long id;
	@Column
	String goodid;
	@Column
	String ima;
	@Column
	String num; //期数
	@Column
	public
	String buynum; //购买的数量
	@Column
	String title;
	@Column
	String remaind; //剩余数量
	@Column
	public
	String price; //单价
	@Column
	public
	String allprice; //总价格
	@Column
	public
	Boolean isCheck; //是否选中

	@Override
	public String toString() {
		return "\"" + goodid + "\":{\"num\":\"" + buynum + "\",\"shenyu\":\"" + remaind + "\",\"money\":\"" + allprice + "\"}";
	}

	public String getGoodid() {
		return goodid;
	}

	public void setGoodid(String goodid) {
		this.goodid = goodid;
	}

	public String getIma() {
		return ima;
	}

	public void setIma(String ima) {
		this.ima = ima;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getBuynum() {
		return buynum;
	}

	public void setBuynum(String buynum) {
		this.buynum = buynum;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRemaind() {
		return remaind;
	}

	public void setRemaind(String remaind) {
		this.remaind = remaind;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getAllprice() {
		return allprice;
	}

	public void setAllprice(String allprice) {
		this.allprice = allprice;
	}
	
	public boolean getIsCheck() {
		return isCheck;
	}
	
	public void setIsCheck(Boolean allprice) {
		this.isCheck = isCheck;
	}
	
	
}
