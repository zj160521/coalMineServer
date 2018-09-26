package com.cm.entity.vo;

public class AnalogCurveVo {
	
	private String responsetime;//测点时间
	private String measure;//措施
//	private String position;//传感器所在位置
	private double max_value;//分钟最大值
	private double min_value;//分钟最小值
	private double avg_value;//分钟平均值
//	private String alais;//传感器别名
//	private double limit_warning;//报警值
//	private double limit_power;//断电值
//	private double limit_repower;//复电值
	private String alarmStatus;//报警状态
	private String powerStatus;//断电状态
	private String feedStatus;//馈电状态
//	private String areaname;//区域
	private int devid;//分站设备编号
	private String ip;//分站IP
	private String status;
	private String type;
	private int debug;//调校标识
	private int dataStatus;//原始数据状态
	private Integer level;//报警等级
	
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public int getDataStatus() {
		return dataStatus;
	}
	public void setDataStatus(int dataStatus) {
		this.dataStatus = dataStatus;
	}
	public int getDebug() {
		return debug;
	}
	public void setDebug(int debug) {
		this.debug = debug;
	}
	public double getAvg_value() {
		return avg_value;
	}
	public void setAvg_value(double avg_value) {
		this.avg_value = avg_value;
	}
	public String getResponsetime() {
		return responsetime;
	}
	public void setResponsetime(String responsetime) {
		this.responsetime = responsetime;
	}
	public double getMax_value() {
		return max_value;
	}
	public void setMax_value(double max_value) {
		this.max_value = max_value;
	}
	public double getMin_value() {
		return min_value;
	}
	public void setMin_value(double min_value) {
		this.min_value = min_value;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMeasure() {
		return measure;
	}
	public void setMeasure(String measure) {
		this.measure = measure;
	}
	public String getAlarmStatus() {
		return alarmStatus;
	}
	public void setAlarmStatus(String alarmStatus) {
		this.alarmStatus = alarmStatus;
	}
	public String getPowerStatus() {
		return powerStatus;
	}
	public void setPowerStatus(String powerStatus) {
		this.powerStatus = powerStatus;
	}
	public String getFeedStatus() {
		return feedStatus;
	}
	public void setFeedStatus(String feedStatus) {
		this.feedStatus = feedStatus;
	}
	public int getDevid() {
		return devid;
	}
	public void setDevid(int devid) {
		this.devid = devid;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	
}
