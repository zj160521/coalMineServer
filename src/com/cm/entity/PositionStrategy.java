package com.cm.entity;

import java.util.List;

public class PositionStrategy {
	private int type_id;
	private int area_type_id;
	private String area_type;
	private String path;
	private List<PositionStrategy2> list;
	
	public int getType_id() {
		return type_id;
	}
	public void setType_id(int type_id) {
		this.type_id = type_id;
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
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public List<PositionStrategy2> getList() {
		return list;
	}
	public void setList(List<PositionStrategy2> list) {
		this.list = list;
	}
	
}
