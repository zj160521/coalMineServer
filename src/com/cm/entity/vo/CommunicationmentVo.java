package com.cm.entity.vo;

import java.util.ArrayList;
import java.util.List;

public class CommunicationmentVo {
	
	private int id;
	private int sensor_id;//传感器主键id
	private int status;
	private int type;
	private String ip;
	private int sensorId;
	private String pid;
	private String sensortype;//传感器类型
	private String position;//传感器所在位置
	private int alerts;//设备故障次数
	private long timess;
	private String times;//累计时长
	private String alais;//别名
	private List<CommunicationVo> list = new ArrayList<CommunicationVo>();
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
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
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
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
	public int getAlerts() {
		return alerts;
	}
	public void setAlerts(int alerts) {
		this.alerts = alerts;
	}
	public String getTimes() {
		return times;
	}
	public void setTimes(String times) {
		this.times = times;
	}
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
	public List<CommunicationVo> getList() {
		return list;
	}
	public void setList(List<CommunicationVo> list) {
		this.list = list;
	}
	public long getTimess() {
		return timess;
	}
	public void setTimess(long timess) {
		this.timess = timess;
	}
	
}
