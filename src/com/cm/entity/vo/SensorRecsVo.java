package com.cm.entity.vo;

public class SensorRecsVo {
	private String ip;
	private String devid;
	private double value;
	private int status;
	private String responsetime;
	private int type;//传感器类型
	private int isAlarm;//是否是报警状态，0：无报警，1：报警
	
	public int getIsAlarm() {
		return isAlarm;
	}
	public void setIsAlarm(int isAlarm) {
		this.isAlarm = isAlarm;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getDevid() {
		return devid;
	}
	public void setDevid(String devid) {
		this.devid = devid;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getResponsetime() {
		return responsetime;
	}
	public void setResponsetime(String responsetime) {
		this.responsetime = responsetime;
	}
	
	
}
