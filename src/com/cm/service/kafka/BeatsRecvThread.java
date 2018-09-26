package com.cm.service.kafka;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.cm.entity.vo.BeatMssage;

import util.LogOut;

public class BeatsRecvThread extends Thread {

	String message = "";
	
	Logger logger = Logger.getRootLogger();

	private static HashMap<String, BeatMssage> map = new HashMap<String, BeatMssage>();

	public static BlockingQueue<ConsumerRecord<String, String>> beatsRecvQueue = new LinkedBlockingQueue<ConsumerRecord<String, String>>();

	@Override
	public void run() {
		LogOut.log.debug("BeatsRecvThread Start");
		while (true) {
			try {
				ConsumerRecord<String, String> record = beatsRecvQueue.poll(1000, TimeUnit.MICROSECONDS);
				if(record!=null){
					message = record.value();
					JSONObject object = JSONObject.parseObject(message);
					String ip = object.getString("ip");
					int port = object.getIntValue("port");
					String servicename = object.getString("servicename");
					long time = object.getLongValue("time");
					String service = ip + ":" + port;
					BeatMssage beat = new BeatMssage();
					beat.setIp(ip);
					beat.setPort(port);
					beat.setServicename(servicename);
					beat.setTime(time);
					map.put(service, beat);
				}
				Iterator<Entry<String, BeatMssage>> it = map.entrySet().iterator();
				while(it.hasNext()){
					Entry<String, BeatMssage> next = it.next();
					BeatMssage mssage = next.getValue();
					if((System.currentTimeMillis() - mssage.getTime())/1000 > 30){
						it.remove();
					}
				}
//				Thread.sleep(1000);
//				LogOut.log.debug("BeatsRecvThread data-"+record.value());
			} catch (Exception e) {
				e.printStackTrace();
				StringBuffer sb = new StringBuffer();
				StackTraceElement[] stackTrace = e.getStackTrace();
				for (int i = 0; i < stackTrace.length; i++) {
					StackTraceElement element = stackTrace[i];
					sb.append(element.toString()+"\n");
				}
				LogOut.log.error("BeatsRecvThread Exception-"+sb.toString());
			}
		}
	}
	
	public static HashMap<String, BeatMssage> getMap(){
		return map;
	}

}
