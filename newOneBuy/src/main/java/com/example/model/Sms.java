package com.example.model;

import com.google.gson.annotations.Expose;

public class Sms {

    @Expose
    private Integer tag;
    @Expose
    private String message;
    @Expose
    private Data6 data;

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
     *     The massage
     */
    public String getMassage() {
        return message;
    }

    /**
     * 
     * @param massage
     *     The massage
     */
    public void setMassage(String massage) {
        this.message = massage;
    }

    /**
     * 
     * @return
     *     The data
     */
    public Data6 getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(Data6 data) {
        this.data = data;
    }

}
