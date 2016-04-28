package com.example.model;

import com.google.gson.annotations.Expose;

public class Appbean {

    @Expose
    private Integer code;
    @Expose
    private String message;
    @Expose
    private Data9 data;

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

    /**
     * 
     * @return
     *     The data
     */
    public Data9 getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(Data9 data) {
        this.data = data;
    }

}
