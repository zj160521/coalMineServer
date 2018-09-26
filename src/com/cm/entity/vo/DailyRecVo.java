package com.cm.entity.vo;

import java.util.List;

public class DailyRecVo {

	private int id;
	private int workerId;
	private String rfcard_id; // 卡号
	private String name; // 员工名字
	private int departId;
	private String departName; // 部门名称
	private int worktypeId;
	private String worktypeName; // 工种名称
	private int special;//特殊人员
	private int duty_id;//职务ID
	private String dutyname;//职务名称
	private int workplace_id;//工作区域ID
	private String workplace;//工作区域
	private String startTime; // 开始时间
	private String endTime; // 离开时间或者离井时间
	private String wellduration; // 进入时长或者下井时长
	private String workday;//日期
	private List<Object> list;//
	private String remark;//异常备注
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<Object> getList() {
		return list;
	}
	public void setList(List<Object> list) {
		this.list = list;
	}
	public int getWorkerId() {
		return workerId;
	}
	public void setWorkerId(int workerId) {
		this.workerId = workerId;
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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
	public int getDepartId() {
		return departId;
	}
	public void setDepartId(int departId) {
		this.departId = departId;
	}
	public String getDepartName() {
		return departName;
	}
	public void setDepartName(String departName) {
		this.departName = departName;
	}
	public int getWorktypeId() {
		return worktypeId;
	}
	public void setWorktypeId(int worktypeId) {
		this.worktypeId = worktypeId;
	}
	public String getWorktypeName() {
		return worktypeName;
	}
	public void setWorktypeName(String worktypeName) {
		this.worktypeName = worktypeName;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getWellduration() {
		return wellduration;
	}
	public void setWellduration(String wellduration) {
		this.wellduration = wellduration;
	}
	public String getWorkday() {
		return workday;
	}
	public void setWorkday(String workday) {
		this.workday = workday;
	}
	
	
}
