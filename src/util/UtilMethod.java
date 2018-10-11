package util;

import com.cm.entity.vo.LongStringVo;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class UtilMethod {
	
	private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 判断str是否是空串
	 * @param str
	 * @return
	 */
	public static boolean notEmptyStr(String str){
		return (str != null && !str.isEmpty()) ? true : false;
	}
	
	/**
	 * 判断是否为list的最后一个元素
	 * @param list
	 * @param i
	 * @return
	 */
	public static boolean isLastRec(List<?> list, int i){
		return (list.size() == i) ? true : false;
	}
	
	/**
	 * 判断是否为非空列表
	 * @param list
	 * @return
	 */
	public static boolean notEmptyList(List<?> list){
		return (list != null && !list.isEmpty()) ? true : false;
	}

	/**
	 * "yyyy-MM-dd HH:mm:ss"类型String转换为Date类型
	 * 
	 * @param StringTime
	 */
	public static Date StringToDateFormat(String StringTime)
			throws ParseException {
		return df.parse(StringTime);
	}
	
	/**
	 * "yyyy-MM-dd HH:mm:ss"类型Date转换为String类型
	 * @param dateTime
	 * @return
	 * @throws ParseException
	 */
	public static String DateToStringFormat(Date dateTime)
			throws ParseException {
		return df.format(dateTime);
	}

	/**
	 * 计算两个"yyyy-MM-dd HH:mm:ss"类型String的差值,得到long类型结果和字符串结果，并封装入LongStringVo对象
	 * @param time1
	 * @param time2
	 * @return
	 * @throws ParseException
	 */
	public static LongStringVo longToTimeFormat(String time1, String time2)
			throws ParseException {
		
		long countLongDvalue = countLongDvalue(time1, time2);
		String countTimeCast = countTimeCast(countLongDvalue);

		LongStringVo lsv = new LongStringVo();
		lsv.setTime(countLongDvalue);
		lsv.setTimCast(countTimeCast);

		return lsv;
	}

	/**
	 * 计算两个"yyyy-MM-dd HH:mm:ss"类型String的差值,返回long类型结果
	 * @param time1
	 * @param time2
	 * @return
	 * @throws ParseException
	 */
	public static long countLongDvalue(String time1, String time2)
			throws ParseException {
		time1 = time1.substring(0, 19);
		time2 = time2.substring(0, 19);
		long longTime1 = df.parse(time1).getTime();
		long longTime2 = df.parse(time2).getTime();

		long time = longTime1 - longTime2;

		return (time < 0) ? -time : time;
	}

	/**
	 * 计算两个"yyyy-MM-dd HH:mm:ss"类型String的差值,返回String类型结果
	 * @param time
	 * @return
	 */
	public static String countTimeCast(long time) {

		int hours = (int) (time / 3600000);
		int minutes = (int) ((time - hours * 3600000) / 60000);
		int seconds = (int) ((time - hours * 3600000 - minutes * 60000) / 1000);
		String timeString1 = null;
		String timeString2 = null;
		
		timeString1 = seconds < 10 ? hours + " 小时 0" + minutes + " 分 0"+ seconds + "秒" 
				: hours + " 小时 0" + minutes + " 分 " + seconds + "秒";
		timeString2 = seconds < 10 ? hours + " 小时 " + minutes + " 分 0"+ seconds + "秒" 
				: hours + " 小时 " + minutes + " 分 " + seconds + "秒";

		String timeString = minutes < 10 ? timeString1 : timeString2;

		return timeString;
	}
	
	/**
	 * xx小时xx分xx秒类型String，转化成秒
	 * @param s
	 * @return
	 */
	public static Integer getSec(String s){
		int hour = 0;
		int min = 0;
		int sec = 0;
		
		if(s.contains("小时") && s.contains("分") && s.contains("秒")){
			
			hour = getData(s, "小时");
			
			min = getData(s.split("小时")[1], "分");
			
			sec = getData(s.split("小时")[1].split("分")[1], "秒");
		}else
			return 0;
		return hour*60*60 + min*60 + sec;
	}
	
	public static int getData(String s, String splitFlag){
		String[] split = s.split(splitFlag);
		if(split != null){
			boolean integer = isInteger(split[0].trim());
			if(integer){
				return Integer.parseInt(split[0].trim());
			}else
				return 0;
		}else
			return 0;
	}
	
	/**
	 * 判断String是否是int类型
	 * @param s
	 * @return
	 */
	public static boolean isInteger(String s){
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * 判断时间类型字符串s是否处于startS和endS中间
	 * @param s
	 * @param startS
	 * @param endS
	 * @return
	 * @throws ParseException
	 */
	public static boolean isMid(String s, String startS, String endS) throws ParseException{
		long time = df.parse(s).getTime();
		long time2 = df.parse(startS).getTime();
		long time3 = df.parse(endS).getTime();
		
		return time - time2 >= 0 && time3 - time >= 0 ? true : false;
	}
	
	/**
	 * 获取现在时间，字符串类型结果
	 * @return
	 */
	public static String getNow(){
		Calendar cal = Calendar.getInstance();
		String now = df.format(cal.getTime());
		return now;
	}
	
	/**
	 * 获得当日凌晨0点时间
	 * @return
	 */
	public static String getStarttimeOfDay(){
		Calendar cal = Calendar.getInstance();
		
		cal.set(Calendar.HOUR, -12);  
		cal.set(Calendar.MINUTE, 0);  
		cal.set(Calendar.SECOND, 0);  
		cal.set(Calendar.MILLISECOND, 0);  
		Date sTimeow = cal.getTime();
		
		String startTime = df.format(sTimeow);
		return startTime;
	}
	
	/**
	 * 获取当日最后时间
	 * @return
	 */
	public static String getEndtimeOfDay(){
		Calendar cal = Calendar.getInstance();
		
		cal.set(Calendar.HOUR, 11);  
		cal.set(Calendar.MINUTE, 59);  
		cal.set(Calendar.SECOND, 59);  
		cal.set(Calendar.MILLISECOND, 999);
		Date sTimeow = cal.getTime();
		
		String startTime = df.format(sTimeow);
		return startTime;
	}
	
    /**
     * Windows下读取文件创建时间  
     * @param filePath 文件路径
     * @return
     */
    public static String getCreateTime(String filePath){
//        String filePath = "D:\\1.txt";
        String strTime = null;  
        try {  
            Process p = Runtime.getRuntime().exec("cmd /C dir "           
                    + filePath  
                    + "/tc" );
            InputStream is = p.getInputStream();   
            BufferedReader br = new BufferedReader(new InputStreamReader(is));             
            String line;  
            while((line = br.readLine()) != null){  
                if(line.endsWith(".txt")){  
                    strTime = line.substring(0,19);
                    break;  
                }                             
             }   
        } catch (IOException e) {  
            e.printStackTrace();  
        }         
        System.out.println("创建时间 === " + strTime);     
        return strTime;
        //输出：创建时间   2009-08-17  10:21
    }  
    
    /**
     * Windows下获取文件最后修改时间
     * @param path 文件路径
     * @return
     */
    public static String getLastModifyTime(String path){
    	File f = new File(path);
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Calendar cal = Calendar.getInstance();
    	cal.setTimeInMillis(f.lastModified());
    	String time = sdf.format(cal.getTime());
    	return time;
    }
    
    /**
     * 判断字符串是否是"yyyy-MM-dd HH:mm:ss"类型字符串
     * @param str
     * @return
     */
    public static boolean isTimeString(String str){
    	if(!UtilMethod.notEmptyStr(str))
    		return false;
    	try {
			df.parse(str);
			return true;
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
    }

    /**
     * 判断传过来的时间字符串是昨天，今天还是明天，1表示昨天，0表示今天，-1表示明天，以此类推,null表示传过来的时间字符串不属于当前年或当前月
     * @param time
     * @return
     */
    public static Integer isYesterday(String time){
        Integer i = null;
        try{
            Calendar today = Calendar.getInstance();
            today.setTime(new Date());

            Calendar yestoday = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date parse = sdf.parse(time);
            yestoday.setTime(parse);

            if(today.get(Calendar.YEAR) == yestoday.get(Calendar.YEAR)){
                if(today.get(Calendar.MONTH) == yestoday.get(Calendar.MONTH)){
                    i = today.get(Calendar.DAY_OF_YEAR) - yestoday.get(Calendar.DAY_OF_YEAR);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return i;
    }

    /**
     * 判断传过来的时间字符串是上个月，本月，还是下个月，1表示上个月，0表示本月，-1表示下个月，以此类推，null表示传过来的时间字符串不属于当前年
     * @param time
     * @return
     */
    public static Integer isLastMonth(String time){
        Integer i = null;
        try{
            Calendar thismonth = Calendar.getInstance();
            thismonth.setTime(new Date());

            Calendar lastmonth = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date parse = sdf.parse(time);
            lastmonth.setTime(parse);

            if(thismonth.get(Calendar.YEAR) == lastmonth.get(Calendar.YEAR)){
                i = thismonth.get(Calendar.MONTH) - lastmonth.get(Calendar.MONTH);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return i;
    }

    /**
     * 判断传过来的时间字符串是去年，今年还是明年，-1表示去年，0表示今年，1表示明年，以此类推
     * @param time
     * @return
     */
    public static Integer yearDistinguish(String time){
        Integer i = null;
        try{
            Calendar thisyear = Calendar.getInstance();
            Calendar unkownYear = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            unkownYear.setTime(sdf.parse(time));
            i = unkownYear.get(Calendar.YEAR) - thisyear.get(Calendar.YEAR);
        } catch (Exception e){
            e.printStackTrace();
        }
        return i;
    }
    
}
