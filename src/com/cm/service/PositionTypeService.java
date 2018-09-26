package com.cm.service;

import com.cm.dao.PositionTypeDao;
import com.cm.entity.PositionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PositionTypeService {

    @Autowired
    private PositionTypeDao typeDao;

    public List<PositionType> getAll(){
        return typeDao.getAll();
    }

    public void add(PositionType positionType){
        typeDao.add(positionType);
    }

    public void update(PositionType positionType){
        typeDao.update(positionType);
    }

    public void delete(int id){
        typeDao.delete(id);
    }

    public PositionType getById(int id){
        return typeDao.getById(id);
    }

    public PositionType getByName(String name){
        return typeDao.getByName(name);
    }
}
