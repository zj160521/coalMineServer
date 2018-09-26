package com.cm.entity.vo;


/**
 * 人员失联报警
 * @author Administrator
 *
 */
public class WorkerUnconnectionVo {

	private int card_id;//卡号
	private String name;//姓名
	private String position;//最后出现的读卡器位置
	private String responsetime;//最后出现的时间
	private String areaname;//最后出现的区域
	private String during;//失联的时间
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
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getResponsetime() {
		return responsetime;
	}
	public void setResponsetime(String responsetime) {
		this.responsetime = responsetime;
	}
	public String getAreaname() {
		return areaname;
	}
	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}
	public String getDuring() {
		return during;
	}
	public void setDuring(String during) {
		this.during = during;
	}
	public int getIsuse() {
		return isuse;
	}
	public void setIsuse(int isuse) {
		this.isuse = isuse;
	}
	
	
}
