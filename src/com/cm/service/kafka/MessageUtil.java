package com.cm.service.kafka;

import com.cm.entity.Sensor;
import com.cm.entity.SwitchSensor;
import com.cm.entity.vo.KafkaMessage;

import java.lang.reflect.Field;

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
			msg.setCmd2(4);
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
			msg.setUpper_level1(sensor.getUpper_level1());
			msg.setUpper_level2(sensor.getUpper_level2());
			msg.setUpper_level3(sensor.getUpper_level3());
			msg.setUpper_level4(sensor.getUpper_level4());
		}else if("switchsensor".equals(type)){
			SwitchSensor sensor = (SwitchSensor) object;
			msg.setIp(sensor.getIpaddr());
			msg.setId(sensor.getSensorId());
			msg.setType(sensor.getSensor_type());
			msg.setCmd(3);
			msg.setCmd2(4);
			msg.setAlarm_status(sensor.getAlarm_status());
			msg.setIs_power(sensor.getPower_status());
		}
		return msg;
	}

    public static Object combineSydwCore(Object sourceBean, Object targetBean) {
	    Class sourceBeanClass = sourceBean.getClass();
	    Class targetBeanClass = targetBean.getClass();
	    Field[] sourceFields = sourceBeanClass.getDeclaredFields();
	    Field[] targetFields = targetBeanClass.getDeclaredFields();
	    for (int i = 0; i < sourceFields.length; i++) {
	        Field sourceField = sourceFields[i];
	        Field targetField = targetFields[i];
	        sourceField.setAccessible(true);
	        targetField.setAccessible(true);
	        try {
	            if (!(sourceField.get(sourceBean) == null) &&  !"serialVersionUID".equals(sourceField.getName().toString())) {
	                targetField.set(targetBean, sourceField.get(sourceBean));
	            }
	        } catch (IllegalArgumentException | IllegalAccessException e) {
	            e.printStackTrace();
	        }
	    }
	    return targetBean;
	}


}
