package com.bnym.pr.dao;

import com.bnym.pr.dto.LoginDto;

public interface IPeerReviewDao {
	public String save();
	public boolean login(LoginDto login);
}
