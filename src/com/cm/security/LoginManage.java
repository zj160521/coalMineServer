package com.cm.security;

import com.cm.controller.LicenseController;
import com.cm.controller.ResultObj;
import com.cm.dao.IPermissionDao;
import com.cm.dao.IUserDao;
import com.cm.entity.License;
import com.cm.entity.Permission;
import com.cm.entity.User;
import com.cm.entity.User_log;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.LogOut;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class LoginManage {

	@Autowired
	private IUserDao userDao;
	@Autowired
	private IPermissionDao perDao;
	@Autowired
	private ResultObj result;

	static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	static List<String> list=Arrays.asList("uid","id","name","areaname");
	
	public static String md5sum(String str) throws NoSuchAlgorithmException {
		byte[] buf = str.getBytes();
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.update(buf);
		byte[] tmp = md5.digest();

		int j = tmp.length;
		char s[] = new char[j * 2];
		int k = 0;
		for (int i = 0; i < j; i++) {
			byte byte0 = tmp[i];
			s[k++] = hexDigits[byte0 >>> 4 & 0xf];
			s[k++] = hexDigits[byte0 & 0xf];
		}
		return new String(s);
	}

	public boolean isUserLogin(HttpServletRequest request) {

		boolean ret = false;

		try {

			HttpSession session = request.getSession();
			String authval = (String) session.getAttribute("auth");

			if (authval != null && authval.equals("user")) {
				User u = (User) session.getAttribute("user");
				if (u != null) {
					ret = true;
				}
			}

		} catch (Exception e) {
			ret = false;
		}

		return ret;
	}

	public User getLoginUser(HttpServletRequest request) {

		try {

			if (isUserLogin(request) == true) {
				HttpSession session = request.getSession();
				User u = (User) session.getAttribute("user");
				return u;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}

	public String login(HttpServletRequest request, User user) {

		HttpSession session = request.getSession();
		User u = userDao.getByName(user.getName());

		if (u == null) {
			return "帐号不存在！";
		}

		try {
			String inputpwd = md5sum(user.getPassword());
			if (u.getPassword().equals(inputpwd)) {
				session.setAttribute("auth", "user");
				session.removeAttribute("user");
				session.setAttribute("user", u);
				u.setPassword("******");
				addLog(request,JSONObject.fromObject(u).toString(),u.getName()+"用户登录",130);
				return "success";
			} else {
				return "密码错误！";
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "服务器异常！";
		}
	}

	public void logout(HttpServletRequest request) {

		try {
			HttpSession session = request.getSession();
			String authval = (String) session.getAttribute("auth");
			addLog(request,"","用户登出",129);
			if (authval != null) {
				User u=(User) session.getAttribute("user");
				session.removeAttribute("auth");
				session.removeAttribute("user");
				session.removeAttribute("permission");
				if(u!=null){
					addLog(request,JSONObject.fromObject(u).toString(),u.getName()+"用户登出",129);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 验证登录通用方法
	public Object isLogin(HttpServletRequest request) {
		if (isUserLogin(request)) {
			return null;
		} else {
			return result.setStatus(-1, "not login.");
		}
	}

	// 审核权限和存储日志
	public Object checkPermission(HttpServletRequest request) {
		User u = getLoginUser(request);
		String path=request.getServletPath();
		List<Permission> list = perDao.getAllPermissionByRole(u.getRole_id());
		for (Permission p : list) {
			if(p.getCtrlpath()!=null&&!p.getCtrlpath().equals("")){
				if (path.contains(p.getCtrlpath())) {
//					addLog(request,u,p.getId(),remark, operation2);
					return null;
				}
			}
		}
		return result.setStatus(-2, "no permission.");
//		return null;
	}

	//存储用户日志
	public void addLog(HttpServletRequest request,String remark, String operation2, int pid) {
		User_log log = new User_log();
        User u = getLoginUser(request);
        log.setUser_id(u.getId());
		log.setIp(getIpAddr(request));
		log.setPermission_id(pid);
		log.setRemark(remark);
		log.setOperation2(operation2);
		userDao.addLog(log);
	}
	
	@SuppressWarnings("unchecked")
	public String getOperation2(String remark){
		Map<String,Object> map = JSONObject.fromObject(remark);
		StringBuffer sb=new StringBuffer();
		for(Entry<String,Object> e:map.entrySet()){
			if(e.getValue() instanceof List){
//				for(String s:(List<String>)e.getValue()){
//					setString(sb,s,list);
//				}
			}else{
				setString(sb,e.getKey()+":"+e.getValue(),list);
			}
		}
		return sb.toString();
	}
	
	public void setString(StringBuffer sb,String key,List<String> strlist){
		for(String s:strlist){
			if(key.contains(s)){
				sb.append(key+";");
			}
		}
	}
	
	// 获取用户角色和权限
	public Permission getPermission(int id) {
		List<Permission> list = userDao.getPermission(id);
		if(list!=null&&list.size()>0){
			int userid = list.get(0).getUserid();
			int roleid = list.get(0).getRoleid();
			String username = list.get(0).getUsername();
			String rolename = list.get(0).getRolename();
			List<Permission> list1 = perDao.getAllPermission();
			for (Permission p : list1) {
				p.setUserid(userid);
				p.setRoleid(roleid);
				p.setUsername(username);
				p.setRolename(rolename);
			}
			Permission pm=null;
			for (Permission p : list1) {
				if(p.getId()==10){
					pm=p;
				}
				for (Permission p1 : list) {
					if (p.getId() == p1.getId()) {
						p.setEnable(true);
						break;
					}
				}
			}
			
			//主菜单为false，则子菜单全为false
			boolean huanjing=true;
			boolean dingwei=true;
			boolean yuying=true;
			boolean shipin=true;
			for (Permission p : list1) {
				if(p.isEnable()==false){
					if(p.getId()==150){
						huanjing=false;
					}else if(p.getId()==200){
						dingwei=false;
					}else if(p.getId()==300){
						shipin=false;
					}else if(p.getId()==350){
						yuying=false;
					}
				}
			}

			//验证licanse
			String ip=null;
			try {
				ip = InetAddress.getLocalHost().getHostAddress().toString();
				String res=LicenseController.runDmi(ip,"");
				License l=LicenseController.getStatus(res,ip);
				if("已激活".equals(l.getLicense())||"试用版".equals(l.getLicense())){
					//查看开放的菜单
					Pattern p = Pattern.compile("\t|\r|\n");
					Matcher m = p.matcher(res);
					String res2 = m.replaceAll(" ");
					LogOut.log.debug("菜单："+res2);
					String res3 = res2.replace("(", "");
					String res4 = res3.replace(")", "");
					String res5 = res4.replace(",", "");
					String[] a = res5.split("\\s{1,}");

					if(!"1".equals(a[3])){
						huanjing=false;
					}
					if(!"1".equals(a[4])){
						dingwei=false;
					}
					if(!"1".equals(a[5])){
						yuying=false;
					}
					if(!"1".equals(a[6])){
						shipin=false;
					}
					LogOut.log.debug("菜单2："+a[3]+" "+a[4]+" "+a[5]+" "+a[6]);
					LogOut.log.debug("菜单2："+huanjing+" "+dingwei+" "+yuying+" "+shipin);
				}else if("开发者模式".equals(l.getLicense())){
					huanjing=true;
					dingwei=true;
					yuying=true;
					shipin=true;
				}else{
					huanjing=false;
					dingwei=false;
					yuying=false;
					shipin=false;
				}
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			
			if(pm!=null){
				Permission per2 = recursiveTree(110, list1);
				pm.getList().add(per2);
				
//				Permission per3 = recursiveTree(140, list1);
//				pm.getList().add(per3);
				
				if(huanjing==false){
					Permission per = recursiveTree2(150, list1);
					pm.getList().add(per);
				}else{
					Permission per = recursiveTree(150, list1);
					pm.getList().add(per);
				}
				
				if(dingwei==false){
					Permission per = recursiveTree2(200, list1);
					pm.getList().add(per);
				}else{
					Permission per = recursiveTree(200, list1);
					pm.getList().add(per);
				}
				
				if(yuying==false){
					Permission per = recursiveTree2(350, list1);
					pm.getList().add(per);
				}else{
					Permission per = recursiveTree(350, list1);
					pm.getList().add(per);
				}
				
				if(shipin==false){
					Permission per = recursiveTree2(300, list1);
					pm.getList().add(per);
				}else{
					Permission per = recursiveTree(300, list1);
					pm.getList().add(per);
				}
			}
			
			return pm;
		}
		
		return null;
	}

	public Permission recursiveTree(int id, List<Permission> list) {
		Permission node = getTreeNode(id, list);
		List<Permission> childTreeNodes = getChildTreeNodes(id, list);
		// 遍历子节点
		for (Permission child : childTreeNodes) {
			// 递归
			Permission n = recursiveTree(child.getId(), list);
			if (n != null) {
				node.getList().add(n);
			}
		}
		return node;
	}
	
	public Permission recursiveTree2(int id, List<Permission> list) {
		Permission node = getTreeNode(id, list);
		List<Permission> childTreeNodes = getChildTreeNodes(id, list);
		// 遍历子节点
		for (Permission child : childTreeNodes) {
			// 递归
			Permission n = recursiveTree2(child.getId(), list);
			if (n != null) {
				n.setEnable(false);
				node.getList().add(n);
			}
		}
		return node;
	}
	public Permission getTreeNode(int id, List<Permission> list) {
		for (Permission per : list) {
			if (per.getId() == id) {
				return per;
			}
		}
		return null;
	}

	public List<Permission> getChildTreeNodes(int id, List<Permission> list) {
		List<Permission> reslist = new ArrayList<Permission>();
		for (Permission per : list) {
			if (per.getPid() == id) {
				reslist.add(per);
			}
		}
		return reslist;
	}

	// 获得用户ip地址
	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	public String VerifyPassword(User user){
		User u = userDao.getByName("admin");
		try {
			String inputpwd = md5sum(user.getPassword());
			if (u.getPassword().equals(inputpwd)) {
		
				return "success";
			} else {
				return "密码错误！";
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "服务器异常！";
		}
	}
	
	//获取header
	@SuppressWarnings("unused")
	private Map<String, String> getHeadersInfo(HttpServletRequest request) {
	    Map<String, String> map = new HashMap<String, String>();
	    Enumeration<String> headerNames = request.getHeaderNames();
	    while (headerNames.hasMoreElements()) {
	        String key = (String) headerNames.nextElement();
	        String value = request.getHeader(key);
	        map.put(key, value);
	    }
	    return map;
	}
}
