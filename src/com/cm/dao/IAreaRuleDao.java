package com.cm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cm.entity.AreaRule;

public interface IAreaRuleDao {
	public void addAreaRules(List<AreaRule> list);
	public void updateAreaRules(List<AreaRule> list);
	public List<AreaRule> getAreaRuleByAreaId(@Param("areaId")int areaId);
	public void deleteAreaRules(List<Integer> list);
	public List<AreaRule> getAllAreaRules();
}
