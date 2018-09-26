package com.cm.dao;

import java.util.List;

import com.cm.entity.vo.AreaPass;
import com.cm.entity.vo.Searchform;

public interface AreaDailyDao {
	//插入日统计数据
	public void addRecs(List<AreaPass> resultList);
	//按searchform中的条件获取数据
	public List<AreaPass> getRecs(Searchform searchform);
	
	public List<AreaPass> getMonthlyData(Searchform searchform);
	
	public List<AreaPass> getAreaWarnData(String stime, String etime);
	
}
