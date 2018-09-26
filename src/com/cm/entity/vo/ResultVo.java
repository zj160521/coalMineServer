package com.cm.entity.vo;

import java.util.List;

public class ResultVo {

	private double max = 0;
	private double min = 0;
	private double avg = 0;
	private String position;//传感器所在位置
	private String alais;//传感器别名
	private double limit_warning;//报警值
	private double limit_power;//断电值
	private double limit_repower;//复电值
	private String areaname;//区域
	private List<AnaloghisVo> data;
	
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getAlais() {
		return alais;
	}
	public void setAlais(String alais) {
		this.alais = alais;
	}
	public double getLimit_warning() {
		return limit_warning;
	}
	public void setLimit_warning(double limit_warning) {
		this.limit_warning = limit_warning;
	}
	public double getLimit_power() {
		return limit_power;
	}
	public void setLimit_power(double limit_power) {
		this.limit_power = limit_power;
	}
	public double getLimit_repower() {
		return limit_repower;
	}
	public void setLimit_repower(double limit_repower) {
		this.limit_repower = limit_repower;
	}
	public String getAreaname() {
		return areaname;
	}
	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}
	public double getMax() {
		return max;
	}
	public void setMax(double max) {
		this.max = max;
	}
	public double getMin() {
		return min;
	}
	public void setMin(double min) {
		this.min = min;
	}
	public double getAvg() {
		return avg;
	}
	public void setAvg(double avg) {
		this.avg = avg;
	}
	public List<AnaloghisVo> getData() {
		return data;
	}
	public void setData(List<AnaloghisVo> resultList) {
		this.data = resultList;
	}
	
}
