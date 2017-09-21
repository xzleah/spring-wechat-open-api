package com.rratchet.spring.wechat.open.menu.builder;

import com.rratchet.spring.wechat.open.menu.button.Button;
import com.rratchet.spring.wechat.open.menu.button.ButtonTypeEnum;

public abstract class ButtonBuilder extends MenuCreationAPIRequestComponentBuilder {

	protected Button button;
	
	protected ButtonBuilder(MenuBuilder menuBuilder) {
		super(menuBuilder);
		button = new Button();
		if(ButtonTypeEnum.group.equals(type())) {
			button.setType(null);
		} else {
			button.setType(type().name());
		}
		menuBuilder.add(button);
	}

	abstract protected ButtonTypeEnum type();
	
	protected void name(String buttonName) {
		button.setName(buttonName);
	}
	
}
