package com.cm.dao;

import java.util.List;

import com.cm.entity.vo.CommunicationVo;
import com.cm.entity.vo.NameTime;

public interface EquipmentErrorDao {
	
	
	public List<CommunicationVo> getSwitchSensor(NameTime nameTime);
	
	public List<CommunicationVo> getSensor(NameTime nameTime);
	
	public List<CommunicationVo> getCardreder(NameTime nameTime);
	
	public List<CommunicationVo> getSubstation(NameTime nameTime);
	
	public List<CommunicationVo> getEquipment(NameTime nameTime);
	
	public List<CommunicationVo> getRadio(NameTime nameTime);

}
