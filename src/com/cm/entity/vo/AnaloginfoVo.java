package com.cm.entity.vo;

import java.util.ArrayList;
import java.util.List;

public class AnaloginfoVo {
	
	private int sensor_id;
	private String pid;
	private String alerts_rows;
	private double maxvalue;
	private String maxtime;
	private List<AnaloginfoQuery> list = new ArrayList<AnaloginfoQuery>();
	public int getSensor_id() {
		return sensor_id;
	}
	public void setSensor_id(int sensor_id) {
		this.sensor_id = sensor_id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getAlerts_rows() {
		return alerts_rows;
	}
	public void setAlerts_rows(String alerts_rows) {
		this.alerts_rows = alerts_rows;
	}
	public double getMaxvalue() {
		return maxvalue;
	}
	public void setMaxvalue(double maxvalue) {
		this.maxvalue = maxvalue;
	}
	public String getMaxtime() {
		return maxtime;
	}
	public void setMaxtime(String maxtime) {
		this.maxtime = maxtime;
	}
	public List<AnaloginfoQuery> getList() {
		return list;
	}
	public void setList(List<AnaloginfoQuery> list) {
		this.list = list;
	}
	
	
}
