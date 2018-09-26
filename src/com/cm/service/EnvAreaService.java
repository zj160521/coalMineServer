package com.cm.service;

import com.cm.dao.EnvAreaDao;
import com.cm.dao.StrategyDao;
import com.cm.entity.AreaAttrib;
import com.cm.entity.EnvArea;
import com.cm.entity.Sensor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EnvAreaService {

    @Autowired
    private EnvAreaDao areaDao;
    @Autowired
    private StrategyDao sDao;

    public List<EnvArea> getAll(){
        return areaDao.getAll();
    }

    public void add(EnvArea area){
        areaDao.add(area);
    }

    public void update(EnvArea area){
        areaDao.updateArea(area);
    }

    @Transactional
    public void delete(int areaid){
        areaDao.deleteArea(areaid);
        AreaAttrib aa=new AreaAttrib();
        aa.setArea_id(areaid);
        sDao.delAreaAttrib(aa);
    }

    public List<Sensor> getSensorByAreaid(int areaid){
        return areaDao.getSensorByAreaid(areaid);
    }

    public EnvArea getByid(int area_id){
        return areaDao.getByid(area_id);
    }
}
