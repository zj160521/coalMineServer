package com.cm.entity;

import java.util.List;

public class Area_pos {
	private int area_pos_id;
	private String attrib_name;
	private int pos_type_id;
	private String pos_type;
	private List<Area_sen> list;
	
	public List<Area_sen> getList() {
		return list;
	}
	public void setList(List<Area_sen> list) {
		this.list = list;
	}
	public int getArea_pos_id() {
		return area_pos_id;
	}
	public void setArea_pos_id(int area_pos_id) {
		this.area_pos_id = area_pos_id;
	}
	public String getAttrib_name() {
		return attrib_name;
	}
	public void setAttrib_name(String attrib_name) {
		this.attrib_name = attrib_name;
	}
	public int getPos_type_id() {
		return pos_type_id;
	}
	public void setPos_type_id(int pos_type_id) {
		this.pos_type_id = pos_type_id;
	}
	public String getPos_type() {
		return pos_type;
	}
	public void setPos_type(String pos_type) {
		this.pos_type = pos_type;
	}
	
}
