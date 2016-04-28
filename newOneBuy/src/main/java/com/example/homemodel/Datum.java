package com.example.homemodel;

import com.google.gson.annotations.Expose;

public class Datum {

    @Expose
    private String cateid;
    @Expose
    private String catename;

    public Datum(String cateid, String catename) {
		super();
		this.cateid = cateid;
		this.catename = catename;
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
     *     The catename
     */
    public String getCatename() {
        return catename;
    }

    /**
     * 
     * @param catename
     *     The catename
     */
    public void setCatename(String catename) {
        this.catename = catename;
    }

}
