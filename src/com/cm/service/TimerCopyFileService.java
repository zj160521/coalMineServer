package com.cm.service;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import util.GetHostName;
import util.UtilMethod;

import com.cm.dao.IRSAKeyDao;
import com.cm.entity.Config;


@Service
public class TimerCopyFileService {
	@Autowired
	private ConfigService cfgService;
	@Autowired
	private IRSAKeyDao rsaService;
	
	private String encryptFilePath, unencryptFilePath;
	private DateFormat df2 = new SimpleDateFormat("yyyyMMddHHmmss");
	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Scheduled(cron = "5,35 * * * * ?")
	public void testtest() throws Exception{
        String property = System.getProperty("os.name");
        if (property.startsWith("Linux")){
            // 删除一小时以前数据文件
       		deletefile(encryptFilePath);
       		deletefile(unencryptFilePath);
       		
            encryptFilePath = null;
            unencryptFilePath = null;
            setPath();
            String anthorHost = getAnthorHost();
            String cmd = "/copy.sh "+encryptFilePath+" "+anthorHost+" "+unencryptFilePath;
            Runtime.getRuntime().exec(cmd);
        }
        
//		File file = new File("/copylog/"+StaticUtilMethod.getNow()+".TXT");
//		if(!file.exists()){
//			file.createNewFile();
//		}
//		//写文件
//		OutputStream os = new FileOutputStream(file);
//		String t = "1";
//		os.write(t.getBytes());
//		os.close();
	}
	
	public String getAnthorHost () {
		List<Config> configByV = cfgService.getConfigByV("hostName");
		String hostName = GetHostName.getHostName().trim();
		for (Config cfg : configByV) {
			if (!cfg.getK().trim().equals(hostName)) hostName = cfg.getK().trim();
			break;
		}
		return hostName;
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
	 * 删除一小时前文件
	 */
	public void deletefile(String delpath){
		try {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.SECOND, -30);
			cal.add(Calendar.HOUR, -1);
			String beforeTime = df2.format(cal.getTime());
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
			cal.add(Calendar.MONTH, -3);
			String filltime = df.format(cal.getTime());
			rsaService.delete(filltime);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
