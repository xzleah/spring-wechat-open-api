package com.rratchet.spring.wechat.open.auth;

public class AuthenticationImpl implements Authentication {

	private String appId;
	
	private String appSecret;

	public AuthenticationImpl(String appId, String appSecret) {
		this.appId = appId;
		this.appSecret = appSecret;
	}
	
	public String getAppID() {
		return appId;
	}

	public String getAppsecret() {
		return appSecret;
	}

}
