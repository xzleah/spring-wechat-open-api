package com.rratchet.jwechat.webaccesstoken;

import com.rratchet.jwechat.CommonResponse;

public class WebAccessTokenAPIResponse extends CommonResponse {

	private static final long serialVersionUID = 7870067943311330069L;

	private String access_token;
	
	private Integer expires_in;
	
	private String refresh_token;
	
	private String openid;

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public Integer getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(Integer expires_in) {
		this.expires_in = expires_in;
	}

	public String getRefresh_token() {
		return refresh_token;
	}

	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
}
