package com.rratchet.spring.wechat.open.token;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.rratchet.spring.wechat.open.token.TimedBaseAccessLimitedTokenManager;
import com.rratchet.spring.wechat.open.token.Token;
import com.rratchet.spring.wechat.open.token.TokenAPI;

public class TimedBaseAccessLimitedTokenManagerTest {

	private TimedBaseAccessLimitedTokenManager manager;
	
	private TokenAPI tokenAPIMock;
	
	@Before
	public void setUp() throws Exception {
		tokenAPIMock = mock(TokenAPI.class);
		when(tokenAPIMock.acquireToken()).thenReturn(new Token());
		
		manager = new TimedBaseAccessLimitedTokenManager();
		manager.setTokenAPI(tokenAPIMock);
	}

	@Test
	public void testRefresh_doTwiceInAPeriodAndSecondTimesShouldNotRefresh() {
		assertTrue("Refreshing should return true at first time.", manager.refresh());
		assertFalse("Refreshing should return false at second time.", manager.refresh());
		verify(tokenAPIMock, times(1)).acquireToken();
		verifyNoMoreInteractions(tokenAPIMock);
	}

	@Test
	public void testRefresh_doTwiceInDifferentPeriods() throws InterruptedException {
		manager.setInterval(1);
		assertTrue("Refreshing should return true at first time.", manager.refresh());
		Thread.sleep(1*1000);
		assertTrue("Refreshing should return true at second time.", manager.refresh());
		verify(tokenAPIMock, times(2)).acquireToken();
		verifyNoMoreInteractions(tokenAPIMock);
	}
}
