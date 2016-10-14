package com.rratchet.spring.wechat.open.client;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import com.rratchet.spring.wechat.open.APIResponseAssert;
import com.rratchet.spring.wechat.open.auth.Authentication;
import com.rratchet.spring.wechat.open.config.ConfigProperties;
import com.rratchet.spring.wechat.open.config.WechatComponentsFactory;
import com.rratchet.spring.wechat.open.menu.MenuAPI;
import com.rratchet.spring.wechat.open.sign.WechatRequestSignatureValidator;
import com.rratchet.spring.wechat.open.tmpmsg.TemplateMessageAPI;
import com.rratchet.spring.wechat.open.token.accesstoken.AccessTokenManager;
import com.rratchet.spring.wechat.open.token.jsticket.JsApiSigner;
import com.rratchet.spring.wechat.open.token.jsticket.JsApiTicketManager;
import com.rratchet.spring.wechat.open.webaccesstoken.WebAccessTokenAPI;

public class WechatClientTest {

	private WechatClient wechatClient;
	private ConfigProperties configProperties;
	
	@Before
	public void setUp() {
		wechatClient = new WechatClient();
		configProperties = mock(ConfigProperties.class);
		wechatClient.setConfigProperties(configProperties);
		wechatClient.setAccessTokenManager(new AccessTokenManager(null));
		wechatClient.setRestOperations(new RestTemplate());
		wechatClient.setApiResponseAssert(new APIResponseAssert());
	}
	
	@Test
	public void testRequestSignatureValidator() {
		when(configProperties.getValidationToken()).thenReturn("token");
		
		WechatRequestSignatureValidator validator = wechatClient.requestSignatureValidator();
		
		assertNotNull(ReflectionTestUtils.getField(validator, "token"));
		verify(configProperties, times(1)).getValidationToken();
		verifyNoMoreInteractions(configProperties);
	}
	
	@Test
	public void testWechatAuthentication() {
		when(configProperties.getAppId()).thenReturn("appId");
		when(configProperties.getAppSecret()).thenReturn("appSecret");
		
		Authentication authentication = wechatClient.wechatAuthentication();
		
		assertNotNull(authentication);
		assertThat(authentication.getAppID(), is("appId"));
		assertThat(authentication.getAppsecret(), is("appSecret"));
		
		verify(configProperties, times(1)).getAppId();
		verify(configProperties, times(1)).getAppSecret();
		verifyNoMoreInteractions(configProperties);
	}
	
	@Test
	public void testWechatOperations() {
		RestOperations restOperations1 = wechatClient.wechatRestOperations();
		RestOperations restOperations2 = wechatClient.wechatRestOperations();
		assertSame(restOperations1, restOperations2);
	}
	
	@Test
	public void testTemplateMessageAPI() {
		TemplateMessageAPI templateMessageAPI = wechatClient.templateMessageAPI();
		
		assertNotNull(ReflectionTestUtils.getField(templateMessageAPI, "templateMessageSendAPI"));
	}
	
	@Test
	public void testMenuAPI() {
		MenuAPI menuAPI = wechatClient.menuAPI();
		
		assertNotNull(menuAPI);
		assertNotNull(ReflectionTestUtils.getField(menuAPI, "menuQueryAPI"));
		assertNotNull(ReflectionTestUtils.getField(menuAPI, "menuCreationAPI"));
	}
	
	@Test
	public void webAccessTokenAPI() {
		when(configProperties.getAppId()).thenReturn("appId");
		when(configProperties.getAppSecret()).thenReturn("appSecret");
		
		WebAccessTokenAPI webAccessTokenAPI = wechatClient.webAccessTokenAPI();
		
		assertNotNull(webAccessTokenAPI);
		assertNotNull(ReflectionTestUtils.getField(webAccessTokenAPI, "restOperations"));
		assertNotNull(ReflectionTestUtils.getField(webAccessTokenAPI, "apiResponseAssert"));
		assertNotNull(ReflectionTestUtils.getField(webAccessTokenAPI, "authentication"));
		verify(configProperties, times(1)).getAppId();
		verify(configProperties, times(1)).getAppSecret();
		verifyNoMoreInteractions(configProperties);
	}
	
	@Test
	public void testJsApiSigner() {
		when(configProperties.getAppId()).thenReturn("appId");
		when(configProperties.getAppSecret()).thenReturn("appSecret");
		
		wechatClient.setJsApiTicketManager(new JsApiTicketManager(null));
		JsApiSigner jsApiSigner = wechatClient.jsApiSigner();
		
		assertNotNull(jsApiSigner);
		assertNotNull(ReflectionTestUtils.getField(jsApiSigner, "jsApiTicketManager"));
		assertNotNull(ReflectionTestUtils.getField(jsApiSigner, "authentication"));
		verify(configProperties, times(1)).getAppId();
		verify(configProperties, times(1)).getAppSecret();
		verifyNoMoreInteractions(configProperties);
	}
}
