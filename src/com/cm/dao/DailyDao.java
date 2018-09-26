package com.cm.dao;

import java.util.List;

import com.cm.entity.vo.DailyRecVo;
import com.cm.entity.vo.Searchform;

public interface DailyDao {
	public void addRecs(List<DailyRecVo> resultList);
	public List<DailyRecVo> getRecs(Searchform searchform);
	public List<DailyRecVo> getMonthlyData(Searchform searchform);
}
