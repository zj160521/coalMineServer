package com.cm.entity.vo;

public class SwitchVo {
	
	private int sensor_id;
	private int status;
	private int alerts;//报警次数
	private String pid;//测点号
	private String typename;
	private String position;
	private String zerostate;//零态
	private String onestate;//一态
	private int value;//值
	private String valuestate;//值状态
	private String times;//累计报警时间
	public int getSensor_id() {
		return sensor_id;
	}
	public void setSensor_id(int sensor_id) {
		this.sensor_id = sensor_id;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getAlerts() {
		return alerts;
	}
	public void setAlerts(int alerts) {
		this.alerts = alerts;
	}
	
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getTypename() {
		return typename;
	}
	public void setTypename(String typename) {
		this.typename = typename;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getZerostate() {
		return zerostate;
	}
	public void setZerostate(String zerostate) {
		this.zerostate = zerostate;
	}
	public String getOnestate() {
		return onestate;
	}
	public void setOnestate(String onestate) {
		this.onestate = onestate;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public String getValuestate() {
		return valuestate;
	}
	public void setValuestate(String valuestate) {
		this.valuestate = valuestate;
	}
	public String getTimes() {
		return times;
	}
	public void setTimes(String times) {
		this.times = times;
	}

	
	
}
