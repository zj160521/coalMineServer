package com.cm.dao;

import com.cm.entity.SensorLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ISensorLogDao {

	//添加
	public void addSensorLog(SensorLog sensorLog);
	
	//分页查询
	public List<SensorLog> getByPage(@Param("currentPage")int currentPage,@Param("pageSize")int pageSize,@Param("uid")String uid);
	
	//查询总条数
	public Integer getTotalCount(@Param("uid")String uid);
	
	//查询最近的一条记录
	public SensorLog getOrderByTime(@Param("uid")String uid);
}
