package com.cm.entity.vo;

import java.util.List;

public class AnaloghisVo {
	
	private String starttime;//开始时间
	private String measure;//措施
	private Double avalue;//值
	private Double avgValue;//平均值
	private Double maxValue;//最大值
	private Double minValue;//最小值
	private String alarmStatus;//报警状态
	private String powerStatus;//断电状态
	private List<String> powerStatusList;//断电状态列表
	private Integer isCut;//是否断电
	private Integer feedstatus;//馈电状态值
	private Integer feedback;//馈电值
	private List<String> feedStatusList;//馈电状态列表
	private int devid;//分站设备编号
	private String ip;//分站IP
	private String status;//开关量设备状态
	private int debug;//调校标识
	private int dataStatus;//原始数据状态
	private String feedbackstatus;//馈电状态
	private Integer level;//报警等级
	private String measuretime;//采取措施时刻
	private String alarmStartTime;//报警开始时间
	private String alarmEndTime;//报警结束时间
	private String alarmTime;//报警时长
	private String cutAlarmStartTime;//断电报警开始时间
	private String cutAlarmEndTime;//断电报警结束时间
	private String cutAlarmTime;//断电报警时长
	private List<String> feedErrorList;//馈电异常详细信息列表
	private String unit;//传感器单位
	
	public Double getAvgValue() {
		return avgValue;
	}
	public void setAvgValue(Double avgValue) {
		this.avgValue = avgValue;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public List<String> getFeedErrorList() {
		return feedErrorList;
	}
	public void setFeedErrorList(List<String> feedErrorList) {
		this.feedErrorList = feedErrorList;
	}
	public String getAlarmTime() {
		return alarmTime;
	}
	public void setAlarmTime(String alarmTime) {
		this.alarmTime = alarmTime;
	}
	public String getCutAlarmTime() {
		return cutAlarmTime;
	}
	public void setCutAlarmTime(String cutAlarmTime) {
		this.cutAlarmTime = cutAlarmTime;
	}
	public String getAlarmStartTime() {
		return alarmStartTime;
	}
	public void setAlarmStartTime(String alarmStartTime) {
		this.alarmStartTime = alarmStartTime;
	}
	public String getAlarmEndTime() {
		return alarmEndTime;
	}
	public void setAlarmEndTime(String alarmEndTime) {
		this.alarmEndTime = alarmEndTime;
	}
	public String getCutAlarmStartTime() {
		return cutAlarmStartTime;
	}
	public void setCutAlarmStartTime(String cutAlarmStartTime) {
		this.cutAlarmStartTime = cutAlarmStartTime;
	}
	public String getCutAlarmEndTime() {
		return cutAlarmEndTime;
	}
	public void setCutAlarmEndTime(String cutAlarmEndTime) {
		this.cutAlarmEndTime = cutAlarmEndTime;
	}
	public String getMeasuretime() {
		return measuretime;
	}
	public void setMeasuretime(String measuretime) {
		this.measuretime = measuretime;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public String getFeedbackstatus() {
		return feedbackstatus;
	}
	public void setFeedbackstatus(String feedbackstatus) {
		this.feedbackstatus = feedbackstatus;
	}
	public Integer getFeedback() {
		return feedback;
	}
	public void setFeedback(Integer feedback) {
		this.feedback = feedback;
	}
	public int getDataStatus() {
		return dataStatus;
	}
	public void setDataStatus(int dataStatus) {
		this.dataStatus = dataStatus;
	}
	public List<String> getPowerStatusList() {
		return powerStatusList;
	}
	public void setPowerStatusList(List<String> powerStatusList) {
		this.powerStatusList = powerStatusList;
	}
	public List<String> getFeedStatusList() {
		return feedStatusList;
	}
	public void setFeedStatusList(List<String> feedStatusList) {
		this.feedStatusList = feedStatusList;
	}
	public Integer getIsCut() {
		return isCut;
	}
	public void setIsCut(Integer isCut) {
		this.isCut = isCut;
	}
	public Integer getFeedstatus() {
		return feedstatus;
	}
	public void setFeedstatus(Integer feedstatus) {
		this.feedstatus = feedstatus;
	}
	public int getDebug() {
		return debug;
	}
	public void setDebug(int debug) {
		this.debug = debug;
	}
	public Double getMaxValue() {
		return maxValue;
	}
	public void setMaxValue(Double maxValue) {
		this.maxValue = maxValue;
	}
	public Double getMinValue() {
		return minValue;
	}
	public void setMinValue(Double minValue) {
		this.minValue = minValue;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getMeasure() {
		return measure;
	}
	public void setMeasure(String measure) {
		this.measure = measure;
	}
	public Double getAvalue() {
		return avalue;
	}
	public void setAvalue(Double avalue) {
		this.avalue = avalue;
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
