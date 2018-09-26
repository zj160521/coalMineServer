package com.cm.entity.vo;

public class CardBattaryRecordVo {

	private int rfcard_id;
	private int battary;
	private String battaryCondition;
	private String responsetime;
	private String name;
	private String departname;
	private String worktypename;
	
	public String getBattaryCondition() {
		return battaryCondition;
	}
	public void setBattaryCondition(String battaryCondition) {
		this.battaryCondition = battaryCondition;
	}
	public int getRfcard_id() {
		return rfcard_id;
	}
	public void setRfcard_id(int rfcard_id) {
		this.rfcard_id = rfcard_id;
	}
	public int getBattary() {
		return battary;
	}
	public void setBattary(int battary) {
		this.battary = battary;
	}
	public String getResponsetime() {
		return responsetime;
	}
	public void setResponsetime(String responsetime) {
		this.responsetime = responsetime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDepartname() {
		return departname;
	}
	public void setDepartname(String departname) {
		this.departname = departname;
	}
	public String getWorktypename() {
		return worktypename;
	}
	public void setWorktypename(String worktypename) {
		this.worktypename = worktypename;
	}
	

}
