package com.cm.service.kafka;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import util.LogOut;
@Scope("singleton")
@Service
public class ConfigSyncThread extends Thread {

	
	private static BlockingQueue<String> SendQueue = new LinkedBlockingQueue<String>();
	
	@SuppressWarnings("resource")
	@Override
	public void run() {
		LogOut.log.debug("ConfigSyncThread Start");
		
		KafkaProducer<String,String> producer = KafkaUtil.getProducer("configsync");

		while(true) {
			try {
				String msg = GetMessage();
				if (msg == null) continue;
				
				// kafka send
				ProducerRecord<String,String> record = new ProducerRecord<String, String>("configsync", msg);
				producer.send(record, new Callback() {
					public void onCompletion(RecordMetadata metadata, Exception e) {
						if(null!=e)
							e.printStackTrace();
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
				producer = KafkaUtil.getProducer("configsync");
				StringBuffer sb = new StringBuffer();
				StackTraceElement[] stackTrace = e.getStackTrace();
				for (int i = 0; i < stackTrace.length; i++) {
					StackTraceElement element = stackTrace[i];
					sb.append(element.toString()+"\n");
				}
				LogOut.log.error("ConfigSyncThread Exception-"+sb.toString());
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	public String GetMessage() {
		String msg = null;
		try {
			msg = SendQueue.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return msg;
	}
	
	public static void SendMessage(String msg) {
		try {
			SendQueue.put(msg);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
