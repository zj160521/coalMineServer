package com.cm.service;

import com.cm.controller.ResultObj;
import com.cm.dao.IStaticDao;
import com.cm.dao.IUserDao;
import com.cm.entity.Permission;
import com.cm.entity.User;
import com.cm.entity.User_log;
import com.cm.entity.vo.NameTime;
import com.cm.security.LoginManage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

@Service("userService")
public class UserService {
	// 此类中调用IUserDao中所有方法
	@Autowired
	private IUserDao userDao;
	@Autowired
	private ResultObj result;
	@Autowired
	private LoginManage loginManage;
	@Autowired
	private IStaticDao staticDao;

	public Object addUpUser(User user, HttpServletRequest request)
			throws NoSuchAlgorithmException {

		try {
			if (user.getPassword().length() > 0 && user.getPassword().length() < 6){
				return result.setStatus(-2, "密码至少6位！");
			}else if(user.getPassword().length() >= 6){
				user.setPassword(LoginManage.md5sum(user.getPassword()));
			}
			
			if (user.getId() > 0) {
				userDao.update(user);
			} else {
				userDao.add(user);
				result.put("id", user.getId());
			}

		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-3, "exception");
		}

		return result.setStatus(0, "操作成功！");
	}

	public Object allUser(HttpServletRequest request) {

		try {

			User user = loginManage.getLoginUser(request);

			List<User> userList = new ArrayList<User>();
			if (user.getId() == 1)
				userList = userDao.getAll();
			if (userList != null) {
				for (User u : userList) {
					u.setPassword("******");
				}
			}
			result.put("userlist", userList);

		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-3, "exception");
		}

		return result.setStatus(0, "操作成功");
	}

	public Object myself(HttpServletRequest request) {

		try {

			User u = loginManage.getLoginUser(request);

			result.put("user", u);

		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-3, "exception");
		}

		return result.setStatus(0, "操作成功");
	}

	public Object deleteUser(User user1, HttpServletRequest request) {

		Object ret = loginManage.isLogin(request);
		if (ret != null)
			return ret;

		try {

			User u = userDao.getById(user1.getId());

			userDao.delete(u.getId());

		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-3, "exception");
		}

		return result.setStatus(0, "删除成功");
	}

	public Object login(User user, HttpServletRequest request) throws Exception {

		User user1 = user;
		if (user1.getName() == null || user1.getPassword() == null
				|| user1.getName().equals("") || user1.getPassword().equals("")) {
			return result.setStatus(-3, "No user and password.");
		}
		String ret = loginManage.login(request, user1);
		if (ret.equals("success")) {
			User u = loginManage.getLoginUser(request);
			Permission p = loginManage.getPermission(u.getId());
			result.put("permission", p);

			result.put("user", u);
			result.setStatus(0, ret);
		} else {
			result.setStatus(-3, ret);
		}
        String v = staticDao.getV(8);
		if(null==v||"".equals(v)){
		    result.put("isuse",0);
        } else {
		    result.put("isuse",1);
        }

        return result.getResult();
	}

	public List<User_log> getAllLog(NameTime nameTime) {
		return userDao.getAllLog(nameTime);
	}

	public int getAllPage(NameTime nameTime) {
		return userDao.getAllPage(nameTime);
	}

	public List<User> getUserByRoleId(LinkedHashSet<Integer> listRoleId) {

		List<User> list = new ArrayList<User>();
		for (Integer RoleId : listRoleId) {
			User user = userDao.getUserByRoleId(RoleId);
			list.add(user);
		}
		return list;

	}
	
	public String VerifyPassword(User user){
		String u = loginManage.VerifyPassword(user);
		return u;
	}
	
	public List<Permission> getLogtype(){
		return userDao.getLogtype();
	}
}
