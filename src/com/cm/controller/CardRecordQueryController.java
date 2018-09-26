package com.cm.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import util.SearchformCheck;

import com.cm.entity.vo.CardBattaryRecordVo;
import com.cm.entity.vo.Searchform;
import com.cm.security.LoginManage;
import com.cm.service.CardRecordQueryService;


@Scope("prototype")
@Controller
@RequestMapping("/localize")
public class CardRecordQueryController {

	@Autowired
	private ResultObj result;
	@Autowired
	private LoginManage loginManage;
	@Autowired
	private CardRecordQueryService cardRecordQueryService;
	
	@RequestMapping(value = "/checkCard", method = RequestMethod.POST)
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
		
		List<CardBattaryRecordVo> resultList = cardRecordQueryService.getCardReaderRecord(searchform);
		
        ArrayList<Object> list = new ArrayList<Object>();
		
		int recordNum = (searchform.getCur_page()-1) * searchform.getPage_rows();
		for(int i = 0; i < searchform.getPage_rows(); i++){
			if(i + recordNum >= resultList.size()) break;
			CardBattaryRecordVo vo = resultList.get(i + recordNum);
			vo.setResponsetime(vo.getResponsetime().substring(0, 19));
			if(vo.getBattary() == 0)
				vo.setBattaryCondition("正常");
			else if(vo.getBattary() == 1)
				vo.setBattaryCondition("低电量");
			list.add(vo);
		}
		
		int totalPages = 0;
		if(resultList.size()%searchform.getPage_rows() > 0){
			totalPages = resultList.size()/searchform.getPage_rows() + 1;
		}else {
			totalPages = resultList.size()/searchform.getPage_rows();
		}
		
		searchform.setTotal_pages(totalPages);
		searchform.setTotal_record(resultList.size());
		
		if (resultList == null || resultList.isEmpty() || resultList.size() < 1) {
			searchform.setTotal_pages(0);
			searchform.setTotal_record(0);
			result.put("data", resultList);
			result.put("searchform", searchform);
			return result.setStatus(0, "ok");
		} else {
			result.put("data", list);
			result.put("searchform", searchform);
		}
		return result.setStatus(0, "ok");
		
	}
	
	
}
