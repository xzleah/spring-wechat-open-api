package com.rratchet.spring.wechat.open.config;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.client.RestOperations;

import com.rratchet.spring.wechat.open.Authentication;
import com.rratchet.spring.wechat.open.AuthenticationImpl;
import com.rratchet.spring.wechat.open.RestOperationsFactory;
import com.rratchet.spring.wechat.open.WechatRequestSignatureValidator;
import com.rratchet.spring.wechat.open.accesstoken.AccessTokenAPI;
import com.rratchet.spring.wechat.open.accesstoken.AccessTokenManager;
import com.rratchet.spring.wechat.open.jsticket.JsApiSigner;
import com.rratchet.spring.wechat.open.jsticket.JsApiTicketAPI;
import com.rratchet.spring.wechat.open.jsticket.JsApiTicketManager;
import com.rratchet.spring.wechat.open.tmpmsg.TemplateMessageAPI;
import com.rratchet.spring.wechat.open.webaccesstoken.WebAccessTokenAPI;

public class WechatComponentsFactory {

	public WechatRequestSignatureValidator wechatRequestSignatureValidator(String token) {
		WechatRequestSignatureValidator validator = new WechatRequestSignatureValidator();
		validator.setToken(token);
		return validator;
	}
	
	public RestOperations wechatRestOperations() {
		return new RestOperationsFactory().get();
	}
	
	public ThreadPoolTaskScheduler wechatTaskScheduler() {
		return wechatTaskScheduler(2);
	}
	
	public ThreadPoolTaskScheduler wechatTaskScheduler(int poolSize) {
		ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
		taskScheduler.setPoolSize(poolSize);
		return taskScheduler;
	}
	
	public Authentication wechatAuthentication(String appId, String secret) {
		return new AuthenticationImpl(appId, secret);
	}
	
	public AccessTokenAPI accessTokenAPI(Authentication authentication, RestOperations restOperations) {
		AccessTokenAPI accessTokenAPI = new AccessTokenAPI();
		accessTokenAPI.setRestOperations(restOperations);
		accessTokenAPI.setAuthentication(authentication);
		return accessTokenAPI;
	}
	
	public AccessTokenManager accessTokenManager(AccessTokenAPI accessTokenAPI, ThreadPoolTaskScheduler taskScheduler) {
		AccessTokenManager accessTokenManager = new AccessTokenManager(accessTokenAPI);
		accessTokenManager.setTaskScheduler(taskScheduler);
		return accessTokenManager;
	}
	
	public JsApiTicketAPI jsApiTicketAPI(AccessTokenManager accessTokenManager, RestOperations restOperations) {
		JsApiTicketAPI jsApiTicketAPI = new JsApiTicketAPI();
		jsApiTicketAPI.setAccessTokenManager(accessTokenManager);
		jsApiTicketAPI.setRestOperations(restOperations);
		return jsApiTicketAPI;
	}
	
	public JsApiTicketManager jsApiTicketManager(JsApiTicketAPI jsApiTicketAPI,
			ThreadPoolTaskScheduler taskScheduler) {
		JsApiTicketManager jsApiTicketManager = new JsApiTicketManager(jsApiTicketAPI);
		jsApiTicketManager.setTaskScheduler(taskScheduler);
		return jsApiTicketManager;
	}
	
	public JsApiSigner jsApiSigner(Authentication authentication, JsApiTicketManager jsApiTicketManager) {
		JsApiSigner jsApiSigner = new JsApiSigner();
		jsApiSigner.setAuthentication(authentication);
		jsApiSigner.setJsApiTicketManager(jsApiTicketManager);
		return jsApiSigner;
	}
	
	public TemplateMessageAPI templateMessageAPI(AccessTokenManager accessTokenManager, RestOperations restOperations) {
		TemplateMessageAPI templateMessageAPI = new TemplateMessageAPI();
		templateMessageAPI.setAccessTokenManager(accessTokenManager);
		templateMessageAPI.setRestOperations(restOperations);
		return templateMessageAPI;
	}
	
	public WebAccessTokenAPI webAccessTokenAPI(Authentication authentication, RestOperations restOperations) {
		WebAccessTokenAPI webAccessTokenAPI = new WebAccessTokenAPI();
		webAccessTokenAPI.setAuthentication(authentication);
		webAccessTokenAPI.setRestOperations(restOperations);
		return webAccessTokenAPI;
	}
}
