package com.cm.dao;

import java.util.List;

import com.cm.entity.vo.AnaloginfoQuery;
import com.cm.entity.vo.NameTime;
import com.cm.entity.vo.SwitchVo;

public interface SwitchStatementDao {
	
	public List<SwitchVo> getAna(NameTime nameTime);
	
	public List<AnaloginfoQuery> getTime(NameTime nameTime);
}
