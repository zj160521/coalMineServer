package com.cm.dao;

import java.util.List;

import com.cm.entity.Permission;
import com.cm.entity.User;
import com.cm.entity.User_log;
import com.cm.entity.vo.NameTime;

public interface IUserDao {
	// 添加方法
	public void add(User user);

	// 删除方法
	public void delete(int id);
	public void delByWorker(int workerid);

	// 修改
	public void update(User user);

	// 根据id来查询全部
	public User getById(int id);
	
	public User getByName(String name);

	// 查询所有
	public List<User> getAll();
	
	//获取权限
	public List<Permission> getPermission(int id);
	
	//添加日志
	public void addLog(User_log log);
	
	//查询日志
	public List<User_log> getAllLog(NameTime nameTime);
	public int getAllPage(NameTime nameTime);
	
	public User getUserByRoleId(int roleId);
	
	public List<Permission> getLogtype();
}
