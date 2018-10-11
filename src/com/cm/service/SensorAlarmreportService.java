package com.cm.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cm.dao.EnvclassesDao;
import com.cm.dao.SensorAlarmreportDao;
import com.cm.entity.EnvClasses;
import com.cm.entity.SensorAlarmreport;
import com.cm.entity.vo.AnaloginfoQuery;

@Service("sensorAlarmreportService")
public class SensorAlarmreportService {
	
	@Autowired
	private SensorAlarmreportDao dao;
	
	@Autowired
	private AnaloginfoService service;
	
	@Autowired
	private EnvclassesDao envclassesDao;
	
	public List<SensorAlarmreport> getAlarmreport() throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<EnvClasses> classes = envclassesDao.getAll();
		List<SensorAlarmreport> list = new ArrayList<SensorAlarmreport>();
		List<AnaloginfoQuery> list2 = service.getallAna(null);
		for(AnaloginfoQuery l:list2){
			SensorAlarmreport a = new SensorAlarmreport();
			a.setIp(l.getIp());
			a.setSensorId(l.getSensorId());
			a.setSensor_type(l.getSensor_type());
			a.setStarttime(l.getStarttime());
			if(classes!=null&&classes.size()>0){
				long starttimelong = format.parse(a.getStarttime()).getTime();
				String startday = a.getStarttime().split(" ")[0];
				for(EnvClasses c:classes){
					String start = startday+" "+c.getStart()+":00";
					String end = startday+" "+c.getEnd()+":00";
					long startlong = format.parse(start).getTime();
					long endlong = format.parse(end).getTime();
					if(c.getStatus()==1&&starttimelong>startlong&&starttimelong<=endlong){
						a.setClassname(c.getName());
						a.setClassstart(c.getStart()+":00");
						a.setClassend(c.getEnd()+":00");
					}
					if(c.getStatus()==2&&starttimelong<=startlong||c.getStatus()==2&&starttimelong>endlong){
						a.setClassname(c.getName());
						a.setClassstart(c.getStart()+":00");
						a.setClassend(c.getEnd()+":00");
					}
				}
			}
			a.setEndtime(l.getEndtime());
			a.setMaxvalues(l.getMaxvalues());
			a.setMaxtime(l.getMaxtime());
			a.setAvgvalue(l.getAvgvalue());
			a.setStatus(l.getStatus());
			a.setPosition(l.getPosition());
			a.setPowerposition(l.getPowerposition());
			a.setPowercom(l.getPowercom());
			a.setMeasures(l.getMeasures());
			a.setMeasurestime(l.getMeasurestime());
			a.setFeedstatus(l.getFeedstatus());
			a.setTimes(l.getTimes());
			a.setFilltime(gettime());
			a.setDebug(l.getDebug());
			list.add(a);
		}
		return list;
	}
	
	public void addAlarmreport(List<SensorAlarmreport> list){
		dao.addAlarmreport(list);
	}
	
	public List<SensorAlarmreport> getPowerReport() throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<EnvClasses> classes = envclassesDao.getAll();
		List<SensorAlarmreport> list = new ArrayList<SensorAlarmreport>();
		List<AnaloginfoQuery> list2 = service.getpowrer(null);
		for(AnaloginfoQuery l:list2){
			SensorAlarmreport a = new SensorAlarmreport();
			a.setIp(l.getIp());
			a.setSensorId(l.getSensorId());
			a.setSensor_type(l.getSensor_type());
			a.setStarttime(l.getStarttime());
			if(classes!=null&&classes.size()>0){
				long starttimelong = format.parse(a.getStarttime()).getTime();
				String startday = a.getStarttime().split(" ")[0];
				for(EnvClasses c:classes){
					String start = startday+" "+c.getStart()+":00";
					String end = startday+" "+c.getEnd()+":00";
					long startlong = format.parse(start).getTime();
					long endlong = format.parse(end).getTime();
					if(c.getStatus()==1&&starttimelong>startlong&&starttimelong<=endlong){
						a.setClassname(c.getName());
						a.setClassstart(c.getStart()+":00");
						a.setClassend(c.getEnd()+":00");
					}
					if(c.getStatus()==2&&starttimelong<=startlong||c.getStatus()==2&&starttimelong>endlong){
						a.setClassname(c.getName());
						a.setClassstart(c.getStart()+":00");
						a.setClassend(c.getEnd()+":00");
					}
				}
			}
			a.setEndtime(l.getEndtime());
			a.setMaxvalues(l.getMaxvalues());
			a.setMaxtime(l.getMaxtime());
			a.setAvgvalue(l.getAvgvalue());
			a.setStatus(l.getStatus());
			a.setPosition(l.getPosition());
			a.setPowerposition(l.getPowerposition());
			a.setPowercom(l.getPowercom());
			a.setMeasures(l.getMeasures());
			a.setMeasurestime(l.getMeasurestime());
			a.setFeedstatus(l.getFeedstatus());
			a.setTimes(l.getTimes());
			a.setFilltime(gettime());
			a.setDebug(l.getDebug());;
			list.add(a);
			
		}
		return list;
	}
	
	public void addPowerReport(List<SensorAlarmreport> list){
		dao.addPowerReport(list);
	}
	
	
	public List<SensorAlarmreport> getRepowerReport() throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<EnvClasses> classes = envclassesDao.getAll();
		List<SensorAlarmreport> list = new ArrayList<SensorAlarmreport>();
		List<AnaloginfoQuery> list2 = service.getfeedError(null);
		for(AnaloginfoQuery l:list2){
			SensorAlarmreport a = new SensorAlarmreport();
			a.setIp(l.getIp());
			a.setSensorId(l.getSensorId());
			a.setSensor_type(l.getSensor_type());
			a.setStarttime(l.getStarttime());
			if(classes!=null&&classes.size()>0){
				long starttimelong = format.parse(a.getStarttime()).getTime();
				String startday = a.getStarttime().split(" ")[0];
				for(EnvClasses c:classes){
					String start = startday+" "+c.getStart()+":00";
					String end = startday+" "+c.getEnd()+":00";
					long startlong = format.parse(start).getTime();
					long endlong = format.parse(end).getTime();
					if(c.getStatus()==1&&starttimelong>startlong&&starttimelong<=endlong){
						a.setClassname(c.getName());
						a.setClassstart(c.getStart()+":00");
						a.setClassend(c.getEnd()+":00");
					}
					if(c.getStatus()==2&&starttimelong<=startlong||c.getStatus()==2&&starttimelong>endlong){
						a.setClassname(c.getName());
						a.setClassstart(c.getStart()+":00");
						a.setClassend(c.getEnd()+":00");
					}
				}
			}
			a.setEndtime(l.getEndtime());
			a.setAvgvalue(l.getAvgvalue());
			a.setStatus(l.getStatus());
			a.setPosition(l.getPosition());
			a.setPowerposition(l.getPowerposition());
			a.setMeasures(l.getMeasures());
			a.setMeasurestime(l.getMeasurestime());
			a.setFeedstatus(l.getFeedstatus());
			a.setTimes(l.getTimes());
			a.setFilltime(gettime());
			a.setDebug(l.getDebug());
			a.setFeeduid(l.getFeeduid());
			list.add(a);
		}
		return list;
	}
	
	public void addRepowerReport(List<SensorAlarmreport> list){
		dao.addRepowerReport(list);
	}
	
	public int isexisting(SensorAlarmreport alarmreport){
		return dao.isexisting(alarmreport);
	}
	
	public String gettime(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:00:00");
		String time3 =format.format(new Date());
		return time3;
	}
}
