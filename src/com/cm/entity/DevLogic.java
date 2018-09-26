package com.cm.entity;

public class DevLogic {
	private int id;
	private int groupId;
	private int lgcDevid;
	private String lgcExps;//逻辑表达式
	private String dev;//设备别名
	private String dev2;//设备2别名
	private double value;//开关量执行参数
	private String switchValueText;
	private String dsp;//设备描述
	private String lgcOperator;//逻辑运算符
	private int type;//设备类型标识
	
	public String getDev() {
		return dev;
	}
	public void setDev(String dev) {
		this.dev = dev;
	}
	public String getDev2() {
		return dev2;
	}
	public void setDev2(String dev2) {
		this.dev2 = dev2;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public String getSwitchValueText() {
		return switchValueText;
	}
	public void setSwitchValueText(String switchValueText) {
		this.switchValueText = switchValueText;
	}
	public String getDsp() {
		return dsp;
	}
	public void setDsp(String dsp) {
		this.dsp = dsp;
	}
	public String getLgcOperator() {
		return lgcOperator;
	}
	public void setLgcOperator(String lgcOperator) {
		this.lgcOperator = lgcOperator;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public int getLgcDevid() {
		return lgcDevid;
	}
	public void setLgcDevid(int lgcDevid) {
		this.lgcDevid = lgcDevid;
	}
	public String getLgcExps() {
		return lgcExps;
	}
	public void setLgcExps(String lgcExps) {
		this.lgcExps = lgcExps;
	}
	
	
}
