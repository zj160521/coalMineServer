package com.cm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cm.dao.IAreaCacheDao;
import com.cm.dao.IWorkerInAreaRecDao;
import com.cm.entity.Area;
import com.cm.entity.AreaWorker2;
import com.cm.entity.AttendanceMap;
import com.cm.entity.CardWorker;
import com.cm.entity.Cardreder;
import com.cm.entity.Substation2;
import com.cm.entity.Worker;
import com.cm.entity.WorkerInAreaRec;
import com.cm.entity.vo.AreaVo;


@Service("AreaCardService")
public class AreaCardService {

	@Autowired
	public IAreaCacheDao acDao;
	@Autowired
	public IWorkerInAreaRecDao warDao;
	
	public List<AreaVo> getAreaTable(){
		return acDao.getAreaTable();
	}
	
	public List<String> getAllCard(){
		return acDao.getAllCard();
	}
	
	public void addRec(WorkerInAreaRec w){
		warDao.addRec(w);
	}
	
	public List<Area> getAllArea(){
		return acDao.getAllArea();
	}
	
	public List<AreaWorker2> getAreaWorker(){
		return acDao.getAreaWorker();
	}
	
	public List<CardWorker> getCradWorker(){
		return warDao.getCradWorker();
	}
	
	public List<Cardreder> getExitCardreader(){
		return acDao.getExitCardreader();
	}
	
	public List<Cardreder> getCardreaderName(){
		return acDao.getCardreaderName();
	}
	
	public List<Substation2> getSubstation(){
		return acDao.getSubstation();
	}
	
	public List<Worker> getEntranceCard(){
		return acDao.getEntranceCard();
	}
	
	public List<AttendanceMap> getAttendance(){
		return acDao.getAttendance();
	}
	
	public List<Worker> getWorkerName(){
		return warDao.getWorkerName();
	}
}
