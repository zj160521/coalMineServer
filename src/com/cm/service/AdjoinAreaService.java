package com.cm.service;

import com.cm.dao.AdjoinAreaDao;
import com.cm.entity.Area;
import com.cm.entity.AreaSensor;
import com.cm.entity.Sensor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdjoinAreaService {

    @Autowired
    private AdjoinAreaDao areaDao;

    //添加两个区域的相邻关系
    @Transactional
    public void addAdjoinArea(List<Area> areas){
        areaDao.addAdjoinArea(areas);
        areaDao.addAdjoinArea2(areas);
    }

    //更新两个区域的相邻关系
    @Transactional
    public void updateAdjoinArea(List<Area> areas){
        areaDao.deleteAdjoinArea(areas.get(0).getTypeid());
        areaDao.addAdjoinArea(areas);
        areaDao.addAdjoinArea2(areas);
    }

    public List<Sensor> getSensorByAreaid(int m_area_id,int s_area_id){
        return areaDao.getSensorByAreaid(m_area_id, s_area_id);
    }

    public List<Sensor> getSensorByAdjoinArea(List<Integer> adjoinAreaIds){
        return areaDao.getSensorByAdjoinArea(adjoinAreaIds);
    }

    public void deleteAdjoinArea(int area_id){
        areaDao.deleteAdjoinArea(area_id);
    }

    public List<AreaSensor> getAreaSensorByIds(List<Sensor> list){
        return areaDao.getAreaSensorByIds(list);
    }

    @Transactional
    public void updateAreaneighborOperation(List<AreaSensor> list){
        areaDao.deleteAreaneighbor(list);
        areaDao.addAreaneighborSensor(list);
    }

    public List<Sensor> getAllAreaSensor(){
        return areaDao.getAllAreaSensor();
    }

    public void deleteAreaneighbor(List<AreaSensor> list){
        areaDao.deleteAreaneighbor(list);
    }

    public void addNeighborSensor(List<Sensor> list){
        areaDao.addNeighborSensor(list);
    }

    public void deleteNeighborArea(int area_id){
        areaDao.deleteNeighborArea(area_id);
    }


}
