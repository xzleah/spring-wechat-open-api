package com.rratchet.jwechat.menu.button;

import java.util.List;

import com.rratchet.jwechat.menu.MenuCreationAPIRequest;

public class ButtonGroupBuilder extends ButtonBuilder {

	protected ButtonGroupBuilder(MenuCreationAPIRequest request) {
		super(request);
	}

	@Override
	protected ButtonTypeEnum type() {
		return ButtonTypeEnum.group;
	}

	public ButtonGroupBuilder withName(String buttonName) {
		name(buttonName);
		return this;
	}

	public ButtonGroupBuilder group(ButtonBuilder buttonBuilder) {
		MenuCreationAPIRequest request = buttonBuilder.build();
		List<Button> buttonList = request.getButtonList();
		this.button.setButtonList(buttonList);
		return this;
	}
}
