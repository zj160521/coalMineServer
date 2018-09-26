package com.cm.entity.vo;

public class AreaVo {
	private int areaId;
	private int readerId;
	private int is_exit;
	private int substation_id;
	
	public int getSubstation_id() {
		return substation_id;
	}
	public void setSubstation_id(int substation_id) {
		this.substation_id = substation_id;
	}
	public int getIs_exit() {
		return is_exit;
	}
	public void setIs_exit(int is_exit) {
		this.is_exit = is_exit;
	}
	public int getAreaId() {
		return areaId;
	}
	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}
	public int getReaderId() {
		return readerId;
	}
	public void setReaderId(int readerId) {
		this.readerId = readerId;
	}
	
}
