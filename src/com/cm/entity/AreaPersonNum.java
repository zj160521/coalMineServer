package com.cm.entity;

import java.util.List;

public class AreaPersonNum {
	private String areaid;
	private String areaname;
	private int personNum;
	private int max_allow;
	private String cmd;
	private List<AreaPersonNum> list;
	
	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	public String getAreaid() {
		return areaid;
	}
	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}
	public String getAreaname() {
		return areaname;
	}
	public void setAreaname(String areaname) {
		this.areaname = areaname;
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
	public List<AreaPersonNum> getList() {
		return list;
	}
	public void setList(List<AreaPersonNum> list) {
		this.list = list;
	}
	
}
