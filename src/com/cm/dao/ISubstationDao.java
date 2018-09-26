package com.cm.dao;

import com.cm.entity.Sensor;
import com.cm.entity.Substation;
import com.cm.entity.vo.StationSensorVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface ISubstationDao {

	//查询所有分站
	@SuppressWarnings("rawtypes")
	public List<Substation> getAll();
	
	//添加分站
	@SuppressWarnings("rawtypes")
	public void addup(Substation station);
	
	//更新分站
	@SuppressWarnings("rawtypes")
	public void update(Substation station);
	
	//删除分站
	public void delete(@Param("id")int id);

	
	@SuppressWarnings("rawtypes")
	public Substation getSubbyid(@Param("id") int id);

	//查询分站上是否有重复地址的传感器
	public Sensor getRepetition(@Param("station")int station,@Param("sensorId")int sensorId);
	
	//查询分站上所有传感器的个数
	public Integer sensorCount(@Param("station")int station);

	public List<StationSensorVo> getAllSensor();
	
	//查询分站上所有开关量传感器
	@SuppressWarnings("rawtypes")
	public List<Substation> getAllSwitchSensor();
	
	//根据ip地址查询分站
	@SuppressWarnings("rawtypes")
	public Substation getByIp(@Param("ip")String ip);
	
	public List<String> getAllipaddr();

	public void updateUid(@Param("id")int id,@Param("uid")String uid);

	String getUid(@Param("sensorId")int sensorId,@Param("ipaddr")String ipaddr);

}
