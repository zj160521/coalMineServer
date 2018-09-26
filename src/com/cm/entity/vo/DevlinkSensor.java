package com.cm.entity.vo;

public class DevlinkSensor {
	
	private String uid;
	private int sensor_type;
	private int action;
	private String ip;
	private int sensorId;
	private String dsp;
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public int getSensor_type() {
		return sensor_type;
	}
	public void setSensor_type(int sensor_type) {
		this.sensor_type = sensor_type;
	}
	public int getAction() {
		return action;
	}
	public void setAction(int action) {
		this.action = action;
	}
	public String getDsp() {
		return dsp;
	}
	public void setDsp(String dsp) {
		this.dsp = dsp;
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
	
	
}
