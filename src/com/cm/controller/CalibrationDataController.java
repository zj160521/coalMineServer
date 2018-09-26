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

import com.cm.entity.CalibrationData;
import com.cm.entity.vo.NameTime;
import com.cm.security.LoginManage;
import com.cm.service.CalibrationDataService;


@Scope("prototype")
@Controller
@RequestMapping("/calibrationData")
public class CalibrationDataController {

	
	@Autowired
	private ResultObj result;

	@Autowired
	private LoginManage loginManage;
	
	@Autowired
	private CalibrationDataService service;
	
	
	@RequestMapping(value = "/getall", method = RequestMethod.POST)
	@ResponseBody
	public Object getall(@RequestBody NameTime nameTime,HttpServletRequest request) {
		if (!loginManage.isUserLogin(request)) {
			return result.setStatus(-1, "no login");
		}
		try {
			nameTime.setStart_row((nameTime.getCur_page()-1)*nameTime.getPage_rows());
			List<CalibrationData> list = service.getpagedata(nameTime);
			nameTime.setTotal_rows(service.getalldata(nameTime));
			if(list!=null){
				result.put("data", list);
				result.put("nametime", nameTime);
			}else{
				result.put("data", list);
				return result.setStatus(0, "没有数据！");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}
}
