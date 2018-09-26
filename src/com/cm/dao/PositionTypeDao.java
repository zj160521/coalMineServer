package com.cm.dao;

import com.cm.entity.PositionType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PositionTypeDao {
    List<PositionType> getAll();

    void add(PositionType positionType);

    void update(PositionType positionType);

    void delete(@Param("id") int id);

    PositionType getById(@Param("id") int id);

    PositionType getByName(@Param("name") String name);
}
