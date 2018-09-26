package com.cm.service.kafka;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.cm.entity.vo.KafkaMessage;

import util.LogOut;

@Component
public class KafkaSendThread extends Thread {

	@SuppressWarnings("resource")
	@Override
	public void run() {
		
		LogOut.log.debug("KafkaSendThread Start");
		
		KafkaMsgQueue messageObj = KafkaMsgQueue.getInstance();

		KafkaProducer<String,String> producer = KafkaUtil.getProducer("tomcat");

		while(!messageObj.isKafkaStop()) {
			
			try {
				KafkaMessage msg = messageObj.GetMessage();
				if (msg == null) continue;
				
				// kafka send
				String value = JSONObject.toJSONString(msg);
				ProducerRecord<String,String> record = new ProducerRecord<String, String>("tomcat", value);
				producer.send(record, new Callback() {
					public void onCompletion(RecordMetadata metadata, Exception e) {
						if(null!=e)
							e.printStackTrace();
					}
				});
				LogOut.log.debug("send: " + value);
			} catch (Exception e) {
				e.printStackTrace();
				producer = KafkaUtil.getProducer("tomcat");
				StringBuffer sb = new StringBuffer();
				StackTraceElement[] stackTrace = e.getStackTrace();
				for (int i = 0; i < stackTrace.length; i++) {
					StackTraceElement element = stackTrace[i];
					sb.append(element.toString()+"\n");
				}
				LogOut.log.error("KafkaSendThread Exception-"+sb.toString());
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
		LogOut.log.debug("KafkaSendThread Stop");
		super.run();
	}

}
