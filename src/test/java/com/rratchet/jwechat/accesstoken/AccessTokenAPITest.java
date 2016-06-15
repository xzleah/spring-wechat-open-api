package com.rratchet.jwechat.accesstoken;

import static com.rratchet.jwechat.test.WechatMockRestResponseBodyCreators.json;
import static com.rratchet.jwechat.test.WechatTestUtils.accessToken;
import static com.rratchet.jwechat.test.WechatTestUtils.appId;
import static com.rratchet.jwechat.test.WechatTestUtils.appSecret;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.rratchet.jwechat.Authentication;
import com.rratchet.jwechat.ErrorResponseException;
import com.rratchet.jwechat.Token;

public class AccessTokenAPITest {

	private AccessTokenAPI accessTokenAPI;
	
	private int checkedExpired = 1000;
	
	private RestOperations restOperations;
	
	private Authentication authentication;
	
	@Before
	public void setUp() throws Exception {
		authentication = mock(Authentication.class);

		accessTokenAPI = new AccessTokenAPI();
		accessTokenAPI.setRestOperations(restOperations);
		accessTokenAPI.setAuthentication(authentication);
	}

	@Test
	public void test_acquireToken_OK() {
		when(authentication.getAppID()).thenReturn(appId());
		when(authentication.getAppsecret()).thenReturn(appSecret());
		
		RestTemplate restTemplate = new RestTemplate();
		accessTokenAPI.setRestOperations(restTemplate);
		MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
		UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(AccessTokenAPI.ACCESS_TOKEN_API_TEMPLATE)
				.buildAndExpand(appId(), appSecret());

		AccessTokenAPIResponse response = new AccessTokenAPIResponse();
		response.setAccess_token(accessToken());
		response.setExpires_in(checkedExpired);
		
		mockServer.expect(requestTo(uriComponents.toUriString())).andExpect(method(HttpMethod.GET))
				.andRespond(withSuccess(json(response), MediaType.APPLICATION_JSON));
		
		Token token = accessTokenAPI.acquireToken();
		assertThat(token.getToken(), is(accessToken()));
		assertThat(token.getExpire(), is(checkedExpired));
		
		mockServer.verify();
		verifyMock();
	}

	private void verifyMock() {
		verify(authentication, times(1)).getAppID();
		verify(authentication, times(1)).getAppsecret();
		verifyNoMoreInteractions(authentication);
	}

	@Test
	public void test_acquireToken_failWithRestException() {
		when(authentication.getAppID()).thenReturn(appId());
		when(authentication.getAppsecret()).thenReturn(appSecret());
		
		AccessTokenAPIResponse response = new AccessTokenAPIResponse();
		response.setAccess_token(accessToken());
		response.setExpires_in(checkedExpired);
		
		RestTemplate restTemplate = new RestTemplate();
		MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
		mockServer.expect(
				requestTo(
						UriComponentsBuilder
							.fromHttpUrl(AccessTokenAPI.ACCESS_TOKEN_API_TEMPLATE)
							.buildAndExpand(appId(), appSecret()).toUri()
						))
			.andExpect(method(HttpMethod.GET))
			.andRespond(withServerError());
		
		accessTokenAPI.setRestOperations(restTemplate);
		boolean hasRestClientException = false;
		try {
			accessTokenAPI.acquireToken();
		} catch (RestClientException e) {
			hasRestClientException = true;
		}
		assertTrue("Should throw RestClientException.", hasRestClientException);
		mockServer.verify();
		verifyMock();
	}
	
	@Test
	public void test_acquireToken_failWithErrorResponseExceptionCauseByWeChatErrorResponse() {
		when(authentication.getAppID()).thenReturn(appId());
		when(authentication.getAppsecret()).thenReturn(appSecret());
		
		AccessTokenAPIResponse response = new AccessTokenAPIResponse();
		int anyErrorCode = -1;
		response.setErrcode(anyErrorCode);
		String checkedErrorMessage = "系统繁忙，此时请开发者稍候再试";
		response.setErrmsg(checkedErrorMessage);
		
		RestTemplate restTemplate = new RestTemplate();
		MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
		mockServer.expect(requestTo(
				UriComponentsBuilder.fromHttpUrl(AccessTokenAPI.ACCESS_TOKEN_API_TEMPLATE)
					.buildAndExpand(appId(), appSecret()).toUri()
				))
			.andExpect(method(HttpMethod.GET))
			.andRespond(withSuccess(json(response), MediaType.APPLICATION_JSON));
		
		accessTokenAPI.setRestOperations(restTemplate);
		boolean hasErrorResponseException = false;
		int errorCode = 0;
		String errorMessage = null;
		String exceptionMessage = null;
		try {
			accessTokenAPI.acquireToken();
		} catch (ErrorResponseException e) {
			hasErrorResponseException = true;
			errorCode = e.getErrorCode();
			errorMessage = e.getErrorMessage();
			exceptionMessage = e.getMessage();
		}
		assertTrue("Should throw ErrorResponseException.", hasErrorResponseException);
		assertThat(errorCode, is(anyErrorCode));
		assertThat(errorMessage, containsString(checkedErrorMessage));
		assertThat(exceptionMessage, containsString(checkedErrorMessage));
		mockServer.verify();
		verifyMock();
	}
}
