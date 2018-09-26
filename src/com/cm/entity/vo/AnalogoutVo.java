package com.cm.entity.vo;

public class AnalogoutVo {
	
	private String starttime;//报警时间
	private int alevel;//报警等级
	private double avalue;//值
	private int status;//状态 2值报警3断电4复电5通信中断
	private String desp;//状态描述
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public int getAlevel() {
		return alevel;
	}
	public void setAlevel(int alevel) {
		this.alevel = alevel;
	}
	public double getAvalue() {
		return avalue;
	}
	public void setAvalue(double avalue) {
		this.avalue = avalue;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getDesp() {
		return desp;
	}
	public void setDesp(String desp) {
		this.desp = desp;
	}
	
	
}
