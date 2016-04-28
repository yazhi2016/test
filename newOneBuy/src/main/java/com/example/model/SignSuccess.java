package com.example.model;

import com.google.gson.annotations.Expose;

public class SignSuccess {

    @Expose
    private String ushell;
    @Expose
    private String token;
    @Expose
    private User user;
    @Expose
    private Tag tag;

    /**
     * 
     * @return
     *     The ushell
     */
    public String getUshell() {
        return ushell;
    }

    /**
     * 
     * @param ushell
     *     The ushell
     */
    public void setUshell(String ushell) {
        this.ushell = ushell;
    }

    /**
     * 
     * @return
     *     The token
     */
    public String getToken() {
        return token;
    }

    /**
     * 
     * @param token
     *     The token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * 
     * @return
     *     The user
     */
    public User getUser() {
        return user;
    }

    /**
     * 
     * @param user
     *     The user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * 
     * @return
     *     The tag
     */
    public Tag getTag() {
        return tag;
    }

    /**
     * 
     * @param tag
     *     The tag
     */
    public void setTag(Tag tag) {
        this.tag = tag;
    }

}
