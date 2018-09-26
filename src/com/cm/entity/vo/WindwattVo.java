package com.cm.entity.vo;

public class WindwattVo {
	
	private int id;
	private String	serialnumber;//编号
	private String uid;//传感器uid
	private double alarmvalue;//报警值
	private double powervalue;//断电值
	private int status;//通讯中断时联动
	private int windwattId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSerialnumber() {
		return serialnumber;
	}
	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public double getAlarmvalue() {
		return alarmvalue;
	}
	public void setAlarmvalue(double alarmvalue) {
		this.alarmvalue = alarmvalue;
	}
	public double getPowervalue() {
		return powervalue;
	}
	public void setPowervalue(double powervalue) {
		this.powervalue = powervalue;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getWindwattId() {
		return windwattId;
	}
	public void setWindwattId(int windwattId) {
		this.windwattId = windwattId;
	}
	
	
}
