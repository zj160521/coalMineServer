package com.cm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cm.dao.WorktypeDao;
import com.cm.entity.Worktype;



@Service("worktypeService")
public class WorktypeService {
	
	@Autowired
	private WorktypeDao dao;
	
	
	public void addWorktype(Worktype worktype){
		dao.addWorktype(worktype);
	}
	
	public List<Worktype> getAllWorktype(){
		return dao.getAllWorktype();
	}
	
	public void updateWorktype(Worktype worktype){
		dao.updateWorktype(worktype);
	}
	
	public void deleteWorktype(int id){
		dao.deleteWorktype(id);
	}
	
	public int getWorktypeBywid(String wid){
		return dao.getWorktypeBywid(wid);
	}
	
	public int getWorktypeByname(Worktype worktype){
		return dao.getWorktypeByname(worktype);
	}
}
