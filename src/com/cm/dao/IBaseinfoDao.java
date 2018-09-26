package com.cm.dao;

import com.cm.entity.Sensor;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IBaseinfoDao {

	//添加配置信息
	public int addBaseinfo(Sensor sensor);
	
	//查询全部传感器的配置信息
	public List<Sensor> getAllSensor(@Param("type")int type);
	
	//查询全部传感器的配置信息
	public List<Sensor> getAllSensor2();
	
	//根据id更改某一个传感器的配置信息
	public void updateSensor(Sensor sensor);
	
	//根据id删除某一个传感器
	public void deleteSensor(@Param("id")int id);
	
	//根据id查询传感器
	public Sensor getById(@Param("id")int id);
	
	//查询所有瓦斯传感器
	public List<Sensor> getAllmashgas();
	
	//更改传感器的分站信息
	public void updateSubstation(@Param("ipaddr")String ipaddr,@Param("id")int id);
	
	//查询分站下的所有传感器
	public List<Sensor> getbysubid(@Param("subid")int subid);
	
	//查询未同步的设备
	public List<Sensor> getNoSync();
	
	//根据id修改传感器同步情况
	public void updateConfigById(@Param("configsync")int configsync,@Param("id")int id);
	
	public Sensor getById2(@Param("id")int id);
	
	public void updateUid(@Param("id")int id,@Param("uid")String uid);

	//查询所有的GD5传感器
	List<Sensor> getGd5Sensors();
	
	public List<Sensor> getbyareaid(@Param("areaid")int areaid);
	
	public List<Sensor> getbyuids(@Param("uids")List<String> uids);

	Sensor getByCoId(@Param("CoId")int CoId);

	Sensor getBymethaneId(@Param("methaneId")int methaneId);
	
	public List<Sensor> getSensorByAreaId(@Param("areaid")int areaid);
	
}
