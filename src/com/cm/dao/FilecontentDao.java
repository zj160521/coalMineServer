package com.cm.dao;

import com.cm.entity.Filecontent;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FilecontentDao {

    void add(Filecontent filecontent);

    List<Filecontent> getAll();

    void delete(@Param("id")int id);

    void update(@Param("id") int id);
}
