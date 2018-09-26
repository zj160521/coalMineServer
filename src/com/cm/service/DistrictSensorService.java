package com.cm.service;

import com.cm.dao.IDistrictSeneorDao;
import com.cm.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DistrictSensorService{

	@Autowired
	private IDistrictSeneorDao dao;

	public void updateSensor(Sensor sensor) {
		dao.updateSensor(sensor);
	}

	public void updateSwitchSensor(SwitchSensor ss) {
		dao.updateSwitchSensor(ss);
	}

	public void updateCardreader(Cardreder c) {
		dao.updateCardreader(c);
	}

	public void updateRadio(Radio r) {
		dao.updateRadio(r);
	}

	public void updateVideo(Video v) {
		dao.updateVideo(v);
	}

	public void updateSubstation(Substation substation){
	    dao.updateSubstation(substation);
    }

    public void updateEquipments(Equipments equipments){
	    dao.updateEquipment(equipments);
    }

}
