package com.cm.entity;

import java.util.ArrayList;
import java.util.List;

import com.cm.entity.vo.SwitchStateChangeVo;

public class SwitchStateChange {
	
	private int id;
	private int sensor_id;
	private String ip;
	private int sensorId;
	private int sensor_type;
	private String alais;//别名
	private String sensortype;//设备类型
	private String position;//地址
	private int alarm_power;
	private String alarmpower;
	private String times;//累计运行时间
	private int statechangecnt;//状态变动次数
	private List<SwitchStateChangeVo> list = new ArrayList<SwitchStateChangeVo>();//每次变动详情
	private String first;
	private String second;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSensor_id() {
		return sensor_id;
	}
	public void setSensor_id(int sensor_id) {
		this.sensor_id = sensor_id;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getSensorId() {
		return sensorId;
	}
	public void setSensorId(int sensorId) {
		this.sensorId = sensorId;
	}
	public int getSensor_type() {
		return sensor_type;
	}
	public void setSensor_type(int sensor_type) {
		this.sensor_type = sensor_type;
	}
	public String getAlais() {
		return alais;
	}
	public void setAlais(String alais) {
		this.alais = alais;
	}
	public String getSensortype() {
		return sensortype;
	}
	public void setSensortype(String sensortype) {
		this.sensortype = sensortype;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public int getAlarm_power() {
		return alarm_power;
	}
	public void setAlarm_power(int alarm_power) {
		this.alarm_power = alarm_power;
	}
	public String getAlarmpower() {
		return alarmpower;
	}
	public void setAlarmpower(String alarmpower) {
		this.alarmpower = alarmpower;
	}
	public String getTimes() {
		return times;
	}
	public void setTimes(String times) {
		this.times = times;
	}
	public int getStatechangecnt() {
		return statechangecnt;
	}
	public void setStatechangecnt(int statechangecnt) {
		this.statechangecnt = statechangecnt;
	}
	public List<SwitchStateChangeVo> getList() {
		return list;
	}
	public void setList(List<SwitchStateChangeVo> list) {
		this.list = list;
	}
	public String getFirst() {
		return first;
	}
	public void setFirst(String first) {
		this.first = first;
	}
	public String getSecond() {
		return second;
	}
	public void setSecond(String second) {
		this.second = second;
	}

	
	
	
}
