package com.bnym.pr.dto;

import java.util.List;

public class RatingScores {
	private Integer ratedBy;
	private List<Ratings> rated;
	public Integer getRatedBy() {
		return ratedBy;
	}
	public void setRatedBy(Integer ratedBy) {
		this.ratedBy = ratedBy;
	}
	public List<Ratings> getRated() {
		return rated;
	}
	public void setRated(List<Ratings> rated) {
		this.rated = rated;
	}

	
	
	
}
