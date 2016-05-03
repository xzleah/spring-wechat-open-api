package com.rratchet.jwechat.tmgmsg;

import org.springframework.web.client.RestOperations;

import com.rratchet.jwechat.APIResponseAssert;
import com.rratchet.jwechat.accesstoken.AccessTokenManager;

public class TemplateMessageAPI {

	public static final String TEMPLATE_MESSAGE_API_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token={ACCESS_TOKEN}";
	
	private AccessTokenManager accessTokenManager;
	private RestOperations restOperations;

	public TemplateMessageAPIResponse send(TemplateMessageAPIRequest request) {
		String token = accessTokenManager.token();
		TemplateMessageAPIResponse response = restOperations.postForObject(TEMPLATE_MESSAGE_API_URL, request, TemplateMessageAPIResponse.class, token);
		APIResponseAssert.assertOK(response);
		return response;
	}
	
	public void setAccessTokenManager(AccessTokenManager accessTokenManager) {
		this.accessTokenManager = accessTokenManager;		
	}

	public void setRestOperations(RestOperations restOperations) {
		this.restOperations = restOperations;		
	}
}
