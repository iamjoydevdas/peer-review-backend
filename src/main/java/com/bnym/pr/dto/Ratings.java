package com.bnym.pr.dto;

import java.util.List;

public class Ratings {
		private Integer peerId;
		private List<RatedSkills> skills;
		
		public List<RatedSkills> getSkills() {
			return skills;
		}

		public void setSkills(List<RatedSkills> skills) {
			this.skills = skills;
		}

		public Integer getPeerId() {
			return peerId;
		}

		public void setPeerId(Integer peerId) {
			this.peerId = peerId;
		}
		
		
}


