package com.rratchet.spring.wechat.open.token.jsticket;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;

import com.rratchet.spring.wechat.open.auth.Authentication;

public class JsApiSigner {

	private static final String JS_API_TICKET_KEY_NAME = "jsapi_ticket";
	private static final String NONCE_STRING_KEY_NAME = "noncestr";
	private static final String TIMESTAMP_KEY_NAME = "timestamp";
	private static final String URL_KEY_NAME = "url";

	private JsApiTicketManager jsApiTicketManager;
	
	private Authentication authentication;
	
	public JsApiSignature sign(String url) {
		JsApiSignature jsApiSignature = new JsApiSignature();
		jsApiSignature.setAppId(authentication.getAppID());
		long timestamp = System.currentTimeMillis();
		jsApiSignature.setTimestamp(timestamp);
		String noncestr = RandomStringUtils.randomAlphanumeric(16);
		jsApiSignature.setNoncestr(noncestr);
		jsApiSignature.setSignature(sign(noncestr, jsApiTicketManager.token(), timestamp, url));
		return jsApiSignature;
	}
	
	private String sign(String noncestr, String jsapiTicket, long timestamp, String url) {
		StringBuilder sb = new StringBuilder();
		sb.append(JS_API_TICKET_KEY_NAME).append("=").append(jsapiTicket).append("&");
		sb.append(NONCE_STRING_KEY_NAME).append("=").append(noncestr).append("&");
		sb.append(TIMESTAMP_KEY_NAME).append("=").append(timestamp).append("&");
		sb.append(URL_KEY_NAME).append("=").append(url);
		
		return DigestUtils.sha1Hex(sb.toString()).toLowerCase();
	}

	public void setJsApiTicketManager(JsApiTicketManager jsApiTicketManager) {
		this.jsApiTicketManager = jsApiTicketManager;
	}

	public void setAuthentication(Authentication authentication) {
		this.authentication = authentication;
	}

}
