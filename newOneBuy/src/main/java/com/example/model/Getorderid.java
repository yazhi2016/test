package com.example.model;

import com.google.gson.annotations.Expose;

public class Getorderid {

	@Expose
	private Integer tag;
	@Expose
	private String message;
	@Expose
	private Data3 data;

	/**
	 * 
	 * @return The tag
	 */
	public Integer getTag() {
		return tag;
	}

	/**
	 * 
	 * @param tag
	 *            The tag
	 */
	public void setTag(Integer tag) {
		this.tag = tag;
	}

	/**
	 * 
	 * @return The message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * 
	 * @param message
	 *            The message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 
	 * @return The data
	 */
	public Data3 getData() {
		return data;
	}

	/**
	 * 
	 * @param data
	 *            The data
	 */
	public void setData(Data3 data) {
		this.data = data;
	}

}
