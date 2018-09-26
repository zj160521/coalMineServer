package com.cm.service.kafka;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Set;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.Query;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.log4j.Logger;

import util.LogOut;

public class BeatsSendThread extends Thread {
	
	private KafkaProducer<String, String> producer = null;
	Logger logger = Logger.getRootLogger();
    private String ip = null;
	private int port = 0;
	
	@Override
	public void run() {
		LogOut.log.debug("BeatsSendThread Start");
		while (true) {
			producer = KafkaUtil.getProducer("beat_monitor");
			try {
				long time = System.currentTimeMillis();
				String value = "{\"servicename\":\"tomcat\",\"ip\":\"" + getIPaddr() + "\",\"port\":" + getPort()
						+ ",\"time\":" + time + "}";
				ProducerRecord<String, String> record = new ProducerRecord<String, String>("beat_monitor", value);
				producer.send(record, new Callback() {
					public void onCompletion(RecordMetadata metadata, Exception e) {
						if (null != e) {
							e.printStackTrace();
							logger.error(e.getMessage());
							
						}
					}
				});
				Thread.sleep(2000);
			} catch (Exception e) {
				e.printStackTrace();
				StringBuffer sb = new StringBuffer();
				StackTraceElement[] stackTrace = e.getStackTrace();
				for (int i = 0; i < stackTrace.length; i++) {
					StackTraceElement element = stackTrace[i];
					sb.append(element.toString()+"\n");
				}
				LogOut.log.error("BeatsSendThread Exception-"+sb.toString());
				producer.close();
				producer = null;
			}
		}
	}
	
	private String getIPaddr(){
		try {
			if(null!=ip&&!"".equals(ip)){
				return ip;
			}
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return ip;
	}
	
	private int getPort(){
		try {
			if(port!=0){
				return port;
			}
			MBeanServer beanServer = ManagementFactory.getPlatformMBeanServer();
			 Set<ObjectName> names = beanServer.queryNames(new ObjectName("*:type=Connector,*"),
					Query.match(Query.attr("protocol"), Query.value("HTTP/1.1")));
			String ports = names.iterator().next().getKeyProperty("port");
			port = Integer.valueOf(ports);
		} catch (MalformedObjectNameException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return port;
	}
}
