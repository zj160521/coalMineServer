package com.cm.service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cm.dao.IPermissionDao;
import com.cm.entity.Permission;

@Service("permissionService")
public class PermissionService {
	
	@Autowired
	private IPermissionDao perDao;

	public List<Permission> getAllPermission() {
		return perDao.getAllPermission();
	}
	
	public List<Permission> getAllPermissionByRole(int roleid){
		return perDao.getAllPermissionByRole(roleid);
	}
	
	@Transactional
	public void addupPermissionByRole(Permission p) throws Exception{
		perDao.delPermissionByRole(p.getRoleid());
		if(p.getPerids().length>0){
			List<Permission> list=new ArrayList<Permission>();
			for(Integer pid:p.getPerids()){
				Permission per=new Permission();
				per.setId(pid);
				per.setRoleid(p.getRoleid());
				list.add(per);
			}
			perDao.addAllPer(list);
		}
	}

	public LinkedHashSet<Integer> getRoleIdByPermissionId(int permissionId){
		LinkedHashSet<Integer> list = perDao.getRoleIdByPermissionId(permissionId);
		if(list != null){
			return list;
		} else {
			return null;
		}
	}
}
