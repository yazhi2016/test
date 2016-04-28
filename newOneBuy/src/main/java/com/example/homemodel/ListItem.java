package com.example.homemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListItem {

    @SerializedName("sd_id")
    @Expose
    private String sdId;
    @SerializedName("sd_userid")
    @Expose
    private String sdUserid;
    @SerializedName("sd_shopid")
    @Expose
    private String sdShopid;
    @SerializedName("sd_qishu")
    @Expose
    private Object sdQishu;
    @SerializedName("sd_ip")
    @Expose
    private String sdIp;
    @SerializedName("sd_title")
    @Expose
    private String sdTitle;
    @SerializedName("sd_thumbs")
    @Expose
    private String sdThumbs;
    @SerializedName("sd_content")
    @Expose
    private String sdContent;
    @SerializedName("sd_photolist")
    @Expose
    private String sdPhotolist;
    @SerializedName("sd_zhan")
    @Expose
    private String sdZhan;
    @SerializedName("sd_ping")
    @Expose
    private String sdPing;
    @SerializedName("sd_time")
    @Expose
    private String sdTime;
    @SerializedName("sd_shopsid")
    @Expose
    private String sdShopsid;
    @Expose
    private String id;
    @Expose
    private String sid;
    @Expose
    private String cateid;
    @Expose
    private String brandid;
    @Expose
    private String title;
    @SerializedName("title_style")
    @Expose
    private String titleStyle;
    @Expose
    private String title2;
    @Expose
    private String keywords;
    @Expose
    private String description;
    @Expose
    private String money;
    @Expose
    private String yunjiage;
    @Expose
    private String zongrenshu;
    @Expose
    private String canyurenshu;
    @Expose
    private String shenyurenshu;
    @SerializedName("def_renshu")
    @Expose
    private String defRenshu;
    @Expose
    private String qishu;
    @Expose
    private String maxqishu;
    @Expose
    private String thumb;
    @Expose
    private String picarr;
    @Expose
    private String content;
    @SerializedName("codes_table")
    @Expose
    private String codesTable;
    @SerializedName("xsjx_time")
    @Expose
    private String xsjxTime;
    @Expose
    private String pos;
    @Expose
    private String renqi;
    @Expose
    private String time;
    @Expose
    private String order;
    @SerializedName("q_uid")
    @Expose
    private String qUid;
    @SerializedName("q_user")
    @Expose
    private String qUser;
    @SerializedName("q_user_code")
    @Expose
    private String qUserCode;
    @SerializedName("q_content")
    @Expose
    private String qContent;
    @SerializedName("q_counttime")
    @Expose
    private String qCounttime;
    @SerializedName("caipiao_time")
    @Expose
    private String caipiaoTime;
    @SerializedName("caipiao_weak")
    @Expose
    private String caipiaoWeak;
    @SerializedName("caipiao_type")
    @Expose
    private String caipiaoType;
    @SerializedName("caipiao_haoma")
    @Expose
    private String caipiaoHaoma;
    @SerializedName("q_end_time")
    @Expose
    private String qEndTime;
    @SerializedName("q_showtime")
    @Expose
    private String qShowtime;
    @Expose
    private String area;
    @SerializedName("need_hexiao")
    @Expose
    private String needHexiao;

    /**
     * 
     * @return
     *     The sdId
     */
    public String getSdId() {
        return sdId;
    }

    /**
     * 
     * @param sdId
     *     The sd_id
     */
    public void setSdId(String sdId) {
        this.sdId = sdId;
    }

    /**
     * 
     * @return
     *     The sdUserid
     */
    public String getSdUserid() {
        return sdUserid;
    }

    /**
     * 
     * @param sdUserid
     *     The sd_userid
     */
    public void setSdUserid(String sdUserid) {
        this.sdUserid = sdUserid;
    }

    /**
     * 
     * @return
     *     The sdShopid
     */
    public String getSdShopid() {
        return sdShopid;
    }

    /**
     * 
     * @param sdShopid
     *     The sd_shopid
     */
    public void setSdShopid(String sdShopid) {
        this.sdShopid = sdShopid;
    }

    /**
     * 
     * @return
     *     The sdQishu
     */
    public Object getSdQishu() {
        return sdQishu;
    }

    /**
     * 
     * @param sdQishu
     *     The sd_qishu
     */
    public void setSdQishu(Object sdQishu) {
        this.sdQishu = sdQishu;
    }

    /**
     * 
     * @return
     *     The sdIp
     */
    public String getSdIp() {
        return sdIp;
    }

    /**
     * 
     * @param sdIp
     *     The sd_ip
     */
    public void setSdIp(String sdIp) {
        this.sdIp = sdIp;
    }

    /**
     * 
     * @return
     *     The sdTitle
     */
    public String getSdTitle() {
        return sdTitle;
    }

    /**
     * 
     * @param sdTitle
     *     The sd_title
     */
    public void setSdTitle(String sdTitle) {
        this.sdTitle = sdTitle;
    }

    /**
     * 
     * @return
     *     The sdThumbs
     */
    public String getSdThumbs() {
        return sdThumbs;
    }

    /**
     * 
     * @param sdThumbs
     *     The sd_thumbs
     */
    public void setSdThumbs(String sdThumbs) {
        this.sdThumbs = sdThumbs;
    }

    /**
     * 
     * @return
     *     The sdContent
     */
    public String getSdContent() {
        return sdContent;
    }

    /**
     * 
     * @param sdContent
     *     The sd_content
     */
    public void setSdContent(String sdContent) {
        this.sdContent = sdContent;
    }

    /**
     * 
     * @return
     *     The sdPhotolist
     */
    public String getSdPhotolist() {
        return sdPhotolist;
    }

    /**
     * 
     * @param sdPhotolist
     *     The sd_photolist
     */
    public void setSdPhotolist(String sdPhotolist) {
        this.sdPhotolist = sdPhotolist;
    }

    /**
     * 
     * @return
     *     The sdZhan
     */
    public String getSdZhan() {
        return sdZhan;
    }

    /**
     * 
     * @param sdZhan
     *     The sd_zhan
     */
    public void setSdZhan(String sdZhan) {
        this.sdZhan = sdZhan;
    }

    /**
     * 
     * @return
     *     The sdPing
     */
    public String getSdPing() {
        return sdPing;
    }

    /**
     * 
     * @param sdPing
     *     The sd_ping
     */
    public void setSdPing(String sdPing) {
        this.sdPing = sdPing;
    }

    /**
     * 
     * @return
     *     The sdTime
     */
    public String getSdTime() {
        return sdTime;
    }

    /**
     * 
     * @param sdTime
     *     The sd_time
     */
    public void setSdTime(String sdTime) {
        this.sdTime = sdTime;
    }

    /**
     * 
     * @return
     *     The sdShopsid
     */
    public String getSdShopsid() {
        return sdShopsid;
    }

    /**
     * 
     * @param sdShopsid
     *     The sd_shopsid
     */
    public void setSdShopsid(String sdShopsid) {
        this.sdShopsid = sdShopsid;
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

    /**
     * 
     * @return
     *     The sid
     */
    public String getSid() {
        return sid;
    }

    /**
     * 
     * @param sid
     *     The sid
     */
    public void setSid(String sid) {
        this.sid = sid;
    }

    /**
     * 
     * @return
     *     The cateid
     */
    public String getCateid() {
        return cateid;
    }

    /**
     * 
     * @param cateid
     *     The cateid
     */
    public void setCateid(String cateid) {
        this.cateid = cateid;
    }

    /**
     * 
     * @return
     *     The brandid
     */
    public String getBrandid() {
        return brandid;
    }

    /**
     * 
     * @param brandid
     *     The brandid
     */
    public void setBrandid(String brandid) {
        this.brandid = brandid;
    }

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
     *     The titleStyle
     */
    public String getTitleStyle() {
        return titleStyle;
    }

    /**
     * 
     * @param titleStyle
     *     The title_style
     */
    public void setTitleStyle(String titleStyle) {
        this.titleStyle = titleStyle;
    }

    /**
     * 
     * @return
     *     The title2
     */
    public String getTitle2() {
        return title2;
    }

    /**
     * 
     * @param title2
     *     The title2
     */
    public void setTitle2(String title2) {
        this.title2 = title2;
    }

    /**
     * 
     * @return
     *     The keywords
     */
    public String getKeywords() {
        return keywords;
    }

    /**
     * 
     * @param keywords
     *     The keywords
     */
    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    /**
     * 
     * @return
     *     The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * 
     * @param description
     *     The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 
     * @return
     *     The money
     */
    public String getMoney() {
        return money;
    }

    /**
     * 
     * @param money
     *     The money
     */
    public void setMoney(String money) {
        this.money = money;
    }

    /**
     * 
     * @return
     *     The yunjiage
     */
    public String getYunjiage() {
        return yunjiage;
    }

    /**
     * 
     * @param yunjiage
     *     The yunjiage
     */
    public void setYunjiage(String yunjiage) {
        this.yunjiage = yunjiage;
    }

    /**
     * 
     * @return
     *     The zongrenshu
     */
    public String getZongrenshu() {
        return zongrenshu;
    }

    /**
     * 
     * @param zongrenshu
     *     The zongrenshu
     */
    public void setZongrenshu(String zongrenshu) {
        this.zongrenshu = zongrenshu;
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
     *     The shenyurenshu
     */
    public String getShenyurenshu() {
        return shenyurenshu;
    }

    /**
     * 
     * @param shenyurenshu
     *     The shenyurenshu
     */
    public void setShenyurenshu(String shenyurenshu) {
        this.shenyurenshu = shenyurenshu;
    }

    /**
     * 
     * @return
     *     The defRenshu
     */
    public String getDefRenshu() {
        return defRenshu;
    }

    /**
     * 
     * @param defRenshu
     *     The def_renshu
     */
    public void setDefRenshu(String defRenshu) {
        this.defRenshu = defRenshu;
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

    /**
     * 
     * @return
     *     The maxqishu
     */
    public String getMaxqishu() {
        return maxqishu;
    }

    /**
     * 
     * @param maxqishu
     *     The maxqishu
     */
    public void setMaxqishu(String maxqishu) {
        this.maxqishu = maxqishu;
    }

    /**
     * 
     * @return
     *     The thumb
     */
    public String getThumb() {
        return thumb;
    }

    /**
     * 
     * @param thumb
     *     The thumb
     */
    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    /**
     * 
     * @return
     *     The picarr
     */
    public String getPicarr() {
        return picarr;
    }

    /**
     * 
     * @param picarr
     *     The picarr
     */
    public void setPicarr(String picarr) {
        this.picarr = picarr;
    }

    /**
     * 
     * @return
     *     The content
     */
    public String getContent() {
        return content;
    }

    /**
     * 
     * @param content
     *     The content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 
     * @return
     *     The codesTable
     */
    public String getCodesTable() {
        return codesTable;
    }

    /**
     * 
     * @param codesTable
     *     The codes_table
     */
    public void setCodesTable(String codesTable) {
        this.codesTable = codesTable;
    }

    /**
     * 
     * @return
     *     The xsjxTime
     */
    public String getXsjxTime() {
        return xsjxTime;
    }

    /**
     * 
     * @param xsjxTime
     *     The xsjx_time
     */
    public void setXsjxTime(String xsjxTime) {
        this.xsjxTime = xsjxTime;
    }

    /**
     * 
     * @return
     *     The pos
     */
    public String getPos() {
        return pos;
    }

    /**
     * 
     * @param pos
     *     The pos
     */
    public void setPos(String pos) {
        this.pos = pos;
    }

    /**
     * 
     * @return
     *     The renqi
     */
    public String getRenqi() {
        return renqi;
    }

    /**
     * 
     * @param renqi
     *     The renqi
     */
    public void setRenqi(String renqi) {
        this.renqi = renqi;
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
     *     The order
     */
    public String getOrder() {
        return order;
    }

    /**
     * 
     * @param order
     *     The order
     */
    public void setOrder(String order) {
        this.order = order;
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

    /**
     * 
     * @return
     *     The qUser
     */
    public String getQUser() {
        return qUser;
    }

    /**
     * 
     * @param qUser
     *     The q_user
     */
    public void setQUser(String qUser) {
        this.qUser = qUser;
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
     *     The qContent
     */
    public String getQContent() {
        return qContent;
    }

    /**
     * 
     * @param qContent
     *     The q_content
     */
    public void setQContent(String qContent) {
        this.qContent = qContent;
    }

    /**
     * 
     * @return
     *     The qCounttime
     */
    public String getQCounttime() {
        return qCounttime;
    }

    /**
     * 
     * @param qCounttime
     *     The q_counttime
     */
    public void setQCounttime(String qCounttime) {
        this.qCounttime = qCounttime;
    }

    /**
     * 
     * @return
     *     The caipiaoTime
     */
    public String getCaipiaoTime() {
        return caipiaoTime;
    }

    /**
     * 
     * @param caipiaoTime
     *     The caipiao_time
     */
    public void setCaipiaoTime(String caipiaoTime) {
        this.caipiaoTime = caipiaoTime;
    }

    /**
     * 
     * @return
     *     The caipiaoWeak
     */
    public String getCaipiaoWeak() {
        return caipiaoWeak;
    }

    /**
     * 
     * @param caipiaoWeak
     *     The caipiao_weak
     */
    public void setCaipiaoWeak(String caipiaoWeak) {
        this.caipiaoWeak = caipiaoWeak;
    }

    /**
     * 
     * @return
     *     The caipiaoType
     */
    public String getCaipiaoType() {
        return caipiaoType;
    }

    /**
     * 
     * @param caipiaoType
     *     The caipiao_type
     */
    public void setCaipiaoType(String caipiaoType) {
        this.caipiaoType = caipiaoType;
    }

    /**
     * 
     * @return
     *     The caipiaoHaoma
     */
    public String getCaipiaoHaoma() {
        return caipiaoHaoma;
    }

    /**
     * 
     * @param caipiaoHaoma
     *     The caipiao_haoma
     */
    public void setCaipiaoHaoma(String caipiaoHaoma) {
        this.caipiaoHaoma = caipiaoHaoma;
    }

    /**
     * 
     * @return
     *     The qEndTime
     */
    public String getQEndTime() {
        return qEndTime;
    }

    /**
     * 
     * @param qEndTime
     *     The q_end_time
     */
    public void setQEndTime(String qEndTime) {
        this.qEndTime = qEndTime;
    }

    /**
     * 
     * @return
     *     The qShowtime
     */
    public String getQShowtime() {
        return qShowtime;
    }

    /**
     * 
     * @param qShowtime
     *     The q_showtime
     */
    public void setQShowtime(String qShowtime) {
        this.qShowtime = qShowtime;
    }

    /**
     * 
     * @return
     *     The area
     */
    public String getArea() {
        return area;
    }

    /**
     * 
     * @param area
     *     The area
     */
    public void setArea(String area) {
        this.area = area;
    }

    /**
     * 
     * @return
     *     The needHexiao
     */
    public String getNeedHexiao() {
        return needHexiao;
    }

    /**
     * 
     * @param needHexiao
     *     The need_hexiao
     */
    public void setNeedHexiao(String needHexiao) {
        this.needHexiao = needHexiao;
    }

}
