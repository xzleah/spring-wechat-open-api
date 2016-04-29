package com.rratchet.jwechat;

public class CommonResponseAssert {

	public static void assertOK(CommonResponse response) {
		if(response == null) {
			throw new IllegalArgumentException("CommonResponse argument must not be null.");
		}
		Integer errorCode = response.getErrcode();
		if(errorCode != null && errorCode != 0) {
			throw new ErrorResponseException(errorCode, response.getErrmsg());
		}
	}
}
