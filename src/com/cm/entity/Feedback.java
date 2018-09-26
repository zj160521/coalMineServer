package com.cm.entity;

public class Feedback {
	private int id;
	private String ip;
	private int devid;
	private String responsetime;
	private int cut_devid;
	private Integer is_cut;
	private Integer feedback;
	private Integer feedstatus;
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
	public int getDevid() {
		return devid;
	}
	public void setDevid(int devid) {
		this.devid = devid;
	}
	public String getResponsetime() {
		return responsetime;
	}
	public void setResponsetime(String responsetime) {
		this.responsetime = responsetime;
	}
	public int getCut_devid() {
		return cut_devid;
	}
	public void setCut_devid(int cut_devid) {
		this.cut_devid = cut_devid;
	}
	public Integer getIs_cut() {
		return is_cut;
	}
	public void setIs_cut(Integer is_cut) {
		this.is_cut = is_cut;
	}
	public Integer getFeedback() {
		return feedback;
	}
	public void setFeedback(Integer feedback) {
		this.feedback = feedback;
	}
	public Integer getFeedstatus() {
		return feedstatus;
	}
	public void setFeedstatus(Integer feedstatus) {
		this.feedstatus = feedstatus;
	}
	
	
}
