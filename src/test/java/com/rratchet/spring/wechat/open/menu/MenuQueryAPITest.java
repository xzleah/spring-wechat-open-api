package com.rratchet.spring.wechat.open.menu;

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
import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;
import static com.rratchet.spring.wechat.open.test.WechatRequestResponseBodyCreators.json;
import static com.rratchet.spring.wechat.open.test.WechatTestUtils.accessToken;
import static org.hamcrest.core.Is.is;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import com.rratchet.spring.wechat.open.APIResponseAssert;
import com.rratchet.spring.wechat.open.CommonResponse;
import com.rratchet.spring.wechat.open.accesstoken.AccessTokenManager;
import com.rratchet.spring.wechat.open.menu.Menu;
import com.rratchet.spring.wechat.open.menu.MenuQueryAPI;
import com.rratchet.spring.wechat.open.menu.MenuQueryAPIResponse;
import com.rratchet.spring.wechat.open.menu.button.Button;
import com.rratchet.spring.wechat.open.menu.button.ButtonTypeEnum;

public class MenuQueryAPITest {

	private final static String MENU_QUERY_URL_TEMPLATE = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token={ACCESS_TOKEN}";
	
	private MenuQueryAPI menuQueryAPI;

	private AccessTokenManager accessTokenManager;

	private APIResponseAssert apiResponseAssert;

	private RestTemplate restTemplate;
	
	@Before
	public void setUp() {
		accessTokenManager = mock(AccessTokenManager.class);
		apiResponseAssert = mock(APIResponseAssert.class);
		restTemplate = new RestTemplate();
		menuQueryAPI = new MenuQueryAPI();
		menuQueryAPI.setAccessTokenManager(accessTokenManager);
		menuQueryAPI.setApiResponseAssert(apiResponseAssert);
		menuQueryAPI.setRestOperations(restTemplate);
	}
	
	@Test
	public void test_query_responseSimpleMenu_OK() {
		String checkedButtonName = "今日歌曲";
		String checkedButtonKey = "V1001_TODAY_MUSIC";
		
		MenuQueryAPIResponse response = new MenuQueryAPIResponse();
		Menu menu = new Menu();
		Button button = new Button();
		button.setType(ButtonTypeEnum.click.name());
		button.setName(checkedButtonName);
		button.setKey(checkedButtonKey);
		List<Button> buttonList = Collections.emptyList();
		button.setButtonList(buttonList);
		menu.setButtonList(Arrays.asList(button));
		response.setMenu(menu);
		
		when(accessTokenManager.token()).thenReturn(accessToken());
		MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
		mockServer.expect(requestTo(fromHttpUrl(MENU_QUERY_URL_TEMPLATE).buildAndExpand(accessToken()).toUri()))
			.andExpect(method(HttpMethod.GET))
			.andRespond(withSuccess(json(response), MediaType.APPLICATION_JSON));
		doNothing().when(apiResponseAssert).assertOK(any(CommonResponse.class));
		
		MenuQueryAPIResponse actualResponse = menuQueryAPI.query();
		List<Button> actualButtonList = actualResponse.getMenu().getButtonList();
		assertThat(actualButtonList.size(), is(1));
		Button actualButton = actualButtonList.get(0);
		assertThat(actualButton.getType(), is(ButtonTypeEnum.click.name()));
		assertThat(actualButton.getName(), is(checkedButtonName));
		assertThat(actualButton.getKey(), is(checkedButtonKey));
		assertThat(actualButton.getButtonList().size(), is(0));
		
		mockServer.verify();
		verify(accessTokenManager).token();
		verify(apiResponseAssert).assertOK(any(CommonResponse.class));
		verifyNoMoreInteractions(accessTokenManager, apiResponseAssert);
	}
}
