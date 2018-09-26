package com.cm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cm.entity.SensorReport;
import com.cm.entity.vo.SensorReportVo;


public interface CalculateReportDao {
	
	public List<SensorReportVo> getallbyday(@Param("tablename")String tablename);
	
	public void addReportbyday(@Param("list") List<SensorReport> list);
	
	public int isexisting(SensorReport report);
	

}
