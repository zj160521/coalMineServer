package com.cm.entity.vo;

import java.util.ArrayList;
import java.util.List;

public class AreaListVo<T> {
	
	private int id;
	private String areaname;
	private String label;
	private List<T> lists = new ArrayList<T>();
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAreaname() {
		return areaname;
	}
	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public List<T> getLists() {
		return lists;
	}
	public void setLists(List<T> lists) {
		this.lists = lists;
	}
	
}
