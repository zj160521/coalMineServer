package com.cm.entity;

public class OverMan {
	private int id;
	private int area_id;
	private int personNum;
	private int max_allow;
	private int default_allow;
	private int emphasis;
	private String filltime;
	private int type;
	
	public String getFilltime() {
		return filltime;
	}
	public void setFilltime(String filltime) {
		this.filltime = filltime;
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
	public int getArea_id() {
		return area_id;
	}
	public void setArea_id(int area_id) {
		this.area_id = area_id;
	}
	public int getPersonNum() {
		return personNum;
	}
	public void setPersonNum(int personNum) {
		this.personNum = personNum;
	}
	public int getMax_allow() {
		return max_allow;
	}
	public void setMax_allow(int max_allow) {
		this.max_allow = max_allow;
	}
	public int getDefault_allow() {
		return default_allow;
	}
	public void setDefault_allow(int default_allow) {
		this.default_allow = default_allow;
	}
	public int getEmphasis() {
		return emphasis;
	}
	public void setEmphasis(int emphasis) {
		this.emphasis = emphasis;
	}
	
}
