package com.example.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class MyShaidan {

    @Expose
    private List<ListItem> listItems = new ArrayList<ListItem>();
    @Expose
    private Integer code;

    /**
     * 
     * @return
     *     The listItems
     */
    public List<ListItem> getListItems() {
        return listItems;
    }

    /**
     * 
     * @param listItems
     *     The listItems
     */
    public void setListItems(List<ListItem> listItems) {
        this.listItems = listItems;
    }

    /**
     * 
     * @return
     *     The code
     */
    public Integer getCode() {
        return code;
    }

    /**
     * 
     * @param code
     *     The code
     */
    public void setCode(Integer code) {
        this.code = code;
    }

}
