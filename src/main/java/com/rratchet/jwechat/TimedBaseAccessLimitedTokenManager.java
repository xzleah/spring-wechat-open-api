package com.rratchet.jwechat;

public class TimedBaseAccessLimitedTokenManager implements TokenManager {

	private Token token;
	
	private TokenAPI tokenAPI;

	private Long previousRefreshTime;
	
	private int interval; //in seconds
	
	public TimedBaseAccessLimitedTokenManager() {
		previousRefreshTime = 0L;
		interval = 5;
	}
	
	public boolean refresh() {
		long currentTimeMillis = System.currentTimeMillis();
		if(currentTimeMillis - previousRefreshTime >= interval*1000) {
			synchronized (previousRefreshTime) {
				currentTimeMillis = System.currentTimeMillis();
				if(currentTimeMillis - previousRefreshTime >= interval*1000) {
					token = tokenAPI.acquireToken();
					previousRefreshTime = currentTimeMillis;
					return true;
				}
			}
		}
		return false;
	}

	public String token() {
		return token.getToken();
	}

	public int expire() {
		return token.getExpire();
	}

	public void setTokenAPI(TokenAPI tokenAPI) {
		this.tokenAPI = tokenAPI;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

}
