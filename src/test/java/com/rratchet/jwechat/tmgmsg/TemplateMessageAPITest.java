package com.rratchet.jwechat.tmgmsg;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestOperations;

import com.rratchet.jwechat.accesstoken.AccessTokenManager;

public class TemplateMessageAPITest {

	private TemplateMessageAPI api;
	private AccessTokenManager accessTokenManager;
	private RestOperations restOperations;

	@Before
	public void setUp() throws Exception {
		api = new TemplateMessageAPI();
		accessTokenManager = mock(AccessTokenManager.class);
		api.setAccessTokenManager(accessTokenManager);
		restOperations = mock(RestOperations.class);
		api.setRestOperations(restOperations);
	}

	@Test
	public void test() {
		String checkedToken = "checkedToken";
		when(accessTokenManager.token()).thenReturn(checkedToken );
		TemplateMessageAPIResponse response = new TemplateMessageAPIResponse();
		Integer checkedMsgid = 200228332;
		response.setMsgid(checkedMsgid);
		when(restOperations.postForObject(anyString(), any(TemplateMessageAPIRequest.class), eq(TemplateMessageAPIResponse.class), anyString())).thenReturn(response );
		
		TemplateMessageAPIRequest actualRequest = new TemplateMessageAPIRequest();
		TemplateMessageAPIResponse actualResponse = api.send(actualRequest);
		assertThat(actualResponse, is(response));
		verify(accessTokenManager).token();
		verify(restOperations).postForObject(eq(TemplateMessageAPI.TEMPLATE_MESSAGE_API_URL), eq(actualRequest), eq(TemplateMessageAPIResponse.class), eq(checkedToken));
		verifyNoMoreInteractions(accessTokenManager, restOperations);
	}

}
