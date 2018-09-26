package com.cm.service;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import util.StaticUtilMethod;

import com.cm.dao.SwitchStatementDao;
import com.cm.entity.vo.AnaloginfoQuery;
import com.cm.entity.vo.LongStringVo;
import com.cm.entity.vo.NameTime;
import com.cm.entity.vo.SwitchVo;

@Service("switchStatementService")
public class SwitchStatementService {
	
	@Autowired
	private SwitchStatementDao dao;
	
	
	public List<SwitchVo> getAna(NameTime nameTime) throws ParseException{
		List<SwitchVo> list = dao.getAna(nameTime);
		NameTime time = nameTime;
		for(SwitchVo l :list){
			l.setZerostate("关");
			l.setOnestate("开");
			if(l.getValue()==0){
				l.setValuestate(l.getZerostate());
			}else{
				l.setValuestate(l.getOnestate());
			}
			time.setId(l.getSensor_id());
			l.setTimes(getTimecount(time));
		}
		return list;
	}
	private String getTimecount(NameTime nameTime) throws ParseException{
		List<AnaloginfoQuery> list = dao.getTime(nameTime);
		long time = 0;
		for(AnaloginfoQuery l:list){
			if(l.getEndtime()!=null&&l.getStarttime()!=null){
				LongStringVo longToTimeFormat = StaticUtilMethod.longToTimeFormat(l.getStarttime(), l.getEndtime());
				time = time+longToTimeFormat.getTime();
			}
			
		}
		String secondTime = StaticUtilMethod.countTimeCast(time);
		return secondTime;
	}
}
