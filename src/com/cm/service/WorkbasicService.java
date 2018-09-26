package com.cm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cm.dao.WorkbasicDao;
import com.cm.entity.Workbasic;

@Service("workbasicService")
public class WorkbasicService {
	
	@Autowired
	private WorkbasicDao dao;
	
	public List<Workbasic> addWorkbasic(Workbasic workbasic){
		dao.addWorkbasic(workbasic);
		return dao.getWorkbasicbyMarker(workbasic);
	}
	
	public List<Workbasic> getAllWorkbasic(){
		return dao.getAllWorkbasic();
	}
	
	public List<Workbasic> getWorkbasicbyMarker(Workbasic workbasic){
		return dao.getWorkbasicbyMarker(workbasic);
	}
	
	public void updateWorkbasic(Workbasic workbasic){
		dao.updateWorkbasic(workbasic);
	}
	public void deleteWorkbasic(Workbasic workbasic){
		dao.deleteWorkbasic(workbasic);
	}
	
	public List<Workbasic> addposition(Workbasic workbasic){
		dao.addposition(workbasic);
		return dao.getpositionbyname(workbasic);
	}
	
	public List<Workbasic> getallposition(){
		return dao.getallposition();
	}
	
	public List<Workbasic> getpositionbyname(Workbasic workbasic){
		return dao.getpositionbyname(workbasic);
	}
	
	public void updateposition(Workbasic workbasic){
		dao.updateposition(workbasic);
	}
	
	public void deleteposition(Workbasic workbasic){
		dao.deleteposition(workbasic);
	}
}
