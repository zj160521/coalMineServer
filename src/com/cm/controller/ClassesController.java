package com.cm.controller;

import com.cm.entity.Classes;
import com.cm.security.LoginManage;
import com.cm.service.ClassesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@Scope("prototype")
@Controller
@RequestMapping("/classes")
public class ClassesController {
	
	@Autowired
	private LoginManage loginManage;
	
	@Autowired
	private ResultObj result;
	
	@Autowired
	private ClassesService classesService;
	
	private HashMap<Integer, String> map = new HashMap<Integer, String>();
	
	public ClassesController(){
		map.put(1, "周一");
		map.put(2, "周二");
		map.put(3, "周三");
		map.put(4, "周四");
		map.put(5, "周五");
		map.put(6, "周六");
		map.put(7, "周日");
	}
	
	@RequestMapping(value="/all",method=RequestMethod.GET)
	@ResponseBody
	public Object getAll(HttpServletRequest request){
		if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}
		
		try {
			List<Classes> list = classesService.getAll();
			for (Classes classes : list) {
				StringBuffer buffer = new StringBuffer();
				String week = classes.getWeek();
				if(null!=week){
					if(week.contains(",")){
						String[] split = week.split(",");
						for (int i = 0; i < split.length; i++) {
							Integer value = Integer.valueOf(split[i]);
							if(map.containsKey(value)){
								if(i == split.length-1){
									buffer.append(map.get(value));
								}else{
									buffer.append(map.get(value)+",");
								}
							}
						}
					}else{
						Integer value = Integer.valueOf(week);
						if(map.containsKey(value)){
							buffer.append(map.get(value));
						}
					}
				}
				classes.setWeek(buffer.toString());
			}
			result.put("data", list);
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public Object addup(@RequestBody Classes classes,HttpServletRequest request){
		if(!loginManage.isUserLogin(request))
			return result.setStatus(-1, "no login");
		Object per = loginManage.checkPermission(request);
		if(null!=per) return per;
		try {
			classesService.add(classes);
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}
	
	@RequestMapping(value="/delete/{id}",method=RequestMethod.POST)
	@ResponseBody
	public Object delete(@PathVariable int id,HttpServletRequest request){
		if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}
		Object per = loginManage.checkPermission(request);
		if(null!=per) return per;
		try {
			classesService.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}

}
