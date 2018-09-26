package com.cm.entity.vo;


/**
 * 区域超时、井下超时报警
 * @author Administrator
 *
 */
public class OvertimeAlarmVo {
	
	private int card_id;//卡号
	private String name;//姓名
	private int area_id;//区域id
	private String areaname;//区域
	private String inarea_time;//进入时间
	private String alarm_time;//报警时间
	private String max_time;//区域最大允许时长
	private String stay_time;//进入区域时长
	private String timeout_time;//超时时长
	private String endtime;//结束时间
	private int isuse;
	public int getCard_id() {
		return card_id;
	}
	public void setCard_id(int card_id) {
		this.card_id = card_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAreaname() {
		return areaname;
	}
	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}
	public String getInarea_time() {
		return inarea_time;
	}
	public void setInarea_time(String inarea_time) {
		this.inarea_time = inarea_time;
	}
	public String getAlarm_time() {
		return alarm_time;
	}
	public void setAlarm_time(String alarm_time) {
		this.alarm_time = alarm_time;
	}
	public String getMax_time() {
		return max_time;
	}
	public void setMax_time(String max_time) {
		this.max_time = max_time;
	}
	public String getStay_time() {
		return stay_time;
	}
	public void setStay_time(String stay_time) {
		this.stay_time = stay_time;
	}
	public String getTimeout_time() {
		return timeout_time;
	}
	public void setTimeout_time(String timeout_time) {
		this.timeout_time = timeout_time;
	}
	public int getIsuse() {
		return isuse;
	}
	public void setIsuse(int isuse) {
		this.isuse = isuse;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public int getArea_id() {
		return area_id;
	}
	public void setArea_id(int area_id) {
		this.area_id = area_id;
	}
	
}
