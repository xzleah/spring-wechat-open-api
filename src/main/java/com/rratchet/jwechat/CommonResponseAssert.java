package com.rratchet.jwechat;

/**
 * @see APIResponseAssert
 *
 */
@Deprecated
public class CommonResponseAssert {

	public static void assertOK(CommonResponse response) {
		APIResponseAssert.assertOK(response);
	}
}
