package com.rratchet.jwechat.menu.button;

import com.rratchet.jwechat.menu.MenuCreationAPIRequest;

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
