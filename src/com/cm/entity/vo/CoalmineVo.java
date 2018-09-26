package com.cm.entity.vo;

public class CoalmineVo {

	private String ip;
	private int dev_id;
	private int devid;
	private int type;
	private double value;
	private int status;
	private String responsetime;
	private int stcId;
	private int stcPid;
	private int debug;

    public int getDev_id() {
        return dev_id;
    }

    public void setDev_id(int dev_id) {
        this.dev_id = dev_id;
    }

    public int getDebug() {
		return debug;
	}
	public void setDebug(int debug) {
		this.debug = debug;
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getResponsetime() {
		return responsetime;
	}
	public void setResponsetime(String responsetime) {
		this.responsetime = responsetime;
	}
	public int getStcId() {
		return stcId;
	}
	public void setStcId(int stcId) {
		this.stcId = stcId;
	}
	public int getStcPid() {
		return stcPid;
	}
	public void setStcPid(int stcPid) {
		this.stcPid = stcPid;
	}
	
	
}
