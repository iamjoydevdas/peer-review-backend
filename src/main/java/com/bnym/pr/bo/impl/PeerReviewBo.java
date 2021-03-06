package com.bnym.pr.bo.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.bnym.pr.bo.IPeerReviewBo;
import com.bnym.pr.dao.IPeerReviewDao;
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
import com.bnym.pr.util.PeerReviewUtils;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;


public class PeerReviewBo implements IPeerReviewBo {

	@Autowired
	private IPeerReviewDao dao;

	private CacheManager cm;

	public void setDao(IPeerReviewDao dao) { this.dao = dao; }

	public String save() {
		return dao.save();
	}

	@Override
	public boolean login(LoginDto login) {
		return dao.login(login);
	}

	public void configCache() {
		cm = CacheManager.newInstance();
		cm.addCache("tokenStore");
	}

	public String generateAndStoreToken(LoginDto login) {
		String token = PeerReviewUtils.getRandomToken(); System.out.println(token);
		Cache cache = cm.getCache("tokenStore"); 
		cache.put(new Element(token, login)); 
		System.out.println(cache.isElementInMemory(token)); return token;
	}

	@Override
	public Integer getSessionUserId(String token) throws PeerReviewException {
		Cache cache = cm.getCache("tokenStore");
		Integer userId = null;
		Element e = cache.get(token);
		if(e != null) {
			userId = ((LoginDto) cache.get(token).getObjectValue()).getUserName();
		}else {
			new PeerReviewBusinessException(401, "Unauthorized activity spotted.");
		}
		return userId;
	}

	@Override
	public UserDto details(Integer loggedInUserId) {
		return dao.details(loggedInUserId);
	}

	@Override
	public Integer create(UserDto user) throws PeerReviewDatabaseException, PeerReviewException {
		return dao.create(user);
	}

	@Override
	public Integer update(UserDto user, Integer userId) throws PeerReviewDatabaseException, PeerReviewException {
		return dao.update(user, userId);
	}

	@Override
	public Integer delete(Integer userId) throws PeerReviewDatabaseException, PeerReviewException {
		return dao.delete(userId);
	}

	@Override
	public List<Statics> statics() throws PeerReviewBusinessException, PeerReviewException {
		List<Statics> statics = dao.statics();
		if(statics == null) {
			throw new PeerReviewBusinessException(500, "Oops!! No Static Data Found.");
		}
		return dao.statics();
	}

	@Override
	public List<UserDto> viewDetails(Integer userId) throws PeerReviewBusinessException, PeerReviewException {
		List<UserDto> viewDetails = dao.viewDetails(userId);
		if(viewDetails == null) {
			throw new PeerReviewBusinessException(500, "Oops!! No  Data Found.");
		}
		return viewDetails;
	}

	@Override
	public Integer changePassword(LoginDto user, Integer userId) throws PeerReviewDatabaseException, PeerReviewException {
		return dao.changePassword(user, userId);
	}

	@Override
	public Integer resetPassword(LoginDto user, Integer userId) throws PeerReviewDatabaseException, PeerReviewException {
	return dao.resetPassword(user, userId);
	}

	@Override
	public Integer ratePeer(Integer fromPeerId, RatingScores rated) throws PeerReviewDatabaseException, PeerReviewException {
		return dao.ratePeer(fromPeerId, rated);
	}

	@Override
	public Integer addSkills(Skills skills) throws SQLException, PeerReviewDatabaseException, PeerReviewException {
		return dao.addSkills(skills);
	}

	@Override
	public List<Managerview> dataBySkill(Integer skills) throws PeerReviewBusinessException, PeerReviewException {
		List<Managerview> view = dao.dataBySkill(skills);
		if(view == null) {
			throw new PeerReviewBusinessException(500, "Oops!! No Data Found.");
		}
		return dao.dataBySkill(skills);
	}

	@Override
	public List<Managerview> dataByDesignation(Integer designation) throws PeerReviewBusinessException, PeerReviewException {
		List<Managerview> view = dao.dataByDesignation(designation);
		if(view == null) {
			throw new PeerReviewBusinessException(500, "Oops!! No Data Found.");
		}
		return dao.dataByDesignation(designation);
	}

	@Override
	public List<Comments> viewComments() throws PeerReviewBusinessException, PeerReviewException {
		List<Comments> view = dao.viewComments();
		if(view == null) {
			throw new PeerReviewBusinessException(500, "Oops!! No Data Found.");
		}
		return dao.viewComments();
	}
}