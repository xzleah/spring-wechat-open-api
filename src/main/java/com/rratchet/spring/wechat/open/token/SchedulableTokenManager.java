package com.rratchet.spring.wechat.open.token;

import java.util.Date;
import java.util.concurrent.ScheduledFuture;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.scheduling.TaskScheduler;

public class SchedulableTokenManager extends TimedBaseAccessLimitedTokenManager implements Runnable {

	private TaskScheduler taskScheduler;
	private TimeCalculator timeCalculator;
	private ScheduledFuture<?> lastScheduledFuture;

	public SchedulableTokenManager() {
		super();
		timeCalculator = new TimeCalculator();
		lastScheduledFuture = new AlwaysDoneScheduledFuture<Object>();
	}

	@Override
	public boolean refresh() {
		boolean isRefresh = super.refresh();
		if(isRefresh) {
			int expire = expire();
			Date newRefreshTime = timeCalculator.afterNow(expire);
			if(!lastScheduledFuture.isDone()) {
				lastScheduledFuture.cancel(true);
			}
			lastScheduledFuture = taskScheduler.schedule(this, newRefreshTime);
			
		}
		return isRefresh;
	}

	public void setTaskScheduler(TaskScheduler taskScheduler) {
		this.taskScheduler = taskScheduler;
	}

	public void setTimeCalculator(TimeCalculator timeCalculator) {
		this.timeCalculator = timeCalculator;
	}

	public void run() {
		refresh();
	}
	
	public void destroy() throws Exception{
		if(taskScheduler instanceof DisposableBean) {
			DisposableBean disposableBean = (DisposableBean)taskScheduler;
			disposableBean.destroy();
		}
	}
}
