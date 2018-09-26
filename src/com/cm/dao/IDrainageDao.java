package com.cm.dao;

import com.cm.entity.Drainage;
import com.cm.entity.Sensor;
import com.cm.entity.SwitchSensor;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IDrainageDao {

	//查询所有抽采参数
	@SuppressWarnings("rawtypes")
	public List<Drainage> getAllDrainage(Drainage drainage);

	public List<Drainage<Drainage<Sensor>>> getAllDrainageParam();
	
	public List<Drainage<Drainage<SwitchSensor>>> getAlldrainageParamSwitchSensor();
	//添加抽采参数
	@SuppressWarnings("rawtypes")
	public void addDrainageParam(Drainage drainage);
	
	//修改抽采参数
	@SuppressWarnings("rawtypes")
	public void updateDrainage(Drainage drainage);
	
	//根据id查询
	@SuppressWarnings("rawtypes")
	public Drainage getById(@Param("id")int id);
	
	//添加传感器
	public void addDrainageSensor(@Param("drainageId")int drainageId,@Param("sensorId")int sensorId);
	
	//验证参数是否已存在
	@SuppressWarnings("rawtypes")
	public int validation(Drainage drainage);
	
	//删除抽采参数
	@SuppressWarnings("rawtypes")
	public void deleteDrainage(Drainage drainage);
	
	//查询传感器的值
	@SuppressWarnings("rawtypes")
	public List<Drainage> getAllSensorData(@Param("id")int id,@Param("starttime")String starttime);
	
	//删除传感器和抽采参数中间表
	public void deleteSensor(@Param("id")int id);
	
	//查询一级分类下面的传感器
	@SuppressWarnings("rawtypes")
	public List<Drainage> getAlloneparam();

	List<Sensor> getAlldrainageCOSensor();

    List<Sensor> getAlldrainageMethaneSensor();
	
}
