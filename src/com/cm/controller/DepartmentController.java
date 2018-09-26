package com.cm.controller;



import com.cm.entity.Department;
import com.cm.security.LoginManage;
import com.cm.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


@Scope("prototype")
@Controller
@RequestMapping("/department")
public class DepartmentController {
		
		
	@Autowired
	private ResultObj result;
	
	@Autowired
	private LoginManage loginManage;
	
	@Autowired
	private DepartmentService departmentService;
	
	
	
	
	@RequestMapping(value="/addup",method=RequestMethod.POST)
	@ResponseBody
	public Object addDepartment(@RequestBody Department department,HttpServletRequest request){
		if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}
		Object per = loginManage.checkPermission(request);
		if(null!=per) return per;
		try {
			
			int b = departmentService.getDepartmentByname(department.getName());
			if(b>0){
				return result.setStatus(-2, "部门名称已存在");
			}else{
				departmentService.addDepartment(department);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
		
	}
	
	@RequestMapping(value="/getall",method=RequestMethod.GET)
	@ResponseBody
	public Object getAllDepartment(HttpServletRequest request){
		if(!loginManage.isUserLogin(request)){
		return result.setStatus(-1, "no login");
		}
			try {
				List<Department> dlist= new ArrayList<Department>();
				List<Department> departs = departmentService.getAllDepartment();
				for(Department d:departs){
						Department dpt = getDepartment(d);
						dlist.add(dpt);
					}
				result.put("data", dlist);
			} catch (Exception e) {
				e.printStackTrace();
				return result.setStatus(-4, "exception");
			}
			
		return result.setStatus(0, "ok");
	}

	@RequestMapping(value="/update",method=RequestMethod.POST)
	@ResponseBody
	public Object updateDepartment(@RequestBody Department department,HttpServletRequest request){
		if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}
		try {
			
			int b = departmentService.getDepartmentByname(department.getName());
			if(b>0){
				return result.setStatus(-2, "部门名称已存在");
			}else{
				departmentService.updateDepartment(department);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");		
	}
	
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	@ResponseBody
	public Object deleteDepartment(@RequestBody Department department,HttpServletRequest request){
		if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}
		Object per = loginManage.checkPermission(request);
		if(null!=per) return per;
		try {
			List<Integer> ds = new ArrayList<Integer>();
			ds = getDepartid(department.getId(), ds);
			ds.add(department.getId());
			departmentService.deleteDepartment(ds);
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}
	
	@RequestMapping(value="/getDepartList",method=RequestMethod.GET)
	@ResponseBody
	public Object getDepartList(HttpServletRequest request){
		if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
			}
				try {
					List<Department> list = departmentService.getDepartList();
					result.put("data", list);
				} catch (Exception e) {
					e.printStackTrace();
					return result.setStatus(-4, "exception");
				}
				
			return result.setStatus(0, "ok");
	}
	
	private Department getDepartment(Department department){
		Department dp = department;
		List<Department> lists = departmentService.getAllDepartmentByid(department.getId());
		if(lists.size()>0){
			for(Department l:lists){
				Department d = getDepartment(l);
				dp.getList().add(d);
			}
		}
		return dp;
	}
	
	private List<Integer> getDepartid(int id, List<Integer> list) {
		List<Integer> dp = list;
		List<Department> lists = departmentService.getAllDepartmentByid(id);
		if (lists.size() > 0) {
			for (Department l : lists) {
				getDepartid(l.getId(), dp);
				dp.add(l.getId());
			}
		}
		return dp;
	}
	
}
