package com.cm.dao;

import com.cm.entity.Line;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LineDao {
    void add(Line line);

    void update(Line line);

    List<Line> getAll();

    void delete(@Param("id") int id);

    List<Line> getByType(@Param("type") int type);
}
