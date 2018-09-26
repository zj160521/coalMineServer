package com.cm.dao;

import com.cm.entity.AreaType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AreaTypeDao {
    List<AreaType> getAllAreaType();

    void add(AreaType areaType);

    void update(AreaType areaType);

    void delete(@Param("id") int id);

    AreaType getById(@Param("id")int id);

    AreaType getByName(@Param("name") String name);
}
