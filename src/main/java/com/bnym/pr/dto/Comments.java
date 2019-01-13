package com.bnym.pr.dto;

public class Comments {
	String fullName;
	Integer peerId;
	String comments;
	String skillName;
	String designation;
	public String getSkillName() {
		return skillName;
	}
	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public Integer getPeerId() {
		return peerId;
	}
	public void setPeerId(Integer peerId) {
		this.peerId = peerId;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	

}
