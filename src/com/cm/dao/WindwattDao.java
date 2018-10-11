package com.cm.dao;

import java.util.List;





import org.apache.ibatis.annotations.Param;

import com.cm.entity.DevAction;
import com.cm.entity.Windwatt;
import com.cm.entity.vo.DevLinkVo;
import com.cm.entity.vo.DevLogicVo;
import com.cm.entity.vo.WindwattVo;

public interface WindwattDao {
	
	public int addDevlogicGroup(DevLinkVo dlv);
	
	public void addDevlogic(List<DevLogicVo> list);
	
	public void addDevaction(List<DevAction> list);
	
	@SuppressWarnings("rawtypes")
	public int addWindwatt(Windwatt windwatt);
	
	public void addWindwattSensor(@Param("list")List<WindwattVo> list);
	
	@SuppressWarnings("rawtypes")
	public void updateWindwatt(Windwatt windwatt);
	
	public void updateWindwattSensor(WindwattVo windwattVo);
	
	public void deleteWindwatt(@Param("id")int id);
	
	public void deleteWindwattSensor(@Param("windwattId")int windwattId);
	
	@SuppressWarnings("rawtypes")
	public List<Windwatt> getallWindwatt(@Param("id")int id);
	
	public List<WindwattVo> getWindwattSensor(@Param("windwattId")int windwattId);
	
	public List<WindwattVo> getallsensor();
}
