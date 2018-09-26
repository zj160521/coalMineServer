package com.cm.entity.vo;

public class SensorCallVo {
	private String starttime;//开始时间
	private String endtime;//截止时间
	private int[] sensorids;// 模拟量传感器ID数组
	private int mark; //1、表示模拟量2、表示开关量
	private int[] switchids;//开关量传感器ID数组
	private int areaId;//区域ID
	private int positionId;//位置ID
	private String tablename;
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
	public int getMark() {
		return mark;
	}
	public void setMark(int mark) {
		this.mark = mark;
	}
	public int[] getSwitchids() {
		return switchids;
	}
	public void setSwitchids(int[] switchids) {
		this.switchids = switchids;
	}
	public int[] getSensorids() {
		return sensorids;
	}
	public void setSensorids(int[] sensorids) {
		this.sensorids = sensorids;
	}
	public int getAreaId() {
		return areaId;
	}
	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}
	public int getPositionId() {
		return positionId;
	}
	public void setPositionId(int positionId) {
		this.positionId = positionId;
	}
	public String getTablename() {
		return tablename;
	}
	public void setTablename(String tablename) {
		this.tablename = tablename;
	}
	
}
