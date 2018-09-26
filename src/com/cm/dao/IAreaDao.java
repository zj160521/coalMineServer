package com.cm.dao;

import com.cm.entity.*;
import com.cm.entity.vo.AreaChangeVo;
import com.cm.entity.vo.AreaListVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IAreaDao {

	//查询所有区域相关信息
	public List<Area> getAll();
	
	//添加区域相关信息
	public int addArea(Area area);
	
	//查询新添加的区域id
	public int getId(Area area);
	
	public Area2 getDefaultArea();
	
	//添加area_worker中间表
	public void addAreaWorker(@Param("areaid")int areaid,@Param("workers")List<Worker> workers);
	
	//更新cardreder表
	public void updateCardreder(@Param("areaid")int areaid,@Param("list")List<Cardreder> list);
	
	//更新switchsensor表
	public void updateSwitchSensor(@Param("area_id")int area_id,@Param("id")int id);
	
	//更新sensor表
	public void updateSensor(@Param("area_id")int area_id,@Param("id")int id);
	
	//更新区域相关信息
	public void updateArea(Area area);
	
	//更新switchsensor表area_id为null
	public void updateSwitchSensorAreaidIsNull(@Param("id")int id);
	
	//更新sensor表area_id为null
	public void updateSensorAreaidIsNull(@Param("id")int id);
	
	//根据area_id查询sensor
	public List<Sensor> getSensorByAreaid(@Param("area_id")int area_id);
	
	//删除area_worker中间表相关信息
	public void deleteAreaWorker(@Param("areaid")int areaid);
	
	//设置cardreder的areaid为null
	public void updateByAreaid(@Param("areaid")int areaid);
	
	//删除区域信息
	public void deleteArea(@Param("areaid")int areaid);
	
	//查询未使用的读卡器信息
	public List<Cardreder> getCardreder(@Param("areaid")Integer areaid);
	
	//查询未使用的传感器
	public List<Sensor> getUnuseSensor();
	
	public Area2 getById(int id);
	
	public Area getByid(@Param("id")int id);
	
	public List<Area> getAllArea();
	
	public void updateAdjoin(@Param("adjoin")String adjoin,@Param("id")int id);
	
	public List<Area> getAreaByAdjoin(@Param("adjoin")String adjoin);
	
	public List<Integer> getAllemphasisAreaIds();
	
	public List<Integer> getAllAreaLimitIds();
	
	public void insertAreaConfig(List<AreaChangeVo> list);
	
	public AreaChangeVo getAreaChange(String day);
	
	public List<AreaChangeVo> getAreaMSG(String day);
	
	@SuppressWarnings("rawtypes")
	public List<AreaListVo> getallarealist();
}
