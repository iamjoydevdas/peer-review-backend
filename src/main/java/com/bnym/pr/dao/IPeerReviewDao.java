package com.bnym.pr.dao;

import com.bnym.pr.dto.LoginDto;
import com.bnym.pr.dto.UserDto;

public interface IPeerReviewDao {
	public String save();
	public boolean login(LoginDto login);
	public UserDto details(Integer loggedInUserId);
	public Integer create(UserDto userDto);
	public Integer update(UserDto user, Integer userId);
	public Integer delete(Integer userId);
}
