package com.cm.controller;

import java.util.Map;

import com.cm.controller.extrun.RunBackupDb;
import com.cm.entity.DbBackupLog;
import com.cm.service.DbBackupLogService;

import util.LogOut;

public class ImportDbThread implements Runnable{

	private static ImportDbThread importdb;
	private static int flag=0;
	private static Map<String,String> params;
	private static DbBackupLogService logService;
	private static String ip="";
	private static String remark="";
	private int status=0;
	private DbBackupLog log=new DbBackupLog();
	@Override
	public void run() {
		try {
			RunBackupDb runcmd = new RunBackupDb();
			
			flag=5;
			if (runcmd.init2(params) != 0) {
				flag=-1;
				status=-1;
				add(log);
				importdb=null;
				return;
			}
			flag=40;
			if (runcmd.runCli() != 0) {
				flag=-1;
				status=-1;
				add(log);
				importdb=null;
				return;
			}
			flag=60;
			String r=(String) runcmd.doEnd();
			if(!"1".equals(r)){
				flag=-1;
				status=-1;
				add(log);
				importdb=null;
				return;
			}
			flag=100;
			add(log);
			importdb=null;
		} catch (Exception e) {
			flag=-1;
			status=-1;
			add(log);
			importdb=null;
			e.printStackTrace();
			LogOut.log.info("导入数据异常：", e);
		}
	}
	
	public static ImportDbThread getInstance(Map<String,String> params2,DbBackupLogService service){
		if(importdb==null){
			synchronized (ImportDbThread.class) {
				if(importdb==null){
					importdb=new ImportDbThread();
					logService=service;
					params=params2;
				}
			}
		}
		return importdb;
	}
	
	public void add(DbBackupLog log){
		log.setStatus(status);
		log.setType(2);
		log.setPath(params.get("path"));
		log.setIp(ip);
		log.setRemark(remark);
		logService.add(log);
	}
	public int getFlag() {
		return flag;
	}
}
