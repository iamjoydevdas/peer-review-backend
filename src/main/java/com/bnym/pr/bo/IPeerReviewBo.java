package com.bnym.pr.bo;

import java.sql.SQLException;
import java.util.List;

import com.bnym.pr.dto.Comments;
import com.bnym.pr.dto.LoginDto;
import com.bnym.pr.dto.Managerview;
import com.bnym.pr.dto.RatingScores;
import com.bnym.pr.dto.Skills;
import com.bnym.pr.dto.Statics;
import com.bnym.pr.dto.UserDto;
import com.bnym.pr.handler.PeerReviewBusinessException;
import com.bnym.pr.handler.PeerReviewDatabaseException;
import com.bnym.pr.handler.PeerReviewException;

public interface IPeerReviewBo{
	public boolean login(LoginDto login);
	public String generateAndStoreToken(LoginDto login);
	public Integer getSessionUserId(String token) throws PeerReviewException;
	public UserDto details(Integer loggedInUserId);
	public Integer create(UserDto user) throws PeerReviewException, PeerReviewDatabaseException;
	public Integer update(UserDto user, Integer userId) throws PeerReviewException, PeerReviewDatabaseException;
	public Integer delete(Integer userId) throws PeerReviewException, PeerReviewDatabaseException;
	public List<Statics> statics() throws PeerReviewBusinessException, PeerReviewException;
	public List<UserDto> viewDetails(Integer userId) throws PeerReviewBusinessException, PeerReviewException;
	public Integer changePassword(LoginDto userDto, Integer peerId)throws PeerReviewException, PeerReviewDatabaseException;
	public Integer resetPassword(LoginDto user, Integer userId) throws PeerReviewException, PeerReviewDatabaseException;
	public Integer ratePeer(Integer fromPeerId, RatingScores rated) throws PeerReviewDatabaseException, PeerReviewException;
	public Integer addSkills(Skills skills) throws SQLException, PeerReviewDatabaseException, PeerReviewException;
	public List<Managerview> dataBySkill(Integer skills) throws PeerReviewBusinessException, PeerReviewException;
	public List<Managerview> dataByDesignation(Integer designation) throws PeerReviewBusinessException, PeerReviewException;
	public List<Comments> viewComments() throws PeerReviewBusinessException, PeerReviewException;
}