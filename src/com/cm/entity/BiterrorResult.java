package com.cm.entity;

public class BiterrorResult {
	
	private int id;
	private int station;
	private String ip;
	private int devid;
	private int typeid;
	private String type;
	private int correct;
	private int amount;
	private int node;
	private double percent;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getStation() {
		return station;
	}
	public void setStation(int station) {
		this.station = station;
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
	public int getTypeid() {
		return typeid;
	}
	public void setTypeid(int typeid) {
		this.typeid = typeid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getCorrect() {
		return correct;
	}
	public void setCorrect(int correct) {
		this.correct = correct;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public double getPercent() {
		return percent;
	}
	public void setPercent(double percent) {
		this.percent = percent;
	}
	public int getNode() {
		return node;
	}
	public void setNode(int node) {
		this.node = node;
	}
	@Override
	public String toString() {
		return "BiterrorResult [station=" + station + ", ip=" + ip + ", devid=" + devid + ", typeid=" + typeid
				+ ", correct=" + correct + ", amount=" + amount + ", node=" + node + ", percent=" + percent + "]";
	}
	
}
