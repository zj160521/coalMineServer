package com.cm.entity.vo;



public class AreaChangeVo {
	private int id;
	private int area_id;
	private String work_day;
	private int old_max_allow;
	private int old_max_time;
	private int max_allow;
	private int max_time;
	private int is_change;
	private String filltime;
	
	
	public int getIs_change() {
		return is_change;
	}
	public void setIs_change(int is_change) {
		this.is_change = is_change;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getArea_id() {
		return area_id;
	}
	public void setArea_id(int area_id) {
		this.area_id = area_id;
	}
	public String getWork_day() {
		return work_day;
	}
	public void setWork_day(String work_day) {
		this.work_day = work_day;
	}
	public int getOld_max_allow() {
		return old_max_allow;
	}
	public void setOld_max_allow(int old_max_allow) {
		this.old_max_allow = old_max_allow;
	}
	public int getOld_max_time() {
		return old_max_time;
	}
	public void setOld_max_time(int old_max_time) {
		this.old_max_time = old_max_time;
	}
	public int getMax_allow() {
		return max_allow;
	}
	public void setMax_allow(int max_allow) {
		this.max_allow = max_allow;
	}
	public int getMax_time() {
		return max_time;
	}
	public void setMax_time(int max_time) {
		this.max_time = max_time;
	}
	public String getFilltime() {
		return filltime;
	}
	public void setFilltime(String filltime) {
		this.filltime = filltime;
	}
	
	
}
