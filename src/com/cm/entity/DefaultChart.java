package com.cm.entity;

import java.util.List;

public class DefaultChart {
	
	private int drainage_id;
	private int module_id;
	private int pid=0;
	@SuppressWarnings("rawtypes")
	private List<Drainage> list;
	public int getDrainage_id() {
		return drainage_id;
	}
	public void setDrainage_id(int drainage_id) {
		this.drainage_id = drainage_id;
	}
	public int getModule_id() {
		return module_id;
	}
	public void setModule_id(int module_id) {
		this.module_id = module_id;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	@SuppressWarnings("rawtypes")
	public List<Drainage> getList() {
		return list;
	}
	@SuppressWarnings("rawtypes")
	public void setList(List<Drainage> list) {
		this.list = list;
	}
	

}
