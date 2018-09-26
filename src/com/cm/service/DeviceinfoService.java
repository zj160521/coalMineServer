package com.cm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cm.dao.IDeviceinfoDao;
import com.cm.dao.IMoniDao;


@Service
public class DeviceinfoService {

	@Autowired
	private IMoniDao mdao;
	@Autowired
	private IDeviceinfoDao ddao;
	
	public void show(){
	}

}
