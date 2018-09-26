package com.cm.entity.vo;

import java.util.List;

@SuppressWarnings("rawtypes")
public class DailyVo {

	private String rfcard_id; // 卡号
	private String name; // 员工名字
	private int departId;
	private String departName; // 部门名称
	private int worktypeId;
	private String worktypeName; // 工种名称
	private String workday;
	private List<String> eptList;
	private List list;
	
	public List<String> getEptList() {
		return eptList;
	}
	public void setEptList(List<String> eptList) {
		this.eptList = eptList;
	}
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
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
	public String getWorkday() {
		return workday;
	}
	public void setWorkday(String workday) {
		this.workday = workday;
	}
	
}
