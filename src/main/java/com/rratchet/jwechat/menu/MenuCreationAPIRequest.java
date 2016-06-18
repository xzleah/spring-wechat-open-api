package com.rratchet.jwechat.menu;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rratchet.jwechat.menu.button.Button;

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
