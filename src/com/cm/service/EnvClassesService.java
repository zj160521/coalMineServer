package com.cm.service;

import com.cm.dao.EnvclassesDao;
import com.cm.entity.EnvClasses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnvClassesService {

    @Autowired
    private EnvclassesDao envclassesDao;

    public void add(EnvClasses classes){
        envclassesDao.add(classes);
    }

    public void update(EnvClasses classes){
        envclassesDao.update(classes);
    }

    public List<EnvClasses> getAll(){
        return envclassesDao.getAll();
    }

    public void delete(){
        envclassesDao.delete();
    }

    public EnvClasses getById(int id){
        return envclassesDao.getById(id);
    }

    public void batchadd(List<EnvClasses> list){
        envclassesDao.batchadd(list);
    }
}
