package com.cm.service;

import com.cm.controller.EncryptDataFileUtils;
import com.cm.entity.vo.CoalmineVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import util.LogOut;
import util.RedisPool;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class FiveMinDateEncryptService {

    @Autowired
    private CoalmineService cmService;

    @Autowired
    private TimerRealTimeDataEncryptService service;

    @Autowired
    private StaticService stService;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
    private SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy_MM_dd");
    private String startTime;
    private String endTime;
    private String nowTime;
    private HashMap<Integer, List<CoalmineVo>> map = new HashMap<>();
    private final String lockKey = "FiveMinDateEncryptService";
    private final String requestId = UUID.randomUUID().toString();
    private long expireTime = 10000L;

    @Scheduled(cron = "0 0/5 * * * ?")
    public void EncryptFiveMinDate(){
        try{
            boolean b = RedisPool.tryGetDistributedLock(lockKey, requestId, expireTime);
            if (b){
                String v = "";
                String property = System.getProperty("os.name");
                if(property.startsWith("Linux")){

                    getStartEndTime();

                    //拼接文件名
                    v = stService.getV(8);
                    String fileName = "/" + v + "_WFSJ_" + nowTime + ".TXT";

                    StringBuffer sb = new StringBuffer();
                    sb.append(sdf.format(new Date()) + ";");


                    String tableName = "t_coalMine_" + sdf2.format(new Date());
                    List<CoalmineVo> list = cmService.getDataByTime(startTime, endTime, tableName);//获取基础数据

                    makeDateMap(list);
                    if(map.isEmpty()){
                        sb.append(map.size() + ";~||\r\n");
                    } else {

                        sb.append(map.size() + ";~\r\n");


                        int len = 0;
                        Set<Map.Entry<Integer, List<CoalmineVo>>> set = map.entrySet();
                        for (Map.Entry<Integer, List<CoalmineVo>> entry : set) {
                            len += 1;
                            double sumValue = 0.0;
                            double maxValue = 0.0;
                            String maxTime = "";
                            double minValue = 0.0;
                            String minTime = "";
                            List<CoalmineVo> list1 = entry.getValue();
                            for (int i = 0; i < list1.size(); i++) {
                                sumValue += list1.get(i).getValue();
                                if(i == 0){
                                    maxValue = list1.get(i).getValue();
                                    minValue = list1.get(i).getValue();
                                    maxTime = list1.get(i).getResponsetime();
                                    minTime = list1.get(i).getResponsetime();
                                } else {
                                    CoalmineVo coalmine = list1.get(i);
                                    if(coalmine.getValue()>maxValue){
                                        maxValue = coalmine.getValue();
                                        maxTime = coalmine.getResponsetime();
                                    }
                                    if(coalmine.getValue() < minValue){
                                        minValue = coalmine.getValue();
                                        minTime = coalmine.getResponsetime();
                                    }
                                }
                            }
                            StringBuffer sensorPoint = EncryptDataFileUtils.definitionOfMeasurePoints(list1.get(0).getType(), list1.get(0).getDev_id());
                            sb.append(sensorPoint + ";");
                            DecimalFormat df = new DecimalFormat("#.00");
                            String s = df.format(sumValue / list1.size());
                            sb.append(s + ";");
                            sb.append(maxValue + ";");
                            sb.append(maxTime + ";");
                            sb.append(minValue + ";");
                            if(len == set.size()){
                                sb.append(minTime + ";~||\r\n");
                            } else {
                                sb.append(minTime + ";~\r\n");
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
                sb.append(stackTrace[i].toString());
            }
            LogOut.log.error(sb.toString());
        }
    }

    public void getStartEndTime(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE,-5);
        endTime = sdf.format(cal.getTime());
        nowTime = sdf1.format(cal.getTime());
        String substring = nowTime.substring(0,nowTime.length()-1);
        nowTime = substring + "0";
        cal.add(Calendar.MINUTE,-5);
        startTime = sdf.format(cal.getTime());
    }

    public void makeDateMap(List<CoalmineVo> list){
        for (CoalmineVo coalmineVo : list) {
            if(coalmineVo.getType() >=50||coalmineVo.getDev_id() == 0){
                continue;
            }
            List<CoalmineVo> coalmineList = map.get(coalmineVo.getDev_id());
            if(null == coalmineList){
                coalmineList = new ArrayList<>();
            }
            coalmineList.add(coalmineVo);
            map.put(coalmineVo.getDev_id(), coalmineList);

        }
    }

}
