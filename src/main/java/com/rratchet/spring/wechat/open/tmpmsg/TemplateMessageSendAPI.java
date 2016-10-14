package com.rratchet.spring.wechat.open.tmpmsg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rratchet.spring.wechat.open.WechatAPI;
import com.rratchet.spring.wechat.open.token.accesstoken.AccessTokenManager;

public class TemplateMessageSendAPI extends WechatAPI {

	public static final String TEMPLATE_MESSAGE_API_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token={ACCESS_TOKEN}";
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private AccessTokenManager accessTokenManager;

	public TemplateMessageSendAPIResponse send(TemplateMessageSendAPIRequest request) {
		String token = accessTokenManager.token();
		TemplateMessageSendAPIResponse response = restOperations.postForObject(TEMPLATE_MESSAGE_API_URL, request, TemplateMessageSendAPIResponse.class, token);
		logger.debug("post request {} get response {}", request, response);
		apiResponseAssert.assertOK(response);
		return response;
	}
	
	public void setAccessTokenManager(AccessTokenManager accessTokenManager) {
		this.accessTokenManager = accessTokenManager;		
	}
}
