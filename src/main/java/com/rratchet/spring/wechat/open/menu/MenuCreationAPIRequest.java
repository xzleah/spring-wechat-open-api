package com.rratchet.spring.wechat.open.menu;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rratchet.spring.wechat.open.menu.button.Button;

public class MenuCreationAPIRequest {

	@JsonProperty("button")
	private List<Button> buttonList;

	public List<Button> getButtonList() {
		return buttonList;
	}

	public void setButtonList(List<Button> buttonList) {
		this.buttonList = buttonList;
	}
	
}
