package com.cm.entity.vo;

import java.util.Arrays;

public class NameTime {
	private int id;
	private String name;
	private String card_id;
	private String uid;
	private String starttime;
	private String endtime;
	private int[] ids;
	private int cur_page;//页码
	private int page_rows;//每页数据条数
	private int total_rows;//数据总条数
	private int start_row;
	private int atype;
	private int itype;
	private int sensor_type;//传感器类型id
	private String ip;//分站ip
	private String remark;
	private int isDrainage;
	private int pid;
	private int pid2;
	private int sensor_position;//位置
	private int mins;//分钟数
	private String day;//日期
	private int debug;//debug值
	private String classtime;
	private String tableName;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public int getTotal_rows() {
		return total_rows;
	}
	public void setTotal_rows(int total_rows) {
		this.total_rows = total_rows;
	}
	public int[] getIds() {
		return ids;
	}
	public void setIds(int[] ids) {
		this.ids = ids;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public int getAtype() {
		return atype;
	}
	public void setAtype(int atype) {
		this.atype = atype;
	}
	public int getSensor_type() {
		return sensor_type;
	}
	public void setSensor_type(int sensor_type) {
		this.sensor_type = sensor_type;
	}
	public int getStart_row() {
		return start_row;
	}
	public void setStart_row(int start_row) {
		this.start_row = start_row;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getCard_id() {
		return card_id;
	}
	public void setCard_id(String card_id) {
		this.card_id = card_id;
	}
	public int getItype() {
		return itype;
	}
	public void setItype(int itype) {
		this.itype = itype;
	}
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	
	public int getIsDrainage() {
		return isDrainage;
	}
	public void setIsDrainage(int isDrainage) {
		this.isDrainage = isDrainage;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	
	public int getPid2() {
		return pid2;
	}
	public void setPid2(int pid2) {
		this.pid2 = pid2;
	}
	public int getSensor_position() {
		return sensor_position;
	}
	public void setSensor_position(int sensor_position) {
		this.sensor_position = sensor_position;
	}
	
	public int getMins() {
		return mins;
	}
	public void setMins(int mins) {
		this.mins = mins;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public int getDebug() {
		return debug;
	}
	public void setDebug(int debug) {
		this.debug = debug;
	}
	
	public String getClasstime() {
		return classtime;
	}
	public void setClasstime(String classtime) {
		this.classtime = classtime;
	}
	@Override
	public String toString() {
		return "NameTime [id=" + id + ", name=" + name + ", card_id=" + card_id
				+ ", starttime=" + starttime + ", endtime=" + endtime
				+ ", ids=" + Arrays.toString(ids) + ", cur_page=" + cur_page
				+ ", page_rows=" + page_rows + ", total_rows=" + total_rows
				+ ", start_row=" + start_row + ", atype=" + atype + ", itype="
				+ itype + ", sensor_type=" + sensor_type + ", ip=" + ip + "]";
	}

}
