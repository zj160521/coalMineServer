package com.cm.entity.vo;

import java.util.List;


public class Searchform {

	private int id;
	private int rfcard_id; // 卡号
	private int depart_id;// 部门id
	private String departname;// 部门名称
	private int worktype_id;// 工种id
	private int area_id;// 区域id
	private List<Integer> area_ids;//区域ID数组
	private String station;// 网关
	private String statuslist;// 设备状态
	private int radio;// 上下井状态：1下井 2 上井
	private int checkstatus;// 状态(员工告警页面)
	private String cardaddress;// 读卡器名称
	private int cur_page;// 当前页码
	private int page_rows;// 每页数据条数
	private int total_record;//总记录数
	private int total_pages;// 总页数
	private String starttime;// 开始时间
	private String endtime;// 结束时间
	private int status;
	private String aname;//
	private int devid;//设备ID
	private String iType;
	private int workerPlaceId;//员工工作地点ID
	private int dutyId;//职务ID
	private int special;//特殊人员
	private int emphasis;//重点区域
	private int substation;//分站
	private int type;
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getSubstation() {
		return substation;
	}

	public void setSubstation(int substation) {
		this.substation = substation;
	}

	public List<Integer> getArea_ids() {
		return area_ids;
	}

	public void setArea_ids(List<Integer> area_ids) {
		this.area_ids = area_ids;
	}

	public int getEmphasis() {
		return emphasis;
	}

	public void setEmphasis(int emphasis) {
		this.emphasis = emphasis;
	}

	public int getWorkerPlaceId() {
		return workerPlaceId;
	}

	public void setWorkerPlaceId(int workerPlaceId) {
		this.workerPlaceId = workerPlaceId;
	}

	public int getDutyId() {
		return dutyId;
	}

	public void setDutyId(int dutyId) {
		this.dutyId = dutyId;
	}

	public int getSpecial() {
		return special;
	}

	public void setSpecial(int special) {
		this.special = special;
	}

	public String getiType() {
		return iType;
	}

	public void setiType(String iType) {
		this.iType = iType;
	}

	public int getDevid() {
		return devid;
	}

	public void setDevid(int devid) {
		this.devid = devid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAname() {
		return aname;
	}

	public void setAname(String aname) {
		this.aname = aname;
	}

	public int getTotal_record() {
		return total_record;
	}

	public void setTotal_record(int total_record) {
		this.total_record = total_record;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

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

	public int getTotal_pages() {
		return total_pages;
	}

	public void setTotal_pages(int total_pages) {
		this.total_pages = total_pages;
	}

	public int getRfcard_id() {
		return rfcard_id;
	}

	public void setRfcard_id(int rfcard_id) {
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

	public int getWorktype_id() {
		return worktype_id;
	}

	public void setWorktype_id(int worktype_id) {
		this.worktype_id = worktype_id;
	}

	public int getArea_id() {
		return area_id;
	}

	public void setArea_id(int area_id) {
		this.area_id = area_id;
	}

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public String getStatuslist() {
		return statuslist;
	}

	public void setStatuslist(String statuslist) {
		this.statuslist = statuslist;
	}

	public int getRadio() {
		return radio;
	}

	public void setRadio(int radio) {
		this.radio = radio;
	}

	public int getCheckstatus() {
		return checkstatus;
	}

	public void setCheckstatus(int checkstatus) {
		this.checkstatus = checkstatus;
	}

	public String getCardaddress() {
		return cardaddress;
	}

	public void setCardaddress(String cardaddress) {
		this.cardaddress = cardaddress;
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
}
