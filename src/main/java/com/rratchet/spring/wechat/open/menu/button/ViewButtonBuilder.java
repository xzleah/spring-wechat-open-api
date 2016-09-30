package com.rratchet.spring.wechat.open.menu.button;

import com.rratchet.spring.wechat.open.menu.MenuCreationAPIRequest;

public class ViewButtonBuilder extends ButtonBuilder {

	protected ViewButtonBuilder(MenuCreationAPIRequest request) {
		super(request);
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
