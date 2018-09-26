package com.cm.entity.vo;

public class DevActionVo {
	private int id;
	private int groupId;
	private int actDevid;
	private String dev;
	private String action;
	private String param;
	
	public String getDev() {
		return dev;
	}
	public void setDev(String dev) {
		this.dev = dev;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public int getActDevid() {
		return actDevid;
	}
	public void setActDevid(int actDevid) {
		this.actDevid = actDevid;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	
}
