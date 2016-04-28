package com.example.zeromodel;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Zero {

    @SerializedName("shoplist_new")
    @Expose
    private List<ShoplistNew> shoplistNew = new ArrayList<ShoplistNew>();
    @SerializedName("shoplist_fast")
    @Expose
    private List<ShoplistFast> shoplistFast = new ArrayList<ShoplistFast>();
    @SerializedName("shoplist_hot")
    @Expose
    private List<ShoplistHot> shoplistHot = new ArrayList<ShoplistHot>();
    @SerializedName("shoplist_priceasc")
    @Expose
    private List<ShoplistPriceasc> shoplistPriceasc = new ArrayList<ShoplistPriceasc>();
    @SerializedName("shoplist_pricdesc")
    @Expose
    private List<ShoplistPricdesc> shoplistPricdesc = new ArrayList<ShoplistPricdesc>();

    /**
     * 
     * @return
     *     The shoplistNew
     */
    public List<ShoplistNew> getShoplistNew() {
        return shoplistNew;
    }

    /**
     * 
     * @param shoplistNew
     *     The shoplist_new
     */
    public void setShoplistNew(List<ShoplistNew> shoplistNew) {
        this.shoplistNew = shoplistNew;
    }

    /**
     * 
     * @return
     *     The shoplistFast
     */
    public List<ShoplistFast> getShoplistFast() {
        return shoplistFast;
    }

    /**
     * 
     * @param shoplistFast
     *     The shoplist_fast
     */
    public void setShoplistFast(List<ShoplistFast> shoplistFast) {
        this.shoplistFast = shoplistFast;
    }

    /**
     * 
     * @return
     *     The shoplistHot
     */
    public List<ShoplistHot> getShoplistHot() {
        return shoplistHot;
    }

    /**
     * 
     * @param shoplistHot
     *     The shoplist_hot
     */
    public void setShoplistHot(List<ShoplistHot> shoplistHot) {
        this.shoplistHot = shoplistHot;
    }

    /**
     * 
     * @return
     *     The shoplistPriceasc
     */
    public List<ShoplistPriceasc> getShoplistPriceasc() {
        return shoplistPriceasc;
    }

    /**
     * 
     * @param shoplistPriceasc
     *     The shoplist_priceasc
     */
    public void setShoplistPriceasc(List<ShoplistPriceasc> shoplistPriceasc) {
        this.shoplistPriceasc = shoplistPriceasc;
    }

    /**
     * 
     * @return
     *     The shoplistPricdesc
     */
    public List<ShoplistPricdesc> getShoplistPricdesc() {
        return shoplistPricdesc;
    }

    /**
     * 
     * @param shoplistPricdesc
     *     The shoplist_pricdesc
     */
    public void setShoplistPricdesc(List<ShoplistPricdesc> shoplistPricdesc) {
        this.shoplistPricdesc = shoplistPricdesc;
    }

}
