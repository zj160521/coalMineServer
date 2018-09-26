package com.cm.entity.vo;

import java.util.List;

import com.cm.entity.DevAction;

public class DevLinkVo {
	private int id;
	private int pid;
	private int areaId;//区域ID
	private String name;//联动名
	private String lgcExps;//逻辑表达式
	private String repowerExps;//逻辑表达式
	private String alarm;//报警名
	private int isUse;//是否启用
	private String filePath;//广播语音文件路径
	private int crarea;//读卡器区域
	private int leastNum;//满足条件的设备的最少个数
	private String isControl;//是逻辑监测还是逻辑控制
	private List<DevLogicVo> listLgc;//设备逻辑组
	private List<DevAction> listCutOut;//断馈电仪动作组
	private List<DevAction> listRadio;//广播动作组
	private List<DevAction> listCardReader;//读卡器动作组
	private List<String> uidlist;

	public String getIsControl() {
		return isControl;
	}
	public void setIsControl(String isControl) {
		this.isControl = isControl;
	}
	public String getRepowerExps() {
		return repowerExps;
	}
	public void setRepowerExps(String repowerExps) {
		this.repowerExps = repowerExps;
	}
	public int getLeastNum() {
		return leastNum;
	}
	public void setLeastNum(int leastNum) {
		this.leastNum = leastNum;
	}
	public int getCrarea() {
		return crarea;
	}
	public void setCrarea(int crarea) {
		this.crarea = crarea;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public List<DevAction> getListCutOut() {
		return listCutOut;
	}
	public void setListCutOut(List<DevAction> listCutOut) {
		this.listCutOut = listCutOut;
	}
	public List<DevAction> getListRadio() {
		return listRadio;
	}
	public void setListRadio(List<DevAction> listRadio) {
		this.listRadio = listRadio;
	}
	public List<DevAction> getListCardReader() {
		return listCardReader;
	}
	public void setListCardReader(List<DevAction> listCardReader) {
		this.listCardReader = listCardReader;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public int getAreaId() {
		return areaId;
	}
	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLgcExps() {
		return lgcExps;
	}
	public void setLgcExps(String lgcExps) {
		this.lgcExps = lgcExps;
	}
	public String getAlarm() {
		return alarm;
	}
	public void setAlarm(String alarm) {
		this.alarm = alarm;
	}
	public int getIsUse() {
		return isUse;
	}
	public void setIsUse(int isUse) {
		this.isUse = isUse;
	}
	public List<DevLogicVo> getListLgc() {
		return listLgc;
	}
	public void setListLgc(List<DevLogicVo> listLgc) {
		this.listLgc = listLgc;
	}
	public List<String> getUidlist() {
		return uidlist;
	}
	public void setUidlist(List<String> uidlist) {
		this.uidlist = uidlist;
	}
	
}
