package com.rratchet.spring.wechat.open.jsticket;

import com.rratchet.spring.wechat.open.SchedulableTokenManager;

public class JsApiTicketManager extends SchedulableTokenManager {

	public JsApiTicketManager(JsApiTicketAPI api) {
		setTokenAPI(api);
	}
}
