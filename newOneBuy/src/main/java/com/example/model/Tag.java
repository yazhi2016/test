package com.example.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tag {

    @Expose
    private String message;
    @SerializedName("0")
    @Expose
    private Integer _0;

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
     *     The _0
     */
    public Integer get0() {
        return _0;
    }

    /**
     * 
     * @param _0
     *     The 0
     */
    public void set0(Integer _0) {
        this._0 = _0;
    }

}
