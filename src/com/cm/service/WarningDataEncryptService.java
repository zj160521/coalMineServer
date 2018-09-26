package com.cm.service;

import com.cm.controller.EncryptDataFileUtils;
import com.cm.entity.vo.SSParaVo;
import com.cm.entity.vo.SSWarning;
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

    private String startTime;
    private String endTime;
    private String nowTime;
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat df1 = new SimpleDateFormat("yyyyMMddHHmmss");
    private HashMap<Integer, String> map = new HashMap<>();
    private final String lockKey = "WarningDataEncryptService";
    private final String requestId = UUID.randomUUID().toString();
    private long expireTime = 10000L;

    @Scheduled(cron = "0/30 * * * * ?")
    public void WarningDataEncryptMethod(){
        try{
            boolean lock = RedisPool.tryGetDistributedLock(lockKey, requestId, expireTime);
            if (lock){

                String property = System.getProperty("os.name");
                if(property.startsWith("Linux")){

                    getStartEndTime();//获取开始时间和结束时间

                    makeMap();
                    String v = stService.getV(8);
                    String fileName = "/" + v + "_YCBJ_" + nowTime + ".TXT";

                    StringBuffer sb = new StringBuffer();
                    sb.append(df.format(new Date()) + ";");

                    SSParaVo para = new SSParaVo();
                    para.setStarttime(startTime);
                    para.setEndtime(endTime);
                    List<SSWarning> list = siService.getWarnimgRec(para);//获取基础数据
                    if(list.isEmpty()){
                        sb.append(list.size() + ";~||\r\n");
                    } else {
                        sb.append(list.size() + ";~\r\n");
                        for (SSWarning warning : list) {
                            if(warning.getSensorId()==0||warning.getSensor_id()==0){
                                continue;
                            }
                            StringBuffer sensorPoint = EncryptDataFileUtils.definitionOfMeasurePoints(warning.getSensorId(), warning.getSensor_type());
                            sb.append(sensorPoint + ";");
                            sb.append(map.get(warning.getStatus()) + ";");
                            sb.append(warning.getStartValue() + ";");
                            sb.append(warning.getStartValue()-0.02 + ";");
                            sb.append(warning.getStartValue()-0.03 + ";");
                            sb.append(warning.getStarttime() + ";");
                            sb.append(warning.getEndtime() + ";");
                            if(warning.getStartValue() > warning.getEndValue()){
                                sb.append(warning.getStartValue() + ";");
                                sb.append(warning.getStarttime() + ";");
                                sb.append(warning.getEndtime() + ";");
                                sb.append((warning.getEndValue()+warning.getStartValue())/2 + ";");
                                sb.append(warning.getEndValue() + ";");

                            } else {
                                sb.append(warning.getEndValue() + ";");
                                sb.append(warning.getEndtime() + ";");
                                sb.append(warning.getStarttime() + ";");
                                sb.append((warning.getEndValue()+warning.getStartValue())/2 + ";");
                                sb.append(warning.getStartValue());
                            }
                            sb.append(warning.getMeasure() + ";");
                            sb.append(warning.getMeasuretime() + ";");

                            if(list.indexOf(warning) == list.size()-1){
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
            StackTraceElement[] elements = e.getStackTrace();
            for (int i = 0; i < elements.length; i++) {
                sb.append(elements[i].toString());
            }
            LogOut.log.error(sb.toString());
        }


    }

    public void getStartEndTime(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND,-30);
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
    }


}
