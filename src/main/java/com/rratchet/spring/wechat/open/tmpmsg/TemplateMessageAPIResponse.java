package com.rratchet.spring.wechat.open.tmpmsg;

import com.rratchet.spring.wechat.open.CommonResponse;

public class TemplateMessageAPIResponse extends CommonResponse {

	private static final long serialVersionUID = -5917285033716688644L;

	private Integer msgid;

	public Integer getMsgid() {
		return msgid;
	}

	public void setMsgid(Integer msgid) {
		this.msgid = msgid;
	}

	@Override
	public String toString() {
		return "TemplateMessageAPIResponse [msgid=" + msgid + ", getErrcode()=" + getErrcode() + ", getErrmsg()="
				+ getErrmsg() + "]";
	}

}
