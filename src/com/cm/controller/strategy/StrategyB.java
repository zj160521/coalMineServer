package com.cm.controller.strategy;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cm.entity.AreaSensor;
import com.cm.entity.AreaSensor2;
import com.cm.service.StategyService;


@Component
public class StrategyB implements Strategy {

	@Autowired
	private StategyService service;
	
	@Override
	public Object dofilter(Object o) {
		AreaSensor as=(AreaSensor) o;
		List<AreaSensor2> list=service.getAllAreaSensor(as);
		return list;
	}

	@Override
	public void addAreaCon(Object ps) {
//		service.addAreaSenOne((AreaSensor)ps);
//		service.addAreaSen((List<LinkedHashMap<String, Object>>) ps);
	}

	@Override
	public void delAreaCon(Object o) {
		service.delAreaSensor((AreaSensor) o);
	}

}
