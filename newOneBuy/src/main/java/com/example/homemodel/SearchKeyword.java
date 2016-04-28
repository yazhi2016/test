package com.example.homemodel;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchKeyword {

    @SerializedName("have_keywored")
    @Expose
    private Integer haveKeywored;
    @SerializedName("0")
    @Expose
    private List<_0> _0 = new ArrayList<_0>();

    /**
     * 
     * @return
     *     The haveKeywored
     */
    public Integer getHaveKeywored() {
        return haveKeywored;
    }

    /**
     * 
     * @param haveKeywored
     *     The have_keywored
     */
    public void setHaveKeywored(Integer haveKeywored) {
        this.haveKeywored = haveKeywored;
    }

    /**
     * 
     * @return
     *     The _0
     */
    public List<_0> get0() {
        return _0;
    }

    /**
     * 
     * @param _0
     *     The 0
     */
    public void set0(List<_0> _0) {
        this._0 = _0;
    }

}
