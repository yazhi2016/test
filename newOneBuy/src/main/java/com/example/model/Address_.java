package com.example.model;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Address_ implements Serializable{

    @Expose
    private String id;
    @Expose
    private String uid;
    @Expose
    private String sheng;
    @Expose
    private String shi;
    @Expose
    private String xian;
    @Expose
    private String jiedao;
    @Expose
    private String youbian;
    @Expose
    private String shouhuoren;
    @Expose
    private String mobile;
    @Expose
    private String tell;
    @SerializedName("default")
    @Expose
    private String _default;
    @Expose
    private String time;

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
     *     The sheng
     */
    public String getSheng() {
        return sheng;
    }

    /**
     * 
     * @param sheng
     *     The sheng
     */
    public void setSheng(String sheng) {
        this.sheng = sheng;
    }

    /**
     * 
     * @return
     *     The shi
     */
    public String getShi() {
        return shi;
    }

    /**
     * 
     * @param shi
     *     The shi
     */
    public void setShi(String shi) {
        this.shi = shi;
    }

    /**
     * 
     * @return
     *     The xian
     */
    public String getXian() {
        return xian;
    }

    /**
     * 
     * @param xian
     *     The xian
     */
    public void setXian(String xian) {
        this.xian = xian;
    }

    /**
     * 
     * @return
     *     The jiedao
     */
    public String getJiedao() {
        return jiedao;
    }

    /**
     * 
     * @param jiedao
     *     The jiedao
     */
    public void setJiedao(String jiedao) {
        this.jiedao = jiedao;
    }

    /**
     * 
     * @return
     *     The youbian
     */
    public String getYoubian() {
        return youbian;
    }

    /**
     * 
     * @param youbian
     *     The youbian
     */
    public void setYoubian(String youbian) {
        this.youbian = youbian;
    }

    /**
     * 
     * @return
     *     The shouhuoren
     */
    public String getShouhuoren() {
        return shouhuoren;
    }

    /**
     * 
     * @param shouhuoren
     *     The shouhuoren
     */
    public void setShouhuoren(String shouhuoren) {
        this.shouhuoren = shouhuoren;
    }

    /**
     * 
     * @return
     *     The mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 
     * @param mobile
     *     The mobile
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 
     * @return
     *     The tell
     */
    public String getTell() {
        return tell;
    }

    /**
     * 
     * @param tell
     *     The tell
     */
    public void setTell(String tell) {
        this.tell = tell;
    }

    /**
     * 
     * @return
     *     The _default
     */
    public String getDefault() {
        return _default;
    }

    /**
     * 
     * @param _default
     *     The default
     */
    public void setDefault(String _default) {
        this._default = _default;
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
