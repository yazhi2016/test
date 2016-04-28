package com.example.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class UnShow {

    @Expose
    private List<ListItem> listItems = new ArrayList<ListItem>();
    @Expose
    private Integer code;
    @Expose
    private Integer unPostCount;

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

    /**
     * 
     * @return
     *     The unPostCount
     */
    public Integer getUnPostCount() {
        return unPostCount;
    }

    /**
     * 
     * @param unPostCount
     *     The unPostCount
     */
    public void setUnPostCount(Integer unPostCount) {
        this.unPostCount = unPostCount;
    }

}
