package com.cm.controller;

import java.io.File;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;

import javax.crypto.Cipher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import sun.misc.BASE64Decoder;

import com.cm.security.LoginManage;
import com.cm.service.CoalmineService;
import com.cm.service.ConfigService;
import com.cm.service.StaticService;

@RequestMapping("/realtimedata")
@Controller
@Scope("prototype")
public class RealTimeDataController {
	
	@Autowired
	private LoginManage loginManage;
	@Autowired
	private ResultObj result;
	
	@Autowired
	private CoalmineService cmService;
	@Autowired
	private StaticService stcService;
	@Autowired
	private ConfigService cfgDao;
	private String pri_key = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKo++i9J9dzAFtbxwowKDCo2mxi7MXxE8A8VvssaydWjjgmEz/HHMPLOhi1182a1si4pWL0/MizKnquD7T2Bu4jpQ"
			+ "bAFnkNYEMEyq/kw904Xl0JCQHYFuvnI99RE8Q3KlTP6kEUGDjV34EL6vBGJcQvArLtj1xoP8y0nIfJ2Pw5TAgMBAAECgYAGGB8IllMwxceLhjf6n1l0IWRH7FuHIUieoZ6k0p6rASHSgWiYNR"
			+ "MxfecbtX8zDAoG0QAWNi7rn40ygpR5gS1fWDAKhmnhKgQIT6wW0VmD4hraaeyP78iy8BLhlvblri2nCPIhDH5+l96v7D47ZZi3ZSOzcj89s1eS/k7/N4peEQJBAPEtGGJY+lBoCxQMhGyzuzDm"
			+ "gcS1Un1ZE2pt+XNCVl2b+T8fxWJH3tRRR8wOY5uvtPiK1HM/IjT0T5qwQeH8Yk0CQQC0tcv3d/bDb7bOe9QzUFDQkUSpTdPWAgMX2OVPxjdq3Sls9oA5+fGNYEy0OgyqTjde0b4iRzlD1O0OhLq"
			+ "PSUMfAkEAh5FIvqezdRU2/PsYSR4yoAdCdLdT+h/jGRVefhqQ/6eYUJJkWp15tTFHQX3pIe9/s6IeT/XyHYAjaxmevxAmlQJBAKSdhvQjf9KAjZKDEsa7vyJ/coCXuQUWSCMNHbcR5aGfXgE4e4"
			+ "5UtUoIE1eKGcd6AM6LWhx3rR6xdFDpb9je8BkCQB0SpevGfOQkMk5i8xkEt9eeYP0fi8nv6eOUcK96EXbzs4jV2SAoQJ9oJegPtPROHbhIvVUmNQTbuP10Yjg59+8=";
	private final String decryptFilePath = "D:/RealtimeData2";
	
	@RequestMapping(value="/DecryptRealtimeData",method=RequestMethod.GET)
	@ResponseBody
	public void DecryptRealtimeData(HttpServletRequest request){
		try {
			ArrayList<String> files = getFiles("D:/RealtimeData");
			for(String str : files){
				String[] split = str.split("_");
				String key = split[2];
				String pwd = cfgDao.get(key);
				
				String AESKey = decrypt(pwd.getBytes("GBK"), getPrivateKey(pri_key));
//				AES.decrypt(str, decryptFilePath.concat(key), AESKey, 4, 4);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//RSA公钥
	public static PublicKey getPublicKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
	}
	
	//RSA私钥
	public static PrivateKey getPrivateKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
	}
	
	//RSA解密
	private static String decrypt(byte[] srcFile, PrivateKey privateKey)
			throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] bt_original = cipher.doFinal(srcFile);
		
		return new String(bt_original);
	}
	

	
	public static ArrayList<String> getFiles(String path) {
	    ArrayList<String> files = new ArrayList<String>();
	    File file = new File(path);
	    File[] tempList = file.listFiles();
	    
	    for (int i = 0; i < tempList.length; i++) {
	        if (tempList[i].isFile()) {
	            files.add(tempList[i].toString());
	        }
	        if (tempList[i].isDirectory()) {
	        }
	    }
	    return files;
	}
}
