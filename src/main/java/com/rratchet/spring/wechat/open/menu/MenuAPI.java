package com.rratchet.spring.wechat.open.menu;

public class MenuAPI {

	private MenuQueryAPI menuQueryAPI;
	
	private MenuCreationAPI menuCreationAPI;
	
	public MenuQueryAPIResponse query() {
		return menuQueryAPI.query();
	}
	
	public MenuCreationAPIResponse create(MenuCreationAPIRequest request) {
		return menuCreationAPI.create(request);
	}

	public void setMenuQueryAPI(MenuQueryAPI menuQueryAPI) {
		this.menuQueryAPI = menuQueryAPI;
	}

	public void setMenuCreationAPI(MenuCreationAPI menuCreationAPI) {
		this.menuCreationAPI = menuCreationAPI;
	}
}
