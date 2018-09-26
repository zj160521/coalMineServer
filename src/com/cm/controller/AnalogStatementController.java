package com.cm.controller;



import java.util.Calendar;
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

import com.cm.entity.AnalogStatement;
import com.cm.entity.Analoginfo;
import com.cm.entity.vo.HistoryData;
import com.cm.entity.vo.NameTime;
import com.cm.entity.vo.SensorReportsVo;
import com.cm.security.LoginManage;
import com.cm.service.AnalogStatementService;
import com.cm.service.AnaloginfoService;

/**
 * 环境监测报表
 * @author hetaiyun
 *
 */

@Scope("prototype")
@Controller
@RequestMapping("/analogStatement")
public class AnalogStatementController {
	

	@Autowired
	private ResultObj result;
	
	@Autowired
	private LoginManage loginManage;
	
	@Autowired
	private AnalogStatementService service;
	
	@Autowired
	private AnaloginfoService anaService;
	
	/**
	 * 模拟量（日）班报表总表
	 * @param nameTime
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getottable/getall",method=RequestMethod.POST)
	@ResponseBody
	public Object getOttable(@RequestBody NameTime nameTime,HttpServletRequest request){
		if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}
		try {
			boolean start = valiDateTimeWithLongFormat(nameTime.getStarttime());
			if(nameTime.getAtype()==1&&start==true){
					List<SensorReportsVo> list = service.getAnasByDay(nameTime);
					if(list.size()==0){
						result.put("data", list);
						return result.setStatus(0, "没有数据！");
					}else{
						result.put("data", list);
					}
			}else{
				return result.setStatus(-2, "时间输入不正确，请重新输入");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}
	/**
	 * 模拟量日（班）表分表 
	 * @param nameTime
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getTable/getall",method=RequestMethod.POST)
	@ResponseBody
	public Object getAnalogalarm(@RequestBody NameTime nameTime,HttpServletRequest request){
		if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}
		try {
			boolean start = valiDateTimeWithLongFormat(nameTime.getStarttime());
			if(start==true){
				if(nameTime.getAtype()==1){
					List<AnalogStatement> list = service.getAlerts(nameTime);
					if(list.size()==0){
						result.put("data", list);
						return result.setStatus(0, "没有数据！");
					}else{
						result.put("data", list);
					}
				}else if(nameTime.getAtype()==2){
					List<AnalogStatement> list = service.getpowers(nameTime);
					if(list.size()==0){
						result.put("data", list);
						return result.setStatus(0, "没有数据！");
					}else{
						result.put("data", list);
					}
				}else if(nameTime.getAtype()==3){
					List<AnalogStatement> list = service.getfeeds(nameTime);
					if(list.size()==0){
						result.put("data", list);
						return result.setStatus(0, "没有数据！");
					}else{
						result.put("data", list);
					}
				}
			}else{
				return result.setStatus(-2, "时间输入不正确，请重新输入");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}
	
	/**
	 * 历史数据
	 * @param nameTime
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getHistory/getall",method=RequestMethod.POST)
	@ResponseBody
	public Object getAnalogHistory(@RequestBody NameTime nameTime,HttpServletRequest request){
		if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}
		try {
			boolean start = valiDateTimeWithLongFormat(nameTime.getStarttime());
			boolean end = valiDateTimeWithLongFormat(nameTime.getEndtime());		
			if(start==true&&end==true){
					nameTime.setStart_row((nameTime.getCur_page()-1)*nameTime.getPage_rows());
					if(nameTime.getAtype()==1){
						List<HistoryData> list2 = service.getHistorys(nameTime);
						result.put("alldata", list2);
					}
					List<Analoginfo> list = service.getHistory(nameTime);
					nameTime.setTotal_rows(service.getcountHis(nameTime));
					result.put("namTime", nameTime);
					result.put("data",list);
			}else{
				return result.setStatus(-2, "时间输入不正确，请重新输入");
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
