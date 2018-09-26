package com.cm.service;

import com.cm.controller.EncryptDataFileUtils;
import com.cm.dao.SwitchStateChangeDao;
import com.cm.entity.vo.NameTime;
import com.cm.entity.vo.SwitchStateChangeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import util.LogOut;
import util.RedisPool;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class SwitchStateChangeDataEncryptService {

    @Autowired
    private SwitchStateChangeDao changeDao;

    @Autowired
    private StaticService staticService;

    @Autowired
    private TimerRealTimeDataEncryptService service;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
    private String nowTime;
    private String startTime;
    private String endTime;
    private final String lockKey = "SwitchStateChangeDataEncryptService";
    private final String requstId = UUID.randomUUID().toString();
    private long exipreTime = 10000L;

    @Scheduled(cron = "0/60 * * * * ?")
    public void EncryptSwitchStateChangeData(){
        try{
            boolean lock = RedisPool.tryGetDistributedLock(lockKey, requstId, exipreTime);
            if (lock){

                String property = System.getProperty("os.name");
                String v = "";
                if(property.startsWith("Linux")){

                    getStartEndTime();//获取查询数据的开始时间和结束时间

                    v = staticService.getV(8);
                    String fileName = "/" + v + "_KGBH_" + nowTime + ".TXT";
                    StringBuffer sb = new StringBuffer();

                    NameTime time = new NameTime();
                    time.setStarttime(startTime);
                    time.setEndtime(endTime);

                    List<SwitchStateChangeVo> list = changeDao.getall(time);//获取数据

                    String now = sdf.format(new Date());
                    sb.append(now + ";");
                    if(list.isEmpty()){
                        sb.append(list.size() + ";~||\r\n");
                    } else {

                        sb.append(list.size() + ";~\r\n");
                        for (SwitchStateChangeVo change : list) {
                            StringBuffer sensorPoint = EncryptDataFileUtils.definitionOfMeasurePoints(change.getType(), change.getDev_id());
                            sb.append(sensorPoint + ";");
                            sb.append(change.getValue() + ";");
                            sb.append(change.getResponsetime() + ";~\r\n");
                            if(list.indexOf(change) == list.size()-1){
                                sb.append(change.getResponsetime() + ";~||\r\n");
                            } else {
                                sb.append(change.getResponsetime() + ";~\r\n");
                            }
                        }
                    }

                    service.produceData(fileName, sb);
                }
                RedisPool.releaseDistributedLock(lockKey, requstId);
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
        nowTime = df.format(cal.getTime());
        cal.add(Calendar.DAY_OF_MONTH,-1);
        endTime = sdf.format(cal.getTime());
        String substring = nowTime.substring(0,nowTime.length()-1);
        nowTime = substring + "0";
        cal.add(Calendar.MINUTE,-1);
        startTime = sdf.format(cal.getTime());
    }
}
