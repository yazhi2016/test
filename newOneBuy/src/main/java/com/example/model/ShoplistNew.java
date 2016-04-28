package com.example.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShoplistNew {

    @Expose
    private String id;
    @Expose
    private String title;
    @SerializedName("goods_price")
    @Expose
    private String goodsPrice;
    @SerializedName("goods_img")
    @Expose
    private String goodsImg;
    @SerializedName("goods_num")
    @Expose
    private String goodsNum;
    @SerializedName("is_hot")
    @Expose
    private String isHot;
    @SerializedName("sell_num")
    @Expose
    private String sellNum;
    @SerializedName("goods_jifen")
    @Expose
    private String goodsJifen;
    @Expose
    private String cateid;

    /**
     * 
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     * @return
     *     The goodsPrice
     */
    public String getGoodsPrice() {
        return goodsPrice;
    }

    /**
     * 
     * @param goodsPrice
     *     The goods_price
     */
    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    /**
     * 
     * @return
     *     The goodsImg
     */
    public String getGoodsImg() {
        return goodsImg;
    }

    /**
     * 
     * @param goodsImg
     *     The goods_img
     */
    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    /**
     * 
     * @return
     *     The goodsNum
     */
    public String getGoodsNum() {
        return goodsNum;
    }

    /**
     * 
     * @param goodsNum
     *     The goods_num
     */
    public void setGoodsNum(String goodsNum) {
        this.goodsNum = goodsNum;
    }

    /**
     * 
     * @return
     *     The isHot
     */
    public String getIsHot() {
        return isHot;
    }

    /**
     * 
     * @param isHot
     *     The is_hot
     */
    public void setIsHot(String isHot) {
        this.isHot = isHot;
    }

    /**
     * 
     * @return
     *     The sellNum
     */
    public String getSellNum() {
        return sellNum;
    }

    /**
     * 
     * @param sellNum
     *     The sell_num
     */
    public void setSellNum(String sellNum) {
        this.sellNum = sellNum;
    }

    /**
     * 
     * @return
     *     The goodsJifen
     */
    public String getGoodsJifen() {
        return goodsJifen;
    }

    /**
     * 
     * @param goodsJifen
     *     The goods_jifen
     */
    public void setGoodsJifen(String goodsJifen) {
        this.goodsJifen = goodsJifen;
    }

    /**
     * 
     * @return
     *     The cateid
     */
    public String getCateid() {
        return cateid;
    }

    /**
     * 
     * @param cateid
     *     The cateid
     */
    public void setCateid(String cateid) {
        this.cateid = cateid;
    }

}
