package com.cm.dao;

import java.util.List;





import org.apache.ibatis.annotations.Param;

import com.cm.entity.Worker;
import com.cm.entity.vo.Searchform;
import com.cm.entity.vo.WorkerVo;

/**
 * 员工信息管理Dao
 * @author hetaiyun
 *
 */
public interface WorkerDao {
	
	//添加员工信息
	public void addWorker(Worker worker);
	
	//查询员工信息
	public List<Worker> getAllWorker(Worker worker);
	
	//修改更新员工信息
	public void updateWorker(Worker worker);
	
	//将员工改为离职
	public void deleteWorker(Worker worker);
	
	//查询卡号是否已存在
	public int getWorker(Worker worker);
	
	//批量删除员工信息
	public void deleteLeaveWorker(@Param("ids") int[] ids);
	
	//批量修改员工信息
	public void updateWorkers(Worker worker);
	
	//获取所需员工
	public List<Worker> getAll(Searchform searchform);
	
	//获取所需员工
	public List<Worker> getAreaAll(Searchform searchform);

	public List<Worker> getAll();
	
	public List<Worker> getAllBySearchform(Searchform searchform);
	
	public List<WorkerVo> getWorkers();
	
	public List<WorkerVo> getWorkersBySearchform(Searchform searchform);
	
	public Worker getWorkerBycardId(String cardId);
	
}
