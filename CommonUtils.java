package com.faire.lab.util;

import java.util.Calendar;

public class CommonUtils {
	private static final Long WORK_START_TIME = 10L;
	private static final Long WORK_END_TIME = 19L;
//Calculating working and non working hour and make reportType   Calendar cal = Calendar.getInstance();
	//cal.get(Calendar.HOUR);
	public static Boolean haveToAdd(Calendar cal, Long reportType) {
		return (reportType == 1 && cal.get(Calendar.HOUR) >= WORK_START_TIME && cal.get(Calendar.HOUR) <= WORK_END_TIME)
				|| (reportType == 2 && !(cal.get(Calendar.HOUR) >= WORK_START_TIME && cal.get(Calendar.HOUR) <= WORK_END_TIME));
	}
}
