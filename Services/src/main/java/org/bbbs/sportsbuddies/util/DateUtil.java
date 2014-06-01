package org.bbbs.sportsbuddies.util;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.xml.bind.DatatypeConverter;

public final class DateUtil {

	public static String DateToString(Date date) {
		
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		cal.setTime(date);
		return DatatypeConverter.printDateTime(cal);
	}
	
	public static Date StringToDate(String date) {
		
		Calendar cal = DatatypeConverter.parseDate(date);
		return cal.getTime();
	}
}
