package org.bbbs.sportsbuddies.util;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.xml.bind.DatatypeConverter;

public final class DateUtil {

	public static String DateToString(Date date) {
		
		if(date != null) {
			Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
			cal.setTime(date);
			return DatatypeConverter.printDateTime(cal);
		}
		
		return null;
	}
	
	public static Date StringToDate(String date) {
		
		if(date != null && date.trim().length() > 0 ) {
			Calendar cal = DatatypeConverter.parseDate(date);
			return cal.getTime();
		}
		
		return null;
	}
}
