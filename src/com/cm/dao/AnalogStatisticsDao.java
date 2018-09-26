package com.cm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cm.entity.AnalogStatistics;
import com.cm.entity.vo.NameTime;

public interface AnalogStatisticsDao {
	

	//1小时统计值查询
	public List<AnalogStatistics> getbyonehour(@Param("tablename")String tablename,@Param("time")String time);
	
	//8小时统计值查询
	public List<AnalogStatistics> getbyeighthour(@Param("tablename")String tablename,@Param("time")String time);
	
	//1天统计值查询
	public List<AnalogStatistics> getbyday(@Param("tablename")String tablename,@Param("time")String time);
	
	//向数据库写入数据
	public void addAnalogStatistics(@Param("list") List<AnalogStatistics> list,@Param("tablename")String tablename);
	
	//模拟量统计值查询
	public List<AnalogStatistics> getall(NameTime nameTime);
	
	//模拟量统计值查询计数
	public int getcount(NameTime nameTime);
	

}
