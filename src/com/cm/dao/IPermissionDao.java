package com.cm.dao;

import java.util.LinkedHashSet;
import java.util.List;

import com.cm.entity.MidPermission;
import com.cm.entity.Permission;

public interface IPermissionDao {
	public List<Permission> getAllPermission();
	public List<Permission> getAllPermissionByRole(int roleid);
	public void delPermissionByRole(int roleid);
	public void addAllPer(List<Permission> list);
	public LinkedHashSet<Integer> getRoleIdByPermissionId(int permissionId);
	public void delPermissionByPid(int permission_id);
	public List<MidPermission> getMidPer(int permission_id);
	public List<MidPermission> getAllRoleId();
	public void addMidPer(MidPermission m);
}
