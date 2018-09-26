package com.cm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cm.dao.IRadioDao;
import com.cm.entity.Radio;

@Service
public class RadioService {

	@Autowired
	private IRadioDao radioDao;
	
	public void add(Radio radio){
		radioDao.add(radio);
	}
	
	public List<Radio> getAll(){
		List<Radio> list = radioDao.getAll();
		for(Radio l:list){
			if(l.getPosition()==null){
				l.setPosition("未配置位置");
			}
		}
		return list;
	}
	
	public void update(Radio radio){
		radioDao.update(radio);
	}
	
	public void delete(int id){
		radioDao.delete(id);
	}
	
	public Radio getById(int id){
		return radioDao.getById(id);
	}
	
	public void updateUid(int id,String uid){
		radioDao.updateUid(id, uid);
	}
}
