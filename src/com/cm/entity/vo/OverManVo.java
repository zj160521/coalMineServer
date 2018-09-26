package com.cm.entity.vo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.cm.entity.Person;

public class OverManVo {
	private int areaId;//区域id
	private String areaName;//区域名
	private int personNum;//区域总人数
	private int maxAllow;//区域限制人数
	private int defaultAllow;
	private int emphasis;
	private String filltime;//报警时间
	private String responsetime;
	private String cards;
	private String[] cardss;
	private List<Person> list = new ArrayList<Person>();//人员名单
	private String endtime;
	private String times;//时长
	private int overNum;//超员人数
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public int getAreaId() {
		return areaId;
	}
	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}
	public int getPersonNum() {
		return personNum;
	}
	public void setPersonNum(int personNum) {
		this.personNum = personNum;
	}
	public int getMaxAllow() {
		return maxAllow;
	}
	public void setMaxAllow(int maxAllow) {
		this.maxAllow = maxAllow;
	}
	public int getDefaultAllow() {
		return defaultAllow;
	}
	public void setDefaultAllow(int defaultAllow) {
		this.defaultAllow = defaultAllow;
	}
	public int getEmphasis() {
		return emphasis;
	}
	public void setEmphasis(int emphasis) {
		this.emphasis = emphasis;
	}
	public String getFilltime() {
		return filltime;
	}
	public void setFilltime(String filltime) {
		this.filltime = filltime;
	}
	public String getCards() {
		return cards;
	}
	public void setCards(String cards) {
		this.cards = cards;
	}

	public List<Person> getList() {
		return list;
	}
	public void setList(List<Person> list) {
		this.list = list;
	}
	public String getTimes() {
		return times;
	}
	public void setTimes(String times) {
		this.times = times;
	}
	public String[] getCardss() {
		return cardss;
	}
	public void setCardss(String[] cardss) {
		this.cardss = cardss;
	}
	public String getResponsetime() {
		return responsetime;
	}
	public void setResponsetime(String responsetime) {
		this.responsetime = responsetime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	
	public int getOverNum() {
		return overNum;
	}
	public void setOverNum(int overNum) {
		this.overNum = overNum;
	}
	@Override
	public String toString() {
		return "OverManVo [areaId=" + areaId + ", areaName=" + areaName
				+ ", personNum=" + personNum + ", maxAllow=" + maxAllow
				+ ", defaultAllow=" + defaultAllow + ", emphasis=" + emphasis
				+ ", filltime=" + filltime + ", responsetime=" + responsetime
				+ ", cards=" + cards + ", cardss=" + Arrays.toString(cardss)
				+ ", list=" + list + "]";
	}
	
	
}
