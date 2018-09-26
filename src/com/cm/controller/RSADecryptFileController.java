package com.cm.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Security;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.crypto.Cipher;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.cm.dao.IRSAKeyDao;
import com.cm.security.LoginManage;
import com.cm.service.ConfigService;

import sun.misc.BASE64Decoder;
import util.StaticUtilMethod;

@Scope("prototype")
@Controller
@RequestMapping("/rsa")
public class RSADecryptFileController {
	@Autowired
	private ResultObj result;
	@Autowired
	private IRSAKeyDao rsaDao;
	@Autowired
	private ConfigService cfgService;
	@Autowired
	private LoginManage loginManage;
	private String PADDING = "RSA/NONE/NoPadding";
	private String PROVIDER = "BC";
	
	@RequestMapping(value = "/decryptFile", method = RequestMethod.POST)
	@ResponseBody
	public Object decryptFile(HttpServletRequest request) {
		try {
			// 验证是否导入私钥
			String priKey = cfgService.get("priKey");
			if(priKey == null || priKey == ""){
				return result.setStatus(-2, "请导入私钥！");
			}
			
			MultipartFile file = getFile(request, "file");
			String filename = file.getOriginalFilename();
			// 去除文件名中的括号
			String wipeOffParenthesisFileName = wipeOffParenthesis(filename);
			// 文件解密
			String encryptKey = rsaDao.get(wipeOffParenthesisFileName);
			Base64 base64 = new Base64();
			byte[] cipherTextArray = base64.decode(encryptKey);
			String AESKey = decrypt(cipherTextArray, getPrivateKey(priKey));
			String decrypt = AES.decrypt(file.getInputStream(), "/opt/decrypt_file/" + wipeOffParenthesisFileName, AESKey, 4, 4);
			String str = decrypt.replaceAll("\u0000", "");/*解密后数据*/
			// 对比解密后内容与明文是否一致
			String undecryptData = getUndecryptData(wipeOffParenthesisFileName).trim();/*明文文件数据*/
			boolean isEqual = str.trim().equals(undecryptData) ? true : false;
			loginManage.addLog(request, str, "解密加密文件："+ filename, 1517);
			result.put("isEqual", isEqual);
			result.put("data", str);
		} catch(Exception e) {
			e.printStackTrace();
			return result.setStatus(-2, "出错啦！");
		}
		return result.setStatus(0, "ok");
	}
	
	private MultipartFile getFile(HttpServletRequest request, String name) {
		MultipartFile file = ((MultipartHttpServletRequest) request).getFile(name);
		return file;
	}

	// rsa解密
	public String decrypt(byte[] text, PrivateKey key) {
		byte[] dectyptedText = null;
		try {
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			final Cipher cipher = Cipher.getInstance(PADDING, PROVIDER);
			cipher.init(Cipher.DECRYPT_MODE, key);
			dectyptedText = cipher.doFinal(text);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new String(dectyptedText);
	}
	
	// RSA私钥
	public PrivateKey getPrivateKey(String key) throws Exception {
		byte[] keyBytes = (new BASE64Decoder()).decodeBuffer(key);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA", new BouncyCastleProvider());
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		return privateKey;
	}
	
	// 获取明文文件内容
	public String getUndecryptData(String fileName){
		String unencryptFilePath = cfgService.get("unencrypt_file");
		if(!StaticUtilMethod.notNullOrEmptyStr(unencryptFilePath)){
			unencryptFilePath = "/opt/unencrypt_file";
		}
		
		String str = null;
		try {
			File file = new File(unencryptFilePath+"/"+fileName);
			str = "";
			InputStream is = new FileInputStream(file);
			byte[] tempbytes = new byte[1024];
			int ch = 0;
			while((ch = is.read(tempbytes)) != -1){
				str = str + new String(tempbytes,0,ch,"UTF-8");
			}
			is.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	// 去除文件名中的括号
	public String wipeOffParenthesis(String filename){
		String[] split = filename.split("\\(");
		if(split.length > 1){
			String[] split2 = split[1].split("\\)");
			if(split2.length > 1){
				filename = split[0].trim()+split2[1].trim();
			}
		}
		return filename;
	}
	
}

