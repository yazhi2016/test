package com.example.model;

import com.google.gson.annotations.Expose;

public class Allmyshow {

    @Expose
    private Integer tag;
    @Expose
    private String message;
    @Expose
    private Data5 data;

    /**
     * 
     * @return
     *     The tag
     */
    public Integer getTag() {
        return tag;
    }

    /**
     * 
     * @param tag
     *     The tag
     */
    public void setTag(Integer tag) {
        this.tag = tag;
    }

    /**
     * 
     * @return
     *     The message
     */
    public String getMessage() {
        return message;
    }

    /**
     * 
     * @param message
     *     The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 
     * @return
     *     The data
     */
    public Data5 getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(Data5 data) {
        this.data = data;
    }

}
