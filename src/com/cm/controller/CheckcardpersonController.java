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

import com.cm.entity.Checkcardperson;
import com.cm.entity.WorkerTrackRecord;
import com.cm.entity.vo.CheckcardpersonVo;
import com.cm.security.LoginManage;
import com.cm.service.CheckcardpersonService;



@Scope("prototype")
@Controller
@RequestMapping("/checkcardperson")
public class CheckcardpersonController {
	
	@Autowired
	private ResultObj result;
	
	@Autowired
	private LoginManage loginManage;
	
	@Autowired
	private CheckcardpersonService service;
	
	
	@RequestMapping(value="/getall",method=RequestMethod.POST)
	@ResponseBody
	public Object getall(@RequestBody CheckcardpersonVo checkcardpersonVo,HttpServletRequest request){
		if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}
		try {
//			LogOut.log.debug(checkcardpersonVo);
			checkcardpersonVo.setMonth(checkcardpersonVo.getMonth()+"-01");
			List<Checkcardperson> list = service.getall(checkcardpersonVo);
			result.put("data", list);
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}
	
	@RequestMapping(value="/getallbycard",method=RequestMethod.POST)
	@ResponseBody
	public Object getallByCard(@RequestBody CheckcardpersonVo checkcardpersonVo,HttpServletRequest request){
		if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}
		try {
			checkcardpersonVo.setMonth(checkcardpersonVo.getMonth()+"-01");
			List<WorkerTrackRecord> list = service.getallbycard(checkcardpersonVo); 
			result.put("data", list);
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}
}
