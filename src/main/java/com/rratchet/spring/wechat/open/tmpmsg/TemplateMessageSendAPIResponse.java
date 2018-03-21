package com.rratchet.spring.wechat.open.tmpmsg;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.rratchet.spring.wechat.open.CommonResponse;

public class TemplateMessageSendAPIResponse extends CommonResponse {

	private static final long serialVersionUID = -5917285033716688644L;

	private Long msgid;

	@Deprecated
	public Integer getMsgid() {
		return msgid.intValue();
	}

	@Deprecated
	public void setMsgid(Integer msgid) {
		this.msgid = msgid.longValue();
	}
	
	@JsonSetter("msgid")
	public void setMsgid(Long msgid) {
		this.msgid = msgid;
	}
	
	@JsonGetter("msgid")
	public long getLongMsgid() {
		return msgid;
	}

	@Override
	public String toString() {
		return "TemplateMessageAPIResponse [msgid=" + msgid + ", getErrcode()=" + getErrcode() + ", getErrmsg()="
				+ getErrmsg() + "]";
	}

}
