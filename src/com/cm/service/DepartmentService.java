package com.cm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cm.dao.DepartmentDao;
import com.cm.entity.Department;

@Service("departmentService")
public class DepartmentService {
		
	@Autowired
	private DepartmentDao departmentDao;
	
	public void addDepartment(Department department){
		departmentDao.addDepartment(department);
	}
	
	public List<Department> getAllDepartment(){
		return departmentDao.getAllDepartment();
	}
	
	public void updateDepartment(Department department){
		departmentDao.updateDepartment(department);
	}
	
	public void deleteDepartment(List<Integer> ids){
		departmentDao.deleteDepartment(ids);
	}
	
	public int getDepartmentByname(String name){
		return departmentDao.getDepartmentByname(name);
	}
	
	public List<Department> getAllDepartmentByid(int id){
		return departmentDao.getAllDepartmentByid(id);
	}
	public List<Department> getDepartList(){
		return departmentDao.getDepartList();
	}
}
