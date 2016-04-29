package com.rratchet.jwechat;

public class WeChatRequestSignatureValidator {

	private String token;
	
	public Signature sign(String timestamp, String nonce) {
		return new Signature(token, timestamp, nonce);
	}

	public void setToken(String token) {
		this.token = token;
	}
}
