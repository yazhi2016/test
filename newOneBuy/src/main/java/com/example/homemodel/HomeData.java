package com.example.homemodel;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HomeData {

	@SerializedName("regist_num")
	@Expose
	private String registNum;
	@Expose
	private List<Lists1> lists1 = new ArrayList<Lists1>();
	@SerializedName("new_shop_list")
	@Expose
	private List<NewShopList> newShopList = new ArrayList<NewShopList>();
	@Expose
	private List<Country> countries = new ArrayList<Country>();
	@SerializedName("area_new")
	@Expose
	private List<AreaNew> areaNew = new ArrayList<AreaNew>();
	@SerializedName("area_fast")
	@Expose
	private List<AreaNew> areaFast = new ArrayList<AreaNew>();
	@SerializedName("area_hot")
	@Expose
	private List<AreaNew> areaHot = new ArrayList<AreaNew>();
	@SerializedName("area_price_asc")
	@Expose
	private List<AreaNew> areaPriceAsc = new ArrayList<AreaNew>();
	@SerializedName("area_new_desc")
	@Expose
	private List<AreaNew> areaNewDesc = new ArrayList<AreaNew>();

	/**
	 * 
	 * @return The lists1
	 */
	public List<Lists1> getLists1() {
		return lists1;
	}

	public String getRegistNum() {
		return registNum;
	}

	/**
	 * 
	 * @param lists1
	 *            The lists1
	 */
	public void setLists1(List<Lists1> lists1) {
		this.lists1 = lists1;
	}

	/**
	 * 
	 * @return The newShopList
	 */
	public List<NewShopList> getNewShopList() {
		return newShopList;
	}

	/**
	 * 
	 * @param newShopList
	 *            The new_shop_list
	 */
	public void setNewShopList(List<NewShopList> newShopList) {
		this.newShopList = newShopList;
	}

	/**
	 * 
	 * @return The countries
	 */
	public List<Country> getCountries() {
		return countries;
	}

	/**
	 * 
	 * @param countries
	 *            The countries
	 */
	public void setCountries(List<Country> countries) {
		this.countries = countries;
	}

	/**
	 * 
	 * @return The areaNew
	 */
	public List<AreaNew> getAreaNew() {
		return areaNew;
	}

	/**
	 * 
	 * @param areaNew
	 *            The area_new
	 */
	public void setAreaNew(List<AreaNew> areaNew) {
		this.areaNew = areaNew;
	}

	/**
	 * 
	 * @return The areaFast
	 */
	public List<AreaNew> getAreaFast() {
		return areaFast;
	}

	/**
	 * 
	 * @param areaFast
	 *            The area_fast
	 */
	public void setAreaFast(List<AreaNew> areaFast) {
		this.areaFast = areaFast;
	}

	/**
	 * 
	 * @return The areaHot
	 */
	public List<AreaNew> getAreaHot() {
		return areaHot;
	}

	/**
	 * 
	 * @param areaHot
	 *            The area_hot
	 */
	public void setAreaHot(List<AreaNew> areaHot) {
		this.areaHot = areaHot;
	}

	/**
	 * 
	 * @return The areaPriceAsc
	 */
	public List<AreaNew> getAreaPriceAsc() {
		return areaPriceAsc;
	}

	/**
	 * 
	 * @param areaPriceAsc
	 *            The area_price_asc
	 */
	public void setAreaPriceAsc(List<AreaNew> areaPriceAsc) {
		this.areaPriceAsc = areaPriceAsc;
	}

	/**
	 * 
	 * @return The areaNewDesc
	 */
	public List<AreaNew> getAreaNewDesc() {
		return areaNewDesc;
	}

	/**
	 * 
	 * @param areaNewDesc
	 *            The area_new_desc
	 */
	public void setAreaNewDesc(List<AreaNew> areaNewDesc) {
		this.areaNewDesc = areaNewDesc;
	}

}
