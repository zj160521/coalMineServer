package com.cm.entity.vo;

import java.util.List;

public class AreaPass {
	
	private int id;
	private int areaId;
	private String areaName;
	private String card;
	private int cardId; // 卡号
	private Integer rfcard_id;
	private int workerId;
	private String name; // 员工名字
	private int departId;
	private String departName; // 部门名称
	private int worktypeId;
	private String worktypeName; // 工种名称
	private int special;
	private int dutyId;//职务ID
	private String dutyName;//职务名称
	private int workplaceId;//工作地点ID
	private String workplace;//工作地点
	private String startTime; // 开始时间
	private String endTime; // 离开时间或者离井时间
	private String wellduration; // 进入时长或者下井时长
	private String workday;
	private int status;
	private int defaultAllow;
	private int emphasis;
	private int default_allow;
	private List<String> eptList;
	
	public Integer getRfcard_id() {
		return rfcard_id;
	}
	public void setRfcard_id(Integer rfcard_id) {
		this.rfcard_id = rfcard_id;
	}
	public String getCard() {
		return card;
	}
	public void setCard(String card) {
		this.card = card;
	}
	public int getDefault_allow() {
		return default_allow;
	}
	public void setDefault_allow(int default_allow) {
		this.default_allow = default_allow;
	}
	public List<String> getEptList() {
		return eptList;
	}
	public void setEptList(List<String> eptList) {
		this.eptList = eptList;
	}
	public int getWorkerId() {
		return workerId;
	}
	public void setWorkerId(int workerId) {
		this.workerId = workerId;
	}
	public int getDefaultAllow() {
		return defaultAllow;
	}
	public void setDefaultAllow(int defaultAllow) {
		this.defaultAllow = defaultAllow;
	}
	public int getEmphasis() {
		return emphasis;
	}
	public void setEmphasis(int emphasis) {
		this.emphasis = emphasis;
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
	public int getAreaId() {
		return areaId;
	}
	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public int getCardId() {
		return cardId;
	}
	public void setCardId(int cardId) {
		this.cardId = cardId;
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
	public int getSpecial() {
		return special;
	}
	public void setSpecial(int special) {
		this.special = special;
	}
	public int getDutyId() {
		return dutyId;
	}
	public void setDutyId(int dutyId) {
		this.dutyId = dutyId;
	}
	public String getDutyName() {
		return dutyName;
	}
	public void setDutyName(String dutyName) {
		this.dutyName = dutyName;
	}
	public int getWorkplaceId() {
		return workplaceId;
	}
	public void setWorkplaceId(int workplaceId) {
		this.workplaceId = workplaceId;
	}
	public String getWorkplace() {
		return workplace;
	}
	public void setWorkplace(String workplace) {
		this.workplace = workplace;
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
