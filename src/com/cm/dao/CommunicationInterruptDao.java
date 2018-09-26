package com.cm.dao;

import java.util.List;

import com.cm.entity.Analoginfo;
import com.cm.entity.vo.CommunicationVo;
import com.cm.entity.vo.NameTime;

public interface CommunicationInterruptDao {
	
	//根据条件分页查询
	public List<CommunicationVo> getbySensor(NameTime nameTime);
	
	public List<CommunicationVo> getbySwitch(NameTime nameTime);
	
	//根据条件查询所有
	public List<Analoginfo> getbySensors(NameTime nameTime);
	
	public int getcount(NameTime nameTime);
	
	public List<Analoginfo> getallbySensor(NameTime nameTime);
	

}
