package com.cm.controller;

import com.alibaba.fastjson.JSONObject;
import com.cm.entity.SensorLog;
import com.cm.entity.SwitchSensor;
import com.cm.entity.vo.KafkaMessage;
import com.cm.security.LoginManage;
import com.cm.service.SensorLogService;
import com.cm.service.SubstationService;
import com.cm.service.kafka.KafkaMsgQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

@Scope("prototype")
@Controller
@RequestMapping("/SwitchSensorOper")
public class SwitchSensorOperController {

	@Autowired
	private ResultObj result;
	@Autowired
    private LoginManage loginManage;
	@Autowired
	private SensorLogService logService;
	@Autowired
	private SubstationService stationService;
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @RequestMapping(value = "/operation", method = RequestMethod.POST)
	@ResponseBody
	public Object addCallinfo(@RequestBody SwitchSensor sensor, HttpServletRequest request) {
	    if (!loginManage.isUserLogin(request)){
	        return result.setStatus(-1,"no login");
        }
        KafkaMessage mesg = new KafkaMessage();
        mesg.setIp(sensor.getIpaddr());
		mesg.setId(sensor.getSensorId());
		mesg.setType(sensor.getSensor_type());
        mesg.setCmd(0x12);
		mesg.setSwitch_oper(sensor.getAction());
		
		try {
            KafkaMsgQueue msgQueue = KafkaMsgQueue.getInstance();
            msgQueue.SendMessage(mesg);
            long sendtime = System.currentTimeMillis();
            long recvtime = System.currentTimeMillis();
            String msgid = mesg.getIp()+":"+mesg.getId()+"-"+mesg.getCmd();
            SensorLog log = new SensorLog();
            while(recvtime - sendtime < 10000){
                KafkaMessage message = msgQueue.RecvMessage(msgid);
                if(null != message){
                    log.setResult(message.getStatus());
                    log.setTime(df.format(new Date()));
                    log.setContent("传感器手动控制");
                    log.setSensorId(message.getId());
                    log.setType(message.getType());
                    log.setUid(stationService.getUid(message.getId(),message.getIp()));
                    if(message.getStatus() == 0){
                        log.setFeedback("传感器手动控制成功");
                        logService.addSensorLog(log);
                        return result.setStatus(0,"ok");
                    }else{
                        log.setFeedback(message.getResponse());
                        logService.addSensorLog(log);
                        return result.setStatus(message.getStatus(),message.getResponse());
                    }
                }
                recvtime = System.currentTimeMillis();
                Thread.sleep(100);
            }
            String remark = JSONObject.toJSONString(log);
            String operation2 = "手动控制设备" + log.getUid();
            loginManage.addLog(request, remark, operation2, 172);
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-2, "操作失败！");
		}
		return result.setStatus(-5, "同步超时");
	}
}

