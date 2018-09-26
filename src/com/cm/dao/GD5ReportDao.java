package com.cm.dao;

import com.cm.entity.GD5Report;
import com.cm.entity.Sensor;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GD5ReportDao {

	public List<GD5Report> getAll(@Param("startime")String startime,@Param("endtime")String endtime);
	
	public List<GD5Report> getAllByTime(@Param("startime")String startime,@Param("endtime")String endtime,@Param("tableName")String tableName);
	
	public String getSensorPosition(@Param("station")int station,@Param("sensorId")int sensorId);
	
	public List<Sensor> getAllSensorPosition();

	public void addGD5Statistics(@Param("list") List<GD5Report> list);
}
