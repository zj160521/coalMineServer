package com.cm.controller;

import java.text.SimpleDateFormat;
import java.util.*;

import com.alibaba.fastjson.JSON;
import com.cm.entity.*;
import com.cm.service.*;
import org.springframework.beans.factory.annotation.Autowired;

import util.GetHostName;

public class SystemInit {
	@Autowired
	private ConfigService cfgService;

	@Autowired
	private SwitchSensorService sensorService;

	@Autowired
	private BaseinfoService baseinfoService;

	@Autowired
	private StaticService staticService;

	@Autowired
	private TimerRealTimeDataEncryptService service;

	@Autowired
	private DevLinkService linkService;
	
	@Autowired
	private TimerCreateTableService createTableService;
	
	public void initialized() {
        try{
            String property = System.getProperty("os.name");
            if (property.startsWith("Linux")){
            	addHostName();
            	createTableService.createTable();
            	generateCDDYFile();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
	
	private void addHostName () {
		List<Config> configByV = cfgService.getConfigByV("hostName");
		if (configByV == null || configByV.size() == 0 ||
				(configByV.size() == 1 && !configByV.get(0).getK().equals(GetHostName.getHostName()))) {
			Config cfg = new Config();
			cfg.setK(GetHostName.getHostName());
			cfg.setV("hostName");
			cfgService.add(cfg);
		}
	}
	
	public void generateCDDYFile(){
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar cal = Calendar.getInstance();
        String s1 = sdf1.format(cal.getTime());
        String substring = s1.substring(0, s1.length() - 1);
        s1 = substring + "0";
        Static sta = staticService.getPositionByid(8);
        String fileName = "/"+sta.getV()+"_CDDY_"+ s1 +".TXT";
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
        service.produceData(fileName, sb);
    
	}

    public Map<Integer, Integer> DevLinkToMap(){
        HashMap<Integer, Integer> map = new HashMap<>();
        List<DevLink> links = linkService.getAllDevLink();
        for (DevLink link : links) {
            map.put(link.getLgcDevId(), link.getActDevId());
        }
        return map;
    }
}
