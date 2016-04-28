package com.example.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllOrder {

    @SerializedName("order_all")
    @Expose
    private List<OrderAll> orderAll = new ArrayList<OrderAll>();
    @SerializedName("order_ongoing")
    @Expose
    private List<OrderAll> orderOngoing = new ArrayList<OrderAll>();
    @SerializedName("order_comple")
    @Expose
    private List<OrderAll> orderComple = new ArrayList<OrderAll>();

    /**
     * 
     * @return
     *     The orderAll
     */
    public List<OrderAll> getOrderAll() {
        return orderAll;
    }

    /**
     * 
     * @param orderAll
     *     The order_all
     */
    public void setOrderAll(List<OrderAll> orderAll) {
        this.orderAll = orderAll;
    }

    /**
     * 
     * @return
     *     The orderOngoing
     */
    public List<OrderAll> getOrderOngoing() {
        return orderOngoing;
    }

    /**
     * 
     * @param orderOngoing
     *     The order_ongoing
     */
    public void setOrderOngoing(List<OrderAll> orderOngoing) {
        this.orderOngoing = orderOngoing;
    }

    /**
     * 
     * @return
     *     The orderComple
     */
    public List<OrderAll> getOrderComple() {
        return orderComple;
    }

    /**
     * 
     * @param orderComple
     *     The order_comple
     */
    public void setOrderComple(List<OrderAll> orderComple) {
        this.orderComple = orderComple;
    }

}
