package com.rratchet.spring.wechat.open.sign;

public class WechatRequestSignatureValidator {

	private String token;
	
	public Signature sign(String timestamp, String nonce) {
		return new Signature(token, timestamp, nonce);
	}

	public void setToken(String token) {
		this.token = token;
	}
}
