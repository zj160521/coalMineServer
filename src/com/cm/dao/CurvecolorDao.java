package com.cm.dao;

import com.cm.entity.CurveColor;
import org.apache.ibatis.annotations.Param;

public interface CurvecolorDao {
    void add(CurveColor color);

    void update(CurveColor color);

    CurveColor getAll();

    void delete(@Param("id") int id);
}
