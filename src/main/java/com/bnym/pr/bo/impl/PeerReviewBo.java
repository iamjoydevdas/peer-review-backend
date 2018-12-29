package com.bnym.pr.bo.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.bnym.pr.bo.IPeerReviewBo;
import com.bnym.pr.dao.IPeerReviewDao;
import com.bnym.pr.dto.LoginDto;
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
		cm = CacheManager.newInstance(); cm.addCache("tokenStore");
	}

	public String generateAndStoreToken(LoginDto login) {
		String token = PeerReviewUtils.getRandomToken(); System.out.println(token);
		Cache cache = cm.getCache("tokenStore"); 
		cache.put(new Element(token, login)); 
		System.out.println(cache.isElementInMemory(token)); return token;
	}

	@Override
	public Integer getSessionUserId(String token) {
		Cache cache = cm.getCache("tokenStore");
		LoginDto session  = (LoginDto) cache.get(token).getObjectValue();
		return session.getUserName();
	}
}