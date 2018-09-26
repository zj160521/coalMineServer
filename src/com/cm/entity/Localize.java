package com.cm.entity;


public class Localize {

	private int id;
	private int rfcard_id; // 卡号
	private String name; // 员工名字
	private int depart_id; // 部门id
	private String departname; // 部门名称
	private int worktype_id;// 工种id
	private String worktypename; // 工种名称
	private int dev_id; // 读卡器id
	private int area_id; // 区域id
	private String areaname; // 区域名字
	private String starttime; // 开始时间
	private String endtime; // 离开时间或者离井时间
	private String wellduration; // 进入时长或者下井时长
	private String aname; // 菜单
	private int status; // 上下井状态
	private String addr; //读卡器地址
	private String position; //读卡器位置
	private int entry_status;//入井状态
	private int out_status;//出井状态
	private long longTimeCast;
	
	
	public long getLongTimeCast() {
		return longTimeCast;
	}

	public void setLongTimeCast(long longTimeCast) {
		this.longTimeCast = longTimeCast;
	}

	public int getEntry_status() {
		return entry_status;
	}

	public void setEntry_status(int entry_status) {
		this.entry_status = entry_status;
	}

	public int getOut_status() {
		return out_status;
	}

	public void setOut_status(int out_status) {
		this.out_status = out_status;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRfcard_id() {
		return rfcard_id;
	}

	public void setRfcard_id(int rfcard_id) {
		this.rfcard_id = rfcard_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public int getWorktype_id() {
		return worktype_id;
	}

	public void setWorktype_id(int worktype_id) {
		this.worktype_id = worktype_id;
	}

	public String getWorktypename() {
		return worktypename;
	}

	public void setWorktypename(String worktypename) {
		this.worktypename = worktypename;
	}

	public int getDev_id() {
		return dev_id;
	}

	public void setDev_id(int dev_id) {
		this.dev_id = dev_id;
	}

	public int getArea_id() {
		return area_id;
	}

	public void setArea_id(int area_id) {
		this.area_id = area_id;
	}

	public String getAreaname() {
		return areaname;
	}

	public void setAreaname(String areaname) {
		this.areaname = areaname;
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

	public String getWellduration() {
		return wellduration;
	}

	public void setWellduration(String wellduration) {
		this.wellduration = wellduration;
	}

	public String getAname() {
		return aname;
	}

	public void setAname(String aname) {
		this.aname = aname;
	}

}
