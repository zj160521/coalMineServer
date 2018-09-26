package com.cm.entity.vo;

public class SensorVo {

	private int positionId;//传感器位置id
	private String position;//传感器所在位置
	private String alais;//传感器别名
	private double limit_alarm;//上限报警值
	private double limit_power;//上限断电值
	private double limit_repower;//上限复电值
	private double floor_alarm;//下限报警值
	private double floor_power;//下限断电值
	private double floor_repower;//下限复电值 
	private String areaname;//区域
	private int type;//传感器类型id
	private int devlinkId;//上限联动id
	private int floorDevlinkId;//下限联动id
	private String ip;//设备ip
	private int devid;//分站id
	private String unit;//传感器单位
	private String power_scope;//断电器的断电范围
	
	public String getPower_scope() {
		return power_scope;
	}
	public void setPower_scope(String power_scope) {
		this.power_scope = power_scope;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public int getPositionId() {
		return positionId;
	}
	public void setPositionId(int positionId) {
		this.positionId = positionId;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getDevid() {
		return devid;
	}
	public void setDevid(int devid) {
		this.devid = devid;
	}
	public int getDevlinkId() {
		return devlinkId;
	}
	public void setDevlinkId(int devlinkId) {
		this.devlinkId = devlinkId;
	}
	public int getFloorDevlinkId() {
		return floorDevlinkId;
	}
	public void setFloorDevlinkId(int floorDevlinkId) {
		this.floorDevlinkId = floorDevlinkId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public double getFloor_alarm() {
		return floor_alarm;
	}
	public void setFloor_alarm(double floor_alarm) {
		this.floor_alarm = floor_alarm;
	}
	public double getFloor_power() {
		return floor_power;
	}
	public void setFloor_power(double floor_power) {
		this.floor_power = floor_power;
	}
	public double getFloor_repower() {
		return floor_repower;
	}
	public void setFloor_repower(double floor_repower) {
		this.floor_repower = floor_repower;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getAlais() {
		return alais;
	}
	public void setAlais(String alais) {
		this.alais = alais;
	}
	public double getLimit_alarm() {
		return limit_alarm;
	}
	public void setLimit_alarm(double limit_alarm) {
		this.limit_alarm = limit_alarm;
	}
	public double getLimit_power() {
		return limit_power;
	}
	public void setLimit_power(double limit_power) {
		this.limit_power = limit_power;
	}
	public double getLimit_repower() {
		return limit_repower;
	}
	public void setLimit_repower(double limit_repower) {
		this.limit_repower = limit_repower;
	}
	public String getAreaname() {
		return areaname;
	}
	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}
	
	
}
