package com.cm.dao;

import com.cm.entity.Pageeditor;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PagaeeditorDao {

    void add(Pageeditor editor);

    List<Pageeditor> getAll();

    void update(Pageeditor editor);

    Pageeditor getByTypeAndName(@Param("type") String type);

    void delelte(@Param("id") int id);
}
