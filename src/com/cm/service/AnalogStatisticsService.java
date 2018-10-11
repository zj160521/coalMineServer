package com.cm.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cm.dao.AnalogStatisticsDao;
import com.cm.entity.AnalogStatistics;
import com.cm.entity.vo.NameTime;

@Service("analogStatisticsService")
public class AnalogStatisticsService {
	
	@Autowired
	private AnalogStatisticsDao dao;

	
	public List<AnalogStatistics> getbyonehour() throws ParseException{
		String tablename = "t_analog_statistics_"+getDay();
		String time = gettime();
		List<AnalogStatistics> list = dao.getbyonehour(tablename,time);
		Map<String, List<AnalogStatistics>> map = new HashMap<String,List<AnalogStatistics>>();
		for(AnalogStatistics l:list){
			List<AnalogStatistics> a = map.get(l.getIp()+l.getSensorId()+l.getSensor_type());
			if(a==null){
				a = new ArrayList<AnalogStatistics>();
				
				map.put(l.getIp()+l.getSensorId()+l.getSensor_type(), a);
			}
			a.add(l);
		}
		return list;
	}
	
	public List<AnalogStatistics> getbyeighthour() throws ParseException{
		String tablename = "t_analog_statistics_"+getDay();
		String time = gettime();
		List<AnalogStatistics> list = dao.getbyeighthour(tablename,time);
		for(AnalogStatistics l:list){
			l.setRemark(3);
		}
		return list;
	}
	
	public List<AnalogStatistics> getbyday() throws ParseException{
		String tablename = "t_analog_statistics_"+getDay();
		String time = gettime();
		List<AnalogStatistics> list = dao.getbyday(tablename,time);
		for(AnalogStatistics l:list){
			l.setRemark(4);
		}
		return list;
	}
	
	public void addAnalogStatistics(List<AnalogStatistics> list) throws ParseException{
		String tablename = "t_coalmine_min_data_"+getDay();
		dao.addAnalogStatistics(list,tablename);
	}

	
	public List<AnalogStatistics> getall(NameTime nameTime){
		List<AnalogStatistics> list = dao.getall(nameTime);
		Map<String, AnalogStatistics> map = new HashMap<String,AnalogStatistics>();
		List<AnalogStatistics> statistics = new ArrayList<AnalogStatistics>();
		for(AnalogStatistics l:list){
			if(l.getPosition()==null){
				l.setPosition("未配置位置");
			}
			if(Math.abs(l.getLimit_alarms())==999999&&Math.abs(l.getFloor_alarms())==999999||Math.abs(l.getLimit_alarms())==0&&Math.abs(l.getFloor_alarms())==0||Math.abs(l.getLimit_alarms())==0&&Math.abs(l.getFloor_alarms())==999999||Math.abs(l.getLimit_alarms())==999999&&Math.abs(l.getFloor_alarms())==0){
				l.setLimit_alarm("--/--");
			}else if(Math.abs(l.getLimit_alarms())!=999999&&Math.abs(l.getLimit_alarms())!=0&&Math.abs(l.getFloor_alarms())==999999||Math.abs(l.getLimit_alarms())!=999999&&Math.abs(l.getLimit_alarms())!=0&&Math.abs(l.getFloor_alarms())==0){
				l.setLimit_alarm(l.getLimit_alarms()+"/--");
			}else if(Math.abs(l.getLimit_alarms())==999999&&Math.abs(l.getFloor_alarms())!=999999&&Math.abs(l.getFloor_alarms())!=0||Math.abs(l.getLimit_alarms())==0&&Math.abs(l.getFloor_alarms())!=999999&&Math.abs(l.getFloor_alarms())!=0){
				l.setLimit_alarm("--/"+l.getFloor_alarms());
			}else{
				l.setLimit_alarm(l.getLimit_alarms()+"/"+l.getFloor_alarms());
			}
			if(Math.abs(l.getLimit_powers())==999999&&Math.abs(l.getFloor_powers())==999999||Math.abs(l.getLimit_powers())==0&&Math.abs(l.getFloor_powers())==0||Math.abs(l.getLimit_powers())==0&&Math.abs(l.getFloor_powers())==999999||Math.abs(l.getLimit_powers())==999999&&Math.abs(l.getFloor_powers())==0){
				l.setLimit_power("--/--");
			}else if(Math.abs(l.getLimit_powers())!=999999&&Math.abs(l.getLimit_powers())!=0&&Math.abs(l.getFloor_powers())==999999||Math.abs(l.getLimit_powers())!=999999&&Math.abs(l.getLimit_powers())!=0&&Math.abs(l.getFloor_powers())==0){
				l.setLimit_power(l.getLimit_powers()+"/--");
			}else if(Math.abs(l.getLimit_powers())==999999&&Math.abs(l.getFloor_powers())!=999999&&Math.abs(l.getFloor_powers())!=0||Math.abs(l.getLimit_powers())==999999&&Math.abs(l.getFloor_powers())!=0&&Math.abs(l.getFloor_powers())!=0){
				l.setLimit_power("--/"+l.getFloor_powers());
			}else{
				l.setLimit_power(l.getLimit_powers()+"/"+l.getFloor_powers());
			}
			if(Math.abs(l.getLimit_repowers())==999999&&Math.abs(l.getFloor_repowers())==999999||Math.abs(l.getLimit_repowers())==0&&Math.abs(l.getFloor_repowers())==0||Math.abs(l.getLimit_repowers())==0&&Math.abs(l.getFloor_repowers())==999999||Math.abs(l.getLimit_repowers())==999999&&Math.abs(l.getFloor_repowers())==0){
				l.setLimit_repower("--/--");
			}else if(Math.abs(l.getLimit_repowers())!=999999&&Math.abs(l.getLimit_repowers())!=0&&Math.abs(l.getFloor_repowers())==999999||Math.abs(l.getLimit_repowers())!=999999&&Math.abs(l.getLimit_repowers())!=0&&Math.abs(l.getFloor_repowers())==0){
				l.setLimit_repower(l.getLimit_repowers()+"/--");
			}else if(Math.abs(l.getLimit_repowers())==999999&&Math.abs(l.getFloor_repowers())!=999999&&Math.abs(l.getFloor_repowers())!=0||Math.abs(l.getLimit_repowers())==0&&Math.abs(l.getFloor_repowers())!=999999&&Math.abs(l.getFloor_repowers())!=0){
				l.setLimit_repower("--/"+l.getFloor_repowers());
			}else{
				l.setLimit_repower(l.getLimit_repowers()+"/"+l.getFloor_repowers());
			}
			AnalogStatistics a = map.get(l.getIp()+l.getSensorId()+l.getSensor_type());
			if(a==null){
				a = new AnalogStatistics();
				a.setIp(l.getIp());
				a.setSensorId(l.getSensorId());
				a.setSensor_type(l.getSensor_type());
				a.setAlais(l.getAlais());
				a.setSensortype(l.getSensortype());
				a.setMaxvalues(l.getMaxvalues());
				a.setMaxtime(l.getMaxtime());
				a.setMinvalue(l.getMinvalue());
				a.setMintime(l.getMintime());
				a.setUnit(l.getUnit());
				a.setLimit_alarm(l.getLimit_alarm());
				a.setLimit_power(l.getLimit_power());
				a.setLimit_repower(l.getLimit_repower());
				a.setPosition(l.getPosition());
				map.put(l.getIp()+l.getSensorId()+l.getSensor_type(), a);
				statistics.add(a);
			}
			a.getList().add(l);
			if(l.getMaxvalues()>=a.getMaxvalues()){
				a.setMaxvalues(l.getMaxvalues());
				a.setMaxtime(l.getMaxtime());
			}
			if(l.getMinvalue()<=a.getMinvalue()){
				a.setMinvalue(l.getMinvalue());
				a.setMintime(l.getMintime());
			}
		}
		for(AnalogStatistics l : statistics){
				List<AnalogStatistics> list2 = l.getList();
				double avgcount = 0;
				for(int i=0;i<list2.size();i++){
					list.get(i).setId(i+1);
					avgcount = avgcount+list2.get(i).getAvgvalue();
				}
				l.setAvgvalue(saveOneBitTwoRound(avgcount/list2.size()));
			}
		return statistics;
	}
	
	public int getcount(NameTime nameTime){
		return dao.getcount(nameTime);
	}
	
	public static Double saveOneBitTwoRound(Double d){
        BigDecimal bd = new BigDecimal(d);
        Double tem = bd.setScale(2,BigDecimal.ROUND_FLOOR).doubleValue();
        return tem;
    }
	
	private String gettime() throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:00");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		String time = dateFormat.format(new Date());
		String time3 =format.format(new Date());
		long a = format.parse(time3).getTime();
		long b = dateFormat.parse(time).getTime();
		long c = a-10000;
		if(a==b){
			Date date = new Date(c);
			
			return format.format(date);
		}
		return time3;
	}
	
	private String getDay() throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:00");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy_MM_dd");
		Date day = new Date();
		String time = dateFormat.format(new Date());
		String time3 =format.format(new Date());
		long a = format.parse(time3).getTime();
		long b = dateFormat.parse(time).getTime();
		if(a==b){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(day);
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			day = calendar.getTime();
			return format1.format(day);
		}
		
		return format1.format(day);
	}
}
