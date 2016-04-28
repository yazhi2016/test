package com.example.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data8 {

    @Expose
    private String id;
    @SerializedName("wechar_service")
    @Expose
    private String wecharService;
    @SerializedName("QQ_service")
    @Expose
    private String QQService;
    @SerializedName("weibo_service")
    @Expose
    private String weiboService;
    @Expose
    private String address;
    @SerializedName("t_support")
    @Expose
    private String tSupport;

    /**
     * 
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The wecharService
     */
    public String getWecharService() {
        return wecharService;
    }

    /**
     * 
     * @param wecharService
     *     The wechar_service
     */
    public void setWecharService(String wecharService) {
        this.wecharService = wecharService;
    }

    /**
     * 
     * @return
     *     The QQService
     */
    public String getQQService() {
        return QQService;
    }

    /**
     * 
     * @param QQService
     *     The QQ_service
     */
    public void setQQService(String QQService) {
        this.QQService = QQService;
    }

    /**
     * 
     * @return
     *     The weiboService
     */
    public String getWeiboService() {
        return weiboService;
    }

    /**
     * 
     * @param weiboService
     *     The weibo_service
     */
    public void setWeiboService(String weiboService) {
        this.weiboService = weiboService;
    }

    /**
     * 
     * @return
     *     The address
     */
    public String getAddress() {
        return address;
    }

    /**
     * 
     * @param address
     *     The address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 
     * @return
     *     The tSupport
     */
    public String getTSupport() {
        return tSupport;
    }

    /**
     * 
     * @param tSupport
     *     The t_support
     */
    public void setTSupport(String tSupport) {
        this.tSupport = tSupport;
    }

}
