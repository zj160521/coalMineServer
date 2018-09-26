package com.cm.dao;

import java.util.List;

import com.cm.entity.Workbasic;

public interface WorkbasicDao {
		
	public void addWorkbasic(Workbasic workbasic);
	
	public List<Workbasic> getAllWorkbasic();
	
	public List<Workbasic> getWorkbasicbyMarker(Workbasic workbasic);
	
	public void updateWorkbasic(Workbasic workbasic);
	
	public void deleteWorkbasic(Workbasic workbasic);
	
	public void addposition(Workbasic workbasic);
	
	public List<Workbasic> getallposition();
	
	public List<Workbasic> getpositionbyname(Workbasic workbasic);
	
	public void updateposition(Workbasic workbasic);
	
	public void deleteposition(Workbasic workbasic);
	
}
