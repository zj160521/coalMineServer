package com.cm.controller.extrun;

import org.apache.ibatis.type.Alias;

@Alias("RunCmdLog")
public class RunCmdLog {
	private int id;
	private int deviceid;
	private String optime;
	private int opcode;
	private String opcodetxt;
	private int opresult;
	private String stdin;
	private String stderr;
	
	public synchronized int getId() {
		return id;
	}
	public synchronized void setId(int id) {
		this.id = id;
	}
	public synchronized int getDeviceid() {
		return deviceid;
	}
	public synchronized void setDeviceid(int deviceid) {
		this.deviceid = deviceid;
	}
	public synchronized String getOptime() {
		return optime;
	}
	public synchronized void setOptime(String optime) {
		this.optime = optime;
	}
	public synchronized int getOpcode() {
		return opcode;
	}
	public synchronized void setOpcode(int opcode) {
		this.opcode = opcode;
	}
	public synchronized int getOpresult() {
		return opresult;
	}
	public synchronized void setOpresult(int opresult) {
		this.opresult = opresult;
	}
	public synchronized String getStdin() {
		return stdin;
	}
	public synchronized void setStdin(String stdin) {
		this.stdin = stdin;
	}
	public synchronized String getStderr() {
		return stderr;
	}
	public synchronized void setStderr(String stderr) {
		this.stderr = stderr;
	}
	public String getOpcodetxt() {
		return opcodetxt;
	}
	public void setOpcodetxt(String opcodetxt) {
		this.opcodetxt = opcodetxt;
	}
}