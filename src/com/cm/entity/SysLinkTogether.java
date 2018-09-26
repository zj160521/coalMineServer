package com.cm.entity;

import java.util.List;

public class SysLinkTogether {
	private int id;
	private int ltid;//联动id
	private String ltname;//联动名称
	private String ipaddr;//分站地址
	private int sensor_id;//设备地址
	private int prestep;//步骤
	private String logic1;//逻辑1
	private String logic2;//逻辑2
	private double value;//值
	private int result_action;//动作或者发生条件
	private String actionname;
	private int isuse;
	private List<Sensor> sensors;
	private List<SysLinkTogether> list;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getLtid() {
		return ltid;
	}
	public void setLtid(int ltid) {
		this.ltid = ltid;
	}
	public String getLtname() {
		return ltname;
	}
	public void setLtname(String ltname) {
		this.ltname = ltname;
	}
	public String getIpaddr() {
		return ipaddr;
	}
	public void setIpaddr(String ipaddr) {
		this.ipaddr = ipaddr;
	}
	public int getSensor_id() {
		return sensor_id;
	}
	public void setSensor_id(int sensor_id) {
		this.sensor_id = sensor_id;
	}
	public int getPrestep() {
		return prestep;
	}
	public void setPrestep(int prestep) {
		this.prestep = prestep;
	}
	public String getLogic1() {
		return logic1;
	}
	public void setLogic1(String logic1) {
		this.logic1 = logic1;
	}
	public String getLogic2() {
		return logic2;
	}
	public void setLogic2(String logic2) {
		this.logic2 = logic2;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public int getResult_action() {
		return result_action;
	}
	public void setResult_action(int result_action) {
		this.result_action = result_action;
	}
	public String getActionname() {
		return actionname;
	}
	public void setActionname(String actionname) {
		this.actionname = actionname;
	}
	public int getIsuse() {
		return isuse;
	}
	public void setIsuse(int isuse) {
		this.isuse = isuse;
	}
	public List<Sensor> getSensors() {
		return sensors;
	}
	public void setSensors(List<Sensor> sensors) {
		this.sensors = sensors;
	}
	public List<SysLinkTogether> getList() {
		return list;
	}
	public void setList(List<SysLinkTogether> list) {
		this.list = list;
	}
	
	
}
