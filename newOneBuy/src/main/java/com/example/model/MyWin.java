package com.example.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class MyWin {

    @Expose
    private Integer tag;
    @Expose
    private String message;
    @Expose
    private List<Record> record = new ArrayList<Record>();

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
     *     The record
     */
    public List<Record> getRecord() {
        return record;
    }

    /**
     * 
     * @param record
     *     The record
     */
    public void setRecord(List<Record> record) {
        this.record = record;
    }

}
