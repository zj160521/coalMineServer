package com.cm.entity;


public class NVR {

	private Integer id;
	private Integer port;//端口号
	private String ip;//IP地址
	private String username;//账号
	private String password;//密码
	private int isnvr;//是否为NVR 1：是 2：否
	private int positionId;//位置ID
	private String position;//位置
	private Integer accnum;//通道号
	private String videoname;//设备名字
	private double n_point;
	private double e_point;
	private int logintype;//登录方式 1：通过NVR 2：通过摄像机
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getIsnvr() {
		return isnvr;
	}
	public void setIsnvr(int isnvr) {
		this.isnvr = isnvr;
	}
	public int getPositionId() {
		return positionId;
	}
	public void setPositionId(int positionId) {
		this.positionId = positionId;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}

	public Integer getAccnum() {
		return accnum;
	}
	public void setAccnum(Integer accnum) {
		this.accnum = accnum;
	}
	public String getVideoname() {
		return videoname;
	}
	public void setVideoname(String videoname) {
		this.videoname = videoname;
	}
	public int getLogintype() {
		return logintype;
	}
	public void setLogintype(int logintype) {
		this.logintype = logintype;
	}
	public void setN_point(double n_point) {
		this.n_point = n_point;
	}
	public void setE_point(double e_point) {
		this.e_point = e_point;
	}
	public double getN_point() {
		return n_point;
	}
	public double getE_point() {
		return e_point;
	}
	
}
