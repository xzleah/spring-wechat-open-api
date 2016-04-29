package com.rratchet.jwechat.tmgmsg;

import java.util.Map;

public class TemplateMessageAPIRequest {

	private String touser;

	private String template_id;

	private String url;

	private Map<String, Model> modelMap;

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

	public Map<String, Model> getModelMap() {
		return modelMap;
	}

	public void setModelMap(Map<String, Model> modelMap) {
		this.modelMap = modelMap;
	}

	@Override
	public String toString() {
		return "TemplateMessageRequest [touser=" + touser + ", template_id=" + template_id + ", url=" + url
				+ ", modelMap=" + modelMap + "]";
	}

}
