package com.cm.entity.vo;

public class CutOutVo {
	private int id;
	private int devid;
	private int param;
	private int sole;//是否保存
	
	public int getSole() {
		return sole;
	}
	public void setSole(int sole) {
		this.sole = sole;
	}
	public int getDevid() {
		return devid;
	}
	public void setDevid(int devid) {
		this.devid = devid;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getParam() {
		return param;
	}
	public void setParam(int param) {
		this.param = param;
	}
	
}
