package com.rratchet.jwechat.webaccesstoken;

import static com.rratchet.jwechat.test.WechatRequestResponseBodyCreators.json;
import static com.rratchet.jwechat.test.WechatTestUtils.accessToken;
import static com.rratchet.jwechat.test.WechatTestUtils.appId;
import static com.rratchet.jwechat.test.WechatTestUtils.appSecret;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
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
import org.springframework.web.util.UriComponentsBuilder;

import com.rratchet.jwechat.APIResponseAssert;
import com.rratchet.jwechat.Authentication;
import com.rratchet.jwechat.CommonResponse;

public class WebAccessTokenAPITest {

	private RestTemplate restTemplate;
	private Authentication authentication;
	private WebAccessTokenAPI webAccessTokenAPI;
	private String checkedWebAuthCode = "checkedWebAuthCode";
	private APIResponseAssert apiResponseAssert;

	@Before
	public void setUp() throws Exception {
		webAccessTokenAPI = new WebAccessTokenAPI();
		restTemplate = new RestTemplate();
		webAccessTokenAPI.setRestOperations(restTemplate);
		authentication = mock(Authentication.class);
		webAccessTokenAPI.setAuthentication(authentication);
		apiResponseAssert = mock(APIResponseAssert.class);
		webAccessTokenAPI.setApiResponseAssert(apiResponseAssert);
	}

	@Test
	public void test_exchangeToken_OK() {
		WebAccessTokenAPIResponse response = new WebAccessTokenAPIResponse();
		response.setAccess_token(accessToken());
		when(authentication.getAppID()).thenReturn(appId());
		when(authentication.getAppsecret()).thenReturn(appSecret());
		doNothing().when(apiResponseAssert).assertOK(any(CommonResponse.class));
		MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
		mockServer.expect(requestTo(UriComponentsBuilder.fromHttpUrl(
					WebAccessTokenAPI.WEB_ACCESS_TOKEN_EXCHANGE_API_URL_TEMPLATE)
					.buildAndExpand(appId(), appSecret(), checkedWebAuthCode).toUri()))
			.andExpect(method(HttpMethod.GET))
			.andRespond(withSuccess(json(response), MediaType.APPLICATION_JSON));
		
		WebAccessTokenAPIResponse webAccessTokenAPIResponse = webAccessTokenAPI.exchangeToken(checkedWebAuthCode);
		assertThat(response.getAccess_token(), is(webAccessTokenAPIResponse.getAccess_token()));
		verify(authentication).getAppID();
		verify(authentication).getAppsecret();
		verify(apiResponseAssert).assertOK(any(CommonResponse.class));
		verifyNoMoreInteractions(authentication, apiResponseAssert);
		mockServer.verify();
	}
	
}
