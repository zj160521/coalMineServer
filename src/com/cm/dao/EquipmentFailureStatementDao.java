package com.cm.dao;

import java.util.List;



import com.cm.entity.vo.CommunicationVo;
import com.cm.entity.vo.CommunicationmentVo;
import com.cm.entity.vo.NameTime;

public interface EquipmentFailureStatementDao {
		
	public List<CommunicationmentVo> getAna(NameTime nameTime);
	
	public List<CommunicationVo> getTime(NameTime nameTime);
	
	public List<CommunicationmentVo> getbySwitch(NameTime nameTime);

}
