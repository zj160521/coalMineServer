package com.cm.service;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cm.dao.StrategyDao;
import com.cm.entity.AreaAttrib;
import com.cm.entity.AreaSensor;
import com.cm.entity.AreaSensor2;
import com.cm.entity.PositionStrategy2;

@Service("stategyService")
public class StategyService {

	@Autowired
	private StrategyDao dao;
	
	public List<PositionStrategy2> getStrategy(PositionStrategy2 ps){
		return dao.getStrategy(ps);
	}

	public void addAreaPos(List<PositionStrategy2> list) {
		dao.addAreaPos(list);
	}

	public List<AreaSensor2> getAreaSensor(AreaSensor as) {
		return dao.getAreaSensor(as);
	}

	@Transactional(rollbackFor=RuntimeException.class)
	public void addAreaSen(AreaSensor as,List<LinkedHashMap<String, Object>> list) {
		try {
			dao.delAreaSensor(as);
			dao.addAreaSen(list);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	public void addAreaSenOne(AreaSensor as) {
		dao.addAreaSenOne(as);
	}

	public List<AreaSensor2> getAllAreaSensor(AreaSensor as) {
		return dao.getAllAreaSensor(as);
	}

	public void delAreaSensor(AreaSensor as) {
		dao.delAreaSensor(as);
	}

	public List<AreaAttrib> getAreaAttrib(AreaAttrib ab) {
		return dao.getAreaAttrib(ab);
	}

	@Transactional(rollbackFor=RuntimeException.class)
	public void addAreaAttrib(AreaAttrib ab,List<LinkedHashMap<String, Object>> list) {
		try {
			dao.delAreaAttrib(ab);
			dao.addAreaAttrib(list);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	public void delAreaAttrib(AreaAttrib ab) {
		dao.delAreaAttrib(ab);
	}
	
	@Transactional(rollbackFor=RuntimeException.class)
	public void delStrategy(PositionStrategy2 ps){
		if(ps.getType_id()==0){
			try {
				dao.delStrategy(ps);
				dao.delStrategy2(ps);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException();
			}
		}
	}
	
	public List<AreaSensor> getTAreaSensor(){
		return dao.getTAreaSensor();
	}
}
