package com.bnym.pr.handler;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.bnym.pr.dto.ErrorTo;
import com.bnym.pr.dto.PeerReviewResponse;

@Provider
public class ExceptionHandler extends Exception implements ExceptionMapper<PeerReviewException>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 332122629321459700L;

	@Override
	public Response toResponse(PeerReviewException exception) {
		PeerReviewResponse response = new PeerReviewResponse();
		ErrorTo errorTo = new ErrorTo();
		errorTo.setErrorCode(exception.getErrorCode());
		errorTo.setErrorMessage(exception.getErrorMessage());
		response.setError(errorTo);
		response.setSuccess(false);
		return Response.status(exception.getErrorCode()).entity(response).build();
	}

}
