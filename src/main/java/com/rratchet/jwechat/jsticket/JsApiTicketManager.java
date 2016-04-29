package com.rratchet.jwechat.jsticket;

import com.rratchet.jwechat.SchedulableTokenManager;

public class JsApiTicketManager extends SchedulableTokenManager {

	public JsApiTicketManager(JsApiTicketAPI api) {
		setTokenAPI(api);
	}
}
