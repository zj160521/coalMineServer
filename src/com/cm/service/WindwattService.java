package com.cm.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cm.dao.WindwattDao;
import com.cm.entity.DevAction;
import com.cm.entity.Windwatt;
import com.cm.entity.vo.DevLinkVo;
import com.cm.entity.vo.DevLogicVo;
import com.cm.entity.vo.WindwattVo;


@Service("windwattService")
public class WindwattService {

	@Autowired
	private WindwattDao dao;
	
	
	public int addDevlogicGroup(DevLinkVo dlv){
		
		return 0;
	}
	
	public void addDevlogic(List<DevLogicVo> list){
		
	}
	
	public void addDevaction(List<DevAction> list){
		
	}
	
	@SuppressWarnings("rawtypes")
	public Windwatt addWindwatt(Windwatt windwatt){
		dao.addWindwatt(windwatt);
		return windwatt;
	}
	public void addWindwattSensor(List<WindwattVo> list){
		dao.addWindwattSensor(list);
	}
	
	@SuppressWarnings("rawtypes")
	public void updateWindwatt(Windwatt windwatt){
		dao.updateWindwatt(windwatt);
	}
	
	public void updateWindwattSensor(WindwattVo windwattVo){
		dao.updateWindwattSensor(windwattVo);
	}
	
	public void deleteWindwatt(int id){
		dao.deleteWindwatt(id);
	}
	
	public void deleteWindwattSensor(int windwattId){
		dao.deleteWindwattSensor(windwattId);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Windwatt> getallWindwatt(){
		List<Windwatt> list = dao.getallWindwatt();
		for(Windwatt l:list){
			l.setList(dao.getWindwattSensor(l.getId()));
		}
		
		return list;
	}
	
}
