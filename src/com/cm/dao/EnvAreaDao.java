package com.cm.dao;

import com.cm.entity.EnvArea;
import com.cm.entity.Sensor;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EnvAreaDao {

    void add(EnvArea area);

    List<EnvArea> getAll();

    void updateArea(EnvArea area);

    void deleteArea(@Param("areaid")int areaid);

    List<Sensor> getSensorByAreaid(@Param("area_id")int area_id);

    EnvArea getByid(@Param("id")int id);

}
