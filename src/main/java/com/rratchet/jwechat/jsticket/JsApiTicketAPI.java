package com.rratchet.jwechat.jsticket;

import org.springframework.web.client.RestOperations;

import com.rratchet.jwechat.CommonResponseAssert;
import com.rratchet.jwechat.Token;
import com.rratchet.jwechat.TokenAPI;
import com.rratchet.jwechat.accesstoken.AccessTokenManager;

public class JsApiTicketAPI implements TokenAPI {

	public final static String ACCESS_TOKEN_API_TEMPLATE = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token={ACCESS_TOKEN}&type=jsapi";
	
	private AccessTokenManager accessTokenManager;
	
	private RestOperations restOperations;

	public Token acquireToken() {
		String token = accessTokenManager.token();
		JsApiTicketAPIResponse response = restOperations.getForObject(ACCESS_TOKEN_API_TEMPLATE, JsApiTicketAPIResponse.class, token);
		CommonResponseAssert.assertOK(response);
		Token ticket = new Token(response.getTicket(), response.getExpires_in());
		return ticket;
	}

	public void setAccessTokenManager(AccessTokenManager accessTokenManager) {
		this.accessTokenManager = accessTokenManager;
	}

	public void setRestOperations(RestOperations restOperations) {
		this.restOperations = restOperations;
	}

}
