package com.cm.dao;

import java.util.List;

import com.cm.entity.DbBackupLog;
import com.cm.entity.vo.NameTime;

public interface DbBackupLogDao {
	public List<DbBackupLog> getAll(NameTime nt);
	public void add(DbBackupLog log);
	public int getAllPage(NameTime nt);
}
