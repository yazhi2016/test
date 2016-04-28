package com.example.model;

import com.google.gson.annotations.Expose;

public class HotKeyword {

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
