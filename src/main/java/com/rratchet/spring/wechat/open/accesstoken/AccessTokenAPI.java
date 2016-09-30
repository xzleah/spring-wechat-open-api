package com.rratchet.spring.wechat.open.accesstoken;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestClientException;

import com.rratchet.spring.wechat.open.Authentication;
import com.rratchet.spring.wechat.open.ErrorResponseException;
import com.rratchet.spring.wechat.open.Token;
import com.rratchet.spring.wechat.open.TokenAPI;
import com.rratchet.spring.wechat.open.WechatAPI;

public class AccessTokenAPI extends WechatAPI implements TokenAPI {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public final static String ACCESS_TOKEN_API_TEMPLATE = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={APPID}&secret={APPSECRET}";

	private Authentication authentication;

	public Token acquireToken() throws RestClientException, ErrorResponseException {
		String appID = authentication.getAppID();
		String appsecret = authentication.getAppsecret();
		AccessTokenAPIResponse response = restOperations.getForObject(ACCESS_TOKEN_API_TEMPLATE, AccessTokenAPIResponse.class, appID, appsecret);
		apiResponseAssert.assertOK(response);
		logger.info("acquiring a Wechat access token and getting a correct response.");
		Token token = new Token(response.getAccess_token(), response.getExpires_in());
		return token;
	}

	public void setAuthentication(Authentication authentication) {
		this.authentication = authentication;
	}

}
