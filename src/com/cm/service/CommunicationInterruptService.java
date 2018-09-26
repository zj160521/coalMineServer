package com.cm.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import util.StaticUtilMethod;

import com.cm.dao.CommunicationInterruptDao;
import com.cm.entity.Analoginfo;
import com.cm.entity.vo.CommunicationVo;
import com.cm.entity.vo.NameTime;

@Service("communicationInterruptService")
public class CommunicationInterruptService {
	
	@Autowired
	private CommunicationInterruptDao dao;
	
	
	//根据分页查询
	public List<CommunicationVo> getbySensor(NameTime nameTime) throws ParseException{
		List<CommunicationVo> list = dao.getbySensor(nameTime);
		List<CommunicationVo> list4 = dao.getbySwitch(nameTime);
		for(CommunicationVo l:list){
			if(l.getPosition()==null){
				l.setPosition("未配置位置");
			}
			l.setTimes(getTimes(l.getStarttime(), l.getEndtime()));
			if(l.getStatus()==5){
				l.setFaultstatus("通讯中断");
			}
			if(l.getDebug()==1){
				l.setFaultstatus("欠压");
			}else if(l.getDebug()==2){
				l.setFaultstatus("故障");
			}
		}
		for(CommunicationVo l:list4){
			if(l.getPosition()==null){
				l.setPosition("未配置位置");
			}
			l.setTimes(getTimes(l.getStarttime(), l.getEndtime()));
			if(l.getStatus()==5){
				l.setFaultstatus("通讯中断");
			}
			if(l.getDebug()==1){
				l.setFaultstatus("欠压");
			}else if(l.getDebug()==2){
				l.setFaultstatus("故障");
			}
			list.add(l);
		}
		return list;
	}
	
	public List<CommunicationVo> getbySwitch(NameTime nameTime) throws ParseException{
		List<CommunicationVo> list = dao.getbySwitch(nameTime);
		for(CommunicationVo l:list){
			l.setTimes(getTimes(l.getStarttime(), l.getEndtime()));
			if(l.getEndtime()==null){
				l.setEndtime("至今");
			}
		}
		return list;
	}
	
	//根据条件查询所有
	public List<Analoginfo> getbySensors(NameTime nameTime){
		return dao.getbySensors(nameTime);
	}
	
	public int getcount(NameTime nameTime){
		return dao.getcount(nameTime);
	}
	public List<Analoginfo> getallbySensor(NameTime nameTime){
		return dao.getallbySensor(nameTime);
	}
	
	private String getTimes(String time1,String time2) throws ParseException{
		if(time1!=null&&time2!=null){
			return StaticUtilMethod.longToTimeFormat(time1, time2).getTimCast();
		}else if(time1!=null&&time2==null){
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time3 =format.format(new Date());
			return StaticUtilMethod.longToTimeFormat(time1, time3).getTimCast();
		}else{
			return null;
		}
		
	}
}
