package com.bnym.pr.dao.impl;

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
import com.bnym.pr.dto.Comments;
import com.bnym.pr.dto.LoginDto;
import com.bnym.pr.dto.Managerview;
import com.bnym.pr.dto.RatedSkills;
import com.bnym.pr.dto.RatingScores;
import com.bnym.pr.dto.Ratings;
import com.bnym.pr.dto.Skills;
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
		String UPDATE_A_PEER = "UPDATE PEERS SET PEER_FNAME=?, PEER_LNAME=?, PEER_FULL_NAME=?, PEER_DESIG_ID=?, PEER_ROLE_ID=?, PEER_LAST_UPDATED_TS=sysdate(), PEER_LAST_UPDATED_BY=? " + 
				" WHERE PEER_CTS_ID = ?";
		String DELETE_A_PEER = "DELETE FROM PEERS WHERE PEER_CTS_ID = ?";
		String FETCH_ALL_STATICS = "SELECT  'ROLE' AS TYPE, ROLE_ID AS 'ID', ROLE_NAME AS 'DESC' FROM ROLE " + 
				"UNION SELECT 'DESIGNATION' AS 'TYPE', DESIGNATION_ID AS 'ID', DESIGNATION_NAME AS 'DESC' FROM designation "+
				"UNION SELECT 'SKILLS' AS 'TYPE', SKILL_ID AS 'ID', SKILL_NAME AS 'DESC' FROM SKILLS";
		String User_Details="SELECT * FROM PEERS WHERE PEER_CTS_ID!=?";
		String User_Details_data="SELECT * FROM PEERS WHERE PEER_CTS_ID=?";
		String UPDATE_PASSWORD= "UPDATE PEERS SET PEER_PSWD=?" + 
				" WHERE PEER_CTS_ID = ?";
		String RESET_PASSWORD= "UPDATE PEERS SET PEER_PSWD=?" + 
				" WHERE PEER_CTS_ID = ?";
		String RATING_DETAILS="INSERT INTO RATINGS(RATED_BY, RATED_TO) VALUES (?, ?)";
		String RATINGSCORES="INSERT INTO RATING_SCORES(RATE_ID, SKILL_ID, RATING_SCORE, COMMENT) VALUES (?, ?, ?, ?)";
		String ALL_RATING="select * from ratings order by RATE_ID DESC";
		String ADD_SKILLS="INSERT INTO SKILLS(SKILL_NAME) VALUES (?)";
		String FETCH_ALL_SCORES="select p.PEER_CTS_ID, p.peer_full_name, s.skill_name, d.designation_name, avg(rs.RATING_SCORE) as ratingscore from rating_scores rs join ratings r on rs.RATE_ID=r.RATE_ID join skills s on rs.SKILL_ID=s.SKILL_ID join peers p on p.PEER_CTS_ID=r.RATED_TO join designation d on d.designation_id=p.peer_desig_id where rs.SKILL_ID = ?";
		String FETCH_ALL_SCORES_DESIGNATION="select p.PEER_CTS_ID, p.peer_full_name, s.skill_name, d.designation_name, avg(rs.RATING_SCORE) as ratingscore from rating_scores rs join ratings r on rs.RATE_ID=r.RATE_ID join skills s on rs.SKILL_ID=s.SKILL_ID join peers p on p.PEER_CTS_ID=r.RATED_TO join designation d on d.designation_id=p.peer_desig_id where p.PEER_DESIG_ID = ? group by s.SKILL_NAME";
		String FETCH_ALL_COMMENTS="select p.PEER_CTS_ID, p.peer_full_name, s.skill_name, d.designation_name, rs.comment from rating_scores rs join ratings r on rs.RATE_ID=r.RATE_ID join skills s on rs.SKILL_ID=s.SKILL_ID join peers p on p.PEER_CTS_ID=r.RATED_TO join designation d on d.designation_id=p.peer_desig_id  group by s.SKILL_NAME";
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
			e.printStackTrace();
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
			ps.setString(3, user.getFirstName() + " " + user.getLastName()); 
			ps.setInt(4, (user.getDesignation().getDesignationValue())); 
			ps.setInt(5, (user.getRole()).getRoleValue()); 
			ps.setString(6, user.getUpdatedBy());
			ps.setInt(7, userId);
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
	public Integer resetPassword(LoginDto user, Integer userId) throws PeerReviewDatabaseException, PeerReviewException {
		Connection conn = null;
		int count = 0;
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(query.RESET_PASSWORD);
			ps.setString(1, user.getPassword());
			ps.setInt(2, userId);
			count = ps.executeUpdate();
			if(count <= 0) {
				throw new PeerReviewDatabaseException(500, "Something went wrong. Peer Password didn't reseted.") ;
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
	public Integer changePassword(LoginDto user, Integer userId) throws PeerReviewDatabaseException, PeerReviewException {
		Connection conn = null;
		int count = 0;
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(query.UPDATE_PASSWORD);
			ps.setString(1,user.getPassword());
			ps.setInt(2, userId);
			count = ps.executeUpdate();
			if(count <= 0) {
				throw new PeerReviewDatabaseException(500, "Something went wrong. Peer password didn't updated.") ;
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
	public List<UserDto> viewDetails(Integer userId) {
		Connection conn = null;
		List<UserDto> viewDetails = new ArrayList<>();
		try {
			conn = dataSource.getConnection(); 
			PreparedStatement ps = conn.prepareStatement(query.User_Details);
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				UserDto s = new UserDto();
				s.setCreatedBy(rs.getString("PEER_CREATED_BY"));
				s.setCreatedTime(rs.getTimestamp("PEER_CREATION_TS"));
				int desig_id=rs.getInt("PEER_DESIG_ID");
				//			Designation designation=new Designation(desig_id);
				s.setDesignation(desig_id);
				s.setFirstName(rs.getString("PEER_FNAME"));
				s.setFullName(rs.getString("PEER_FULL_NAME"));
				s.setLastName(rs.getString("PEER_LNAME"));
				s.setRole(rs.getInt("PEER_ROLE_ID"));
				s.setUpdatedBy(rs.getString("PEER_LAST_UPDATED_BY"));
				//s.setUpdatedTime(rs.getTimestamp("PEER_LAST_UPDATED_TS"));
				s.setUserId(rs.getInt("PEER_CTS_ID"));
				viewDetails.add(s);
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
		return viewDetails;
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
	public Integer ratePeer(Integer fromPeerId, RatingScores ratingScores) throws PeerReviewDatabaseException, PeerReviewException {
		Connection conn = null;
		int count = 0, count1=0;
		Integer rateId=0;
		try {
			conn = dataSource.getConnection();

			for(Ratings ratings:ratingScores.getRated())
			{
				PreparedStatement ps = conn.prepareStatement(query.RATING_DETAILS);
				ps.setInt(1, fromPeerId );
				ps.setInt(2, ratings.getPeerId());
				count = ps.executeUpdate();
				for(RatedSkills ratedSkills:ratings.getSkills())
				{
					PreparedStatement ps2 = conn.prepareStatement(query.ALL_RATING);
					ResultSet rs = ps2.executeQuery();
					while(rs.next()) {
						rateId=rs.getInt("RATE_ID");
						break;

					}
					PreparedStatement ps1 = conn.prepareStatement(query.RATINGSCORES);
					ps1.setInt(1, rateId  );
					ps1.setInt(2,ratedSkills.getSkill());
					ps1.setInt(3, ratedSkills.getRatingProvided());
					ps1.setString(4, ratedSkills.getComment());
					count1=ps1.executeUpdate();
				}

			}
			if(count <= 0 && count1<=0) {
				throw new PeerReviewDatabaseException(500, "Something went wrong. Peer not rated.") ;
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
	public Integer addSkills(Skills skills) throws SQLException, PeerReviewDatabaseException, PeerReviewException {
		Connection conn = null;
		int count = 0;
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(query.ADD_SKILLS);
			ps.setString(1, skills.getSkills() );
			count = ps.executeUpdate();
			if(count <= 0) {
				throw new PeerReviewDatabaseException(500, "Something went wrong. Skills not added.") ;
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
	public List<Managerview> dataBySkill(Integer skills) {
		Connection conn = null;
		List<Managerview> view = new ArrayList<>();
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(query.FETCH_ALL_SCORES);
			ps.setInt(1, skills);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Managerview s = new Managerview();
				s.setPeerId(rs.getInt("PEER_CTS_ID"));
				s.setFullName(rs.getString("PEER_FULL_NAME"));
				s.setSkill(rs.getString("Skill_name"));
				s.setRatingScore(rs.getDouble("ratingscore"));
				s.setDesignation(rs.getString("designation_name"));
				view.add(s);
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
		return view;
	}

	@Override
	public List<Managerview> dataByDesignation(Integer designation) {
		Connection conn = null;
		List<Managerview> view = new ArrayList<>();
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(query.FETCH_ALL_SCORES_DESIGNATION);
			ps.setInt(1, designation);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Managerview s = new Managerview();
				s.setPeerId(rs.getInt("PEER_CTS_ID"));
				s.setFullName(rs.getString("PEER_FULL_NAME"));
				s.setSkill(rs.getString("Skill_name"));
				s.setRatingScore(rs.getDouble("ratingscore"));
				s.setDesignation(rs.getString("designation_name"));
				view.add(s);
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
		return view;
	}

	@Override
	public List<Comments> viewComments() {
		Connection conn = null;
		List<Comments> view = new ArrayList<>();
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(query.FETCH_ALL_COMMENTS);

			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Comments s = new Comments();
				s.setPeerId(rs.getInt("PEER_CTS_ID"));
				s.setFullName(rs.getString("PEER_FULL_NAME"));
				s.setSkillName(rs.getString("Skill_name"));
				s.setComments(rs.getString("comment"));
				s.setDesignation(rs.getString("designation_name"));
				view.add(s);
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
		return view;
	}
}
