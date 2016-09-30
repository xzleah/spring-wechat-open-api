package com.rratchet.spring.wechat.open.accesstoken;

import com.rratchet.spring.wechat.open.SchedulableTokenManager;

public class AccessTokenManager extends SchedulableTokenManager {

	public AccessTokenManager(AccessTokenAPI accessTokenAPI) {
		setTokenAPI(accessTokenAPI);
	}

}
