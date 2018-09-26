package com.cm.entity;

import java.util.ArrayList;
import java.util.List;


public class Equipment<T> {
	
	private int id;
	private String label;
	private List<T> children = new ArrayList<T>();
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
	public List<T> getChildren() {
		return children;
	}
	public void setChildren(List<T> children) {
		this.children = children;
	}
	public List<T> getLists() {
		return lists;
	}
	public void setLists(List<T> lists) {
		this.lists = lists;
	}
	
	
}
