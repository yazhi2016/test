package com.example.model;

import com.google.gson.annotations.Expose;

public class ImaDetail {

    @Expose
    private Goods goods;
    private String message;
    
    public String getMessage() {
    	return message;
    }

    /**
     * 
     * @return
     *     The goods
     */
    public Goods getGoods() {
        return goods;
    }

    /**
     * 
     * @param goods
     *     The goods
     */
    public void setGoods(Goods goods) {
        this.goods = goods;
    }

}
