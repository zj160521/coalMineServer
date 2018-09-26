package com.cm.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import util.StaticUtilMethod;

import com.cm.dao.IWorkerWarningFactory;
import com.cm.dao.UnderMineOvermanQueryDao;
import com.cm.entity.vo.AreaOvertimeVo;
import com.cm.entity.vo.LongStringVo;
import com.cm.entity.vo.Searchform;

@Service(value = "UnderMineOvermanQueryService")
public class UnderMineOvermanQueryService implements IWorkerWarningFactory{

	@Autowired
	private UnderMineOvermanQueryDao dao;
	@Autowired
	private AreaOvermanQueryService areaOvermanQueryService;

	public List<?> warningQuery(Searchform searchform) {
		
		int maxAllowPerson = dao.getMaxAllowPerson();
		searchform.setId(maxAllowPerson);
		List<AreaOvertimeVo> list1 = dao.getData(searchform);
		
		try {
			List<List<AreaOvertimeVo>> list2 = areaOvermanQueryService.cutList(list1);
			List<AreaOvertimeVo> resultList = new ArrayList<AreaOvertimeVo>();
			
			if(list2 != null && list2.size() > 0){
				for(List<AreaOvertimeVo> listVo : list2){
					
					if(listVo != null && listVo.size() > 0){
						
						AreaOvertimeVo aovo = listVo.get(0);
						aovo.setStarttime(aovo.getResponsetime());
						aovo.setMax_allow(maxAllowPerson);
						
						int max = 0;
						AreaOvertimeVo maxVo = null;
						for(AreaOvertimeVo vo : listVo){
							if(vo.getTotalPN() > max){
								max = vo.getTotalPN();
								maxVo = vo;
							}
							aovo.setEndtime(vo.getResponsetime());	
						}
						
						aovo.setPersonNum(max);
						aovo.setMaxstarttime(maxVo.getResponsetime());
						
						LongStringVo longToTimeFormat = StaticUtilMethod.longToTimeFormat(aovo.getStarttime(), 
								listVo.get(listVo.size() -1).getResponsetime());
						
						if(longToTimeFormat.getTime() < 1000)
							aovo.setWellduration("0 小时 00 分 01秒");
						else
							aovo.setWellduration(longToTimeFormat.getTimCast());
						
						resultList.add(aovo);
					}
					
				}
				
				return resultList;
			}
			
			return null;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
}