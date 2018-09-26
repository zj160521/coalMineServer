package com.cm.controller;

import com.cm.controller.extrun.IRunCmd;
import com.cm.controller.extrun.RunDmi;
import com.cm.entity.License;
import com.cm.security.ConfigUtil;
import com.cm.security.LoginManage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import util.LogOut;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Scope("prototype")
@Controller
@RequestMapping(value = "/license")
public class LicenseController {
	@Autowired
	private LoginManage loginManage;
	@Autowired
	private ResultObj result;

	@RequestMapping(value = "/addup", method = RequestMethod.POST)
	@ResponseBody
	public Object addup(HttpServletRequest request,@RequestBody License license) {
		Object ret = loginManage.isLogin(request);
		if (ret != null) return ret;
		Object per = loginManage.checkPermission(request);
		if (per != null)
			return per;
	    try {
	    	
	    	StringBuffer sb=new StringBuffer();
	    	for(License l:license.getLicenses()){
	    		String status=runDmi(l.getIp()," -l "+l.getLicense());
	    		if(status.contains("verify exception")){
	    			sb.append(l.getIp()+"：导入licens错误！ ");
	    		}else if(status.contains("ok")){
	    			sb.append(l.getIp()+"：导入licens成功！ ");
	    		}
	    	}
	    	
	    	result.put("data", sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-2, "license输入错误");
		}
		
		return result.setStatus(0, "ok");
	}
	
	@RequestMapping(value = "/getStatus", method = RequestMethod.GET)
	@ResponseBody
	public Object getStatus(HttpServletRequest request) {
//		Object ret = loginManage.isLogin(request);
//		if (ret != null) return ret;
		
		try {
			String ip = InetAddress.getLocalHost().getHostAddress().toString();
			String res=runDmi(ip,"");
			License l=getStatus(res,ip);
			result.put("data", l);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return result.setStatus(-2, "错误！");
		}
		
		return result.setStatus(0, "ok");
	}
	
	@RequestMapping(value = "/getAll", method = RequestMethod.GET)
	@ResponseBody
	public Object getAll(HttpServletRequest request) {
		Object ret = loginManage.isLogin(request);
		if (ret != null) return ret;
		
		String ma=ConfigUtil.getInstance().getSys_peerip();
		String pe=ConfigUtil.getInstance().getSys_ip();
		String res=runDmi(ma,"");
		String res2=runDmi(pe,"");
		List<License> list=new ArrayList<License>();
		list.add(getStatus(res,ma));
		list.add(getStatus(res2,pe));
		result.put("data", list);
		
		return result.setStatus(0, "ok");
	}
	
	public static License getStatus(String res,String ip){
		License license=new License();
		try {
			if(res.contains("No imported license.")){
				//未导入license
				license.setStatus(-1);
				license.setIp(ip);
				license.setDay("license未导入");
				license.setLicense("license未导入");
			}else{
				String[] a = res.split("\\s{1,}");
				String time=a[0];
				String cpu=a[1];
				String sign=a[2];
				Date date=new Date();
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				Date stime = null;
				try {
					stime = format.parse(time);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				if(stime.before(date)){
					//已过期
					license.setStatus(1);
					license.setIp(ip);
					license.setDay(time);
					license.setLicense("license已过期");
				}else{
					if("True".equals(cpu)&&"True".equals(sign)){
						license.setStatus(0);
						license.setIp(ip);
						license.setDay(time);
						license.setLicense("已激活");
					}else if("True".equals(cpu)&&"False".equals(sign)){
						license.setStatus(1);
						license.setIp(ip);
						license.setDay(time);
						license.setLicense("试用版");
					}else if("False".equals(cpu)){
						license.setStatus(1);
						license.setIp(ip);
						license.setDay(time);
						license.setLicense("授权失败");
					}
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			LogOut.log.debug("菜单开发者模式：");
			e.printStackTrace();
			license.setLicense("开发者模式");
		}
		return license;
	}
	
	@RequestMapping(value = "/getMacCode", method = RequestMethod.GET)
	@ResponseBody
	public Object getMacCode(HttpServletRequest request) {
		Object ret = loginManage.isLogin(request);
		if (ret != null) return ret;
		String ma=ConfigUtil.getInstance().getSys_peerip();
		String pe=ConfigUtil.getInstance().getSys_ip();
		String cpu=runDmi(ma," -d");
		String cpu2=runDmi(pe," -d");
		String res=ma+"："+cpu;
		String res2=pe+"："+cpu2;
		List<String> list=new ArrayList<String>();
		list.add(res);
		list.add(res2);
		result.put("data", list);
		
		return result.setStatus(0, "ok");
	}
	
	public static String runDmi(String ip,String order){
    	IRunCmd runcmd = new RunDmi();
    	Map<String,String> m=new HashMap<String,String>();
    	m.put("ip", ip);
    	m.put("order", order);
		if (runcmd.init(m) != 0) {
			return "查询命令初始化失败！";
		}else{
			if (runcmd.runCli() != 0) {
				return "查询命令运行失败！";
			}else{
				String r=(String) runcmd.doEnd();
				if(r.equals("-1")){
					return "查询命令回复出错！";
				}else{
					LogOut.log.debug("信息："+r);
					return r;
				}
			}
		}
    }
}
