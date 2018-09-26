package com.cm.entity;

public class SensorAlarmreport {
	
	private int	id; 
	private String ip;
	private int sensorId;
	private int sensor_type;
	private String position;
	private String starttime;
	private String endtime;
	private double maxvalues;
	private String maxtime;
	private double avgvalue;
	private String times;
	private int status;
	private String measures;
	private String measurestime;
	private String powercom;
	private String powerposition;
	private String feedstatus;
	private String filltime;
	private int debug;
	private String feeduid;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public double getMaxvalues() {
		return maxvalues;
	}
	public void setMaxvalues(double maxvalues) {
		this.maxvalues = maxvalues;
	}
	public String getMaxtime() {
		return maxtime;
	}
	public void setMaxtime(String maxtime) {
		this.maxtime = maxtime;
	}
	public double getAvgvalue() {
		return avgvalue;
	}
	public void setAvgvalue(double avgvalue) {
		this.avgvalue = avgvalue;
	}
	public String getTimes() {
		return times;
	}
	public void setTimes(String times) {
		this.times = times;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMeasures() {
		return measures;
	}
	public void setMeasures(String measures) {
		this.measures = measures;
	}
	public String getMeasurestime() {
		return measurestime;
	}
	public void setMeasurestime(String measurestime) {
		this.measurestime = measurestime;
	}
	public String getPowercom() {
		return powercom;
	}
	public void setPowercom(String powercom) {
		this.powercom = powercom;
	}
	public String getPowerposition() {
		return powerposition;
	}
	public void setPowerposition(String powerposition) {
		this.powerposition = powerposition;
	}
	public String getFeedstatus() {
		return feedstatus;
	}
	public void setFeedstatus(String feedstatus) {
		this.feedstatus = feedstatus;
	}
	public String getFilltime() {
		return filltime;
	}
	public void setFilltime(String filltime) {
		this.filltime = filltime;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public int getDebug() {
		return debug;
	}
	public void setDebug(int debug) {
		this.debug = debug;
	}
	public String getFeeduid() {
		return feeduid;
	}
	public void setFeeduid(String feeduid) {
		this.feeduid = feeduid;
	}
	
}
