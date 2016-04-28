package com.example.model;

import com.google.gson.annotations.Expose;

public class Datum3 {

    @Expose
    private String keywords;

    /**
     * 
     * @return
     *     The keywords
     */
    public String getKeywords() {
        return keywords;
    }

    /**
     * 
     * @param keywords
     *     The keywords
     */
    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

}
