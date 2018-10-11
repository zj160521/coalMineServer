package com.cm.controller.extrun;

import java.util.Map;

import util.LogOut;

public class RunBackupDb extends RunCmdBase implements IRunCmd {

	private String[] cmd;
	private String[] cmd2;
	private Process process;
	public RunBackupDb() {
	}
	
	public int init(Map<String,String> params) {
		cmd=new String[]{"/bin/sh","-c","/usr/bin/mysqldump -ucoalmine  -p'"+params.get("password")+"' coalmine"+params.get("table")+"|openssl enc -e -aes256 -k 000000"+"|gzip >"+params.get("path")+params.get("filename")};
		cmd2=new String[]{"/bin/sh","-c","/usr/bin/mysqldump -ucoalmine  -p'"+params.get("password")+"' coalmine"+params.get("table2")+"|openssl enc -e -aes256 -k 000000"+"|gzip >"+params.get("path")+params.get("filename2")};
		LogOut.log.debug("备份命令："+cmd[2].toString());
		LogOut.log.debug("备份命令2："+cmd2[2].toString());
		return 0;
	}

	public int init0(Map<String,String> params) {
		cmd=new String[]{"/bin/sh","-c","scp "+params.get("path")+params.get("filename")+" root@"+params.get("ip")+":"+params.get("path")};
		cmd2=new String[]{"/bin/sh","-c","scp "+params.get("path")+params.get("filename2")+" root@"+params.get("ip")+":"+params.get("path")};
		LogOut.log.debug("拷贝命令："+cmd[2].toString());
		return 0;
	}
	
	public int init1(Map<String,String> params) {
		cmd=new String[]{"/bin/sh","-c","scp "+params.get("path")+params.get("filename")+" root@"+params.get("ip2")+":"+params.get("path")};
		cmd2=new String[]{"/bin/sh","-c","scp "+params.get("path")+params.get("filename2")+" root@"+params.get("ip2")+":"+params.get("path")};
		LogOut.log.debug("拷贝命令："+cmd[2].toString());
		return 0;
	}
	
	public int init2(Map<String,String> params) {
		cmd=new String[]{"/bin/sh","-c","gunzip < "+params.get("path")+params.get("filename")+"|openssl enc -d -aes256 -k 000000"+" | /usr/bin/mysql -ucoalmine  -p'"+params.get("password")+"' -f coalmine"};
		LogOut.log.debug("导入命令："+cmd[2].toString());
		return 0;
	}
	
	public int runCli() {
		try {
			process=Runtime.getRuntime().exec(cmd);
		} catch (Exception e) {
			e.printStackTrace();
			LogOut.log.debug("备份错误："+e.getMessage());
			return -1;
		}
		return 0;
	}

	public int runCli2() {
		try {
			process=Runtime.getRuntime().exec(cmd2);
		} catch (Exception e) {
			e.printStackTrace();
			LogOut.log.debug("备份错误："+e.getMessage());
			return -1;
		}
		return 0;
	}
	
	public Object doEnd() {
		try {
			process.waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
			LogOut.log.debug("备份/导入错误："+e.getMessage());
			LogOut.log.info("备份/导入错误：", e);
			return e.getMessage();
		}
		String res=super.runlog.getStderr();
		if(res==null||"".equals(res)){
			return "1";
		}else{
			return res;
		}
	}

	public Object doEnd2() {
		try {
			process.waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
			LogOut.log.debug("备份/导入错误："+e.getMessage());
			LogOut.log.info("备份/导入错误：", e);
			return e.getMessage();
		}
		String res=super.runlog.getStderr();
		if(res==null||"".equals(res)){
			return "1";
		}else{
			return res;
		}
	}
	
	@Override
	public String getCmdline() {
		return null;
	}
}
