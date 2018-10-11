package com.cm.controller;

import com.alibaba.fastjson.JSONObject;
import com.cm.dao.IRoleDao;
import com.cm.entity.Role;
import com.cm.security.LoginManage;
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
@RequestMapping(value = "/role")
public class RoleController {
	@Autowired
	private IRoleDao theDao;
	@Autowired
	private ResultObj result;
	@Autowired
	private LoginManage loginManage;

	@RequestMapping(value = "/addup", method = RequestMethod.POST)
	@ResponseBody
	public Object addup(HttpServletRequest request,@RequestBody Role role) {
		Object ret = loginManage.isLogin(request);
		if (ret != null) return ret;
		Object per = loginManage.checkPermission(request);
	    if (per != null) return per;
	    
	    String operation2 = "";
		try {
			if(role.getId()>0){
			    theDao.update(role);
			    operation2 = "修改角色："+role.getName();
			}else{
			    theDao.addRole(role);
			    operation2 = "增加角色："+role.getName();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-3, "添加修改角色出错！");
		}
		
		String remark = JSONObject.toJSONString(role);
        loginManage.addLog(request, remark, operation2, 120);
		return result.setStatus(0, "ok");
	}
	
	@RequestMapping(value = "/getAll", method = RequestMethod.GET)
	@ResponseBody
	public Object getAll(HttpServletRequest request) {
		Object ret = loginManage.isLogin(request);
		if (ret != null) return ret;
	    
		try {
			List<Role> list=theDao.getAllRole();
		    result.put("res", list);
			
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-3, "查询角色出错！");
		}
		
		return result.setStatus(0, "ok");
	}
	
	@RequestMapping(value = "/delRole", method = RequestMethod.POST)
	@ResponseBody
	public Object delRole(HttpServletRequest request,@RequestBody Role role) {
		Object ret = loginManage.isLogin(request);
		if (ret != null) return ret;
		
		Object per = loginManage.checkPermission(request);
	    if (per != null) return per;
		try {
			if(role.getId()>0){
				theDao.delRole(role.getId());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-3, "删除角色出错！");
		}
		
		String remark = JSONObject.toJSONString(role);
		String operation2 = "删除角色："+role.getName();
        loginManage.addLog(request, remark, operation2, 121);
		return result.setStatus(0, "ok");
	}
}
