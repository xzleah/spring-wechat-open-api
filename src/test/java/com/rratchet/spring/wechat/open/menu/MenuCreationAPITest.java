package com.rratchet.spring.wechat.open.menu;

import static com.rratchet.spring.wechat.open.menu.button.MenuCreationAPIRequestBuilder.clickButton;
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
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import com.rratchet.spring.wechat.open.APIResponseAssert;
import com.rratchet.spring.wechat.open.CommonResponse;
import com.rratchet.spring.wechat.open.accesstoken.AccessTokenManager;
import com.rratchet.spring.wechat.open.menu.MenuCreationAPI;
import com.rratchet.spring.wechat.open.menu.MenuCreationAPIRequest;
import com.rratchet.spring.wechat.open.menu.MenuCreationAPIResponse;

public class MenuCreationAPITest {

	private final static String MENU_CREATEION_URL_TEMPLATE = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token={ACCESS_TOKEN}";
	
	private RestTemplate restTemplate;
	private MenuCreationAPI menuCreationAPI;

	private APIResponseAssert apiResponseAssert;
	private AccessTokenManager accessTokenManager;
	

	@Before
	public void setUp() {
		menuCreationAPI = new MenuCreationAPI();
		
		restTemplate = new RestTemplate();
		menuCreationAPI.setRestOperations(restTemplate);
		apiResponseAssert = mock(APIResponseAssert.class);
		menuCreationAPI.setApiResponseAssert(apiResponseAssert);
		accessTokenManager = mock(AccessTokenManager.class);
		menuCreationAPI.setAccessTokenManager(accessTokenManager);
	}

	@Test
	public void test_create_OK() {
		doNothing().when(apiResponseAssert).assertOK(any(CommonResponse.class));
		when(accessTokenManager.token()).thenReturn(accessToken());
		
		MenuCreationAPIResponse response = new MenuCreationAPIResponse();
		response.setErrcode(0);

		String checkedKey = "checkedKey";
		String checkedButtonName = "button1";
		MenuCreationAPIRequest request = clickButton(checkedButtonName).key(checkedKey).build();
		
		MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
		mockServer.expect(requestTo(fromHttpUrl(MENU_CREATEION_URL_TEMPLATE).buildAndExpand(accessToken()).toUri()))
				.andExpect(method(HttpMethod.POST))
				.andExpect(content().string(json(request)))
				.andRespond(withSuccess(json(response), MediaType.APPLICATION_JSON));
		
		MenuCreationAPIResponse actualResponse = menuCreationAPI.create(request);
		assertThat(actualResponse.getErrcode(), is(response.getErrcode()));
		
		mockServer.verify();
		verify(accessTokenManager).token();
		verify(apiResponseAssert).assertOK(any(CommonResponse.class));
		verifyNoMoreInteractions(accessTokenManager, apiResponseAssert);
	}
}
