package com.example.homemodel;

import com.google.gson.annotations.Expose;

public class Lists1 {

	@Expose
	private String img;
	@Expose
	private String title;
	@Expose
	private String link;

	/**
	 * 
	 * @return The img
	 */
	public String getImg() {
		return img;
	}

	public String getLink() {
		return link;
	}

	/**
	 * 
	 * @param img
	 *            The img
	 */
	public void setImg(String img) {
		this.img = img;
	}

	/**
	 * 
	 * @return The title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 
	 * @param title
	 *            The title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

}
