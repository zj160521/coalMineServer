package com.cm.entity;

public class License {
	private int id;
	private String ip;
	private int status;
	private String license;
	private String day;
	private License[] licenses;
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public License[] getLicenses() {
		return licenses;
	}
	public void setLicenses(License[] licenses) {
		this.licenses = licenses;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLicense() {
		return license;
	}
	public void setLicense(String license) {
		this.license = license;
	}
	
}
