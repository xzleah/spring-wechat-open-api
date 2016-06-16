package com.rratchet.jwechat.webaccesstoken;

import org.springframework.web.client.RestOperations;

import com.rratchet.jwechat.Authentication;
import com.rratchet.jwechat.WechatAPI;

public class WebAccessTokenAPI extends WechatAPI {

	public static final String WEB_ACCESS_TOKEN_EXCHANGE_API_URL_TEMPLATE = "https://api.weixin.qq.com/sns/oauth2/access_token?appid={appid}&secret={secret}&code={webAuthCode}&grant_type=authorization_code";

	private RestOperations restOperations;
	private Authentication authentication;

	public WebAccessTokenAPIResponse exchangeToken(String webAuthCode) {
		WebAccessTokenAPIResponse response = restOperations.getForObject(WEB_ACCESS_TOKEN_EXCHANGE_API_URL_TEMPLATE, WebAccessTokenAPIResponse.class,
				authentication.getAppID(), authentication.getAppsecret(), webAuthCode);
		apiResponseAssert.assertOK(response);
		return response;
	}

	public void setRestOperations(RestOperations restOperations) {
		this.restOperations = restOperations;
	}

	public void setAuthentication(Authentication authentication) {
		this.authentication = authentication;
	}

}
