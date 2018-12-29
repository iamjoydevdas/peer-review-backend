package com.bnym.pr;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bnym.pr.bo.IPeerReviewBo;
import com.bnym.pr.dto.ErrorTo;
import com.bnym.pr.dto.LoginDto;
import com.bnym.pr.dto.PeerReviewResponse;
import com.bnym.pr.dto.UserDto;

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
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
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
	
	@GET
	@Path("/details")
	public Response getUserDetails(@HeaderParam("token") String token) {
		int responseCode = 200;
		PeerReviewResponse response = new PeerReviewResponse();
		Integer loggedInUser = service.getSessionUserId(token);
		System.out.println("Logged In user id "+ loggedInUser);
		UserDto userDto = service.details(loggedInUser);
		if(loggedInUser == null || userDto == null) {
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
	public Response peerCreate(UserDto userDto) {
		System.out.println(userDto.toString());
		int cnt = service.create(userDto);
		int responseCode = 201;
		PeerReviewResponse response = new PeerReviewResponse();
		if(cnt <= 0) {
			response.setSuccess(false);
			responseCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
			response.setMessage("Something Went Wrong");
			ErrorTo error = new ErrorTo();
			error.setErrorCode(responseCode);
			error.setErrorMessage("Something Went Wrong");
			response.setError(error);
		}else {
			response.setSuccess(true);
			response.setMessage("Peer Created Successfully.");
		}
		return Response.status(responseCode).entity(response)
				.build();
	}
	
	@PUT
	@Path("/peer/{peerId}/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response peerUpdate() {
		return Response.status(201).entity("create")
				.build();
	}
	
	@DELETE
	@Path("/peer/{peerId}/delete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response peerDelete() {
		return Response.status(201).entity("create")
				.build();
	}
}