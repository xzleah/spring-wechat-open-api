package com.rratchet.jwechat;

import org.springframework.web.client.RestOperations;

public abstract class WechatAPI {

	protected RestOperations restOperations;
	
	protected APIResponseAssert apiResponseAssert;
	
	public void setRestOperations(RestOperations restOperations) {
		this.restOperations = restOperations;
	}

	public void setApiResponseAssert(APIResponseAssert apiResponseAssert) {
		this.apiResponseAssert = apiResponseAssert;
	}
}
