package com.cm.service;

import com.cm.controller.EncryptDataFileUtils;
import com.cm.entity.Sensor;
import com.cm.entity.SwitchSensor;
import com.cm.entity.vo.AnaloginfoQuery;
import com.cm.entity.vo.NameTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import util.LogOut;
import util.RedisPool;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class WarningDataEncryptService {

    @Autowired
    private SwitchinfoService siService;

    @Autowired
    private StaticService stService;

    @Autowired
    private TimerRealTimeDataEncryptService service;

    @Autowired
    private BaseinfoService baseinfoService;

    @Autowired
    private SwitchSensorService sensorService;

    private String startTime;
    private String endTime;
    private String nowTime;
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat df1 = new SimpleDateFormat("yyyyMMddHHmmss");
    private SimpleDateFormat df2 = new SimpleDateFormat("yyyy_MM_dd");
    private HashMap<Integer, String> map = new HashMap<>();
    private HashMap<String, Integer> sensorMap = new HashMap<>();

    @Scheduled(cron = "0/30 * * * * ?")
    public void WarningDataEncryptMethod(){
        try{
            String lockKey = "WarningDataEncryptService";
            String requestId = UUID.randomUUID().toString();
            long expireTime = 10000l;
            boolean lock = RedisPool.tryGetDistributedLock(lockKey, requestId, expireTime);
            if (lock){

                String property = System.getProperty("os.name");
                if(property.startsWith("Linux")){

                    getStartEndTime();//获取开始时间和结束时间

                    makeMap();
                    String v = stService.getV(8);
                    String fileName = "/" + v + "_YCBJ_" + nowTime + ".TXT";

                    StringBuffer sb = new StringBuffer();
                    sb.append(df.format(Calendar.getInstance().getTime()) + ";");

                    String tableName = "t_coalMine_" + df2.format(new Date());

                    NameTime nameTime = new NameTime();
                    nameTime.setStarttime(startTime);
                    nameTime.setEndtime(endTime);
                    nameTime.setTableName(tableName);
                    List<AnaloginfoQuery> sensorAlarms = siService.getSensorAlarms(nameTime);
                    List<AnaloginfoQuery> switchSensorAlams = siService.getSwitchSensorAlams(nameTime);
                    sb.append((sensorAlarms.size() + switchSensorAlams.size()) + ";~||\r\n");
                    if (null != sensorAlarms && !sensorAlarms.isEmpty()) {
                        for (AnaloginfoQuery alarm : sensorAlarms) {
                            StringBuffer points = EncryptDataFileUtils.definitionOfMeasurePoints(alarm.getSensor_type(), alarm.getSensor_id());
                            sb.append(points + ";");
                            sb.append(map.get(alarm.getStatus()) + ";");
                            sb.append(alarm.getStartValue() + ";");
                            if (alarm.getStatus() == 2){
                                sb.append(alarm.getLimit_alarm() + ";");
                                sb.append(alarm.getLimit_alarm() + ";");
                            } else if (alarm.getStatus() == 3) {
                                sb.append(alarm.getLimit_power() + ";");
                                sb.append(alarm.getLimit_power() + ";");
                            }
                            sb.append(alarm.getStarttime() + ";");
                            if (null == alarm.getEndtime()) {
                                sb.append("X;");
                            } else {
                                sb.append(alarm.getEndtime());
                            }
                            sb.append(alarm.getMaxvalues() + ";");
                            sb.append(null == alarm.getEndtime() ? ";" : alarm.getEndtime()+";");
                            sb.append(alarm.getStarttime() + ";");
                            sb.append(alarm.getAvgvalue() + ";");
                            sb.append(alarm.getMinvalue() + ";;");
                            sb.append(null == alarm.getMeasures() ? ";" : alarm.getMeasures() + ";");
                            sb.append(null == alarm.getMeasurestime() ? ";" : alarm.getMeasurestime() + ";");
                            if (sensorAlarms.indexOf(alarm) == sensorAlarms.size() - 1) {
                                sb.append("admin;~||\r\n");
                            } else {
                                sb.append("admin;~\r\n");
                            }
                        }
                    }

                    if (null != switchSensorAlams && !switchSensorAlams.isEmpty()) {
                        for (AnaloginfoQuery alam : switchSensorAlams) {
                            StringBuffer points = EncryptDataFileUtils.definitionOfMeasurePoints(alam.getSensor_type(), alam.getSensor_id());
                            sb.append(points + ";");
                            sb.append(map.get(alam.getStatus()) + ";");
                            sb.append(alam.getStartValue() + ";");
                            sb.append(1 + ";");
                            sb.append(0 + ";");
                            sb.append(alam.getStarttime() + ";");
                            sb.append(alam.getEndtime() + ";;;;;;;");
                            sb.append(null == alam.getMeasures() ? ";" : alam.getMeasures() + ";");
                            sb.append(null == alam.getMeasurestime() ? ";" : alam.getMeasurestime() + ";");
                            if (switchSensorAlams.indexOf(alam) == switchSensorAlams.size()) {
                                sb.append("admin;~||\r\n");
                            } else {
                                sb.append("admin;~\r\n");
                            }
                        }
                    }
                    service.produceData(fileName, sb);
                }
                RedisPool.releaseDistributedLock(lockKey, requestId);
            }

        } catch (Exception e){
            e.printStackTrace();
            StringBuffer sb = new StringBuffer();
            StackTraceElement[] stackTrace = e.getStackTrace();
            for (int i = 0; i < stackTrace.length; i++) {
                StackTraceElement element = stackTrace[i];
                sb.append(element.toString()+"\n");
            }
            LogOut.log.error(e);
            LogOut.log.error(sb.toString());
        }


    }

    public void getStartEndTime(){
        Calendar cal = Calendar.getInstance();
        endTime = df.format(cal.getTime());
        nowTime = df1.format(cal.getTime());
        String substring = nowTime.substring(0,nowTime.length()-1);
        nowTime = substring + "0";
        cal.add(Calendar.SECOND, -30);
        startTime = df.format(cal.getTime());
    }

    public void makeMap(){
        map.put(2, "001");
        map.put(3, "002");
        map.put(5, "004");

        List<Sensor> list = baseinfoService.getAllSensor2();
        for (Sensor sensor : list) {
            sensorMap.put(sensor.getIpaddr() + ":" +sensor.getSensorId(), sensor.getId());
        }

        List<SwitchSensor> switchSensors = sensorService.AllSwitchSensor();
        for (SwitchSensor sensor : switchSensors) {
            sensorMap.put(sensor.getIpaddr() + ":" + sensor.getSensorId(), sensor.getId());
        }
    }


}
