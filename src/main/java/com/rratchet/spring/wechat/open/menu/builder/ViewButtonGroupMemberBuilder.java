package com.rratchet.spring.wechat.open.menu.builder;

import com.rratchet.spring.wechat.open.menu.button.ButtonTypeEnum;

public class ViewButtonGroupMemberBuilder extends ButtonGroupMemberBuilder {

	protected ViewButtonGroupMemberBuilder(ButtonGroupBuilder buttonGroupBuilder) {
		super(buttonGroupBuilder);
	}

	@Override
	protected ButtonTypeEnum type() {
		return ButtonTypeEnum.view;
	}

	public ViewButtonGroupMemberBuilder url(String url) {
		button.setUrl(url);
		return this;
	}

}
