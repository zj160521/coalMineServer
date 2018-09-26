package com.cm.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;



import com.cm.entity.Department;
import com.cm.entity.Workbasic;
import com.cm.entity.Worktype;
import com.cm.security.LoginManage;
import com.cm.service.DepartmentService;
import com.cm.service.WorkbasicService;
import com.cm.service.WorktypeService;

/**
 * 员工基本信息
 * @author hetaiyun
 *
 */

@Scope("prototype")
@Controller
@RequestMapping("/workbasic")
public class WorkbasicController {

	@Autowired
	private ResultObj result;
	
	@Autowired
	private LoginManage loginManage;
	
	@Autowired
	private WorkbasicService service;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private WorktypeService worktypeService;
	
	@RequestMapping(value="/addup",method=RequestMethod.POST)
	@ResponseBody
	public Object addWorkbasic(@RequestBody Workbasic workbasic,HttpServletRequest request){
		if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}
		try {
				int a = service.getWorkbasicbyMarker(workbasic).size();
				if(a>0){
					return result.setStatus(-2, "名称已存在");
				}else{
					if(workbasic.getId()>0){
						service.updateWorkbasic(workbasic);
					}else{
						service.addWorkbasic(workbasic);
					}
				}
				
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}
	
	@RequestMapping(value="/getall",method=RequestMethod.POST)
	@ResponseBody
	public Object getAllWorktype(@RequestBody Workbasic workbasic,HttpServletRequest request){
		if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			List<Worktype> worktypes = worktypeService.getAllWorktype();
			List<Department> departments = departmentService.getAllDepartment();
			List<Workbasic> workbasics = service.getAllWorkbasic();
			List<Workbasic> position = service.getallposition();
			map.put("departments", departments);
			map.put("worktypes", worktypes);
			map.put("duty", workbasics);
			map.put("position", position);
			result.put("data", map);
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}
	
	@RequestMapping(value="/update",method=RequestMethod.POST)
	@ResponseBody
	public Object updateWorkbasic(@RequestBody Workbasic workbasic,HttpServletRequest request){
		if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}
		try {
				int b = service.getWorkbasicbyMarker(workbasic).size();
				if(b>0){
					return result.setStatus(-2, "名称已存在");
				}else{
					service.updateWorkbasic(workbasic);
				}
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");		
	}
	
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	@ResponseBody
	public Object deleteWorkbasic(@RequestBody Workbasic workbasic,HttpServletRequest request){
		if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}
		try {
			service.deleteWorkbasic(workbasic);
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}
}
