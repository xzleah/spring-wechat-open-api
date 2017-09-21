package com.rratchet.spring.wechat.open.menu.builder;

import java.util.ArrayList;
import java.util.List;

import com.rratchet.spring.wechat.open.menu.MenuCreationAPIRequest;
import com.rratchet.spring.wechat.open.menu.button.Button;

public class MenuBuilder {

	private List<Button> buttonList;
	
	public MenuBuilder() {
		buttonList = new ArrayList<>();
	}

	public ClickButtonBuilder clickButton(String name) {
		ClickButtonBuilder clickButtonBuilder = new ClickButtonBuilder(this);
		clickButtonBuilder.name(name);
		return clickButtonBuilder;
	}

	public ViewButtonBuilder viewButton(String name) {
		ViewButtonBuilder viewButtonBuilder = new ViewButtonBuilder(this);
		viewButtonBuilder.name(name);
		return viewButtonBuilder;
	}

	public ButtonGroupBuilder buttonGroup(String name) {
		ButtonGroupBuilder buttonGroupBuilder = new ButtonGroupBuilder(this);
		buttonGroupBuilder.name(name);
		return buttonGroupBuilder;
	}
	
	public ScanCodePushButtonBuilder scanCodePushButton(String name) {
		ScanCodePushButtonBuilder builder = new ScanCodePushButtonBuilder(this);
		builder.name(name);
		return builder;
		
	}

	public MenuCreationAPIRequest build() {
		MenuCreationAPIRequest menuCreationAPIRequest = new MenuCreationAPIRequest();
		menuCreationAPIRequest.setButtonList(buttonList);
		return menuCreationAPIRequest;
	}
	
	protected void add(Button button) {
		buttonList.add(button);
	}

}
