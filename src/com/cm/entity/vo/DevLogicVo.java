package com.cm.entity.vo;

public class DevLogicVo {
	private int id;
	private int groupId;
	private int lgcDevid;
	private String dev;//设备别名
	private String dev2;//设备2别名
	private String lgcExps;//逻辑表达式
	private double value;//执行参数
	private String switchValueText;
	private String dsp;//设备描述
	private int sole;//是否删除
	private String lgcOperator;//逻辑运算符
	private int type;//设备类型标识,100是模拟量，25是开关量
	private int comparisonType;//比较类型
	private String ip;
	private double value2;//开关量执行参数
	private String lgcOperator2;//解除逻辑运算符
	private String repowerExps;//取消联动逻辑表达式
	private int scene;//逻辑检查情景模式：
	private int dev2Id;//针对于情景模式的设备2ID，ID一般为模拟量传感器ID
	private String uid;//设备唯一标识
	private String uid2;//设备唯一标识
	private String lgcOperator3;//通讯中断逻辑运算符
	private String value3;//通讯中断执行参数
	private Integer value_change;//监测值变化标识符
	private Integer reverting;//值同向异向标识符
	private boolean newAdd;
	private String debugList;//标校、调校数组
	
	public String getDebugList() {
		return debugList;
	}
	public void setDebugList(String debugList) {
		this.debugList = debugList;
	}
	public boolean isNewAdd() {
		return newAdd;
	}
	public void setNewAdd(boolean newAdd) {
		this.newAdd = newAdd;
	}
	public Integer getReverting() {
		return reverting;
	}
	public void setReverting(Integer reverting) {
		this.reverting = reverting;
	}
	public Integer getValue_change() {
		return value_change;
	}
	public void setValue_change(Integer value_change) {
		this.value_change = value_change;
	}
	public String getLgcOperator3() {
		return lgcOperator3;
	}
	public void setLgcOperator3(String lgcOperator3) {
		this.lgcOperator3 = lgcOperator3;
	}
	public String getValue3() {
		return value3;
	}
	public void setValue3(String value3) {
		this.value3 = value3;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUid2() {
		return uid2;
	}
	public void setUid2(String uid2) {
		this.uid2 = uid2;
	}
	public int getScene() {
		return scene;
	}
	public void setScene(int scene) {
		this.scene = scene;
	}
	public int getDev2Id() {
		return dev2Id;
	}
	public void setDev2Id(int dev2Id) {
		this.dev2Id = dev2Id;
	}
	public String getRepowerExps() {
		return repowerExps;
	}
	public void setRepowerExps(String repowerExps) {
		this.repowerExps = repowerExps;
	}
	public double getValue2() {
		return value2;
	}
	public void setValue2(double value2) {
		this.value2 = value2;
	}
	public String getLgcOperator2() {
		return lgcOperator2;
	}
	public void setLgcOperator2(String lgcOperator2) {
		this.lgcOperator2 = lgcOperator2;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getComparisonType() {
		return comparisonType;
	}
	public void setComparisonType(int comparisonType) {
		this.comparisonType = comparisonType;
	}
	public String getDev2() {
		return dev2;
	}
	public void setDev2(String dev2) {
		this.dev2 = dev2;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public String getLgcOperator() {
		return lgcOperator;
	}
	public void setLgcOperator(String lgcOperator) {
		this.lgcOperator = lgcOperator;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getDsp() {
		return dsp;
	}
	public void setDsp(String dsp) {
		this.dsp = dsp;
	}
	public String getSwitchValueText() {
		return switchValueText;
	}
	public void setSwitchValueText(String switchValueText) {
		this.switchValueText = switchValueText;
	}
	public int getSole() {
		return sole;
	}
	public void setSole(int sole) {
		this.sole = sole;
	}
	public String getDev() {
		return dev;
	}
	public void setDev(String dev) {
		this.dev = dev;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public int getLgcDevid() {
		return lgcDevid;
	}
	public void setLgcDevid(int lgcDevid) {
		this.lgcDevid = lgcDevid;
	}
	public String getLgcExps() {
		return lgcExps;
	}
	public void setLgcExps(String lgcExps) {
		this.lgcExps = lgcExps;
	}
	
	
}
