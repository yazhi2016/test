package com.example.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class Hotsearch {

    @Expose
    private Integer tag;
    @Expose
    private String message;
    @Expose
    private List<Datum3> data = new ArrayList<Datum3>();

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
    public List<Datum3> getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(List<Datum3> data) {
        this.data = data;
    }

}
