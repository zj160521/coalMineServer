package com.cm.service;

import com.cm.controller.EncryptDataFileUtils;
import com.cm.dao.SwitchinfoDao;
import com.cm.entity.vo.AnaloginfoQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import util.LogOut;
import util.RedisPool;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SwitchStateChangeDataEncryptService {

    @Autowired
    private SwitchinfoDao switchinfoDao;

    @Autowired
    private StaticService staticService;

    @Autowired
    private TimerRealTimeDataEncryptService service;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
    private SimpleDateFormat df1 = new SimpleDateFormat("yyyy_MM_dd");
    private String nowTime;
    private String startTime;
    private String endTime;

    @Scheduled(cron = "0/60 * * * * ?")
    public void EncryptSwitchStateChangeData(){
        try{
            String lockKey = "SwitchStateChangeDataEncryptService";
            String requestId = UUID.randomUUID().toString();
            long exipreTime = 10000l;
            boolean lock = RedisPool.tryGetDistributedLock(lockKey, requestId, exipreTime);
            if (lock){

                String property = System.getProperty("os.name");
                String v = "";
                if(property.startsWith("Linux")){

                    getStartEndTime();//获取查询数据的开始时间和结束时间

                    v = staticService.getV(8);
                    String fileName = "/" + v + "_KGBH_" + nowTime + ".TXT";
                    StringBuffer sb = new StringBuffer();

                    String tableName = "t_coalMine_" + df1.format(new Date());

                    String now = sdf.format(Calendar.getInstance().getTime());
                    sb.append(now + ";");

                    List<AnaloginfoQuery> list = switchinfoDao.getSwitchStateChange(tableName);
                    List<AnaloginfoQuery> list1 = new ArrayList<>();
                    if (null != list && !list.isEmpty() && list.size() >= 2){
                        for (int i = 0; i < list.size() - 1; i++) {
                            AnaloginfoQuery a = list.get(i);
                            AnaloginfoQuery b = list.get(i + 1);
                            if (a.getAlais().equals(b.getAlais()) && a.getMinvalue() != b.getMinvalue()){
                                if (a.getSensor_type() == 56 || a.getSensor_type() == 53) {
                                    continue;
                                }
                                list1.add(b);
                            }
                        }
                    }
                    sb.append(list1.size() + ";~||\r\n");
                    for (AnaloginfoQuery query : list1) {
                        StringBuffer points = EncryptDataFileUtils.definitionOfMeasurePoints(query.getSensor_type(), query.getSensor_id());
                        sb.append(points + ";");
                        sb.append(query.getMinvalue() + ";");
                        if (list1.indexOf(query) == (list1.size()-1)){
                            sb.append(query.getStarttime() + ";~||\r\n");
                        } else {
                            sb.append(query.getStarttime() + ";~\r\n");
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
        nowTime = df.format(cal.getTime());
        endTime = sdf.format(cal.getTime());
        String substring = nowTime.substring(0,nowTime.length()-1);
        nowTime = substring + "0";
        cal.add(Calendar.MINUTE,-1);
        startTime = sdf.format(cal.getTime());
    }
}
