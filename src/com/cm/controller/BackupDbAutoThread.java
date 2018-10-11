package com.cm.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;

import com.cm.controller.extrun.RunBackupDb;
import com.cm.controller.extrun.RunFindMaster;
import com.cm.entity.DbBackupLog;
import com.cm.service.DbBackupLogService;

import util.LogOut;

public class BackupDbAutoThread implements Runnable{

	private static BackupDbAutoThread backup;
	private static int flag=0;
	private static Map<String,String> params;
	private static DbBackupLogService logService;
	private static String ip;
	private static String remark="";
	private static SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_dd");
	private int status=0;
	private int type=0;
	private DbBackupLog log=new DbBackupLog();
	private RunBackupDb runcmd = new RunBackupDb();
	@Override
	public void run() {
		backup("config");
		List<String> str=getDate();
		LogOut.log.debug("自动备份日期size："+str.size());
		for(String s:str){
			LogOut.log.debug("自动备份日期："+s);
		    backup(s);	
		}
	}
	
	public void backup(String starttime){
       try {
			if("config".equals(starttime)){
				params.put("table2", BackupController.gettable2());
				params.put("filename2", starttime+".sql.gz");
				if (runcmd.init(params) != 0) {
					flag=-1;
					status=-1;
					add(log);
					backup=null;
					return;
				}

				if (runcmd.runCli2() != 0) {
					flag=-1;
					status=-1;
					add(log);
					backup=null;
					return;
				}
				
				String r=(String) runcmd.doEnd2();
				remark=r;
				if(!"1".equals(r)){
					flag=-1;
					status=-1;
					add(log);
					backup=null;
					return;
				}
			}else{
				params.put("table", BackupController.gettable(starttime));
				params.put("filename", starttime+".sql.gz");
				if (runcmd.init(params) != 0) {
					flag=-1;
					status=-1;
					add(log);
					backup=null;
					return;
				}

				if (runcmd.runCli() != 0) {
					flag=-1;
					status=-1;
					add(log);
					backup=null;
					return;
				}
				
				String r=(String) runcmd.doEnd();
				remark=r;
				if(!"1".equals(r)){
					flag=-1;
					status=-1;
					add(log);
					backup=null;
					return;
				}
			}
			
			add(log);
			backup=null;
		} catch (Exception e) {
			flag=-1;
			add(log);
			backup=null;
			e.printStackTrace();
			LogOut.log.info("自动备份异常：", e);
		}
	}
	
	public static BackupDbAutoThread getInstance(Map<String,String> params2,DbBackupLogService service,String ip2){
		if(backup==null){
			synchronized (BackupDbAutoThread.class) {
				if(backup==null){
					backup=new BackupDbAutoThread();
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
	
	/**
	 * 获取两个日期之间的日期
	 * @param start 开始日期
	 * @param end 结束日期
	 * @return 日期集合
	 */
	public List<Date> getBetweenDates(Date start, Date end) {
	    List<Date> result = new ArrayList<Date>();
	    Calendar tempStart = Calendar.getInstance();
	    tempStart.setTime(start);
	    tempStart.add(Calendar.DAY_OF_YEAR, 1);
	    
	    Calendar tempEnd = Calendar.getInstance();
	    tempEnd.setTime(end);
	    while (tempStart.before(tempEnd)) {
	        result.add(tempStart.getTime());
	        tempStart.add(Calendar.DAY_OF_YEAR, 1);
	    }
	    return result;
	}
	
	public List<String> getBetweenDates2(List<Date> list){
		List<String> res=new ArrayList<String>();
		for(Date date:list){
			res.add(format.format(date));
		}
		return res;
	}
	
	public List<String> getDate(){
		Date now=new Date();
		List<Date> dates=getBetweenDates(DateUtils.addMonths(now, -3),now);
		List<String> sdate=getBetweenDates2(dates);
		RunFindMaster runcmd = new RunFindMaster();
		Map<String,String> m=new HashMap<String,String>();
		if (runcmd.init2(m) != 0) {
			LogOut.log.info("自动备份init2异常");
		}else{
			if (runcmd.runCli() != 0) {
				LogOut.log.info("自动备份runCli异常");
			}else{
				String r=(String) runcmd.doEnd2();
				if(r.equals("-1")){
					LogOut.log.info("自动备份doEnd2异常");
				}else{
					String[] a = r.split("\\s{1,}");
					if(a.length>9){
						int num = (a.length-2)/9;
						for(int i=0;i<num;i++){
							//筛选时间内所有文件
							String time=null;
							try {
//								LogOut.log.debug("自动备份文件名："+a[i*9+10]);
								time = a[i*9+10].substring(0, 10);
							} catch (Exception e) {
								e.printStackTrace();
								LogOut.log.info("自动备份异常：", e);
								time=null;
							}
							if(sdate.contains(time)){
								sdate.remove(time);
							}
						}
					}
				}
			}
		}
		return sdate;
	}
}
