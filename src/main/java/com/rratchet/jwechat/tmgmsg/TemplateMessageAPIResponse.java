package com.rratchet.jwechat.tmgmsg;

import com.rratchet.jwechat.CommonResponse;

public class TemplateMessageAPIResponse extends CommonResponse {

	private static final long serialVersionUID = -5917285033716688644L;

	private Integer msgid;

	public Integer getMsgid() {
		return msgid;
	}

	public void setMsgid(Integer msgid) {
		this.msgid = msgid;
	}

}
