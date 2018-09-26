package com.cm.entity;

import java.util.ArrayList;
import java.util.List;

public class WorkerTrackRecord {
	
	private int id;
	private String card_id;//卡号
	private String name;//名字
	private String theDate;//日期
	private int wokerInArea_intoId;//进井数据ID
	private int wokerInArea_outId;//出井数据ID
	private String intoTime;//进井时间
	private String outTime;//出井时间
	private long seconds;
	private String times;//时长
	private String time;
	private int special;
	private String type;
	private int isuse;
	@SuppressWarnings("rawtypes")
	private List list = new ArrayList();
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCard_id() {
		return card_id;
	}
	public void setCard_id(String card_id) {
		this.card_id = card_id;
	}
	public int getWokerInArea_intoId() {
		return wokerInArea_intoId;
	}
	public void setWokerInArea_intoId(int wokerInArea_intoId) {
		this.wokerInArea_intoId = wokerInArea_intoId;
	}
	public int getWokerInArea_outId() {
		return wokerInArea_outId;
	}
	public void setWokerInArea_outId(int wokerInArea_outId) {
		this.wokerInArea_outId = wokerInArea_outId;
	}
	public String getIntoTime() {
		return intoTime;
	}
	public void setIntoTime(String intoTime) {
		this.intoTime = intoTime;
	}
	public String getOutTime() {
		return outTime;
	}
	public void setOutTime(String outTime) {
		this.outTime = outTime;
	}
	public String getTheDate() {
		return theDate;
	}
	public void setTheDate(String theDate) {
		this.theDate = theDate;
	}
	public String getTimes() {
		return times;
	}
	public void setTimes(String times) {
		this.times = times;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@SuppressWarnings("rawtypes")
	public List getList() {
		return list;
	}
	@SuppressWarnings("rawtypes")
	public void setList(List list) {
		this.list = list;
	}
	public long getSeconds() {
		return seconds;
	}
	public void setSeconds(long seconds) {
		this.seconds = seconds;
	}
	
	public int getSpecial() {
		return special;
	}
	public void setSpecial(int special) {
		this.special = special;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public int getIsuse() {
		return isuse;
	}
	public void setIsuse(int isuse) {
		this.isuse = isuse;
	}
	@Override
	public String toString() {
		return "WorkerTrackRecord [id=" + id + ", card_id=" + card_id
				+ ", name=" + name + ", theDate=" + theDate
				+ ", wokerInArea_intoId=" + wokerInArea_intoId
				+ ", wokerInArea_outId=" + wokerInArea_outId + ", intoTime="
				+ intoTime + ", outTime=" + outTime + ", seconds=" + seconds
				+ ", times=" + times + ", list=" + list + ", getId()="
				+ getId() + ", getCard_id()=" + getCard_id()
				+ ", getWokerInArea_intoId()=" + getWokerInArea_intoId()
				+ ", getWokerInArea_outId()=" + getWokerInArea_outId()
				+ ", getIntoTime()=" + getIntoTime() + ", getOutTime()="
				+ getOutTime() + ", getTheDate()=" + getTheDate()
				+ ", getTimes()=" + getTimes() + ", getName()=" + getName()
				+ ", getList()=" + getList() + ", getSeconds()=" + getSeconds()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
	
	
}
