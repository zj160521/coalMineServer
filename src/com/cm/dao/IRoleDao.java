package com.cm.dao;

import java.util.List;

import com.cm.entity.Role;

public interface IRoleDao {
	public void addRole(Role role);
	public void update(Role role);
	public List<Role> getAllRole();
	public void delRole(int id);
}
