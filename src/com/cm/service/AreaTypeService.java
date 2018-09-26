package com.cm.service;

import com.cm.dao.AreaTypeDao;
import com.cm.entity.AreaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaTypeService {

    @Autowired
    private AreaTypeDao areaTypeDao;

    public List<AreaType> getAllAreaType(){
        return areaTypeDao.getAllAreaType();
    }

    public void add(AreaType areaType){
        areaTypeDao.add(areaType);
    }

    public void update(AreaType areaType){
        areaTypeDao.update(areaType);
    }

    public void delete(int id){
        areaTypeDao.delete(id);
    }

    public AreaType getById(int id){
        return areaTypeDao.getById(id);
    }

    public AreaType getByName(String name){
        return areaTypeDao.getByName(name);
    }
}
