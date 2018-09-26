package com.cm.service;

import com.cm.dao.CumulantDao;
import com.cm.entity.Cumulant;
import com.cm.entity.GD5Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CumulanService {

    @Autowired
    private CumulantDao cumulantDao;

    public void batchAdd(List<Cumulant> list){
        cumulantDao.batchAdd(list);
    }

    public void batchUpdate(List<Cumulant> list){
        cumulantDao.batchUpdate(list);
    }

    public List<Cumulant> getAllCumulant(int sensor_position){
        return cumulantDao.getAllCumulant(sensor_position);
    }

    public List<Cumulant> getAllTotalCumulant(){
        return cumulantDao.getAllTotalCumulant();
    }

    public List<GD5Report> getBasedata(String tableName,String starttime,String endtime){
        return cumulantDao.getBasedata(tableName,starttime, endtime);
    }

    public List<Cumulant> getAllYearCumulant(){
        return cumulantDao.getAllYearCumulant();
    }

    public List<Cumulant> getAllMonthCumulant(){
        return cumulantDao.getAllMonthCumulant();
    }

    public List<Cumulant> getAllDayCumulant(){
        return cumulantDao.getAllDayCumulant();
    }

    public void add(Cumulant cumulant){
        cumulantDao.add(cumulant);
    }
}
