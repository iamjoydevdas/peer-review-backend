package com.bnym.pr.dto;

import java.io.Serializable;

public class LoginDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2218793536904251336L;
	private Integer userName;
	private String password;
	
	public LoginDto() {
		super();
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getUserName() {
		return userName;
	}

	public void setUserName(Integer userName) {
		this.userName = userName;
	}

}
