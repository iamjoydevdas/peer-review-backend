package com.bnym.pr.dto;

import java.io.Serializable;

public class Statics implements Serializable{
	private static final long serialVersionUID = 1686205853614134392L;
	private String staticType;
	private Integer staticId;
	private String staticDesc;
	
	public String getStaticType() {
		return staticType;
	}
	public void setStaticType(String staticType) {
		this.staticType = staticType;
	}
	public Integer getStaticId() {
		return staticId;
	}
	public void setStaticId(Integer staticId) {
		this.staticId = staticId;
	}
	public String getStaticDesc() {
		return staticDesc;
	}
	public void setStaticDesc(String staticDesc) {
		this.staticDesc = staticDesc;
	}
}
