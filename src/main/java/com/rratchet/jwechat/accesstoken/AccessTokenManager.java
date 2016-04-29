package com.rratchet.jwechat.accesstoken;

import com.rratchet.jwechat.SchedulableTokenManager;

public class AccessTokenManager extends SchedulableTokenManager {

	public AccessTokenManager(AccessTokenAPI accessTokenAPI) {
		setTokenAPI(accessTokenAPI);
	}

}
