package com.rratchet.spring.wechat.open.menu.builder;

import com.rratchet.spring.wechat.open.menu.button.ButtonTypeEnum;

public class ViewButtonBuilder extends ButtonBuilder {

	protected ViewButtonBuilder(MenuBuilder menuBuilder) {
		super(menuBuilder);
	}

	public ViewButtonBuilder url(String url) {
		button.setUrl(url);
		return this;
	}

	@Override
	protected ButtonTypeEnum type() {
		return ButtonTypeEnum.view;
	}
}
