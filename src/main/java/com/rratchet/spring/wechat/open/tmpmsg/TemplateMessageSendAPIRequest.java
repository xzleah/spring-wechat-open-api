package com.rratchet.spring.wechat.open.tmpmsg;

import java.util.Map;

public class TemplateMessageSendAPIRequest {

	private String touser;

	private String template_id;

	private String url;

	private Map<String, Model> data;

	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map<String, Model> getData() {
		return data;
	}

	public void setData(Map<String, Model> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "TemplateMessageSendAPIRequest [touser=" + touser + ", template_id=" + template_id + ", url=" + url
				+ ", data=" + data + "]";
	}

}
