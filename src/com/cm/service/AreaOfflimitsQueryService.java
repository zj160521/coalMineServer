package com.cm.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import util.UtilMethod;

import com.cm.dao.AreaOfflimitsQueryDao;
import com.cm.dao.IWorkerWarningFactory;
import com.cm.entity.AreaWorker;
import com.cm.entity.vo.AreaOfflimitsVo;
import com.cm.entity.vo.LongStringVo;
import com.cm.entity.vo.Searchform;

/**
 * 
 */
@Service(value = "AreaOfflimitsQueryService")
public class AreaOfflimitsQueryService implements IWorkerWarningFactory{
	
	@Autowired
	private AreaOfflimitsQueryDao dao;

	public List<?> warningQuery(Searchform searchform) {
		
		try{

			List<AreaOfflimitsVo> list = dao.getData(searchform);
			List<AreaWorker> allAreaWorker = dao.getAllAreaWorker();

			List<AreaOfflimitsVo> resultList = new ArrayList<AreaOfflimitsVo>();
			
			if(list != null && list.size() > 0){
				List<List<AreaOfflimitsVo>> eachWorkerList = getEachWorkerList(list, allAreaWorker);
				
				for(List<AreaOfflimitsVo> aovoList : eachWorkerList){
					
					List<List<AreaOfflimitsVo>> cutList = cutList(aovoList);
					for(List<AreaOfflimitsVo> listVo : cutList){
						
						if(listVo != null && listVo.size() > 0){
							
							AreaOfflimitsVo aovo = listVo.get(0);
							AreaOfflimitsVo lastVo = listVo.get(listVo.size()-1);
							
							LongStringVo longToTimeFormat = UtilMethod.longToTimeFormat(aovo.getResponsetime(), 
									lastVo.getResponsetime());
							
							if(longToTimeFormat.getTime() < 1000)
								aovo.setWellduration("0 小时 00 分 01秒");
							else
								aovo.setWellduration(longToTimeFormat.getTimCast());
							
							resultList.add(aovo);
						}
					}
				}
				
				return resultList;
			}
			
			return null;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<List<AreaOfflimitsVo>> getEachWorkerList(List<AreaOfflimitsVo> list, List<AreaWorker> allAreaWorker){
		
		List<List<AreaOfflimitsVo>> ll = new ArrayList<List<AreaOfflimitsVo>>();
		
		
		int cardId = 0;
		int i = 0;
		List<AreaOfflimitsVo> resultList = null;
		for(AreaOfflimitsVo aovo : list){
			AreaWorker awb = null;
			for(AreaWorker aw : allAreaWorker){
				if(aovo.getArea_id() == aw.getArea_id() && aovo.getRfcard_id() == aw.getRfcard_id())
					awb = aw;
			}
			if(awb != null)
				continue;
			if(cardId == 0){
				cardId = aovo.getRfcard_id();
				resultList = new ArrayList<AreaOfflimitsVo>();
				resultList.add(aovo);
			}else if(cardId == aovo.getRfcard_id()){
				resultList.add(aovo);
			}else{
				ll.add(resultList);
				cardId = aovo.getRfcard_id();
				resultList = new ArrayList<AreaOfflimitsVo>();
				resultList.add(aovo);
			}
			i++;
			if(i == list.size())
				ll.add(resultList);
		}
		return ll;
	}
	
	public List<List<AreaOfflimitsVo>> cutList(List<AreaOfflimitsVo> list) throws ParseException{
		
		String startTime = null;
		List<AreaOfflimitsVo> listvo = null;
		List<List<AreaOfflimitsVo>> resultList = new ArrayList<List<AreaOfflimitsVo>>();
		
		for(int i = 0; i < list.size(); i++){
			
			list.get(i).setResponsetime(list.get(i).getResponsetime().substring(0, 19));
			
			if(startTime == null){
				startTime = list.get(i).getResponsetime();
				listvo = new ArrayList<AreaOfflimitsVo>();
				listvo.add(list.get(i));
			}else{
				long countLongDvalue = UtilMethod.countLongDvalue(list.get(i).getResponsetime(), startTime);
				
				if(countLongDvalue < 60000){ 
					listvo.add(list.get(i));
					startTime = list.get(i).getResponsetime();
				}else{
					resultList.add(listvo);
					
					startTime = list.get(i).getResponsetime();
					listvo = new ArrayList<AreaOfflimitsVo>();
					listvo.add(list.get(i));
				}
			}
			
			if(i == list.size() - 1){
				resultList.add(listvo);
			}
		}
		
		return resultList;
	}
}