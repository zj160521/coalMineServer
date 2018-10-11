package com.cm.dao;

import com.cm.entity.Classes;

import java.util.List;

public interface ClassesDao {
	
	public int add(Classes classes);
	
	public List<Classes> getAll();
	
	public void delete(int id);

	void update(Classes classes);

	List<Classes> getAllEnv();

}
