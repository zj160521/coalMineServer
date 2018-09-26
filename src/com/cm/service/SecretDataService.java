package com.cm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cm.dao.SecretDataDao;
import com.cm.entity.SecretData;
import com.cm.entity.vo.NameTime;

@Service("secretDataService")
public class SecretDataService {
	
	@Autowired
	private SecretDataDao dao;
	
	
	public List<SecretData> getbypag(NameTime nameTime){
		List<SecretData> list = dao.getbypag(nameTime);
		for(SecretData l:list){
			if(l.getPosition()==null){
				l.setPosition("未配置位置");
			}
			if(Math.abs(l.getLimit_alarms())==999999&&Math.abs(l.getFloor_alarms())==999999||Math.abs(l.getLimit_alarms())==0&&Math.abs(l.getFloor_alarms())==0||Math.abs(l.getLimit_alarms())==0&&Math.abs(l.getFloor_alarms())==999999||Math.abs(l.getLimit_alarms())==999999&&Math.abs(l.getFloor_alarms())==0){
				l.setLimit_alarm("--/--");
			}else if(Math.abs(l.getLimit_alarms())!=999999&&Math.abs(l.getLimit_alarms())!=0&&Math.abs(l.getFloor_alarms())==999999||Math.abs(l.getLimit_alarms())!=999999&&Math.abs(l.getLimit_alarms())!=0&&Math.abs(l.getFloor_alarms())==0){
				l.setLimit_alarm(l.getLimit_alarms()+"/--");
			}else if(Math.abs(l.getLimit_alarms())==999999&&Math.abs(l.getFloor_alarms())!=999999&&Math.abs(l.getFloor_alarms())!=0||Math.abs(l.getLimit_alarms())==0&&Math.abs(l.getFloor_alarms())!=999999&&Math.abs(l.getFloor_alarms())!=0){
				l.setLimit_alarm("--/"+l.getFloor_alarms());
			}else{
				l.setLimit_alarm(l.getLimit_alarms()+"/"+l.getFloor_alarms());
			}
		}
		return list;
	}
	
	
	public List<SecretData> getall(NameTime nameTime){
		List<SecretData> list = dao.getall(nameTime);
		for(SecretData l:list){
			if(l.getPosition()==null){
				l.setPosition("未配置位置");
			}
			if(Math.abs(l.getLimit_alarms())==999999&&Math.abs(l.getFloor_alarms())==999999||Math.abs(l.getLimit_alarms())==0&&Math.abs(l.getFloor_alarms())==0||Math.abs(l.getLimit_alarms())==0&&Math.abs(l.getFloor_alarms())==999999||Math.abs(l.getLimit_alarms())==999999&&Math.abs(l.getFloor_alarms())==0){
				l.setLimit_alarm("--/--");
			}else if(Math.abs(l.getLimit_alarms())!=999999&&Math.abs(l.getLimit_alarms())!=0&&Math.abs(l.getFloor_alarms())==999999||Math.abs(l.getLimit_alarms())!=999999&&Math.abs(l.getLimit_alarms())!=0&&Math.abs(l.getFloor_alarms())==0){
				l.setLimit_alarm(l.getLimit_alarms()+"/--");
			}else if(Math.abs(l.getLimit_alarms())==999999&&Math.abs(l.getFloor_alarms())!=999999&&Math.abs(l.getFloor_alarms())!=0||Math.abs(l.getLimit_alarms())==0&&Math.abs(l.getFloor_alarms())!=999999&&Math.abs(l.getFloor_alarms())!=0){
				l.setLimit_alarm("--/"+l.getFloor_alarms());
			}else{
				l.setLimit_alarm(l.getLimit_alarms()+"/"+l.getFloor_alarms());
			}
		}
		return list;
	}
	
	public int getcount(NameTime nameTime){
		return dao.getcount(nameTime);
	}
	
	public int getcountdata(NameTime nameTime){
		return dao.getcountdata(nameTime);
	}
	
}
