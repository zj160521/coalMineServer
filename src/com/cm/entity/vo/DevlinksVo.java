package com.cm.entity.vo;

import java.util.ArrayList;
import java.util.List;

public class DevlinksVo<T> {
	
	private int id;
	private String label;
	private List<String> uids = new ArrayList<String>();
	private List<T> lists = new ArrayList<T>();
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public List<String> getUids() {
		return uids;
	}
	public void setUids(List<String> uids) {
		this.uids = uids;
	}
	public List<T> getLists() {
		return lists;
	}
	public void setLists(List<T> lists) {
		this.lists = lists;
	}
	
	
}
