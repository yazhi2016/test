package com.example.model;

import com.google.gson.annotations.Expose;

public class Forgetpwd {

    @Expose
    private Integer tag;
    @Expose
    private Integer code;
    @Expose
    private String message;

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

}
