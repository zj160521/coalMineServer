package com.cm.entity.vo;

import java.util.List;

import com.cm.entity.Sensor;
import com.cm.entity.SwitchSensor;

public class SensorTypeVO {
	private int id;
	private int pid;
	private String k;
	private String v;
	private String path;
	private String type;
	private List<Sensor> sensors;
	private List<SwitchSensor> switches;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String getK() {
		return k;
	}
	public void setK(String k) {
		this.k = k;
	}
	public String getV() {
		return v;
	}
	public void setV(String v) {
		this.v = v;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public List<Sensor> getSensors() {
		return sensors;
	}
	public void setSensors(List<Sensor> sensors) {
		this.sensors = sensors;
	}
	public List<SwitchSensor> getSwitches() {
		return switches;
	}
	public void setSwitches(List<SwitchSensor> switches) {
		this.switches = switches;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
