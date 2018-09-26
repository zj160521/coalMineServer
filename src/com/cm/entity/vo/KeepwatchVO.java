package com.cm.entity.vo;

public class KeepwatchVO {
	private int worktype_id=0;
	private int rfcard_id=0;
	private String starttime;
	private String endtime;
	public int getWorktype_id() {
		return worktype_id;
	}
	public void setWorktype_id(int worktype_id) {
		this.worktype_id = worktype_id;
	}
	public int getRfcard_id() {
		return rfcard_id;
	}
	public void setRfcard_id(int rfcard_id) {
		this.rfcard_id = rfcard_id;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	@Override
	public String toString() {
		return "KeepwatchVO [worktype_id=" + worktype_id + ", rfcard_id=" + rfcard_id + ", starttime=" + starttime
				+ ", endtime=" + endtime + "]";
	}
	
	

}
