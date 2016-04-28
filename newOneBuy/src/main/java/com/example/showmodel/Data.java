package com.example.showmodel;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("goods_info")
    @Expose
    private GoodsInfo goodsInfo;
    @SerializedName("order_status")
    @Expose
    private OrderStatus orderStatus;
    
    Integer have_shaidan;
    
    @SerializedName("buy_list")
    @Expose
    private List<BuyList> buyList = new ArrayList<BuyList>();

    /**
     * 
     * @return
     *     The goodsInfo
     */
    public GoodsInfo getGoodsInfo() {
        return goodsInfo;
    }

    /**
     * 
     * @param goodsInfo
     *     The goods_info
     */
    public void setGoodsInfo(GoodsInfo goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    /**
     * 
     * @return
     *     The orderStatus
     */
    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    /**
     * 
     * @param orderStatus
     *     The order_status
     */
    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * 
     * @return
     *     The buyList
     */
    public List<BuyList> getBuyList() {
        return buyList;
    }

    /**
     * 
     * @param buyList
     *     The buy_list
     */
    public void setBuyList(List<BuyList> buyList) {
        this.buyList = buyList;
    }
    
    public Integer getHave_shaidan() {
    	return have_shaidan;
    }

}
