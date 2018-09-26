package com.cm.dao;

import com.cm.entity.Equipments;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EquipmentDao {

	public void add(Equipments equip);
	
	public void update(Equipments equip);
	
	public void updatePoint(Equipments equip);
	
	public void delete(@Param("id")int id);
	
	public List<Equipments> getAllEquipments();
	
	public List<Equipments> getAllElec();
	
	public void updateUid(@Param("id")int id,@Param("uid")String uid);
	
	public Equipments getById(@Param("id")int id);

	void batchUpdate(List<Equipments> list);
	
	public List<String> getIp();
	
	public List<String> getsubIp();
	
	public String getIpbyId(@Param("id")int id);
}
