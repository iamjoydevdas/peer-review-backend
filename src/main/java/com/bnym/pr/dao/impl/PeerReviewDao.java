package com.bnym.pr.dao.impl;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.bnym.pr.dao.IPeerReviewDao;
import com.bnym.pr.dto.LoginDto;

public class PeerReviewDao implements IPeerReviewDao{

	static class Query{
		String IS_USER_VALID = "SELECT COUNT(1) AS ROWCOUNT FROM PEERS WHERE PEER_CTS_ID=? AND PEER_PSWD=?";
	}

	PeerReviewDao.Query query = new PeerReviewDao.Query();

	@Autowired
	private DataSource dataSource;
	private JdbcTemplate jdbc;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void construct() {
		jdbc = new JdbcTemplate(dataSource);
	}

	@Override
	public String save() {

		return "From Dao";
	}

	@Override
	public boolean login(LoginDto login) {

		/*
		 * int count = jdbc.queryForObject(query.IS_USER_VALID, new Object[] {
		 * login.getUserName(), login.getPassword() }, (rs, rownum) ->
		 * rs.getInt("ROWCOUNT") );
		 * 
		 * return count > 0 ? true : false;
		 */
		return true;
	}

}
