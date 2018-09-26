package com.cm.dao;

import com.cm.entity.Cumulant;
import com.cm.entity.GD5Report;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CumulantDao {

    void batchAdd(List<Cumulant> list);

    void batchUpdate(List<Cumulant> list);

    List<Cumulant> getAllCumulant(@Param("sensor_position")int sensor_position);

    List<GD5Report> getBasedata(@Param("tableName")String tableName,@Param("starttime")String starttime,@Param("endtime")String endtime);

    List<Cumulant> getAllTotalCumulant();

    List<Cumulant> getAllYearCumulant();

    List<Cumulant> getAllMonthCumulant();

    List<Cumulant> getAllDayCumulant();

    void add(Cumulant cumulant);
}
