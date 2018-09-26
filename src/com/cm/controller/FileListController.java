package com.cm.controller;

import com.alibaba.fastjson.JSON;
import com.cm.entity.*;
import com.cm.security.LoginManage;
import com.cm.service.*;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import util.GetHostName;
import util.LogOut;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Scope
@Controller
@RequestMapping("/file")
public class FileListController {
    @Autowired
    private LoginManage loginManage;

    @Autowired
    private ResultObj result;

    @Autowired
    private BaseinfoService baseinfoService;

    @Autowired
    private SwitchSensorService sensorService;

    @Autowired
    private DevLinkService linkService;

    @Autowired
    private TimerRealTimeDataEncryptService service;

    @Autowired
    private StaticService staticService;

    @Autowired
    private ConfigService configService;

    @Autowired
    private FilecontentService fcService;
    
	public void copyFileToSlave () {
		try {
			List<Config> configByV = configService.getConfigByV("hostName");
			String hostName = GetHostName.getHostName();
			for (Config cfg : configByV) {
				if (!cfg.getK().equals(hostName)) hostName = cfg.getK();
				break;
			}
			
			try {
				String[] cmd = {"scp","/opt/encrypt_file/*.TXT","root@"+hostName+":/opt/encrypt_file"};
				Runtime.getRuntime().exec(cmd);
				Thread.sleep(1000);
				String[] cmd3 = {"scp","root@"+hostName+":/opt/encrypt_file/*.TXT","/opt/encrypt_file"};
				Runtime.getRuntime().exec(cmd3);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    @RequestMapping(value = "/logUpload", method = RequestMethod.POST)
    @ResponseBody
    public Object updateLog(@RequestParam("file") MultipartFile file, HttpServletRequest request){
        if (!loginManage.isUserLogin(request)){
            return result.setStatus(-1, "no login");
        }
        try{
            String property = System.getProperty("os.name");
            if (property.startsWith("Linux")){
                String log_path = configService.get("log_path");
                String fileName = file.getOriginalFilename();
                String filePath = log_path + fileName;
                file.transferTo(new File(filePath));
            }
        } catch (Exception e){
            e.printStackTrace();
            return result.setStatus(-4,"ok");
        }
	    return result.setStatus(0, "ok");
    }

    @RequestMapping(value = "/getfilenames", method = RequestMethod.POST)
    @ResponseBody
    public Object getFileNames(@RequestBody Files files, HttpServletRequest request){
        if(!loginManage.isUserLogin(request)){
            return result.setStatus(-1,"no login");
        }
        copyFileToSlave();
        try{
            List<Files> filesList = new ArrayList<>();
            String property = System.getProperty("os.name");
            if(property.startsWith("Linux")) {
                File file = new File("/opt/unencrypt_file");
                File[] unencryptfiles = file.listFiles();
                File file1 = new File("/opt/encrypt_file");
                File[] encryptfiles = file1.listFiles();
                if(null != files.getName() && !"".equals(files.getName())){
                    for (int i = 0; i < unencryptfiles.length; i++) {
                        for (int j = 0; j < encryptfiles.length; j++) {
                            File unencryptfile = unencryptfiles[i];
                            File encryptfile = encryptfiles[j];
                            if(unencryptfile.getName().contains(files.getName()) && unencryptfile.getName().equals(encryptfile.getName())){
                                Files files1 = new Files();
                                files1.setName(unencryptfile.getName());
                                files1.setEncryptpath(encryptfile.getPath());
                                files1.setUncryptpath(unencryptfile.getPath());
                                filesList.add(files1);
                            }
                        }
                    }
                } else {
                    for (int i = 0; i < unencryptfiles.length; i++) {
                        for (int j = 0; j < encryptfiles.length; j++) {
                            File unencryptfile = unencryptfiles[i];
                            File encryptfile = encryptfiles[j];
                            if( unencryptfile.getName().equals(encryptfile.getName())){
                                Files files1 = new Files();
                                files1.setName(unencryptfile.getName());
                                files1.setUncryptpath(unencryptfile.getPath());
                                files1.setEncryptpath(encryptfile.getPath());
                                filesList.add(files1);
                            }
                        }
                    }
                }
            }
            Collections.sort(filesList);
            Collections.reverse(filesList);
            result.put("data",filesList);
        } catch (Exception e){
            e.printStackTrace();
            StringBuffer sb = new StringBuffer();
            StackTraceElement[] stackTrace = e.getStackTrace();
            for (int i = 0; i < stackTrace.length; i++) {
                StackTraceElement element = stackTrace[i];
                sb.append(element.toString()+"\n");
            }
            LogOut.log.error(sb.toString());
            result.put("error",sb.toString());
            return result.setStatus(-4,"exception");
        }
        return result.setStatus(0,"ok");
    }

    @RequestMapping(value = "/download", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<byte[]> fileDownload(String path){
        try{
            String property = System.getProperty("os.name");
            if(property.startsWith("Linux")){
                int i = path.lastIndexOf("/");
                String fileName = path.substring(i + 1);
                String dfileName = new String(fileName.getBytes(),"iso-8859-1");
                File file = new File(path);
                HttpHeaders header = new HttpHeaders();
                header.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                header.setContentDispositionFormData("attachment", dfileName);
                return new ResponseEntity(FileUtils.readFileToByteArray(file),
                        header, HttpStatus.CREATED);
            } else {
                return null;
            }
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/generate", method = RequestMethod.GET)
    @ResponseBody
    public Object generateFile(HttpServletRequest request){
        if(!loginManage.isUserLogin(request)){
            return result.setStatus(-1,"no login");
        }
        try{
            String property = System.getProperty("os.name");
            if(property.startsWith("Linux")){
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
                Calendar cal = Calendar.getInstance();
                String s1 = sdf1.format(cal.getTime());
                String substring = s1.substring(0, s1.length() - 1);
                s1 = substring + "0";
                Static sta = staticService.getPositionByid(8);
                String fileName = "/"+sta.getV()+"_CDDY_"+ s1 +".TXT";
//                LogOut.log.info(fileName);
                Map<Integer, Integer> linkMap = DevLinkToMap();
                StringBuffer sb = new StringBuffer();
                List<SwitchSensor> switchSensorList = sensorService.AllSwitchSensor();
                List<Sensor> sensorList = baseinfoService.getAllSensor2();
                sb.append("KJ1031X;煤矿安全监控系统;四川省川煤科技有限公司;");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String s = sdf.format(new Date());
                sb.append(s+";");
                sb.append(switchSensorList.size()+";");
                sb.append(sensorList.size()+";");
                sb.append("0;0;0;0;;~\r\n");
                for (SwitchSensor sensor : switchSensorList) {
                    if(sensor.getSensor_type()==71){
                        continue;
                    }
                    StringBuffer points = EncryptDataFileUtils.definitionOfMeasurePoints(sensor.getSensor_type(), sensor.getId());
                    sb.append(points+";");
                    StringBuffer stationPoint = EncryptDataFileUtils.definitionOfMeasurePoints(16, sensor.getStation());
                    sb.append(stationPoint+";");
                    if(null == sensor.getAreaname()){
                        sb.append(" ;;");
                    } else {
                        sb.append(sensor.getAreaname() + ";;");
                    }
                    String types = sensor.getType0x();
                    Map map = (Map) JSON.parse(types);
                    sb.append(map.get(1)+";"+map.get(0)+";");
                    if(sensor.getAlarm_status() == 0 || sensor.getAlarm_status() ==-1){
                        sb.append("0;1;0;1;");
                    } else if(sensor.getAlarm_status() == 1) {
                        sb.append("1;0;1;0;");
                    }
                    Integer id = linkMap.get(sensor.getId());
                    if(null == id){
                        sb.append("~\r\n");
                    } else {
                        StringBuffer buffer = EncryptDataFileUtils.definitionOfMeasurePoints(56, id);
                        sb.append("D-");
                        sb.append(buffer+"~\r\n");
                    }
                }
                for (Sensor sensor : sensorList) {
                    StringBuffer sensorPoint = EncryptDataFileUtils.definitionOfMeasurePoints(sensor.getSensor_type(), sensor.getId());
                    sb.append(sensorPoint + ";");
                    StringBuffer stationPoint = EncryptDataFileUtils.definitionOfMeasurePoints(16, sensor.getStation());
                    sb.append(stationPoint + ";");
                    if(null == sensor.getAreaname()){
                        sb.append(" ;;");
                    } else {
                        sb.append(sensor.getAreaname() + ";;");
                    }
                    sb.append(sensor.getSensorUnit() + ";;;");
                    sb.append(sensor.getLimit_alarm() + ";;");
                    sb.append(sensor.getFloor_alarm() + ";;");
                    sb.append(sensor.getLimit_power() + ";");
                    sb.append(sensor.getLimit_repower() + ";");
                    sb.append(sensor.getFloor_power() + ";");
                    sb.append(sensor.getFloor_repower() + ";");
                    Integer id = linkMap.get(sensor.getId());
                    if (null == id) {
                        if(sensorList.indexOf(sensor) == sensorList.size()-1){
                            sb.append("~||\r\n");
                        } else {
                            sb.append("~\r\n");
                        }
                    } else {
                        StringBuffer buffer = EncryptDataFileUtils.definitionOfMeasurePoints(56, id);
                        sb.append("D-");
                        if(sensorList.indexOf(sensor) == sensorList.size()-1){
                            sb.append(buffer + "~||\r\n");
                        } else {
                            sb.append(buffer + "~\r\n");
                        }
                    }
                }
                Filecontent content = new Filecontent();
                content.setFilename(fileName);
                content.setStr(sb.toString());
                fcService.add(content);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return result.setStatus(0,"ok");
    }

    public Map<Integer, Integer> DevLinkToMap(){
        HashMap<Integer, Integer> map = new HashMap<>();
        List<DevLink> links = linkService.getAllDevLink();
        for (DevLink link : links) {
            map.put(link.getLgcDevId(), link.getActDevId());
        }
        return map;
    }

    @RequestMapping(value = "/setting", method = RequestMethod.POST)
    @ResponseBody
    public Object fileSettings(@RequestBody Files files,HttpServletRequest request){
        if(!loginManage.isUserLogin(request)){
            return result.setStatus(-1,"no login");
        }
        try{
            if(!files.getEncryptpath().startsWith("/") || !files.getUncryptpath().startsWith("/")){
                return result.setStatus(-3,"请输入正确的路劲");
            }
            Config unencrypt = configService.getConfig("unencrypt_file");
            Config encrypt = configService.getConfig("encrypt_file");
            Config logpath = configService.getConfig("log_path");
            if(null == unencrypt){
                unencrypt = new Config();
                unencrypt.setK("unencrypt_file");
                unencrypt.setV(files.getUncryptpath());
                configService.add(unencrypt);
            } else {
                unencrypt.setK("unencrypt_file");
                unencrypt.setV(files.getUncryptpath());
                configService.update(unencrypt);
            }
            if (null == encrypt){
                encrypt = new Config();
                encrypt.setK("encrypt_file");
                encrypt.setV(files.getEncryptpath());
                configService.add(encrypt);
            } else {
                encrypt.setK("encrypt_file");
                encrypt.setV(files.getEncryptpath());
                configService.update(encrypt);
            }
            if (null == logpath) {
                logpath = new Config();
                logpath.setK("log_path");
                logpath.setV(files.getLogpath());
                configService.add(logpath);
            } else {
                logpath.setK("log_path");
                logpath.setV(files.getLogpath());
                configService.update(logpath);
            }
            result.put("data",files);
        } catch (Exception e){
            e.printStackTrace();
            return result.setStatus(-4,"exception");
        }
        return result.setStatus(0, "ok");
    }

    @RequestMapping(value = "/getpath", method = RequestMethod.GET)
    @ResponseBody
    public Object getPath(HttpServletRequest request){
        if(!loginManage.isUserLogin(request)){
            return result.setStatus(-1,"no login");
        }
        try{
            String unencrypt_file = configService.get("unencrypt_file");
            String encrypt_file = configService.get("encrypt_file");
            String log_path = configService.get("log_path");
            HashMap<String, String> map = new HashMap<>();
            map.put("uncryptpath",unencrypt_file);
            map.put("encryptpath",encrypt_file);
            map.put("logpath", log_path);
            result.put("data",map);
        } catch (Exception e){
            e.printStackTrace();
        }
        return result.setStatus(0,"ok");
    }
}