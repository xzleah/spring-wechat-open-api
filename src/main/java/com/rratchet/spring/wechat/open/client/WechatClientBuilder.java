package com.rratchet.spring.wechat.open.client;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.client.RestOperations;

import com.rratchet.spring.wechat.open.APIResponseAssert;
import com.rratchet.spring.wechat.open.auth.Authentication;
import com.rratchet.spring.wechat.open.config.ConfigProperties;
import com.rratchet.spring.wechat.open.config.WechatComponentsFactory;
import com.rratchet.spring.wechat.open.token.accesstoken.AccessTokenAPI;
import com.rratchet.spring.wechat.open.token.accesstoken.AccessTokenManager;
import com.rratchet.spring.wechat.open.token.jsticket.JsApiTicketAPI;
import com.rratchet.spring.wechat.open.token.jsticket.JsApiTicketManager;

public class WechatClientBuilder {

	private ConfigProperties configProperties;
	private boolean enableJsApiTicketManager = false;
	private boolean refreshJsApiTicketWhenBuild = false;
	private boolean refreshAccessTokenWhenBuild = true;
	
	public static WechatClientBuilder config() {
		return new WechatClientBuilder();
	}
	
	public WechatClientBuilder() {
		this.configProperties = new ConfigProperties();
	}
	
	public WechatClientBuilder validationToken(String validationToken) {
		configProperties.setValidationToken(validationToken);
		return this;
	}
	
	public WechatClientBuilder appId(String appId) {
		configProperties.setAppId(appId);
		return this;
	}
	
	public WechatClientBuilder appSecret(String appSecret) {
		configProperties.setAppSecret(appSecret);
		return this;
	}
	
	public WechatClientBuilder enableJsApiTicketManager() {
		enableJsApiTicketManager = true;
		refreshJsApiTicketWhenBuild = true;
		return this;
	}
	
	public WechatClientBuilder disableRefreshAccessTokenWhenBuild() {
		refreshAccessTokenWhenBuild = false;
		return this;
	}
	
	public WechatClientBuilder disableRefreshJsApiTicketWhenBuild() {
		refreshJsApiTicketWhenBuild = false;
		return this;
	}
	
	public WechatClient build() {
		APIResponseAssert apiResponseAssert = new APIResponseAssert();
		
		WechatComponentsFactory wechatComponentsFactory = new WechatComponentsFactory();
		RestOperations wechatRestOperations = wechatComponentsFactory.wechatRestOperations();
		Authentication wechatAuthentication = wechatComponentsFactory.wechatAuthentication(configProperties.getAppId(), configProperties.getAppSecret());
		AccessTokenAPI accessTokenAPI = wechatComponentsFactory.accessTokenAPI(wechatAuthentication, wechatRestOperations, apiResponseAssert);
		ThreadPoolTaskScheduler wechatTaskScheduler = wechatComponentsFactory.wechatTaskScheduler();
		wechatTaskScheduler.afterPropertiesSet();
		AccessTokenManager accessTokenManager = wechatComponentsFactory.accessTokenManager(accessTokenAPI, wechatTaskScheduler);
		if(refreshAccessTokenWhenBuild) {
			accessTokenManager.refresh();
		}
		
		WechatClient wechatClient = new WechatClient();
		wechatClient.setConfigProperties(configProperties);
		wechatClient.setRestOperations(wechatRestOperations);
		wechatClient.setAccessTokenManager(accessTokenManager);
		wechatClient.setApiResponseAssert(apiResponseAssert);
		if(enableJsApiTicketManager) {
			JsApiTicketAPI jsApiTicketAPI = new JsApiTicketAPI();
			jsApiTicketAPI.setRestOperations(wechatRestOperations);
			jsApiTicketAPI.setApiResponseAssert(apiResponseAssert);
			jsApiTicketAPI.setAccessTokenManager(accessTokenManager);
			JsApiTicketManager jsApiTicketManager = new JsApiTicketManager(jsApiTicketAPI);
			jsApiTicketManager.setTaskScheduler(wechatTaskScheduler);
			wechatClient.setJsApiTicketManager(jsApiTicketManager);
			if(refreshJsApiTicketWhenBuild) {
				jsApiTicketManager.refresh();
			}
		}
		
		return wechatClient;
	}
}
