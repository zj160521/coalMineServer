package com.cm.dao;

import java.util.List;

import com.cm.entity.Sensor;
import com.cm.entity.SwitchSensor;

public interface VentilateDao {

	public List<Sensor> getAllVentilateSensor();
	
	public List<SwitchSensor> getAllVentilateSwitchSensor();
}
