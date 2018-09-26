package com.cm.controller.extrun;

import java.io.IOException;
import java.util.Map;

import com.cm.security.ConfigUtil;

import util.LogOut;

public class RunCheckDiskMemory extends RunCmdBase implements IRunCmd {

	private String[] cmdline;
	
	public RunCheckDiskMemory() {
	}
	
	public int init(Map<String,String> params) {
		
		try {
			cmdline = new String[]{"/bin/sh","-c","ssh "+params.get("ip")+" df -hl|grep centos-home"};
		} catch(Exception e) {
			e.printStackTrace();
			super.runlog.setOpresult(-1);
			return -1;
		}

		return 0;
	}

	public int runCli() {
		
		super.runlog.setOpcode(1);
		super.runlog.setOpcodetxt("查询磁盘空间");

		try {
			process=Runtime.getRuntime().exec(cmdline);
		} catch (IOException e) {
//			e.printStackTrace();
			LogOut.log.debug("查询磁盘空间："+e.getMessage());
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
		LogOut.log.debug("容量："+res);
		if(res==null||res.isEmpty()){
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
