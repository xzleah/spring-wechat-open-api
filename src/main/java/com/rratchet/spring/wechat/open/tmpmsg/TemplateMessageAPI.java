package com.rratchet.spring.wechat.open.tmpmsg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestOperations;

import com.rratchet.spring.wechat.open.WechatAPI;
import com.rratchet.spring.wechat.open.accesstoken.AccessTokenManager;

public class TemplateMessageAPI extends WechatAPI {

	public static final String TEMPLATE_MESSAGE_API_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token={ACCESS_TOKEN}";
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private AccessTokenManager accessTokenManager;
	private RestOperations restOperations;

	public TemplateMessageAPIResponse send(TemplateMessageAPIRequest request) {
		String token = accessTokenManager.token();
		TemplateMessageAPIResponse response = restOperations.postForObject(TEMPLATE_MESSAGE_API_URL, request, TemplateMessageAPIResponse.class, token);
		logger.debug("post request {} get response {}", request, response);
		apiResponseAssert.assertOK(response);
		return response;
	}
	
	public void setAccessTokenManager(AccessTokenManager accessTokenManager) {
		this.accessTokenManager = accessTokenManager;		
	}

	public void setRestOperations(RestOperations restOperations) {
		this.restOperations = restOperations;		
	}
}
