package com.cm.entity.vo;


public class HistoryData {
	private String starttime;
	private String ipaddr;
	private double avalue;
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getIpaddr() {
		return ipaddr;
	}
	public void setIpaddr(String ipaddr) {
		this.ipaddr = ipaddr;
	}
	public double getAvalue() {
		return avalue;
	}
	public void setAvalue(double avalue) {
		this.avalue = avalue;
	}
	@Override
	public String toString() {
		return "HistoryData [starttime=" + starttime + ", ipaddr=" + ipaddr
				+ ", avalue=" + avalue + "]";
	}
	
	
	
}
