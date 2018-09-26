package com.cm.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cm.entity.vo.Sound;
import com.cm.security.LoginManage;

@Scope("prototype")
@Controller
@RequestMapping(value = "/sound")
public class GetSoundController {
	@Autowired
	private LoginManage loginManage;
	@Autowired
	private ResultObj result;
	
	@RequestMapping(value = "/getSound", method = RequestMethod.POST)
	@ResponseBody
	public Object addup(HttpServletRequest request,@RequestBody Sound sound) {
		Object ret = loginManage.isLogin(request);
		if (ret != null) return ret;
		
	    
		
		return result.setStatus(0, "ok");
	}
}
