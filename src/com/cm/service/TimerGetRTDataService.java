package com.cm.service;

import com.cm.controller.EncryptDataFileUtils;
import com.cm.dao.GD5ReportDao;
import com.cm.dao.IRSAKeyDao;
import com.cm.entity.vo.Coalmine;
import com.cm.service.kafka.SSDataService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import util.RedisPool;
import util.UtilMethod;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

@Service
public class TimerGetRTDataService {

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
	private TimerRealTimeDataEncryptService encryptDataService;

	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private DateFormat df2 = new SimpleDateFormat("yyyyMMddHHmmss");
	private String endTime;
	private String startTime;
	private String now2;
	private final String rootPath = "/opt/";
	private String encryptFilePath;
	private String unencryptFilePath;
	private final String decryptFilePath = "decrypt_file";
	private final String splitFlag = "~";
	private final String lineFeed = "\r\n";

	@Scheduled(cron = "0/30 * * * * ?")
	public void EncryptData() {
		String lockKey = "RTDataEncrypt";
        String requestId = UUID.randomUUID().toString();
        boolean lock = RedisPool.tryGetDistributedLock(lockKey, requestId, 15000l);
        if (lock) {
        	try {
	        	//设置明文密文储存文件路径
	    		setPath();
	    		// 导入公钥后才进行加密
	    		String pubKey = cfgService.get("pubKey");
	    		if(UtilMethod.notEmptyStr(pubKey) && System.getProperty("os.name").startsWith("Linux")){
					createFile();// 创建文件夹
					getStartEndTime();// 获取需保存数据的开始和结束时间
					// 获取矿井编号
					String mineNum = stcService.getV(8);
					if(mineNum == null || mineNum.isEmpty()) mineNum = "未设置矿井编号";
					// 加密实时数据并保存至指定文件夹
					encryptRealTimeData(getOriginalRealTimeData(), mineNum, "_SSSJ_");
	    		}
    		} catch (Exception e) {
				e.printStackTrace();
			} finally {
				RedisPool.releaseDistributedLock(lockKey, requestId);
			}
        }
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
		encryptDataService.produceData(fileName, sb);
	}
	
	// 实时数据元数据
	public StringBuffer getOriginalRealTimeData(){
		Map<String, LinkedList<Coalmine>> dataMap = SSDataService.dataMap;
		List<Coalmine> dataByTime = new LinkedList<>();
		for (Entry<String, LinkedList<Coalmine>> etr : dataMap.entrySet()) {
			LinkedList<Coalmine> linkedList = dataMap.get(etr.getKey());
			for (Coalmine cm : linkedList) {
				if (UtilMethod.notEmptyStr(cm.getResponsetime()) && UtilMethod.isTimeString(cm.getResponsetime())) {
					try {
						boolean mid = UtilMethod.isMid(cm.getResponsetime(), startTime, endTime);
						if (mid) dataByTime.add(cm);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			}
		}
		StringBuffer sb = getRTSB(dataByTime, endTime);// 实时数据拼接成指定格式
		return sb;
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
	}

	public StringBuffer getRTSB(List<Coalmine> dataByTime, String now) {
		StringBuffer sb = new StringBuffer();
		sb.append(now + ";" + dataByTime.size() + splitFlag + lineFeed);
		if(UtilMethod.notEmptyList(dataByTime)){
			for (int i = 0; i < dataByTime.size(); i++) {
				Coalmine cv = dataByTime.get(i);
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
								+ ";" + cv.getStatus() + splitFlag + lineFeed);

					if (i == dataByTime.size() - 1)
						sb.append("||");
				}
			}
		}else{
			sb.append("||");
		}
		return sb;
	}
}
