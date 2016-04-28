package com.example.model;

import com.google.gson.annotations.Expose;

public class Data9 {

    @Expose
    private String lastBuild;
    @Expose
    private String downloadURL;
    @Expose
    private String versionCode;
    @Expose
    private String versionName;
    @Expose
    private String appUrl;
    @Expose
    private String build;
    @Expose
    private String releaseNote;

    /**
     * 
     * @return
     *     The lastBuild
     */
    public String getLastBuild() {
        return lastBuild;
    }

    /**
     * 
     * @param lastBuild
     *     The lastBuild
     */
    public void setLastBuild(String lastBuild) {
        this.lastBuild = lastBuild;
    }

    /**
     * 
     * @return
     *     The downloadURL
     */
    public String getDownloadURL() {
        return downloadURL;
    }

    /**
     * 
     * @param downloadURL
     *     The downloadURL
     */
    public void setDownloadURL(String downloadURL) {
        this.downloadURL = downloadURL;
    }

    /**
     * 
     * @return
     *     The versionCode
     */
    public String getVersionCode() {
        return versionCode;
    }

    /**
     * 
     * @param versionCode
     *     The versionCode
     */
    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    /**
     * 
     * @return
     *     The versionName
     */
    public String getVersionName() {
        return versionName;
    }

    /**
     * 
     * @param versionName
     *     The versionName
     */
    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    /**
     * 
     * @return
     *     The appUrl
     */
    public String getAppUrl() {
        return appUrl;
    }

    /**
     * 
     * @param appUrl
     *     The appUrl
     */
    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    /**
     * 
     * @return
     *     The build
     */
    public String getBuild() {
        return build;
    }

    /**
     * 
     * @param build
     *     The build
     */
    public void setBuild(String build) {
        this.build = build;
    }

    /**
     * 
     * @return
     *     The releaseNote
     */
    public String getReleaseNote() {
        return releaseNote;
    }

    /**
     * 
     * @param releaseNote
     *     The releaseNote
     */
    public void setReleaseNote(String releaseNote) {
        this.releaseNote = releaseNote;
    }

}
