package com.cm.service.kafka;

import com.cm.entity.Sensor;
import com.cm.entity.SwitchSensor;
import com.cm.entity.vo.KafkaMessage;

public class MessageUtil {

	//构造消息对象
	public static KafkaMessage makeMessage(Object object,String type){
		KafkaMessage msg = new KafkaMessage();
		if("sensor".equals(type)){
			Sensor sensor = (Sensor) object;
			msg.setIp(sensor.getIpaddr());
			msg.setId(sensor.getSensorId());
			msg.setType(sensor.getSensor_type());
			msg.setCmd(3);
			msg.setError_band(sensor.getError_band());
			msg.setFloor_alarm(sensor.getFloor_alarm());
			msg.setFloor_power(sensor.getFloor_power());
			msg.setFloor_repower(sensor.getFloor_repower());
			msg.setFloor_warning(sensor.getFloor_warning());
			msg.setLimit_alarm(sensor.getLimit_alarm());
			msg.setLimit_power(sensor.getLimit_power());
			msg.setLimit_repower(sensor.getLimit_repower());
			msg.setLimit_warning(sensor.getLimit_warning());
			msg.setMax_frequency(sensor.getMax_frequency());
			msg.setMax_range(sensor.getMax_range());
			msg.setMin_frequency(sensor.getMin_frequency());
			msg.setMin_range(sensor.getMin_range());
		}else if("switchsensor".equals(type)){
			SwitchSensor sensor = (SwitchSensor) object;
			msg.setIp(sensor.getIpaddr());
			msg.setId(sensor.getSensorId());
			msg.setType(sensor.getSensor_type());
			msg.setCmd(3);
			msg.setAlarm_status(sensor.getAlarm_status());
			msg.setIs_power(sensor.getPower_status());
		}
		return msg;
	}
}
