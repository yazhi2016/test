package com.example.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Complete {

    @Expose
    private String id;
    @Expose
    private String code;
    @SerializedName("code_tmp")
    @Expose
    private Object codeTmp;
    @Expose
    private String username;
    @Expose
    private Object uphoto;
    @Expose
    private String uid;
    @Expose
    private String shopid;
    @Expose
    private String shopname;
    @Expose
    private String shopqishu;
    @Expose
    private Object gonumber;
    @Expose
    private String goucode;
    @Expose
    private String moneycount;
    @Expose
    private String huode;
    @SerializedName("pay_type")
    @Expose
    private String payType;
    @Expose
    private Object ip;
    @Expose
    private String status;
    @SerializedName("company_money")
    @Expose
    private String companyMoney;
    @SerializedName("company_code")
    @Expose
    private Object companyCode;
    @Expose
    private Object company;
    @Expose
    private String time;
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
     *     The code
     */
    public String getCode() {
        return code;
    }

    /**
     * 
     * @param code
     *     The code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 
     * @return
     *     The codeTmp
     */
    public Object getCodeTmp() {
        return codeTmp;
    }

    /**
     * 
     * @param codeTmp
     *     The code_tmp
     */
    public void setCodeTmp(Object codeTmp) {
        this.codeTmp = codeTmp;
    }

    /**
     * 
     * @return
     *     The username
     */
    public String getUsername() {
        return username;
    }

    /**
     * 
     * @param username
     *     The username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 
     * @return
     *     The uphoto
     */
    public Object getUphoto() {
        return uphoto;
    }

    /**
     * 
     * @param uphoto
     *     The uphoto
     */
    public void setUphoto(Object uphoto) {
        this.uphoto = uphoto;
    }

    /**
     * 
     * @return
     *     The uid
     */
    public String getUid() {
        return uid;
    }

    /**
     * 
     * @param uid
     *     The uid
     */
    public void setUid(String uid) {
        this.uid = uid;
    }

    /**
     * 
     * @return
     *     The shopid
     */
    public String getShopid() {
        return shopid;
    }

    /**
     * 
     * @param shopid
     *     The shopid
     */
    public void setShopid(String shopid) {
        this.shopid = shopid;
    }

    /**
     * 
     * @return
     *     The shopname
     */
    public String getShopname() {
        return shopname;
    }

    /**
     * 
     * @param shopname
     *     The shopname
     */
    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    /**
     * 
     * @return
     *     The shopqishu
     */
    public String getShopqishu() {
        return shopqishu;
    }

    /**
     * 
     * @param shopqishu
     *     The shopqishu
     */
    public void setShopqishu(String shopqishu) {
        this.shopqishu = shopqishu;
    }

    /**
     * 
     * @return
     *     The gonumber
     */
    public Object getGonumber() {
        return gonumber;
    }

    /**
     * 
     * @param gonumber
     *     The gonumber
     */
    public void setGonumber(Object gonumber) {
        this.gonumber = gonumber;
    }

    /**
     * 
     * @return
     *     The goucode
     */
    public String getGoucode() {
        return goucode;
    }

    /**
     * 
     * @param goucode
     *     The goucode
     */
    public void setGoucode(String goucode) {
        this.goucode = goucode;
    }

    /**
     * 
     * @return
     *     The moneycount
     */
    public String getMoneycount() {
        return moneycount;
    }

    /**
     * 
     * @param moneycount
     *     The moneycount
     */
    public void setMoneycount(String moneycount) {
        this.moneycount = moneycount;
    }

    /**
     * 
     * @return
     *     The huode
     */
    public String getHuode() {
        return huode;
    }

    /**
     * 
     * @param huode
     *     The huode
     */
    public void setHuode(String huode) {
        this.huode = huode;
    }

    /**
     * 
     * @return
     *     The payType
     */
    public String getPayType() {
        return payType;
    }

    /**
     * 
     * @param payType
     *     The pay_type
     */
    public void setPayType(String payType) {
        this.payType = payType;
    }

    /**
     * 
     * @return
     *     The ip
     */
    public Object getIp() {
        return ip;
    }

    /**
     * 
     * @param ip
     *     The ip
     */
    public void setIp(Object ip) {
        this.ip = ip;
    }

    /**
     * 
     * @return
     *     The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 
     * @return
     *     The companyMoney
     */
    public String getCompanyMoney() {
        return companyMoney;
    }

    /**
     * 
     * @param companyMoney
     *     The company_money
     */
    public void setCompanyMoney(String companyMoney) {
        this.companyMoney = companyMoney;
    }

    /**
     * 
     * @return
     *     The companyCode
     */
    public Object getCompanyCode() {
        return companyCode;
    }

    /**
     * 
     * @param companyCode
     *     The company_code
     */
    public void setCompanyCode(Object companyCode) {
        this.companyCode = companyCode;
    }

    /**
     * 
     * @return
     *     The company
     */
    public Object getCompany() {
        return company;
    }

    /**
     * 
     * @param company
     *     The company
     */
    public void setCompany(Object company) {
        this.company = company;
    }

    /**
     * 
     * @return
     *     The time
     */
    public String getTime() {
        return time;
    }

    /**
     * 
     * @param time
     *     The time
     */
    public void setTime(String time) {
        this.time = time;
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
