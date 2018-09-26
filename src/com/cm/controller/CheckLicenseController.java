package com.cm.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cm.dao.ILicenseDao;
import com.cm.dao.IPermissionDao;
import com.cm.entity.License;
import com.cm.security.AesCode;
import com.cm.security.GetCpuCode;

import util.LogOut;

@Scope("singleton")
@Component
public class CheckLicenseController {
	/**
	 * 定时器任务
	 */
	@Autowired
	private IPermissionDao perDao;
	@Autowired
	private ILicenseDao licenseDao;
	
	//每天9点检查一次
//	@Scheduled(cron = "0 0 9 * * ?")
	public void timetask(){
		
	}
}
