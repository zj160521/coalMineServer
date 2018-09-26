package com.cm.dao;

import java.util.List;

import com.cm.entity.Maporg;

public interface MaporgDao {
	
	//添加
	public void add(Maporg maporg);
	
	//修改
	public void update(Maporg maporg);
	
	//查询
	public List<Maporg> get();
	
	//查询
	public List<Maporg> getByType(int type);
	
	public void del(Maporg maporg);
}
