package com.cm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cm.entity.Radio;

public interface IRadioDao {

	public void add(Radio radio);
	
	public List<Radio> getAll();
	
	public void update(Radio radio);
	
	public void delete(@Param("id")int id);
	
	public Radio getById(@Param("id")int id);
	
	public void updateUid(@Param("id")int id,@Param("uid")String uid);
}
