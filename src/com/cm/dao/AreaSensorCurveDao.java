package com.cm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cm.entity.vo.AnalogQueryVo;
import com.cm.entity.vo.AreaSensorVo;
import com.cm.entity.vo.SensorRecsVo;

public interface AreaSensorCurveDao {
	
    public List<AreaSensorVo> getAllSensorByArea(@Param("areaId")int areaId);
    
    public List<SensorRecsVo> getSensorRecs(List<AreaSensorVo> list,@Param("startTime")String startTime);
    
    public List<AnalogQueryVo> getAlarmRecs(List<AreaSensorVo> list,@Param("startTime")String startTime,@Param("endTime")String endTime);
    
    public List<SensorRecsVo> getDailySensorRecs(List<AreaSensorVo> list,@Param("startTime")String startTime);
}
