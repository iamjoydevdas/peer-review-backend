package com.bnym.pr.dto;

import java.io.Serializable;

public class LoginDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2218793536904251336L;
	private String userName;
	private String password;
	
	public LoginDto() {
		super();
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
