package com.cm.entity.vo;

public class SensorReportVo {
	
	private int dev_id;
	private String ip;
	private int devid;
	private int type;
	private String alais;
	private double maxvalues;
	private double minvalue;
	private double avgvalue;
	private String maxtime;
	private int status;
	private int cnt;
	private String filltime;
	public int getDev_id() {
		return dev_id;
	}
	public void setDev_id(int dev_id) {
		this.dev_id = dev_id;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getDevid() {
		return devid;
	}
	public void setDevid(int devid) {
		this.devid = devid;
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
	public double getAvgvalue() {
		return avgvalue;
	}
	public void setAvgvalue(double avgvalue) {
		this.avgvalue = avgvalue;
	}
	public String getMaxtime() {
		return maxtime;
	}
	public void setMaxtime(String maxtime) {
		this.maxtime = maxtime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	public String getFilltime() {
		return filltime;
	}
	public void setFilltime(String filltime) {
		this.filltime = filltime;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getAlais() {
		return alais;
	}
	public void setAlais(String alais) {
		this.alais = alais;
	}
	
	
}
