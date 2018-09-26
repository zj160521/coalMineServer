package com.cm.entity.vo;

public class AreaSensorVo {
	private Integer sensor_id;//传感器数据库id
	private Integer sensor_type_id;
	private String alais;
	private String uid;
	private Integer sensorId;//分站id
	private String ip;
	
	public Integer getSensorId() {
		return sensorId;
	}
	public void setSensorId(Integer sensorId) {
		this.sensorId = sensorId;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public Integer getSensor_id() {
		return sensor_id;
	}
	public void setSensor_id(Integer sensor_id) {
		this.sensor_id = sensor_id;
	}
	public Integer getSensor_type_id() {
		return sensor_type_id;
	}
	public void setSensor_type_id(Integer sensor_type_id) {
		this.sensor_type_id = sensor_type_id;
	}
	public String getAlais() {
		return alais;
	}
	public void setAlais(String alais) {
		this.alais = alais;
	}
	
	
}
