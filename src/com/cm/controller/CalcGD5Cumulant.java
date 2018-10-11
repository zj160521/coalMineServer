package com.cm.controller;

import com.cm.entity.Cumulant;
import com.cm.entity.GD5Report;
import com.cm.entity.Sensor;
import com.cm.service.BaseinfoService;
import com.cm.service.CumulanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import util.LogOut;
import util.RedisPool;
import util.UtilMethod;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Component("calcGD5Cumulant")
public class CalcGD5Cumulant {

    @Autowired
    private CumulanService cumulanService;

    @Autowired
    private BaseinfoService baseinfoService;

    private SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
    private long expireTime = 10000l;

    @Scheduled(cron = "0 0/1 * * * ?")
    private void TimerGD5TotalCumulant(){
        try{
            String lockKey = "TimerGD5TotalCumulant";
            String requestId = UUID.randomUUID().toString();
            boolean lock = RedisPool.tryGetDistributedLock(lockKey, requestId, expireTime);
            if (lock) {
                List<Sensor> sensors = baseinfoService.getGD5Sensor();
                Map<String, Integer> sensorMap = sensorMap(sensors);

                List<Cumulant> allTotalCumulant = cumulanService.getAllTotalCumulant();
                Map<Integer, Cumulant> totalCumulantMap = totalCumulantMap(allTotalCumulant);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Calendar cal = Calendar.getInstance();
                cal.setTime(new Date());
                String endtime = sdf.format(cal.getTime());
                cal.add(Calendar.MINUTE,-1);
                String starttime = sdf.format(cal.getTime());
                String tableName = "t_gd5_" + df.format(new Date());
                List<GD5Report> basedata = cumulanService.getBasedata(tableName,starttime, endtime);
                Map<String, List<GD5Report>> baseDataMap = baseDataMap(basedata);

                DecimalFormat df = new DecimalFormat("#.##");

                if(null != sensorMap && !sensorMap.isEmpty()){
                    if(null != baseDataMap && !baseDataMap.isEmpty()){
                        if(null == totalCumulantMap || totalCumulantMap.isEmpty()){
                            List<Cumulant> list = new ArrayList<>();
                            for (List<GD5Report> reports : baseDataMap.values()) {
                                double flow_work = 0.0;
                                double flow_standard = 0.0;
                                double flow_pure = 0.0;
                                Cumulant cumulant = new Cumulant();
                                cumulant.setIp(reports.get(0).getIp());
                                cumulant.setResponsetime(reports.get(reports.size()-1).getResponsetime());
                                cumulant.setSensor_id(sensorMap.get(reports.get(0).getIp() + ":" + reports.get(0).getDevid()));
                                cumulant.setSensorId(reports.get(0).getDevid());
                                cumulant.setStatus(1);
                                cumulant.setType(reports.get(0).getSensor_type());
                                for (GD5Report report : reports) {
                                    flow_work += report.getFlow_work();
                                    flow_standard += report.getFlow_standard();
                                    flow_pure += report.getFlow_pure();
                                }
                                cumulant.setFlow_work(Double.parseDouble(df.format(flow_work)));
                                cumulant.setFlow_standard(Double.parseDouble(df.format(flow_standard)));
                                cumulant.setFlow_pure(Double.parseDouble(df.format(flow_pure)));
                                list.add(cumulant);
                            }
                            cumulanService.batchAdd(list);
                        } else {
                            List<Cumulant> list = new ArrayList<>();
                            for (Map.Entry<String, Integer> entry : sensorMap.entrySet()) {
                                Integer sensor_id = entry.getValue();
                                Cumulant cumulant = totalCumulantMap.get(sensor_id);
                                if(null == cumulant){
                                    cumulant = new Cumulant();
                                    List<GD5Report> reports = baseDataMap.get(entry.getKey());
                                    if(UtilMethod.notEmptyList(reports)){
                                        GD5Report port = reports.get(0);
                                        double flow_work = 0.0;
                                        double flow_standard = 0.0;
                                        double flow_pure = 0.0;
                                        cumulant.setIp(port.getIp());
                                        cumulant.setSensor_id(sensor_id);
                                        cumulant.setResponsetime(reports.get(reports.size()-1).getResponsetime());
                                        cumulant.setType(port.getSensor_type());
                                        cumulant.setStatus(2);
                                        cumulant.setSensorId(port.getDevid());
                                        for (GD5Report report : reports) {
                                            flow_pure += report.getFlow_pure();
                                            flow_standard += report.getFlow_standard();
                                            flow_work += report.getFlow_work();
                                        }
                                        flow_pure = Double.parseDouble(df.format(flow_pure));
                                        flow_standard = Double.parseDouble(df.format(flow_standard));
                                        flow_work = Double.parseDouble(df.format(flow_work));
                                        cumulant.setFlow_pure(flow_pure);
                                        cumulant.setFlow_work(flow_work);
                                        cumulant.setFlow_standard(flow_standard);
                                        list.add(cumulant);
//                                    cumulanService.add(cumulant);
                                    }
                                }
                            }
                            if (UtilMethod.notEmptyList(list)){
                                cumulanService.batchAdd(list);
                            }
                            for (Cumulant cumulant : allTotalCumulant) {
                                List<GD5Report> reports = baseDataMap.get(cumulant.getIp() + ":" + cumulant.getSensorId());
                                if (UtilMethod.notEmptyList(reports)){
                                    double flow_work = 0.0;
                                    double flow_standard = 0.0;
                                    double flow_pure = 0.0;
                                    reports.remove(0);
                                    for (GD5Report report : reports) {
                                        flow_pure += report.getFlow_pure();
                                        flow_work += report.getFlow_work();
                                        flow_standard += report.getFlow_standard();
                                    }
                                    flow_pure = Double.parseDouble(df.format(flow_pure));
                                    flow_standard = Double.parseDouble(df.format(flow_standard));
                                    flow_work = Double.parseDouble(df.format(flow_work));
                                    cumulant.setFlow_pure(cumulant.getFlow_pure() + flow_pure);
                                    cumulant.setFlow_work(cumulant.getFlow_work() + flow_work);
                                    cumulant.setFlow_standard(cumulant.getFlow_standard() + flow_standard);
                                    cumulant.setType(reports.get(0).getSensor_type());
                                    cumulant.setResponsetime(reports.get(reports.size()-1).getResponsetime());
                                    cumulant.setSensorId(reports.get(0).getDevid());
                                    cumulant.setIp(reports.get(0).getIp());
                                }
                            }
                            cumulanService.batchUpdate(allTotalCumulant);
                        }
                    }
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
            LogOut.log.error(e.getMessage());
            LogOut.log.error(sb.toString());
        }
    }

    @Scheduled(cron = "0 0/1 * * * ?")
    private void TimerGD5YearCumulant(){
        try{
            String lockKey = "TimerGD5YearCumulant";
            String requestId = UUID.randomUUID().toString();
            boolean b = RedisPool.tryGetDistributedLock(lockKey, requestId, expireTime);
            if (b) {
                List<Sensor> sensors = baseinfoService.getGD5Sensor();
                Map<String, Integer> sensorMap = sensorMap(sensors);

                List<Cumulant> allTotalCumulant = cumulanService.getAllYearCumulant();
                Map<Integer, Cumulant> totalCumulantMap = totalCumulantMap(allTotalCumulant);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Calendar cal = Calendar.getInstance();
                cal.setTime(new Date());
                String endtime = sdf.format(cal.getTime());
                cal.add(Calendar.MINUTE,-1);
                String starttime = sdf.format(cal.getTime());
                String tableName = "t_gd5_" + df.format(new Date());
                List<GD5Report> basedata = cumulanService.getBasedata(tableName,starttime, endtime);
                Map<String, List<GD5Report>> baseDataMap = baseDataMap(basedata);

                DecimalFormat df = new DecimalFormat("#.##");

                if(null != sensorMap && !sensorMap.isEmpty()){
                    if(null != baseDataMap && !baseDataMap.isEmpty()){
                        if(null == totalCumulantMap || totalCumulantMap.isEmpty()){
                            List<Cumulant> list = new ArrayList<>();
                            for (List<GD5Report> reports : baseDataMap.values()) {
                                double flow_work = 0.0;
                                double flow_standard = 0.0;
                                double flow_pure = 0.0;
                                Cumulant cumulant = new Cumulant();
                                GD5Report port = reports.get(0);
                                cumulant.setIp(port.getIp());
                                cumulant.setStatus(2);
                                cumulant.setSensorId(port.getDevid());
                                cumulant.setType(port.getSensor_type());
                                cumulant.setSensor_id(sensorMap.get(port.getIp() + ":" + port.getDevid()));
                                cumulant.setResponsetime(reports.get(reports.size()-1).getResponsetime());
                                for (GD5Report report : reports) {
                                    flow_work += report.getFlow_work();
                                    flow_standard += report.getFlow_standard();
                                    flow_pure += report.getFlow_pure();
                                }
                                cumulant.setFlow_standard(flow_standard);
                                cumulant.setFlow_work(flow_work);
                                cumulant.setFlow_pure(flow_pure);
                                list.add(cumulant);
                            }
                            cumulanService.batchAdd(list);
                        } else {
                            List<Cumulant> list = new ArrayList<>();
                            for (Map.Entry<String, Integer> entry : sensorMap.entrySet()) {
                                Integer sensor_id = entry.getValue();
                                Cumulant cumulant = totalCumulantMap.get(sensor_id);
                                if(null == cumulant){
                                    cumulant = new Cumulant();
                                    List<GD5Report> reports = baseDataMap.get(entry.getKey());
                                    if(UtilMethod.notEmptyList(reports)){
                                        GD5Report port = reports.get(0);
                                        double flow_work = 0.0;
                                        double flow_standard = 0.0;
                                        double flow_pure = 0.0;
                                        cumulant.setIp(port.getIp());
                                        cumulant.setSensor_id(sensor_id);
                                        cumulant.setResponsetime(reports.get(reports.size()-1).getResponsetime());
                                        cumulant.setType(port.getSensor_type());
                                        cumulant.setStatus(2);
                                        cumulant.setSensorId(port.getDevid());
                                        for (GD5Report report : reports) {
                                            flow_pure += report.getFlow_pure();
                                            flow_standard += report.getFlow_standard();
                                            flow_work += report.getFlow_work();
                                        }
                                        flow_pure = Double.parseDouble(df.format(flow_pure));
                                        flow_standard = Double.parseDouble(df.format(flow_standard));
                                        flow_work = Double.parseDouble(df.format(flow_work));
                                        cumulant.setFlow_pure(flow_pure);
                                        cumulant.setFlow_work(flow_work);
                                        cumulant.setFlow_standard(flow_standard);
                                        list.add(cumulant);
    //                                cumulanService.add(cumulant);
                                    }
                                }
                            }
                            if (UtilMethod.notEmptyList(list)){
                                cumulanService.batchAdd(list);
                            }
                            Integer lastYear = UtilMethod.yearDistinguish(allTotalCumulant.get(0).getResponsetime());
                            if(null != lastYear){
                                if (lastYear == 0){
                                    for (Cumulant cumulant : allTotalCumulant) {
                                        List<GD5Report> reports = baseDataMap.get(cumulant.getIp() + ":" + cumulant.getSensorId());
                                        if (UtilMethod.notEmptyList(reports)){
                                            double flow_work = 0.0;
                                            double flow_standard = 0.0;
                                            double flow_pure = 0.0;
                                            reports.remove(0);
                                            for (GD5Report report : reports) {
                                                flow_pure += report.getFlow_pure();
                                                flow_work += report.getFlow_work();
                                                flow_standard += report.getFlow_standard();
                                            }
                                            flow_pure = Double.parseDouble(df.format(flow_pure));
                                            flow_standard = Double.parseDouble(df.format(flow_standard));
                                            flow_work = Double.parseDouble(df.format(flow_work));
                                            cumulant.setFlow_pure(cumulant.getFlow_pure() + flow_pure);
                                            cumulant.setFlow_work(cumulant.getFlow_work() + flow_work);
                                            cumulant.setFlow_standard(cumulant.getFlow_standard() + flow_standard);
                                            cumulant.setType(reports.get(0).getSensor_type());
                                            cumulant.setResponsetime(reports.get(reports.size()-1).getResponsetime());
                                            cumulant.setSensorId(reports.get(0).getDevid());
                                            cumulant.setIp(reports.get(0).getIp());
                                        }
                                    }
                                    cumulanService.batchUpdate(allTotalCumulant);
                                } else if (lastYear == -1){
                                    for (Cumulant cumulant : allTotalCumulant) {
                                        List<GD5Report> reports = baseDataMap.get(cumulant.getIp() + ":" + cumulant.getSensorId());
                                        if (UtilMethod.notEmptyList(reports)){
                                            double flow_work = 0.0;
                                            double flow_standard = 0.0;
                                            double flow_pure = 0.0;
                                            for (GD5Report report : reports) {
                                                flow_pure += report.getFlow_pure();
                                                flow_work += report.getFlow_work();
                                                flow_standard += report.getFlow_standard();
                                            }
                                            flow_pure = Double.parseDouble(df.format(flow_pure));
                                            flow_standard = Double.parseDouble(df.format(flow_standard));
                                            flow_work = Double.parseDouble(df.format(flow_work));
                                            cumulant.setFlow_pure(flow_pure);
                                            cumulant.setFlow_work(flow_work);
                                            cumulant.setFlow_standard(flow_standard);
                                            cumulant.setType(reports.get(0).getSensor_type());
                                            cumulant.setResponsetime(reports.get(reports.size()-1).getResponsetime());
                                            cumulant.setSensorId(reports.get(0).getDevid());
                                            cumulant.setIp(reports.get(0).getIp());
                                        }
                                    }
                                    cumulanService.batchUpdate(allTotalCumulant);
                                }
                            }
                        }
                    }
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
            LogOut.log.error(e.getMessage());
            LogOut.log.error(sb.toString());
        }
    }

    @Scheduled(cron = "0 0/1 * * * ?")
    private void TimerGD5MonthCumulant(){
        try{
            String lockKey = "TimerGD5MonthCumulant";
            String requestId = UUID.randomUUID().toString();
            boolean lock = RedisPool.tryGetDistributedLock(lockKey, requestId, expireTime);
            if (lock) {
                List<Sensor> gd5Sensor = baseinfoService.getGD5Sensor();
                Map<String, Integer> sensorMap = sensorMap(gd5Sensor);

                List<Cumulant> allMonthCumulant = cumulanService.getAllMonthCumulant();
                Map<Integer, Cumulant> totalCumulantMap = totalCumulantMap(allMonthCumulant);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Calendar cal = Calendar.getInstance();
                cal.setTime(new Date());
                String endtime = sdf.format(cal.getTime());
                cal.add(Calendar.MINUTE,-1);
                String starttime = sdf.format(cal.getTime());
                String tableName = "t_gd5_" + df.format(new Date());
                List<GD5Report> basedata = cumulanService.getBasedata(tableName,starttime, endtime);
                Map<String, List<GD5Report>> baseDataMap = baseDataMap(basedata);

                DecimalFormat df = new DecimalFormat("#.##");

                if(null!= sensorMap && !sensorMap.isEmpty()){
                    if(null != baseDataMap && !baseDataMap.isEmpty()){
                        if(null == totalCumulantMap || totalCumulantMap.isEmpty()){
                            List<Cumulant> list = new ArrayList<>();
                            for (List<GD5Report> reports : baseDataMap.values()) {
                                double flow_work = 0.0;
                                double flow_standard = 0.0;
                                double flow_pure = 0.0;
                                Cumulant cumulant = new Cumulant();
                                GD5Report port = reports.get(0);
                                cumulant.setSensor_id(sensorMap.get(port.getIp() + ":" +port.getDevid()));
                                cumulant.setResponsetime(reports.get(reports.size()-1).getResponsetime());
                                cumulant.setIp(port.getIp());
                                cumulant.setType(port.getSensor_type());
                                cumulant.setStatus(3);
                                cumulant.setSensorId(port.getDevid());
                                for (GD5Report report : reports) {
                                    flow_pure += report.getFlow_pure();
                                    flow_standard += report.getFlow_standard();
                                    flow_work += report.getFlow_work();
                                }
                                flow_pure = Double.parseDouble(df.format(flow_pure));
                                flow_standard = Double.parseDouble(df.format(flow_standard));
                                flow_work = Double.parseDouble(df.format(flow_work));
                                cumulant.setFlow_pure(flow_pure);
                                cumulant.setFlow_work(flow_work);
                                cumulant.setFlow_standard(flow_standard);
                                list.add(cumulant);
                            }
                            cumulanService.batchAdd(list);
                        } else {
                            List<Cumulant> list = new ArrayList<>();
                            for (Map.Entry<String, Integer> entry : sensorMap.entrySet()) {
                                Integer sensor_id = entry.getValue();
                                Cumulant cumulant = totalCumulantMap.get(sensor_id);
                                if(null == cumulant){
                                    cumulant = new Cumulant();
                                    List<GD5Report> reports = baseDataMap.get(entry.getKey());
                                    if(UtilMethod.notEmptyList(reports)){
                                        GD5Report port = reports.get(0);
                                        double flow_work = 0.0;
                                        double flow_standard = 0.0;
                                        double flow_pure = 0.0;
                                        cumulant.setIp(port.getIp());
                                        cumulant.setSensor_id(sensor_id);
                                        cumulant.setResponsetime(reports.get(reports.size()-1).getResponsetime());
                                        cumulant.setType(port.getSensor_type());
                                        cumulant.setStatus(2);
                                        cumulant.setSensorId(port.getDevid());
                                        for (GD5Report report : reports) {
                                            flow_pure += report.getFlow_pure();
                                            flow_standard += report.getFlow_standard();
                                            flow_work += report.getFlow_work();
                                        }
                                        flow_pure = Double.parseDouble(df.format(flow_pure));
                                        flow_standard = Double.parseDouble(df.format(flow_standard));
                                        flow_work = Double.parseDouble(df.format(flow_work));
                                        cumulant.setFlow_pure(flow_pure);
                                        cumulant.setFlow_work(flow_work);
                                        cumulant.setFlow_standard(flow_standard);
                                        list.add(cumulant);
                                        //                                cumulanService.add(cumulant);
                                    }
                                }
                            }
                            if (UtilMethod.notEmptyList(list)){
                                cumulanService.batchAdd(list);
                            }
                            Integer lastMonth = UtilMethod.isLastMonth(allMonthCumulant.get(0).getResponsetime());
                            if(null != lastMonth){
                                if(lastMonth == 0){
                                    for (Cumulant cumulant : allMonthCumulant) {
                                        List<GD5Report> reports = baseDataMap.get(cumulant.getIp() + ":" + cumulant.getSensorId());
                                        if (UtilMethod.notEmptyList(reports)){
                                            double flow_work = 0.0;
                                            double flow_standard = 0.0;
                                            double flow_pure = 0.0;
                                            reports.remove(0);
                                            for (GD5Report report : reports) {
                                                flow_pure += report.getFlow_pure();
                                                flow_work += report.getFlow_work();
                                                flow_standard += report.getFlow_standard();
                                            }
                                            flow_pure = Double.parseDouble(df.format(flow_pure));
                                            flow_standard = Double.parseDouble(df.format(flow_standard));
                                            flow_work = Double.parseDouble(df.format(flow_work));
                                            cumulant.setFlow_pure(cumulant.getFlow_pure() + flow_pure);
                                            cumulant.setFlow_work(cumulant.getFlow_work() + flow_work);
                                            cumulant.setFlow_standard(cumulant.getFlow_standard() + flow_standard);
                                            cumulant.setType(reports.get(0).getSensor_type());
                                            cumulant.setResponsetime(reports.get(reports.size()-1).getResponsetime());
                                            cumulant.setSensorId(reports.get(0).getDevid());
                                            cumulant.setIp(reports.get(0).getIp());
                                        }
                                    }
                                    cumulanService.batchUpdate(allMonthCumulant);
                                } else if (lastMonth == 1){
                                    for (Cumulant cumulant : allMonthCumulant) {
                                        List<GD5Report> reports = baseDataMap.get(cumulant.getIp() + ":" + cumulant.getSensorId());
                                        if (UtilMethod.notEmptyList(reports)){
                                            double flow_work = 0.0;
                                            double flow_standard = 0.0;
                                            double flow_pure = 0.0;
                                            for (GD5Report report : reports) {
                                                flow_pure += report.getFlow_pure();
                                                flow_work += report.getFlow_work();
                                                flow_standard += report.getFlow_standard();
                                            }
                                            flow_pure = Double.parseDouble(df.format(flow_pure));
                                            flow_standard = Double.parseDouble(df.format(flow_standard));
                                            flow_work = Double.parseDouble(df.format(flow_work));
                                            cumulant.setFlow_pure(flow_pure);
                                            cumulant.setFlow_work(flow_work);
                                            cumulant.setFlow_standard(flow_standard);
                                            cumulant.setType(reports.get(0).getSensor_type());
                                            cumulant.setResponsetime(reports.get(reports.size()-1).getResponsetime());
                                            cumulant.setSensorId(reports.get(0).getDevid());
                                            cumulant.setIp(reports.get(0).getIp());
                                        }
                                    }
                                    cumulanService.batchUpdate(allMonthCumulant);
                                }
                            }
                        }
                    }
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
            LogOut.log.error(e.getMessage());
            LogOut.log.error(sb.toString());
        }
    }

    @Scheduled(cron = "0 0/1 * * * ?")
    private void TimerGD5DayCumulant(){
        try{
            String lockKey = "TimerGD5DayCumulant";
            String requestId = UUID.randomUUID().toString();
            boolean lock = RedisPool.tryGetDistributedLock(lockKey, requestId, expireTime);
            if (lock) {
                List<Sensor> gd5Sensor = baseinfoService.getGD5Sensor();
                Map<String, Integer> sensorMap = sensorMap(gd5Sensor);

                List<Cumulant> allDayCumulant = cumulanService.getAllDayCumulant();
                Map<Integer, Cumulant> dayCumulantMap = totalCumulantMap(allDayCumulant);

                Calendar cal = Calendar.getInstance();
                cal.setTime(new Date());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String endtime = sdf.format(cal.getTime());
                cal.add(Calendar.MINUTE,-1);
                String starttime = sdf.format(cal.getTime());
                String tableName = "t_gd5_" + df.format(new Date());
                List<GD5Report> basedata = cumulanService.getBasedata(tableName, starttime, endtime);
                Map<String, List<GD5Report>> baseDataMap = baseDataMap(basedata);

                DecimalFormat df = new DecimalFormat("#.##");

                if(null != sensorMap && !sensorMap.isEmpty()){
                    if(null != baseDataMap && !baseDataMap.isEmpty()){
                        if(null == dayCumulantMap || dayCumulantMap.isEmpty()){
                            List<Cumulant> list = new ArrayList<>();
                            for (List<GD5Report> reports : baseDataMap.values()) {
                                Cumulant cumulant = new Cumulant();
                                double flow_work = 0.0;
                                double flow_standard = 0.0;
                                double flow_pure = 0.0;
                                GD5Report port = reports.get(0);
                                cumulant.setSensorId(port.getDevid());
                                cumulant.setStatus(4);
                                cumulant.setType(port.getSensor_type());
                                cumulant.setResponsetime(reports.get(reports.size()-1).getResponsetime());
                                cumulant.setSensor_id(sensorMap.get(port.getIp() + ":" + port.getDevid()));
                                cumulant.setIp(port.getIp());
                                for (GD5Report report : reports) {
                                    flow_work += report.getFlow_work();
                                    flow_standard += report.getFlow_standard();
                                    flow_pure += report.getFlow_pure();
                                }
                                flow_work = Double.parseDouble(df.format(flow_work));
                                flow_pure = Double.parseDouble(df.format(flow_pure));
                                flow_standard = Double.parseDouble(df.format(flow_standard));
                                cumulant.setFlow_standard(flow_standard);
                                cumulant.setFlow_work(flow_work);
                                cumulant.setFlow_pure(flow_pure);
                                list.add(cumulant);
                            }
                            cumulanService.batchAdd(list);
                        } else {
                            List<Cumulant> list = new ArrayList<>();
                            for (Map.Entry<String, Integer> entry : sensorMap.entrySet()) {
                                Integer sensor_id = entry.getValue();
                                Cumulant cumulant = dayCumulantMap.get(sensor_id);
                                if(null == cumulant){
                                    cumulant = new Cumulant();
                                    List<GD5Report> reports = baseDataMap.get(entry.getKey());
                                    if(UtilMethod.notEmptyList(reports)){
                                        GD5Report port = reports.get(0);
                                        double flow_work = 0.0;
                                        double flow_standard = 0.0;
                                        double flow_pure = 0.0;
                                        cumulant.setIp(port.getIp());
                                        cumulant.setSensor_id(sensor_id);
                                        cumulant.setResponsetime(reports.get(reports.size()-1).getResponsetime());
                                        cumulant.setType(port.getSensor_type());
                                        cumulant.setStatus(2);
                                        cumulant.setSensorId(port.getDevid());
                                        for (GD5Report report : reports) {
                                            flow_pure += report.getFlow_pure();
                                            flow_standard += report.getFlow_standard();
                                            flow_work += report.getFlow_work();
                                        }
                                        flow_pure = Double.parseDouble(df.format(flow_pure));
                                        flow_standard = Double.parseDouble(df.format(flow_standard));
                                        flow_work = Double.parseDouble(df.format(flow_work));
                                        cumulant.setFlow_pure(flow_pure);
                                        cumulant.setFlow_work(flow_work);
                                        cumulant.setFlow_standard(flow_standard);
                                        list.add(cumulant);
//                                cumulanService.add(cumulant);
                                    }
                                }
                            }
                            if (UtilMethod.notEmptyList(list)){
                                cumulanService.batchAdd(list);
                            }
                            Integer yesterday = UtilMethod.isYesterday(allDayCumulant.get(0).getResponsetime());
                            if(null != yesterday){
                                if(yesterday == 0){
                                    for (Cumulant cumulant : allDayCumulant) {
                                        List<GD5Report> reports = baseDataMap.get(cumulant.getIp() + ":" + cumulant.getSensorId());
                                        if (UtilMethod.notEmptyList(reports)){
                                            double flow_work = 0.0;
                                            double flow_standard = 0.0;
                                            double flow_pure = 0.0;
                                            reports.remove(0);
                                            for (GD5Report report : reports) {
                                                flow_pure += report.getFlow_pure();
                                                flow_work += report.getFlow_work();
                                                flow_standard += report.getFlow_standard();
                                            }
                                            flow_pure = Double.parseDouble(df.format(flow_pure));
                                            flow_standard = Double.parseDouble(df.format(flow_standard));
                                            flow_work = Double.parseDouble(df.format(flow_work));
                                            cumulant.setFlow_pure(cumulant.getFlow_pure() + flow_pure);
                                            cumulant.setFlow_work(cumulant.getFlow_work() + flow_work);
                                            cumulant.setFlow_standard(cumulant.getFlow_standard() + flow_standard);
                                            cumulant.setType(reports.get(0).getSensor_type());
                                            cumulant.setResponsetime(reports.get(reports.size()-1).getResponsetime());
                                            cumulant.setSensorId(reports.get(0).getDevid());
                                            cumulant.setIp(reports.get(0).getIp());
                                        }
                                    }
                                    cumulanService.batchUpdate(allDayCumulant);
                                } else if (yesterday > 0){
                                    for (Cumulant cumulant : allDayCumulant) {
                                        List<GD5Report> reports = baseDataMap.get(cumulant.getIp() + ":" + cumulant.getSensorId());
                                        if (UtilMethod.notEmptyList(reports)){
                                            double flow_work = 0.0;
                                            double flow_standard = 0.0;
                                            double flow_pure = 0.0;
                                            for (GD5Report report : reports) {
                                                flow_pure += report.getFlow_pure();
                                                flow_work += report.getFlow_work();
                                                flow_standard += report.getFlow_standard();
                                            }
                                            flow_pure = Double.parseDouble(df.format(flow_pure));
                                            flow_standard = Double.parseDouble(df.format(flow_standard));
                                            flow_work = Double.parseDouble(df.format(flow_work));
                                            cumulant.setFlow_pure(flow_pure);
                                            cumulant.setFlow_work(flow_work);
                                            cumulant.setFlow_standard(flow_standard);
                                            cumulant.setType(reports.get(0).getSensor_type());
                                            cumulant.setResponsetime(reports.get(reports.size()-1).getResponsetime());
                                            cumulant.setSensorId(reports.get(0).getDevid());
                                            cumulant.setIp(reports.get(0).getIp());
                                        }
                                    }
                                    cumulanService.batchUpdate(allDayCumulant);
                                }
                            }
                        }
                    }
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
            LogOut.log.error(e.getMessage());
            LogOut.log.error(sb.toString());
        }
    }

    private Map<Integer,Cumulant> totalCumulantMap(List<Cumulant> cumulants){
        if(null == cumulants || cumulants.isEmpty()){
            return null;
        } else {
            HashMap<Integer,Cumulant> totalCumulantMap = new HashMap<>();
            for (Cumulant cumulant : cumulants) {
                totalCumulantMap.put(cumulant.getSensor_id(),cumulant);
            }
            return totalCumulantMap;
        }
    }

    private Map<String,List<GD5Report>> baseDataMap(List<GD5Report> list){
        if(null == list || list.isEmpty()) {
            return null;
        } else {
            HashMap<String,List<GD5Report>> basedataMap = new HashMap<>();
            for (GD5Report report : list) {
                List<GD5Report> reports = basedataMap.get(report.getIp() + ":" + report.getDevid());
                if(null == reports){
                    reports = new ArrayList<>();
                }
                reports.add(report);
                basedataMap.put(report.getIp() + ":" + report.getDevid(),reports);
            }
            return basedataMap;
        }
    }

    private Map<String,Integer> sensorMap(List<Sensor> sensors){
        if(null == sensors || sensors.isEmpty()){
            return null;
        } else {
            HashMap<String,Integer> sensorMap = new HashMap<>();
            for (Sensor sensor : sensors) {
                sensorMap.put(sensor.getIpaddr() + ":" + sensor.getSensorId(), sensor.getId());
            }
            return sensorMap;
        }
    }




}
