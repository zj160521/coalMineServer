package com.cm.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import util.UtilMethod;

import com.cm.dao.IWorkerWarningFactory;
import com.cm.entity.vo.LocalizeVo;
import com.cm.entity.vo.Searchform;

@Service(value = "UnderMineOvertimeQueryService")
public class UnderMineOvertimeQueryService implements IWorkerWarningFactory{

	@Autowired
	private LocalService localService;

	public List<?> warningQuery(Searchform searchform) {
		
		try {
			searchform.setRadio(1);
			List<LocalizeVo> allAream = localService.getAllAream(searchform);
			List<LocalizeVo> overtimeWorkers = countOvertimeWorker(allAream);
			return overtimeWorkers;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public List<LocalizeVo> countOvertimeWorker(List<LocalizeVo> list) throws ParseException{
		
		List<LocalizeVo> resultList = new ArrayList<LocalizeVo>();
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		for(LocalizeVo localize : list){
			long longTimeCast = localize.getLongTimeCast();
			if(longTimeCast > 28800000){
				
				Date date = df.parse(localize.getStarttime());
				
				long offWorkTime = date.getTime()+28800000;
				String offWork = df.format(offWorkTime);
				localize.setEndtime(offWork);
				long overTime = longTimeCast - 28800000;
				
				String countTimeCast = UtilMethod.countTimeCast(overTime);
				
				localize.setWellduration(countTimeCast);
				
				resultList.add(localize);
			}
				
		}
		
		return resultList;
	}
	
}