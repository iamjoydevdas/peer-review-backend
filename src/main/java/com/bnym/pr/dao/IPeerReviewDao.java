package com.bnym.pr.dao;

import java.util.List;

import com.bnym.pr.dto.LoginDto;
import com.bnym.pr.dto.Statics;
import com.bnym.pr.dto.UserDto;
import com.bnym.pr.handler.PeerReviewDatabaseException;
import com.bnym.pr.handler.PeerReviewException;

public interface IPeerReviewDao {
	public String save();
	public boolean login(LoginDto login) throws PeerReviewDatabaseException, PeerReviewException;
	public UserDto details(Integer loggedInUserId);
	public Integer create(UserDto userDto) throws PeerReviewDatabaseException, PeerReviewException;
	public Integer update(UserDto user, Integer userId) throws PeerReviewDatabaseException, PeerReviewException;
	public Integer delete(Integer userId) throws PeerReviewDatabaseException, PeerReviewException;
	public List<Statics> statics();
	public List<UserDto> viewAll(Integer loggedInUserId) throws PeerReviewDatabaseException, PeerReviewException;
}
