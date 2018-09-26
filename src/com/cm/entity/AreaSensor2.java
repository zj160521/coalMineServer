package com.cm.entity;

import java.util.List;

public class AreaSensor2 {
	private int area_id;
	private String areaname;
	private int area_type_id;
	private String area_type;
	private List<Area_pos> list;
	public int getArea_id() {
		return area_id;
	}
	public void setArea_id(int area_id) {
		this.area_id = area_id;
	}
	public String getAreaname() {
		return areaname;
	}
	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}
	public int getArea_type_id() {
		return area_type_id;
	}
	public void setArea_type_id(int area_type_id) {
		this.area_type_id = area_type_id;
	}
	public String getArea_type() {
		return area_type;
	}
	public void setArea_type(String area_type) {
		this.area_type = area_type;
	}
	public List<Area_pos> getList() {
		return list;
	}
	public void setList(List<Area_pos> list) {
		this.list = list;
	}
	
}
