package com.rratchet.spring.wechat.open.menu.builder;

import java.util.ArrayList;
import java.util.List;

import com.rratchet.spring.wechat.open.menu.button.Button;
import com.rratchet.spring.wechat.open.menu.button.ButtonTypeEnum;

public class ButtonGroupBuilder extends ButtonBuilder {

	private List<Button> buttonMemberList;
	
	protected ButtonGroupBuilder(MenuBuilder menuBuilder) {
		super(menuBuilder);
		this.buttonMemberList = new ArrayList<>();
		button.setButtonList(buttonMemberList);
	}

	@Override
	protected ButtonTypeEnum type() {
		return ButtonTypeEnum.group;
	}

	public void add(Button button) {
		buttonMemberList.add(button);
	}

	public ClickButtonGroupMemberBuilder clickButton(String name) {
		ClickButtonGroupMemberBuilder builder = new ClickButtonGroupMemberBuilder(this);
		builder.name(name);
		return builder;
	}


	public ViewButtonGroupMemberBuilder viewButton(String name) {
		ViewButtonGroupMemberBuilder viewBUttonGroupMemberBuilder = new ViewButtonGroupMemberBuilder(this);
		viewBUttonGroupMemberBuilder.name(name);
		return viewBUttonGroupMemberBuilder;
	}
	
	public ScanCodePushGroupMemberBuilder scanCodePush(String name) {
		ScanCodePushGroupMemberBuilder builder = new ScanCodePushGroupMemberBuilder(this);
		builder.name(name);
		return builder;
	}

}
