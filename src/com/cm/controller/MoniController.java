package com.cm.controller;

import javax.servlet.http.HttpServletRequest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.cm.entity.Moni;


import com.cm.service.MoniService;

@Scope("prototype")
@Controller
@RequestMapping(value = "/moni")
public class MoniController {

	@Autowired
	private MoniService moniService;

	@RequestMapping(value = "/getAllMoni", method = RequestMethod.GET)
	@ResponseBody
	public Object getAll(HttpServletRequest request) {
		return moniService.getAllNvrAndDvr(request);
	}

	// 添加修改
	@RequestMapping(value = "/addup", method = RequestMethod.POST)
	@ResponseBody
	public Object addUp(HttpServletRequest request, @RequestBody Moni moni) {
		return moniService.addup(request, moni);
	}

	// 向nvr添加摄像头
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Object addDvr(HttpServletRequest request,@RequestBody Moni moni) {

		return moniService.addDvr(request, moni);
	}

	// 删除
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Object delete(HttpServletRequest request, @RequestBody Moni moni) {
		return moniService.delete(moni, request);
	}
	
	/*// 判断ip是否重复
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	@ResponseBody
	public Object check(HttpServletRequest request, @RequestBody Moni moni) {
		return moniService.checkDipAndName(moni, request);
	}*/
}
