package com.cm.entity.vo;


/**
 * 门禁异常报警
 * @author Administrator
 *
 */
public class WorkerExitWarnVo {

	private int card_id;//卡号
	private String name;//姓名
	private String entranceGuardNum;//门禁卡号
	private String position;//位置
	private String responsetime;//时间
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
	public String getEntranceGuardNum() {
		return entranceGuardNum;
	}
	public void setEntranceGuardNum(String entranceGuardNum) {
		this.entranceGuardNum = entranceGuardNum;
	}
	public int getIsuse() {
		return isuse;
	}
	public void setIsuse(int isuse) {
		this.isuse = isuse;
	}
	
	
}
