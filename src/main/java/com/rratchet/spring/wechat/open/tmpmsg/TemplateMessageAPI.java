package com.rratchet.spring.wechat.open.tmpmsg;

public class TemplateMessageAPI {

	private TemplateMessageSendAPI templateMessageSendAPI;

	public TemplateMessageSendAPIResponse send(TemplateMessageSendAPIRequest request) {
		return templateMessageSendAPI.send(request);
	}

	public void setTemplateMessageSendAPI(TemplateMessageSendAPI templateMessageSendAPI) {
		this.templateMessageSendAPI = templateMessageSendAPI;
	}
	
}
