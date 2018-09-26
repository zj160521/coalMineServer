package com.cm.entity;

public class Card {

	private int id;
	private int rfcard_id; // 卡号
	private String name; // 姓名
	private int depart_id; // 部门id
	private String departname; // 部门名称
	private int worktype_id;// 工种id
	private String worktypename; // 工种名称
	private String checktime; // 检卡时间
	private String battery; // 电量
	private String starttime; // 条件:开始时间
	private String endtime; // 条件:结束时间

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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRfcard_id() {
		return rfcard_id;
	}

	public void setRfcard_id(int rfcard_id) {
		this.rfcard_id = rfcard_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDepart_id() {
		return depart_id;
	}

	public void setDepart_id(int depart_id) {
		this.depart_id = depart_id;
	}

	public String getDepartname() {
		return departname;
	}

	public void setDepartname(String departname) {
		this.departname = departname;
	}

	public int getWorktype_id() {
		return worktype_id;
	}

	public void setWorktype_id(int worktype_id) {
		this.worktype_id = worktype_id;
	}

	public String getWorktypename() {
		return worktypename;
	}

	public void setWorktypename(String worktypename) {
		this.worktypename = worktypename;
	}

	public String getChecktime() {
		return checktime;
	}

	public void setChecktime(String checktime) {
		this.checktime = checktime;
	}

	public String getBattery() {
		return battery + "%";
	}

	public void setBattery(String battery) {
		this.battery = battery;
	}

}
