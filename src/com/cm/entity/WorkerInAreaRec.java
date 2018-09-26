package com.cm.entity;

public class WorkerInAreaRec {
	private int id;
	private int worker_id;
	private String card_id;
	private String name;
	private String departName;
	private String worktypeName;
	private String theDate;
	private int area_id;
	private String areaname;
	private int dev_id;
	private String startTime;
	private String responsetime;
	private int status;
	private String remark;
	private int isuse;
	private int sub_id;
	
	public int getSub_id() {
		return sub_id;
	}
	public void setSub_id(int sub_id) {
		this.sub_id = sub_id;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getWorker_id() {
		return worker_id;
	}
	public void setWorker_id(int worker_id) {
		this.worker_id = worker_id;
	}
	public String getAreaname() {
		return areaname;
	}
	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDepartName() {
		return departName;
	}
	public void setDepartName(String departName) {
		this.departName = departName;
	}
	public String getWorktypeName() {
		return worktypeName;
	}
	public void setWorktypeName(String worktypeName) {
		this.worktypeName = worktypeName;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCard_id() {
		return card_id;
	}
	public void setCard_id(String card_id) {
		this.card_id = card_id;
	}
	public int getArea_id() {
		return area_id;
	}
	public void setArea_id(int area_id) {
		this.area_id = area_id;
	}
	public int getDev_id() {
		return dev_id;
	}
	public void setDev_id(int dev_id) {
		this.dev_id = dev_id;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getResponsetime() {
		return responsetime;
	}
	public void setResponsetime(String responsetime) {
		this.responsetime = responsetime;
	}
	@Override
	public String toString() {
		return "WorkerInAreaRec [id=" + id + ", card_id=" + card_id + ", name="
				+ name + ", departName=" + departName + ", worktypeName="
				+ worktypeName + ", area_id=" + area_id + ", areaname="
				+ areaname + ", dev_id=" + dev_id + ", startTime=" + startTime
				+ ", responsetime=" + responsetime + ", status=" + status + "]";
	}
	public String getTheDate() {
		return theDate;
	}
	public void setTheDate(String theDate) {
		this.theDate = theDate;
	}
	public int getIsuse() {
		return isuse;
	}
	public void setIsuse(int isuse) {
		this.isuse = isuse;
	}
	
	
}
