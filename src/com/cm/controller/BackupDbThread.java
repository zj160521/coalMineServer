package com.cm.controller;

import java.util.Map;

import com.cm.controller.extrun.RunBackupDb;
import com.cm.entity.DbBackupLog;
import com.cm.service.DbBackupLogService;

public class BackupDbThread implements Runnable{

	private static BackupDbThread backup;
	private static int flag=0;
	private static Map<String,String> params;
	private static DbBackupLogService logService;
	private static String ip;
	private static String remark="";
	private int status=0;
	private int type=0;
	private DbBackupLog log=new DbBackupLog();
	@Override
	public void run() {
		try {
			RunBackupDb runcmd = new RunBackupDb();
			
			flag=5;
			if (runcmd.init(params) != 0) {
				flag=-1;
				status=-1;
				add(log);
				backup=null;
				return;
			}
			flag=20;
			if (runcmd.runCli() != 0) {
				flag=-1;
				status=-1;
				add(log);
				backup=null;
				return;
			}
			flag=30;
			String r=(String) runcmd.doEnd();
			remark=r;
			if(!"1".equals(r)){
				flag=-1;
				status=-1;
				add(log);
				backup=null;
				return;
			}
			flag=50;
			if (runcmd.init0(params) != 0) {
				status=-1;
				add(log);
				backup=null;
			}
			flag=60;
			if (runcmd.runCli() != 0) {
				status=-1;
				add(log);
				backup=null;
			}
			flag=70;
			String r1=(String) runcmd.doEnd();
			remark=r1;
			if(!"1".equals(r1)){
				status=-1;
				add(log);
				backup=null;
			}
			flag=80;
			if (runcmd.init0(params) != 0) {
				status=-1;
				add(log);
				backup=null;
			}
			flag=90;
			if (runcmd.runCli() != 0) {
				status=-1;
				add(log);
				backup=null;
			}
			flag=95;
			String r2=(String) runcmd.doEnd();
			remark=r2;
			if(!"1".equals(r2)){
				status=-1;
				add(log);
				backup=null;
			}
			flag=100;
			add(log);
			backup=null;
		} catch (Exception e) {
			flag=-1;
			add(log);
			backup=null;
			e.printStackTrace();
		}
	}
	
	public static BackupDbThread getInstance(Map<String,String> params2,DbBackupLogService service,String ip2){
		if(backup==null){
			synchronized (BackupDbThread.class) {
				if(backup==null){
					backup=new BackupDbThread();
					logService=service;
					ip=ip2;
				}
			}
		}
		params=params2;
		return backup;
	}

	public static int getFlag() {
		return flag;
	}
	
	public void add(DbBackupLog log){
		String logtype=params.get("type");
		if(logtype!=null){
			if("0".equals(logtype)){
				type=0;
			}else if("1".equals(logtype)){
				type=1;
			}
		}
		log.setStatus(status);
		log.setType(type);
		log.setPath(params.get("path"));
		log.setIp(ip);
		log.setRemark(remark);
		logService.add(log);
	}
}
