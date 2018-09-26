package com.cm.dao;

import com.cm.entity.Area;
import com.cm.entity.AreaSensor;
import com.cm.entity.Sensor;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdjoinAreaDao {

    //添加两个区域的相邻关系到关系表
    void addAdjoinArea(List<Area> list);

    //添加两个区域的另一种相邻关系到关系表
    void addAdjoinArea2(List<Area> list);

    //删除两个相邻的关系
    void deleteAdjoinArea(@Param("area_id")int area_id);

    List<Sensor> getSensorByAreaid(@Param("m_area_id")int m_area_id, @Param("s_area_id")int s_area_id);

    List<Sensor> getSensorByAdjoinArea(@Param("list") List<Integer> list);

    List<AreaSensor> getAreaSensorByIds(@Param("list") List<Sensor> list);

    void deleteAreaneighbor(@Param("list") List<AreaSensor> list);

    void addAreaneighborSensor(@Param("list") List<AreaSensor> list);

    List<Sensor> getAllAreaSensor();

    void addNeighborSensor(@Param("list") List<Sensor> list);

    void deleteNeighborArea(@Param("area_id") int area_id);
}
