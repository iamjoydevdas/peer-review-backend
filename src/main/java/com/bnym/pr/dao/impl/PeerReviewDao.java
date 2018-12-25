package com.bnym.pr.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
		Connection conn = null;
		int count = 0;
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(query.IS_USER_VALID);
			ps.setInt(1, login.getUserName());
			ps.setString(2, login.getPassword());
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				count = rs.getInt("ROWCOUNT");
				System.out.println("Database thekei");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return count > 0 ? true : false;
	}

}
