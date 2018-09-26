package com.cm.dao;

import java.util.LinkedHashMap;
import java.util.List;

import com.cm.entity.AreaAttrib;
import com.cm.entity.AreaSensor;
import com.cm.entity.AreaSensor2;
import com.cm.entity.PositionStrategy2;

public interface StrategyDao {
	public List<PositionStrategy2> getStrategy(PositionStrategy2 ps);
	public void addAreaPos(List<PositionStrategy2> list);
	public List<AreaSensor2> getAreaSensor(AreaSensor as);
	public List<AreaSensor2> getAllAreaSensor(AreaSensor as);
	public void addAreaSen(List<LinkedHashMap<String, Object>> list);
	public void addAreaSenOne(AreaSensor as);
	public void delAreaSensor(AreaSensor as);
	public List<AreaAttrib> getAreaAttrib(AreaAttrib ab);
	public void addAreaAttrib(List<LinkedHashMap<String, Object>> list);
	public void delAreaAttrib(AreaAttrib ab);
	public List<AreaSensor> getAS(AreaSensor as);
	public void delStrategy(PositionStrategy2 ps);
	public void delStrategy2(PositionStrategy2 ps);
	public List<AreaSensor> getTAreaSensor();
}
