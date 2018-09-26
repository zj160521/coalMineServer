package com.cm.dao;

import java.util.List;

import com.cm.entity.License;

public interface ILicenseDao {
	public void add(License license);
	public void update(License license);
	public List<License> getAll();
	public void del(int id);
}
