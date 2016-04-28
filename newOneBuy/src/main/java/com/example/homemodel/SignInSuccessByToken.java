package com.example.homemodel;

import com.example.model.Tag;
import com.google.gson.annotations.Expose;

public class SignInSuccessByToken {

    @Expose
    private User user;
    @Expose
    private Integer tag;
    @Expose
    private String message;

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
