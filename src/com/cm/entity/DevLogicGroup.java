package com.cm.entity;

public class DevLogicGroup {
	private int id;
	private int pid;
	private int areaId;
	private String name;
	private String lgcExps;//逻辑表达式
	private String alarm;
	private int isUse;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public int getAreaId() {
		return areaId;
	}
	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLgcExps() {
		return lgcExps;
	}
	public void setLgcExps(String lgcExps) {
		this.lgcExps = lgcExps;
	}
	public String getAlarm() {
		return alarm;
	}
	public void setAlarm(String alarm) {
		this.alarm = alarm;
	}
	public int getIsUse() {
		return isUse;
	}
	public void setIsUse(int isUse) {
		this.isUse = isUse;
	}
	
}
