package com.bnym.pr.dao.impl;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.bnym.pr.dao.IPeerReviewDao;
import com.bnym.pr.dto.LoginDto;
import com.bnym.pr.dto.Statics;
import com.bnym.pr.dto.UserDto;
import com.bnym.pr.handler.PeerReviewDatabaseException;
import com.bnym.pr.handler.PeerReviewException;

public class PeerReviewDao implements IPeerReviewDao{

	static class Query{
		String IS_USER_VALID = "SELECT COUNT(1) AS ROWCOUNT FROM PEERS WHERE PEER_CTS_ID=? AND PEER_PSWD=?";
		String GET_USER_DETAILS = "SELECT PEER_FNAME, PEER_LNAME, PEER_FULL_NAME, PEER_CTS_ID, PEER_DESIG_ID, PEER_ROLE_ID FROM PEERS WHERE PEER_CTS_ID = ?";
		String CREATE_NEW_PEER = "INSERT INTO PEERS (PEER_FNAME, PEER_LNAME, PEER_FULL_NAME, PEER_DESIG_ID, PEER_ROLE_ID, PEER_CREATED_BY, PEER_CREATION_TS, PEER_LAST_UPDATED_BY, PEER_LAST_UPDATED_TS, PEER_CTS_ID, PEER_PSWD) VALUES "
				+ " (?, ?, ?, ?, ?, ?, sysdate(), ?, sysdate(), ?, 'Password1$')";
		String UPDATE_A_PEER = "UPDATE PEERS SET PEER_FNAME=?, PEER_LNAME=?, PEER_DESIG_ID=?, PEER_ROLE_ID=?, PEER_LAST_UPDATED_TS=sysdate(), PEER_LAST_UPDATED_BY=? " + 
				" WHERE PEER_CTS_ID = ?";
		String DELETE_A_PEER = "DELETE FROM PEERS WHERE PEER_CTS_ID = ?";
		String FETCH_ALL_STATICS = "SELECT  'ROLE' AS TYPE, ROLE_ID AS 'ID', ROLE_NAME AS 'DESC' FROM ROLE " + 
				"UNION SELECT 'DESIGNATION' AS 'TYPE', DESIGNATION_ID AS 'ID', DESIGNATION_NAME AS 'DESC' FROM designation";
		String GET_ALL_USERS = "SELECT PEER_FNAME, PEER_LNAME, PEER_FULL_NAME, PEER_CTS_ID, PEER_DESIG_ID, PEER_ROLE_ID FROM PEERS WHERE PEER_CTS_ID <> ?";
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
	public boolean login(LoginDto login) throws PeerReviewDatabaseException, PeerReviewException {
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
		} catch (Exception e) {
			throw new PeerReviewDatabaseException(500, e.getMessage());
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return count > 0 ? true : false;
	}

	@Override
	public UserDto details(Integer loggedInUserId) {
		Connection conn = null;
		UserDto userDto = null;
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(query.GET_USER_DETAILS);
			ps.setInt(1, loggedInUserId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				userDto = new UserDto();
				userDto.setUserId(rs.getInt("PEER_CTS_ID"));
				userDto.setFirstName(rs.getString("PEER_FNAME"));
				userDto.setLastName(rs.getString("PEER_LNAME"));
				userDto.setFullName(rs.getString("PEER_FULL_NAME"));
				userDto.setDesignation(rs.getInt("PEER_DESIG_ID"));
				userDto.setRole(rs.getInt("PEER_ROLE_ID"));
				System.out.println(userDto.toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return userDto;
	}

	@Override
	public Integer create(UserDto userDto) throws PeerReviewDatabaseException, PeerReviewException {
		Connection conn = null;
		int count = 0;
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(query.CREATE_NEW_PEER);
			ps.setString(1, userDto.getFirstName());
			ps.setString(2, userDto.getLastName()); 
			ps.setString(3, userDto.getFirstName() + " " + userDto.getLastName()); 
			ps.setInt(4, (userDto.getDesignation().getDesignationValue())); 
			ps.setInt(5, (userDto.getRole()).getRoleValue()); 
			ps.setString(6, userDto.getCreatedBy());
			ps.setString(7, userDto.getUpdatedBy());
			ps.setInt(8, userDto.getUserId());
			count = ps.executeUpdate();
			if(count <= 0) {
				throw new PeerReviewDatabaseException(500, "Something went wrong. Peer didn't created.") ;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return count;
	}

	@Override
	public Integer update(UserDto user, Integer userId) throws PeerReviewDatabaseException, PeerReviewException {
		Connection conn = null;
		int count = 0;
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(query.UPDATE_A_PEER);
			ps.setString(1, user.getFirstName());
			ps.setString(2, user.getLastName());
			ps.setInt(3, (user.getDesignation().getDesignationValue())); 
			ps.setInt(4, (user.getRole()).getRoleValue()); 
			ps.setString(5, user.getUpdatedBy());
			ps.setInt(6, userId);
			count = ps.executeUpdate();
			if(count <= 0) {
				throw new PeerReviewDatabaseException(500, "Something went wrong. Peer didn't updated.") ;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return count;
	}

	@Override
	public Integer delete(Integer userId) throws PeerReviewDatabaseException, PeerReviewException {
		Connection conn = null;
		int count = 0;
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(query.DELETE_A_PEER);
			ps.setInt(1, userId);
			count = ps.executeUpdate();
			if(count <= 0) {
				throw new PeerReviewDatabaseException(500, "Something went wrong. Peer didn't deleted.") ;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return count;
	}

	@Override
	public List<Statics> statics() {
		Connection conn = null;
		List<Statics> statics = new ArrayList<>();
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(query.FETCH_ALL_STATICS);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Statics s = new Statics();
				s.setStaticType(rs.getString("TYPE"));
				s.setStaticId(rs.getInt("ID"));
				s.setStaticDesc(rs.getString("DESC"));
				statics.add(s);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return statics;
	}

	@Override
	public List<UserDto> viewAll(Integer loggedInUserId) throws PeerReviewDatabaseException, PeerReviewException {
		Connection conn = null;
		UserDto userDto = null;
		List<UserDto> users = new ArrayList<>();
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(query.GET_ALL_USERS);
			ps.setInt(1, loggedInUserId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				userDto = new UserDto();
				userDto.setUserId(rs.getInt("PEER_CTS_ID"));
				userDto.setFirstName(rs.getString("PEER_FNAME"));
				userDto.setLastName(rs.getString("PEER_LNAME"));
				userDto.setFullName(rs.getString("PEER_FULL_NAME"));
				userDto.setDesignation(rs.getInt("PEER_DESIG_ID"));
				userDto.setRole(rs.getInt("PEER_ROLE_ID"));
				users.add(userDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return users;
	}

}
