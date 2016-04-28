package com.example.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Detail2 {

    @Expose
    private Item2 item;
    @Expose
    private Integer tag;
    @Expose
    private String massage;
    @SerializedName("huode_shang")
    @Expose
    private HuodeShang2 huodeShang;
    @SerializedName("qishu_info")
    @Expose
    private List<QishuInfo> qishuInfo = new ArrayList<QishuInfo>();
    @Expose
    private String category;
    @Expose
    private String brand;
    @Expose
    private String time;
    @Expose
    private String message;
    
    /**
     * 
     * @return
     *     The qishuInfo
     */
    public List<QishuInfo> getQishuInfo() {
        return qishuInfo;
    }

    /**
     * 
     * @return
     *     The item
     */
    public Item2 getItem() {
        return item;
    }

    /**
     * 
     * @param item
     *     The item
     */
    public void setItem(Item2 item) {
        this.item = item;
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
     *     The massage
     */
    public String getMassage() {
        return massage;
    }

    /**
     * 
     * @param massage
     *     The massage
     */
    public void setMassage(String massage) {
        this.massage = massage;
    }

    /**
     * 
     * @return
     *     The huodeShang
     */
    public HuodeShang2 getHuodeShang() {
        return huodeShang;
    }

    /**
     * 
     * @param huodeShang
     *     The huode_shang
     */
    public void setHuodeShang(HuodeShang2 huodeShang) {
        this.huodeShang = huodeShang;
    }

    /**
     * 
     * @return
     *     The category
     */
    public String getCategory() {
        return category;
    }

    /**
     * 
     * @param category
     *     The category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * 
     * @return
     *     The brand
     */
    public String getBrand() {
        return brand;
    }

    /**
     * 
     * @param brand
     *     The brand
     */
    public void setBrand(String brand) {
        this.brand = brand;
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
