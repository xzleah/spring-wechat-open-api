package com.rratchet.spring.wechat.open.token;

import java.util.Calendar;
import java.util.Date;

public class TimeCalculator {

	public Date afterNow(int amount) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.SECOND, amount);
		return calendar.getTime();
	}
}
