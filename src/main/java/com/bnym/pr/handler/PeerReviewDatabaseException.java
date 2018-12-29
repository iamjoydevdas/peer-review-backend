package com.bnym.pr.handler;

public class PeerReviewDatabaseException extends Exception {
	private static final long serialVersionUID = -4235336700517186955L;
	
	public PeerReviewDatabaseException(int errorCode, String errorMessage) throws PeerReviewException {
		super();
		PeerReviewException ex = new PeerReviewException();
		ex.setErrorCode(errorCode);
		ex.setErrorMessage("PeerReviewDatabaseException -> "+ errorMessage);
		throw ex;
	}
	
}
