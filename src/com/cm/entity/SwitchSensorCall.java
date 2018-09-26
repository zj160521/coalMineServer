package com.cm.entity;

import java.util.List;

public class SwitchSensorCall {
	
	private int id;
	private String alais;//点号
	private String sensorkey;//唯一标识
	private String type;// 设备类型
	private String position;// 位置
	private String alarmstatus;//报警/断电状态
	private String[] alarmStarttime;//报警/断电时刻
	private String alarmEndtime;//最近一次报警结束时间
	private String[] powerStarttime;//断电时刻
	private String powerarea;//断电区域
	private String[] feedstastus;//馈电状态时刻
	private String feedtime;//断馈电时间范围
	private String measure;//措施
	private String measuretime;//措施时间
	private List<Integer> feedIds;
	private String chageTime;//状态变动及时刻
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAlais() {
		return alais;
	}
	public void setAlais(String alais) {
		this.alais = alais;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getAlarmEndtime() {
		return alarmEndtime;
	}
	public void setAlarmEndtime(String alarmEndtime) {
		this.alarmEndtime = alarmEndtime;
	}
	public String getSensorkey() {
		return sensorkey;
	}
	public void setSensorkey(String sensorkey) {
		this.sensorkey = sensorkey;
	}
	public String getPowerarea() {
		return powerarea;
	}
	public void setPowerarea(String powerarea) {
		this.powerarea = powerarea;
	}
	public String[] getAlarmStarttime() {
		return alarmStarttime;
	}
	public void setAlarmStarttime(String[] alarmStarttime) {
		this.alarmStarttime = alarmStarttime;
	}
	public String[] getFeedstastus() {
		return feedstastus;
	}
	public void setFeedstastus(String[] feedstastus) {
		this.feedstastus = feedstastus;
	}
	public String getFeedtime() {
		return feedtime;
	}
	public void setFeedtime(String feedtime) {
		this.feedtime = feedtime;
	}
	public String getMeasure() {
		return measure;
	}
	public void setMeasure(String measure) {
		this.measure = measure;
	}
	public String getMeasuretime() {
		return measuretime;
	}
	public void setMeasuretime(String measuretime) {
		this.measuretime = measuretime;
	}
	public String getAlarmstatus() {
		return alarmstatus;
	}
	public void setAlarmstatus(String alarmstatus) {
		this.alarmstatus = alarmstatus;
	}
	public String[] getPowerStarttime() {
		return powerStarttime;
	}
	public void setPowerStarttime(String[] powerStarttime) {
		this.powerStarttime = powerStarttime;
	}
	public void setFeedIds(List<Integer> feedIds) {
		this.feedIds = feedIds;
	}
	public List<Integer> getFeedIds() {
		return feedIds;
	}
	public String getChageTime() {
		return chageTime;
	}
	public void setChageTime(String chageTime) {
		this.chageTime = chageTime;
	}
	
	
}
