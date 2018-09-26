package com.cm.entity.vo;



public class AnalogVo {
	
	private int sensor_id;
	private int status;
	private int cnt;
	private String ip;//设备类型
	private int sensorId;
	private double limit_alarm;//报警门限
	private double limit_power;//断电门限
	private double limit_repower;//复电门限
	private String typename;
	private String position;
	private String unit;
	private double maxvalues;
	private double minvalue;
	private double avgvalue;
	private String maxtime;
	private String alais;

	public double getMaxvalues() {
		return maxvalues;
	}
	public void setMaxvalues(double maxvalues) {
		this.maxvalues = maxvalues;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public int getSensor_id() {
		return sensor_id;
	}
	public void setSensor_id(int sensor_id) {
		this.sensor_id = sensor_id;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
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
	public double getLimit_alarm() {
		return limit_alarm;
	}
	public void setLimit_alarm(double limit_alarm) {
		this.limit_alarm = limit_alarm;
	}
	public double getLimit_power() {
		return limit_power;
	}
	public void setLimit_power(double limit_power) {
		this.limit_power = limit_power;
	}
	public double getLimit_repower() {
		return limit_repower;
	}
	public void setLimit_repower(double limit_repower) {
		this.limit_repower = limit_repower;
	}
	public String getTypename() {
		return typename;
	}
	public void setTypename(String typename) {
		this.typename = typename;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public double getMinvalue() {
		return minvalue;
	}
	public void setMinvalue(double minvalue) {
		this.minvalue = minvalue;
	}
	public double getAvgvalue() {
		return avgvalue;
	}
	public void setAvgvalue(double avgvalue) {
		this.avgvalue = avgvalue;
	}
	public String getMaxtime() {
		return maxtime;
	}
	public void setMaxtime(String maxtime) {
		this.maxtime = maxtime;
	}
	public String getAlais() {
		return alais;
	}
	public void setAlais(String alais) {
		this.alais = alais;
	}
	
}
