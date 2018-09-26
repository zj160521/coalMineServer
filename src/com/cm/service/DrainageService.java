package com.cm.service;

import com.cm.dao.IBaseinfoDao;
import com.cm.dao.IDrainageDao;
import com.cm.dao.ISwitchDao;
import com.cm.entity.Drainage;
import com.cm.entity.Sensor;
import com.cm.entity.SwitchSensor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DrainageService {

	@Autowired
	private IDrainageDao drainageDao;
	
	@Autowired
	private IBaseinfoDao baseinfoDao;
	
	@Autowired
	private ISwitchDao switchDao;
	
	//查询所有抽采参数
	@SuppressWarnings("rawtypes")
	public List<Drainage> getAllDrainage(Drainage drainage){
		return drainageDao.getAllDrainage(drainage);
	}
	public List<Drainage<Drainage<Sensor>>> getAllDrainageParam(){
		return drainageDao.getAllDrainageParam();

	}
	
	//添加抽采参数
	@SuppressWarnings("rawtypes")
	public void addDrainageParam(Drainage drainage){
		drainageDao.addDrainageParam(drainage);
	}
	
	//修改抽采参数
	@SuppressWarnings("rawtypes")
	public void updateDrainage(Drainage drainage){
		drainageDao.updateDrainage(drainage);
	}
	
	//根据id查询
	@SuppressWarnings("rawtypes")
	public Drainage getById(int id){
		return drainageDao.getById(id);
	}
	
	//添加传感器
	public void addDrainageSensor(int drainageId,int sensorId){
		drainageDao.addDrainageSensor(drainageId, sensorId);
	}
	
	//验证参数是否已存在
	@SuppressWarnings("rawtypes")
	public int validation(Drainage drainage){
		return drainageDao.validation(drainage);
	}
	
	@SuppressWarnings("rawtypes")
	public void deleteDrainage(Drainage drainage){
		drainageDao.deleteDrainage(drainage);
	}
	
	//查询传感器的值
	@SuppressWarnings("rawtypes")
	public List<Drainage> getAllSensorData(int id,String starttime){
		return drainageDao.getAllSensorData(id,starttime);
	}
	
	//删除瓦斯抽放系统的模拟量传感器
	@Transactional
	public void deleteSensor(int id){
		drainageDao.deleteSensor(id);
		baseinfoDao.deleteSensor(id);
	}
	
	public void deleteDrainageSensor(int id){
		drainageDao.deleteSensor(id);
	}
	
	//查询一级分类下面的传感器
	public List<Drainage> getAlloneparam(){
		return drainageDao.getAlloneparam();
	}
	
	public List<Drainage<Drainage<SwitchSensor>>> getAlldrainageParamSwitchSensor(){
		return drainageDao.getAlldrainageParamSwitchSensor();
	}
	
	//删除瓦斯抽放系统的开关量传感器
	@Transactional
	public void deleteSwitch(int id){
		drainageDao.deleteSensor(id);
		switchDao.delete(id);
	}

	public List<Sensor> getAlldrainageCOSensor(){
	    return drainageDao.getAlldrainageCOSensor();
    }

    public List<Sensor> getAlldrainageMethaneSensor(){
	    return drainageDao.getAlldrainageMethaneSensor();
    }
}
