package com.rratchet.jwechat;

import java.text.MessageFormat;

import com.rratchet.jwechat.accesstoken.AccessTokenExpiredException;

public class APIResponseAssert {

	public static void assertOK(CommonResponse response) {
		if(response == null) {
			throw new IllegalArgumentException("CommonResponse argument must not be null.");
		}
		assertOK(response.getErrmsg(), response.getErrcode());
	}
	
	public static void assertOK(String errorMessage, Integer errorCode) {
		if(errorCode != null && errorCode != 0) {
			if(errorCode.equals(AccessTokenExpiredException.INVALID_ACCESS_TOKEN_ERROR)) {
				
				throw new AccessTokenExpiredException(errorMessage);
			}
			String formatedErrorMessage = MessageFormat.format("wechat response errcode:{0}, errmsg:{1}", errorCode.toString(), errorMessage);
			throw new ErrorResponseException(errorCode, formatedErrorMessage);
		}
	}
}
