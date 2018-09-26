package com.cm.controller;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.cm.dao.IUserDao;
import com.cm.entity.Config;
import com.cm.entity.vo.KeyVo;
import com.cm.security.LoginManage;
import com.cm.service.ConfigService;

@Scope("prototype")
@Controller
@RequestMapping("/rsa")
public class RSAKeyUploadController {
	@Autowired
	private ResultObj result;
	@Autowired
	private ConfigService cfgService;
	@Autowired
	private LoginManage loginManage;
	@Autowired
	private IUserDao userDao;
	
	
	@RequestMapping(value = "/keyUpload", method = RequestMethod.POST)
	@ResponseBody
	public Object keyUpload(HttpServletRequest request) throws ParseException, IOException{
		MultipartFile file = getFile(request, "file");
		String type = request.getParameter("type");
		String str = setK(file, type);
		loginManage.addLog(request, str, "上传密钥文件："+ file.getOriginalFilename(), 1516);
		return result.setStatus(0, "ok");
	}
	
	private MultipartFile getFile(HttpServletRequest request, String name) {
		MultipartFile file = ((MultipartHttpServletRequest) request).getFile(name);
		return file;
	}
	
	//通过文件形式传入密钥
	public String setK(MultipartFile file,String key) throws IOException{
		
		byte[] bytes = file.getBytes();
		String str = new String(bytes, "UTF-8");
		Config cfg = new Config();
		cfg.setK(key);
		cfg.setV(str);
		
		String value = cfgService.get(key);
		if(value == null){
			cfgService.add(cfg);
		}else{
			cfgService.update(cfg);
		}
		return str;
	}
	
	//通过字符串形式传入密钥
//	public void setKey(String value,String key){
//		if(value != null && !value.isEmpty()){
//			Config cfg = new Config();
//			cfg.setK(key);
//			cfg.setV(value);
//			
//			String str = cfgService.get(key);
//			if(str == null){
//				cfgService.add(cfg);
//			}else{
//				cfgService.update(cfg);
//			}
//		}
//	}
	
	//通过文件路径传入密钥
//	public void setKeys(String filePath,String key){
//		if(filePath != null && !filePath.isEmpty()){
//			String str = "";
//			File file = new File(filePath);
//			try {
//				FileInputStream is = new FileInputStream(file);
//				byte[] tempbytes = new byte[100];
//				@SuppressWarnings("unused")
//				int ch = 0;
//				while((ch=is.read(tempbytes))!=-1){
//					str = str + new String(tempbytes, "UTF-8");
//				}
//				is.close();
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			
//			Config cfg = new Config();
//			cfg.setK(key);
//			cfg.setV(str);
//			
//			String value = cfgService.get(key);
//			if(value == null){
//				cfgService.add(cfg);
//			}else{
//				cfgService.update(cfg);
//			}
//		}
//	}
	
	@RequestMapping(value = "/getKeys", method = RequestMethod.GET)
	@ResponseBody
	public Object getKeys(HttpServletRequest request) throws ParseException{
		Config ca = cfgService.getConfig("ca");
		Config priKey = cfgService.getConfig("priKey");
		Config pubKey = cfgService.getConfig("pubKey");
		
		KeyVo kv = new KeyVo();
		if(ca == null) 
			kv.setCa("未上传");
		else{
			kv.setCa("未上传");
		}
		
		if(priKey != null){
			kv.setPriKey("已上传");
		}else{
			kv.setPriKey("未上传");
		}
		if(pubKey != null){
			kv.setPubKey("已上传");
		}else{
			kv.setPubKey("未上传");
		}
		result.put("data", kv);
		return result.setStatus(0, "ok");
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

}
