package com.cm.entity;

import java.util.ArrayList;
import java.util.List;

import com.cm.entity.vo.AnaloginfoQuery;


public class AnalogStatement {
	
	private int id;
	private String pid;//测点号
	private String alais;//别名
	private int sensor_id;//设备主键
	private int sensor_type;//设备类型编号
	private String type;//设备类型
	private String position;//设备位置
	private String unit;//单位
	private String powerposition;//断电区域
	private String limit_alarm;//报警门限
	private String limit_power;//断电门限
	private String limit_repower;//复电门限
	private int alerts;//值报警次数
	private int powerfres;//断电报警次数
	private int feeabnum;//复电报警次数
	private long times;
	private String alertTimes;//值报警累计时长
	private String powerfreTimes;//断电报警累计时长
	private String feeabnumTimes;//复电报警累计时长
	private double minvalue;//最小值
	private double maxvalues;//最大值
	private double avgvalue;//平均值
	private double acgcnt;
	private String maxtime;//最大值时间
	private List<AnaloginfoQuery> list = new ArrayList<AnaloginfoQuery>();
	private int itype;
	private int feedId;
	private String[] first;//第一次
	private String[] second;//第二次
	public double getMinvalue() {
		return minvalue;
	}
	public void setMinvalue(double minvalue) {
		this.minvalue = minvalue;
	}
	public double getMaxvalues() {
		return maxvalues;
	}
	public void setMaxvalues(double maxvalues) {
		this.maxvalues = maxvalues;
	}
	public double getAvgvalue() {
		return avgvalue;
	}
	public void setAvgvalue(double avgvalue) {
		this.avgvalue = avgvalue;
	}
	public String getPowerfreTimes() {
		return powerfreTimes;
	}
	public void setPowerfreTimes(String powerfreTimes) {
		this.powerfreTimes = powerfreTimes;
	}
	public String getFeeabnumTimes() {
		return feeabnumTimes;
	}
	public void setFeeabnumTimes(String feeabnumTimes) {
		this.feeabnumTimes = feeabnumTimes;
	}
	public String getAlertTimes() {
		return alertTimes;
	}
	public void setAlertTimes(String alertTimes) {
		this.alertTimes = alertTimes;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
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
	public int getAlerts() {
		return alerts;
	}
	public void setAlerts(int alerts) {
		this.alerts = alerts;
	}
	public int getPowerfres() {
		return powerfres;
	}
	public void setPowerfres(int powerfres) {
		this.powerfres = powerfres;
	}
	public int getFeeabnum() {
		return feeabnum;
	}
	public void setFeeabnum(int feeabnum) {
		this.feeabnum = feeabnum;
	}
	public int getSensor_id() {
		return sensor_id;
	}
	public void setSensor_id(int sensor_id) {
		this.sensor_id = sensor_id;
	}
	public int getSensor_type() {
		return sensor_type;
	}
	public void setSensor_type(int sensor_type) {
		this.sensor_type = sensor_type;
	}
	public String getMaxtime() {
		return maxtime;
	}
	public void setMaxtime(String maxtime) {
		this.maxtime = maxtime;
	}
	public List<AnaloginfoQuery> getList() {
		return list;
	}
	public void setList(List<AnaloginfoQuery> list) {
		this.list = list;
	}
	public String getAlais() {
		return alais;
	}
	public void setAlais(String alais) {
		this.alais = alais;
	}
	public int getItype() {
		return itype;
	}
	public void setItype(int itype) {
		this.itype = itype;
	}
	public String getPowerposition() {
		return powerposition;
	}
	public void setPowerposition(String powerposition) {
		this.powerposition = powerposition;
	}
	public int getFeedId() {
		return feedId;
	}
	public void setFeedId(int feedId) {
		this.feedId = feedId;
	}
	public long getTimes() {
		return times;
	}
	public void setTimes(long times) {
		this.times = times;
	}
	public double getAcgcnt() {
		return acgcnt;
	}
	public void setAcgcnt(double acgcnt) {
		this.acgcnt = acgcnt;
	}
	public String[] getFirst() {
		return first;
	}
	public void setFirst(String[] first) {
		this.first = first;
	}
	public String[] getSecond() {
		return second;
	}
	public void setSecond(String[] second) {
		this.second = second;
	}
	@Override
	public String toString() {
		return "AnalogStatement [id=" + id + ", pid=" + pid + ", alais="
				+ alais + ", sensor_id=" + sensor_id + ", sensor_type="
				+ sensor_type + ", type=" + type + ", position=" + position
				+ ", unit=" + unit + ", powerposition=" + powerposition
				+ ", limit_alarm=" + limit_alarm + ", limit_power="
				+ limit_power + ", limit_repower=" + limit_repower
				+ ", alerts=" + alerts + ", powerfres=" + powerfres
				+ ", feeabnum=" + feeabnum + ", times=" + times
				+ ", alertTimes=" + alertTimes + ", powerfreTimes="
				+ powerfreTimes + ", feeabnumTimes=" + feeabnumTimes
				+ ", minvalue=" + minvalue + ", maxvalues=" + maxvalues
				+ ", avgvalue=" + avgvalue + ", acgcnt=" + acgcnt
				+ ", maxtime=" + maxtime + ", list=" + list + ", itype="
				+ itype + ", feedId=" + feedId + "]";
	}
	
}
