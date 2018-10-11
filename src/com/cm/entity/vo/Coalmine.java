package com.cm.entity.vo;

public class Coalmine implements Cloneable{
	private int id;
	private String ip;
	private int dev_id;
	private int devid;
	private int type;
	private double value;
	private int status;
	private int level;
	private String response;
	private String filltime;
	private String desp;
	private String responsetime;
	private int debug;
	private int can1;
	private int can2;
	private double battary;
	private double percent;
	private int cut1;
	private int cut2;
	private int is_cut;
	private int feedback;
	private int feedstatus;
	private int rescale;
	private String duration;
	
	@Override
	public Object clone() throws CloneNotSupportedException
	{
		Object object = super.clone();
		return object;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public int getRescale() {
		return rescale;
	}
	public void setRescale(int rescale) {
		this.rescale = rescale;
	}
	public int getIs_cut() {
		return is_cut;
	}
	public void setIs_cut(int is_cut) {
		this.is_cut = is_cut;
	}
	public int getFeedback() {
		return feedback;
	}
	public void setFeedback(int feedback) {
		this.feedback = feedback;
	}
	public int getFeedstatus() {
		return feedstatus;
	}
	public void setFeedstatus(int feedstatus) {
		this.feedstatus = feedstatus;
	}
	public double getBattary() {
		return battary;
	}
	public void setBattary(double battary) {
		this.battary = battary;
	}
	public double getPercent() {
		return percent;
	}
	public void setPercent(double percent) {
		this.percent = percent;
	}
	public int getCut1() {
		return cut1;
	}
	public void setCut1(int cut1) {
		this.cut1 = cut1;
	}
	public int getCut2() {
		return cut2;
	}
	public void setCut2(int cut2) {
		this.cut2 = cut2;
	}
	public int getCan1() {
		return can1;
	}
	public void setCan1(int can1) {
		this.can1 = can1;
	}
	public int getCan2() {
		return can2;
	}
	public void setCan2(int can2) {
		this.can2 = can2;
	}
	public int getDebug() {
		return debug;
	}
	public void setDebug(int debug) {
		this.debug = debug;
	}
	public String getResponsetime() {
		return responsetime;
	}
	public void setResponsetime(String responsetime) {
		this.responsetime = responsetime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getDev_id() {
		return dev_id;
	}
	public void setDev_id(int dev_id) {
		this.dev_id = dev_id;
	}
	public int getDevid() {
		return devid;
	}
	public void setDevid(int devid) {
		this.devid = devid;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public String getFilltime() {
		return filltime;
	}
	public void setFilltime(String filltime) {
		this.filltime = filltime;
	}
	public String getDesp() {
		return desp;
	}
	public void setDesp(String desp) {
		this.desp = desp;
	}
	
}
