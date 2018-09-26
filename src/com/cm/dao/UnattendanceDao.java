package com.cm.dao;

import java.util.List;

import com.cm.entity.Unattendance;
import com.cm.entity.Worker;

public interface UnattendanceDao {

	public List<Unattendance> getAllUnattendanceById(Unattendance unattendance);
	
	public List<Worker> getAllWorkerUnattendanceCount(Unattendance unattendance);
}
