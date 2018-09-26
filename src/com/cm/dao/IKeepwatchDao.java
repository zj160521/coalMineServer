package com.cm.dao;

import java.util.List;

import com.cm.entity.vo.Keepwatch;
import com.cm.entity.vo.KeepwatchVO;

public interface IKeepwatchDao {
	
	public List<Keepwatch> getAllKeepwatchRecord(KeepwatchVO keepwatch);

}
