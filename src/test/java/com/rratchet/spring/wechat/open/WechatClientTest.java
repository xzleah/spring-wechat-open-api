package com.rratchet.spring.wechat.open;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.rratchet.spring.wechat.open.config.WechatComponentsFactory;

public class WechatClientTest {

	private WechatClient wechatClient;
	
	private WechatComponentsFactory wechatComponentsFactory;
	
	@Before
	public void setUp() {
		wechatClient = new WechatClient();
		wechatComponentsFactory = mock(WechatComponentsFactory.class);
		wechatClient.setWechatComponentsFactory(wechatComponentsFactory);
	}
	
	@Test
	public void testRequestSignatureValidator() {
		String checkedToken = "checkedToken";
//		wechatClient.setValidationToken(checkedToken);
		when(wechatComponentsFactory.wechatRequestSignatureValidator(anyString())).thenReturn(new WechatRequestSignatureValidator());
		
		WechatRequestSignatureValidator validator = wechatClient.requestSignatureValidator();
		
		assertNotNull(validator);
		verify(wechatComponentsFactory, times(1)).wechatRequestSignatureValidator(checkedToken);
		verifyNoMoreInteractions(wechatComponentsFactory);
	}
	
	@Test
	public void testWechatAuthentication() {
		String appSecret = "appSecret";
		String appId = "appId";
		when(wechatComponentsFactory.wechatAuthentication(anyString(), anyString())).thenReturn(new AuthenticationImpl(appId, appSecret));
		
		Authentication authentication = wechatClient.wechatAuthentication();
		
		assertNotNull(authentication);
		verify(wechatComponentsFactory, times(1)).wechatAuthentication(appId, appSecret);
		verifyNoMoreInteractions(wechatComponentsFactory);
	}
	
	//TODO 编写类似于WechatComponentsFactory方法的测试方法
}
