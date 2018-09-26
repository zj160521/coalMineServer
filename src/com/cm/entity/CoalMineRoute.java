package com.cm.entity;

import java.util.Date;

public class CoalMineRoute {

	private int id;
	private String ip;
	private int dev_id;
	private int devid;
	private int type;
	private int card;
	private int status;
	private int battary;
	private String filltime;
	private Date responsetime;
	private String response;
	private String desp;
	
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
	public int getDev_id() {
		return dev_id;
	}
	public void setDev_id(int dev_id) {
		this.dev_id = dev_id;
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
	public int getCard() {
		return card;
	}
	public void setCard(int card) {
		this.card = card;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getBattary() {
		return battary;
	}
	public void setBattary(int battary) {
		this.battary = battary;
	}
	public String getFilltime() {
		return filltime;
	}
	public void setFilltime(String filltime) {
		this.filltime = filltime;
	}
	public Date getResponsetime() {
		return responsetime;
	}
	public void setResponsetime(Date responsetime) {
		this.responsetime = responsetime;
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
