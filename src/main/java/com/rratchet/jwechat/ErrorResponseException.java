package com.rratchet.jwechat;

public class ErrorResponseException extends RuntimeException {

	private static final long serialVersionUID = 1820602460068625732L;
	
	private Integer errorCode;
	
	private String errorMessage;
	
	public ErrorResponseException(Integer errcode, String errmsg) {
		super(errmsg);
		this.errorCode = errcode;
		this.errorMessage = errmsg;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}


}
