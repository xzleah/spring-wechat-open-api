package com.rratchet.spring.wechat.open.menu;

import com.rratchet.spring.wechat.open.WechatAPI;
import com.rratchet.spring.wechat.open.token.accesstoken.AccessTokenManager;

public class MenuCreationAPI extends WechatAPI {

	public static final String URL_TEMPLATE = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token={ACCESS_TOKEN}";
	
	private AccessTokenManager accessTokenManager;

	public MenuCreationAPIResponse create(MenuCreationAPIRequest request) {
		MenuCreationAPIResponse response = restOperations.postForObject(URL_TEMPLATE, request, MenuCreationAPIResponse.class, accessTokenManager.token());
		apiResponseAssert.assertOK(response);
		return response;
	}

	public void setAccessTokenManager(AccessTokenManager accessTokenManager) {
		this.accessTokenManager = accessTokenManager;
	}


}
