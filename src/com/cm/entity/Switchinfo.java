package com.cm.entity;

public class Switchinfo {
	
	private int id;
	private int sensor_id;//传感器主键id
	private String pid;//传感器通信地址
	private int sensor_type;//传感器类型id
	private String sensortype;//传感器类型
	private String position;//传感器所在位置
	private String zerostate;//零态
	private String onestate;//一态
	private int value;//值
	private String valuestate;//值状态
	private int startId;
	private int endId;
	private String starttime;//开始时间
	private String endtime;//结束时间
	private String times;//报警时长
	private int status;//状态
	private String measurescnt;//措施内容
	private int state;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSensor_id() {
		return sensor_id;
	}
	public void setSensor_id(int sensor_id) {
		this.sensor_id = sensor_id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public int getSensor_type() {
		return sensor_type;
	}
	public void setSensor_type(int sensor_type) {
		this.sensor_type = sensor_type;
	}

	public String getSensortype() {
		return sensortype;
	}
	public void setSensortype(String sensortype) {
		this.sensortype = sensortype;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getZerostate() {
		return zerostate;
	}
	public void setZerostate(String zerostate) {
		this.zerostate = zerostate;
	}
	public String getOnestate() {
		return onestate;
	}
	public void setOnestate(String onestate) {
		this.onestate = onestate;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public String getValuestate() {
		return valuestate;
	}
	public void setValuestate(String valuestate) {
		this.valuestate = valuestate;
	}
	public int getStartId() {
		return startId;
	}
	public void setStartId(int startId) {
		this.startId = startId;
	}
	public int getEndId() {
		return endId;
	}
	public void setEndId(int endId) {
		this.endId = endId;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public String getTimes() {
		return times;
	}
	public void setTimes(String times) {
		this.times = times;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMeasurescnt() {
		return measurescnt;
	}
	public void setMeasurescnt(String measurescnt) {
		this.measurescnt = measurescnt;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	
}
