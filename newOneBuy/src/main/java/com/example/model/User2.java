package com.example.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User2 {

    @Expose
    private String username;
    @Expose
    private String qianming;
    @Expose
    private String sex;
    @SerializedName("address_now")
    @Expose
    private String addressNow;
    @SerializedName("address_home")
    @Expose
    private String addressHome;

    /**
     * 
     * @return
     *     The username
     */
    public String getUsername() {
        return username;
    }

    /**
     * 
     * @param username
     *     The username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 
     * @return
     *     The qianming
     */
    public String getQianming() {
        return qianming;
    }

    /**
     * 
     * @param qianming
     *     The qianming
     */
    public void setQianming(String qianming) {
        this.qianming = qianming;
    }

    /**
     * 
     * @return
     *     The sex
     */
    public String getSex() {
        return sex;
    }

    /**
     * 
     * @param sex
     *     The sex
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * 
     * @return
     *     The addressNow
     */
    public String getAddressNow() {
        return addressNow;
    }

    /**
     * 
     * @param addressNow
     *     The address_now
     */
    public void setAddressNow(String addressNow) {
        this.addressNow = addressNow;
    }

    /**
     * 
     * @return
     *     The addressHome
     */
    public String getAddressHome() {
        return addressHome;
    }

    /**
     * 
     * @param addressHome
     *     The address_home
     */
    public void setAddressHome(String addressHome) {
        this.addressHome = addressHome;
    }

}
