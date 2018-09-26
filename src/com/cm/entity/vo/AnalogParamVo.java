package com.cm.entity.vo;

import java.util.List;

public class AnalogParamVo {

	private int id;
	private int devid;//分站下的ID
	private int minutes;//实时显示分钟数
	private int sensor_type;//传感器类型id
	private String startTime;//开始时间
	private Double max;//最大值
	private String ip;//分站IP
	private String endTime;//结束时间
	private List<DevVo> eqp;//设备集合
	private Integer type;//1:历史曲线
	private String typeName;//模拟量开关量借口判断标志
	private double limitValue;//上限报警或断电值
	private double floorValue;//下限报警或断电值
	private String uid;//断电器设备唯一标识
	private String model;//小时或天模式判断标志
	private String day;//日期
	private Integer detailHourType;//历史小时详细信息判断标志
	private int positionId;//设备位置id
	private int cur_page;// 当前页码
	private int page_rows;// 每页数据条数
	private int total_record;//总记录数
	private int total_pages;// 总页数
	
	public int getCur_page() {
		return cur_page;
	}
	public void setCur_page(int cur_page) {
		this.cur_page = cur_page;
	}
	public int getPage_rows() {
		return page_rows;
	}
	public void setPage_rows(int page_rows) {
		this.page_rows = page_rows;
	}
	public int getTotal_record() {
		return total_record;
	}
	public void setTotal_record(int total_record) {
		this.total_record = total_record;
	}
	public int getTotal_pages() {
		return total_pages;
	}
	public void setTotal_pages(int total_pages) {
		this.total_pages = total_pages;
	}
	public int getPositionId() {
		return positionId;
	}
	public void setPositionId(int positionId) {
		this.positionId = positionId;
	}
	public Integer getDetailHourType() {
		return detailHourType;
	}
	public void setDetailHourType(Integer detailHourType) {
		this.detailHourType = detailHourType;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public double getFloorValue() {
		return floorValue;
	}
	public void setFloorValue(double floorValue) {
		this.floorValue = floorValue;
	}
	public double getLimitValue() {
		return limitValue;
	}
	public void setLimitValue(double limitValue) {
		this.limitValue = limitValue;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public List<DevVo> getEqp() {
		return eqp;
	}
	public void setEqp(List<DevVo> eqp) {
		this.eqp = eqp;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public int getDevid() {
		return devid;
	}
	public void setDevid(int devid) {
		this.devid = devid;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Double getMax() {
		return max;
	}
	public void setMax(Double max) {
		this.max = max;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMinutes() {
		return minutes;
	}
	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}
	public int getSensor_type() {
		return sensor_type;
	}
	public void setSensor_type(int sensor_type) {
		this.sensor_type = sensor_type;
	}
	
	
}
