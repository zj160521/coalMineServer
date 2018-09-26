package com.cm.controller;

import com.cm.entity.Worktype;
import com.cm.security.LoginManage;
import com.cm.service.WorktypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 工种管理
 * @author hetaiyun
 *
 */

@Scope("prototype")
@Controller
@RequestMapping("/worktype")
public class WorktypeController {
	
	@Autowired
	private ResultObj result;
	
	@Autowired
	private LoginManage loginManage;
	
	@Autowired
	private WorktypeService service;
	
	/**
	 * 添加或者修改工种
	 * @param worktype
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/addup",method=RequestMethod.POST)
	@ResponseBody
	public Object addWorktype(@RequestBody Worktype worktype,HttpServletRequest request){
		if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}
		try {
			Object per = loginManage.checkPermission(request);
			if(null!=per) return per;	
			int b = service.getWorktypeByname(worktype);
			if(b>0){
				return result.setStatus(-2, "部门名称已存在");
			}else{
				if(worktype.getId()>0){
					service.updateWorktype(worktype);
				}else{
					service.addWorktype(worktype);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
		
	}
	
	/**
	 * 查询所有工种信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getall",method=RequestMethod.POST)
	@ResponseBody
	public Object getAllWorktype(HttpServletRequest request){
		if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}
		try {
			
			List<Worktype> worktypes = service.getAllWorktype();
			if(worktypes==null||worktypes.size()<1||worktypes.isEmpty()){
				result.put("data", worktypes);
				return result.setStatus(0, "no data");
			}else{
				result.put("data", worktypes);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}

	@RequestMapping(value="/update",method=RequestMethod.POST)
	@ResponseBody
	public Object updateWorktype(@RequestBody Worktype worktype,HttpServletRequest request){
		if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}
		try {
				int b = service.getWorktypeByname(worktype);
				if(b>0){
					return result.setStatus(-2, "部门名称已存在");
				}else{
					service.updateWorktype(worktype);
				}
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");		
	}
	
	/**
	 * 删除工种信息
	 * @param worktype
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	@ResponseBody
	public Object deleteWorktype(@RequestBody Worktype worktype,HttpServletRequest request){
		if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}
		Object per = loginManage.checkPermission(request);
		if(null!=per) return per;
		try {
			service.deleteWorktype(worktype.getId());
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}
	
}
