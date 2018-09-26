package com.cm.service.kafka;

import com.cm.entity.vo.KafkaMessage;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class KafkaMsgQueue {
	
	private BlockingQueue<KafkaMessage> SendQueue = new LinkedBlockingQueue<KafkaMessage>();
	private HashMap<String, KafkaMessage> RecvMap = new HashMap<String, KafkaMessage>();
	private HashMap<String, KafkaMessage> SendMap = new HashMap<String, KafkaMessage>();
	private boolean KafkaStop = false;
//	private int msgid = 1;
	private static KafkaMsgQueue instance = null;
	
	private KafkaMsgQueue() {
	}
	
	public synchronized static KafkaMsgQueue getInstance() {
		if (instance == null) {
			instance = new KafkaMsgQueue();
		}
		return instance;
	}

	// return 0: fail, >0 success
	public void SendMessage(KafkaMessage msg1) {
//		int ret = 0;
		try {
			KafkaMessage msg = (KafkaMessage) msg1.clone();
//			synchronized(this) {
//				msg.setMsgid(msgid);
//				ret = msgid++;
//			}
			SendQueue.put(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// if null: fail, not null: success
	public synchronized KafkaMessage RecvMessage(String key) {
		KafkaMessage msg = RecvMap.get(key);
		if (msg != null) {
			SendMap.remove(key);
			RecvMap.remove(key);
		}
		
		try {
			return msg;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public synchronized KafkaMessage GetSendMessage(String key){
		KafkaMessage msg = null;
		if(SendMap.containsKey(key)){
			msg = SendMap.get(key);
		}
		return msg;
	}

	public KafkaMessage GetMessage() {
		KafkaMessage msg = null;
		try {
			msg = SendQueue.poll(1000, TimeUnit.MICROSECONDS);
			synchronized(this) {
				if (msg != null) {
					msg.setLifesecond(0);
					String key = msg.getIp()+":"+msg.getId()+"-"+msg.getCmd();
					SendMap.put(key, msg);
					RecvMap.put(key, null);
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return msg;
	}
	
	public synchronized void PutMessage(KafkaMessage recvmsg) {
        recvmsg.setLifesecond(0);
        String key = recvmsg.getIp()+":"+recvmsg.getId()+"-"+recvmsg.getCmd();
        RecvMap.put(key, recvmsg);

	}
	
	public synchronized void clean() {

		for (Iterator<Map.Entry<String, KafkaMessage>> it = SendMap.entrySet().iterator(); it.hasNext();){
		    Map.Entry<String, KafkaMessage> item = it.next();
		    KafkaMessage msg = item.getValue();
		    if(null != msg) {
		    	if (System.currentTimeMillis()-msg.getSendtime()>=120000) {
		    		it.remove();
		    	}
		    }
		}
		
		for (Iterator<Map.Entry<String, KafkaMessage>> it = RecvMap.entrySet().iterator(); it.hasNext();){
		    Map.Entry<String, KafkaMessage> item = it.next();
		    KafkaMessage msg = item.getValue();
		    if(null != msg) {
		    	if (System.currentTimeMillis()-msg.getSendtime()>=120000) {
		    		it.remove();
		    	}
		    }
		}
	}
	
	public synchronized void logqueue() {
		for (Iterator<Map.Entry<String, KafkaMessage>> it = SendMap.entrySet().iterator(); it.hasNext();){
		    Map.Entry<String, KafkaMessage> item = it.next();
//		    LogOut.log.debug("SendMap: " + item.getKey() + ", " + item.getValue());
		}
		for (Iterator<Map.Entry<String, KafkaMessage>> it = RecvMap.entrySet().iterator(); it.hasNext();){
		    Map.Entry<String, KafkaMessage> item = it.next();
//		    LogOut.log.debug("RecvMap: " + item.getKey() + ", " + item.getValue());
		}
	}
	
	public synchronized boolean isKafkaStop() {
		return KafkaStop;
	}

	public synchronized void setKafkaStop(boolean kafkaStop) {
		KafkaStop = kafkaStop;
	}


}
