package com.example.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class Data5 {

    @Expose
    private List<ListItem2> listItems = new ArrayList<ListItem2>();
    @Expose
    private Integer code;
    @Expose
    private Integer postCount;

    /**
     * 
     * @return
     *     The listItems
     */
    public List<ListItem2> getListItems() {
        return listItems;
    }

    /**
     * 
     * @param listItems
     *     The listItems
     */
    public void setListItems(List<ListItem2> listItems) {
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
     *     The postCount
     */
    public Integer getPostCount() {
        return postCount;
    }

    /**
     * 
     * @param postCount
     *     The postCount
     */
    public void setPostCount(Integer postCount) {
        this.postCount = postCount;
    }

}
