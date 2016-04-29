package com.rratchet.jwechat;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.concurrent.ScheduledFuture;

import org.junit.Before;
import org.junit.Test;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.test.util.ReflectionTestUtils;

public class SchedulableTokenManagerTest {

	private SchedulableTokenManager manager;
	
	private TokenAPI tokenAPI;
	
	private TaskScheduler taskScheduler;

	private TimeCalculator timeCalculator;

	@SuppressWarnings("rawtypes")
	private ScheduledFuture scheduledFuture;

	@Before
	public void setUp() throws Exception {
		manager = new SchedulableTokenManager();
		tokenAPI = mock(TokenAPI.class);
		manager.setTokenAPI(tokenAPI);
		taskScheduler = mock(TaskScheduler.class);
		manager.setTaskScheduler(taskScheduler);
		timeCalculator = mock(TimeCalculator.class);
		manager.setTimeCalculator(timeCalculator);
		scheduledFuture = mock(ScheduledFuture.class);
		ReflectionTestUtils.setField(manager, "lastScheduledFuture", scheduledFuture);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test_refresh_schedulNextRefreshActionInCorrectInteval() {
		int checkedExpired = 100;
		Token validToken = new Token();
		Date checkedDate = new Date(System.currentTimeMillis());
		validToken.setExpire(checkedExpired);
		when(tokenAPI.acquireToken()).thenReturn(validToken);
		when(timeCalculator.afterNow(anyInt())).thenReturn(checkedDate);
		when(taskScheduler.schedule(any(SchedulableTokenManager.class), any(Date.class))).thenReturn(scheduledFuture);
		when(scheduledFuture.isDone()).thenReturn(false);
		when(scheduledFuture.cancel(anyBoolean())).thenReturn(true);
		
		boolean refresh = manager.refresh();
		assertTrue(refresh);
		verify(tokenAPI, times(1)).acquireToken();
		verify(timeCalculator, times(1)).afterNow(eq(checkedExpired));
		verify(taskScheduler, times(1)).schedule(eq(manager), eq(checkedDate));
		verify(scheduledFuture).isDone();
		verify(scheduledFuture).cancel(eq(true));
		verifyNoMoreInteractions(tokenAPI, timeCalculator, taskScheduler, scheduledFuture);
	}

}
