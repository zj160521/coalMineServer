package com.cm.entity;

public class SecretData {
	
	private String alais;//别名
	private String position;//位置
	private String sensortype;//设备类型
	private String unit;//单位
	private String limit_alarm;//报警门限
	private double limit_alarms;
	private double floor_alarms;
	private double value;//值
	private int status;//状态
	private String responsetime;//时间
	private int debug;//标校状态
	private int count;//数据统计值
	public String getAlais() {
		return alais;
	}
	public void setAlais(String alais) {
		this.alais = alais;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getSensortype() {
		return sensortype;
	}
	public void setSensortype(String sensortype) {
		this.sensortype = sensortype;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getLimit_alarm() {
		return limit_alarm;
	}
	public void setLimit_alarm(String limit_alarm) {
		this.limit_alarm = limit_alarm;
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
	public int getDebug() {
		return debug;
	}
	public void setDebug(int debug) {
		this.debug = debug;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public double getLimit_alarms() {
		return limit_alarms;
	}
	public void setLimit_alarms(double limit_alarms) {
		this.limit_alarms = limit_alarms;
	}
	public double getFloor_alarms() {
		return floor_alarms;
	}
	public void setFloor_alarms(double floor_alarms) {
		this.floor_alarms = floor_alarms;
	}
	
	
}
