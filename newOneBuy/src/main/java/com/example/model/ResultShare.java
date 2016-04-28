package com.example.model;

import com.google.gson.annotations.Expose;

public class ResultShare {

    @Expose
    private Data7 data;
    @Expose
    private Integer tag;
    @Expose
    private String message;

    /**
     * 
     * @return
     *     The data
     */
    public Data7 getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(Data7 data) {
        this.data = data;
    }

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

}
