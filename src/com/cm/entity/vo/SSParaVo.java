package com.cm.entity.vo;

import java.util.List;

public class SSParaVo {
	private String starttime;
	private String endtime;
	private String ip;//分站ip
	private int sensorId;
	private int areaId;
	private int atype;
	private int cur_page;
	private int id;
	private int page_rows;
	private int sensor_type;
	private int sensor_position;
	private List<Integer> list;
	private int debug;

    public int getDebug() {
        return debug;
    }

    public void setDebug(int debug) {
        this.debug = debug;
    }

    public int getSensor_position() {
		return sensor_position;
	}
	public void setSensor_position(int sensor_position) {
		this.sensor_position = sensor_position;
	}
	public List<Integer> getList() {
		return list;
	}
	public void setList(List<Integer> list) {
		this.list = list;
	}
	public int getAtype() {
		return atype;
	}
	public void setAtype(int atype) {
		this.atype = atype;
	}
	public int getCur_page() {
		return cur_page;
	}
	public void setCur_page(int cur_page) {
		this.cur_page = cur_page;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPage_rows() {
		return page_rows;
	}
	public void setPage_rows(int page_rows) {
		this.page_rows = page_rows;
	}
	public int getSensor_type() {
		return sensor_type;
	}
	public void setSensor_type(int sensor_type) {
		this.sensor_type = sensor_type;
	}
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
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getSensorId() {
		return sensorId;
	}
	public void setSensorId(int sensorId) {
		this.sensorId = sensorId;
	}
	public int getAreaId() {
		return areaId;
	}
	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}

}
