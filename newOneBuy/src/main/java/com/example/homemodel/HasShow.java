package com.example.homemodel;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class HasShow {

    @Expose
    private List<ListItem> listItems = new ArrayList<ListItem>();
    @Expose
    private Integer code;
    @Expose
    private Integer postCount;

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
