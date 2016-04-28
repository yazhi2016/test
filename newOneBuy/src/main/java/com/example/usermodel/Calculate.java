package com.example.usermodel;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Calculate {

    @SerializedName("sum_num")
    @Expose
    private Integer sumNum;
    @SerializedName("sum_time")
    @Expose
    private long sumTime;
    @Expose
    private Integer result;
    @Expose
    private String canyurenshu;
    @Expose
    private Integer code;
    @Expose
    private List<Datum> data = new ArrayList<Datum>();

    /**
     * 
     * @return
     *     The sumNum
     */
    public Integer getSumNum() {
        return sumNum;
    }

    /**
     * 
     * @param sumNum
     *     The sum_num
     */
    public void setSumNum(Integer sumNum) {
        this.sumNum = sumNum;
    }

    /**
     * 
     * @return
     *     The sumTime
     */
    public long getSumTime() {
        return sumTime;
    }

    /**
     * 
     * @param sumTime
     *     The sum_time
     */
    public void setSumTime(long sumTime) {
        this.sumTime = sumTime;
    }

    /**
     * 
     * @return
     *     The result
     */
    public Integer getResult() {
        return result;
    }

    /**
     * 
     * @param result
     *     The result
     */
    public void setResult(Integer result) {
        this.result = result;
    }

    /**
     * 
     * @return
     *     The canyurenshu
     */
    public String getCanyurenshu() {
        return canyurenshu;
    }

    /**
     * 
     * @param canyurenshu
     *     The canyurenshu
     */
    public void setCanyurenshu(String canyurenshu) {
        this.canyurenshu = canyurenshu;
    }

    /**
     * 
     * @return
     *     The code
     */
    public Integer getCode() {
        return code;
    }

    /**
     * 
     * @param code
     *     The code
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * 
     * @return
     *     The data
     */
    public List<Datum> getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(List<Datum> data) {
        this.data = data;
    }

}
