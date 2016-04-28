package com.example.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum2 {

	@SerializedName("sd_id")
	@Expose
	private String sdId;
	@SerializedName("is_zan")
	@Expose
	private Integer isZan;
	@SerializedName("sd_userid")
	@Expose
	private String sdUserid;
	@SerializedName("sd_shopid")
	@Expose
	private String sdShopid;
	@SerializedName("sd_qishu")
	@Expose
	private Object sdQishu;
	@SerializedName("sd_ip")
	@Expose
	private String sdIp;
	@SerializedName("sd_title")
	@Expose
	private String sdTitle;
	@SerializedName("sd_thumbs")
	@Expose
	private String sdThumbs;
	@SerializedName("sd_content")
	@Expose
	private String sdContent;
	@SerializedName("sd_photolist")
	@Expose
	private String sdPhotolist;
	@SerializedName("sd_zhan")
	@Expose
	private String sdZhan;
	@SerializedName("sd_ping")
	@Expose
	private String sdPing;
	@SerializedName("sd_time")
	@Expose
	private String sdTime;
	@SerializedName("sd_shopsid")
	@Expose
	private String sdShopsid;
	@Expose
	private String user;
	@Expose
	private String time;
	@Expose
	private String pic;
	@Expose
	private Integer page;
	@Expose
	private Integer sum;

	/**
	 * 
	 * @return The sdId
	 */
	public String getSdId() {
		return sdId;
	}

	public Integer getIsZan() {
		return isZan;
	}

	/**
	 * 
	 * @param sdId
	 *            The sd_id
	 */
	public void setSdId(String sdId) {
		this.sdId = sdId;
	}

	/**
	 * 
	 * @return The sdUserid
	 */
	public String getSdUserid() {
		return sdUserid;
	}

	/**
	 * 
	 * @param sdUserid
	 *            The sd_userid
	 */
	public void setSdUserid(String sdUserid) {
		this.sdUserid = sdUserid;
	}

	/**
	 * 
	 * @return The sdShopid
	 */
	public String getSdShopid() {
		return sdShopid;
	}

	/**
	 * 
	 * @param sdShopid
	 *            The sd_shopid
	 */
	public void setSdShopid(String sdShopid) {
		this.sdShopid = sdShopid;
	}

	/**
	 * 
	 * @return The sdQishu
	 */
	public Object getSdQishu() {
		return sdQishu;
	}

	/**
	 * 
	 * @param sdQishu
	 *            The sd_qishu
	 */
	public void setSdQishu(Object sdQishu) {
		this.sdQishu = sdQishu;
	}

	/**
	 * 
	 * @return The sdIp
	 */
	public String getSdIp() {
		return sdIp;
	}

	/**
	 * 
	 * @param sdIp
	 *            The sd_ip
	 */
	public void setSdIp(String sdIp) {
		this.sdIp = sdIp;
	}

	/**
	 * 
	 * @return The sdTitle
	 */
	public String getSdTitle() {
		return sdTitle;
	}

	/**
	 * 
	 * @param sdTitle
	 *            The sd_title
	 */
	public void setSdTitle(String sdTitle) {
		this.sdTitle = sdTitle;
	}

	/**
	 * 
	 * @return The sdThumbs
	 */
	public String getSdThumbs() {
		return sdThumbs;
	}

	/**
	 * 
	 * @param sdThumbs
	 *            The sd_thumbs
	 */
	public void setSdThumbs(String sdThumbs) {
		this.sdThumbs = sdThumbs;
	}

	/**
	 * 
	 * @return The sdContent
	 */
	public String getSdContent() {
		return sdContent;
	}

	/**
	 * 
	 * @param sdContent
	 *            The sd_content
	 */
	public void setSdContent(String sdContent) {
		this.sdContent = sdContent;
	}

	/**
	 * 
	 * @return The sdPhotolist
	 */
	public String getSdPhotolist() {
		return sdPhotolist;
	}

	/**
	 * 
	 * @param sdPhotolist
	 *            The sd_photolist
	 */
	public void setSdPhotolist(String sdPhotolist) {
		this.sdPhotolist = sdPhotolist;
	}

	/**
	 * 
	 * @return The sdZhan
	 */
	public String getSdZhan() {
		return sdZhan;
	}

	/**
	 * 
	 * @param sdZhan
	 *            The sd_zhan
	 */
	public void setSdZhan(String sdZhan) {
		this.sdZhan = sdZhan;
	}

	/**
	 * 
	 * @return The sdPing
	 */
	public String getSdPing() {
		return sdPing;
	}

	/**
	 * 
	 * @param sdPing
	 *            The sd_ping
	 */
	public void setSdPing(String sdPing) {
		this.sdPing = sdPing;
	}

	/**
	 * 
	 * @return The sdTime
	 */
	public String getSdTime() {
		return sdTime;
	}

	/**
	 * 
	 * @param sdTime
	 *            The sd_time
	 */
	public void setSdTime(String sdTime) {
		this.sdTime = sdTime;
	}

	/**
	 * 
	 * @return The sdShopsid
	 */
	public String getSdShopsid() {
		return sdShopsid;
	}

	/**
	 * 
	 * @param sdShopsid
	 *            The sd_shopsid
	 */
	public void setSdShopsid(String sdShopsid) {
		this.sdShopsid = sdShopsid;
	}

	/**
	 * 
	 * @return The user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * 
	 * @param user
	 *            The user
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * 
	 * @return The time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * 
	 * @param time
	 *            The time
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * 
	 * @return The pic
	 */
	public String getPic() {
		return pic;
	}

	/**
	 * 
	 * @param pic
	 *            The pic
	 */
	public void setPic(String pic) {
		this.pic = pic;
	}

	/**
	 * 
	 * @return The page
	 */
	public Integer getPage() {
		return page;
	}

	/**
	 * 
	 * @param page
	 *            The page
	 */
	public void setPage(Integer page) {
		this.page = page;
	}

	/**
	 * 
	 * @return The sum
	 */
	public Integer getSum() {
		return sum;
	}

	/**
	 * 
	 * @param sum
	 *            The sum
	 */
	public void setSum(Integer sum) {
		this.sum = sum;
	}

}
