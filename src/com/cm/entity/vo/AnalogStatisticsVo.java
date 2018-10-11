package com.cm.entity.vo;

public class AnalogStatisticsVo {

	private String ip;
	private Integer sensorId;
	private Integer sensor_type;
	private String statistictime;
	private Double maxvalues;
	private String maxtime;
	private Double minvalue;
	private String mintime;
	private Double avgvalue;
	private String avgTime;
	
	public String getAvgTime() {
		return avgTime;
	}
	public void setAvgTime(String avgTime) {
		this.avgTime = avgTime;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Integer getSensorId() {
		return sensorId;
	}
	public void setSensorId(Integer sensorId) {
		this.sensorId = sensorId;
	}
	public Integer getSensor_type() {
		return sensor_type;
	}
	public void setSensor_type(Integer sensor_type) {
		this.sensor_type = sensor_type;
	}
	public String getStatistictime() {
		return statistictime;
	}
	public void setStatistictime(String statistictime) {
		this.statistictime = statistictime;
	}
	public Double getMaxvalues() {
		return maxvalues;
	}
	public void setMaxvalues(Double maxvalues) {
		this.maxvalues = maxvalues;
	}
	public String getMaxtime() {
		return maxtime;
	}
	public void setMaxtime(String maxtime) {
		this.maxtime = maxtime;
	}
	public Double getMinvalue() {
		return minvalue;
	}
	public void setMinvalue(Double minvalue) {
		this.minvalue = minvalue;
	}
	public String getMintime() {
		return mintime;
	}
	public void setMintime(String mintime) {
		this.mintime = mintime;
	}
	public Double getAvgvalue() {
		return avgvalue;
	}
	public void setAvgvalue(Double avgvalue) {
		this.avgvalue = avgvalue;
	}
	
	
}
