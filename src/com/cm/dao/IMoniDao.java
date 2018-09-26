package com.cm.dao;

import java.util.List;

import com.cm.entity.Moni;

public interface IMoniDao {

	public List<Moni> getAllNvr();
	
	public List<Moni> getAllByPid();
	
	public void add(Moni moni);
	
	public void update(Moni moni);
	
	public Moni getMoni(int id);

	public Moni getMoniByDip(String dip);
	
	public void delete(int id);
	
	public void deleteByPid(int pid);
	
	public int getMoniDipByDip(String dip);

	public int getMoniDipByName(String name);
	
}
