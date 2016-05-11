package com.rratchet.jwechat.accesstoken;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;

import com.rratchet.jwechat.Authentication;
import com.rratchet.jwechat.ErrorResponseException;
import com.rratchet.jwechat.Token;

public class AccessTokenAPITest {

	private AccessTokenAPI accessTokenAPI;
	
	private String checkedToken = "checkedToken";
	
	private int checkedExpired = 1000;
	
	private RestOperations restOperations;
	
	private Authentication authentication;
	
	private String checkedAppID = "appId";
	
	private String checkedAppSecret = "appSecret";

	@Before
	public void setUp() throws Exception {
		restOperations = mock(RestOperations.class);
		authentication = mock(Authentication.class);

		accessTokenAPI = new AccessTokenAPI();
		accessTokenAPI.setRestOperations(restOperations);
		accessTokenAPI.setAuthentication(authentication);
	}

	@Test
	public void test_acquireToken_OK() {
		when(authentication.getAppID()).thenReturn(checkedAppID);
		when(authentication.getAppsecret()).thenReturn(checkedAppSecret);
		
		AccessTokenAPIResponse response = new AccessTokenAPIResponse();
		response.setAccess_token(checkedToken);
		response.setExpires_in(checkedExpired);
		when(restOperations.getForObject(anyString(), eq(AccessTokenAPIResponse.class),
				eq(checkedAppID), eq(checkedAppSecret))).thenReturn(response);
		
		Token token = accessTokenAPI.acquireToken();
		assertThat(token.getToken(), is(checkedToken));
		assertThat(token.getExpire(), is(checkedExpired));
		
		verifyMock();
	}

	private void verifyMock() {
		verify(authentication, times(1)).getAppID();
		verify(authentication, times(1)).getAppsecret();
		verify(restOperations, times(1)).getForObject(anyString(), eq(AccessTokenAPIResponse.class),
				eq(checkedAppID), eq(checkedAppSecret));
		verifyNoMoreInteractions(authentication, restOperations);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test_acquireToken_failWithRestException() {
		when(authentication.getAppID()).thenReturn(checkedAppID);
		when(authentication.getAppsecret()).thenReturn(checkedAppSecret);
		
		AccessTokenAPIResponse response = new AccessTokenAPIResponse();
		response.setAccess_token(checkedToken);
		response.setExpires_in(checkedExpired);
		when(restOperations.getForObject(anyString(), eq(AccessTokenAPIResponse.class),
				anyString(), anyString())).thenThrow(RestClientException.class);
		
		boolean hasRestClientException = false;
		try {
			accessTokenAPI.acquireToken();
		} catch (RestClientException e) {
			hasRestClientException = true;
		}
		assertTrue("Should throw RestClientException.", hasRestClientException);
		verifyMock();
	}
	
	@Test
	public void test_acquireToken_failWithErrorResponseExceptionCauseByWeChatErrorResponse() {
		when(authentication.getAppID()).thenReturn(checkedAppID);
		when(authentication.getAppsecret()).thenReturn(checkedAppSecret);
		
		AccessTokenAPIResponse response = new AccessTokenAPIResponse();
		int anyErrorCode = -1;
		response.setErrcode(anyErrorCode);
		String checkedErrorMessage = "系统繁忙，此时请开发者稍候再试";
		response.setErrmsg(checkedErrorMessage);
		when(restOperations.getForObject(anyString(), eq(AccessTokenAPIResponse.class),
				anyString(), anyString())).thenReturn(response);
		
		boolean hasErrorResponseException = false;
		int errorCode = 0;
		String errorMessage = null;
		try {
			accessTokenAPI.acquireToken();
		} catch (ErrorResponseException e) {
			hasErrorResponseException = true;
			errorCode = e.getErrorCode();
			errorMessage = e.getErrorMessage();
		}
		assertTrue("Should throw ErrorResponseException.", hasErrorResponseException);
		assertThat(errorCode, is(anyErrorCode));
		assertThat(errorMessage, containsString(checkedErrorMessage));
		verifyMock();
	}
}
