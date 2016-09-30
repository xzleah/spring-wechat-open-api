package com.rratchet.spring.wechat.open.menu.button;

import com.rratchet.spring.wechat.open.menu.MenuCreationAPIRequest;

public class ClickButtonBuilder extends ButtonBuilder {
	
	protected ClickButtonBuilder(MenuCreationAPIRequest request) {
		super(request);
	}

	public ClickButtonBuilder key(String checkedKey) {
		button.setKey(checkedKey);
		return this;
	}

	@Override
	protected ButtonTypeEnum type() {
		return ButtonTypeEnum.click;
	}

}
