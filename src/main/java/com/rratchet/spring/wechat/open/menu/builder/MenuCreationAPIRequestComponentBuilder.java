package com.rratchet.spring.wechat.open.menu.builder;

import com.rratchet.spring.wechat.open.menu.MenuCreationAPIRequest;

public abstract class MenuCreationAPIRequestComponentBuilder {

	private MenuBuilder menuBuilder;
	
	protected MenuCreationAPIRequestComponentBuilder(MenuBuilder menuBuilder) {
		this.menuBuilder = menuBuilder;
	}

	public MenuCreationAPIRequest build() {
		return menuBuilder.build();
	}
	
	public MenuBuilder and() {
		return menuBuilder;
	}
	
}
