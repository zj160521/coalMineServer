package com.cm.dao;

import java.util.List;

import com.cm.entity.vo.AreaLimitTimeVo;
import com.cm.entity.vo.AreaOfflimitsVo;
import com.cm.entity.vo.Searchform;

public interface AreaOvertimeQueryDao {

	public List<AreaOfflimitsVo> getData(Searchform searchform);
	public List<AreaLimitTimeVo> getAreaLimitTime();
	public List<AreaOfflimitsVo> getBeforeData(Searchform searchform);
}
