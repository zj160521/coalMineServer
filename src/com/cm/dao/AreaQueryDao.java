package com.cm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cm.entity.Area;

public interface AreaQueryDao {

	//查询重点区域
	public List<Area> EmphasisArea(@Param("emphasis")int emphasis);
	
	//查询普通区域
	public List<Area> GeneralArea(@Param("emphasis")int emphasis);
	
	//查询限制区域
	public List<Area> AstrictArea(@Param("default_allow")int default_allow);
}
