package com.cm.service;

import com.cm.controller.AES;
import com.cm.controller.EncryptDataFileUtils;
import com.cm.dao.GD5ReportDao;
import com.cm.dao.IRSAKeyDao;
import com.cm.entity.Config;
import com.cm.entity.GD5Report;
import com.cm.entity.RsaKey;
import com.cm.entity.vo.CoalmineVo;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import sun.misc.BASE64Decoder;
import util.GetHostName;
import util.RedisPool;
import util.StaticUtilMethod;

import javax.crypto.Cipher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class TimerRealTimeDataEncryptService {

	@Autowired
	private CoalmineService cmService;
	@Autowired
	private StaticService stcService;
	@Autowired
	private GD5ReportDao gd5Dao;
	@Autowired
	private ConfigService cfgService;
	@Autowired
	private IRSAKeyDao rsaService;

	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private DateFormat df2 = new SimpleDateFormat("yyyyMMddHHmmss");
	private DateFormat df3 = new SimpleDateFormat("yyyy_MM_dd");
	private String endTime;
	private String startTime;
	private String now2;
	private String beforeTime;
	private final String rootPath = "/opt/";
	private String encryptFilePath;
	private String unencryptFilePath;
	private final String decryptFilePath = "decrypt_file";
	private final String splitFlag = "~";
	private String AESKey;
	private String PADDING = "RSA/NONE/NoPadding";
	private String PROVIDER = "BC";

	@Scheduled(cron = "0/30 * * * * ?")
	public void EncryptData() {
//		String lockKey = "SSAndGD5DataEncrypt";
//        String requestId = UUID.randomUUID().toString();
//        boolean lock = RedisPool.tryGetDistributedLock(lockKey, requestId, 15000l);
//        if (lock) {
        	try {
	        	//设置明文密文储存文件路径
	    		setPath();
	    		// 导入公钥后才进行加密
	    		String pubKey = cfgService.get("pubKey");
	    		if(StaticUtilMethod.notNullOrEmptyStr(pubKey) && System.getProperty("os.name").startsWith("Linux")){
					createFile();// 创建文件夹
					getStartEndTime();// 获取需保存数据的开始和结束时间
					// 获取矿井编号
					String mineNum = stcService.getV(8);
					if(mineNum == null || mineNum.isEmpty()) mineNum = "未设置矿井编号";
					// 加密实时数据并保存至指定文件夹
					encryptRealTimeData(getOriginalRealTimeData(), mineNum, "_SSSJ_");
					// 加密GD5数据并保存至指定文件夹
					encryptRealTimeData(getOriginalGD5Data(), mineNum, "_LJSJ_");
	    		}
	    		// 删除一小时以前数据文件
	    		deletefile(encryptFilePath);
	    		deletefile(unencryptFilePath);
    		} catch (Exception e) {
				e.printStackTrace();
			} 
//	    		finally {
//				RedisPool.releaseDistributedLock(lockKey, requestId);
//			}
//        }
	}
	
	//设置明文密文储存文件路径
	public void setPath(){
		encryptFilePath = cfgService.get("encrypt_file");
		unencryptFilePath = cfgService.get("unencrypt_file");
		if(!StaticUtilMethod.notNullOrEmptyStr(encryptFilePath)){
			encryptFilePath = "/opt/encrypt_file";
		}
		if(!StaticUtilMethod.notNullOrEmptyStr(unencryptFilePath)){
			unencryptFilePath = "/opt/unencrypt_file";
		}
	}
	
	/**
	 * @param sb 元数据
	 * @param mineNum 矿井编号
	 * @param fileType 文件类型
	 */
	public void encryptRealTimeData(StringBuffer sb, String mineNum, String fileType){
		// 数据文件名
		String fileName = "/" + mineNum + fileType + now2 + ".TXT";
		// 加密并写数据
		produceData(fileName, sb);
	}
	
	// 实时数据元数据
	public StringBuffer getOriginalRealTimeData(){
		String tableName = "t_coalMine_" + df3.format(new Date());
		List<CoalmineVo> dataByTime = cmService.getDataByTime(startTime, endTime, tableName);// 查询实时数据
		StringBuffer sb = getRTSB(dataByTime, endTime);// 实时数据拼接成指定格式
		return sb;
	}
	
	// 累积量元数据
	public StringBuffer getOriginalGD5Data(){
		String tableName = "t_gd5_" + df3.format(new Date());
		List<GD5Report> all = gd5Dao.getAllByTime(startTime, endTime, tableName);
		StringBuffer gd5Sb = getGD5SB(all, endTime);
		return gd5Sb;
	}

	//创建文件夹
	public void createFile() {
		//创建密文件夹
		File f = new File(encryptFilePath);
		if (!f.exists()) {
			f.mkdir();
		}
		//创建明文文件夹
		File f2 = new File(unencryptFilePath);
		if (!f2.exists()) {
			f2.mkdir();
		}
		//创建解密文件夹
		File f4= new File(rootPath, decryptFilePath);
		if (!f4.exists()) {
			f4.mkdir();
		}
	}

	public void getStartEndTime() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.SECOND, -30);
		endTime = df.format(cal.getTime());
		now2 = df2.format(cal.getTime());
		cal.add(Calendar.SECOND, -30);
		startTime = df.format(cal.getTime());
		cal.add(Calendar.SECOND, 30);
		cal.add(Calendar.HOUR, -1);
		beforeTime = df2.format(cal.getTime());
	}

	public StringBuffer getRTSB(List<CoalmineVo> dataByTime, String now) {
		StringBuffer sb = new StringBuffer();
		sb.append(now + ";" + dataByTime.size() + splitFlag + "\r\n");
		if(StaticUtilMethod.notNullOrEmptyList(dataByTime)){
			for (int i = 0; i < dataByTime.size(); i++) {
				CoalmineVo cv = dataByTime.get(i);
				if (cv.getIp() != null && cv.getIp() != "") {
					StringBuffer definitionOfMeasurePoints = null;
					if (cv.getType() == 16 && cv.getDev_id() > 0) {
						definitionOfMeasurePoints = EncryptDataFileUtils
								.definitionOfMeasurePoints(16, cv.getDev_id());
					} else if (cv.getType() > 0 && cv.getDev_id() > 0) {
						definitionOfMeasurePoints = EncryptDataFileUtils
								.definitionOfMeasurePoints(cv.getType(),
										cv.getDev_id());
					}
					if (definitionOfMeasurePoints != null)
						sb.append(definitionOfMeasurePoints + ";" + cv.getValue()
								+ ";" + cv.getStatus() + splitFlag);

					if (i == dataByTime.size() - 1)
						sb.append("||");
				}
			}
		}else{
			sb.append("||");
		}
		return sb;
	}

	public StringBuffer getGD5SB(List<GD5Report> dataByTime, String now) {
		StringBuffer sb = new StringBuffer();
		sb.append(now + ";" + dataByTime.size() + splitFlag + "\r\n");
		if(StaticUtilMethod.notNullOrEmptyList(dataByTime)){
			for (int i = 0; i < dataByTime.size(); i++) {
				GD5Report cv = dataByTime.get(i);
				if (cv.getIp() != null && cv.getIp() != "") {
					StringBuffer definitionOfMeasurePoints;
					if ("16".equals(cv.getType())) {
						definitionOfMeasurePoints = EncryptDataFileUtils
								.definitionOfMeasurePoints(16, cv.getDevid());
					} else {
						definitionOfMeasurePoints = EncryptDataFileUtils
								.definitionOfMeasurePoints(
										Integer.parseInt(cv.getType()),
										cv.getDevid());
					}
					sb.append(definitionOfMeasurePoints + ";" + "0;"
							+ cv.getFlow_work_sum() + splitFlag);

					if (i == dataByTime.size() - 1)
						sb.append("||");
				}
			}
		}else{
			sb.append("||");
		}
		return sb;
	}

	/**
	 * 加密并写进文件夹
	 * @param fileName 文件名
	 * @param sb 数据
	 */
	public void produceData(String fileName, StringBuffer sb) {
		setPath();//设置文件储存路径
		String pubKey = cfgService.get("pubKey");
		if(pubKey != null){
			String filename = fileName.substring(1, fileName.length());
			String encryptKey = rsaService.get(filename);
			if(encryptKey == null){
				AESKey = getRandomString(16);
			}else{
				return;
			}
			String encryptFile = encryptFilePath.concat(fileName);
			try {
				// 加密数据并写到指定文件
				AES.encrypt(sb, encryptFile, AESKey, 4, 4);
				// 明文写到指定文件
				OutputStream os = new FileOutputStream(unencryptFilePath.concat(fileName));
				os.write(sb.toString().getBytes());
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			RsaEncrypt(fileName);//加密AES密钥
//			copyFileToSlave(filename);
		}
	}
	
	public void copyFileToSlave (String fileName) {
		try {
			List<Config> configByV = cfgService.getConfigByV("hostName");
			String hostName = GetHostName.getHostName();
			for (Config cfg : configByV) {
				if (!cfg.getK().equals(hostName)) hostName = cfg.getK();
				break;
			}
			
			try {
				String[] cmd = {"scp", unencryptFilePath + fileName, "root@"+ hostName +":" + unencryptFilePath+"/"};
				int waitFor = Runtime.getRuntime().exec(cmd).waitFor();
				if (waitFor == 0) {
					String[] cmd2 = {"scp", encryptFilePath + fileName, "root@"+ hostName +":" + encryptFilePath+"/"};
					Runtime.getRuntime().exec(cmd2);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * RSA加密AES密码并存入数据库
	 * @param fileName
	 */
	public void RsaEncrypt(String fileName){
		try {
			//加密aes的key
			byte[] AESKeyEncrypt = encrypt(AESKey, getPublicKey(cfgService.get("pubKey")));
			//密文存进数据库
			Base64 base64 = new Base64();
			String cipherTextBase64 = base64.encodeToString(AESKeyEncrypt);
			RsaKey rk = new RsaKey();
			rk.setFilename(fileName.substring(1, fileName.length()));
			rk.setEncryptKey(cipherTextBase64);
//			rk.setUndecryptKey(AESKey);
			rsaService.add(rk);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// RSA公钥
	public static PublicKey getPublicKey(String key) throws Exception {
		byte[] keyBytes = (new BASE64Decoder()).decodeBuffer(key);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA", new BouncyCastleProvider());
		PublicKey publicKey = keyFactory.generatePublic(keySpec);
		return publicKey;
	}

	// RSA私钥
	public static PrivateKey getPrivateKey(String key) throws Exception {
		byte[] keyBytes = (new BASE64Decoder()).decodeBuffer(key);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA", new BouncyCastleProvider());
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		return privateKey;
	}
	
	//rsa加密
	public byte[] encrypt(String text, PublicKey key) {
		byte[] cipherText = null;
		try {
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			final Cipher cipher = Cipher.getInstance(PADDING, PROVIDER);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			cipherText = cipher.doFinal(text.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cipherText;
	}

	//rsa解密
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
	
	// 生成AES密钥
	public static String getRandomString(int length) {
		// 定义一个字符串（A-Z，a-z，0-9）即62位；
		String str = "zxcvbnmlkjhgfdsaqwertyuiopQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
		// 由Random生成随机数
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		// 长度为几就循环几次
		for (int i = 0; i < length; ++i) {
			// 产生0-61的数字
			int number = random.nextInt(62);
			// 将产生的数字通过length次承载到sb中
			sb.append(str.charAt(number));
		}
		return sb.toString();
	}
	
	/**
	 * 删除一小时前文件
	 */
	public void deletefile(String delpath){
		try {
			//删除一小时前的明文和密文文件
			File file = new File(delpath);
			if (file.isDirectory()) {
				String[] filelist = file.list();
				for (int i = 0; i < filelist.length; i++) {
					String fileName = filelist[i];
					String[] split = fileName.split("_");
					String name = split[split.length -1].split(".TXT")[0];
					
					long parseLong = Long.parseLong(name);
					long parseLong2 = Long.parseLong(beforeTime);
					
					if(parseLong < parseLong2){
						File delFile  = new File(delpath.concat("/"+fileName));
						delFile.delete();
					}
				}
			}
			//删除rsa加密的aes密钥
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, -3);
			String filltime = df.format(cal.getTime());
			rsaService.delete(filltime);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
