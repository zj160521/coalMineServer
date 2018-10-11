package com.cm.entity;

import java.util.List;


public class SensorCall {
	
	private int id;
	private String alais;//点号
	private String sensorkey;//唯一标识
	private String type;// 设备类型
	private String position;// 位置
	private String sensorUnit;// 单位
	private String limit_alarm;// 上限报警
	private String limit_power;// 上限断电值
	private String limit_repower;//复电门限
	private String[] alarmStarttime;//报警状态、时刻
	private String alarmEndtime;//最后一次报警结束时间
	private String[] powerStarttime;//断电状态、时刻
	private String powerEndtime;//断电结束时间
	private	String maxvalue;//最近一次统计最大值
	private String avgvalue;//最近一次统计平均值
	private String minvalue;//最近一次统计最小值
	private String powerarea;//断电区域
	private String[] feedstastus;//馈电状态、时刻
	private String measure;//措施
	private String measuretime;//措施时间
	private List<Integer> feedIds;
	private String maxvaluestime;
	private String minvaluetime;
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
	public String getSensorUnit() {
		return sensorUnit;
	}
	public void setSensorUnit(String sensorUnit) {
		this.sensorUnit = sensorUnit;
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
	public void setLimit_repower(String limit_repower) {
		this.limit_repower = limit_repower;
	}
	
	public String getAlarmEndtime() {
		return alarmEndtime;
	}
	public void setAlarmEndtime(String alarmEndtime) {
		this.alarmEndtime = alarmEndtime;
	}
	public String getPowerEndtime() {
		return powerEndtime;
	}
	public void setPowerEndtime(String powerEndtime) {
		this.powerEndtime = powerEndtime;
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
	public String getLimit_repower() {
		return limit_repower;
	}
	public String[] getAlarmStarttime() {
		return alarmStarttime;
	}
	public void setAlarmStarttime(String[] alarmStarttime) {
		this.alarmStarttime = alarmStarttime;
	}
	public String[] getPowerStarttime() {
		return powerStarttime;
	}
	public void setPowerStarttime(String[] powerStarttime) {
		this.powerStarttime = powerStarttime;
	}
	public String[] getFeedstastus() {
		return feedstastus;
	}
	public void setFeedstastus(String[] feedstastus) {
		this.feedstastus = feedstastus;
	}
	public String getMaxvalue() {
		return maxvalue;
	}
	public void setMaxvalue(String maxvalue) {
		this.maxvalue = maxvalue;
	}
	public String getAvgvalue() {
		return avgvalue;
	}
	public void setAvgvalue(String avgvalue) {
		this.avgvalue = avgvalue;
	}
	public String getMinvalue() {
		return minvalue;
	}
	public void setMinvalue(String minvalue) {
		this.minvalue = minvalue;
	}
	public List<Integer> getFeedIds() {
		return feedIds;
	}
	public void setFeedIds(List<Integer> feedIds) {
		this.feedIds = feedIds;
	}
	public String getMaxvaluestime() {
		return maxvaluestime;
	}
	public void setMaxvaluestime(String maxvaluestime) {
		this.maxvaluestime = maxvaluestime;
	}
	public String getMinvaluetime() {
		return minvaluetime;
	}
	public void setMinvaluetime(String minvaluetime) {
		this.minvaluetime = minvaluetime;
	}
	
	
	
}
