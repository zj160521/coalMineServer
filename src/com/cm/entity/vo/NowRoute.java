package com.cm.entity.vo;

public class NowRoute {
	private String num;//工号
	private String name;//姓名
	private String rfcard_id;//卡号
	private int depart_id;//部门
	private String departname;
	private String areaid;//区域id
	private String areaname;//区域名称
	private String devid;//读卡器id
	private String devname;//读卡器地址
	private String reachtime;//到达时间
	private String intime;//入井时间
	private String staytime;//入井时长
	private int emphasis;
	private String out_time;
	private int default_allow;
	private int substation_id;
	private String doorStatus;
	private String status;
	private String battary;
	public String getBattary() {
		return battary;
	}
	public void setBattary(String battary) {
		this.battary = battary;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDoorStatus() {
		return doorStatus;
	}
	public void setDoorStatus(String doorStatus) {
		this.doorStatus = doorStatus;
	}
	public int getSubstation_id() {
		return substation_id;
	}
	public void setSubstation_id(int substation_id) {
		this.substation_id = substation_id;
	}
	public int getDefault_allow() {
		return default_allow;
	}
	public void setDefault_allow(int default_allow) {
		this.default_allow = default_allow;
	}
	public String getOut_time() {
		return out_time;
	}
	public void setOut_time(String out_time) {
		this.out_time = out_time;
	}
	public int getEmphasis() {
		return emphasis;
	}
	public void setEmphasis(int emphasis) {
		this.emphasis = emphasis;
	}
	public String getDevname() {
		return devname;
	}
	public void setDevname(String devname) {
		this.devname = devname;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRfcard_id() {
		return rfcard_id;
	}
	public void setRfcard_id(String rfcard_id) {
		this.rfcard_id = rfcard_id;
	}
	public int getDepart_id() {
		return depart_id;
	}
	public void setDepart_id(int depart_id) {
		this.depart_id = depart_id;
	}
	public String getDepartname() {
		return departname;
	}
	public void setDepartname(String departname) {
		this.departname = departname;
	}
	
	public String getAreaid() {
		return areaid;
	}
	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}
	public String getAreaname() {
		return areaname;
	}
	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}
	public String getDevid() {
		return devid;
	}
	public void setDevid(String devid) {
		this.devid = devid;
	}
	public String getReachtime() {
		return reachtime;
	}
	public void setReachtime(String reachtime) {
		this.reachtime = reachtime;
	}
	public String getIntime() {
		return intime;
	}
	public void setIntime(String intime) {
		this.intime = intime;
	}
	public String getStaytime() {
		return staytime;
	}
	public void setStaytime(String staytime) {
		this.staytime = staytime;
	}
	
}
