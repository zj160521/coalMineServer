package com.cm.controller;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.cm.entity.SecretData;
import com.cm.entity.vo.NameTime;
import com.cm.security.LoginManage;
import com.cm.service.SecretDataService;

@Scope("prototype")
@Controller
@RequestMapping(value = "/secretData")
public class SecretDataController {
	
	
	@Autowired
	private ResultObj result;
	
	@Autowired
	private LoginManage loginManage;
	
	@Autowired
	private SecretDataService service;
	
	
	@RequestMapping(value="/getbypag",method=RequestMethod.POST)
	@ResponseBody
	public Object getbypag(@RequestBody NameTime nameTime,HttpServletRequest request){
		if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String nowday = dateFormat.format(new Date());
			long a = dateFormat.parse(nowday).getTime();
			long b = dateFormat.parse(nameTime.getDay()).getTime();
			if(b>a){
				List<SecretData> list = new ArrayList<SecretData>();
				result.put("data", list);
				return result.setStatus(0, "没有数据！");
			}else{
				String starttime = nameTime.getDay()+" "+nameTime.getStarttime();
				String endtime = nameTime.getDay()+" "+nameTime.getEndtime();
				boolean start = valiDateTimeWithLongFormat(starttime);
				boolean end = valiDateTimeWithLongFormat(endtime);
				if(start==true&&end==true){
					nameTime.setStarttime(starttime);
					nameTime.setEndtime(endtime);
					nameTime.setStart_row((nameTime.getCur_page()-1)*nameTime.getPage_rows());
					List<SecretData> list = service.getbypag(nameTime);
					nameTime.setTotal_rows(service.getcount(nameTime));
					if(list!=null&&list.size()>0){
						result.put("data", list);
						result.put("nametime", nameTime);
					}else{
						result.put("data", list);
						return result.setStatus(0, "没有数据！");
					}
				}else{
					return result.setStatus(-2, "时间格式有误！");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}
	
	
	
	@RequestMapping(value="/getallcount",method=RequestMethod.POST)
	@ResponseBody
	public Object getallcount(@RequestBody NameTime nameTime,HttpServletRequest request){
		if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}
		try {
			String starttime = nameTime.getDay()+" "+nameTime.getStarttime();
			String endtime = nameTime.getDay()+" "+nameTime.getEndtime();
			boolean start = valiDateTimeWithLongFormat(starttime);
			boolean end = valiDateTimeWithLongFormat(endtime);
			if(start==true&&end==true){
				nameTime.setStarttime(starttime);
				nameTime.setEndtime(endtime);
				nameTime.setStart_row((nameTime.getCur_page()-1)*nameTime.getPage_rows());
				List<SecretData> list = service.getall(nameTime);
				nameTime.setTotal_rows(service.getcountdata(nameTime));
				if(list!=null&&list.size()>0){
					result.put("data", list);
					result.put("nametime", nameTime);
				}else{
					result.put("data", list);
					return result.setStatus(0, "没有数据！");
				}
			}else{
				return result.setStatus(-2, "时间格式有误！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}
	
	public static boolean valiDateTimeWithLongFormat(String timeStr) {
		String format = "((19|20)[0-9]{2})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01]) "
				+ "([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]";
		Pattern pattern = Pattern.compile(format);
		Matcher matcher = pattern.matcher(timeStr);
		if (matcher.matches()) {
			pattern = Pattern.compile("(\\d{4})-(\\d+)-(\\d+).*");
			matcher = pattern.matcher(timeStr);
			if (matcher.matches()) {
				int y = Integer.valueOf(matcher.group(1));
				int m = Integer.valueOf(matcher.group(2));
				int d = Integer.valueOf(matcher.group(3));
				if (d > 28) {
					Calendar c = Calendar.getInstance();
					c.set(y, m-1, 1);
					int lastDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
					return (lastDay >= d);
				}
			}
			return true;
		}
		return false;
	}
	
}
