package com.example.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class Goods {

    @Expose
    private String content;
    @Expose
    private List<String> picarr = new ArrayList<String>();

    /**
     * 
     * @return
     *     The content
     */
    public String getContent() {
        return content;
    }

    /**
     * 
     * @param content
     *     The content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 
     * @return
     *     The picarr
     */
    public List<String> getPicarr() {
        return picarr;
    }

    /**
     * 
     * @param picarr
     *     The picarr
     */
    public void setPicarr(List<String> picarr) {
        this.picarr = picarr;
    }

}
