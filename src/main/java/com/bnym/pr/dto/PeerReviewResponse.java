package com.bnym.pr.dto;

import java.util.List;

public class PeerReviewResponse {
	private boolean success;
	private List<?> dataList;
	private Object data;
	private String message;
	private String token;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public List<?> getDataList() {
		return dataList;
	}
	public void setDataList(List<?> dataList) {
		this.dataList = dataList;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public String toString() {
		return "PeerReviewResponse [success=" + success + ", dataList=" + dataList + ", data=" + data + ", message="
				+ message + ", token=" + token + "]";
	}
}
