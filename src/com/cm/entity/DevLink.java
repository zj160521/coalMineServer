package com.cm.entity;

public class DevLink {
	private int id;
	private int lgcDevId;
	private int actDevId;
	private String action;
	private String param;
	private String logic_exps;
	private String repower_exps;
	private int ssid;//段馈电仪id
	private String logic_uid;
	private String logic_ip;
	private int logic_type;
	private String action_uid;
	private String action_ip;
	private int action_type;
	private String dsp;
	public int getSsid() {
		return ssid;
	}
	public void setSsid(int ssid) {
		this.ssid = ssid;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getLgcDevId() {
		return lgcDevId;
	}
	public void setLgcDevId(int lgcDevId) {
		this.lgcDevId = lgcDevId;
	}
	public int getActDevId() {
		return actDevId;
	}
	public void setActDevId(int actDevId) {
		this.actDevId = actDevId;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public String getLogic_exps() {
		return logic_exps;
	}
	public void setLogic_exps(String logic_exps) {
		this.logic_exps = logic_exps;
	}
	public String getRepower_exps() {
		return repower_exps;
	}
	public void setRepower_exps(String repower_exps) {
		this.repower_exps = repower_exps;
	}
	public String getLogic_uid() {
		return logic_uid;
	}
	public void setLogic_uid(String logic_uid) {
		this.logic_uid = logic_uid;
	}
	public String getLogic_ip() {
		return logic_ip;
	}
	public void setLogic_ip(String logic_ip) {
		this.logic_ip = logic_ip;
	}
	public int getLogic_type() {
		return logic_type;
	}
	public void setLogic_type(int logic_type) {
		this.logic_type = logic_type;
	}
	public String getAction_uid() {
		return action_uid;
	}
	public void setAction_uid(String action_uid) {
		this.action_uid = action_uid;
	}
	public String getAction_ip() {
		return action_ip;
	}
	public void setAction_ip(String action_ip) {
		this.action_ip = action_ip;
	}
	public int getAction_type() {
		return action_type;
	}
	public void setAction_type(int action_type) {
		this.action_type = action_type;
	}
	public String getDsp() {
		return dsp;
	}
	public void setDsp(String dsp) {
		this.dsp = dsp;
	}
	
}
