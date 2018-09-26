package com.cm.entity;

public class AreaWorker {

	private int id;
	private int area_id;
	private int worker_id;
	private int rfcard_id;
	
	public int getRfcard_id() {
		return rfcard_id;
	}
	public void setRfcard_id(int rfcard_id) {
		this.rfcard_id = rfcard_id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getArea_id() {
		return area_id;
	}
	public void setArea_id(int area_id) {
		this.area_id = area_id;
	}
	public int getWorker_id() {
		return worker_id;
	}
	public void setWorker_id(int worker_id) {
		this.worker_id = worker_id;
	}
	
}
