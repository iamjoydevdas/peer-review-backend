package com.bnym.pr.dto;

public class RatedSkills {
	private Integer skill;
	private Integer ratingProvided;
	private String comment;
	
	
	
	public Integer getSkill() {
		return skill;
	}
	public void setSkill(Integer skill) {
		this.skill = skill;
	}
	public Integer getRatingProvided() {
		return ratingProvided;
	}
	public void setRatingProvided(Integer ratingProvided) {
		this.ratingProvided = ratingProvided;
	}
	public String  getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	@Override
	public String toString() {
		return "RatedSkills [skill=" + skill + ", ratingProvided=" + ratingProvided + ", comment=" + comment + "]";
	}
	
}
