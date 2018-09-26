package com.cm.controller;

import com.cm.security.RedisClient;
import com.cm.service.kafka.KafkaUtil;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import redis.clients.jedis.Jedis;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@ServerEndpoint("/websocket")
public class WebSocketController {

	private static int onlineCount = 0;

	private static CopyOnWriteArraySet<WebSocketController> websocket = new CopyOnWriteArraySet<WebSocketController>();
	private BlockingQueue<String> msgQueue = new LinkedBlockingQueue<String>();
	private KafkaProducer<String,Object> producer = KafkaUtil.getProducer2("redio");

	private Session session;
	private Thread sendThread;
	private boolean isClose;
	private String fileName;
	private Thread keepaliveThread;
	private volatile boolean isKeepaliveClose;
	
	FileOutputStream fos;
	
	public static void pushMsg(String msg) {
		for (WebSocketController socket : websocket) {
			try {
				socket.msgQueue.put(msg);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	

	@OnOpen
	public void onOpen(Session session) {
		this.session = session;
		websocket.add(this);
		addOnlineCount(); // 在线数加1

		sendThread = new Thread() {
			public void run() {
				isClose = false;
				while (!isClose) {
					try {
						String msg = msgQueue.poll(2000, TimeUnit.MICROSECONDS);
						if (null != msg) {
							sendMessage(msg);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		};
		sendThread.start();
	}

	private static synchronized int getOnlineCount() {
		return onlineCount;
	}

	private void subOnlineCount() {
		WebSocketController.onlineCount--;
	}

	private void addOnlineCount() {
		WebSocketController.onlineCount++;
	}

	@OnClose
	public void onClose() {
		isClose = true;
		isKeepaliveClose = true;
		websocket.remove(this);
		subOnlineCount(); // 在线数减1
	}

	@OnMessage
	public void onMessage(final String message, Session session) {
		if(null != message){
			try {
				if(message.equals("stop")){
					try {
						fos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				sendMessage(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
    @OnMessage
    public void onMessage(ByteArrayInputStream input){  
        //【3】读二进制信息
        try{
        	Jedis j=RedisClient.getInstance().getJedis();
        	if(fos==null||(!fos.getFD().valid())){
        		fileName=getFileName();
        		File file=new File("/media/sound/"+fileName);
        		// 创建目录
        		if (!file.exists()) {
        			file.mkdirs();// 目录不存在的情况下，创建目录。
        		}
        		fos= new FileOutputStream(file);
        		//把数据存到redis中，并打开锁
        		j.set("sound", fileName);
        	}
			byte[] buffer = new byte[1024];
			int blen=-1;
            while ((blen = input.read(buffer)) != -1){
                fos.write(buffer,0,blen);
                ProducerRecord<String,Object> record = new ProducerRecord<String, Object>("redio", buffer);
				producer.send(record, new Callback() {
					public void onCompletion(RecordMetadata metadata, Exception e) {
						if(null!=e)
							e.printStackTrace();
					}
				});
            }
        }catch (Exception e) {
            e.printStackTrace();  
        }
    }

	@OnError
	public void onError(Session session, Throwable error) {
		error.printStackTrace();
	}

	private void sendMessage(String message) throws IOException {
		this.session.getBasicRemote().sendText(message);
	}
	
	private String getFileName(){
		Date day=new Date();    
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		return df.format(day)+".mp3";
	}
}
