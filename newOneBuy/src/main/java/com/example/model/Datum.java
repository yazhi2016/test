package com.example.model;

import com.google.gson.annotations.Expose;

public class Datum {

    @Expose
    private Integer YunTaoId;
    @Expose
    private Integer GoodsId;
    @Expose
    private String Name;
    @Expose
    private Integer Number;
    @Expose
    private String Pic;
    @Expose
    private Double price;
    @Expose
    private Integer QuantityTotal;
    @Expose
    private Integer Quantity;
    @Expose
    private Integer QuantityNeed;

    /**
     * 
     * @return
     *     The YunTaoId
     */
    public Integer getYunTaoId() {
        return YunTaoId;
    }

    /**
     * 
     * @param YunTaoId
     *     The YunTaoId
     */
    public void setYunTaoId(Integer YunTaoId) {
        this.YunTaoId = YunTaoId;
    }

    /**
     * 
     * @return
     *     The GoodsId
     */
    public Integer getGoodsId() {
        return GoodsId;
    }

    /**
     * 
     * @param GoodsId
     *     The GoodsId
     */
    public void setGoodsId(Integer GoodsId) {
        this.GoodsId = GoodsId;
    }

    /**
     * 
     * @return
     *     The Name
     */
    public String getName() {
        return Name;
    }

    /**
     * 
     * @param Name
     *     The Name
     */
    public void setName(String Name) {
        this.Name = Name;
    }

    /**
     * 
     * @return
     *     The Number
     */
    public Integer getNumber() {
        return Number;
    }

    /**
     * 
     * @param Number
     *     The Number
     */
    public void setNumber(Integer Number) {
        this.Number = Number;
    }

    /**
     * 
     * @return
     *     The Pic
     */
    public String getPic() {
        return Pic;
    }

    /**
     * 
     * @param Pic
     *     The Pic
     */
    public void setPic(String Pic) {
        this.Pic = Pic;
    }

    /**
     * 
     * @return
     *     The price
     */
    public Double getPrice() {
        return price;
    }

    /**
     * 
     * @param price
     *     The price
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * 
     * @return
     *     The QuantityTotal
     */
    public Integer getQuantityTotal() {
        return QuantityTotal;
    }

    /**
     * 
     * @param QuantityTotal
     *     The QuantityTotal
     */
    public void setQuantityTotal(Integer QuantityTotal) {
        this.QuantityTotal = QuantityTotal;
    }

    /**
     * 
     * @return
     *     The Quantity
     */
    public Integer getQuantity() {
        return Quantity;
    }

    /**
     * 
     * @param Quantity
     *     The Quantity
     */
    public void setQuantity(Integer Quantity) {
        this.Quantity = Quantity;
    }

    /**
     * 
     * @return
     *     The QuantityNeed
     */
    public Integer getQuantityNeed() {
        return QuantityNeed;
    }

    /**
     * 
     * @param QuantityNeed
     *     The QuantityNeed
     */
    public void setQuantityNeed(Integer QuantityNeed) {
        this.QuantityNeed = QuantityNeed;
    }

}
