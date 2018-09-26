package com.cm.service.kafka;

import com.cm.controller.WebSocketController;
import com.cm.entity.*;
import com.cm.security.RedisClient;
import net.sf.json.JSONObject;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import redis.clients.jedis.Jedis;
import util.LogOut;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class KafkaConsumerThread extends Thread{
	
	public static final int MAXMSGCOUNT = 300;
	public static final int SENDINTERVAL = 1000;
	public static final int FRESHINTERVAL = 5000;
	public static final int CARDTIMEOUT = 3000 * 60;
	public static BlockingQueue<ConsumerRecord<String, String>> consumerQueue1 = new LinkedBlockingQueue<ConsumerRecord<String, String>>();
	private Jedis jedis=RedisClient.getInstance().getJedis();
	private ReaderPeople readerPeople = new ReaderPeople();
	private List sendList = new ArrayList<String>();
	
	@Override
	public void run() {
		LogOut.log.debug("接收上报消息线程开始");
		
		LogOut.log.debug("KafkaConsumerThread Start");
		long last_sent = 0;
		while(true) {
			try {

				long now = System.currentTimeMillis();
				if (sendList.size() >= 50 || now - last_sent >= 500) {
					sendMsg();
					last_sent = now;
				}

				ConsumerRecord<String, String> record = consumerQueue1.poll(500, TimeUnit.MICROSECONDS);

				// 人员定位报警信息
				if (record != null && record.topic().equals("route")) {
					// 解析json
//					LogOut.log.debug(record.value());
					JSONObject jsonObject = JSONObject.fromObject(record.value());
//					LogOut.log.debug(jsonObject);
					int type = jsonObject.getInt("type");
					Voice v = new Voice();
					if (type == 1) {
						OverTime jb = (OverTime) JSONObject.toBean(jsonObject, OverTime.class);
						String area = jedis.hget("areaName", "" + jb.getArea_id());
						setWorkName(jb.getCard_id(),v);
						v.setType(type);
						v.setText(jb.getCard_id() + "号卡在" + area + "超时" + (jb.getStay_time() - jb.getMax_time()) + "分钟报警");
						v.setResponsetime(jb.getAlarm_time());
						v.setTypeName("超时报警");
						v.setCmd("person");
						v.setStatus(2);
						v.setAreaid(jb.getArea_id());
						v.setAreaName(area);
						v.setCard_id(jb.getCard_id());
						JSONObject json = JSONObject.fromObject(v);
						sendMsg2(json.toString());
					} else if (type == 2) {
						OverMan jb = (OverMan) JSONObject.toBean(jsonObject, OverMan.class);
						String area = jedis.hget("areaName", "" + jb.getArea_id());
						v.setType(type);
						v.setText(area + "区域" + "超员" + (jb.getPersonNum() - jb.getMax_allow()) + "人报警");
						v.setResponsetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
						v.setTypeName("超员报警");
						v.setCmd("person");
						v.setStatus(2);
						v.setAreaid(jb.getArea_id());
						v.setAreaName(area);
						JSONObject json = JSONObject.fromObject(v);
						sendMsg2(json.toString());
					} else if (type == 3) {
						WorkerAreaWarn jb = (WorkerAreaWarn) JSONObject.toBean(jsonObject, WorkerAreaWarn.class);
						String area = jedis.hget("areaName", "" + jb.getArea_id());
						setWorkName(jb.getCard_id(),v);
						v.setType(type);
						v.setText(jb.getCard_id() + "号卡进入" + area + "区域报警");
						v.setResponsetime(jb.getResponsetime());
						v.setTypeName("限制区域报警");
						v.setCmd("person");
						v.setStatus(2);
						v.setAreaid(jb.getArea_id());
						v.setAreaName(area);
						v.setCard_id(jb.getCard_id());
						JSONObject json = JSONObject.fromObject(v);
						sendMsg2(json.toString());
					} else if (type == 4) {
						WorkerExitWarn jb = (WorkerExitWarn) JSONObject.toBean(jsonObject, WorkerExitWarn.class);
						String cardreader = jedis.hget("cardreaderName", jb.getSub_id() + ":" + jb.getCardreader_id());
						setWorkName(jb.getCard_id(),v);
						v.setType(type);
						v.setText(jb.getCard_id() + "号卡在读卡器" + cardreader + "门禁异常报警");
						v.setResponsetime(jb.getResponsetime());
						v.setTypeName("门禁异常报警");
						v.setCmd("person");
						v.setStatus(2);
						v.setCid(jb.getCardreader_id());
						v.setAreaid(jb.getArea_id());
						v.setCardreaderName(cardreader);
						v.setSub_id(jb.getSub_id());
						v.setCard_id(jb.getCard_id());
						JSONObject json = JSONObject.fromObject(v);
						sendMsg2(json.toString());
					} else if (type == 5) {
						WorkerUnconnection jb = (WorkerUnconnection) JSONObject.toBean(jsonObject,
								WorkerUnconnection.class);
						String area = jedis.hget("areaName", "" + jb.getArea_id());
						String cardreader = jedis.hget("cardreaderName", jb.getSub_id() + ":" + jb.getLast_cardreader());
						setWorkName(jb.getCard_id(),v);
						v.setType(type);
						v.setText(jb.getCard_id() + "号卡人员失联" + jb.getDuring() + "分钟报警，最后出现在读卡器" + cardreader + "");
						v.setResponsetime(jb.getLast_time());
						v.setTypeName("人员失联报警");
						v.setCmd("person");
						v.setStatus(2);
						v.setCid(jb.getLast_cardreader());
						v.setCardreaderName(cardreader);
						v.setAreaName(area);
						v.setCard_id(jb.getCard_id());
						JSONObject json = JSONObject.fromObject(v);
						sendMsg2(json.toString());
					} else if (type == 6) {
						Unattendance jb = (Unattendance) JSONObject.toBean(jsonObject, Unattendance.class);
						// String area=rc.jedis.hget("areaName",
						// ""+jb.getResponsearea_id());
						setWorkName(jb.getCard(),v);
						v.setType(type);
						v.setText(jb.getCard() + "号卡工作异常报警");
						v.setResponsetime(jb.getResponsetime());
						v.setTypeName("工作异常报警");
						v.setCmd("person");
						v.setStatus(2);
						v.setAreaid(jb.getResponsearea_id());
						// v.setAreaName(area);
						v.setCard_id(jb.getCard());
						JSONObject json = JSONObject.fromObject(v);
						sendMsg2(json.toString());
					} else if(type==7){
						Helpme help=(Helpme)JSONObject.toBean(jsonObject, Helpme.class);
						String cardreader = jedis.hget("cardreaderName", help.getSubid() + ":" + help.getCardreader());
						String area = jedis.hget("areaName", "" + help.getAreaid());
						setWorkName(help.getCard(),v);
						v.setType(type);
						v.setText(help.getCard() + "号卡在"+area+cardreader+"求救");
						v.setResponsetime(help.getResponsetime());
						v.setTypeName("人员求救");
						v.setCmd("helpMe");
						v.setStatus(2);
						v.setAreaid(help.getAreaid());
						v.setAreaName(area);
						v.setCard_id(help.getCard());
						v.setCardreaderName(cardreader);
						JSONObject json = JSONObject.fromObject(v);
						sendMsg2(json.toString());
					}
//					LogOut.log.debug("KafkaConsumerThread data-"+record.value());
				}
				else if(record != null && record.topic().equals("websocket")){
					MsgObj msgObj = new MsgObj();
					JSONObject jsonObj = null;

					jsonObj = parseMsg(record.value(), msgObj);
					if (jsonObj == null) {
						sendMsg2(record.value());
					}
					else {
						if (msgObj.type == 0x40) {
							String msg = readerPeople.parseReader(jsonObj);
							if (msg != null)
								sendMsg2("[" + msg + "]");
						}
						else {
							sendList.add(record.value());
						}
					}
				}
			} catch(Exception e) {
				e.printStackTrace();
//				LogOut.log.error("KafkaConsumerThread Exception-"+e.getMessage());
				jedis=RedisClient.getInstance().getJedis();
				LogOut.log.error("接收上报消息kafka异常，重连");
				StringBuffer sb = new StringBuffer();
				StackTraceElement[] elements = e.getStackTrace();
				for (int i = 0; i < elements.length; i++) {
					StackTraceElement element = elements[i];
					sb.append(element.toString()+"\n");
				}
				LogOut.log.error("KafkaConsumerThread Exception-"+sb.toString());
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	private void sendMsg() {
		if (sendList.size() > 0) {
			StringBuilder msgbuilder = new StringBuilder();
			msgbuilder.append("[");
			Iterator<String> itr = sendList.iterator();
			while(itr.hasNext()) {
				String s = itr.next();
				msgbuilder.append(s);
				if (itr.hasNext())
					msgbuilder.append(",");
			}
			msgbuilder.append("]");
			WebSocketController.pushMsg(msgbuilder.toString());
			sendList.clear();
		}
	}
	
	private void sendMsg2(String str) {
		WebSocketController.pushMsg(str);
	}
	
	class MsgObj {
		public int id;
		public int devid;
		public int type;
		public String ip;
		public String key;
	}
	
	private JSONObject parseMsg(String msg, MsgObj obj) {
		try {
			JSONObject json = JSONObject.fromObject(msg);
			if (obj != null) {
				if (json.containsKey("devid") == false)
					return null;
				obj.devid = Integer.parseInt(""+json.getString("devid"));
				obj.type = Integer.parseInt(""+json.getString("type"));
				obj.ip = json.getString("ip");
				obj.key = obj.ip + obj.devid + obj.type;
			}
			return json;
		} catch(Exception e) {
			LogOut.log.error("异常数据："+msg);
			e.printStackTrace();
		}
		return null;
	}

	public void setWorkName(int card_id,Voice v){
		int worker_id = 0;
		String worker = jedis.hget("cardWorker", String.valueOf(card_id));
		if (worker != null) {
			worker_id = Integer.parseInt(worker);
		}
		if(worker_id>0){
			String workerName=jedis.hget("workerName", String.valueOf(worker_id));
			if(workerName!=null){
				String w[]=workerName.split(";");
				v.setName(w[0]);
				v.setDepartname(w[1]);
				v.setDuty(w[2]);
				v.setWorktypename(w[3]);
				v.setWorker_id(worker_id);
			}else{
				if(!jedis.exists("workerName")){
					try {
						Thread.sleep(500);
						workerName=jedis.hget("workerName", String.valueOf(worker_id));
						if(workerName!=null){
							String w[]=workerName.split(";");
							v.setName(w[0]);
							v.setDepartname(w[1]);
							v.setDuty(w[2]);
							v.setWorktypename(w[3]);
							v.setWorker_id(worker_id);
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
