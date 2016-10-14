package com.rratchet.spring.wechat.open.client;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.web.client.RestOperations;

import com.rratchet.spring.wechat.open.APIResponseAssert;
import com.rratchet.spring.wechat.open.auth.Authentication;
import com.rratchet.spring.wechat.open.auth.AuthenticationImpl;
import com.rratchet.spring.wechat.open.config.ConfigProperties;
import com.rratchet.spring.wechat.open.menu.MenuAPI;
import com.rratchet.spring.wechat.open.menu.MenuCreationAPI;
import com.rratchet.spring.wechat.open.menu.MenuQueryAPI;
import com.rratchet.spring.wechat.open.sign.WechatRequestSignatureValidator;
import com.rratchet.spring.wechat.open.tmpmsg.TemplateMessageAPI;
import com.rratchet.spring.wechat.open.tmpmsg.TemplateMessageSendAPI;
import com.rratchet.spring.wechat.open.token.accesstoken.AccessTokenManager;
import com.rratchet.spring.wechat.open.token.jsticket.JsApiSigner;
import com.rratchet.spring.wechat.open.token.jsticket.JsApiTicketManager;
import com.rratchet.spring.wechat.open.webaccesstoken.WebAccessTokenAPI;

public class WechatClient implements DisposableBean {

	private ConfigProperties configProperties;
	
	//每个公众号一个单例
	private RestOperations restOperations;
	private AccessTokenManager accessTokenManager;
	private JsApiTicketManager jsApiTicketManager;
	private APIResponseAssert apiResponseAssert;

	public WechatClient() {
	}
	
	public WechatRequestSignatureValidator requestSignatureValidator() {
		WechatRequestSignatureValidator validator = new WechatRequestSignatureValidator();
		validator.setToken(configProperties.getValidationToken());
		return validator;
	}
	
	public Authentication wechatAuthentication() {
		return new AuthenticationImpl(configProperties.getAppId(), configProperties.getAppSecret());
	}
	
	public RestOperations wechatRestOperations() {
		return restOperations;
	}
	
	public TemplateMessageAPI templateMessageAPI() {
		TemplateMessageSendAPI templateMessageSendAPI = new TemplateMessageSendAPI();
		templateMessageSendAPI.setRestOperations(restOperations);
		templateMessageSendAPI.setApiResponseAssert(apiResponseAssert);
		templateMessageSendAPI.setAccessTokenManager(accessTokenManager);
		
		TemplateMessageAPI templateMessageAPI = new TemplateMessageAPI();
		templateMessageAPI.setTemplateMessageSendAPI(templateMessageSendAPI);
		return templateMessageAPI;
	}
	
	public MenuAPI menuAPI() {
		MenuQueryAPI menuQueryAPI = new MenuQueryAPI();
		menuQueryAPI.setRestOperations(restOperations);
		menuQueryAPI.setApiResponseAssert(apiResponseAssert);
		menuQueryAPI.setAccessTokenManager(accessTokenManager);
		
		MenuCreationAPI menuCreationAPI = new MenuCreationAPI();
		menuCreationAPI.setRestOperations(restOperations);
		menuCreationAPI.setApiResponseAssert(apiResponseAssert);
		menuCreationAPI.setAccessTokenManager(accessTokenManager);
		
		MenuAPI menuAPI = new MenuAPI();
		menuAPI.setMenuQueryAPI(menuQueryAPI);
		menuAPI.setMenuCreationAPI(menuCreationAPI);
		return menuAPI;
	}
	
	public WebAccessTokenAPI webAccessTokenAPI() {
		WebAccessTokenAPI webAccessTokenAPI = new WebAccessTokenAPI();
		webAccessTokenAPI.setRestOperations(restOperations);
		webAccessTokenAPI.setApiResponseAssert(apiResponseAssert);
		webAccessTokenAPI.setAuthentication(wechatAuthentication());
		return webAccessTokenAPI;
	}
	
	public JsApiSigner jsApiSigner() {
		if(jsApiTicketManager == null) {
			throw new IllegalStateException("Missing jsApiTicketManager.");
		}
		JsApiSigner jsApiSigner = new JsApiSigner();
		jsApiSigner.setAuthentication(wechatAuthentication());
		jsApiSigner.setJsApiTicketManager(jsApiTicketManager);
		return jsApiSigner;
	}
	
	public JsApiTicketManager jsApiTicketManager() {
		if(jsApiTicketManager == null) {
			throw new IllegalStateException("Missing jsApiTicketManager.");
		}
		return jsApiTicketManager;
	}
	
	public AccessTokenManager accessTokenManager() {
		return this.accessTokenManager;
	}
	
	public void destroy() throws Exception {
		accessTokenManager.destroy();
		jsApiTicketManager.destroy();
	}
	
	// setters
	public void setConfigProperties(ConfigProperties configProperties) {
		this.configProperties = configProperties;
	}

	public void setRestOperations(RestOperations restOperations) {
		this.restOperations = restOperations;
	}

	public void setAccessTokenManager(AccessTokenManager accessTokenManager) {
		this.accessTokenManager = accessTokenManager;
	}

	public void setJsApiTicketManager(JsApiTicketManager jsApiTicketManager) {
		this.jsApiTicketManager = jsApiTicketManager;
	}

	public void setApiResponseAssert(APIResponseAssert apiResponseAssert) {
		this.apiResponseAssert = apiResponseAssert;
	}

}
