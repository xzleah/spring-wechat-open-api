package com.rratchet.jwechat.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.client.RestOperations;

import com.rratchet.jwechat.Authentication;
import com.rratchet.jwechat.WeChatRequestSignatureValidator;
import com.rratchet.jwechat.accesstoken.AccessTokenAPI;
import com.rratchet.jwechat.accesstoken.AccessTokenManager;
import com.rratchet.jwechat.jsticket.JsApiSigner;
import com.rratchet.jwechat.jsticket.JsApiTicketAPI;
import com.rratchet.jwechat.jsticket.JsApiTicketManager;
import com.rratchet.jwechat.tmgmsg.TemplateMessageAPI;
import com.rratchet.jwechat.webaccesstoken.WebAccessTokenAPI;

@Configuration
@PropertySource({ "classpath:jwechat.properties" }) 
public class AllJWechatComponentsConfig {

	@Autowired
	private Environment env;
	
	private JWechatComponentsFactory componentsFactory;
	
	public AllJWechatComponentsConfig() {
		componentsFactory = new JWechatComponentsFactory();
	}
	
	@Bean
	public WeChatRequestSignatureValidator wechatRequestSignatureValidator() {
		return componentsFactory.wechatRequestSignatureValidator(env.getProperty("jwechat.notify.request.token"));
	}
	
	@Bean
	public RestOperations wechatRestOperations() {
		return componentsFactory.wechatRestOperations();
	}
	
	@Bean
	public ThreadPoolTaskScheduler wechatTaskScheduler() {
		return componentsFactory.wechatTaskScheduler();
	}
	
	@Bean
	public Authentication wechatAuthentication() {
		return componentsFactory.wechatAuthentication(env.getProperty("jwechat.appid"),
				env.getProperty("jwechat.secret"));
	}
	
	@Bean
	public AccessTokenAPI accessTokenAPI() {
		return componentsFactory.accessTokenAPI(wechatAuthentication(), wechatRestOperations());
	}
	
	@Bean
	public AccessTokenManager accessTokenManager() {
		AccessTokenManager accessTokenManager = componentsFactory.accessTokenManager(accessTokenAPI(), wechatTaskScheduler());
		Boolean refreshOnStartup = env.getProperty("jwechat.refresh.access.token.on.startup", Boolean.class, Boolean.TRUE);
		if(refreshOnStartup) {
			accessTokenManager.refresh();
		}
		return accessTokenManager;
	}
	
	@Bean
	public JsApiTicketAPI jsApiTicketAPI() {
		return componentsFactory.jsApiTicketAPI(accessTokenManager(), wechatRestOperations());
	}
	
	@Bean
	public JsApiTicketManager jsApiTicketManager() {
		JsApiTicketManager jsApiTicketManager = componentsFactory.jsApiTicketManager(jsApiTicketAPI(), wechatTaskScheduler());
		Boolean refreshOnStartup = env.getProperty("jwechat.refresh.js.api.ticket.on.startup", Boolean.class, Boolean.TRUE);
		if(refreshOnStartup) {
			jsApiTicketManager.refresh();
		}
		return jsApiTicketManager;
	}
	
	@Bean
	public JsApiSigner jsApiSigner() {
		return componentsFactory.jsApiSigner(wechatAuthentication(), jsApiTicketManager());
	}

	@Bean
	public TemplateMessageAPI templateMessageAPI() {
		return componentsFactory.templateMessageAPI(accessTokenManager(), wechatRestOperations());
	}
	
	@Bean
	public WebAccessTokenAPI webAccessTokenAPI() {
		return componentsFactory.webAccessTokenAPI(wechatAuthentication(), wechatRestOperations());
	}

	public void setComponentsFactory(JWechatComponentsFactory componentsFactory) {
		this.componentsFactory = componentsFactory;
	}
}
