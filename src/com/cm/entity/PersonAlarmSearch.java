package com.cm.entity;

public class PersonAlarmSearch {
	
	private int card_id;//卡号
	private String name;//姓名
	private String starttime;//开始时间
	private String endtime;//截止时间
	private int type;//标识1：限制区域报警、2：门禁异常报警、3：人员失联报警、4：区域超时、井下超时报警、5： 区域超员、井下超员报警
	private int cardreader_id;//读卡器id
	private int area_id;//区域ID
	private int cur_page;//页码
	private int page_rows;//每页数据条数
	private int total_rows;//数据总条数
	private int start_row;
	
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getCardreader_id() {
		return cardreader_id;
	}
	public void setCardreader_id(int cardreader_id) {
		this.cardreader_id = cardreader_id;
	}
	public int getArea_id() {
		return area_id;
	}
	public void setArea_id(int area_id) {
		this.area_id = area_id;
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
	public int getStart_row() {
		return start_row;
	}
	public void setStart_row(int start_row) {
		this.start_row = start_row;
	}
	@Override
	public String toString() {
		return "PersonAlarmSearch [card_id=" + card_id + ", name=" + name
				+ ", starttime=" + starttime + ", endtime=" + endtime
				+ ", type=" + type + ", cardreader_id=" + cardreader_id
				+ ", area_id=" + area_id + ", cur_page=" + cur_page
				+ ", page_rows=" + page_rows + ", total_rows=" + total_rows
				+ ", start_row=" + start_row + "]";
	}
	
	
}
