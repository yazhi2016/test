package com.example.homemodel;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllProduct {

    @SerializedName("shoplist_new")
    @Expose
    private List<ShoplistNew> shoplistNew = new ArrayList<ShoplistNew>();
    @SerializedName("shoplist_fast")
    @Expose
    private List<ShoplistNew> shoplistFast = new ArrayList<ShoplistNew>();
    @SerializedName("shoplist_hot")
    @Expose
    private List<ShoplistNew> shoplistHot = new ArrayList<ShoplistNew>();
    @SerializedName("shoplist_priceasc")
    @Expose
    private List<ShoplistNew> shoplistPriceasc = new ArrayList<ShoplistNew>();
    @SerializedName("shoplist_pricdesc")
    @Expose
    private List<ShoplistNew> shoplistPricdesc = new ArrayList<ShoplistNew>();

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
    public List<ShoplistNew> getShoplistFast() {
        return shoplistFast;
    }

    /**
     * 
     * @param shoplistFast
     *     The shoplist_fast
     */
    public void setShoplistFast(List<ShoplistNew> shoplistFast) {
        this.shoplistFast = shoplistFast;
    }

    /**
     * 
     * @return
     *     The shoplistHot
     */
    public List<ShoplistNew> getShoplistHot() {
        return shoplistHot;
    }

    /**
     * 
     * @param shoplistHot
     *     The shoplist_hot
     */
    public void setShoplistHot(List<ShoplistNew> shoplistHot) {
        this.shoplistHot = shoplistHot;
    }

    /**
     * 
     * @return
     *     The shoplistPriceasc
     */
    public List<ShoplistNew> getShoplistPriceasc() {
        return shoplistPriceasc;
    }

    /**
     * 
     * @param shoplistPriceasc
     *     The shoplist_priceasc
     */
    public void setShoplistPriceasc(List<ShoplistNew> shoplistPriceasc) {
        this.shoplistPriceasc = shoplistPriceasc;
    }

    /**
     * 
     * @return
     *     The shoplistPricdesc
     */
    public List<ShoplistNew> getShoplistPricdesc() {
        return shoplistPricdesc;
    }

    /**
     * 
     * @param shoplistPricdesc
     *     The shoplist_pricdesc
     */
    public void setShoplistPricdesc(List<ShoplistNew> shoplistPricdesc) {
        this.shoplistPricdesc = shoplistPricdesc;
    }

}
