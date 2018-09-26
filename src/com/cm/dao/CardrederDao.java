package com.cm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cm.entity.Cardreder;

public interface CardrederDao {
	
	//添加读卡器数据
	public int addCardreder(Cardreder cardreder);
	
	//查询所有在用和停用的读卡器
	public List<Cardreder> getallCardreder(Cardreder cardreder);
	
	//更新读卡器数据
	public void updateCardreder(Cardreder cardreder);
	
	//查询单个读卡器
	public Cardreder getCardreder(Cardreder cardreder);
	
	public  int isuse(Cardreder cardreder);
	
	//查询所有读卡器（包括假删除状态的）
	public List<Cardreder> getCardreders();
	
	//查询分站下的所有读卡器
	public List<Cardreder> getCardrederbysub(@Param("subid")int subid);
	
	public void delete(Cardreder cardreder);
	
	public Cardreder getById(@Param("id")int id);
	
	public Cardreder getbyCid(Cardreder cardreder);
	
}
