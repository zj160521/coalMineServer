package com.cm.service.kafka;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;

import com.alibaba.fastjson.JSONObject;
import com.cm.service.TimerTaskController;

import util.LogOut;

public class AllConsumerThread extends Thread {
	
    private final static AtomicBoolean closed = new AtomicBoolean(false);
    private static KafkaConsumer<String, String> consumer=null;

    public void run() {
    	LogOut.log.debug("AllConsumerThread Start");
		while (true) {
			closed.set(false);
			consumer = KafkaUtil.getConsumer();
			consumer.subscribe(Arrays.asList("websocket", "route", "tomcat_ack", "beat_monitor"));
			try {
				while (!closed.get()) {
//					long start=System.currentTimeMillis();
					ConsumerRecords<String, String> records = consumer.poll(1000);
					TimerTaskController.checkSelf=System.currentTimeMillis();
					for (ConsumerRecord<String, String> record : records) {
//						LogOut.log.debug("AllConsumerThread data-"+record.topic()+": "+record.value());
						if (record.topic().equals("websocket")) {							
//							LogOut.log.debug(record.value());
							KafkaConsumerThread.consumerQueue1.put(record);
							SSDataService ms = new SSDataService();
							String str = record.value();
							ms.insert(str);
						} else if (record.topic().equals("route")) {							
//							LogOut.log.debug(record.value());
							KafkaConsumerThread.consumerQueue1.put(record);
						} else if (record.topic().equals("tomcat_ack")) {
							KafkaRecvThread.recvQueue.put(record);
							LogOut.log.debug(record.topic()+": "+record.value());
						} else if (record.topic().equals("beat_monitor")) {
//							LogOut.log.debug("心跳："+record.value());
							JSONObject object = JSONObject.parseObject(record.value());
							long time=object.getLongValue("time");
							long now=System.currentTimeMillis();
							if((now-time)/1000 < 30){
								BeatsRecvThread.beatsRecvQueue.put(record);
							}else{
//							    LogOut.log.debug("失效数据:"+record.value());	
							}
						}
					}
//					long end=System.currentTimeMillis();
//					LogOut.log.debug("poll一次用时："+(end-start)+"ms");
				}
			} catch (WakeupException e) {
				// Ignore exception if closing
				StringBuffer sb = new StringBuffer();
				StackTraceElement[] stackTrace = e.getStackTrace();
				for (int i = 0; i < stackTrace.length; i++) {
					StackTraceElement element = stackTrace[i];
					sb.append(element.toString()+"\n");
				}
				LogOut.log.error("AllConsumerThread Exception-"+sb.toString());
				if (!closed.get())
					throw e;
			} catch (InterruptedException e) {
				e.printStackTrace();
				StringBuffer sb = new StringBuffer();
				StackTraceElement[] stackTrace = e.getStackTrace();
				for (int i = 0; i < stackTrace.length; i++) {
					StackTraceElement element = stackTrace[i];
					sb.append(element.toString()+"\n");
				}
				LogOut.log.error("AllConsumerThread Exception-"+sb.toString());
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				consumer.close();
				consumer=null;
			}
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
    }

    // Shutdown hook which can be called from a separate thread
    public static void shutdown() {
        closed.set(true);
        consumer.wakeup();
    }
}
