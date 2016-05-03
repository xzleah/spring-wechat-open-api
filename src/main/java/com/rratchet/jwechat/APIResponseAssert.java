package com.rratchet.jwechat;

import com.rratchet.jwechat.accesstoken.AccessTokenExpiredException;

public class APIResponseAssert {

	public static void assertOK(CommonResponse response) {
		if(response == null) {
			throw new IllegalArgumentException("CommonResponse argument must not be null.");
		}
		assertOK(response.getErrcode(), response.getErrmsg());
	}
	
	public static void assertOK(Integer errorCode, String errorMessage) {
		if(errorCode != null && errorCode != 0) {
			if(errorCode.equals(AccessTokenExpiredException.INVALID_ACCESS_TOKEN_ERROR)) {
				throw new AccessTokenExpiredException(errorMessage);
			}
			throw new ErrorResponseException(errorCode, errorMessage);
		}
	}
}
