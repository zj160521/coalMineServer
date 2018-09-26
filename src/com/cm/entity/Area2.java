package com.cm.entity;

import java.util.List;

public class Area2 {
	private int id;//区域id
	private String areaname;//区域名称
	private int max_allow;//最大允许人数
	private int max_time;//允许时长
	private int default_allow;//默认是否允许进入
	private String remark;//区域说明
	private int is_exit;
	private int worker_timeout;//井下人员超时时长
	private int emphasis;//是否为限制区域，1非限制区域，2限制区域
	private List<Cardreder> cardreders;//读卡器集合
	private List<Worker> workers;//员工集合
	
	public int getWorker_timeout() {
		return worker_timeout;
	}
	public void setWorker_timeout(int worker_timeout) {
		this.worker_timeout = worker_timeout;
	}
	public int getEmphasis() {
		return emphasis;
	}
	public void setEmphasis(int emphasis) {
		this.emphasis = emphasis;
	}
	public int getIs_exit() {
		return is_exit;
	}
	public void setIs_exit(int is_exit) {
		this.is_exit = is_exit;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public int getMax_time() {
		return max_time;
	}
	public void setMax_time(int max_time) {
		this.max_time = max_time;
	}
	public int getDefault_allow() {
		return default_allow;
	}
	public void setDefault_allow(int default_allow) {
		this.default_allow = default_allow;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<Cardreder> getCardreders() {
		return cardreders;
	}
	public void setCardreders(List<Cardreder> cardreders) {
		this.cardreders = cardreders;
	}
	public List<Worker> getWorkers() {
		return workers;
	}
	public void setWorkers(List<Worker> workers) {
		this.workers = workers;
	}
	
}
