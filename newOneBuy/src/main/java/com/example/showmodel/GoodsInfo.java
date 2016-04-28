package com.example.showmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GoodsInfo {

	@SerializedName("q_uid")
	@Expose
	private String qUid;
	@SerializedName("q_user_code")
	@Expose
	private String qUserCode;
	@Expose
	private String title;
	@SerializedName("q_end_time")
	@Expose
	private String qEndTime;
	@SerializedName("need_hexiao")
	@Expose
	private String needHexiao;
	@Expose
	private String thumb;
	@Expose
	private String name;
	@Expose
	private String buy_num;
	@Expose
	private String qishu;

	/**
	 * 
	 * @return The qUid
	 */
	public String getQUid() {
		return qUid;
	}

	/**
	 * 
	 * @param qUid
	 *            The q_uid
	 */
	public void setQUid(String qUid) {
		this.qUid = qUid;
	}

	/**
	 * 
	 * @return The qUserCode
	 */
	public String getQUserCode() {
		return qUserCode;
	}

	/**
	 * 
	 * @param qUserCode
	 *            The q_user_code
	 */
	public void setQUserCode(String qUserCode) {
		this.qUserCode = qUserCode;
	}

	/**
	 * 
	 * @return The title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 
	 * @param title
	 *            The title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 
	 * @return The qEndTime
	 */
	public String getQEndTime() {
		return qEndTime;
	}

	/**
	 * 
	 * @param qEndTime
	 *            The q_end_time
	 */
	public void setQEndTime(String qEndTime) {
		this.qEndTime = qEndTime;
	}

	/**
	 * 
	 * @return The needHexiao
	 */
	public String getNeedHexiao() {
		return needHexiao;
	}

	/**
	 * 
	 * @param needHexiao
	 *            The need_hexiao
	 */
	public void setNeedHexiao(String needHexiao) {
		this.needHexiao = needHexiao;
	}

	/**
	 * 
	 * @return The thumb
	 */
	public String getThumb() {
		return thumb;
	}

	/**
	 * 
	 * @param thumb
	 *            The thumb
	 */
	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

	/**
	 * 
	 * @return The name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @param name
	 *            The name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @return The buy_num
	 */
	public String getBuy_num() {
		return buy_num;
	}

	/**
	 * 
	 * @param buy_num
	 *            The buy_num
	 */
	public void setBuy_num(String buy_num) {
		this.buy_num = buy_num;
	}

	/**
	 * 
	 * @return The qiShu
	 */
	public String getQiShu() {
		return qishu;
	}

	/**
	 * 
	 * @param qiShu
	 *            The qiShu
	 */
	public void setQiShu(String qiShu) {
		this.qishu = qiShu;
	}

}
