package com.cm.service;


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

import com.alibaba.fastjson.JSON;
import com.cm.dao.SwitchEfficiencyDao;
import com.cm.entity.Coalmine;
import com.cm.entity.SwitchEfficiency;
import com.cm.entity.vo.NameTime;


@Service("switchEfficiencyService")
public class SwitchEfficiencyService {
	
	@Autowired
	private SwitchEfficiencyDao dao;
	
	@SuppressWarnings("unchecked")
	public List<SwitchEfficiency> getdata() throws ParseException{
		String tablename = "t_coalMine_"+getDay();
		String time = gettime();
		List<SwitchEfficiency> list = dao.getsensors(tablename,time);
		SwitchEfficiency efficiency = new SwitchEfficiency();
		for(SwitchEfficiency l:list){
			l.setStatistictime(gettime());
			Map<Integer, String> powers = JSON.parseObject(l.getPowers(), Map.class);
			if(l.getSensor_type()==56){
				l.setPowers("--");
			}else{
				l.setPowers(powers.get(l.getPower()));
			}
			efficiency.setIp(l.getIp());
			efficiency.setSensorId(l.getSensorId());
			efficiency.setSensor_type(l.getSensor_type());
			SwitchEfficiency efficiency2 = getBasicdata(efficiency,tablename,time);
			if(efficiency2!=null){
				l.setPowercnt(efficiency2.getPowercnt());
				l.setSwitcheff(efficiency2.getSwitcheff());
				l.setSwitchtime(efficiency2.getSwitchtime());
				l.setRemark("正常");
			}else{
				l.setRemark("无数据上报");
			}
			
		}
		
		return list;
	}
	public SwitchEfficiency getBasicdata(SwitchEfficiency efficiency,String tablename,String time) throws ParseException{
		SwitchEfficiency switchEfficiency = new SwitchEfficiency();
		List<Coalmine> list = dao.getBasicdata(efficiency,tablename,time);
		if(list.size()>0){
			long a = 0;
			int b = 0;
			for(int i=0;i<list.size()-1;i++){
				int k = (int) list.get(i).getValue();
				int j = (int) list.get(i+1).getValue();
				if(efficiency.getSensor_type()==56){
					if(k==0){
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						long time1 = format.parse(list.get(i).getResponsetime()).getTime();
						long time2 = format.parse(list.get(i+1).getResponsetime()).getTime();
						long time3 = Math.abs(time1-time2);
						a = a+time3;
					}
				}else{
					if(k==1){
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						long time1 = format.parse(list.get(i).getResponsetime()).getTime();
						long time2 = format.parse(list.get(i+1).getResponsetime()).getTime();
						long time3 = Math.abs(time1-time2);
						a = a+time3;
					}
				}
				if(k!=j&&k!=2&&j!=2){
					b = b+1;
				}
			}
			int c = 0;
			if(b==0){
				if(list.get(0).getValue()==0){
					c = 0;
				}else if(list.get(0).getValue()==1){
					c = 60;
				}
			}else{
				c = (int) (a/60000);
			}
			
			double d = c;
			switchEfficiency.setPowercnt(b);
			switchEfficiency.setSwitcheff(d/60);
			switchEfficiency.setSwitchtime(c+"分钟");
			return switchEfficiency;
		}else{
			return null;
		}
		
	}

	public int isexisting(SwitchEfficiency efficiency){
		return dao.isexisting(efficiency);
	}
	
	public void adddata(List<SwitchEfficiency> list){
		dao.adddata(list);
	}
	
	
	public List<SwitchEfficiency> getall(NameTime nameTime) throws ParseException{
		String time = nameTime.getStarttime();
		String daytime = time.split(" ")[0];
		List<String> timelist = new ArrayList<String>();
		String times = null;
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		String time3 = format1.format(new Date());
		Date date3 = format1.parse(time3);
		Date date4 = format1.parse(time);
		if(date4.getTime()<date3.getTime()){
			for(int i=0;i<=23;i++){
				if(i<10){
					times = daytime+" 0"+i+":00:00";
					timelist.add(times);
				}else{
					times = daytime+" "+i+":00:00";
					timelist.add(times);
				}
			}
		}else if(date4.getTime()==date3.getTime()){
			Calendar c = Calendar.getInstance();
			 int a = c.get(Calendar.HOUR_OF_DAY);
			 for(int i=0;i<=a;i++){
				 if(i<10){
						times = daytime+" 0"+i+":00:00";
						timelist.add(times);
					}else{
						times = daytime+" "+i+":00:00";
						timelist.add(times);
					}
				}
		}
		Map<String, SwitchEfficiency> map = new HashMap<String, SwitchEfficiency>();
		List<SwitchEfficiency> efficiencies = new ArrayList<SwitchEfficiency>();
		List<SwitchEfficiency> list = dao.getall(nameTime);
		 int id = 0;
		 int sensor_id = 0;//开关量id
		 String alais = null;//别名
		 String ip = null;
		 int sensorId = 0;
		 int sensor_type = 0;
		 String sensortype = null;//类型
		 String position = null;//位置
		 String powers = null;
		
		for(SwitchEfficiency l:list){
			int a = (int) (l.getSwitcheff()*100);
			double b = a;
			l.setSwitcheff(b);
			id = l.getId();
			sensor_id = l.getSensor_id();
			alais = l.getAlais();
			ip = l.getIp();
			sensorId = l.getSensorId();
			sensor_type = l.getSensor_type();
			sensortype = l.getSensortype();
			if(l.getPosition()==null){
				l.setPosition("未配置位置");
			}
			position = l.getPosition();
			powers = l.getPowers();
			map.put(l.getStatistictime(), l);
		}
		for(String s:timelist){
			SwitchEfficiency a =map.get(s);
			if(a!=null){
				if(a.getSwitchtime()==null){
					a.setSwitchtime("--");
				}
				efficiencies.add(a);
			}
			if(a==null){
				a = new SwitchEfficiency();
				a.setId(id);
				a.setAlais(alais);
				a.setIp(ip);
				a.setSensorId(sensorId);
				a.setSensor_id(sensor_id);
				a.setSensor_type(sensor_type);
				a.setSensortype(sensortype);
				a.setPosition(position);
				a.setStatistictime(s);
				a.setPowers(powers);
				a.setRemark("无数据上报");
				a.setSwitchtime("--");
				efficiencies.add(a);
			}
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
		String time2 = format.format(new Date());
		Date date = format.parse(time);
		Date date2 = format.parse(time2);
		if(date.getTime()<=date2.getTime()){
			return efficiencies;
		}else{
			return list;
		}
		
	}
	
	private String gettime() throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:00:00");
		String time3 =format.format(new Date());
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
