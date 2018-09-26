package com.cm.entity.vo;

public class LocalizeVo {

	private int rfcard_id; // 卡号
	private String name; // 员工名字
	private String departname; // 部门名称
	private String worktypename; // 工种名称
	private String starttime; // 开始时间
	private String endtime; // 离开时间或者离井时间
	private String wellduration; // 进入时长或者下井时长
	private int entry_status;//入井状态
	private int out_status;//出井状态
	private long longTimeCast;
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
	public String getWellduration() {
		return wellduration;
	}
	public void setWellduration(String wellduration) {
		this.wellduration = wellduration;
	}
	public int getEntry_status() {
		return entry_status;
	}
	public void setEntry_status(int entry_status) {
		this.entry_status = entry_status;
	}
	public int getOut_status() {
		return out_status;
	}
	public void setOut_status(int out_status) {
		this.out_status = out_status;
	}
	public long getLongTimeCast() {
		return longTimeCast;
	}
	public void setLongTimeCast(long longTimeCast) {
		this.longTimeCast = longTimeCast;
	}
	
	
}
