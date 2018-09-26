package com.cm.entity.vo;



public class AnalogAllVo {
	
	private String starttime;
	private String ip;
	private int sensorId;
	private int debug;
	private Integer measureId;
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
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
	public int getDebug() {
		return debug;
	}
	public void setDebug(int debug) {
		this.debug = debug;
	}
	public Integer getMeasureId() {
		return measureId;
	}
	public void setMeasureId(Integer measureId) {
		this.measureId = measureId;
	}
	
}
