package com.cm.dao;

import com.cm.entity.EnvClasses;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EnvclassesDao {

    void add(EnvClasses classes);

    void update(EnvClasses classes);

    List<EnvClasses> getAll();
    
    void delete();

    EnvClasses getById(@Param("id") int id);

    void batchadd(@Param("list") List<EnvClasses> list);
    
}
