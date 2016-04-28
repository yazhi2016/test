package com.example.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QishuInfo {

    @Expose
    private String id;
    @Expose
    private String qishu;

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
     *     The qishu
     */
    public String getQishu() {
        return qishu;
    }

    /**
     * 
     * @param qishu
     *     The qishu
     */
    public void setQishu(String qishu) {
        this.qishu = qishu;
    }


}
