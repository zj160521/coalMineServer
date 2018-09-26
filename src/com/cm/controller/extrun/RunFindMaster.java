package com.cm.controller.extrun;

import java.io.IOException;
import java.util.Map;

import com.cm.security.ConfigUtil;

import util.LogOut;

public class RunFindMaster extends RunCmdBase implements IRunCmd {

	private String[] cmdline;
	
	public RunFindMaster() {
	}
	
	public int init(Map<String,String> params) {
		
		try {
			cmdline = new String[]{"/bin/sh","-c","ip addr"};
		} catch(Exception e) {
			e.printStackTrace();
			super.runlog.setOpresult(-1);
			return -1;
		}

		return 0;
	}

    public int init2(Map<String,String> params) {
		
		try {
			cmdline = new String[]{"/bin/sh","-c","ls -l /opt/backupDB/"};
		} catch(Exception e) {
			e.printStackTrace();
			super.runlog.setOpresult(-1);
			return -1;
		}

		return 0;
	}
	
	public int runCli() {
		
		super.runlog.setOpcode(1);
		super.runlog.setOpcodetxt("查询主备机");

		try {
			process=Runtime.getRuntime().exec(cmdline);
		} catch (IOException e) {
//			e.printStackTrace();
			LogOut.log.debug("查询错误："+e.getMessage());
			return -1;
		}
		return 0;
	}

	public Object doEnd() {
		
//		killRun(process);
		waitFor(process);
		try {
			logRun(process);
		} catch (IOException e) {
			e.printStackTrace();
			LogOut.log.info("异常：", e);
		}
		String res=super.runlog.getStdin();
		LogOut.log.debug("主备机："+res);
		if(res==null){
			return "-1";
		}else{
			if(res.contains(getRedisAddr())){
				return "1";
			}else{
				return "0";
			}
		}
	}

    public Object doEnd2() {
		
//		killRun(process);
		waitFor(process);
		try {
			logRun(process);
		} catch (IOException e) {
			e.printStackTrace();
			LogOut.log.info("异常：", e);
		}
		String res=super.runlog.getStdin();
		LogOut.log.debug("文件列表："+res);
		if(res==null||"".equals(res)){
			return "-1";
		}else{
			return res;
		}
	}
	
	public static String getRedisAddr() {
		ConfigUtil cfg = ConfigUtil.getInstance();
        return cfg.getRedis_ip();
    }
	
	public static String getSysIp() {
		ConfigUtil cfg = ConfigUtil.getInstance();
        return cfg.getSys_ip();
    }

	@Override
	public String getCmdline() {
		return null;
	}
}
