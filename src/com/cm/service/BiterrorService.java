package com.cm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cm.dao.IBiterrorDao;
import com.cm.entity.Biterror;
import com.cm.entity.BiterrorResult;

@Service("BiterrorService")
public class BiterrorService {
	
	@Autowired
	private IBiterrorDao bitDao;
	
	public List<Biterror> getBydev(int resultId){
		return bitDao.getBydev(resultId);
	}
	
	public void add(Biterror bit){
		bitDao.add(bit);
	}
	
	public int count(int resultId){
		return bitDao.count(resultId);
	}
	
	public int minimumId(int resultId){
		return bitDao.minimumId(resultId);
	}
	
	public void delete(int id){
		bitDao.delete(id);
	}
	
	public void deleteBydev(int resultId){
		bitDao.deleteBydevice(resultId);
	}
	
	public BiterrorResult getdevice(BiterrorResult result){
		return bitDao.getdevice(result);
	}
	
	public void addBiterrorResult(BiterrorResult result){
		bitDao.addBiterrorResult(result);
	}
	
	public void updateBiterrorResult(BiterrorResult result){
		bitDao.updateBiterrorResult(result);
	}
	
	public void batchadd(List<Biterror> list){
		bitDao.batchadd(list);
	}

}
