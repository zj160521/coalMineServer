package com.cm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cm.dao.AnalogMeasureDao;
import com.cm.entity.Measure;

@Service("analogMeasureService")
public class AnalogMeasureService {
	
	@Autowired
	private AnalogMeasureDao dao;
	
	public void add(Measure measure){
		dao.add(measure);
	}
}
