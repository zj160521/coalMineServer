package com.cm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cm.entity.Department;

public interface DepartmentDao {
	
	//添加
	public void addDepartment(Department department);
	
	//查询所有
	public List<Department> getAllDepartment();
	
	//修改
	public void updateDepartment(Department department);
	
	//删除
	public void deleteDepartment(@Param("ids") List<Integer> ids);
	
	//查询部门名称是否已存在
	public int getDepartmentByname(String name);
	
	//根据ID查询
	public List<Department> getAllDepartmentByid(int id);
	
	public List<Department> getDepartList();
	
	
}
