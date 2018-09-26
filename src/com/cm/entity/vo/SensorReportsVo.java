package com.cm.entity.vo;

public class SensorReportsVo {
	
	private int sensor_id;
	private String alais;//点号
	private String position;//地址
	private int sensor_type;
	private String sensortype;//传感器类型
	private String unit;//单位
	private double maxvalues;//最大值
	private double minvalue;//最小值
	private double midvalue;//平均值
	private String maxtime;//最大值时间
	private String limit_alarm;//报警门限
	private String limit_power;//断电门限
	private String limit_repower;//复电门限
	private double limit_alarms;
	private double limit_powers;
	private double limit_repowers;
	private double floor_alarms;
	private double floor_powers;
	private double floor_repowers;
	private int alerts;//报警次数
	private String alerttime;//累计报警时长
	private int powerfres;//断电次数
	private String powertime;//累计断电时长
	private int faults;//设备故障次数
	private String faulttime;//累计故障时长
	private int feeabnums;//馈电异常次数
	private String feedtime;//馈电异常时长
	public int getSensor_id() {
		return sensor_id;
	}
	public void setSensor_id(int sensor_id) {
		this.sensor_id = sensor_id;
	}
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
	public double getMidvalue() {
		return midvalue;
	}
	public void setMidvalue(double midvalue) {
		this.midvalue = midvalue;
	}
	public String getMaxtime() {
		return maxtime;
	}
	public void setMaxtime(String maxtime) {
		this.maxtime = maxtime;
	}
	public String getLimit_alarm() {
		return limit_alarm;
	}
	public void setLimit_alarm(String limit_alarm) {
		this.limit_alarm = limit_alarm;
	}
	public String getLimit_power() {
		return limit_power;
	}
	public void setLimit_power(String limit_power) {
		this.limit_power = limit_power;
	}
	public String getLimit_repower() {
		return limit_repower;
	}
	public void setLimit_repower(String limit_repower) {
		this.limit_repower = limit_repower;
	}
	public double getLimit_alarms() {
		return limit_alarms;
	}
	public void setLimit_alarms(double limit_alarms) {
		this.limit_alarms = limit_alarms;
	}
	public double getLimit_powers() {
		return limit_powers;
	}
	public void setLimit_powers(double limit_powers) {
		this.limit_powers = limit_powers;
	}
	public double getLimit_repowers() {
		return limit_repowers;
	}
	public void setLimit_repowers(double limit_repowers) {
		this.limit_repowers = limit_repowers;
	}
	public double getFloor_alarms() {
		return floor_alarms;
	}
	public void setFloor_alarms(double floor_alarms) {
		this.floor_alarms = floor_alarms;
	}
	public double getFloor_powers() {
		return floor_powers;
	}
	public void setFloor_powers(double floor_powers) {
		this.floor_powers = floor_powers;
	}
	public double getFloor_repowers() {
		return floor_repowers;
	}
	public void setFloor_repowers(double floor_repowers) {
		this.floor_repowers = floor_repowers;
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
	public int getSensor_type() {
		return sensor_type;
	}
	public void setSensor_type(int sensor_type) {
		this.sensor_type = sensor_type;
	}
	
}
