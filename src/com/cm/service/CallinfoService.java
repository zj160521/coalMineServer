package com.cm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cm.dao.CallinfoDao;
import com.cm.entity.Callinfo;
import com.cm.entity.Helpme;
import com.cm.entity.vo.NameTime;

@Service("callinfoService")
public class CallinfoService {
	
	@Autowired
	private CallinfoDao dao;
	
	public void addCallinfo(Callinfo callinfo){
		dao.addCallinfo(callinfo);
	}
	
	public List<Callinfo> getallCallinfo(Callinfo callinfo){
		return dao.getallCallinfo(callinfo);
	}
	
	public List<Callinfo> getnewCallinfo(NameTime nt){
		return dao.getnewCallinfo(nt);
	}
	
	public List<Helpme> getHelp(NameTime nt){
		return dao.getHelp(nt);
	}
	
	public List<Callinfo> getUnfinish(){
		return dao.getUnfinish();
	}
	
	public void stopCallinfo(int id){
		dao.stopCallinfo(id);;
	}
	
	public int getAllPage(NameTime nt){
		return dao.getAllPage(nt);
	}
	
	public int getAllPage2(NameTime nt){
		return dao.getAllPage2(nt);
	}
	
	public void helpRemark(NameTime nt){
		dao.helpRemark(nt);
	}
}
