package com.cm.service.kafka;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.cm.entity.vo.KafkaMessage;

import util.LogOut;

@Component
public class KafkaRecvThread extends Thread {

	public static BlockingQueue<ConsumerRecord<String, String>> recvQueue = new LinkedBlockingQueue<ConsumerRecord<String, String>>();

	private String message = "";

	@Override
	public void run() {

		KafkaMsgQueue messageObj = KafkaMsgQueue.getInstance();

		LogOut.log.debug("接收应答消息线程开始");
		
		ConsumerRecord<String, String> record = null;
		while (!messageObj.isKafkaStop()) {
			// kafka recieve message
			try {
				record = recvQueue.poll(1000, TimeUnit.MICROSECONDS);
				if(record!=null){
					message = record.value();
//					LogOut.log.debug("recv: " + message);
					if (null != message && message != "") {
						JSONObject object = JSONObject.parseObject(message);
						// make message object
						KafkaMessage msg = new KafkaMessage();
						if (object.get("devid") != null)
							msg.setId(object.getIntValue("devid"));
						if (object.get("ip") != null)
							msg.setIp(object.getString("ip"));
						if (object.get("cmd") != null)
							msg.setCmd(object.getIntValue("cmd"));
						if (object.get("type") != null)
							msg.setType(object.getIntValue("type"));
						if (object.get("msgid") != null)
							msg.setMsgid(object.getIntValue("msgid"));
						if (object.get("max_range") != null)
							msg.setMax_range(object.getDoubleValue("max_range"));
						if (object.get("min_range") != null)
							msg.setMin_range(object.getDoubleValue("min_range"));
						if (object.get("min_frequency") != null)
							msg.setMin_frequency(object.getDoubleValue("min_frequency"));
						if (object.get("max_frequency") != null)
							msg.setMax_frequency(object.getDoubleValue("max_frequency"));
						if (object.get("error_band") != null)
							msg.setError_band(object.getDoubleValue("error_band"));
						if (object.get("limit_warning") != null)
							msg.setLimit_warning(object.getDoubleValue("limit_warning"));
						if (object.get("floor_warning") != null)
							msg.setFloor_warning(object.getDoubleValue("floor_warning"));
						if (object.get("limit_alarm") != null)
							msg.setLimit_alarm(object.getDoubleValue("limit_alarm"));
						if (object.get("floor_alarm") != null)
							msg.setFloor_alarm(object.getDoubleValue("floor_alarm"));
						if (object.get("limit_power") != null)
							msg.setLimit_power(object.getDoubleValue("limit_power"));
						if (object.get("floor_power") != null)
							msg.setFloor_power(object.getDoubleValue("floor_power"));
						if (object.get("limit_repower") != null)
							msg.setLimit_repower(object.getDoubleValue("limit_repower"));
						if (object.get("floor_repower") != null)
							msg.setFloor_repower(object.getDoubleValue("floor_repower"));
						if (object.get("alarm_status") != null)
							msg.setAlarm_status(object.getIntValue("alarm_status"));
						if (object.get("is_power") != null)
							msg.setIs_power(object.getIntValue("is_power"));
						if (object.get("status") != null)
							msg.setStatus(object.getIntValue("status"));
						if (object.get("response") != null)
							msg.setResponse(object.getString("response"));
						if (object.get("desp") != null)
							msg.setDesp(object.getString("desp"));
						if (object.get("objtype") != null)
							msg.setObjtype(object.getInteger("objtype"));
						if (object.get("sdata") != null)
							msg.setSdata(object.getString("sdata"));
						if (object.get("rdata") != null)
							msg.setRdata(object.getString("rdata"));
						if (object.get("node") != null)
							msg.setNode(object.getIntValue("node"));
						if (object.get("station") != null)
							msg.setStation(object.getIntValue("station"));
						if (object.get("amount") != null)
							msg.setAmount(object.getIntValue("amount"));
						messageObj.PutMessage(msg);
					}
				}
			} catch(Exception e) {
				e.printStackTrace();
				LogOut.log.debug("KafKaRecvThread data-"+message);
				LogOut.log.debug("接收应答消息kafka异常，重连");
				StringBuffer sb = new StringBuffer();
				StackTraceElement[] stackTrace = e.getStackTrace();
				for (int i = 0; i < stackTrace.length; i++) {
					StackTraceElement element = stackTrace[i];
					sb.append(element.toString()+"\n");
				}
				LogOut.log.error("KafkaRecvThread Exception-"+sb.toString());
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
		super.run();
	}
	
}
