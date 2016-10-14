package com.rratchet.spring.wechat.open.token.accesstoken;

import com.rratchet.spring.wechat.open.token.SchedulableTokenManager;

public class AccessTokenManager extends SchedulableTokenManager {

	public AccessTokenManager(AccessTokenAPI accessTokenAPI) {
		setTokenAPI(accessTokenAPI);
	}

}
