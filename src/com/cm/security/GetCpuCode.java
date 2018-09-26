package com.cm.security;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class GetCpuCode {
	public static String getCode() {
		Process process;
		try {
			process = Runtime.getRuntime().exec(new String[] { "/bin/sh","-c","/usr/sbin/dmi -d" });  
	        process.getOutputStream().close();
	        Scanner sc = new Scanner(process.getErrorStream());
	        String serial = sc.nextLine();
	        sc.close();
			return serial;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/** 
	 * 判断时间是否在时间段内 
	 *  
	 */  
	public static boolean isInDate(Date date, String strDateBegin,  
	        String strDateEnd) {  
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");  
	    String strDate = sdf.format(date);  
	    // 截取当前时间年月日  
	    int strDateH = Integer.parseInt(strDate.substring(0, 4));  
	    int strDateM = Integer.parseInt(strDate.substring(4, 6));  
	    int strDateS = Integer.parseInt(strDate.substring(6, 8));  
	    // 截取开始时间年月日  
	    int strDateBeginH = Integer.parseInt(strDateBegin.substring(0, 4));  
	    int strDateBeginM = Integer.parseInt(strDateBegin.substring(4, 6));  
	    int strDateBeginS = Integer.parseInt(strDateBegin.substring(6, 8));  
	    // 截取结束时间年月日  
	    int strDateEndH = Integer.parseInt(strDateEnd.substring(0, 4));  
	    int strDateEndM = Integer.parseInt(strDateEnd.substring(4, 6));  
	    int strDateEndS = Integer.parseInt(strDateEnd.substring(6, 8));  
	    if ((strDateH >= strDateBeginH && strDateH <= strDateEndH)) {  
	        // 当前时间年份数在开始时间和结束时间年份数之间  
	        if (strDateH > strDateBeginH && strDateH < strDateEndH) {  
	            return true;  
	            // 当前时间年份数等于开始时间年份数，月份数在开始和结束之间  
	        } else if (strDateH == strDateBeginH && strDateM >= strDateBeginM  
	                && strDateM <= strDateEndM) {  
	            return true;  
	            // 当前时间年份数等于开始时间年份数，月份数等于开始时间月份数，号数在开始和结束之间  
	        } else if (strDateH == strDateBeginH && strDateM == strDateBeginM  
	                && strDateS >= strDateBeginS && strDateS <= strDateEndS) {  
	            return true;  
	        }  
	        // 当前时间年份数大等于开始时间年份数，等于结束时间年份数，月份数小等于结束时间月份数  
	        else if (strDateH >= strDateBeginH && strDateH == strDateEndH  
	                && strDateM <= strDateEndM) {  
	            return true;  
	            // 当前时间年份数大等于开始时间年份数，等于结束时间年份数，月份数等于结束时间月份数，号数小等于结束时间号数  
	        } else if (strDateH >= strDateBeginH && strDateH == strDateEndH  
	                && strDateM == strDateEndM && strDateS <= strDateEndS) {  
	            return true;  
	        } else {  
	            return false;  
	        }  
	    } else {  
	        return false;  
	    }  
	}  
}
