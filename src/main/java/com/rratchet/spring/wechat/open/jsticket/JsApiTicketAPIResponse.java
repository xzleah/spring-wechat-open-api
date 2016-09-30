package com.rratchet.spring.wechat.open.jsticket;

import com.rratchet.spring.wechat.open.CommonResponse;

public class JsApiTicketAPIResponse extends CommonResponse {

	private static final long serialVersionUID = 76180478945266818L;

	private String ticket;
	
	private Integer expires_in;
	
	public Integer getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(Integer expires_in) {
		this.expires_in = expires_in;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
}
