package com.rratchet.jwechat.menu.button;

import com.rratchet.jwechat.menu.MenuCreationAPIRequest;

public class MenuCreationAPIRequestBuilder {

	public static ClickButtonBuilder clickButton(String buttonName) {
		ClickButtonBuilder builder = new ClickButtonBuilder(new MenuCreationAPIRequest());
		builder.name(buttonName);
		return builder;
	}

	public static ViewButtonBuilder viewButton(String buttonName) {
		ViewButtonBuilder builder = new ViewButtonBuilder(new MenuCreationAPIRequest());
		builder.name(buttonName);
		return builder;
	}

	public static ButtonGroupBuilder buttonGroup(ButtonBuilder buttonBuilder) {
		ButtonGroupBuilder buttonGroupBuilder = new ButtonGroupBuilder(new MenuCreationAPIRequest());
		return buttonGroupBuilder.group(buttonBuilder);
	}

}
