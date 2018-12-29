package com.bnym.pr.handler;

public class PeerReviewException extends Exception {
	private static final long serialVersionUID = 4679227572108646584L;
	private int errorCode;
	private String errorMessage;
	
	public PeerReviewException() {
		super();
	}
	public PeerReviewException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	public PeerReviewException(String message, Throwable cause) {
		super(message, cause);
	}
	public PeerReviewException(String message) {
		super(message);
	}
	public PeerReviewException(Throwable cause) {
		super(cause);
	}
	public PeerReviewException(int errorCode, String errorMessage) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
