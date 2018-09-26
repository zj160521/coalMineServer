package com.cm.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Drainage<T> {

	private Integer id;
	private Integer pid;
	private String type;//参数名称
	private String unit;//单位
	private String remark;//备注
	private int typeid;//传感器类型id
	private String typename;//传感器类型名称
    private int attribute;
    private List<Map<String, String>> titles;

    public List<Map<String, String>> getTitles() {
        return titles;
    }

    public void setTitles(List<Map<String, String>> titles) {
        this.titles = titles;
    }

    @SuppressWarnings("rawtypes")
	private List<Drainage> lists = new ArrayList<Drainage>();
	private List<Sensor> sensors = new ArrayList<Sensor>();
	private List<SwitchSensor> switches = new ArrayList<SwitchSensor>();
	private List<T> list = new ArrayList<T>();
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	@SuppressWarnings("rawtypes")
	public List<Drainage> getLists() {
		return lists;
	}
	@SuppressWarnings("rawtypes")
	public void setLists(List<Drainage> lists) {
		this.lists = lists;
	}
	public List<Sensor> getSensors() {
		return sensors;
	}
	public void setSensors(List<Sensor> sensors) {
		this.sensors = sensors;
	}
	public List<SwitchSensor> getSwitches() {
		return switches;
	}
	public void setSwitches(List<SwitchSensor> switches) {
		this.switches = switches;
	}
	public int getTypeid() {
		return typeid;
	}
	public void setTypeid(int typeid) {
		this.typeid = typeid;
	}
	public String getTypename() {
		return typename;
	}
	public void setTypename(String typename) {
		this.typename = typename;
	}

    public int getAttribute() {
        return attribute;
    }

    public void setAttribute(int attribute) {
        this.attribute = attribute;
    }
}
