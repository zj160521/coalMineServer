package com.cm.service;

import com.cm.dao.EquipmentDao;
import com.cm.entity.Equipments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentsService {

	@Autowired
	private EquipmentDao dao;
	
	public void add(Equipments equipments){
		dao.add(equipments);
	}
	
	public void update(Equipments equip){
		dao.update(equip);
	}
	public void updatePoint(Equipments equip){
		dao.updatePoint(equip);
	}
	public void delete(int id){
		dao.delete(id);
	}
	
	public List<Equipments> getAllEquipments(){
		List<Equipments> list = dao.getAllEquipments();
		for(Equipments l:list){
			if(l.getPosition()==null){
				l.setPosition("未配置位置");
			}
		}
		return list;
	}
	
	public List<Equipments> getAllElec(){
		List<Equipments> list = dao.getAllElec();
		for(Equipments l:list){
			if(l.getPosition()==null){
				l.setPosition("未配置位置");
			}
		}
		return list;
	}
	
	public void updateUid(int id,String uid){
		dao.updateUid(id, uid);
	}
	
	public Equipments getById(int id){
		return dao.getById(id);
	}

	public void batchUpdate(List<Equipments> list){
	    dao.batchUpdate(list);
    }
	
	public List<String> getallIp(){
		List<String> list = dao.getIp();
		List<String> list2 = dao.getsubIp();
		for(String l:list2){
			list.add(l);
		}
		return list;
	}
	
	public String getIpbyId(int id){
		return dao.getIpbyId(id);
	}
	
	
	
}
