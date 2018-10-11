package com.cm.entity;

public class SensorReport {
	
	private String ip;//分站ip
	private int sensor_id;//设备Id
	private int sensorId;//通道号
	private int type;//设备类型
	private double maxvalues;//最大值
	private double minvalue;//最小值
	private double avgvalue;//平局值
	private String maxtime;//最大值时间
	private int alerts;//报警次数
	private String alerttime;//累计报警时长
	private int powerfres;//断电次数
	private String powertime;//累计断电时长
	private int faults;//设备故障次数
	private String faulttime;//累计故障时长
	private int feeabnums;//馈电异常次数
	private String feedtime;//馈电异常时长
	private String filltime;//统计日期
	private int remark;
	private String classname;
	private String classstart;
	private String classend;
	public String getClassname() {
		return classname;
	}
	public void setClassname(String classname) {
		this.classname = classname;
	}
	public String getClassstart() {
		return classstart;
	}
	public void setClassstart(String classstart) {
		this.classstart = classstart;
	}
	public String getClassend() {
		return classend;
	}
	public void setClassend(String classend) {
		this.classend = classend;
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public double getMaxvalues() {
		return maxvalues;
	}
	public void setMaxvalues(double maxvalues) {
		this.maxvalues = maxvalues;
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
	public int getAlerts() {
		return alerts;
	}
	public void setAlerts(int alerts) {
		this.alerts = alerts;
	}
	public String getAlerttime() {
		return alerttime;
	}
	public void setAlerttime(String alerttime) {
		this.alerttime = alerttime;
	}
	public int getPowerfres() {
		return powerfres;
	}
	public void setPowerfres(int powerfres) {
		this.powerfres = powerfres;
	}
	public String getPowertime() {
		return powertime;
	}
	public void setPowertime(String powertime) {
		this.powertime = powertime;
	}
	public int getFaults() {
		return faults;
	}
	public void setFaults(int faults) {
		this.faults = faults;
	}
	public String getFaulttime() {
		return faulttime;
	}
	public void setFaulttime(String faulttime) {
		this.faulttime = faulttime;
	}
	public int getFeeabnums() {
		return feeabnums;
	}
	public void setFeeabnums(int feeabnums) {
		this.feeabnums = feeabnums;
	}
	public String getFeedtime() {
		return feedtime;
	}
	public void setFeedtime(String feedtime) {
		this.feedtime = feedtime;
	}
	public String getFilltime() {
		return filltime;
	}
	public void setFilltime(String filltime) {
		this.filltime = filltime;
	}
	public int getSensor_id() {
		return sensor_id;
	}
	public void setSensor_id(int sensor_id) {
		this.sensor_id = sensor_id;
	}
	public int getRemark() {
		return remark;
	}
	public void setRemark(int remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "SensorReport [ip=" + ip + ", sensor_id=" + sensor_id
				+ ", sensorId=" + sensorId + ", type=" + type + ", maxvalues="
				+ maxvalues + ", minvalue=" + minvalue + ", avgvalue="
				+ avgvalue + ", maxtime=" + maxtime + ", alerts=" + alerts
				+ ", alerttime=" + alerttime + ", powerfres=" + powerfres
				+ ", powertime=" + powertime + ", faults=" + faults
				+ ", faulttime=" + faulttime + ", feeabnums=" + feeabnums
				+ ", feedtime=" + feedtime + ", filltime=" + filltime + "]";
	}
	
	
}
