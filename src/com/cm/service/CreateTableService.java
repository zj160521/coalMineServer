package com.cm.service;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cm.dao.ICreateTableDao;


@Service
public class CreateTableService implements ICreateTableDao {
	
	@Autowired
	private ICreateTableDao dao;

	public void createTble(Map<String, String> tableName) {
		dao.createTble(tableName);
	}

	@Override
	public List<Integer> getWorkerIds(Map<String, String> map) {
		try {
			return dao.getWorkerIds(map);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


}
