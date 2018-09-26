package com.cm.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import util.StaticUtilMethod;

import com.cm.dao.AreaOvermanQueryDao;
import com.cm.dao.IWorkerWarningFactory;
import com.cm.entity.vo.AreaOvertimeVo;
import com.cm.entity.vo.LongStringVo;
import com.cm.entity.vo.Searchform;


/**
 * 1分钟未有人员超员报警就算作报警解除
 */
@Service(value = "AreaOvermanQueryService")
public class AreaOvermanQueryService implements IWorkerWarningFactory{

	@Autowired
	private AreaOvermanQueryDao dao;

	public List<?> warningQuery(Searchform searchform) {
		
		try{
			List<AreaOvertimeVo> list = dao.getData(searchform);
			
			List<List<AreaOvertimeVo>> list2 = cutList(list);
			List<AreaOvertimeVo> resultList = new ArrayList<AreaOvertimeVo>();
			if(list2 != null && list2.size() > 0){
				for(List<AreaOvertimeVo> listVo : list2){
					AreaOvertimeVo maxValue = getMaxValue(listVo);
					resultList.add(maxValue);
				}
				
				return resultList;
			}
			
			return null;
			
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		
		
	}
	
	public List<List<AreaOvertimeVo>> cutList(List<AreaOvertimeVo> list) throws ParseException{
		
		String startTime = null;
		List<AreaOvertimeVo> listvo = null;
		List<List<AreaOvertimeVo>> resultList = new ArrayList<List<AreaOvertimeVo>>();
		
		for(int i = 0; i < list.size(); i++){
			
			list.get(i).setResponsetime(list.get(i).getResponsetime().substring(0, 19));
			
			if(startTime == null){
				startTime = list.get(i).getResponsetime();
				listvo = new ArrayList<AreaOvertimeVo>();
				listvo.add(list.get(i));
			}else{
				long countLongDvalue = StaticUtilMethod.countLongDvalue(list.get(i).getResponsetime(), startTime);
				
				if(countLongDvalue < 60000){ 
					listvo.add(list.get(i));
					startTime = list.get(i).getResponsetime();
				}else{
					resultList.add(listvo);
					
					startTime = list.get(i).getResponsetime();
					listvo = new ArrayList<AreaOvertimeVo>();
					listvo.add(list.get(i));
				}
			}
			
			if(i == list.size() - 1){
				resultList.add(listvo);
			}
		}
		
		return resultList;
	}
	
	public AreaOvertimeVo getMaxValue(List<AreaOvertimeVo> list) throws ParseException{
		
		if(list != null && list.size() > 0){
			
			AreaOvertimeVo aovo = list.get(0);
			aovo.setStarttime(aovo.getResponsetime());
			
			int max = 0;
			AreaOvertimeVo maxVo = null;
			for(AreaOvertimeVo vo : list){
				if(vo.getPersonNum() > max){
					max = vo.getPersonNum();
					maxVo = vo;
				}
				aovo.setEndtime(vo.getResponsetime());	
			}
			
			aovo.setPersonNum(max);
			aovo.setMaxstarttime(maxVo.getResponsetime());
			
			LongStringVo longToTimeFormat = StaticUtilMethod.longToTimeFormat(aovo.getStarttime(), list.get(list.size() -1).getResponsetime());
			
			if(longToTimeFormat.getTime() < 1000)
				aovo.setWellduration("0 小时 00 分 01秒");
			else
				aovo.setWellduration(longToTimeFormat.getTimCast());
			return aovo;
		}
		
				
		return null;
	}
	
}