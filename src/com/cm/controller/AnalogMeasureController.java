package com.cm.controller;



import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cm.entity.Measure;
import com.cm.security.LoginManage;
import com.cm.service.AnalogMeasureService;


@Scope("prototype")
@Controller
@RequestMapping("/analogmeasure")
public class AnalogMeasureController {
	
	
	@Autowired
	private ResultObj result;
	
	@Autowired
	private LoginManage loginManage;
	
	@Autowired
	private AnalogMeasureService service;
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public Object getOttable(@RequestBody Measure measure,HttpServletRequest request){
		if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String measuretime = format.format(new Date());
			measure.setMeasuretime(measuretime);
			service.add(measure);
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}
}
