package com.cm.controller;

import com.cm.entity.Permission;
import com.cm.entity.User;
import com.cm.entity.User_log;
import com.cm.entity.vo.NameTime;
import com.cm.security.LoginManage;
import com.cm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Scope("prototype")
@Controller
@RequestMapping(value = "/user")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private ResultObj result;
	@Autowired
	private LoginManage loginManage;

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	@ResponseBody
	public Object ai(HttpServletRequest request) throws Exception {
		Permission p=loginManage.getPermission(1);
		result.put("res", p);
		return result.setStatus(0, "连接测试");
	}
	
	@RequestMapping(value = "/addup", method = RequestMethod.POST)
	@ResponseBody
	public Object addUser(@RequestBody User user, HttpServletRequest request)
			throws NoSuchAlgorithmException {
		HttpSession session = request.getSession();
		User u = (User) session.getAttribute("user");
		
		if(user.getId()==u.getId()){
			return userService.addUpUser(user, request);
		}
		
		Object per = loginManage.checkPermission(request);
	    if (per != null) return per;
		return userService.addUpUser(user, request);
	}

	// 显示所有用户的方法
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@ResponseBody
	public Object allUser(HttpServletRequest request) {

		return userService.allUser(request);
	}

	@RequestMapping(value = "/myself", method = RequestMethod.GET)
	@ResponseBody
	public Object myself(HttpServletRequest request) {

		return userService.myself(request);
	}

	// 用户删除
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Object deleteUser(@RequestBody User user1, HttpServletRequest request) {

		return userService.deleteUser(user1, request);
	}

	// 登录方法
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public Object login(@RequestBody User user, HttpServletRequest request)
			throws Exception {

        return userService.login(user, request);
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	@ResponseBody
	public Object loginOut(HttpServletRequest request) {
		loginManage.logout(request);
		return result.setStatus(0, "ok");
	}

	@RequestMapping(value = "/islogin", method = RequestMethod.GET)
	@ResponseBody
	public Object isLogin(HttpServletRequest request) {

		if (loginManage.isUserLogin(request)) {
			HttpSession session = request.getSession();
			User u = (User) session.getAttribute("user");
			
			Permission p=loginManage.getPermission(u.getId());
			result.put("permission", p);
			
			result.put("user", u);
			result.setStatus(0, "");
		} else {
			result.setStatus(-1, "not login.");
		}
		return result.getResult();
	}
	
	@RequestMapping(value = "/getAllLog", method = RequestMethod.POST)
	@ResponseBody
	public Object getAllLog(HttpServletRequest request,@RequestBody NameTime nameTime) {
		Object ret = loginManage.isLogin(request);
		if (ret != null) return ret;
		
	    if(nameTime.getName()==null){
	    	nameTime.setName("");
	    }
	    nameTime.setName(nameTime.getName());
	    int nowpage=nameTime.getCur_page()+1;
		int rowsum=nameTime.getPage_rows();
		int start_row=nowpage*rowsum-rowsum;
		nameTime.setCur_page(start_row);
		nameTime.setPage_rows(rowsum);
		List<User_log> list=userService.getAllLog(nameTime);
		int pages=userService.getAllPage(nameTime);
	    
		result.put("cur_page", nowpage);
	    result.put("page_rows", rowsum);
	    result.put("total_rows", pages);
		result.put("rows", list);
		return result.setStatus(0, "ok");
	}
	
	@RequestMapping(value = "/verifyPassword",method=RequestMethod.POST)
	@ResponseBody
	public Object VerifyPassword(@RequestBody User user,HttpServletRequest request){
		String u = userService.VerifyPassword(user);
		if(u.equals("success")){
			return result.setStatus(0, "验证通过");
		}else{
			return result.setStatus(-2, "密码错误");
		}
	}
	
	@RequestMapping(value = "/getLogtype", method = RequestMethod.GET)
	@ResponseBody
	public Object getLogtype(HttpServletRequest request) {
		if (!loginManage.isUserLogin(request)) {
			return result.setStatus(-1, "no login");
		}
		
		result.put("data", userService.getLogtype());
		
		return result.setStatus(0, "ok");
	}
}
