package com.example.showmodel;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Showdetail {

	@Expose
	private Integer tag;
	@SerializedName("tag_zan")
	@Expose
	private Integer tagZan;
	@Expose
	private String message;
	@Expose
	private User user;
	@SerializedName("gou_num")
	@Expose
	private Integer gouNum;
	@Expose
	private String sql;
	@SerializedName("shaidan_huifu")
	@Expose
	private List<ShaidanHuifu> shaidanHuifu = new ArrayList<ShaidanHuifu>();
	@Expose
	private Goods goods;
	@Expose
	private Shaidan shaidan;

	/**
	 * 
	 * @return The tag
	 */
	public Integer getTag() {
		return tag;
	}

	public Integer getTagZan() {
		return tagZan;
	}

	/**
	 * 
	 * @param tag
	 *            The tag
	 */
	public void setTag(Integer tag) {
		this.tag = tag;
	}

	/**
	 * 
	 * @return The message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * 
	 * @param message
	 *            The message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 
	 * @return The user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * 
	 * @param user
	 *            The user
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * 
	 * @return The gouNum
	 */
	public Integer getGouNum() {
		return gouNum;
	}

	/**
	 * 
	 * @param gouNum
	 *            The gou_num
	 */
	public void setGouNum(Integer gouNum) {
		this.gouNum = gouNum;
	}

	/**
	 * 
	 * @return The sql
	 */
	public String getSql() {
		return sql;
	}

	/**
	 * 
	 * @param sql
	 *            The sql
	 */
	public void setSql(String sql) {
		this.sql = sql;
	}

	/**
	 * 
	 * @return The shaidanHuifu
	 */
	public List<ShaidanHuifu> getShaidanHuifu() {
		return shaidanHuifu;
	}

	/**
	 * 
	 * @param shaidanHuifu
	 *            The shaidan_huifu
	 */
	public void setShaidanHuifu(List<ShaidanHuifu> shaidanHuifu) {
		this.shaidanHuifu = shaidanHuifu;
	}

	/**
	 * 
	 * @return The goods
	 */
	public Goods getGoods() {
		return goods;
	}

	/**
	 * 
	 * @param goods
	 *            The goods
	 */
	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	/**
	 * 
	 * @return The shaidan
	 */
	public Shaidan getShaidan() {
		return shaidan;
	}

	/**
	 * 
	 * @param shaidan
	 *            The shaidan
	 */
	public void setShaidan(Shaidan shaidan) {
		this.shaidan = shaidan;
	}

}
