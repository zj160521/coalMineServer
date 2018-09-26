package com.cm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cm.dao.ClassesDao;
import com.cm.entity.Classes;

@Service
public class ClassesService {

	@Autowired
	private ClassesDao dao;
	
	public int add(Classes classes){
		return dao.add(classes);
	}
	
	public List<Classes> getAll(){
		return dao.getAll();
	}
	
	public void delete(int id){
		dao.delete(id);
	}
}
