package com.cm.entity.vo;

import java.util.ArrayList;
import java.util.List;

public class CardReader {
	int id;
	String ip;
	int devid;
	int type;
	List<Integer> card = new ArrayList<Integer>();
	List<Integer> battary = new ArrayList<Integer>();
	List<Integer> emerge = new ArrayList<Integer>();
	String time;
	int can1;
	int can2;
	int status;
	int level;
	int debug;
	String response;
	String desp;
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
	public List<Integer> getCard() {
		return card;
	}
	public void setCard(List<Integer> card) {
		this.card = card;
	}
	public List<Integer> getBattary() {
		return battary;
	}
	public void setBattary(List<Integer> battary) {
		this.battary = battary;
	}
	public List<Integer> getEmerge() {
		return emerge;
	}
	public void setEmerge(List<Integer> emerge) {
		this.emerge = emerge;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getDebug() {
		return debug;
	}
	public void setDebug(int debug) {
		this.debug = debug;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public String getDesp() {
		return desp;
	}
	public void setDesp(String desp) {
		this.desp = desp;
	}
}