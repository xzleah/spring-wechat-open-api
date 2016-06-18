package com.rratchet.jwechat.menu.button;

import com.rratchet.jwechat.menu.MenuCreationAPIRequest;

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
