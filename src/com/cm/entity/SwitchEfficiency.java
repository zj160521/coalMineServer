package com.cm.entity;

public class SwitchEfficiency {
	
	
	private int id;
	private int sensor_id;//开关量id
	private String alais;//别名
	private String ip;
	private int sensorId;
	private int sensor_type;
	private String sensortype;//类型
	private String position;//位置
	private int power;//报警及断电状态
	private String powers;
	private String statistictime;//统计时间
	private double switcheff;//开机效率
	private String switchtime;//开机时长
	private int powercnt;//开停次数
	private String remark;//备注
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
	public String getAlais() {
		return alais;
	}
	public void setAlais(String alais) {
		this.alais = alais;
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
	public int getPower() {
		return power;
	}
	public void setPower(int power) {
		this.power = power;
	}
	public String getPowers() {
		return powers;
	}
	public void setPowers(String powers) {
		this.powers = powers;
	}
	public String getStatistictime() {
		return statistictime;
	}
	public void setStatistictime(String statistictime) {
		this.statistictime = statistictime;
	}
	public double getSwitcheff() {
		return switcheff;
	}
	public void setSwitcheff(double switcheff) {
		this.switcheff = switcheff;
	}
	public String getSwitchtime() {
		return switchtime;
	}
	public void setSwitchtime(String switchtime) {
		this.switchtime = switchtime;
	}
	public int getPowercnt() {
		return powercnt;
	}
	public void setPowercnt(int powercnt) {
		this.powercnt = powercnt;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}	
	
	
}
