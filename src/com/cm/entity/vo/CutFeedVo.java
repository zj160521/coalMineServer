package com.cm.entity.vo;

import java.util.List;
import java.util.Map;

public class CutFeedVo {

	private int id;
	private String alais;
	private String uid;
	private String sensorTypeName;
	private String position;
	private String detailDescription;//别名+类型名+位置描述
	private List<Map<String,String>> dataList;
	
	public String getDetailDescription() {
		return detailDescription;
	}
	public void setDetailDescription(String detailDescription) {
		this.detailDescription = detailDescription;
	}
	public List<Map<String, String>> getDataList() {
		return dataList;
	}
	public void setDataList(List<Map<String, String>> dataList) {
		this.dataList = dataList;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAlais() {
		return alais;
	}
	public void setAlais(String alais) {
		this.alais = alais;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getSensorTypeName() {
		return sensorTypeName;
	}
	public void setSensorTypeName(String sensorTypeName) {
		this.sensorTypeName = sensorTypeName;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	
	
}
