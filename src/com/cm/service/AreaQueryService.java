package com.cm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cm.dao.AreaQueryDao;
import com.cm.entity.Area;

@Service
public class AreaQueryService {

	@Autowired
	private AreaQueryDao areaQueryDao;
	
	public List<Area> EmphasisArea(int emphasis){
		return areaQueryDao.EmphasisArea(emphasis);
	}
	
	public List<Area> GeneralArea(int emphasis){
		return areaQueryDao.GeneralArea(emphasis);
	}
	
	public List<Area> AstrictArea(int default_allow){
		return areaQueryDao.AstrictArea(default_allow);
	}
}
