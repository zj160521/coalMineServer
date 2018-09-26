package com.cm.entity;

public class OverTime {
	private int id;
	private int card_id;
	private int area_id;
	private String inarea_time;
	private String alarm_time;
	private int stay_time;
	private int max_time;
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
	public int getMax_time() {
		return max_time;
	}
	public void setMax_time(int max_time) {
		this.max_time = max_time;
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
	public int getArea_id() {
		return area_id;
	}
	public void setArea_id(int area_id) {
		this.area_id = area_id;
	}
	public String getInarea_time() {
		return inarea_time;
	}
	public void setInarea_time(String inarea_time) {
		this.inarea_time = inarea_time;
	}
	public String getAlarm_time() {
		return alarm_time;
	}
	public void setAlarm_time(String alarm_time) {
		this.alarm_time = alarm_time;
	}
	public int getStay_time() {
		return stay_time;
	}
	public void setStay_time(int stay_time) {
		this.stay_time = stay_time;
	}
	
}
