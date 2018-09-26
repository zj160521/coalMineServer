package com.cm.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cm.entity.SensorCall;
import com.cm.entity.SwitchSensorCall;
import com.cm.entity.vo.NameTime;
import com.cm.entity.vo.SensorCallVo;
import com.cm.security.LoginManage;
import com.cm.service.SensorCallService;

@Scope("prototype")
@Controller
@RequestMapping(value = "/sensorCall")
public class SensorCallController {
	
	
	@Autowired
	private ResultObj result;
	
	@Autowired
	private LoginManage loginManage;
	
	@Autowired
	private SensorCallService service;
	
	
	@RequestMapping(value="/getAnalog",method=RequestMethod.POST)
	@ResponseBody
	public Object getsensorCall(@RequestBody SensorCallVo callVo,HttpServletRequest request){
		if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}
		try {
			
			if(callVo.getMark()==1){
				List<SensorCall> list = service.getSensorCall(callVo);
				result.put("data", list);
			}
			if(callVo.getMark()==2){
				List<SwitchSensorCall> list = service.getSwitchSensorCall(callVo);
				result.put("data", list);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}
	
	@RequestMapping(value="/getalarm",method=RequestMethod.POST)
	@ResponseBody
	public Object getsensorAlarm(@RequestBody SensorCallVo callVo,HttpServletRequest request){
		if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}
		try {
			
			if(callVo.getMark()==1){
				List<SensorCall> list = service.getsensorAlarm(callVo);
				result.put("data", list);
			}
			if(callVo.getMark()==2){
				List<SwitchSensorCall> list = service.getswitchSensorAlarm(callVo);
				result.put("data", list);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
		
	}
	
	@RequestMapping(value="/getpower",method=RequestMethod.POST)
	@ResponseBody
	public Object getsensorPower(@RequestBody SensorCallVo callVo,HttpServletRequest request){
		if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}
		try {
			
			if(callVo.getMark()==1){
				List<SensorCall> list = service.getsensorPower(callVo);
				result.put("data", list);
			}
			if(callVo.getMark()==2){
				List<SwitchSensorCall> list = service.getswitchSensorPower(callVo);
				result.put("data", list);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
		
	}
	
	@RequestMapping(value="/getrepower",method=RequestMethod.POST)
	@ResponseBody
	public Object getsensorRePower(@RequestBody SensorCallVo callVo,HttpServletRequest request){
		if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}
		try {
			
			if(callVo.getMark()==1){
				List<SensorCall> list = service.getsensorRepower(callVo);
				result.put("data", list);
			}
			if(callVo.getMark()==2){
				List<SwitchSensorCall> list = service.getswitchSensorRepower(callVo);
				result.put("data", list);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
		
	}
	
	@RequestMapping(value="/getfailure",method=RequestMethod.POST)
	@ResponseBody
	public Object getfailure(@RequestBody SensorCallVo callVo,HttpServletRequest request){
		if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}
		try {
			List<SensorCall> list = service.getfailure(callVo);
			result.put("data", list);
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
		
	}
	
	
	@RequestMapping(value="/getstatechange",method=RequestMethod.POST)
	@ResponseBody
	public Object getstatechange(@RequestBody NameTime nameTime,HttpServletRequest request){
		if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}
		try {
			List<SwitchSensorCall> list = service.getSwitchStateChange(nameTime);
			result.put("data", list);
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
		
	}
	
	
	
	
}
