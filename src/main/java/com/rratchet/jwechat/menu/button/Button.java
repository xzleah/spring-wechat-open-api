package com.rratchet.jwechat.menu.button;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Button {

	private String type;
	
	private String name;
	
	private String key;
	
	private String url;
	
	@JsonProperty("media_id")
	private String mediaId;
	
	@JsonProperty("sub_button")
	private List<Button> buttonList;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public List<Button> getButtonList() {
		return buttonList;
	}

	public void setButtonList(List<Button> buttonList) {
		this.buttonList = buttonList;
	}

}
