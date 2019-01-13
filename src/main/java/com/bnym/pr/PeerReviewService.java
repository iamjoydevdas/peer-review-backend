package com.bnym.pr;

import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bnym.pr.bo.IPeerReviewBo;
import com.bnym.pr.dto.ErrorTo;
import com.bnym.pr.dto.LoginDto;
import com.bnym.pr.dto.PeerReviewResponse;
import com.bnym.pr.dto.RatingScores;
import com.bnym.pr.dto.Skills;
import com.bnym.pr.dto.UserDto;
import com.bnym.pr.handler.PeerReviewBusinessException;
import com.bnym.pr.handler.PeerReviewDatabaseException;
import com.bnym.pr.handler.PeerReviewException;

@Component
@Path("/pr")
public class PeerReviewService {

	@Autowired
	IPeerReviewBo service;



	@GET
	@Path("/ping")
	public Response ping() {
		PeerReviewResponse response = new PeerReviewResponse();
		response.setSuccess(true);
		response.setMessage("Peer Review Is Up,, Guys!!!!!");
		return Response.status(200).entity(response).build();
	}

	@POST
	@Path("/login")

	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response login(LoginDto loginDto) {
		int responseCode = 200;
		PeerReviewResponse response = new PeerReviewResponse();
		System.out.println(loginDto.getUserName()+ "  "+loginDto.getPassword());
		boolean flag = service.login(loginDto);
		response.setSuccess(flag);
		if(flag) {
			response.setToken(service.generateAndStoreToken(loginDto));
			response.setMessage("Login successful");
		} else {
			response.setMessage("Login denied");
			ErrorTo error = new ErrorTo();
			error.setErrorCode(401);
			error.setErrorMessage("Login denied");		
			response.setError(error);
			responseCode = 401;
		}
		return Response.status(responseCode).entity(response)
				.build();
	}

	@PUT
	@Path("/peer/password/change")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response changePassword(@HeaderParam("token") String token, LoginDto userDto) throws PeerReviewException, PeerReviewDatabaseException {
		Integer peerId = service.getSessionUserId(token);
		int cnt = service.changePassword(userDto, peerId);
		int responseCode = 200;
		PeerReviewResponse response = new PeerReviewResponse();
		if(cnt > 0) {
			response.setSuccess(true);
			response.setMessage("Peer Password Changed Successfully.");
		}else {
			response.setMessage("Password Updated Failed");
			ErrorTo error = new ErrorTo();
			error.setErrorCode(500);
			error.setErrorMessage("Password Updated Failed");		
			response.setError(error);
			responseCode = 500;
		}
		return Response.status(responseCode).entity(response)
				.build();
	}


	@PUT
	@Path("/peer/{peerid}/resetPassword")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response resetPassword(@HeaderParam("token") String token, @PathParam("peerid") Integer peerId, LoginDto userDto) throws PeerReviewException, PeerReviewDatabaseException {
		System.out.println(peerId);
		service.getSessionUserId(token);
		int cnt = service.resetPassword(userDto, peerId);
		int responseCode = 200;
		PeerReviewResponse response = new PeerReviewResponse();
		response.setSuccess(true);
		response.setMessage("Peer Password Reseted Successfully.");
		return Response.status(responseCode).entity(response)
				.build();

	}

	@PUT

	@Path("/peer/{peerId}/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response peerUpdate(@HeaderParam("token") String token, @PathParam("peerId") Integer peerId, UserDto userDto) throws PeerReviewException, PeerReviewDatabaseException {
		System.out.println(peerId);
		service.getSessionUserId(token);
		service.update(userDto, peerId);
		int responseCode = 200;
		PeerReviewResponse response = new PeerReviewResponse();
		response.setSuccess(true);
		response.setMessage("Peer Updated Successfully.");
		return Response.status(responseCode).entity(response)
				.build();
	}

	@GET
	@Path("/details")
	public Response getUserDetails(@HeaderParam("token") String token) throws PeerReviewException {
		int responseCode = 200;
		PeerReviewResponse response = new PeerReviewResponse();
		Integer loggedInUser = service.getSessionUserId(token);
		if(loggedInUser == null) {
			throw new PeerReviewException(501, "Unauthorized");
		}
		System.out.println("Logged In user id "+ loggedInUser);
		UserDto userDto = service.details(loggedInUser);
		if(userDto == null) {
			response.setSuccess(false);
			responseCode = HttpServletResponse.SC_NOT_FOUND;
			response.setMessage("User Details Not Found.");
			ErrorTo error = new ErrorTo();
			error.setErrorCode(responseCode);
			error.setErrorMessage("User Details Not Found.");
			response.setError(error);
		}else {
			response.setSuccess(true);
			response.setData(userDto);
		}
		return Response.status(responseCode).entity(response)
				.build();
	}

	@POST
	@Path("/peer/create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response peerCreate(@HeaderParam("token") String token, UserDto userDto) throws PeerReviewException, PeerReviewDatabaseException {
		System.out.println(userDto.toString());
		service.getSessionUserId(token);
		service.create(userDto);
		int responseCode = 201;
		PeerReviewResponse response = new PeerReviewResponse();
		response.setSuccess(true);
		response.setMessage("Peer Created Successfully.");
		return Response.status(responseCode).entity(response).build();
	}



	@DELETE
	@Path("/peer/{peerId}/delete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response peerDelete(@HeaderParam("token") String token, @PathParam("peerId") Integer peerId) throws PeerReviewException, PeerReviewDatabaseException {
		System.out.println(peerId);
		service.getSessionUserId(token);
		service.delete(peerId);
		int responseCode = 200;
		PeerReviewResponse response = new PeerReviewResponse();
		response.setSuccess(true);
		response.setMessage("Peer Deleted Successfully.");
		return Response.status(responseCode).entity(response)
				.build();
	}
	@GET
	@Path("/users/details")
	public Response viewDetails(@HeaderParam("token") String token) throws PeerReviewBusinessException, PeerReviewException {
		System.out.println(token);
		Integer loggedInUser = service.getSessionUserId(token);
		PeerReviewResponse response = new PeerReviewResponse();
		response.setSuccess(true);
		response.setDataList(service.viewDetails(loggedInUser));
		return Response.status(200).entity(response).build();
	}

	@GET
	@Path("/statics")
	public Response statics(@HeaderParam("token") String token) throws PeerReviewBusinessException, PeerReviewException {

		System.out.println(token);
		Integer loggedInUser = service.getSessionUserId(token);
		if(loggedInUser == null) {
			throw new PeerReviewException(501, "Unauthorized");
		}
		PeerReviewResponse response = new PeerReviewResponse();
		response.setSuccess(true);
		response.setDataList(service.statics());
		return Response.status(200).entity(response).build();
	}



	@POST
	@Path("/peer/rate/save")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response peerRate(@HeaderParam("token") String token, RatingScores rated) throws PeerReviewException, PeerReviewDatabaseException, PeerReviewBusinessException {
		System.out.println(rated.toString());
		Integer fromPeerId=service.getSessionUserId(token);
		service.ratePeer(fromPeerId,rated);
		int responseCode = 201;
		PeerReviewResponse response = new PeerReviewResponse();
		response.setSuccess(true);
		response.setMessage("Peer Rated Successfully.");
		return Response.status(responseCode).entity(response).build();
	}

	@POST
	@Path("/peer/addskills")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response peerRate(@HeaderParam("token") String token, Skills skills) throws PeerReviewException, PeerReviewDatabaseException, PeerReviewBusinessException, SQLException {
		//System.out.println(rated.toString());
		service.getSessionUserId(token);
		service.addSkills(skills);
		int responseCode = 201;
		PeerReviewResponse response = new PeerReviewResponse();
		response.setSuccess(true);
		response.setMessage("Skill Added Successfully.");
		return Response.status(responseCode).entity(response).build();
	}


	@GET
	@Path("/view/technology/{skills}")

	public Response byTechnology(@HeaderParam("token") String token, @PathParam("skills") Integer skills) throws PeerReviewBusinessException, PeerReviewException {

		System.out.println(token);
		Integer loggedInUser = service.getSessionUserId(token);
		if(loggedInUser == null) {
			throw new PeerReviewException(501, "Unauthorized");
		}
		PeerReviewResponse response = new PeerReviewResponse();
		response.setSuccess(true);
		response.setDataList(service.dataBySkill(skills));
		return Response.status(200).entity(response).build();
	}

	@GET
	@Path("/view/designation/{designation}")

	public Response byDesignation(@HeaderParam("token") String token, @PathParam("designation") Integer designation) throws PeerReviewBusinessException, PeerReviewException {

		System.out.println(token);
		Integer loggedInUser = service.getSessionUserId(token);
		if(loggedInUser == null) {
			throw new PeerReviewException(501, "Unauthorized");
		}
		PeerReviewResponse response = new PeerReviewResponse();
		response.setSuccess(true);
		response.setDataList(service.dataByDesignation(designation));
		return Response.status(200).entity(response).build();
	}

	@GET
	@Path("/view/comments")

	public Response viewComments(@HeaderParam("token") String token) throws PeerReviewBusinessException, PeerReviewException {

		System.out.println(token);
		Integer loggedInUser = service.getSessionUserId(token);
		if(loggedInUser == null) {
			throw new PeerReviewException(501, "Unauthorized");
		}
		PeerReviewResponse response = new PeerReviewResponse();
		response.setSuccess(true);
		response.setDataList(service.viewComments());
		return Response.status(200).entity(response).build();
	}

}