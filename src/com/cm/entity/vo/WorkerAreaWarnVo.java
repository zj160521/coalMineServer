package com.cm.entity.vo;


/**
 * 限制区域报警
 * @author Administrator
 *
 */
public class WorkerAreaWarnVo {
	
	private int card_id;//卡号
	private String name;//姓名
	private String position;//读卡器位置
	private String areaname;//区域名
	private String responsetime;//时间
	private String default_allow;//是否是限制区域
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
	public String getAreaname() {
		return areaname;
	}
	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}
	public String getResponsetime() {
		return responsetime;
	}
	public void setResponsetime(String responsetime) {
		this.responsetime = responsetime;
	}
	public String getDefault_allow() {
		return default_allow;
	}
	public void setDefault_allow(String default_allow) {
		this.default_allow = default_allow;
	}
	public int getIsuse() {
		return isuse;
	}
	public void setIsuse(int isuse) {
		this.isuse = isuse;
	}
	
	
	
}
