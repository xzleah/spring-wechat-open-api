package com.rratchet.spring.wechat.open.token;

import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class AlwaysDoneScheduledFuture<V> implements ScheduledFuture<V> {

	public long getDelay(TimeUnit unit) {
		throw new UnsupportedOperationException();
	}

	public int compareTo(Delayed o) {
		throw new UnsupportedOperationException();
	}

	public boolean cancel(boolean mayInterruptIfRunning) {
		throw new UnsupportedOperationException();
	}

	public boolean isCancelled() {
		throw new UnsupportedOperationException();
	}

	public boolean isDone() {
		return true;
	}

	public V get() throws InterruptedException, ExecutionException {
		throw new UnsupportedOperationException();
	}

	public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
		throw new UnsupportedOperationException();
	}

}
