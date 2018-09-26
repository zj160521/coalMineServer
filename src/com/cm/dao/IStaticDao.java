package com.cm.dao;

import com.cm.entity.Action;
import com.cm.entity.Static;
import com.cm.entity.vo.SensorTypeVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IStaticDao {
	
	//查询所有模拟量传感器类型
	@SuppressWarnings("rawtypes")
	public List<Static> getAllSensorType();
	
	//查询所有模拟量传感器类型
	@SuppressWarnings("rawtypes")
	public List<Static> getAllSwitchSensorType();
	
	//查询所有传感器单位
	@SuppressWarnings("rawtypes")
	public List<Static> getAllSensorUnit();
	
	//查询所有传感器位置信息
	@SuppressWarnings("rawtypes")
	public List<Static> getAllSensorPosition();
	
	//查询所有显示信息
	@SuppressWarnings("rawtypes")
	public List<Static> getAlldisplay();
	
	//添加位置信息
	public void addPosition(@Param("position")String position);
	
	//查询位置信息是否存在
	@SuppressWarnings("rawtypes")
	public Static getByV(@Param("position")String position);
	
	//根据查询位置信息
	@SuppressWarnings("rawtypes")
	public Static getPositionByid(@Param("id")int id);
	
	//根据id查询k
	public String getK(@Param("id")int id);
	
	//查询所有传感器设备类型
	@SuppressWarnings("rawtypes")
	public List<Static> getAllType();
	
	//查询所有传感器类型下的具体模拟量传感器
	public List<SensorTypeVO> getAllSensorOfType();
	
	//查询所有传感器类型下的具体开关量传感器
	public List<SensorTypeVO> getAllSwitchSensorOfType();
	
	//数据字典
	@SuppressWarnings("rawtypes")
	public List<Static<Static>> getAllDataMap(); 
	
	//添加数据字典
	@SuppressWarnings("rawtypes")
	public void addDataMap(Static sta);
	
	//按传感器类型查询瓦斯抽放系统的传感器
	public List<SensorTypeVO> getAllDrainageSensor();
	
	//根据id删除
	public void delete(@Param("id")int id);
	
	//更新
	@SuppressWarnings("rawtypes")
	public void update(Static sta);

	public List<Action> actionGetByK(@Param("key")String key);
	
	public Action actionGetByid(@Param("id")int id);
	
	public List<Action> actionGetByGroupId(@Param("gId")int gId);
	
	public String getV(@Param("id")int id);
	
	//查询所有的工作地点
	@SuppressWarnings("rawtypes")
	public List<Static> getallWorkplace();
	
	//查询所有的职务
	@SuppressWarnings("rawtypes")
	public List<Static> getallduty();
	
	public void addWorkplace(String workplace);
	
	public void addDuty(String  duty);
	
	@SuppressWarnings("rawtypes")
	public List<Static> getWorkpalce(String workplace);
	
	@SuppressWarnings("rawtypes")
	public List<Static> getDuty(String  duty);
	
	public List<Integer> getByPid(int pid);

	void addK(Static sta);

	List<Static> getAllAreaType();

	List<Static> getAllRadio();
}
