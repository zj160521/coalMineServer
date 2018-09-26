package com.cm.entity;

import org.apache.ibatis.type.Alias;

@Alias("config")
public class Config {

	private int id;
	private String k;
	private String v;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	
}
