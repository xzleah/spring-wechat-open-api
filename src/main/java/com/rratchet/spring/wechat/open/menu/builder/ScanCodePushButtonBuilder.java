package com.rratchet.spring.wechat.open.menu.builder;

import com.rratchet.spring.wechat.open.menu.button.ButtonTypeEnum;

public class ScanCodePushButtonBuilder extends ButtonBuilder {

	public ScanCodePushButtonBuilder(MenuBuilder menuBuilder) {
		super(menuBuilder);
	}
	
	public ScanCodePushButtonBuilder key(String key) {
		button.setKey(key);
		return this;
	}

	@Override
	protected ButtonTypeEnum type() {
		return ButtonTypeEnum.scancode_push;
	}

}
