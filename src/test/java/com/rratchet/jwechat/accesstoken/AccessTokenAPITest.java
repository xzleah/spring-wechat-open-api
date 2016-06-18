package com.rratchet.jwechat.accesstoken;

import static com.rratchet.jwechat.test.WechatRequestResponseBodyCreators.json;
import static com.rratchet.jwechat.test.WechatTestUtils.accessToken;
import static com.rratchet.jwechat.test.WechatTestUtils.appId;
import static com.rratchet.jwechat.test.WechatTestUtils.appSecret;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.rratchet.jwechat.APIResponseAssert;
import com.rratchet.jwechat.Authentication;
import com.rratchet.jwechat.CommonResponse;
import com.rratchet.jwechat.Token;

public class AccessTokenAPITest {

	private AccessTokenAPI accessTokenAPI;
	
	private int checkedExpired = 1000;
	
	private RestTemplate restTemplate;
	
	private Authentication authentication;
	
	private APIResponseAssert apiResponseAssert;
	
	@Before
	public void setUp() throws Exception {
		authentication = mock(Authentication.class);
		apiResponseAssert = mock(APIResponseAssert.class);
		restTemplate = new RestTemplate();
		
		accessTokenAPI = new AccessTokenAPI();
		accessTokenAPI.setRestOperations(restTemplate);
		accessTokenAPI.setAuthentication(authentication);
		accessTokenAPI.setApiResponseAssert(apiResponseAssert);
		accessTokenAPI.setRestOperations(restTemplate);
	}

	@Test
	public void test_acquireToken_OK() {
		when(authentication.getAppID()).thenReturn(appId());
		when(authentication.getAppsecret()).thenReturn(appSecret());
		doNothing().when(apiResponseAssert).assertOK(any(CommonResponse.class));
		
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
		verify(apiResponseAssert, times(1)).assertOK(any(CommonResponse.class));
		verifyNoMoreInteractions(authentication, apiResponseAssert);
	}

}
