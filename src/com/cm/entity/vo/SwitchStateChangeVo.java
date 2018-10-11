package com.cm.entity.vo;

public class SwitchStateChangeVo {
	
	private int id;
	private String ip;
	private int devid;
	private int type;
	private int dev_id;
	private int value;
	private String statechange;//变动后的状态
	private String responsetime;//状态变动时刻
	private String filltime;
	private String alais;
	private String sensortype;
	private String position;
	private String ontimes;
	private int alarm_status;
	private String alarmstatus;
	private String classname;
	private String classstart;
	private String classend;
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
	public int getDev_id() {
		return dev_id;
	}
	public void setDev_id(int dev_id) {
		this.dev_id = dev_id;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public String getStatechange() {
		return statechange;
	}
	public void setStatechange(String statechange) {
		this.statechange = statechange;
	}
	public String getResponsetime() {
		return responsetime;
	}
	public void setResponsetime(String responsetime) {
		this.responsetime = responsetime;
	}
	public String getFilltime() {
		return filltime;
	}
	public void setFilltime(String filltime) {
		this.filltime = filltime;
	}
	public String getAlais() {
		return alais;
	}
	public void setAlais(String alais) {
		this.alais = alais;
	}
	public String getSensortype() {
		return sensortype;
	}
	public void setSensortype(String sensortype) {
		this.sensortype = sensortype;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getOntimes() {
		return ontimes;
	}
	public void setOntimes(String ontimes) {
		this.ontimes = ontimes;
	}
	public String getAlarmstatus() {
		return alarmstatus;
	}
	public void setAlarmstatus(String alarmstatus) {
		this.alarmstatus = alarmstatus;
	}
	public int getAlarm_status() {
		return alarm_status;
	}
	public void setAlarm_status(int alarm_status) {
		this.alarm_status = alarm_status;
	}
	public String getClassname() {
		return classname;
	}
	public void setClassname(String classname) {
		this.classname = classname;
	}
	public String getClassstart() {
		return classstart;
	}
	public void setClassstart(String classstart) {
		this.classstart = classstart;
	}
	public String getClassend() {
		return classend;
	}
	public void setClassend(String classend) {
		this.classend = classend;
	}
	@Override
	public String toString() {
		return "SwitchStateChangeVo [id=" + id + ", ip=" + ip + ", devid="
				+ devid + ", type=" + type + ", dev_id=" + dev_id + ", value="
				+ value + ", statechange=" + statechange + ", responsetime="
				+ responsetime + ", filltime=" + filltime + ", alais=" + alais
				+ ", sensortype=" + sensortype + ", position=" + position
				+ ", ontimes=" + ontimes + "]";
	}
	
}
