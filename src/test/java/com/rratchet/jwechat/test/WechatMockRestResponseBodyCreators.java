package com.rratchet.jwechat.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rratchet.jwechat.CommonResponse;

public abstract class WechatMockRestResponseBodyCreators {

	private static final ObjectMapper mapper;
	
	static {
		mapper = new ObjectMapper();
	}
	
	private WechatMockRestResponseBodyCreators() {
	}
	
	/**
	 * 把CommonResponse实例序列化成JSON格式
	 * @param response 模拟的微信响应对象
	 * @return JSON格式的字符串
	 */
	public static String json(CommonResponse response) {
		try {
			return mapper.writeValueAsString(response);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
}
