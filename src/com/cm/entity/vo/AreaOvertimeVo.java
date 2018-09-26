package com.cm.entity.vo;

public class AreaOvertimeVo {

	private String areaname;
	private int max_allow;
	private int personNum;
	private String starttime;
	private String endtime;
	private String responsetime;
	private String maxstarttime;
	private String wellduration;
	private int totalPN;
	
	public int getTotalPN() {
		return totalPN;
	}
	public void setTotalPN(int totalPN) {
		this.totalPN = totalPN;
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
	public String getMaxstarttime() {
		return maxstarttime;
	}
	public void setMaxstarttime(String maxstarttime) {
		this.maxstarttime = maxstarttime;
	}
	public String getWellduration() {
		return wellduration;
	}
	public void setWellduration(String wellduration) {
		this.wellduration = wellduration;
	}
	public String getAreaname() {
		return areaname;
	}
	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}
	public int getMax_allow() {
		return max_allow;
	}
	public void setMax_allow(int max_allow) {
		this.max_allow = max_allow;
	}
	public int getPersonNum() {
		return personNum;
	}
	public void setPersonNum(int personNum) {
		this.personNum = personNum;
	}
	public String getResponsetime() {
		return responsetime;
	}
	public void setResponsetime(String responsetime) {
		this.responsetime = responsetime;
	}
	
	
}
