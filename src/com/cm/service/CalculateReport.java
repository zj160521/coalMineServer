package com.cm.service;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cm.dao.CalculateReportDao;
import com.cm.entity.AnalogStatistics;
import com.cm.entity.CalibrationData;
import com.cm.entity.SensorAlarmreport;
import com.cm.entity.SensorReport;
import com.cm.entity.SwitchEfficiency;
import com.cm.entity.vo.SwitchStateChangeVo;
import com.cm.service.AnalogStatisticsService;
import com.cm.service.CalculateReportService;
import com.cm.service.CalibrationDataService;
import com.cm.service.SensorAlarmreportService;
import com.cm.service.SwitchEfficiencyService;
import com.cm.service.SwitchStateChangeService;

import util.LogOut;

@Lazy(false)
@Component("calculateReport")
public class CalculateReport {
	
	@Autowired
	private CalculateReportService service;
	
	@Autowired
	private CalculateReportDao dao;
	
	@Autowired
	private AnalogStatisticsService analogStatisticsService;
	
	@Autowired
	private SwitchEfficiencyService swithEff;
	
	@Autowired
	private SwitchStateChangeService stateService;
	
	@Autowired
	private SensorAlarmreportService alarmreportService;
	
	@Autowired
	private CalibrationDataService dataService;
	
	@Scheduled(cron = "0 35 1 * * ?")
	public void jobbyDay() throws ParseException{
		try {
			List<SensorReport> list = service.getSensorReportbyDay();
//			LogOut.log.debug(list.size()+"模拟量日班报表生成成功----------------+++++++");
			if(list.size()>0&&list!=null){
					dao.addReportbyday(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
			StringBuffer sb = new StringBuffer();
			StackTraceElement[] stackTrace = e.getStackTrace();
			for (int i = 0; i < stackTrace.length; i++) {
				StackTraceElement element = stackTrace[i];
				sb.append(element.toString()+"\n");
			}
			LogOut.log.error("CalculateReport dayReport Exception-"+sb.toString());
		}
		
	}
	
	/*@Scheduled(cron = "0 * * * * ?")
	public void addCoalMineMinData(){
		try {
			List<AnalogStatistics> list = analogStatisticsService.getbyMin();
			LogOut.log.debug(list.size()+"-----每分钟数据获取成功--------------------------");
			if(list.size()>0&&list!=null){
				analogStatisticsService.addCoalMineMinData(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
			StringBuffer sb = new StringBuffer();
			StackTraceElement[] stackTrace = e.getStackTrace();
			for (int i = 0; i < stackTrace.length; i++) {
				StackTraceElement element = stackTrace[i];
				sb.append(element.toString()+"\n");
			}
			LogOut.log.error("CalculateReport 1minReport Exception-"+sb.toString());
		}
	}
	
	@Scheduled(cron = "0 0/10 * * * ?")
	public void jobbytenmin(){
		try {
			List<AnalogStatistics> list = analogStatisticsService.getbytenmin();
//		LogOut.log.debug(list.size()+"-----10分钟--------------------------报表获取成功");
			if(list.size()>0&&list!=null){
					analogStatisticsService.addAnalogStatistics(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
			StringBuffer sb = new StringBuffer();
			StackTraceElement[] stackTrace = e.getStackTrace();
			for (int i = 0; i < stackTrace.length; i++) {
				StackTraceElement element = stackTrace[i];
				sb.append(element.toString()+"\n");
			}
			LogOut.log.error("CalculateReport 10minReport Exception-"+sb.toString());
		}
	}
	
	@Scheduled(cron = "0 0 * * * ?")
	public void jobbyonehour(){
		try {
			List<AnalogStatistics> list = analogStatisticsService.getbyonehour();
//		LogOut.log.debug(list.size()+"----------1小时---------------------报表获取成功");
			if(list.size()>0&&list!=null){
					analogStatisticsService.addAnalogStatistics(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
			StringBuffer sb = new StringBuffer();
			StackTraceElement[] stackTrace = e.getStackTrace();
			for (int i = 0; i < stackTrace.length; i++) {
				StackTraceElement element = stackTrace[i];
				sb.append(element.toString()+"\n");
			}
			LogOut.log.error("CalculateReport onehourReport Exception-"+sb.toString());
		}
	}
	
	@Scheduled(cron = "0 0 0/8 * * ?")
	public void jobbyeighthour(){
		try {
			List<AnalogStatistics> list = analogStatisticsService.getbyeighthour();
//		LogOut.log.debug(list.size()+"-------------8小时------------------报表获取成功");
			if(list.size()>0&&list!=null){
					analogStatisticsService.addAnalogStatistics(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
			StringBuffer sb = new StringBuffer();
			StackTraceElement[] stackTrace = e.getStackTrace();
			for (int i = 0; i < stackTrace.length; i++) {
				StackTraceElement element = stackTrace[i];
				sb.append(element.toString()+"\n");
			}
			LogOut.log.error("CalculateReport eighthorReport Exception-"+sb.toString());
		}
	}
	
	@Scheduled(cron = "0 0 0 * * ?")
	public void jobbyoneday(){
		try {
			List<AnalogStatistics> list = analogStatisticsService.getbyday();
			analogStatisticsService.deletedata();
//		LogOut.log.debug(list.size()+"-------------1天------------------报表获取成功");
			if(list.size()>0&&list!=null){
					analogStatisticsService.addAnalogStatistics(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
			StringBuffer sb = new StringBuffer();
			StackTraceElement[] stackTrace = e.getStackTrace();
			for (int i = 0; i < stackTrace.length; i++) {
				StackTraceElement element = stackTrace[i];
				sb.append(element.toString()+"\n");
			}
			LogOut.log.error("CalculateReport onedayReport Exception-"+sb.toString());
		}
	}
	*/
	
	@Scheduled(cron = "0 0 * * * ?")
	public void switchEffbyhour() throws ParseException{
		try {
			List<SwitchEfficiency> list = swithEff.getdata();
//		LogOut.log.debug(list.size()+"-----------------------------开机效率数据生成成功");
			if(list.size()>0&&list!=null){
					swithEff.adddata(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
			StringBuffer sb = new StringBuffer();
			StackTraceElement[] stackTrace = e.getStackTrace();
			for (int i = 0; i < stackTrace.length; i++) {
				StackTraceElement element = stackTrace[i];
				sb.append(element.toString()+"\n");
			}
			LogOut.log.error("CalculateReport switchEffbyhour Exception-"+sb.toString());
		}
	}
	
	@Scheduled(cron = "0 25 1 * * ?")
	public void switchStatechang(){
		try {
			List<SwitchStateChangeVo> list = stateService.getdata();
//			LogOut.log.debug(list.size()+"-----------------------------开关量状态变动记录数据");
			if(list!=null&&list.size()>0){
					stateService.adddata(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
			StringBuffer sb = new StringBuffer();
			StackTraceElement[] stackTrace = e.getStackTrace();
			for (int i = 0; i < stackTrace.length; i++) {
				StackTraceElement element = stackTrace[i];
				sb.append(element.toString()+"\n");
			}
			LogOut.log.error("CalculateReport switchStatechang Exception-"+sb.toString());
		}	
	}
	
	@Scheduled(cron = "0 32 * * * ?")
	public void addAlarmreport(){
		try {
			List<SensorAlarmreport> list = alarmreportService.getAlarmreport();
			LogOut.log.debug(list.size()+"-----------------------------模拟量报警数据");
			if(list.size()>0&&list!=null){
					alarmreportService.addAlarmreport(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
			StringBuffer sb = new StringBuffer();
			StackTraceElement[] stackTrace = e.getStackTrace();
			for (int i = 0; i < stackTrace.length; i++) {
				StackTraceElement element = stackTrace[i];
				sb.append(element.toString()+"\n");
			}
			LogOut.log.error("CalculateReport switchStatechang Exception-"+sb.toString());
		}	
	}
	
	@Scheduled(cron = "0 3 * * * ?")
	public void addPowerreport(){
		try {
			List<SensorAlarmreport> list = alarmreportService.getPowerReport();
//			LogOut.log.debug(list.size()+"-----------------------------模拟量断电报警数据");
			if(list.size()>0&&list!=null){
					alarmreportService.addPowerReport(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
			StringBuffer sb = new StringBuffer();
			StackTraceElement[] stackTrace = e.getStackTrace();
			for (int i = 0; i < stackTrace.length; i++) {
				StackTraceElement element = stackTrace[i];
				sb.append(element.toString()+"\n");
			}
			LogOut.log.error("CalculateReport switchStatechang Exception-"+sb.toString());
		}	
	}
	
	@Scheduled(cron = "0 3 * * * ?")
	public void addRepowerreport(){
		try {
			List<SensorAlarmreport> list = alarmreportService.getRepowerReport();
//		LogOut.log.debug(list.size()+"-----------------------------模拟量馈电异常数据");
			if(list.size()>0&&list!=null){
					alarmreportService.addRepowerReport(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
			StringBuffer sb = new StringBuffer();
			StackTraceElement[] stackTrace = e.getStackTrace();
			for (int i = 0; i < stackTrace.length; i++) {
				StackTraceElement element = stackTrace[i];
				sb.append(element.toString()+"\n");
			}
			LogOut.log.error("CalculateReport switchStatechang Exception-"+sb.toString());
		}	
	}
	
	@Scheduled(cron = "0 10 * * * ?")
	public void getCalibrationData(){
		try {
			List<CalibrationData> list = dataService.getall();
			LogOut.log.debug(list.size()+"-----------------------------标校维护数据");
			if(list.size()>0&&list!=null){
				dataService.updatedata(list);;
			}
		} catch (Exception e) {
			e.printStackTrace();
			StringBuffer sb = new StringBuffer();
			StackTraceElement[] stackTrace = e.getStackTrace();
			for (int i = 0; i < stackTrace.length; i++) {
				StackTraceElement element = stackTrace[i];
				sb.append(element.toString()+"\n");
			}
			LogOut.log.error("CalculateReport switchStatechang Exception-"+sb.toString());
		}	
	}
	
}
