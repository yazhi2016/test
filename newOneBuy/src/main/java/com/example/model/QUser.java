package com.example.model;

import com.google.gson.annotations.Expose;

public class QUser {

    @Expose
    private String uid;
    @Expose
    private String username;
    @Expose
    private Object email;
    @Expose
    private String mobile;
    @Expose
    private String img;

    /**
     * 
     * @return
     *     The uid
     */
    public String getUid() {
        return uid;
    }

    /**
     * 
     * @param uid
     *     The uid
     */
    public void setUid(String uid) {
        this.uid = uid;
    }

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
     *     The email
     */
    public Object getEmail() {
        return email;
    }

    /**
     * 
     * @param email
     *     The email
     */
    public void setEmail(Object email) {
        this.email = email;
    }

    /**
     * 
     * @return
     *     The mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 
     * @param mobile
     *     The mobile
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 
     * @return
     *     The img
     */
    public String getImg() {
        return img;
    }

    /**
     * 
     * @param img
     *     The img
     */
    public void setImg(String img) {
        this.img = img;
    }

}
