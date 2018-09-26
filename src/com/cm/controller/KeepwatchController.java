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

import com.cm.entity.vo.Keepwatch;
import com.cm.entity.vo.KeepwatchVO;
import com.cm.security.LoginManage;
import com.cm.service.KeepwatchService;

@Scope("prototype")
@Controller
@RequestMapping("/keep")
public class KeepwatchController {
	
	@Autowired
	private LoginManage loginManage;
	
	@Autowired
	private ResultObj result;
	
	@Autowired
	private KeepwatchService keepwatchService;
	
	@RequestMapping(value="/watch",method=RequestMethod.POST)
	@ResponseBody
	public Object getAllKeepwatchRecord(@RequestBody KeepwatchVO keep,HttpServletRequest request){
		if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}
		try {
			List<Keepwatch> list = keepwatchService.getAllKeepwatchRecord(keep);
			if(list.isEmpty()){
				return result.setStatus(-3, "no data");
			}
			result.put("data", list);
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}

}
