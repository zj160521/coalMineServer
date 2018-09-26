package com.cm.entity;



public class Callinfo {
	
	private int id;
	private int calltype;//呼叫类型
	private int callrange;//呼叫范围:1.定卡呼叫，2.多卡呼叫，3.区域呼叫，4.全矿呼叫
	private String callcard;//呼叫卡号
	private String callthat;//呼叫说明
	private String creattime;//开始时间
	private String creatname;//创建人
	private String endtime;//结束时间
	private int sle;//查询编号 0表示今天依次类推
	private int calltime; //呼叫时长
	private int pagenum;//分页码
	private int pagedate;//每页数据条数
	private String ip;//分站ip
	private int devid;//读卡器id
	private int card1;
	private int card2;
	private int card3;
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getDevid() {
		return devid;
	}
	public void setDevid(int devid) {
		this.devid = devid;
	}
	public int getCard1() {
		return card1;
	}
	public void setCard1(int card1) {
		this.card1 = card1;
	}
	public int getCard2() {
		return card2;
	}
	public void setCard2(int card2) {
		this.card2 = card2;
	}
	public int getCard3() {
		return card3;
	}
	public void setCard3(int card3) {
		this.card3 = card3;
	}
	public int getPagenum() {
		return pagenum;
	}
	public void setPagenum(int pagenum) {
		this.pagenum = pagenum;
	}
	public int getPagedate() {
		return pagedate;
	}
	public void setPagedate(int pagedate) {
		this.pagedate = pagedate;
	}

	private int[] ids;
	
	public int[] getIds() {
		return ids;
	}
	public void setIds(int[] ids) {
		this.ids = ids;
	}
	public int getSle() {
		return sle;
	}
	public void setSle(int sle) {
		this.sle = sle;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCalltype() {
		return calltype;
	}
	public void setCalltype(int calltype) {
		this.calltype = calltype;
	}
	public int getCallrange() {
		return callrange;
	}
	public void setCallrange(int callrange) {
		this.callrange = callrange;
	}
	public int getCalltime() {
		return calltime;
	}
	public void setCalltime(int calltime) {
		this.calltime = calltime;
	}
	public String getCallcard() {
		return callcard;
	}
	public void setCallcard(String callcard) {
		this.callcard = callcard;
	}
	public String getCallthat() {
		return callthat;
	}
	public void setCallthat(String callthat) {
		this.callthat = callthat;
	}
	public String getCreattime() {
		return creattime;
	}
	public void setCreattime(String creattime) {
		this.creattime = creattime;
	}
	public String getCreatname() {
		return creatname;
	}
	public void setCreatname(String creatname) {
		this.creatname = creatname;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	
}
