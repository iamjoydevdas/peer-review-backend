package com.bnym.pr.dao;

import java.sql.SQLException;
import java.util.List;

import com.bnym.pr.dto.Comments;
import com.bnym.pr.dto.LoginDto;
import com.bnym.pr.dto.Managerview;
import com.bnym.pr.dto.RatingScores;
import com.bnym.pr.dto.Skills;
import com.bnym.pr.dto.Statics;
import com.bnym.pr.dto.UserDto;
import com.bnym.pr.handler.PeerReviewDatabaseException;
import com.bnym.pr.handler.PeerReviewException;

public interface IPeerReviewDao {
	public String save();
	public boolean login(LoginDto login);
	public UserDto details(Integer loggedInUserId);
	public Integer create(UserDto userDto) throws PeerReviewDatabaseException, PeerReviewException;
	public Integer update(UserDto user, Integer userId) throws PeerReviewDatabaseException, PeerReviewException;
	public Integer delete(Integer userId) throws PeerReviewDatabaseException, PeerReviewException;
	public List<Statics> statics();
	public List<UserDto> viewDetails(Integer userId);
	public Integer changePassword(LoginDto user, Integer userId) throws PeerReviewDatabaseException, PeerReviewException;
	public Integer resetPassword(LoginDto user, Integer userId) throws PeerReviewException, PeerReviewDatabaseException;
	public Integer ratePeer(Integer fromPeerId, RatingScores rated) throws PeerReviewDatabaseException, PeerReviewException;
	public Integer addSkills(Skills skills) throws SQLException, PeerReviewDatabaseException, PeerReviewException;
	public List<Managerview> dataBySkill(Integer skills);
	public List<Managerview> dataByDesignation(Integer designation);
	public List<Comments> viewComments();
}
