package com.cm.dao;

import com.cm.entity.*;

public interface IDistrictSeneorDao {
	public void updateSensor(Sensor sensor);
	public void updateSwitchSensor(SwitchSensor ss);
	public void updateCardreader(Cardreder c);
	public void updateRadio(Radio r);
	public void updateVideo(Video v);
	public void updateSubstation(Substation substation);
	public void updateEquipment(Equipments equipments);
}
