package com.cm.dao;

import java.util.List;

import com.cm.entity.Classes;

public interface ClassesDao {
	
	public int add(Classes classes);
	
	public List<Classes> getAll();
	
	public void delete(int id);

}
