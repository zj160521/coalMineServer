package com.cm.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cm.entity.Sensor;
import com.cm.entity.SwitchSensor;
import com.cm.security.LoginManage;
import com.cm.service.VentilateService;

@Scope
@Controller
@RequestMapping("/ventilate")
public class VentilateController {

	@Autowired
	private LoginManage loginManage;
	
	@Autowired
	private ResultObj result;
	
	@Autowired
	private VentilateService service;
	
	@RequestMapping(value="/all",method=RequestMethod.GET)
	@ResponseBody
	public Object getAllVentilateSensor(HttpServletRequest request){
		if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}
		try {
			List<Object> list = new ArrayList<Object>();
			List<Sensor> sensors = service.getAllVentilateSensor();
			for (Sensor sensor : sensors) {
				list.add(sensor);
			}
			List<SwitchSensor> switches = service.getAllVentilateSwitchSensor();
			for (SwitchSensor switchSensor : switches) {
				list.add(switchSensor);
			}
			result.clean();
			result.put("data", list);
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}
}
