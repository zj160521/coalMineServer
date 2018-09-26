package com.cm.entity;

import java.util.List;

public class Coalmine_route  implements Comparable<Coalmine_route>{
	private int id;
	private String ip;
	private int dev_id;
	private int devid;
	private int type;
	private int rfcard_id;
	private int card;
	private int[] cards;
	private int status;
	private int battary;
	private String responsetime;
	private String response;
	private String filltime;
	private String desp;
	private String starttime;
	private String endtime;
	private double n_point;
	private double e_point;
	private int depart_id;
	private String departName;
	private String workerName;
	private int position_id;
	private String positionName;
	private List<Coalmine_route> list;
	private String cardReaderName;
	private String position;
	private int debug;
	private String areaname;
	private int default_allow;
	private int emphasis;
	private int worker_id;
	
	public int getWorker_id() {
		return worker_id;
	}
	public void setWorker_id(int worker_id) {
		this.worker_id = worker_id;
	}
	public String getAreaname() {
		return areaname;
	}
	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}
	public int getDefault_allow() {
		return default_allow;
	}
	public void setDefault_allow(int default_allow) {
		this.default_allow = default_allow;
	}
	public int getEmphasis() {
		return emphasis;
	}
	public void setEmphasis(int emphasis) {
		this.emphasis = emphasis;
	}
	public int getDebug() {
		return debug;
	}
	public void setDebug(int debug) {
		this.debug = debug;
	}
	public int getRfcard_id() {
		return rfcard_id;
	}
	public void setRfcard_id(int rfcard_id) {
		this.rfcard_id = rfcard_id;
	}
	public List<Coalmine_route> getList() {
		return list;
	}
	public void setList(List<Coalmine_route> list) {
		this.list = list;
	}
	public int getPosition_id() {
		return position_id;
	}
	public void setPosition_id(int position_id) {
		this.position_id = position_id;
	}
	public String getPositionName() {
		return positionName;
	}
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	public String getCardReaderName() {
		return cardReaderName;
	}
	public void setCardReaderName(String cardReaderName) {
		this.cardReaderName = cardReaderName;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public int getDepart_id() {
		return depart_id;
	}
	public void setDepart_id(int depart_id) {
		this.depart_id = depart_id;
	}
	public String getDepartName() {
		return departName;
	}
	public void setDepartName(String departName) {
		this.departName = departName;
	}
	public String getWorkerName() {
		return workerName;
	}
	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}
	public double getN_point() {
		return n_point;
	}
	public void setN_point(double n_point) {
		this.n_point = n_point;
	}
	public double getE_point() {
		return e_point;
	}
	public void setE_point(double e_point) {
		this.e_point = e_point;
	}
	public int[] getCards() {
		return cards;
	}
	public void setCards(int[] cards) {
		this.cards = cards;
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getDev_id() {
		return dev_id;
	}
	public void setDev_id(int dev_id) {
		this.dev_id = dev_id;
	}
	public int getDevid() {
		return devid;
	}
	public void setDevid(int devid) {
		this.devid = devid;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getCard() {
		return card;
	}
	public void setCard(int card) {
		this.card = card;
	}
	public int getBattary() {
		return battary;
	}
	public void setBattary(int battary) {
		this.battary = battary;
	}
	public String getResponsetime() {
		return responsetime;
	}
	public void setResponsetime(String responsetime) {
		this.responsetime = responsetime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public String getFilltime() {
		return filltime;
	}
	public void setFilltime(String filltime) {
		this.filltime = filltime;
	}
	public String getDesp() {
		return desp;
	}
	public void setDesp(String desp) {
		this.desp = desp;
	}
	@Override
	public int compareTo(Coalmine_route o) {
		return this.responsetime.compareTo(o.responsetime);
	}
	
}
