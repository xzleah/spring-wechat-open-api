package com.rratchet.spring.wechat.open.menu.builder;

import com.rratchet.spring.wechat.open.menu.button.ButtonTypeEnum;

public class ScanCodePushGroupMemberBuilder extends ButtonGroupMemberBuilder {

	protected ScanCodePushGroupMemberBuilder(ButtonGroupBuilder buttonGroupBuilder) {
		super(buttonGroupBuilder);
	}

	@Override
	protected ButtonTypeEnum type() {
		return ButtonTypeEnum.scancode_push;
	}
	
	public ScanCodePushGroupMemberBuilder key(String key) {
		button.setKey(key);
		return this;
	}
}
