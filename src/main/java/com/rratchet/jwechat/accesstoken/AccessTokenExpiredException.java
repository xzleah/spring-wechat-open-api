package com.rratchet.jwechat.accesstoken;

import com.rratchet.jwechat.ErrorResponseException;

public class AccessTokenExpiredException extends ErrorResponseException {

	private static final long serialVersionUID = -4713434272200424445L;
	
	public final static int INVALID_ACCESS_TOKEN_ERROR = 40001; 
	
	public AccessTokenExpiredException(String errmsg) {
		super(INVALID_ACCESS_TOKEN_ERROR, errmsg);
	}

}
