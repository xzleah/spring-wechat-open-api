package com.rratchet.spring.wechat.open.menu;

import com.rratchet.spring.wechat.open.CommonResponse;

public class MenuQueryAPIResponse extends CommonResponse {

	private static final long serialVersionUID = 7289230508450985773L;
	
	private Menu menu;

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}
}
