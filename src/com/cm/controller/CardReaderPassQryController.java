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

import com.cm.dao.ICardReaderPassQryDao;
import com.cm.entity.vo.CardReaderREC;
import com.cm.entity.vo.Searchform;
import com.cm.security.LoginManage;


@Scope("prototype")
@Controller
@RequestMapping("/CardReaderPassQry")
public class CardReaderPassQryController {

	@Autowired
	private ResultObj result;
	@Autowired
	private LoginManage loginManage;
	@Autowired
	private ICardReaderPassQryDao dao;
	private String startTime = " 00:00:00";
	private String endTime = " 23:59:59";
	private String date = null;
	
	@RequestMapping(value = "/getRecs", method = RequestMethod.POST)
	@ResponseBody
	public Object getData(
			@RequestBody Searchform searchform,
			HttpServletRequest request) throws ParseException {
		
		//测试用
		
//		Searchform searchform = new Searchform();
//		searchform.setStarttime("2018-03-28");
		
		if(!loginManage.isUserLogin(request))
			return result.setStatus(-1, "no login");
		
		date = searchform.getStarttime();
		
		searchform.setStarttime(getStartTime());
		searchform.setEndtime(getEndTime());
		
		List<CardReaderREC> stepInRecs = dao.getStepInRecs(searchform);
		return PageHelper.controllerReturnMethod(stepInRecs, searchform, result);
		
	}
	
	public String getStartTime(){
		return date.concat(startTime);
	}
	
	public String getEndTime(){
		return date.concat(endTime);
	}
}
