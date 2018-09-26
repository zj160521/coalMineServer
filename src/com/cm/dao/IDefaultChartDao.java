package com.cm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cm.entity.DefaultChart;

public interface IDefaultChartDao {
	
	public DefaultChart getById(@Param("module_id")int module_id);
	
	public void add(DefaultChart chart);
	
	public void update(DefaultChart chart);
	
	public List<DefaultChart> getAll(@Param("pid")int pid);
	
	public void batchadd(DefaultChart chart);
	
	public void deleteByPid(@Param("pid")int pid);

}
