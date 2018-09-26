package com.cm.entity;


import java.util.ArrayList;
import java.util.List;

public class Department {
	
	private int id;
	private int pid;//上级部门ID
	private String name;//部门名称
	private List<Department> list = new ArrayList<Department>();
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Department> getList() {
		return list;
	}
	public void setList(List<Department> list) {
		this.list = list;
	}
	@Override
	public String toString() {
		return "Department [id=" + id + ", pid=" + pid + ", name=" + name
				+ ", list=" + list + "]";
	}
	
	
}
