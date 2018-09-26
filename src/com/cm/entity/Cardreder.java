package com.cm.entity;

import java.sql.Timestamp;

public class Cardreder {
	
	private int id;
	private int substation_id;//网关id
	private int pid;
	private int cid;//设备地址
	private	int position_id;//位置信息id
	private String position;//位置名称
	private int cstate;//状态 1为在用 2为停用 3为假删除
	private int crange;//通信范围
	private int ctype;//读卡器类型，0为普通 1为出入口
	private String addr;//读卡器地址
	private String subname;//网关IP地址
	private String did;
	private String entrance;//前端转换出入口使用
	private int typeid;//设备类型
	private int areaid;//区域ID
	private String areaname;//区域名字
	private double n_point;//经度
	private double e_point;//纬度
	private int state;
	private Timestamp disconnercttime;
	private String measu;
	private Timestamp keeptime;
	private String starttime;// 开始时间
	private String endtime;// 结束时间
	private String path;
	private int list_type;
	private int is_exit;//是否为门禁读卡器
	private String alais;
	private String uid;//全局唯一ID;
	private double x_point;
	private double y_point;
	private String type;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAlais() {
		return alais;
	}
	public void setAlais(String alais) {
		this.alais = alais;
	}
	public int getIs_exit() {
		return is_exit;
	}
	public void setIs_exit(int is_exit) {
		this.is_exit = is_exit;
	}
	public int getList_type() {
		return list_type;
	}
	public void setList_type(int list_type) {
		this.list_type = list_type;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
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
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public String getEntrance() {
		return entrance;
	}
	public void setEntrance(String entrance) {
		this.entrance = entrance;
	}
	public int getAreaid() {
		return areaid;
	}
	public void setAreaid(int areaid) {
		this.areaid = areaid;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public Timestamp getDisconnercttime() {
		return disconnercttime;
	}
	public void setDisconnercttime(Timestamp disconnercttime) {
		this.disconnercttime = disconnercttime;
	}
	public String getMeasu() {
		return measu;
	}
	public void setMeasu(String measu) {
		this.measu = measu;
	}
	public Timestamp getKeeptime() {
		return keeptime;
	}
	public void setKeeptime(Timestamp keeptime) {
		this.keeptime = keeptime;
	}
	public String getDid() {
		return did;
	}
	public void setDid(String did) {
		this.did = did;
	}
	public int getTypeid() {
		return typeid;
	}
	public void setTypeid(int typeid) {
		this.typeid = typeid;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getSubname() {
		return subname;
	}
	public void setSubname(String subname) {
		this.subname = subname;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSubstation_id() {
		return substation_id;
	}
	public void setSubstation_id(int substation_id) {
		this.substation_id = substation_id;
	}
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public int getCstate() {
		return cstate;
	}
	public void setCstate(int cstate) {
		this.cstate = cstate;
	}
	public int getCrange() {
		return crange;
	}
	public void setCrange(int crange) {
		this.crange = crange;
	}
	public int getCtype() {
		return ctype;
	}
	public void setCtype(int ctype) {
		this.ctype = ctype;
	}
	public int getPosition_id() {
		return position_id;
	}
	public void setPosition_id(int position_id) {
		this.position_id = position_id;
	}
	public String getAreaname() {
		return areaname;
	}
	public void setAreaname(String areaname) {
		this.areaname = areaname;
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
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public double getX_point() {
		return x_point;
	}
	public void setX_point(double x_point) {
		this.x_point = x_point;
	}
	public double getY_point() {
		return y_point;
	}
	public void setY_point(double y_point) {
		this.y_point = y_point;
	}
	
}
