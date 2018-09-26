package com.cm.entity.vo;

import java.util.List;

public class RouteMsg {
	private int devid;
	private String ip;
	private int type;
	private List<Integer> card;
	private int id;
	private List<Integer> battary;
	private String time;
	private List<Integer> emerge;
	private String desp;
	private String response;
	private int can1;
	private int can2;
	private int debug;
	private int alarm_drop;
	private int level;
	private int status;
	public int getDevid() {
		return devid;
	}
	public void setDevid(int devid) {
		this.devid = devid;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public List<Integer> getCard() {
		return card;
	}
	public void setCard(List<Integer> card) {
		this.card = card;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<Integer> getBattary() {
		return battary;
	}
	public void setBattary(List<Integer> battary) {
		this.battary = battary;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public List<Integer> getEmerge() {
		return emerge;
	}
	public void setEmerge(List<Integer> emerge) {
		this.emerge = emerge;
	}
	public String getDesp() {
		return desp;
	}
	public void setDesp(String desp) {
		this.desp = desp;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public int getCan1() {
		return can1;
	}
	public void setCan1(int can1) {
		this.can1 = can1;
	}
	public int getCan2() {
		return can2;
	}
	public void setCan2(int can2) {
		this.can2 = can2;
	}
	public int getDebug() {
		return debug;
	}
	public void setDebug(int debug) {
		this.debug = debug;
	}
	public int getAlarm_drop() {
		return alarm_drop;
	}
	public void setAlarm_drop(int alarm_drop) {
		this.alarm_drop = alarm_drop;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
}
