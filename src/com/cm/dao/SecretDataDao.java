package com.cm.dao;

import java.util.List;

import com.cm.entity.SecretData;
import com.cm.entity.vo.NameTime;

public interface SecretDataDao {

	
	public List<SecretData> getall(NameTime nameTime);
	
	public List<SecretData> getbypag(NameTime nameTime);
	
	public int getcount(NameTime nameTime);
	
	public int getcountdata(NameTime nameTime);
	
	
}
