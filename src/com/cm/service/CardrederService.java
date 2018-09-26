package com.cm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cm.dao.CardrederDao;
import com.cm.entity.Cardreder;

@Service("cardrederService")
public class CardrederService {
	
	@Autowired
	private CardrederDao dao;
	
	public Cardreder addCardreder(Cardreder cardreder){
		dao.addCardreder(cardreder);
		return cardreder;
	}
	
	public List<Cardreder> getallCardreder(Cardreder cardreder){
		List<Cardreder> list = dao.getallCardreder(cardreder);
		for(Cardreder l:list){
			if(l.getPosition()==null){
				l.setPosition("未配置位置");
			}
		}
		return list;
	}
	
	public void updateCardreder(Cardreder cardreder){
		dao.updateCardreder(cardreder);
	}
	
	public Cardreder getCardreder(Cardreder cardreder){
		return dao.getCardreder(cardreder);
	}
	
	public int isuse(Cardreder cardreder){
		return dao.isuse(cardreder);
	}
	
	public List<Cardreder> getCardreders(){
		List<Cardreder> list = dao.getCardreders();
		for(Cardreder l:list){
			if(l.getPosition()==null){
				l.setPosition("未配置位置");
			}
		}
		return list;
	}
	
	public List<Cardreder> getCardrederbysub(int subid){
		List<Cardreder> list = dao.getCardrederbysub(subid);
		for(Cardreder l:list){
			if(l.getPosition()==null){
				l.setPosition("未配置位置");
			}
		}
		return list;
	}
	
	public void delete(Cardreder cardreder){
		dao.delete(cardreder);
	}
	
	public Cardreder getById(int id){
		return dao.getById(id);
	}
	
	public Cardreder getbyCid(Cardreder cardreder){
		return dao.getbyCid(cardreder);
	}
}
