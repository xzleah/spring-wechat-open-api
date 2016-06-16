package com.rratchet.jwechat.tmgmsg;

import static com.rratchet.jwechat.test.WechatMockRestResponseBodyCreators.json;
import static com.rratchet.jwechat.test.WechatTestUtils.accessToken;
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
import com.rratchet.jwechat.CommonResponse;
import com.rratchet.jwechat.accesstoken.AccessTokenManager;

public class TemplateMessageAPITest {

	private TemplateMessageAPI api;
	private AccessTokenManager accessTokenManager;
	private RestTemplate restTemplate;
	private APIResponseAssert apiResponseAssert;

	@Before
	public void setUp() throws Exception {
		api = new TemplateMessageAPI();
		accessTokenManager = mock(AccessTokenManager.class);
		api.setAccessTokenManager(accessTokenManager);
		restTemplate = new RestTemplate();
		api.setRestOperations(restTemplate);
		apiResponseAssert = mock(APIResponseAssert.class);
		api.setApiResponseAssert(apiResponseAssert);
	}

	@Test
	public void test() {
		when(accessTokenManager.token()).thenReturn(accessToken());
		TemplateMessageAPIResponse response = new TemplateMessageAPIResponse();
		Integer checkedMsgid = 200228332;
		response.setMsgid(checkedMsgid);
		doNothing().when(apiResponseAssert).assertOK(any(CommonResponse.class));
		MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
		mockServer.expect(requestTo(
				UriComponentsBuilder.fromHttpUrl(TemplateMessageAPI.TEMPLATE_MESSAGE_API_URL)
					.buildAndExpand(accessToken()).toUri()
					))
			.andExpect(method(HttpMethod.POST))
			.andRespond(withSuccess(json(response), MediaType.APPLICATION_JSON));
		
		TemplateMessageAPIRequest actualRequest = new TemplateMessageAPIRequest();
		TemplateMessageAPIResponse actualResponse = api.send(actualRequest);
		assertThat(actualResponse.getMsgid(), is(response.getMsgid()));
		verify(accessTokenManager).token();
		verify(apiResponseAssert).assertOK(any(CommonResponse.class));
		verifyNoMoreInteractions(accessTokenManager, apiResponseAssert);
	}

}
