package com.cm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cm.entity.Coalmine;
import com.cm.entity.SwitchEfficiency;
import com.cm.entity.vo.NameTime;

public interface SwitchEfficiencyDao {
	
	public List<SwitchEfficiency> getsensors(@Param("tablename")String tablename,@Param("time")String time);
	
	public List<Coalmine> getBasicdata(@Param("s")SwitchEfficiency s,@Param("tablename")String tablename,@Param("time")String time);
	
	public int isexisting(SwitchEfficiency efficiency);
	
	public void adddata(@Param("list") List<SwitchEfficiency> list);
	
	public List<SwitchEfficiency> getall(NameTime nameTime);
	
	
	
}
