package com.rratchet.spring.wechat.open.token.jsticket;

import com.rratchet.spring.wechat.open.token.SchedulableTokenManager;

public class JsApiTicketManager extends SchedulableTokenManager {

	public JsApiTicketManager(JsApiTicketAPI api) {
		setTokenAPI(api);
	}
}
