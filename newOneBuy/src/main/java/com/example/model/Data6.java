package com.example.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data6 {

    @SerializedName("rand_code")
    @Expose
    private Integer randCode;

    /**
     * 
     * @return
     *     The randCode
     */
    public Integer getRandCode() {
        return randCode;
    }

    /**
     * 
     * @param randCode
     *     The rand_code
     */
    public void setRandCode(Integer randCode) {
        this.randCode = randCode;
    }

}
