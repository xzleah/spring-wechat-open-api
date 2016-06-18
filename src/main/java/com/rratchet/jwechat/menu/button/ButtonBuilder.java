package com.rratchet.jwechat.menu.button;

import com.rratchet.jwechat.menu.MenuCreationAPIRequest;

public abstract class ButtonBuilder extends MenuCreationAPIRequestComponentBuilder {

	protected Button button;
	
	protected ButtonBuilder(MenuCreationAPIRequest request) {
		super(request);
		button = new Button();
		if(ButtonTypeEnum.group.equals(type())) {
			button.setType(null);
		} else {
			button.setType(type().name());
		}
		getButtonList().add(button);
	}

	abstract protected ButtonTypeEnum type();
	
	protected void name(String buttonName) {
		button.setName(buttonName);
	}
	
}
