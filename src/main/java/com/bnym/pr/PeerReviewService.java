package com.bnym.pr;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bnym.pr.bo.IPeerReviewBo;
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
		return Response.status(200).entity("Peer Review Is Up,, Guys!!!!!").build();
	}
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public PeerReviewResponse login(LoginDto loginDto) {
		PeerReviewResponse response = new PeerReviewResponse();
		System.out.println(loginDto.getUserName()+ "  "+loginDto.getPassword());
		boolean flag = service.login(loginDto);
		response.setSuccess(flag);
		if(flag) {
			response.setToken(service.generateAndStoreToken(loginDto));
			response.setMessage("Login successful");
		} else {
			response.setMessage("Login denied");
		}
		return response;
	}

}