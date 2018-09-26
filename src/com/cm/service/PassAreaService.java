package com.cm.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cm.controller.ResultObj;
import com.cm.dao.PassAreaDao;
import com.cm.entity.vo.Coalmine_routeVo;
import com.cm.entity.vo.Searchform;

@Service("PassAreaService")
public class PassAreaService{

	@Autowired
	private ResultObj result;
	@Autowired
	private PassAreaDao passAreaDao;

	public List<Coalmine_routeVo> getCardReaderRecord(Searchform searchform) throws ParseException {
		
		List<Coalmine_routeVo>  list= passAreaDao.getCardReaderRecord(searchform);
		
		LinkedHashSet<Integer> linkedHashSet = new LinkedHashSet<Integer>();
		
		int cId = 0;
		HashMap<Integer, List<Coalmine_routeVo>> map = new HashMap<Integer, List<Coalmine_routeVo>>();
		List<Coalmine_routeVo> crList = null ;
		for(int i = 0; i< list.size(); i++){

			if(cId == 0){
				cId = list.get(i).getCard();
				crList = new ArrayList<Coalmine_routeVo>();
				crList.add(list.get(i));
			}else if(list.get(i).getCard() == cId){
				crList.add(list.get(i));
			}else{
				map.put(cId, crList);
				cId = list.get(i).getCard();
				crList = new ArrayList<Coalmine_routeVo>();
				crList.add(list.get(i));
			}
			
			if(i == list.size()-1){
				map.put(cId, crList);
			}
			
			list.get(i).setResponsetime(list.get(i).getResponsetime().substring(0, 19));
			linkedHashSet.add(list.get(i).getCard());
			
		}
		
		List<Coalmine_routeVo>  resultList = new ArrayList<Coalmine_routeVo>();
		
		int devid;
		Coalmine_routeVo crStart;
		Coalmine_routeVo crEnd;
		
		for(Integer cardId : linkedHashSet){
			List<Coalmine_routeVo> list2= map.get(cardId);
			devid =0;
			crStart = null;
			crEnd = null;

			for(int i = 0; i< list2.size(); i++){
				
				if(devid == 0 ){
					devid = list2.get(i).getDev_id();
					crStart = list2.get(i);
					crEnd = list2.get(i);
				}else if(devid == list2.get(i).getDev_id()){
					crEnd = list2.get(i);
				}else{
					crStart.setEndtime(crEnd.getResponsetime());
					crStart.setStarttime(crStart.getResponsetime());
					resultList.add(crStart);
					devid = list2.get(i).getDev_id();
					crStart = list2.get(i);
					crEnd = list2.get(i);
				}
				
				if(i == list2.size()-1){
					crStart.setEndtime(crEnd.getResponsetime());
					crStart.setStarttime(crStart.getResponsetime());
					resultList.add(crStart);
				}
			}
			
		}
		
		return resultList;
	}
}