package com.cm.service;

import com.cm.dao.AdjoinAreaDao;
import com.cm.dao.IAreaDao;
import com.cm.entity.*;
import com.cm.entity.vo.AreaChangeVo;
import com.cm.entity.vo.AreaListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AreaService {
	
	@Autowired
	private IAreaDao areaDao;

	@Autowired
	private AdjoinAreaDao adjoinAreaDao;

	//查询所有区域相关信息
	public List<Area> getAll(){
		return areaDao.getAll();
	}
	
	//添加区域相关信息
	public int addArea(Area area){
		//添加区域信息到表
		return areaDao.addArea(area);
		
	}

	//黑白名单
    @Transactional
	public void areaWorkerOperation(Area area){
        if (null != area.getWorkers() && !area.getWorkers().isEmpty()){
            areaDao.deleteAreaWorker(area.getId());
            areaDao.addAreaWorker(area.getId(), area.getWorkers());
        }
    }
	
	public List<Sensor> getUnuseSensors(){
		return areaDao.getUnuseSensor();
	}
	
	public void updateAdjoin(String adjoin,int id){
		areaDao.updateAdjoin(adjoin,id);
	}
	
	public List<Area> getAreaByadjoin(String adjoin){
		return areaDao.getAreaByAdjoin(adjoin);
	}
	
	public void updateSwitchSensor(int area_id,int id){
		areaDao.updateSwitchSensor(area_id, id);
	}
	
	public void updateSensor(int area_id,int id){
		areaDao.updateSensor(area_id, id);
	}
	
	public void updateSwitchSensorAreaidIsNull(int id){
		areaDao.updateSwitchSensorAreaidIsNull(id);
	}
	
	public void updateSensorAreaidIsNull(int id){
		areaDao.updateSensorAreaidIsNull(id);
	}
	
	public List<Sensor> getSensorByAreaid(int area_id){
		return areaDao.getSensorByAreaid(area_id);
	}
	
	//获得新添加数据的id
	public int getId(Area area){
		return areaDao.getId(area);
	}
	
	public int addArea2(Area area){
		return areaDao.addArea(area);
	}
	
	public void updateArea2(Area area){
		areaDao.updateArea(area);
	}
	
	public void deleteArea2(int id){
		areaDao.deleteArea(id);
	}
	
	//更新区域相关信息
	public void updateArea(Area area){
		//更新区域信息
		areaDao.updateArea(area);
	}
	
	//删除area_worker中间表相关信息
	public void deleteAreaWorker(int id){
		areaDao.deleteAreaWorker(id);
	}
	
	//添加area_worker中间表相关信息
	public void addAreaWorker(int id,List<Worker> list){
		areaDao.addAreaWorker(id, list);
	}
	
	//设置cardreader的areaid为null
	public void updateByAreaid(int id){
		areaDao.updateByAreaid(id);
	}
	
	//更新cardreader表
	public void updateCardreder(int area_id,List<Cardreder> list){
        areaDao.updateCardreder(area_id, list);
	}
	
	//删除区域相关信息
    @Transactional
	public void deleteArea(int areaid){
        List<Sensor> list2 = areaDao.getSensorByAreaid(areaid);
        for (Sensor sensor : list2) {
            if(sensor.getPid()==100){
                areaDao.updateSensorAreaidIsNull(sensor.getId());
            }
            if(sensor.getPid()==25){
                areaDao.updateSwitchSensorAreaidIsNull(sensor.getId());
            }
        }
		//删除区域信息
		areaDao.deleteArea(areaid);
		//删除area_worker中间表相关信息
        areaDao.deleteAreaWorker(areaid);
        //设置cardreder的areaid为null
        areaDao.updateByAreaid(areaid);
        //删除相邻区域关系表
        adjoinAreaDao.deleteAdjoinArea(areaid);
	}
	
	//查询未使用的读卡器信息
	public List<Cardreder> getCardreder(Integer areaid){
		return areaDao.getCardreder(areaid);
	}
	
	public Area2 getDefaultArea(){
		return areaDao.getDefaultArea();
	}
	
	public void updateDefaultArea(Area area){
		areaDao.updateArea(area);
	}
	
	public Area getByid(int id){
		return areaDao.getByid(id);
	}
	
	public Area2 getById(int id){
		return areaDao.getById(id);
	}
	
	public List<Area> getAllArea(){
		return areaDao.getAllArea();
	}
	
	public void insertAreaConfig(List<AreaChangeVo> list){
		areaDao.insertAreaConfig(list);
	}
	
	public AreaChangeVo getAreaChange(String day){
		try {
			return areaDao.getAreaChange(day);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<AreaChangeVo> getAreaMSG(String day){
		try {
			return areaDao.getAreaMSG(day);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@SuppressWarnings("rawtypes")
	public List<AreaListVo> getallarealist(){
		return areaDao.getallarealist();
	}
}
