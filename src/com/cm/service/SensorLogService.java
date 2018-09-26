package com.cm.service;

import com.cm.dao.ISensorLogDao;
import com.cm.entity.SensorLog;
import com.cm.entity.vo.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SensorLogService {

	@Autowired
	private ISensorLogDao sensorLogDao;
	
	//添加
	public void addSensorLog(SensorLog sensorLog){
		sensorLogDao.addSensorLog(sensorLog);
	}
	
	//分页查询
	public PageBean<SensorLog> getByPage(int currentPage,int pageSize,String uid){
		int curPage = (currentPage-1)*pageSize;
		List<SensorLog> list = sensorLogDao.getByPage(curPage, pageSize, uid);
		PageBean<SensorLog> bean = new PageBean<SensorLog>();
		bean.setCurrentPage(currentPage);
		bean.setList(list);
		bean.setPageSize(pageSize);
		bean.setTotalCount(sensorLogDao.getTotalCount(uid));
		return bean;
	}
	
	//查询最近的一条记录
	public SensorLog getOrderByTime(String uid){
		return sensorLogDao.getOrderByTime(uid);
	}
}
