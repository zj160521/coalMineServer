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
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import util.PageHelper;
import util.SearchformCheck;

import com.cm.dao.IWorkerWarningFactory;
import com.cm.entity.vo.Searchform;
import com.cm.security.LoginManage;


@Scope("prototype")
@Controller
@RequestMapping("/localize")
public class WorkerWarningQueryController {

	@Autowired
	private ResultObj result;
	@Autowired
	private LoginManage loginManage;
	
	@RequestMapping(value = "/personAlarmSearch", method = RequestMethod.POST)
	@ResponseBody
	public Object getData(@RequestBody Searchform searchform,HttpServletRequest request) throws Exception {
		
		//测试用
//		Searchform searchform = new Searchform();
//		searchform.setStarttime("2017-12-30 19:55:56");
//		searchform.setEndtime("2018-01-08 08:55:56");
////		searchform.setArea_id(212);
////		searchform.setRfcard_id(77);
//		searchform.setCur_page(1);
//		searchform.setPage_rows(20);
//		searchform.setiType("AreaOvertimeQueryService");
		
		if(SearchformCheck.searchformCheck(searchform, result))
			result.setStatus(-2, "请选择时间！");
		
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
		IWorkerWarningFactory service = (IWorkerWarningFactory) wac.getBean(searchform.getiType());
		List<?> resultList = service.warningQuery(searchform);
		
		return PageHelper.controllerReturnMethod(resultList, searchform, result);
		
	}
	
	
}
