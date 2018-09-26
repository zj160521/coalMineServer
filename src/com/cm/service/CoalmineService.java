package com.cm.service;

import com.cm.dao.ICoalmineDao;
import com.cm.entity.vo.CoalmineVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("CoalmineService")
public class CoalmineService {

	@Autowired
	private ICoalmineDao cmDao;

	public List<CoalmineVo> getDataByTime(String startTime, String endTime, String tableName) {
		try {
			return cmDao.getDataByTime(startTime, endTime, tableName);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	
}
