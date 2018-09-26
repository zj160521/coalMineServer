package com.cm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cm.dao.MaporgDao;
import com.cm.entity.Maporg;

@Service("saporgService")
public class MaporgService implements MaporgDao {
	
	@Autowired
	private MaporgDao dao;
	
	public void add(Maporg maporg){
		dao.add(maporg);
	}
	
	public void update(Maporg maporg){
		dao.update(maporg);
	}
	
	public List<Maporg> get(){
		return dao.get();
	}
	
	public void del(Maporg maporg){
		dao.del(maporg);
	}

	@Override
	public List<Maporg> getByType(int type) {
		return dao.getByType(type);
	}
}
