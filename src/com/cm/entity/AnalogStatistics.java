package com.cm.entity;

public class AnalogStatistics {
	private int id;
	private String ip;
	private int sensorId;//设备编号
	private String alais;//别名
	private String position;//位置
	private int sensor_type;//设备类型ID
	private String sensortype;//传感器类型
	private String limit_alarm;//报警门限
	private String limit_power;//断电门限
	private String limit_repower;//复电门限
	private double limit_alarms;
	private double limit_powers;
	private double limit_repowers;
	private double floor_alarms;
	private double floor_powers;
	private double floor_repowers;
	private String unit;//单位
	private String statistictime;//统计时间
	private double maxvalues;//最大值
	private String maxtime;//最大值时间
	private double minvalue;//最小值
	private String mintime;//最小值时间
	private double avgvalue;//平均值
	private int status;
	private String statuss;//状态（文字）
	private int debug;
	private String debugs;//调校（文字）
	private int remark;//标记1、10分钟统计 2、1H统计 3、8H统计 4、1d统计
	private double value;//值
	private String responsetime;//数据上报时间
	private int level;
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
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
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
	public String getLimit_alarm() {
		return limit_alarm;
	}
	public void setLimit_alarm(String limit_alarm) {
		this.limit_alarm = limit_alarm;
	}
	public String getLimit_power() {
		return limit_power;
	}
	public void setLimit_power(String limit_power) {
		this.limit_power = limit_power;
	}
	public String getLimit_repower() {
		return limit_repower;
	}
	public void setLimit_repower(String limit_repower) {
		this.limit_repower = limit_repower;
	}
	public double getLimit_alarms() {
		return limit_alarms;
	}
	public void setLimit_alarms(double limit_alarms) {
		this.limit_alarms = limit_alarms;
	}
	public double getLimit_powers() {
		return limit_powers;
	}
	public void setLimit_powers(double limit_powers) {
		this.limit_powers = limit_powers;
	}
	public double getLimit_repowers() {
		return limit_repowers;
	}
	public void setLimit_repowers(double limit_repowers) {
		this.limit_repowers = limit_repowers;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getStatistictime() {
		return statistictime;
	}
	public void setStatistictime(String statistictime) {
		this.statistictime = statistictime;
	}
	public double getMaxvalues() {
		return maxvalues;
	}
	public void setMaxvalues(double maxvalues) {
		this.maxvalues = maxvalues;
	}
	public String getMaxtime() {
		return maxtime;
	}
	public void setMaxtime(String maxtime) {
		this.maxtime = maxtime;
	}
	public double getMinvalue() {
		return minvalue;
	}
	public void setMinvalue(double minvalue) {
		this.minvalue = minvalue;
	}
	public String getMintime() {
		return mintime;
	}
	public void setMintime(String mintime) {
		this.mintime = mintime;
	}
	public double getAvgvalue() {
		return avgvalue;
	}
	public void setAvgvalue(double avgvalue) {
		this.avgvalue = avgvalue;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getDebug() {
		return debug;
	}
	public void setDebug(int debug) {
		this.debug = debug;
	}
	public int getRemark() {
		return remark;
	}
	public void setRemark(int remark) {
		this.remark = remark;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getSensorId() {
		return sensorId;
	}
	public void setSensorId(int sensorId) {
		this.sensorId = sensorId;
	}
	public String getStatuss() {
		return statuss;
	}
	public void setStatuss(String statuss) {
		this.statuss = statuss;
	}
	public String getDebugs() {
		return debugs;
	}
	public void setDebugs(String debugs) {
		this.debugs = debugs;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public String getResponsetime() {
		return responsetime;
	}
	public void setResponsetime(String responsetime) {
		this.responsetime = responsetime;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public double getFloor_alarms() {
		return floor_alarms;
	}
	public void setFloor_alarms(double floor_alarms) {
		this.floor_alarms = floor_alarms;
	}
	public double getFloor_powers() {
		return floor_powers;
	}
	public void setFloor_powers(double floor_powers) {
		this.floor_powers = floor_powers;
	}
	public double getFloor_repowers() {
		return floor_repowers;
	}
	public void setFloor_repowers(double floor_repowers) {
		this.floor_repowers = floor_repowers;
	}
	
	
}
