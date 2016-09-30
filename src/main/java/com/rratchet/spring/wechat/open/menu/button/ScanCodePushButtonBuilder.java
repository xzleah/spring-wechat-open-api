package com.rratchet.spring.wechat.open.menu.button;

import com.rratchet.spring.wechat.open.menu.MenuCreationAPIRequest;

public class ScanCodePushButtonBuilder extends ButtonBuilder {

	public ScanCodePushButtonBuilder(MenuCreationAPIRequest request) {
		super(request);
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
