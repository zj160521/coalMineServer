package com.cm.controller;

import com.cm.entity.Permission;
import com.cm.security.LoginManage;
import com.cm.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Scope("prototype")
@Controller
@RequestMapping(value = "/permission")
public class PermissionController {
	@Autowired
	private PermissionService theService;
	@Autowired
	private ResultObj result;
	@Autowired
	private LoginManage loginManage;

	@RequestMapping(value = "/addup", method = RequestMethod.POST)
	@ResponseBody
	public Object addup(HttpServletRequest request,@RequestBody Permission permission) {
		Object ret = loginManage.isLogin(request);
		if (ret != null) return ret;
		
		Object per = loginManage.checkPermission(request);
	    if (per != null) return per;
		
	    try {
			theService.addupPermissionByRole(permission);
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-2, "编辑权限出错！");
		}
		
		return result.setStatus(0, "ok");
	}
	
	@RequestMapping(value = "/getAll", method = RequestMethod.POST)
	@ResponseBody
	public Object getAll(HttpServletRequest request,@RequestBody Permission permission) {
		Object ret = loginManage.isLogin(request);
		if (ret != null) return ret;
		
	    List<Permission> all=theService.getAllPermission();
	    List<Permission> list=theService.getAllPermissionByRole(permission.getRoleid());
	    for(Permission p:all){
			for(Permission p1:list){
				if(p.getId()==p1.getId()){
					p.setEnable(true);
					break;
				}
			}
		}
	    Permission p=loginManage.recursiveTree(10,all);
	    result.put("res", p);
		return result.setStatus(0, "ok");
	}
}
