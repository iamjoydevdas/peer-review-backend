package com.bnym.pr.handler;

public class PeerReviewBusinessException  extends Exception {
	private static final long serialVersionUID = -4235336700517186955L;
	
	public PeerReviewBusinessException(int errorCode, String errorMessage) throws PeerReviewException  {
		super();
		PeerReviewException ex = new PeerReviewException();
		ex.setErrorCode(errorCode);
		ex.setErrorMessage("PeerReviewBusinessException -> "+ errorMessage);
		throw ex;
	}
	
}
