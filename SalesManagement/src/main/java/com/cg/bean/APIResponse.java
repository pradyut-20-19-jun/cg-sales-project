package com.cg.bean;

import com.cg.utils.APIConstants;

public class APIResponse {

	private Integer status = APIConstants.API_RESPONSE_STATUS_SUCCESSS;
	private String message = "";
	private Object data = "";
	
	
	
	public APIResponse(Object data) {
		super();
		this.data = data;
	}
	
	public APIResponse(Integer status, String message) {
		super();
		this.status = status;
		this.message = message;
	}
	
	public APIResponse(Integer status, String message, Object data) {
		super();
		this.status = status;
		this.message = message;
		this.data = data;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
}
