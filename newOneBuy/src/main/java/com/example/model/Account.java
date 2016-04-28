package com.example.model;

import com.google.gson.annotations.Expose;

public class Account {

    @Expose
    private String uid;
    @Expose
    private String type;
    @Expose
    private String pay;
    @Expose
    private String content;
    @Expose
    private String money;
    @Expose
    private String time;

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
     *     The type
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 
     * @return
     *     The pay
     */
    public String getPay() {
        return pay;
    }

    /**
     * 
     * @param pay
     *     The pay
     */
    public void setPay(String pay) {
        this.pay = pay;
    }

    /**
     * 
     * @return
     *     The content
     */
    public String getContent() {
        return content;
    }

    /**
     * 
     * @param content
     *     The content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 
     * @return
     *     The money
     */
    public String getMoney() {
        return money;
    }

    /**
     * 
     * @param money
     *     The money
     */
    public void setMoney(String money) {
        this.money = money;
    }

    /**
     * 
     * @return
     *     The time
     */
    public String getTime() {
        return time;
    }

    /**
     * 
     * @param time
     *     The time
     */
    public void setTime(String time) {
        this.time = time;
    }

}
