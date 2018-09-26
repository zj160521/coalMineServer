package com.cm.entity.vo;

import java.util.Arrays;

public class CheckcardpersonVo {
	
	private String rfcard_id;
	private String name;
	private int[] duty_id;
	private int worktype_id;
	private int department_id;
	private int workplace_id;
	private int special;
	private String month;
	private int worker_id;
	public String getRfcard_id() {
		return rfcard_id;
	}
	public void setRfcard_id(String rfcard_id) {
		this.rfcard_id = rfcard_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int[] getDuty_id() {
		return duty_id;
	}
	public void setDuty_id(int[] duty_id) {
		this.duty_id = duty_id;
	}
	public int getWorktype_id() {
		return worktype_id;
	}
	public void setWorktype_id(int worktype_id) {
		this.worktype_id = worktype_id;
	}
	public int getDepartment_id() {
		return department_id;
	}
	public void setDepartment_id(int department_id) {
		this.department_id = department_id;
	}
	
	public int getWorkplace_id() {
		return workplace_id;
	}
	public void setWorkplace_id(int workplace_id) {
		this.workplace_id = workplace_id;
	}
	public int getSpecial() {
		return special;
	}
	public void setSpecial(int special) {
		this.special = special;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public int getWorker_id() {
		return worker_id;
	}
	public void setWorker_id(int worker_id) {
		this.worker_id = worker_id;
	}
	@Override
	public String toString() {
		return "CheckcardpersonVo [rfcard_id=" + rfcard_id + ", name=" + name
				+ ", duty_id=" + Arrays.toString(duty_id) + ", worktype_id="
				+ worktype_id + ", department_id=" + department_id
				+ ", workplace_id=" + workplace_id + ", special=" + special
				+ ", month=" + month + "]";
	}
	
	
	
}
