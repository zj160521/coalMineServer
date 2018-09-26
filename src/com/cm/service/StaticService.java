package com.cm.service;

import com.cm.dao.IStaticDao;
import com.cm.entity.Static;
import com.cm.entity.vo.SensorTypeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("staticService")
public class StaticService {

	@Autowired
	private IStaticDao staticDao;
	
	//查询所有模拟量传感器设备类型
	@SuppressWarnings("rawtypes")
	public List<Static> getAllSensorType(){
		return staticDao.getAllSensorType();
	}
	
	//查询所有开关量传感器类型
	@SuppressWarnings("rawtypes")
	public List<Static> getAllSwitchSensorType(){
		return staticDao.getAllSwitchSensorType();
	}
	
	//查询所有传感器单位
	@SuppressWarnings("rawtypes")
	public List<Static> getAllSensorUnit(){
		return staticDao.getAllSensorUnit();
	}
	
	//查询所有传感器位置信息
	@SuppressWarnings("rawtypes")
	public List<Static> getAllSensorPosition(){
		return staticDao.getAllSensorPosition();
	}
	
	//查询所有显示信息
	@SuppressWarnings("rawtypes")
	public List<Static> getAlldisplay(){
		return staticDao.getAlldisplay();
	}
	
	//添加并返回位置信息
	@SuppressWarnings("rawtypes")
	public Static addPosition(String position){
		staticDao.addPosition(position);
		return getByPosition(position);
	}
	
	//查询位置信息是否存在
	@SuppressWarnings("rawtypes")
	public Static getByPosition(String position){
		return staticDao.getByV(position);
		
	}
	//根据id查询位置信息
	@SuppressWarnings("rawtypes")
	public Static getPositionByid(int id){
		return staticDao.getPositionByid(id);
	}
	
	//
	public String getK(int id){
		return staticDao.getK(id);
	}
	
	//查询所有传感器设备类型
	@SuppressWarnings("rawtypes")
	public List<Static> getAllType(){
		return staticDao.getAllType();
	}
	
	//查询所有传感器类型下的具体模拟量传感器
	public List<SensorTypeVO> getAllSensorOfType(){
		return staticDao.getAllSensorOfType();
	}
	
	//查询所有传感器类型下的具体开关量传感器
	public List<SensorTypeVO> getAllSwitchSensorOfType(){
		return staticDao.getAllSwitchSensorOfType();
	}
	
	//数据字典
	@SuppressWarnings("rawtypes")
	public List<Static<Static>> getAllDataMap(){
		return staticDao.getAllDataMap();
	}
	
	//添加数据字典
	@SuppressWarnings("rawtypes")
	public void addDataMap(Static sta){
		staticDao.addDataMap(sta);
	}
	
	//按传感器类型查询瓦斯抽放系统的传感器
	public List<SensorTypeVO> getAllDrainageSensor(){
		return staticDao.getAllDrainageSensor();
	}
	
	//根据id删除
	public void delete(int id){
		staticDao.delete(id);
	}
	
	//更新
	@SuppressWarnings("rawtypes")
	public void update(Static sta){
		staticDao.update(sta);
	}
	
	public String getV(int id){
		return staticDao.getV(id);
	}
	
	//查询所有工作地点
	@SuppressWarnings("rawtypes")
	public List<Static> getallWorkplace(){
		return staticDao.getallWorkplace();
	}
	
	//查询所有的职务
	@SuppressWarnings("rawtypes")
	public List<Static> getallduty(){
		return staticDao.getallduty();
	}
	
	@SuppressWarnings("rawtypes")
	public List<Static> addworkplace(String workplace){
		staticDao.addWorkplace(workplace);
		return staticDao.getWorkpalce(workplace); 
	}
	
	@SuppressWarnings("rawtypes")
	public List<Static> addDuty(String duty){
		staticDao.addDuty(duty);
		return staticDao.getDuty(duty);
	}
	@SuppressWarnings("rawtypes")
	public List<Static> getDutyByDuty(String duty){
		return staticDao.getDuty(duty);
	}
	@SuppressWarnings("rawtypes")
	public List<Static> getWorkplacebyWorkplace(String workplace){
		return staticDao.getWorkpalce(workplace);
	}
	
	public List<Integer> getByPid(int pid){
		try {
			return staticDao.getByPid(pid);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void addK(Static sta){
	    staticDao.addK(sta);
    }

    public List<Static> getAllAreaType(){
	    return staticDao.getAllAreaType();
    }

    public List<Static> getAllRadio(){
	    return staticDao.getAllRadio();
    }
}


