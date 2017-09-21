package com.rratchet.spring.wechat.open.menu.builder;

import com.rratchet.spring.wechat.open.menu.button.ButtonTypeEnum;

public class ClickButtonBuilder extends ButtonBuilder {
	
	protected ClickButtonBuilder(MenuBuilder menuBuilder) {
		super(menuBuilder);
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
