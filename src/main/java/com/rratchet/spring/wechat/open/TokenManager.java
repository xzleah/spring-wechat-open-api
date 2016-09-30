package com.rratchet.spring.wechat.open;

public interface TokenManager {

	/**
	 * 发送刷新令牌的通知，是否实际执行刷新操作由实现类决定
	 * @return 执行刷新令牌返回true，没有执行返回false
	 */
	boolean refresh();
	
	/**
	 * 获取令牌
	 * @return 令牌
	 */
	String token();
	
	/**
	 * 获取令牌超时时间，单位秒
	 * @return 超时时间
	 */
	int expire();
}
