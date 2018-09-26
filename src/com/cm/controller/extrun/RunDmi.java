package com.cm.controller.extrun;

import java.io.IOException;
import java.util.Map;

import com.cm.security.ConfigUtil;

import util.LogOut;

public class RunDmi extends RunCmdBase implements IRunCmd {

	private String[] cmdline;
	
	public RunDmi() {
	}
	
	public int init(Map<String,String> params) {
		
		try {
			cmdline = new String[]{"/bin/sh","-c","ssh "+params.get("ip")+" /usr/sbin/dmi"+params.get("order")};
			LogOut.log.debug("命令========== "+cmdline[2]);
		} catch(Exception e) {
			e.printStackTrace();
			super.runlog.setOpresult(-1);
			return -1;
		}

		return 0;
	}

	public int runCli() {
		
		super.runlog.setOpcode(1);
		super.runlog.setOpcodetxt("运行dmi");

		try {
			process=Runtime.getRuntime().exec(cmdline);
		} catch (IOException e) {
//			e.printStackTrace();
			LogOut.log.debug("运行dmi："+e.getMessage());
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
		String res=super.runlog.getStderr();
		LogOut.log.debug("结果："+res);
		if(res==null||res.isEmpty()){
			return "-1";
		}else{
			return res.trim();
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
