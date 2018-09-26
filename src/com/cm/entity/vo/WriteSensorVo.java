package com.cm.entity.vo;

public class WriteSensorVo {
	private int ipaddr;// 分站id
	private int sensorId;// 设备编号
	private int sensor_type;// 设备类型id
	private double limit_warning;// 上限预警
	private double floor_warning;// 下限预警
	private double limit_alarm;// 上限报警
	private double floor_alarm;// 下限报警
	private double limit_power;// 上限断电值
	private double floor_power;// 下限断电值
	private double limit_repower;// 上限复电值
	private double floor_repower;// 下限复电值
	private int alarm_status;//开关量报警状态 0表示断开 1表示接通
	private int is_power; //是否断电 0表示不断电 1表示断电
	private int status;//1表示模拟量 2表示开关量
	
	public int getIpaddr() {
		return ipaddr;
	}
	public void setIpaddr(int ipaddr) {
		this.ipaddr = ipaddr;
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
	public double getLimit_warning() {
		return limit_warning;
	}
	public void setLimit_warning(double limit_warning) {
		this.limit_warning = limit_warning;
	}
	public double getFloor_warning() {
		return floor_warning;
	}
	public void setFloor_warning(double floor_warning) {
		this.floor_warning = floor_warning;
	}
	public double getLimit_alarm() {
		return limit_alarm;
	}
	public void setLimit_alarm(double limit_alarm) {
		this.limit_alarm = limit_alarm;
	}
	public double getFloor_alarm() {
		return floor_alarm;
	}
	public void setFloor_alarm(double floor_alarm) {
		this.floor_alarm = floor_alarm;
	}
	public double getLimit_power() {
		return limit_power;
	}
	public void setLimit_power(double limit_power) {
		this.limit_power = limit_power;
	}
	public double getFloor_power() {
		return floor_power;
	}
	public void setFloor_power(double floor_power) {
		this.floor_power = floor_power;
	}
	public double getLimit_repower() {
		return limit_repower;
	}
	public void setLimit_repower(double limit_repower) {
		this.limit_repower = limit_repower;
	}
	public double getFloor_repower() {
		return floor_repower;
	}
	public void setFloor_repower(double floor_repower) {
		this.floor_repower = floor_repower;
	}
	public int getAlarm_status() {
		return alarm_status;
	}
	public void setAlarm_status(int alarm_status) {
		this.alarm_status = alarm_status;
	}
	public int getIs_power() {
		return is_power;
	}
	public void setIs_power(int is_power) {
		this.is_power = is_power;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
}
