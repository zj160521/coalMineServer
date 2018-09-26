package com.cm.entity;

public class WorkerAreaWarn {
	private int id;
	private int card_id;
	private int cardreader_id;
	private String responsetime;
	private int area_id;
	private int default_allow;
	private String filltime;
	private int type;
	private int worker_id;
	
	public int getWorker_id() {
		return worker_id;
	}
	public void setWorker_id(int worker_id) {
		this.worker_id = worker_id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCard_id() {
		return card_id;
	}
	public void setCard_id(int card_id) {
		this.card_id = card_id;
	}
	public int getCardreader_id() {
		return cardreader_id;
	}
	public void setCardreader_id(int cardreader_id) {
		this.cardreader_id = cardreader_id;
	}
	public String getResponsetime() {
		return responsetime;
	}
	public void setResponsetime(String responsetime) {
		this.responsetime = responsetime;
	}
	public int getArea_id() {
		return area_id;
	}
	public void setArea_id(int area_id) {
		this.area_id = area_id;
	}
	public int getDefault_allow() {
		return default_allow;
	}
	public void setDefault_allow(int default_allow) {
		this.default_allow = default_allow;
	}
	public String getFilltime() {
		return filltime;
	}
	public void setFilltime(String filltime) {
		this.filltime = filltime;
	}
	
}
