package com.cm.service;

import com.cm.dao.ClassesDao;
import com.cm.entity.Classes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

	public void update(Classes classes){
	    dao.update(classes);
    }

    public List<Classes> getAllEnv(){
	    return dao.getAllEnv();
    }
}
