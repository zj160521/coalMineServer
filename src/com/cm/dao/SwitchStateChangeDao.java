package com.cm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cm.entity.Coalmine;
import com.cm.entity.vo.NameTime;
import com.cm.entity.vo.SwitchStateChangeVo;

public interface SwitchStateChangeDao {
	
	public List<Coalmine> getBasicdata(@Param("tablename")String tablename);
	
	public int isexisting(SwitchStateChangeVo changeVo);
	
	public void add(@Param("list")List<SwitchStateChangeVo> list);
	
	public List<SwitchStateChangeVo> getall(NameTime nameTime);
}
