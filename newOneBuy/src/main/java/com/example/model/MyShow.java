package com.example.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyShow {

    @SerializedName("my_shaidan")
    @Expose
    private MyShaidan myShaidan;

    /**
     * 
     * @return
     *     The myShaidan
     */
    public MyShaidan getMyShaidan() {
        return myShaidan;
    }

    /**
     * 
     * @param myShaidan
     *     The my_shaidan
     */
    public void setMyShaidan(MyShaidan myShaidan) {
        this.myShaidan = myShaidan;
    }

}
