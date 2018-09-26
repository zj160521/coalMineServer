package com.cm.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cm.service.StatisticalService;

@Scope("prototype")
@Controller
@RequestMapping("/statistical")
public class StatisticalController {

	@Autowired
	private StatisticalService statisticalService;

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@ResponseBody
	public Object getAll(HttpServletRequest request) {
		return statisticalService.getAll(request);
	}

}
