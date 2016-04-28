package com.example.usermodel;

import com.google.gson.annotations.Expose;

public class Qishu {

    @Expose
    private String qishu;
    @Expose
    private String id;

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

}
