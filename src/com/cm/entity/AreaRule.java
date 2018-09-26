package com.cm.entity;

public class AreaRule {

	private int id;
	private int areaId;
	private String areaname;
	private String ruleDev;//规则设备名
	private String ruleDevId;//设备别名
	private int dev_type;//设备分站id
	private String dev_name;//设备名
	private String simpleType;//设备简称
	private String alais;//别名
	private String position;//设备位置
	private String ip;//分站ip
	private int dev_id;//设备分站id
	private int default_allow;//限制区域
	private int emphasis;//重点区域
	
	public int getDefault_allow() {
		return default_allow;
	}
	public void setDefault_allow(int default_allow) {
		this.default_allow = default_allow;
	}
	public int getEmphasis() {
		return emphasis;
	}
	public void setEmphasis(int emphasis) {
		this.emphasis = emphasis;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getDev_id() {
		return dev_id;
	}
	public void setDev_id(int dev_id) {
		this.dev_id = dev_id;
	}
	public String getSimpleType() {
		return simpleType;
	}
	public void setSimpleType(String simpleType) {
		this.simpleType = simpleType;
	}
	public String getAlais() {
		return alais;
	}
	public void setAlais(String alais) {
		this.alais = alais;
	}
	public String getDev_name() {
		return dev_name;
	}
	public void setDev_name(String dev_name) {
		this.dev_name = dev_name;
	}
	public int getDev_type() {
		return dev_type;
	}
	public void setDev_type(int dev_type) {
		this.dev_type = dev_type;
	}
	public String getAreaname() {
		return areaname;
	}
	public void setAreaname(String areaname) {
		this.areaname = areaname;
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
	public String getRuleDev() {
		return ruleDev;
	}
	public void setRuleDev(String ruleDev) {
		this.ruleDev = ruleDev;
	}
	public String getRuleDevId() {
		return ruleDevId;
	}
	public void setRuleDevId(String ruleDevId) {
		this.ruleDevId = ruleDevId;
	}
	
}
