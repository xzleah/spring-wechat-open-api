package com.rratchet.jwechat;

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
