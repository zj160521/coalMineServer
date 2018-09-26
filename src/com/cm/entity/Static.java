package com.cm.entity;

import java.util.ArrayList;
import java.util.List;

public class Static<T> {

	private int id;
	private int pid;
	private String k;
	private String v;
	private String path;
	private String path2;
	private String encode;
	private String label;
	private int hasfloor;
	private List<T> lists = new ArrayList<T>();

    public String getEncode() {
        return encode;
    }

    public void setEncode(String encode) {
        this.encode = encode;
    }

    public String getPath2() {
		return path2;
	}
	public void setPath2(String path2) {
		this.path2 = path2;
	}
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
	public String getK() {
		return k;
	}
	public void setK(String k) {
		this.k = k;
	}
	public String getV() {
		return v;
	}
	public void setV(String v) {
		this.v = v;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public List<T> getLists() {
		return lists;
	}
	public void setLists(List<T> lists) {
		this.lists = lists;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public int getHasfloor() {
		return hasfloor;
	}
	public void setHasfloor(int hasfloor) {
		this.hasfloor = hasfloor;
	}
	@Override
	public String toString() {
		return "Static [id=" + id + ", pid=" + pid + ", k=" + k + ", v=" + v + "]";
	}
	
}
