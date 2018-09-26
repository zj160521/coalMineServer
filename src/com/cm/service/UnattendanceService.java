package com.cm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cm.dao.UnattendanceDao;
import com.cm.entity.Unattendance;
import com.cm.entity.Worker;

@Service
public class UnattendanceService {

	
	@Autowired
	private UnattendanceDao dao;
	
	public List<Unattendance> getAllUnattendanceById(Unattendance unattendance){
		return dao.getAllUnattendanceById(unattendance);
	}
	
	public List<Worker> getAllWorkerUnattendanceCount(Unattendance unattendance){
		return dao.getAllWorkerUnattendanceCount(unattendance);
	}
}
