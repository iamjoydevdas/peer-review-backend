package com.bnym.pr.dto;

import java.sql.Timestamp;

public class UserDto {
	private String firstName;
	private String lastName;
	private String fullName;
	private Integer userId;
	private Designation designation;
	private Role role;
	private String createdBy;
	private Timestamp createdTime;
	private String updatedBy;
	private Timestamp updatedTime;

	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Timestamp getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Timestamp createdTime) {
		this.createdTime = createdTime;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Timestamp getUpdatedTime() {
		return updatedTime;
	}
	public void setUpdatedTime(Timestamp updatedTime) {
		this.updatedTime = updatedTime;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Integer role) {
		this.role = Role.get(role);
	}
	public Designation getDesignation() {
		return designation;
	}
	public void setDesignation(Integer designation) {
		this.designation = Designation.get(designation);
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	

	@Override
	public String toString() {
		return "UserDto [firstName=" + firstName + ", lastName=" + lastName + ", fullName=" + fullName + ", userId="
				+ userId + ", designation=" + designation + ", role=" + role + "]";
	}
	
}
