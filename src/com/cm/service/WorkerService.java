package com.cm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cm.dao.WorkerDao;
import com.cm.entity.Worker;

/**
 * 员工Service
 * @author hetaiyun
 *
 */
@Service("workerService")
public class WorkerService {
	
	@Autowired
	private WorkerDao workerDao;
	
	//添加员工信息
	public void addWorker(Worker worker){
		workerDao.addWorker(worker);
	}
	
	//查询员工信息
	public List<Worker> getAllWorker(Worker worker){
		List<Worker> list = workerDao.getAllWorker(worker);
		for(Worker l:list){
			l.setPosta(Integer.parseInt(l.getRfcard_id()));
		}
		
		return list;
	}
	
	//修改更新员工信息
	public void updateWorker(Worker worker){
		workerDao.updateWorker(worker);
	}
	
	//将员工改为离职
	public void deleteWorker(Worker worker){
		workerDao.deleteWorker(worker);
	}
	
	//查询卡号是否已存在
	public int getWorker(Worker worker){
		return workerDao.getWorker(worker);
	}
	
	public void deleteLeaveWorker(int[] ids){
		 workerDao.deleteLeaveWorker(ids);
	}
	
	public void updateWorkers(Worker worker){
		workerDao.updateWorkers(worker);
	}
	
	public List<Worker> getAll(){
		return workerDao.getAll();
	}
	
	public Worker getWorkerBycardId(String cardId){
		try {
			return workerDao.getWorkerBycardId(cardId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
