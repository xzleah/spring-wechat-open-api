package com.rratchet.jwechat;

import java.util.Arrays;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

public class Signature {

	private String token;
	private String timestamp;
	private String nonce;

	protected Signature(String token, String timestamp, String nonce) {
		this.token = token;
		this.timestamp = timestamp;
		this.nonce = nonce;
	}

	public boolean validate(String signature) {
		String[] sortArray = {token, timestamp, nonce};
		Arrays.sort(sortArray);
		String code = StringUtils.join(sortArray);
		String sha1HexCode = DigestUtils.sha1Hex(code);
		return sha1HexCode.toString().equalsIgnoreCase(signature);
	}
}
