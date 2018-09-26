package com.cm.service;

import com.cm.entity.Filecontent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import util.LogOut;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class CDDYEncryptFileService {

    @Autowired
    private FilecontentService fcService;

    @Autowired
    private TimerRealTimeDataEncryptService service;

    @Autowired
    private ConfigService cfService;

    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Scheduled(cron = "0/30 * * * * ?")
    public void encryptfile(){
        try{
            String property = System.getProperty("os.name");
            if (property.startsWith("Linux")){
                int count = 0;
                List<Filecontent> list = fcService.getAll();
                if (null != list && !list.isEmpty()){
                    for (Filecontent content : list) {
                        if(content.getStatus() == 1 ){
                            service.produceData(content.getFilename(), new StringBuffer().append(content.getStr()));
                            fcService.update(content.getId());
                        }
                        if (content.getStatus() == 0){
                            String file = cfService.get("encrypt_file");
                            File file1 = new File(file);
                            File[] files = file1.listFiles();
                            for (int i = 0; i < files.length; i++) {
                                if (content.getFilename().equals(files[i].getName())){
                                    count++;
                                }
                            }
                            if (count == 0){
                                service.produceData(content.getFilename(), new StringBuffer().append(content.getStr()));
                            }
                        }
                        Calendar now = Calendar.getInstance();
                        Calendar before = Calendar.getInstance();
                        Date date = df.parse(content.getFilltime());
                        before.setTime(date);
                        if((now.getTimeInMillis() - before.getTimeInMillis()) >= 3600000){
                            fcService.delete(content.getId());
                        }
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            StringBuffer buffer = new StringBuffer();
            StackTraceElement[] stackTrace = e.getStackTrace();
            for (int i = 0; i < stackTrace.length; i++) {
                StackTraceElement element = stackTrace[i];
                buffer.append(element.toString());
            }
            LogOut.log.error(buffer);
        }
    }
}
