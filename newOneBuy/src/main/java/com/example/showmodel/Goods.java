package com.example.showmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Goods {

    @Expose
    private String title;
    @SerializedName("q_user_code")
    @Expose
    private String qUserCode;
    @SerializedName("q_uid")
    @Expose
    private String qUid;

    /**
     * 
     * @return
     *     The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     * @return
     *     The qUserCode
     */
    public String getQUserCode() {
        return qUserCode;
    }

    /**
     * 
     * @param qUserCode
     *     The q_user_code
     */
    public void setQUserCode(String qUserCode) {
        this.qUserCode = qUserCode;
    }

    /**
     * 
     * @return
     *     The qUid
     */
    public String getQUid() {
        return qUid;
    }

    /**
     * 
     * @param qUid
     *     The q_uid
     */
    public void setQUid(String qUid) {
        this.qUid = qUid;
    }

}
