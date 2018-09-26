package com.cm.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cm.entity.Config;
import com.cm.entity.Sensor;
import com.cm.security.LoginManage;
import com.cm.service.ConfigService;

@Scope("prototype")
@Controller
@RequestMapping(value = "/config")
public class ConfigController {
		
	@Autowired
	private ResultObj result;
	@Autowired
	private LoginManage loginManage;
	@Autowired
	private ConfigService  service;
	
	@RequestMapping(value = "/update",method=RequestMethod.POST)
	@ResponseBody
	public Object update(@RequestBody Config config, HttpServletRequest request){
		 if (!loginManage.isUserLogin(request)) {
	            return result.setStatus(-1, "no login");
	        }
	        try {
	           service.update(config);
	        } catch (Exception e) {
	            e.printStackTrace();
	            result.clean();
	            return result.setStatus(-4, "exception");
	        }
	        return result.setStatus(0, "ok");
	}
	
	@RequestMapping(value = "/getconfig",method=RequestMethod.GET)
	@ResponseBody
	public Object update(HttpServletRequest request){
		 if (!loginManage.isUserLogin(request)) {
	            return result.setStatus(-1, "no login");
	        }
	        try {
	        	Config config = service.getConfig("debugController");
	        	result.put("data", config);
	        } catch (Exception e) {
	            e.printStackTrace();
	            result.clean();
	            return result.setStatus(-4, "exception");
	        }
	        return result.setStatus(0, "ok");
	}
}
