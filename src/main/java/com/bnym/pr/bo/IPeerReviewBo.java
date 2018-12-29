package com.bnym.pr.bo;

import com.bnym.pr.dto.LoginDto;
import com.bnym.pr.dto.UserDto;
import com.bnym.pr.handler.PeerReviewException;

public interface IPeerReviewBo{
	public boolean login(LoginDto login);
	public String generateAndStoreToken(LoginDto login);
	public Integer getSessionUserId(String token) throws PeerReviewException;
	public UserDto details(Integer loggedInUserId);
	public Integer create(UserDto user);
}