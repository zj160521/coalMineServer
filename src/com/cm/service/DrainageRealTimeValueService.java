package com.cm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cm.dao.IDrainageRealTimeValueDao;
import com.cm.entity.vo.DrainageRealTimeValue;

@Service
public class DrainageRealTimeValueService {
	
	@Autowired
	private IDrainageRealTimeValueDao dao;

	//查询瓦斯抽放实时值
	public List<DrainageRealTimeValue> getAllrealtimevalue(int id){
		return dao.getAllrealtimevalue(id);
	}
}
