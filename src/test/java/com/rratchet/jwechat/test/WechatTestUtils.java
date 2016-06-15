package com.rratchet.jwechat.test;

public class WechatTestUtils {

	private static String FIXED_APP_ID = "appId";
	
	private static String FIXED_APP_SECRET = "appSecret";

	private static String FIXED_ACCESS_TOKEN = "accessToken";;
	
	public static String appId() {
		return FIXED_APP_ID;
	}
	
	public static String appSecret() {
		return FIXED_APP_SECRET;
	}
	
	public static String accessToken() {
		return FIXED_ACCESS_TOKEN;
	}
}
