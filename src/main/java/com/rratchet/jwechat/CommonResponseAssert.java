package com.rratchet.jwechat;

import java.text.MessageFormat;

public class CommonResponseAssert {

	public static void assertOK(CommonResponse response) {
		if(response == null) {
			throw new IllegalArgumentException("CommonResponse argument must not be null.");
		}
		Integer errorCode = response.getErrcode();
		if(errorCode != null && errorCode != 0) {
			String errorMessage = MessageFormat.format("wechat response errcode:{0}, errmsg:{1}", errorCode, response.getErrmsg());
			throw new ErrorResponseException(errorCode, errorMessage);
		}
	}
}
