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
		Integer loggedInUser = service.getSessionUserId(token);
		System.out.println("Logged In user id "+ loggedInUser);
		return Response.status(HttpServletResponse.SC_ACCEPTED).entity(loggedInUser)
				.build();
	}
	
	@POST
	@Path("/peer/create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response peerCreate() {
		return Response.status(201).entity("create")
				 .header("Access-Control-Max-Age", "151200")
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