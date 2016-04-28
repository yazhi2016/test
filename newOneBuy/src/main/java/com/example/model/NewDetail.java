package com.example.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewDetail {

    @Expose
    private Item item;
    @Expose
    private Integer tag;
    @Expose
    private String massage;
    @SerializedName("huode_shang")
    @Expose
    private HuodeShang huodeShang;
    @Expose
    private String category;
    @Expose
    private String brand;

    /**
     * 
     * @return
     *     The item
     */
    public Item getItem() {
        return item;
    }

    /**
     * 
     * @param item
     *     The item
     */
    public void setItem(Item item) {
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
    public HuodeShang getHuodeShang() {
        return huodeShang;
    }

    /**
     * 
     * @param huodeShang
     *     The huode_shang
     */
    public void setHuodeShang(HuodeShang huodeShang) {
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

}
