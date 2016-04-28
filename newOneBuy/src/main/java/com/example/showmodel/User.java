package com.example.showmodel;

import com.google.gson.annotations.Expose;

public class User {

	@Expose
	private String username;

	String img;

	/**
	 * 
	 * @return The username
	 */
	public String getUsername() {
		return username;
	}

	public String getImg() {
		return img;
	}

	/**
	 * 
	 * @param username
	 *            The username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

}
