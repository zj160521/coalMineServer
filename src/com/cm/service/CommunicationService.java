package com.cm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cm.dao.ICommunicationDao;
import com.cm.entity.Communication;



@Service("communicationService")
public class CommunicationService {

	@Autowired
	private ICommunicationDao mdao;
	
	public List<Communication> getAll(Communication communication){
		return mdao.getAll(communication);
	}
}
