package com.rratchet.spring.wechat.open.webaccesstoken;

import com.rratchet.spring.wechat.open.WechatAPI;
import com.rratchet.spring.wechat.open.auth.Authentication;

public class WebAccessTokenAPI extends WechatAPI {

	public static final String WEB_ACCESS_TOKEN_EXCHANGE_API_URL_TEMPLATE = "https://api.weixin.qq.com/sns/oauth2/access_token?appid={appid}&secret={secret}&code={webAuthCode}&grant_type=authorization_code";

	private Authentication authentication;

	public WebAccessTokenAPIResponse exchangeToken(String webAuthCode) {
		WebAccessTokenAPIResponse response = restOperations.getForObject(WEB_ACCESS_TOKEN_EXCHANGE_API_URL_TEMPLATE, WebAccessTokenAPIResponse.class,
				authentication.getAppID(), authentication.getAppsecret(), webAuthCode);
		apiResponseAssert.assertOK(response);
		return response;
	}

	public void setAuthentication(Authentication authentication) {
		this.authentication = authentication;
	}

}
