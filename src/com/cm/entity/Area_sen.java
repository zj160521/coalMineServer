package com.cm.entity;

public class Area_sen {
	private int sensor_id;
	private String uid;
	private String sensor_type;
	private String position;
	private String alais;
	private int is_area_alarm;
	private int sensor_type_id;
	
	public int getSensor_type_id() {
		return sensor_type_id;
	}
	public void setSensor_type_id(int sensor_type_id) {
		this.sensor_type_id = sensor_type_id;
	}
	public int getIs_area_alarm() {
		return is_area_alarm;
	}
	public void setIs_area_alarm(int is_area_alarm) {
		this.is_area_alarm = is_area_alarm;
	}
	public String getAlais() {
		return alais;
	}
	public void setAlais(String alais) {
		this.alais = alais;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public int getSensor_id() {
		return sensor_id;
	}
	public void setSensor_id(int sensor_id) {
		this.sensor_id = sensor_id;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getSensor_type() {
		return sensor_type;
	}
	public void setSensor_type(String sensor_type) {
		this.sensor_type = sensor_type;
	}
	
}
