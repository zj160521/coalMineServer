package com.cm.controller;

import com.cm.entity.Unattendance;
import com.cm.entity.Worker;
import com.cm.security.LoginManage;
import com.cm.service.UnattendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Scope
@Controller
@RequestMapping("/unnormal")
public class UnattendanceController {

	@Autowired
	private LoginManage loginManage;
	
	@Autowired
	private ResultObj result;
	
	@Autowired
	private UnattendanceService service;
	
	private HashMap<Integer, String> map = new HashMap<Integer, String>();
	
	public UnattendanceController(){
		map.put(1, "周一");
		map.put(2, "周二");
		map.put(3, "周三");
		map.put(4, "周四");
		map.put(5, "周五");
		map.put(6, "周六");
		map.put(7, "周日");
	}
	
	@RequestMapping(value="/all",method=RequestMethod.POST)
	@ResponseBody
	public Object getAll(@RequestBody Unattendance unattendance,HttpServletRequest request){
		if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}
		try {
			List<Worker> list = service.getAllWorkerUnattendanceCount(unattendance);
			for (Worker worker : list) {
				unattendance.setWorker_id(worker.getId());
				List<Unattendance> list2 = service.getAllUnattendanceById(unattendance);
				for (Unattendance attend : list2) {
					if(attend.getResponsearea_id()==-1){
						attend.setResponsearea("不在井下");
					}
					if(attend.getResponsearea_id()==0){
						attend.setResponsearea("出入口");
					}
					if(null!=attend.getStatus()){
						if(attend.getStatus().equals("1")){
							attend.setStatus("迟到");
						}else if(attend.getStatus().equals("2")&&(attend.getResponsearea_id()==-2||attend.getResponsearea_id()==0)){
							attend.setStatus("缺席");
						}else if(attend.getStatus().equals("3")&&attend.getResponsearea_id()==-1){
							attend.setStatus("早退");
						}
					}
					if(null!=attend.getEndtime()){
						DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
						Date d1 = df.parse(attend.getEndtime());  
					    Date d2 = df.parse(attend.getResponsetime());  
					    long diff = d1.getTime() - d2.getTime();//这样得到的差值是微秒级别  
					    long days = diff / (1000 * 60 * 60 * 24);  
				        long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);  
				        long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);
				        attend.setDuration(""+days+"天"+hours+"小时"+minutes+"分");
					}
				}
				worker.setList(list2);
				StringBuffer buffer = new StringBuffer();
				String week = worker.getWeek();
				if(null!=week&&!"".equals(week)){
					if(week.contains(",")){
						String[] split = week.split(",");
						for (int i = 0; i < split.length; i++) {
							Integer value = Integer.valueOf(split[i]);
							if(map.containsKey(value)){
								if(i == split.length-1){
									buffer.append(map.get(value));
									buffer.append(" ");
									buffer.append(worker.getDayrange());
								}else{
									buffer.append(map.get(value)+",");
								}
							}
						}
					}else{
						Integer value = Integer.valueOf(week);
						if(map.containsKey(value)){
							buffer.append(map.get(value));
							buffer.append(" ");
							buffer.append(worker.getDayrange());
						}
					}
				}
				worker.setWeek(buffer.toString());
			}
			result.put("data", list);
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}
}
