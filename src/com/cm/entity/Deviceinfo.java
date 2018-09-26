package com.cm.entity;

public class Deviceinfo {

	private int id; // 主键
	private String serialnumber; // 序列号
	private String byAlarmInPortNum; // 报警输入个数
	private String byAlarmOutPortNum; // 报警输出个数
	private String byDiskNum; // 硬盘个数
	private String byDVRType; // 设备类型
	private String byChanNum; // 模拟通道个数
	private String byStartChan; // 起始通道号
	private String byAudioChanNum; // 语音通道数
	private String byIPChanNum; // 最大数字通道个数
	private String baoliu; // 保留
	private int isuse; // 状态

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSerialnumber() {
		return serialnumber;
	}

	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}

	public String getByAlarmInPortNum() {
		return byAlarmInPortNum;
	}

	public void setByAlarmInPortNum(String byAlarmInPortNum) {
		this.byAlarmInPortNum = byAlarmInPortNum;
	}

	public String getByAlarmOutPortNum() {
		return byAlarmOutPortNum;
	}

	public void setByAlarmOutPortNum(String byAlarmOutPortNum) {
		this.byAlarmOutPortNum = byAlarmOutPortNum;
	}

	public String getByDiskNum() {
		return byDiskNum;
	}

	public void setByDiskNum(String byDiskNum) {
		this.byDiskNum = byDiskNum;
	}

	public String getByDVRType() {
		return byDVRType;
	}

	public void setByDVRType(String byDVRType) {
		this.byDVRType = byDVRType;
	}

	public String getByChanNum() {
		return byChanNum;
	}

	public void setByChanNum(String byChanNum) {
		this.byChanNum = byChanNum;
	}

	public String getByStartChan() {
		return byStartChan;
	}

	public void setByStartChan(String byStartChan) {
		this.byStartChan = byStartChan;
	}

	public String getByAudioChanNum() {
		return byAudioChanNum;
	}

	public void setByAudioChanNum(String byAudioChanNum) {
		this.byAudioChanNum = byAudioChanNum;
	}

	public String getByIPChanNum() {
		return byIPChanNum;
	}

	public void setByIPChanNum(String byIPChanNum) {
		this.byIPChanNum = byIPChanNum;
	}

	public String getBaoliu() {
		return baoliu;
	}

	public void setBaoliu(String baoliu) {
		this.baoliu = baoliu;
	}

	public int getIsuse() {
		return isuse;
	}

	public void setIsuse(int isuse) {
		this.isuse = isuse;
	}

}
