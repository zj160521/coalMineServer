package com.cm.service.kafka;

import com.alibaba.fastjson.JSONObject;
import com.cm.entity.Sensor;
import com.cm.entity.SensorLog;
import com.cm.entity.Substation;
import com.cm.entity.vo.KafkaMessage;
import com.cm.service.BaseinfoService;
import com.cm.service.SensorLogService;
import com.cm.service.SubstationService;
import com.cm.service.SwitchSensorService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
@Service
public class EquipmentSyncThread extends Thread {

	private BaseinfoService baseinfoService;

	private SwitchSensorService switchSensorService;

	private SubstationService substationService;

	private SensorLogService sensorLogService;

	private ArrayList<String> msgids = new ArrayList<String>();

	@SuppressWarnings("rawtypes")
	@Override
	public void run() {
		KafkaMsgQueue msgQueue = KafkaMsgQueue.getInstance();
		baseinfoService = baseinfoGetInstance();
		switchSensorService = switchGetInstance();
		substationService = substationGetInstance();
		sensorLogService = sensorLogGetInstance();
		if (msgQueue == null || baseinfoService == null || switchSensorService == null || substationService == null
				|| sensorLogService == null) {
			return;
		}
		synchronized (this) {
			while (true) {
				try {
					/*boolean b = RedisPool.tryGetDistributedLock(rc.jedis, lockKey, requestId, 5);
					if(!b){
						Thread.sleep(5000);
						continue;
					}*/
					List<Sensor> list = baseinfoService.getNoSync();
					msgids.clear();
					for (Sensor sensor : list) {
						KafkaMessage msg = MessageUtil.makeMessage(sensor, "sensor");
						msg.setObjtype(1);
						msgQueue.SendMessage(msg);
						String msgid = msg.getIp()+":"+msg.getId()+"-"+msg.getCmd();
						msgids.add(msgid);
					}
					long start_time = System.currentTimeMillis();
					long end_time = System.currentTimeMillis();
					while (msgids.size() > 0 && end_time - start_time < 10000) {
						try {
							Iterator itr = msgids.iterator();
							while (itr.hasNext()) {
                                String msgid = (String) itr.next();
                                KafkaMessage message = msgQueue.RecvMessage(msgid);
								KafkaMessage msg = msgQueue.GetSendMessage(msgid);
								if (null != message) {
									Substation substation = substationService.getByIp(msg.getIp());
									int id = switchSensorService.getaddedId(substation.getId(), msg.getId(),
											msg.getType());
									SensorLog sensorLog = new SensorLog();
									if (message.getStatus() == 0) {
										if (msg.getObjtype() == 1) {
											baseinfoService.updateConfigById(0, id);
										} else if (msg.getObjtype() == 2) {
											switchSensorService.updateConfigById(0, id);
										}
										sensorLog.setContent("传感器写入配置信息成功");
										sensorLog.setResult(0);
									} else {
										sensorLog.setContent("传感器写入配置信息失败");
										sensorLog.setResult(1);
									}
									sensorLog.setSensorId(id);
									sensorLog.setFeedback(
											JSONObject.toJSONString(msg) + "\n" + JSONObject.toJSONString(message));
									sensorLog.setType(message.getType());
//									sensorLogService.addSensorLog(sensorLog);

									itr.remove();
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}

						try {
							Thread.sleep(1000);
						} catch (Exception e) {
						}

						end_time = System.currentTimeMillis();
					}

					for (int i = 0; i < msgids.size(); i++) {
						KafkaMessage message = msgQueue.GetSendMessage(msgids.get(i));
						if (message != null) {
							SensorLog sensorLog = new SensorLog();
							sensorLog.setContent("传感器写入配置信息超时");
							sensorLog.setResult(1);
							sensorLog.setSensorId(message.getId());
							sensorLog.setFeedback(JSONObject.toJSONString(message));
							sensorLog.setType(message.getType());
//							sensorLogService.addSensorLog(sensorLog);
						}
					}
					/*RedisPool.releaseDistributedLock(rc.jedis, lockKey, requestId);
					JedisUtil.returnResource(rc.jedis);*/
					Thread.sleep(5000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public BaseinfoService baseinfoGetInstance() {
		if (null == baseinfoService) {
			synchronized (BaseinfoService.class) {
				BaseinfoService bean = ContextLoader.getCurrentWebApplicationContext().getBean(BaseinfoService.class);
				baseinfoService = bean;
			}
		}
		return baseinfoService;
	}

	public SwitchSensorService switchGetInstance() {
		if (null == switchSensorService) {
			synchronized (SwitchSensorService.class) {
				SwitchSensorService bean = ContextLoader.getCurrentWebApplicationContext()
						.getBean(SwitchSensorService.class);
				switchSensorService = bean;
			}
		}
		return switchSensorService;
	}

	public SubstationService substationGetInstance() {
		if (null == substationService) {
			synchronized (SubstationService.class) {
				SubstationService bean = ContextLoader.getCurrentWebApplicationContext()
						.getBean(SubstationService.class);
				substationService = bean;
			}
		}
		return substationService;
	}

	public SensorLogService sensorLogGetInstance() {
		if (null == sensorLogService) {
			synchronized (SensorLogService.class) {
				SensorLogService bean = ContextLoader.getCurrentWebApplicationContext().getBean(SensorLogService.class);
				sensorLogService = bean;
			}
		}
		return sensorLogService;
	}

}
