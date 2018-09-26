package com.cm.security;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class Tools {

	static Pattern pattern1 = Pattern.compile("-?[0-9]+.?[0-9]*");

	public static boolean isNumeric(String str) {
		Matcher isNum = pattern1.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	static Pattern pattern2 = Pattern.compile("-?[0-9]+");

	public static boolean isInteger(String str) {
		if (str == null || str.isEmpty())
			return false;
		Matcher isNum = pattern2.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	
	public static String ToTimeStr(Calendar cal) {
		String format = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(cal.getTime());
	}
	
	public static String ToDateStr(Calendar cal) {
		String format = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(cal.getTime());
	}
	
	public static String ToDateMonth(Calendar cal) {
		String format = "yyyy_MM";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(cal.getTime());
	}
	
	/**
	 * 时间戳转换成日期格式字符串
	 * 
	 * @param seconds
	 *            精确到秒的字符串
	 * @param formatStr
	 * @return
	 */
	public static String timeStamp2Date(String seconds, String format) {
		if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
			return "";
		}
		if (format == null || format.isEmpty())
			format = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date(Long.valueOf(seconds + "000")));
	}

	/**
	 * 日期格式字符串转换成时间戳
	 * 
	 * @param date
	 *            字符串日期
	 * @param format
	 *            如：yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static long date2TimeStamp(String date_str, String format) {
		try {
			if (format == null || format.isEmpty())
				format = "yyyy-MM-dd HH:mm:ss";
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.parse(date_str).getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 取得当前时间戳（精确到秒）
	 * 
	 * @return
	 */
	public static String timeStamp() {
		long time = System.currentTimeMillis();
		String t = String.valueOf(time / 1000);
		return t;
	}
	
	public static String minuteFormat(int num){
		int hour = num/60;
		int minute = num-hour*60;
		if(minute < 10){
			if(hour == 0){
				String time = hour+"0:0"+minute+":00";
				return time;
			}
			String time = hour+":0"+minute+":00";
			return time;
		} else {
			if(hour == 0){
				String time = hour+"0:"+minute+":00";
				return time;
			}
			String time = hour+":"+minute+":00";
			return time;
		}
		
	}
}
