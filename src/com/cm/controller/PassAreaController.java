package com.cm.controller;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import util.PageHelper;
import util.SearchformCheck;

import com.cm.entity.vo.Coalmine_routeVo;
import com.cm.entity.vo.Searchform;
import com.cm.security.LoginManage;
import com.cm.service.PassAreaService;


@Scope("prototype")
@Controller
@RequestMapping("/localize")
public class PassAreaController {

	@Autowired
	private ResultObj result;
	@Autowired
	private LoginManage loginManage;
	@Autowired
	private PassAreaService passAreaService;
	
	@RequestMapping(value = "/passArea", method = RequestMethod.POST)
	@ResponseBody
	public Object getData(
			@RequestBody Searchform searchform,
			HttpServletRequest request) throws ParseException {
		
		//测试用
//		Searchform searchform = new Searchform();
//		searchform.setStarttime("2018-01-01 19:55:56");
//		searchform.setEndtime("2018-01-03 19:55:56");
////		searchform.setDevid(9);
//		searchform.setCur_page(1);
//		searchform.setPage_rows(20);
		
		if(SearchformCheck.searchformCheck(searchform, result))
			result.setStatus(-2, "请选择时间！");
		
		List<Coalmine_routeVo> resultList = passAreaService.getCardReaderRecord(searchform);
		
		return PageHelper.controllerReturnMethod(resultList, searchform, result);
		
	}
	
	
}
