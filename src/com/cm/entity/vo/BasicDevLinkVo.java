package com.cm.entity.vo;

public class BasicDevLinkVo {
	private int id;//联动id
	private int lgcDevId;
	private int actDevId;//断馈电仪id
	private String action;
	private String param;
	private String logic_exps;
	private String repower_exps;
	private int sensorId;//断馈电仪分站id
	private String ipaddr;//段馈电仪IP
	
	
	public int getSensorId() {
		return sensorId;
	}
	public void setSensorId(int sensorId) {
		this.sensorId = sensorId;
	}
	public String getIpaddr() {
		return ipaddr;
	}
	public void setIpaddr(String ipaddr) {
		this.ipaddr = ipaddr;
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
	
}
