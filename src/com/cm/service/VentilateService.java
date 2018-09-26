package com.cm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cm.dao.VentilateDao;
import com.cm.entity.Sensor;
import com.cm.entity.SwitchSensor;

@Service
public class VentilateService {

	@Autowired
	private VentilateDao dao;
	
	public List<Sensor> getAllVentilateSensor(){
		List<Sensor> list = dao.getAllVentilateSensor();
		for(Sensor l:list){
			if(l.getPosition()==null){
				l.setPosition("未配置位置");
			}
		}
		return list;
	}
	
	public List<SwitchSensor> getAllVentilateSwitchSensor(){
		List<SwitchSensor> list =dao.getAllVentilateSwitchSensor();
		for(SwitchSensor l:list){
			if(l.getPosition()==null){
				l.setPosition("未配置位置");
			}
		}
		return list;
	}
}
