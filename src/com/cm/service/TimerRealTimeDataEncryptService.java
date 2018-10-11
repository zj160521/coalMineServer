package com.cm.service;

import com.cm.controller.AES;
import com.cm.controller.EncryptDataFileUtils;
import com.cm.dao.GD5ReportDao;
import com.cm.dao.IRSAKeyDao;
import com.cm.entity.EnvClasses;
import com.cm.entity.GD5Report;
import com.cm.entity.RsaKey;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import sun.misc.BASE64Decoder;
import util.RedisPool;
import util.UtilMethod;

import javax.crypto.Cipher;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
	@Autowired
	private EnvClassesService envClassesService;

	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private DateFormat df2 = new SimpleDateFormat("yyyyMMddHHmmss");
	private DateFormat df3 = new SimpleDateFormat("yyyy_MM_dd");
	private DateFormat df4 = new SimpleDateFormat("yyyy-MM-dd");
	private String today;
	private String tomorrow;
	private String endTime;
	private String startTime;
	private String now2;
	private final String rootPath = "/opt/";
	private String encryptFilePath;
	private String unencryptFilePath;
	private final String decryptFilePath = "decrypt_file";
	private final String splitFlag = "~";
	private final String lineFeed = "\r\n";
	private String AESKey;
	private String PADDING = "RSA/NONE/NoPadding";
	private String PROVIDER = "BC";

	@Scheduled(cron = "0/30 * * * * ?")
	public void EncryptData() {
//		String lockKey = "GD5DataEncrypt";
//        String requestId = UUID.randomUUID().toString();
//        boolean lock = RedisPool.tryGetDistributedLock(lockKey, requestId, 15000l);
//        if (lock) {
        	try {
	        	//设置明文密文储存文件路径
	    		setPath();
	    		// 导入公钥后才进行加密
	    		String pubKey = cfgService.get("pubKey");
//	    		if(UtilMethod.notEmptyStr(pubKey) && System.getProperty("os.name").startsWith("Linux")){
					createFile();// 创建文件夹
					getStartEndTime();// 获取需保存数据的开始和结束时间
					setTomorrow();
					// 获取矿井编号
					String mineNum = stcService.getV(8);
					if(mineNum == null || mineNum.isEmpty()) mineNum = "未设置矿井编号";
					// 加密GD5数据并保存至指定文件夹
					encryptRealTimeData(getOriginalGD5Data(), mineNum, "_LJSJ_");
//	    		}
    		} catch (Exception e) {
				e.printStackTrace();
			} finally {
//				RedisPool.releaseDistributedLock(lockKey, requestId);
			}
//        }
	}
	
	//设置明文密文储存文件路径
	public void setPath(){
		encryptFilePath = cfgService.get("encrypt_file");
		unencryptFilePath = cfgService.get("unencrypt_file");
		if(!UtilMethod.notEmptyStr(encryptFilePath)){
			encryptFilePath = "/opt/encrypt_file";
		}
		if(!UtilMethod.notEmptyStr(unencryptFilePath)){
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
		Calendar cal2 = Calendar.getInstance();
		cal2.set(Calendar.HOUR, -12);  
		cal2.set(Calendar.MINUTE, 0);  
		cal2.set(Calendar.SECOND, 30);  
		cal2.set(Calendar.MILLISECOND, 0);
		if (cal.getTimeInMillis() < cal.getTimeInMillis()) {
			Date yesterdayStartTime = getYesterdayStartTime();
			startTime = df.format(yesterdayStartTime);
		} else {
			startTime = UtilMethod.getStarttimeOfDay();
		}
		today = df4.format(cal.getTime());
		cal.add(Calendar.SECOND, -30);
		endTime = df.format(cal.getTime());
		now2 = df2.format(cal.getTime());
		
	}
	
	public Date getYesterdayStartTime() {
		Calendar cal = new GregorianCalendar();
		cal.set(Calendar.HOUR, -12);  
		cal.set(Calendar.MINUTE, 0);  
		cal.set(Calendar.SECOND, 0);  
		cal.set(Calendar.MILLISECOND, 0);
		cal.setTime(cal.getTime());
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return cal.getTime();
	}
	
	public void setTomorrow() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 1);
		tomorrow = df4.format(cal.getTime());
	}

	public StringBuffer getGD5SB(List<GD5Report> dataByTime, String now) {
		List<EnvClasses> allClasses = envClassesService.getAll();
		if (UtilMethod.notEmptyList(allClasses)) {//处理班次的开始结束时间（班次配置中存在跨夜）
			for (int i = 0; i < allClasses.size(); i++) {
				EnvClasses ec = allClasses.get(i);
				ec.setId(i);
				String[] starts = ec.getStart().split(":");
				String[] ends = ec.getEnd().split(":");
				int startHour = Integer.parseInt(starts[0]);
				int startMin = Integer.parseInt(starts[1]);
				int endHour = Integer.parseInt(ends[0]);
				int endMin = Integer.parseInt(ends[1]);
				
				if (startHour < endHour) {
					ec.setStart(today + " " + ec.getStart() + ":00");
					ec.setEnd(today + " " + ec.getEnd() + ":00");
				} else if (startHour == endHour) {
					if (startMin <= endMin) {
						ec.setStart(today + " " + ec.getStart() + ":00");
						ec.setEnd(today + " " + ec.getEnd() + ":00");
					} else {
						ec.setStart(today + " " + ec.getStart() + ":00");
						ec.setEnd(tomorrow + " " + ec.getEnd() + ":00");
					}
				} else if (startHour > endHour) {
					ec.setStart(today + " " + ec.getStart() + ":00");
					ec.setEnd(tomorrow + " " + ec.getEnd() + ":00");
				}
			}
		}
		
		StringBuffer sb = new StringBuffer();
		if (UtilMethod.notEmptyList(dataByTime)) {
			Map<String, Map<Integer, Double>> devMap = new HashMap<String, Map<Integer, Double>>();
			for (int i = 0; i < dataByTime.size(); i++) {
				GD5Report cv = dataByTime.get(i);
				if (cv.getIp() != null && cv.getIp() != "") {
					StringBuffer definitionOfMeasurePoints;
					if ("16".equals(cv.getType())) {
						definitionOfMeasurePoints = EncryptDataFileUtils
								.definitionOfMeasurePoints(16, cv.getDevid());
					} else {
						definitionOfMeasurePoints = EncryptDataFileUtils
								.definitionOfMeasurePoints(cv.getSensor_type(), cv.getDevid());
					}
					
					Map<Integer, Double> map = devMap.get(definitionOfMeasurePoints.toString());
					if (UtilMethod.notEmptyList(allClasses)) {
						for (EnvClasses ec : allClasses) {
							if (map == null) map = new HashMap<Integer, Double>();//-1:的值为前班次，0：班次1,1：班次2，一次类推
							Double value = map.get(ec.getId());
							if (value == null) {
								value = 0.0;
								map.put(ec.getId(), value);
							}
							try {
								if (UtilMethod.isMid(cv.getResponsetime(), ec.getStart(), ec.getEnd())) {
									Double cls = map.get(-1);
									if (cls == null) {
										map.put(-1, (double) ec.getId());
									} else if (ec.getId() > cls) {
										map.put(-1, (double) ec.getId());
									}
									value += cv.getFlow_work();
									map.put(ec.getId(), value);
								}
							} catch (ParseException e) {
								e.printStackTrace();
							}
						}
					} else {
						if (map == null) {
							map = new HashMap<Integer, Double>();//-1:表示当前班次
							map.put(-1, (double) 0);
						}
						Double value = map.get(0);
						if (value == null) value = 0.0;
						value += cv.getFlow_work();
						map.put(0, value);
					}
					if (map != null)
						devMap.put(definitionOfMeasurePoints.toString(), map);
				}
			}
			sb.append(now + ";" + devMap.size() + splitFlag + lineFeed);
			if (devMap.size() > 0) {
				for (Entry<String, Map<Integer, Double>> etr : devMap.entrySet()) {
					sb.append(etr.getKey() + ";" + etr.getValue().get(-1).intValue());
					Map<Integer, Double> map = etr.getValue();
					if (map != null) {
						for (Entry<Integer, Double> value : map.entrySet()) {
							if (value.getKey() != -1) sb.append(";"+String.format("%.2f", value.getValue()));
						}
					}
					sb.append(splitFlag + lineFeed);
				}
			}
			sb.append("||");
		} else {
			sb.append(now + ";" + 0 + splitFlag + lineFeed + "||");
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

}
