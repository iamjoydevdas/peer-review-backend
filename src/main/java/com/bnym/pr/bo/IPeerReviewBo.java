package com.bnym.pr.bo;

import com.bnym.pr.dto.LoginDto;

public interface IPeerReviewBo{
	public boolean login(LoginDto login);
	public String generateAndStoreToken(LoginDto login);
 
}