package com.cm.entity.vo;

public class DrainageRealTimeValue {
	
	private int id;
	private String name;
	private double value;
	private String unit;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	@Override
	public String toString() {
		return "DrainageRealTimeValue [id=" + id + ", name=" + name + ", value=" + value + ", unit=" + unit + "]";
	}
	

}
