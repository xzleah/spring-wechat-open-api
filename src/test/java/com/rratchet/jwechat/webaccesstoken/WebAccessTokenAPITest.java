package com.rratchet.jwechat.webaccesstoken;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestOperations;

import com.rratchet.jwechat.Authentication;

public class WebAccessTokenAPITest {

	private RestOperations restOperations;
	private Authentication authentication;
	private String checkedAppSecret = "checkedAppSecret";
	private String checkedAppId = "checkedAppId";
	private WebAccessTokenAPI webAccessTokenAPI;
	private String checkedUrl = "checkedUrl";
	private String checkedAccessToken = "checkedAccessToken";

	@Before
	public void setUp() throws Exception {
		webAccessTokenAPI = new WebAccessTokenAPI();
		restOperations = mock(RestOperations.class);
		webAccessTokenAPI.setRestOperations(restOperations);
		authentication = mock(Authentication.class);
		webAccessTokenAPI.setAuthentication(authentication);
	}

	@Test
	public void test_exchangeToken_OK() {
		WebAccessTokenAPIResponse response = new WebAccessTokenAPIResponse();
		response.setAccess_token(checkedAccessToken);
		when(restOperations.getForObject(anyString(), eq(WebAccessTokenAPIResponse.class), anyString(), anyString(),
				anyString())).thenReturn(response);
		when(authentication.getAppID()).thenReturn(checkedAppId);
		when(authentication.getAppsecret()).thenReturn(checkedAppSecret);
		
		WebAccessTokenAPIResponse webAccessTokenAPIResponse = webAccessTokenAPI.exchangeToken(checkedUrl);
		assertThat(response.getAccess_token(), is(webAccessTokenAPIResponse.getAccess_token()));
	}
	
}
