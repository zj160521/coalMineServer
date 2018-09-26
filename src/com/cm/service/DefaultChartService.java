package com.cm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cm.dao.IDefaultChartDao;
import com.cm.entity.DefaultChart;

@Service
public class DefaultChartService {

	@Autowired
	private IDefaultChartDao chartDao;
	
	public DefaultChart getById(int module_id){
		return chartDao.getById(module_id);
	}
	
	public void add(DefaultChart chart){
		chartDao.add(chart);
	}
	
	public void update(DefaultChart chart){
		chartDao.update(chart);
	}
	
	public List<DefaultChart> getAll(int pid){
		return chartDao.getAll(pid);
	}
	
	public void batchadd(DefaultChart chart){
		chartDao.batchadd(chart);
	}
	
	public void deleteByPid(int pid){
		chartDao.deleteByPid(pid);
	}
}
