package com.example.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data7 {

    @Expose
    private Goods2 goods;
    @Expose
    private List<String> pic = new ArrayList<String>();
    @SerializedName("q_user")
    @Expose
    private QUser qUser;
    @SerializedName("q_user_code_len")
    @Expose
    private Integer qUserCodeLen;
    @Expose
    private List<Qishu> qishu = new ArrayList<Qishu>();
    @SerializedName("code_arr")
    @Expose
    private List<Object> codeArr = new ArrayList<Object>();
    @SerializedName("user_shop_number")
    @Expose
    private Integer userShopNumber;
    @SerializedName("user_shop_time")
    @Expose
    private String userShopTime;
    @SerializedName("user_shop_codes")
    @Expose
    private String userShopCodes;

    /**
     * 
     * @return
     *     The goods
     */
    public Goods2 getGoods() {
        return goods;
    }

    /**
     * 
     * @param goods
     *     The goods
     */
    public void setGoods(Goods2 goods) {
        this.goods = goods;
    }

    /**
     * 
     * @return
     *     The pic
     */
    public List<String> getPic() {
        return pic;
    }

    /**
     * 
     * @param pic
     *     The pic
     */
    public void setPic(List<String> pic) {
        this.pic = pic;
    }

    /**
     * 
     * @return
     *     The qUser
     */
    public QUser getQUser() {
        return qUser;
    }

    /**
     * 
     * @param qUser
     *     The q_user
     */
    public void setQUser(QUser qUser) {
        this.qUser = qUser;
    }

    /**
     * 
     * @return
     *     The qUserCodeLen
     */
    public Integer getQUserCodeLen() {
        return qUserCodeLen;
    }

    /**
     * 
     * @param qUserCodeLen
     *     The q_user_code_len
     */
    public void setQUserCodeLen(Integer qUserCodeLen) {
        this.qUserCodeLen = qUserCodeLen;
    }

    /**
     * 
     * @return
     *     The qishu
     */
    public List<Qishu> getQishu() {
        return qishu;
    }

    /**
     * 
     * @param qishu
     *     The qishu
     */
    public void setQishu(List<Qishu> qishu) {
        this.qishu = qishu;
    }

    /**
     * 
     * @return
     *     The codeArr
     */
    public List<Object> getCodeArr() {
        return codeArr;
    }

    /**
     * 
     * @param codeArr
     *     The code_arr
     */
    public void setCodeArr(List<Object> codeArr) {
        this.codeArr = codeArr;
    }

    /**
     * 
     * @return
     *     The userShopNumber
     */
    public Integer getUserShopNumber() {
        return userShopNumber;
    }

    /**
     * 
     * @param userShopNumber
     *     The user_shop_number
     */
    public void setUserShopNumber(Integer userShopNumber) {
        this.userShopNumber = userShopNumber;
    }

    /**
     * 
     * @return
     *     The userShopTime
     */
    public String getUserShopTime() {
        return userShopTime;
    }

    /**
     * 
     * @param userShopTime
     *     The user_shop_time
     */
    public void setUserShopTime(String userShopTime) {
        this.userShopTime = userShopTime;
    }

    /**
     * 
     * @return
     *     The userShopCodes
     */
    public String getUserShopCodes() {
        return userShopCodes;
    }

    /**
     * 
     * @param userShopCodes
     *     The user_shop_codes
     */
    public void setUserShopCodes(String userShopCodes) {
        this.userShopCodes = userShopCodes;
    }

}
