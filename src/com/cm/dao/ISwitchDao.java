package com.cm.dao;

import com.cm.entity.SwitchSensor;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ISwitchDao {

	//查询所有开关量传感器设备
	public List<SwitchSensor> getAllSwitchSensor(@Param("type")Integer type);
	
	//添加开关量传感器设备
	public int addSwitchSensor(SwitchSensor sensor);
	
	//更新开关量传感器
	public void updateSwitchSensor(SwitchSensor sensor);
	
	//根据id删除某个开关量传感器
	public void delete(@Param("id")int id);
	
	//根据id查询传感器
	public SwitchSensor getById(@Param("id")int id);
	
	//查询新添加设备的id
	public Integer getaddedId(@Param("stationId")int stationId,@Param("devid")int devid,@Param("type")int type);
	
	//查询分站下的所有传感器
	public List<SwitchSensor> getbysubid(@Param("subid")int subid);
	
	//查询未同步设备
	public List<SwitchSensor> getNoSync();
	
	//根据id修改传感器同步情况
	public void updateConfigById(@Param("configsync")int configsync,@Param("id")int id);
	
	public List<Integer> getisuseFeedid(@Param("id")int id,@Param("feedId")int feedId);
	
	public void updateFeedId(SwitchSensor sensor);
	
	public void updateUid(@Param("id")int id,@Param("uid")String uid);

	public SwitchSensor getByStationAndSensorId(@Param("station") int station,@Param("sensorId") int sensorId);

	public List<SwitchSensor> AllSwitchSensor();
	
	public SwitchSensor getbyUid(@Param("uid")String uid);
	
	public List<SwitchSensor> getbyareaid(@Param("areaid")int areaid);
	
	public List<SwitchSensor> getbyuids(@Param("uids")List<String> uids);
	
	public List<SwitchSensor> getSensorByAreaId(@Param("areaid")int areaid);
	
	public List<SwitchSensor> getControlSensor();
	
}
