package com.cm.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cm.entity.vo.NameTime;
import com.cm.security.LoginManage;

@Scope("prototype")
@Controller
@RequestMapping("/compute")
public class ComputeController {
	
	@Autowired
	private ResultObj result;
	
	@Autowired
	private LoginManage loginManage;
	@RequestMapping(value="/areabytime",method=RequestMethod.POST)
	@ResponseBody
	public Object addDepartment(@RequestBody NameTime nt,HttpServletRequest request){
		if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}
		
		
		
		return result.setStatus(0, "ok");
	}
}
