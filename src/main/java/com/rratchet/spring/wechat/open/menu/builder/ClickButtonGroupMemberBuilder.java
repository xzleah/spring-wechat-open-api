package com.rratchet.spring.wechat.open.menu.builder;

import com.rratchet.spring.wechat.open.menu.button.ButtonTypeEnum;

public class ClickButtonGroupMemberBuilder extends ButtonGroupMemberBuilder {
	
	protected ClickButtonGroupMemberBuilder(ButtonGroupBuilder buttonGroupBuilder) {
		super(buttonGroupBuilder);
	}

	@Override
	protected ButtonTypeEnum type() {
		return ButtonTypeEnum.click;
	}
	
	public ClickButtonGroupMemberBuilder key(String checkedKey) {
		button.setKey(checkedKey);
		return this;
	}
	
}
