package com.cm.service;

import com.cm.dao.GD5ReportDao;
import com.cm.dao.ISubstationDao;
import com.cm.entity.GD5Report;
import com.cm.entity.Sensor;
import com.cm.entity.Substation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import util.LogOut;
import util.RedisPool;
import util.StaticUtilMethod;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class GD5StatisticsService {

    @Autowired
    private GD5ReportDao reportDao;

    @Autowired
    private ISubstationDao stationDao;

    private SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
    private SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private DecimalFormat df2 = new DecimalFormat("#.##");
    private final String lockKey = "GD5Statistics";
    private final String requestId = UUID.randomUUID().toString();
    private long expireTime = 10000L;

    private String startTime;
    private String endTime;

    @Scheduled(cron = "0 0 0/1 * * ?")
    public void GD5(){
        try{
            boolean lock = RedisPool.tryGetDistributedLock(lockKey, requestId, expireTime);
            if (lock) {
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                if (hour == 0) {
                    calendar.add(Calendar.DAY_OF_MONTH, -1);
                    String tableName = "t_gd5_" + df.format(calendar.getTime());
                    List<GD5Report> baseDate = getBaseDate(tableName);
                    if (StaticUtilMethod.notNullOrEmptyList(baseDate)) {
                        reportDao.addGD5Statistics(baseDate);
                    }
                } else {
                    String tableName = "t_gd5_" + df.format(calendar.getTime());
                    List<GD5Report> baseDate = getBaseDate(tableName);
                    if (StaticUtilMethod.notNullOrEmptyList(baseDate)) {
                        reportDao.addGD5Statistics(baseDate);
                    }
                }
                RedisPool.releaseDistributedLock(lockKey, requestId);
            }
        } catch (Exception e){
            StringBuffer sb = new StringBuffer();
            e.printStackTrace();
            StackTraceElement[] stackTrace = e.getStackTrace();
            for (int i = 0; i < stackTrace.length; i++) {
                sb.append(stackTrace[i].toString());
            }
            LogOut.log.error(sb.toString());
        }
    }

    private void getStartTimeAndEndTime(){
        Calendar calendar = Calendar.getInstance();
        endTime = df1.format(calendar.getTime());
        calendar.add(Calendar.HOUR_OF_DAY, -1);
        startTime = df1.format(calendar.getTime());
    }

    private List<GD5Report> getBaseDate(String tableName){
        getStartTimeAndEndTime();
        Map<String, Integer> stationMap = new HashMap<>();
        List<Substation> stationList = stationDao.getAll();
        for (Substation substation : stationList) {
            stationMap.put(substation.getIpaddr(), substation.getId());
        }
        Map<String, String> positionMap = new HashMap<>();
        List<Sensor> allSensorPosition = reportDao.getAllSensorPosition();
        for (Sensor sensor : allSensorPosition) {
            positionMap.put(sensor.getStation() + "." + sensor.getSensorId(), sensor.getPosition());
        }
        List<GD5Report> dateList = new ArrayList<>();
        List<GD5Report> list = reportDao.getAllByTime(startTime, endTime, tableName);
        if (StaticUtilMethod.notNullOrEmptyList(list)){
            Map<String, List<GD5Report>> baseDateMap = getBaseDateMap(list);
            for (List<GD5Report> reports : baseDateMap.values()) {
                if (StaticUtilMethod.notNullOrEmptyList(reports)){
                    GD5Report report = new GD5Report();
                    GD5Report gd5Report = reports.get(0);
                    report.setSensor_type(gd5Report.getSensor_type());
                    report.setDevid(gd5Report.getDevid());
                    report.setIp(gd5Report.getIp());
                    double flow_work = 0.0;
                    double flow_standard = 0.0;
                    double flow_pure = 0.0;
                    double temperature = 0.0;
                    double pressure = 0.0;
                    double wasi = 0.0;
                    double co = 0.0;
                    for (GD5Report report1 : reports) {
                        flow_pure += report1.getFlow_pure();
                        flow_standard += report1.getFlow_standard();
                        flow_pure += report1.getFlow_pure();
                        temperature += report1.getTemperature();
                        pressure += report1.getPressure();
                        wasi += report1.getWasi();
                        co += report1.getCo();
                    }
                    report.setFlow_pure(parseDouble(flow_pure/reports.size()));
                    report.setFlow_standard(parseDouble(flow_standard/reports.size()));
                    report.setFlow_work(parseDouble(flow_work/reports.size()));
                    report.setTemperature(parseDouble(temperature/reports.size()));
                    report.setPressure(parseDouble(pressure/reports.size()));
                    report.setWasi(parseDouble(wasi/reports.size()));
                    report.setCo(parseDouble(co/reports.size()));
                    report.setFlow_work_sum(parseDouble(flow_work));
                    report.setFlow_pure_sum(parseDouble(flow_pure));
                    report.setFlow_standard_sum(parseDouble(flow_standard));
                    report.setResponsetime(df1.format(new Date()));
                    report.setPosition(positionMap.get(stationMap.get(report.getIp()) + "." +report.getDevid()));
                    dateList.add(report);
                }
            }
        }
        return dateList;
    }

    private Map<String, List<GD5Report>> getBaseDateMap(List<GD5Report> list){
        HashMap<String, List<GD5Report>> map = new HashMap<>();
        for (GD5Report report : list) {
            String ukey = report.getIp() + ":" + report.getDevid();
            List<GD5Report> list1 = map.get(ukey);
            if (null == list1){
                list1 = new ArrayList<>();
            }
            list1.add(report);
            map.put(ukey, list1);
        }
        return map;
    }

    private double parseDouble(double value){
        return Double.parseDouble(df2.format(value));
    }
}
