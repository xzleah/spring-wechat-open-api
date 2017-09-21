package com.rratchet.spring.wechat.open.menu.builder;

import com.rratchet.spring.wechat.open.menu.button.Button;
import com.rratchet.spring.wechat.open.menu.button.ButtonTypeEnum;

public abstract class ButtonGroupMemberBuilder {

	private ButtonGroupBuilder buttonGroupBuilder;
	
	protected Button button;

	protected ButtonGroupMemberBuilder(ButtonGroupBuilder buttonGroupBuilder) {
		this.buttonGroupBuilder = buttonGroupBuilder;
		button = new Button();
		button.setType(type().name());
		buttonGroupBuilder.add(button);
	}
	
	abstract protected ButtonTypeEnum type();
	
	protected void name(String buttonName) {
		button.setName(buttonName);
	}

	public ButtonGroupBuilder andMember() {
		return buttonGroupBuilder;
	}
	
	public MenuBuilder and() {
		return buttonGroupBuilder.and();
	}
}
