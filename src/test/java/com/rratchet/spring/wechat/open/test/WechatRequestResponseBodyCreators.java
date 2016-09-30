package com.rratchet.spring.wechat.open.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rratchet.spring.wechat.open.CommonResponse;

public abstract class WechatRequestResponseBodyCreators {

	private static final ObjectMapper mapper;
	
	static {
		mapper = new ObjectMapper();
	}
	
	private WechatRequestResponseBodyCreators() {
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
	
	/**
	 * 把request实例序列化成JSON格式
	 * @param request 请求对象
	 * @return 请求对象json字符串
	 */
	public static String json(Object request) {
		try {
			return mapper.writeValueAsString(request);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
}
