package com.cm.controller;

import com.cm.entity.SensorLog;
import com.cm.entity.vo.KafkaMessage;
import com.cm.security.LoginManage;
import com.cm.service.SensorLogService;
import com.cm.service.SubstationService;
import com.cm.service.kafka.KafkaMsgQueue;
import com.cm.service.kafka.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

@Scope
@Controller
@RequestMapping("/equip")
public class EquipController {

    @Autowired
    private ResultObj result;

    @Autowired
    private LoginManage loginManage;

    @Autowired
    private SensorLogService logService;

    @Autowired
    private SubstationService stationService;

    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 从设备读取参数
     *
     * @param ipaddr
     * @param sensorId
     * @param request
     * @return
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/select", method = RequestMethod.POST)
    @ResponseBody
    public Object selectParam(String ipaddr, Integer sensorId, Integer typeId, HttpServletRequest request) {
        if (!loginManage.isUserLogin(request)) {
            return result.setStatus(-1, "no login");
        }
        if (null == ipaddr || null == sensorId || null == typeId) {
            return result.setStatus(-3, "分站，设备id或者设备类型不能为空");
        }

        KafkaMessage msg = new KafkaMessage();
        msg.setIp(ipaddr);
        msg.setId(sensorId);
        msg.setType(typeId);
        msg.setCmd(1);
        msg.setCmd2(2);
        msg.setSendtime(System.currentTimeMillis());
        KafkaMsgQueue.getInstance().SendMessage(msg);
        result.clean();
        return result.setStatus(0, "ok");
    }

    /**
     * 轮询消息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/poll", method = RequestMethod.POST)
    @ResponseBody
    public Object pollMessage(String ipaddr, Integer sensorId, Integer typeId, HttpServletRequest request) {
        if (!loginManage.isUserLogin(request)) {
            return result.setStatus(-1, "no login");
        }
        if (null == ipaddr || null == sensorId || null == typeId) {
            return result.setStatus(-3, "分站，设备id或者设备类型不能为空");
        }
        KafkaMsgQueue msgQueue = KafkaMsgQueue.getInstance();
        String msgid = ipaddr + ":" + sensorId + "-" + 1;
        String msgid2 = ipaddr + ":" + sensorId + "-" + 2;
        KafkaMessage message1 = msgQueue.RecvMessage(msgid2);
        KafkaMessage message = msgQueue.RecvMessage(msgid);
        if (null != message && null != message1) {
            KafkaMessage kafkaMessage = (KafkaMessage) MessageUtil.combineSydwCore(message, message1);
            SensorLog log = new SensorLog();
            log.setTime(df.format(new Date()));
            log.setResult(kafkaMessage.getStatus());
            log.setContent("读取设备运行参数");
            log.setSensorId(kafkaMessage.getId());
            log.setType(kafkaMessage.getType());
            log.setUid(stationService.getUid(kafkaMessage.getId(),kafkaMessage.getIp()));
            if (message.getStatus() == 0 && message1.getStatus() ==0) {
                log.setFeedback("读取设备运行参数成功");
                logService.addSensorLog(log);
                result.clean();
                result.put("data", kafkaMessage);
                return result.setStatus(0, "ok");
            } else {
                log.setFeedback(kafkaMessage.getResponse());
                logService.addSensorLog(log);
                result.clean();
                return result.setStatus(kafkaMessage.getStatus(), kafkaMessage.getResponse());
            }
        }
        return result.setStatus(-6, "暂时没有数据");
    }

    /**
     * 轮询消息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/polll", method = RequestMethod.POST)
    @ResponseBody
    public Object pollMessages(String ipaddr, Integer sensorId, Integer typeId, HttpServletRequest request) {
        if (!loginManage.isUserLogin(request)) {
            return result.setStatus(-1, "no login");
        }
        if (null == ipaddr || null == sensorId || null == typeId) {
            return result.setStatus(-3, "分站，设备id或者设备类型不能为空");
        }
        KafkaMsgQueue msgQueue = KafkaMsgQueue.getInstance();
        String msgid = ipaddr + ":" + sensorId + "-" + 3;
        String msgid2 = ipaddr + ":" + sensorId + "-" +4;
        KafkaMessage message1 = msgQueue.RecvMessage(msgid2);
        KafkaMessage message = msgQueue.RecvMessage(msgid);
        if (null != message && null != message1) {
            KafkaMessage kafkaMessage = (KafkaMessage) MessageUtil.combineSydwCore(message, message1);
            SensorLog log = new SensorLog();
            log.setType(kafkaMessage.getType());
            log.setSensorId(kafkaMessage.getId());
            log.setContent("传感器写入配置信息");
            log.setResult(kafkaMessage.getStatus());
            log.setTime(df.format(new Date()));
            log.setUid(stationService.getUid(kafkaMessage.getId(),kafkaMessage.getIp()));
            if (message.getStatus() == 0 && message1.getStatus() == 0) {
                log.setFeedback("传感器写入配置信息成功");
                logService.addSensorLog(log);
                result.clean();
                result.put("data", kafkaMessage);
                return result.setStatus(0, "ok");
            } else {
                log.setFeedback(kafkaMessage.getResponse());
                logService.addSensorLog(log);
                result.clean();
                return result.setStatus(kafkaMessage.getStatus(), kafkaMessage.getResponse());
            }
        }
        return result.setStatus(-6, "暂时没有数据");
    }
}
