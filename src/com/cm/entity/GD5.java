package com.cm.entity;

public class GD5 {

	private int id;
	private String ip;//所属分站IP
	private int devid;//设备地址id
	private String responsetime;//上报时间
	private double temperature;//温度
	private double pressure;//压力
	private double wasi;//瓦斯浓度
	private double co;//一氧化碳浓度
	private double flow_work;//工况混合瞬时流量
	
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
	public String getResponsetime() {
		return responsetime;
	}
	public void setResponsetime(String responsetime) {
		this.responsetime = responsetime;
	}
	public double getTemperature() {
		return temperature;
	}
	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}
	public double getPressure() {
		return pressure;
	}
	public void setPressure(double pressure) {
		this.pressure = pressure;
	}
	public double getWasi() {
		return wasi;
	}
	public void setWasi(double wasi) {
		this.wasi = wasi;
	}
	public double getCo() {
		return co;
	}
	public void setCo(double co) {
		this.co = co;
	}
	public double getFlow_work() {
		return flow_work;
	}
	public void setFlow_work(double flow_work) {
		this.flow_work = flow_work;
	}

	
}
