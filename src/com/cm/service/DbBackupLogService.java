package com.cm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cm.dao.DbBackupLogDao;
import com.cm.entity.DbBackupLog;
import com.cm.entity.vo.NameTime;

@Service
public class DbBackupLogService implements DbBackupLogDao {

	@Autowired
	private DbBackupLogDao dao;
	
	@Override
	public List<DbBackupLog> getAll(NameTime nt) {
		return dao.getAll(nt);
	}

	@Override
	public void add(DbBackupLog log) {
		dao.add(log);
	}

	@Override
	public int getAllPage(NameTime nt) {
		return dao.getAllPage(nt);
	}

}
