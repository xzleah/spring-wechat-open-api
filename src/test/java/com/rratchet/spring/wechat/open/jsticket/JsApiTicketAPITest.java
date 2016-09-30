package com.rratchet.spring.wechat.open.jsticket;

import static com.rratchet.spring.wechat.open.test.WechatRequestResponseBodyCreators.json;
import static com.rratchet.spring.wechat.open.test.WechatTestUtils.accessToken;
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

import com.rratchet.spring.wechat.open.APIResponseAssert;
import com.rratchet.spring.wechat.open.CommonResponse;
import com.rratchet.spring.wechat.open.Token;
import com.rratchet.spring.wechat.open.accesstoken.AccessTokenManager;
import com.rratchet.spring.wechat.open.jsticket.JsApiTicketAPI;
import com.rratchet.spring.wechat.open.jsticket.JsApiTicketAPIResponse;

public class JsApiTicketAPITest {

	private AccessTokenManager accessTokenManager;
	
	private RestTemplate restTemplate;

	private JsApiTicketAPI api;
	
	private String checkedTicket = "checkedTicket";

	private APIResponseAssert apiResponseAssert;

	@Before
	public void setUp() throws Exception {
		api = new JsApiTicketAPI();
		accessTokenManager = mock(AccessTokenManager.class);
		api.setAccessTokenManager(accessTokenManager);
		restTemplate = new RestTemplate();
		api.setRestOperations(restTemplate);
		apiResponseAssert = mock(APIResponseAssert.class);
		api.setApiResponseAssert(apiResponseAssert);
	}

	@Test
	public void test_require_OK() {
		when(accessTokenManager.token()).thenReturn(accessToken());
		JsApiTicketAPIResponse response = new JsApiTicketAPIResponse();
		response.setTicket(checkedTicket);
		response.setExpires_in(100);
		MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
		mockServer.expect(requestTo(UriComponentsBuilder.fromHttpUrl(JsApiTicketAPI.JS_API_TICKET_API_TEMPLATE)
				.buildAndExpand(accessToken()).toUri()))
			.andExpect(method(HttpMethod.GET))
			.andRespond(withSuccess(json(response), MediaType.APPLICATION_JSON));
		doNothing().when(apiResponseAssert).assertOK(any(CommonResponse.class));
		
		Token token = api.acquireToken();
		assertThat(checkedTicket, is(token.getToken()));
		verifyMock();
	}

	private void verifyMock() {
		verify(accessTokenManager).token();
		verify(apiResponseAssert).assertOK(any(CommonResponse.class));
		verifyNoMoreInteractions(accessTokenManager, apiResponseAssert);
	}
}
