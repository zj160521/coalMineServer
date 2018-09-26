package com.cm.entity.vo;

public class WorkerVo {
	
	private int id;
	private String name;//姓名
	private String rfcard_id;//卡号
	private int depart_id;//部门
	private String departname;
	private int worktype_id;//工种
	private String worktypename;
	private int special;
	private int duty_id;//职务ID
	private String dutyname;//职务名称
	private int workplace_id;//工作地点ID
	private String workplace;//工作地点
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRfcard_id() {
		return rfcard_id;
	}
	public void setRfcard_id(String rfcard_id) {
		this.rfcard_id = rfcard_id;
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
	public int getSpecial() {
		return special;
	}
	public void setSpecial(int special) {
		this.special = special;
	}
	public int getDuty_id() {
		return duty_id;
	}
	public void setDuty_id(int duty_id) {
		this.duty_id = duty_id;
	}
	public String getDutyname() {
		return dutyname;
	}
	public void setDutyname(String dutyname) {
		this.dutyname = dutyname;
	}
	public int getWorkplace_id() {
		return workplace_id;
	}
	public void setWorkplace_id(int workplace_id) {
		this.workplace_id = workplace_id;
	}
	public String getWorkplace() {
		return workplace;
	}
	public void setWorkplace(String workplace) {
		this.workplace = workplace;
	}
	
	
	
}
