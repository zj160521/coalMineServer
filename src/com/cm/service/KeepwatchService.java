package com.cm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cm.dao.IKeepwatchDao;
import com.cm.entity.vo.Keepwatch;
import com.cm.entity.vo.KeepwatchVO;

@Service
public class KeepwatchService {

	@Autowired
	private IKeepwatchDao keepwatchDao;
	
	public List<Keepwatch> getAllKeepwatchRecord(KeepwatchVO keepwatch){
		return keepwatchDao.getAllKeepwatchRecord(keepwatch);
	}
}
