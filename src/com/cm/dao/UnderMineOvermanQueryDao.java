package com.cm.dao;

import java.util.List;

import com.cm.entity.vo.AreaOvertimeVo;
import com.cm.entity.vo.Searchform;

public interface UnderMineOvermanQueryDao {

	public List<AreaOvertimeVo> getData(Searchform searchform);
	
	public int getMaxAllowPerson();
}
