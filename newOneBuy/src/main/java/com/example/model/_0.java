package com.example.model;

import com.google.gson.annotations.Expose;

public class _0 {

    @Expose
    private String cateid;
    @Expose
    private String name;
    
    

    public _0(String cateid, String name) {
		super();
		this.cateid = cateid;
		this.name = name;
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
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

}
