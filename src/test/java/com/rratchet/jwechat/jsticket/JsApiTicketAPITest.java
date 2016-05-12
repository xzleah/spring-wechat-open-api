package com.rratchet.jwechat.jsticket;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;

import com.rratchet.jwechat.ErrorResponseException;
import com.rratchet.jwechat.Token;
import com.rratchet.jwechat.accesstoken.AccessTokenManager;

public class JsApiTicketAPITest {

	private AccessTokenManager accessTokenManager;
	
	private RestOperations restOperations;

	private JsApiTicketAPI api;
	
	private String checkedAccessToken = "checkedAccessToken";
	private String checkedTicket = "checkedTicket";

	@Before
	public void setUp() throws Exception {
		api = new JsApiTicketAPI();
		accessTokenManager = mock(AccessTokenManager.class);
		api.setAccessTokenManager(accessTokenManager);
		restOperations = mock(RestOperations.class);
		api.setRestOperations(restOperations);
	}

	@Test
	public void test_require_OK() {
		when(accessTokenManager.token()).thenReturn(checkedAccessToken);
		JsApiTicketAPIResponse response = new JsApiTicketAPIResponse();
		response.setTicket(checkedTicket);
		response.setExpires_in(100);
		when(restOperations.getForObject(anyString(), eq(JsApiTicketAPIResponse.class), anyString())).thenReturn(response);
		
		Token token = api.acquireToken();
		assertThat(checkedTicket, is(token.getToken()));
		verifyMock();
	}

	private void verifyMock() {
		verify(accessTokenManager).token();
		verify(restOperations).getForObject(anyString(), eq(JsApiTicketAPIResponse.class), eq(checkedAccessToken));
		verifyNoMoreInteractions(accessTokenManager, restOperations);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test_requireToken_failWithRestException() {
		when(accessTokenManager.token()).thenReturn(checkedAccessToken);
		
		when(restOperations.getForObject(anyString(), eq(JsApiTicketAPIResponse.class),
				anyString())).thenThrow(RestClientException.class);
		
		boolean hasRestClientException = false;
		try {
			api.acquireToken();
		} catch (RestClientException e) {
			hasRestClientException = true;
		}
		assertTrue("Should throw RestClientException.", hasRestClientException);
		verifyMock();
	}
	
	@Test
	public void test_requireToken_failWithErrorResponseExceptionCauseByWeChatErrorResponse() {
		when(accessTokenManager.token()).thenReturn(checkedAccessToken);
		
		JsApiTicketAPIResponse response = new JsApiTicketAPIResponse();
		int anyErrorCode = -1;
		response.setErrcode(anyErrorCode);
		String checkedErrorMessage = "系统繁忙，此时请开发者稍候再试";
		response.setErrmsg(checkedErrorMessage);
		when(restOperations.getForObject(anyString(), eq(JsApiTicketAPIResponse.class), anyString())).thenReturn(response);
		
		boolean hasErrorResponseException = false;
		int errorCode = 0;
		String errorMessage = null;
		try {
			api.acquireToken();
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
