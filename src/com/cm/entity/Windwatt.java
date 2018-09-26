package com.cm.entity;

import java.util.ArrayList;
import java.util.List;

import com.cm.entity.vo.WindwattVo;


public class Windwatt<T> {
	
	
	private int id;
	private int areaId;//区域ID
	private String name;//联动名
	private String alarm;//报警名
	private List<WindwattVo> list = new ArrayList<WindwattVo>();//传感器数据集合
	private List<T> lists = new ArrayList<T>();
	private String label;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAreaId() {
		return areaId;
	}
	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAlarm() {
		return alarm;
	}
	public void setAlarm(String alarm) {
		this.alarm = alarm;
	}
	public List<WindwattVo> getList() {
		return list;
	}
	public void setList(List<WindwattVo> list) {
		this.list = list;
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
	
	
}
