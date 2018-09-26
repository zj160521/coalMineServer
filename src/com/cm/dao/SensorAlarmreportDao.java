package com.cm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cm.entity.SensorAlarmreport;

public interface SensorAlarmreportDao {
	
	public void addAlarmreport(@Param("list")List<SensorAlarmreport> list);
	
	public void addPowerReport(@Param("list")List<SensorAlarmreport> list);
	
	public void addRepowerReport(@Param("list")List<SensorAlarmreport> list);
	
	public int isexisting(SensorAlarmreport alarmreport);
}
