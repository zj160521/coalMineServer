package com.cm.service;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.cm.dao.AnalogStatementDao;
import com.cm.entity.AnalogStatement;
import com.cm.entity.Analoginfo;
import com.cm.entity.vo.AnaloginfoQuery;
import com.cm.entity.vo.HistoryData;
import com.cm.entity.vo.NameTime;
import com.cm.entity.vo.SensorReportsVo;

@Service("analogStatementService")
public class AnalogStatementService {
	
	@Autowired
	private AnalogStatementDao dao;

	

	public List<SensorReportsVo> getAnasByDay(NameTime nameTime) throws ParseException{
		List<SensorReportsVo> statlist = dao.getAnasByDay(nameTime);
		for(SensorReportsVo s:statlist){
			if(s.getPosition()==null){
				s.setPosition("未配置位置");
			}
			if(Math.abs(s.getLimit_alarms())==999999&&Math.abs(s.getFloor_alarms())==999999||Math.abs(s.getLimit_alarms())==0&&Math.abs(s.getFloor_alarms())==0||Math.abs(s.getLimit_alarms())==0&&Math.abs(s.getFloor_alarms())==999999||Math.abs(s.getLimit_alarms())==999999&&Math.abs(s.getFloor_alarms())==0){
				s.setLimit_alarm("--/--");
			}else if(Math.abs(s.getLimit_alarms())!=999999&&Math.abs(s.getLimit_alarms())!=0&&Math.abs(s.getFloor_alarms())==999999||Math.abs(s.getLimit_alarms())!=999999&&Math.abs(s.getLimit_alarms())!=0&&Math.abs(s.getFloor_alarms())==0){
				s.setLimit_alarm(s.getLimit_alarms()+"/--");
			}else if(Math.abs(s.getLimit_alarms())==999999&&Math.abs(s.getFloor_alarms())!=999999&&Math.abs(s.getFloor_alarms())!=0||Math.abs(s.getLimit_alarms())==0&&Math.abs(s.getFloor_alarms())!=999999&&Math.abs(s.getFloor_alarms())!=0){
				s.setLimit_alarm("--/"+s.getFloor_alarms());
			}else{
				s.setLimit_alarm(s.getLimit_alarms()+"/"+s.getFloor_alarms());
			}
			if(Math.abs(s.getLimit_powers())==999999&&Math.abs(s.getFloor_powers())==999999||Math.abs(s.getLimit_powers())==0&&Math.abs(s.getFloor_powers())==0||Math.abs(s.getLimit_powers())==0&&Math.abs(s.getFloor_powers())==999999||Math.abs(s.getLimit_powers())==999999&&Math.abs(s.getFloor_powers())==0){
				s.setLimit_power("--/--");
			}else if(Math.abs(s.getLimit_powers())!=999999&&Math.abs(s.getLimit_powers())!=0&&Math.abs(s.getFloor_powers())==999999||Math.abs(s.getLimit_powers())!=999999&&Math.abs(s.getLimit_powers())!=0&&Math.abs(s.getFloor_powers())==0){
				s.setLimit_power(s.getLimit_powers()+"/--");
			}else if(Math.abs(s.getLimit_powers())==999999&&Math.abs(s.getFloor_powers())!=999999&&Math.abs(s.getFloor_powers())!=0||Math.abs(s.getLimit_powers())==0&&Math.abs(s.getFloor_powers())!=999999&&Math.abs(s.getFloor_powers())!=0){
				s.setLimit_power("--/"+s.getFloor_powers());
			}else{
				s.setLimit_power(s.getLimit_powers()+"/"+s.getFloor_powers());
			}
			if(Math.abs(s.getLimit_repowers())==999999&&Math.abs(s.getFloor_repowers())==999999||Math.abs(s.getLimit_repowers())==0&&Math.abs(s.getFloor_repowers())==0||Math.abs(s.getLimit_repowers())==0&&Math.abs(s.getFloor_repowers())==999999||Math.abs(s.getLimit_repowers())==999999&&Math.abs(s.getFloor_repowers())==0){
				s.setLimit_repower("--/--");
			}else if(Math.abs(s.getLimit_repowers())!=999999&&Math.abs(s.getLimit_repowers())!=0&&Math.abs(s.getFloor_repowers())==999999||Math.abs(s.getLimit_repowers())!=999999&&Math.abs(s.getLimit_repowers())!=0&&Math.abs(s.getFloor_repowers())==0){
				s.setLimit_repower(s.getLimit_repowers()+"/--");
			}else if(Math.abs(s.getLimit_repowers())==999999&&Math.abs(s.getFloor_repowers())!=999999&&Math.abs(s.getFloor_repowers())!=0||Math.abs(s.getLimit_repowers())==0&&Math.abs(s.getFloor_repowers())!=999999&&Math.abs(s.getFloor_repowers())!=0){
				s.setLimit_repower("--/"+s.getFloor_repowers());
			}else{
				s.setLimit_repower(s.getLimit_repowers()+"/"+s.getFloor_repowers());
			}
		}
		return statlist;
	}
	
	
	public List<AnalogStatement> getAlerts(NameTime nameTime) throws ParseException {
		List<AnalogStatement> result = new ArrayList<AnalogStatement>();
		List<AnaloginfoQuery> list = dao.getAlerts(nameTime);
		HashMap<Integer, AnalogStatement> statmap = new HashMap<Integer, AnalogStatement>();
		long k = 0;
		double j = 0;
		for(AnaloginfoQuery s : list) {
			AnalogStatement a = statmap.get(s.getSensor_id());
			if (a == null) {
				a = new AnalogStatement();
				a.setAlais(s.getAlais());;
				a.setSensor_id(s.getSensor_id());
				a.setSensor_type(s.getSensor_type());
				a.setType(s.getSensortype());
				if(s.getPosition()==null){
					s.setPosition("未配置位置");
				}
				a.setPosition(s.getPosition());
				a.setUnit(s.getUnit());
				if(Math.abs(s.getLimit_alarm())==999999&&Math.abs(s.getFloor_alarm())==999999||Math.abs(s.getLimit_alarm())==0&&Math.abs(s.getFloor_alarm())==0||Math.abs(s.getLimit_alarm())==0&&Math.abs(s.getFloor_alarm())==999999||Math.abs(s.getLimit_alarm())==999999&&Math.abs(s.getFloor_alarm())==0){
					a.setLimit_alarm("--/--");
				}else if(Math.abs(s.getLimit_alarm())!=999999&&Math.abs(s.getLimit_alarm())!=0&&Math.abs(s.getFloor_alarm())==999999||Math.abs(s.getLimit_alarm())!=999999&&Math.abs(s.getLimit_alarm())!=0&&Math.abs(s.getFloor_alarm())==0){
					a.setLimit_alarm(s.getLimit_alarm()+"/--");
				}else if(Math.abs(s.getLimit_alarm())==999999&&Math.abs(s.getFloor_alarm())!=999999&&Math.abs(s.getFloor_alarm())!=0||Math.abs(s.getLimit_alarm())==0&&Math.abs(s.getFloor_alarm())!=999999&&Math.abs(s.getFloor_alarm())!=0){
					a.setLimit_alarm("--/"+s.getFloor_alarm());
				}else{
					a.setLimit_alarm(s.getLimit_alarm()+"/"+s.getFloor_alarm());
				}
				a.setMaxvalues(s.getMaxvalues());;
				a.setMaxtime(s.getMaxtime());
				a.setAvgvalue(s.getAvgvalue());
				a.setItype(s.getStatus());
				statmap.put(s.getSensor_id(), a);
				result.add(a);
				k = 0;
				j = 0;
			}
				a.getList().add(s);
				a.setAlerts(a.getAlerts()+1);
				if(s.getMaxvalues()>=a.getMaxvalues()){
					a.setMaxvalues(s.getMaxvalues());
					a.setMaxtime(s.getMaxtime());
				}
				k = a.getTimes();
				k = k+gettimes(s.getStarttime(), s.getEndtime());
				a.setAlertTimes(countTimeCast(k));
				a.setTimes(k);
				j = a.getAcgcnt();
				j = j+s.getAvgvalue();
				a.setAcgcnt(j);
				a.setAvgvalue(Math.rint(j/a.getAlerts()*100)/100);
				if(s.getMeasures()==null&&s.getMeasurestime()==null){
					s.setMeasures("暂未处理");
				}
				if(s.getPosition()==null){
					s.setPosition("未设置位置");
				}
				if(s.getPowerposition()==null){
					s.setPowerposition("未配置区域");
				}
				
		}
		for(AnalogStatement s:result){
			String[] first = new String[4];
			String[] second = new String[4];
			if(s.getList().size()>=1){
				AnaloginfoQuery firstAna = s.getList().get(0);
				first[0] = firstAna.getMaxvalues()+"/"+firstAna.getMaxtime()+"/"+firstAna.getTimes();
				first[1] = firstAna.getStarttime()+"~"+firstAna.getEndtime();
				if(firstAna.getMeasurestime()!=null){
					first[2] = firstAna.getMeasurestime()+"/"+firstAna.getMeasures();
				}else{
					first[2] = "暂未处理";
				}
				if(firstAna.getDebug()==0){
					first[3] = "正常";
				}else if(firstAna.getDebug()==1){
					first[3] = "欠压";
				}else if(firstAna.getDebug()==2){
					first[3] = "故障";
				}else if(firstAna.getDebug()==3){
					first[3] = "调校";
				}else if(firstAna.getDebug()==4){
					first[3] = "开机";
				}
				s.setFirst(first);
			}
			if(s.getList().size()>=2){
				AnaloginfoQuery firstAna = s.getList().get(1);
				second[0] = firstAna.getMaxvalues()+"/"+firstAna.getMaxtime()+"/"+firstAna.getTimes();
				second[1] = firstAna.getStarttime()+"~"+firstAna.getEndtime();
				if(firstAna.getMeasurestime()!=null){
					second[2] = firstAna.getMeasurestime()+"/"+firstAna.getMeasures();
				}else{
					second[2] = "暂未处理";
				}
				if(firstAna.getDebug()==0){
					second[3] = "正常";
				}else if(firstAna.getDebug()==1){
					second[3] = "欠压";
				}else if(firstAna.getDebug()==2){
					second[3] = "故障";
				}else if(firstAna.getDebug()==3){
					second[3] = "调校";
				}else if(firstAna.getDebug()==4){
					second[3] = "开机";
				}
				s.setSecond(second);
			}
		}
		return result;
	}
	public long gettimes(String time1,String time2) throws ParseException{
		long a=0;
		if(time1!=null&&time2!=null){
			a = countLongDvalue(time1, time2);
		}else if(time1!=null&&time2==null){
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time3 =format.format(new Date());
			a = countLongDvalue(time1, time3);
		}
		return a;
	}
	
	public String getTimecount(NameTime nameTime) throws ParseException{
		List<AnaloginfoQuery> list = dao.getTime(nameTime);
		long time = 0;
		for(AnaloginfoQuery l:list){
			if(l.getEndtime()!=null&&l.getStarttime()!=null){
				time = time+countLongDvalue(l.getStarttime(), l.getEndtime());
			}else if(l.getStarttime()!=null&&l.getEndtime()==null){
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String time3 =format.format(new Date());
				time = time+countLongDvalue(l.getStarttime(), time3);
			}
			
		}
		String secondTime = countTimeCast(time);
		return secondTime;
	}
	
	public List<AnalogStatement> getpowers(NameTime nameTime) throws ParseException{
		List<AnalogStatement> list = new ArrayList<AnalogStatement>();
		List<AnaloginfoQuery> list2 = dao.getPowers(nameTime);
		Map<Integer, AnalogStatement> map = new HashMap<Integer, AnalogStatement>();
		long k = 0;
		double j = 0;
		for(AnaloginfoQuery s:list2){
			AnalogStatement a = map.get(s.getSensor_id());
			if(a==null){
				a = new AnalogStatement();
				a.setAlais(s.getAlais());;
				a.setSensor_id(s.getSensor_id());
				a.setSensor_type(s.getSensor_type());
				a.setType(s.getSensortype());
				if(s.getPosition()==null){
					s.setPosition("未配置位置");
				}
				a.setPosition(s.getPosition());
				a.setUnit(s.getUnit());
				if(Math.abs(s.getLimit_power())==999999&&Math.abs(s.getFloor_power())==999999||Math.abs(s.getLimit_power())==0&&Math.abs(s.getFloor_power())==0||Math.abs(s.getLimit_power())==0&&Math.abs(s.getFloor_power())==999999||Math.abs(s.getLimit_power())==999999&&Math.abs(s.getFloor_power())==0){
					a.setLimit_power("--/--");
				}else if(Math.abs(s.getLimit_power())!=999999&&Math.abs(s.getLimit_power())!=0&&Math.abs(s.getFloor_power())==999999||Math.abs(s.getLimit_power())!=999999&&Math.abs(s.getLimit_power())!=0&&Math.abs(s.getFloor_power())==0){
					a.setLimit_power(s.getLimit_power()+"/--");
				}else if(Math.abs(s.getLimit_power())==999999&&Math.abs(s.getFloor_power())!=999999&&Math.abs(s.getFloor_power())!=0||Math.abs(s.getLimit_power())==0&&Math.abs(s.getFloor_power())!=999999&&Math.abs(s.getFloor_power())!=0){
					a.setLimit_power("--/"+s.getFloor_power());
				}else{
					a.setLimit_power(s.getLimit_power()+"/"+s.getFloor_power());
				}
				if(Math.abs(s.getLimit_repower())==999999&&Math.abs(s.getFloor_repower())==999999||Math.abs(s.getLimit_repower())==0&&Math.abs(s.getFloor_repower())==0||Math.abs(s.getLimit_repower())==0&&Math.abs(s.getFloor_repower())==999999||Math.abs(s.getLimit_repower())==999999&&Math.abs(s.getFloor_repower())==0){
					a.setLimit_repower("--/--");
				}else if(Math.abs(s.getLimit_repower())!=999999&&Math.abs(s.getLimit_repower())!=0&&Math.abs(s.getFloor_repower())==999999||Math.abs(s.getLimit_repower())!=999999&&Math.abs(s.getLimit_repower())!=0&&Math.abs(s.getFloor_repower())==0){
					a.setLimit_repower(s.getLimit_repower()+"/--");
				}else if(Math.abs(s.getLimit_repower())==999999&&Math.abs(s.getFloor_repower())!=999999&&Math.abs(s.getFloor_repower())!=0||Math.abs(s.getLimit_repower())==0&&Math.abs(s.getFloor_repower())!=999999&&Math.abs(s.getFloor_repower())!=0){
					a.setLimit_repower("--/"+s.getFloor_repower());
				}else{
					a.setLimit_repower(s.getLimit_repower()+"/"+s.getFloor_repower());
				}
				a.setMaxvalues(s.getMaxvalues());;
				a.setMaxtime(s.getMaxtime());
				a.setAvgvalue(s.getAvgvalue());
				a.setItype(s.getStatus());
				map.put(s.getSensor_id(), a);
				list.add(a);
				k = 0;
				j = 0;
			}
			if(s.getFeedstatus()!=null){
				String[] strings = s.getFeedstatus().split(",");
				s.setFeedstatuslist(strings);
				s.setFeedstatus(null);
				a.getList().add(s);
			}else{
				a.getList().add(s);
			}
			a.setAlerts(a.getAlerts()+1);
			if(s.getMaxvalues()>=a.getMaxvalues()){
				a.setMaxvalues(s.getMaxvalues());
				a.setMaxtime(s.getMaxtime());
			}
			k = a.getTimes();
			k = k+gettimes(s.getStarttime(), s.getEndtime());
			a.setAlertTimes(countTimeCast(k));
			a.setTimes(k);
			j = a.getAcgcnt();
			j = j+s.getAvgvalue();
			a.setAcgcnt(j);
			a.setAvgvalue(Math.rint(j/a.getAlerts()*100)/100);
			if(s.getMeasures()==null&&s.getMeasurestime()==null){
				s.setMeasures("暂未处理");
			}
			if(s.getPosition()==null){
				s.setPosition("未配置位置");
			}
			if(s.getPowerposition()==null){
				s.setPowerposition("未配置区域");
			}
		}
		for(AnalogStatement s:list){
			if(s.getList().size()>=1){
				AnaloginfoQuery firstAna = s.getList().get(0);
				if(firstAna.getFeedstatuslist()!=null){
					String[] first = new String[firstAna.getFeedstatuslist().length+4];
					first[0] = firstAna.getMaxvalues()+"/"+firstAna.getMaxtime()+"/"+firstAna.getTimes();
					first[1] = firstAna.getStarttime()+"~"+firstAna.getEndtime();
					for(int i=0;i<firstAna.getFeedstatuslist().length;i++){
						first[i+2] = firstAna.getFeedstatuslist()[i]+"/"+firstAna.getTimes();
					}
					if(firstAna.getMeasurestime()!=null){
						first[firstAna.getFeedstatuslist().length+2] = firstAna.getMeasurestime()+"/"+firstAna.getMeasures();
					}else{
						first[firstAna.getFeedstatuslist().length+2] = "暂未处理";
					}
					if(firstAna.getDebug()==0){
						first[firstAna.getFeedstatuslist().length+3] = "正常";
					}else if(firstAna.getDebug()==1){
						first[firstAna.getFeedstatuslist().length+3] = "欠压";
					}else if(firstAna.getDebug()==2){
						first[firstAna.getFeedstatuslist().length+3] = "故障";
					}else if(firstAna.getDebug()==3){
						first[firstAna.getFeedstatuslist().length+3] = "调校";
					}else if(firstAna.getDebug()==4){
						first[firstAna.getFeedstatuslist().length+3] = "开机";
					}
					s.setFirst(first);
				}else{
					String[] first = new String[5];
					first[0] = firstAna.getMaxvalues()+"/"+firstAna.getMaxtime()+"/"+firstAna.getTimes();
					first[1] = firstAna.getStarttime()+"~"+firstAna.getEndtime();
					first[2] = "未配置断馈电器";
					if(firstAna.getMeasurestime()!=null){
						first[3] = firstAna.getMeasurestime()+"/"+firstAna.getMeasures();
					}else{
						first[3] = "暂未处理";
					}
					if(firstAna.getDebug()==0){
						first[4] = "正常";
					}else if(firstAna.getDebug()==1){
						first[4] = "欠压";
					}else if(firstAna.getDebug()==2){
						first[4] = "故障";
					}else if(firstAna.getDebug()==3){
						first[4] = "调校";
					}else if(firstAna.getDebug()==4){
						first[4] = "开机";
					}
					s.setFirst(first);
				}
			}
			if(s.getList().size()>=2){
				AnaloginfoQuery secondAna = s.getList().get(1);
				if(secondAna.getFeedstatuslist()!=null){
					String[] second = new String[secondAna.getFeedstatuslist().length+4];
					second[0] = secondAna.getMaxvalues()+"/"+secondAna.getMaxtime()+"/"+secondAna.getTimes();
					second[1] = secondAna.getStarttime()+"~"+secondAna.getEndtime();
					for(int i=0;i<secondAna.getFeedstatuslist().length;i++){
						second[i+2] = secondAna.getFeedstatuslist()[i]+"/"+secondAna.getTimes();
					}
					if(secondAna.getMeasurestime()!=null){
						second[secondAna.getFeedstatuslist().length+2] = secondAna.getMeasurestime()+"/"+secondAna.getMeasures();
					}else{
						second[secondAna.getFeedstatuslist().length+2] = "暂未处理";
					}
					if(secondAna.getDebug()==0){
						second[secondAna.getFeedstatuslist().length+3] = "正常";
					}else if(secondAna.getDebug()==1){
						second[secondAna.getFeedstatuslist().length+3] = "欠压";
					}else if(secondAna.getDebug()==2){
						second[secondAna.getFeedstatuslist().length+3] = "故障";
					}else if(secondAna.getDebug()==3){
						second[secondAna.getFeedstatuslist().length+3] = "调校";
					}else if(secondAna.getDebug()==4){
						second[secondAna.getFeedstatuslist().length+3] = "开机";
					}
					s.setSecond(second);
				}else{
					String[] second = new String[5];
					second[0] = secondAna.getMaxvalues()+"/"+secondAna.getMaxtime()+"/"+secondAna.getTimes();
					second[1] = secondAna.getStarttime()+"~"+secondAna.getEndtime();
					second[2] = "未配置断馈电器";
					if(secondAna.getMeasurestime()!=null){
						second[3] = secondAna.getMeasurestime()+"/"+secondAna.getMeasures();
					}else{
						second[3] = "暂未处理";
					}
					if(secondAna.getDebug()==0){
						second[4] = "正常";
					}else if(secondAna.getDebug()==1){
						second[4] = "欠压";
					}else if(secondAna.getDebug()==2){
						second[4] = "故障";
					}else if(secondAna.getDebug()==3){
						second[4] = "调校";
					}else if(secondAna.getDebug()==4){
						second[4] = "开机";
					}
					s.setSecond(second);
				}
			}
		}
		return list;
	}
	
	public List<AnalogStatement> getfeeds(NameTime nameTime) throws ParseException{
		List<AnalogStatement> list = new ArrayList<AnalogStatement>();
		List<AnaloginfoQuery> list2 = dao.getfeeds(nameTime);
		Map<Integer, AnalogStatement> map = new HashMap<Integer, AnalogStatement>();
		long k = 0;
		for(AnaloginfoQuery s:list2){
			AnalogStatement a = map.get(s.getSensor_id());
			if(a==null){
				a = new AnalogStatement();
				a.setAlais(s.getAlais());;
				a.setSensor_id(s.getSensor_id());
				a.setSensor_type(s.getSensor_type());
				a.setType(s.getSensortype());
				if(s.getPosition()==null){
					s.setPosition("未配置位置");
				}
				a.setPosition(s.getPosition());
				a.setPowerposition(s.getPowerposition());
				a.setItype(s.getStatus());
				map.put(s.getSensor_id(), a);
				list.add(a);
				k = 0;
			}
			a.getList().add(s);
			a.setAlerts(a.getAlerts()+1);
			k = a.getTimes();
			k = k+gettimes(s.getStarttime(), s.getEndtime());
			a.setAlertTimes(countTimeCast(k));
			a.setTimes(k);
			if(s.getMeasures()==null&&s.getMeasurestime()==null){
				s.setMeasures("暂未处理");
			}
			if(s.getPosition()==null){
				s.setPosition("未设置位置");
			}
			if(s.getPowerposition()==null){
				s.setPowerposition("未配置区域");
			}
			
		}
		for(AnalogStatement s:list){
			String[] first = new String[3];
			String[] second = new String[3];
			if(s.getList().size()>=1){
				AnaloginfoQuery firstAna = s.getList().get(0);
				first[0] = firstAna.getFeedstatus()+"/"+firstAna.getTimes()+"/"+firstAna.getStarttime()+"~"+firstAna.getEndtime();
				if(firstAna.getMeasurestime()!=null){
					first[1] = firstAna.getMeasurestime()+"/"+firstAna.getMeasures();
				}else{
					first[1] = "暂未处理";
				}
				if(firstAna.getDebug()==0){
					first[2] = "正常";
				}else if(firstAna.getDebug()==1){
					first[2] = "欠压";
				}else if(firstAna.getDebug()==2){
					first[2] = "故障";
				}else if(firstAna.getDebug()==3){
					first[2] = "调校";
				}else if(firstAna.getDebug()==4){
					first[2] = "开机";
				}
				s.setFirst(first);
			}
			if(s.getList().size()>=2){
				AnaloginfoQuery firstAna = s.getList().get(1);
				second[0] = firstAna.getFeedstatus()+"/"+firstAna.getTimes()+"/"+firstAna.getStarttime()+"~"+firstAna.getEndtime();
				if(firstAna.getMeasurestime()!=null){
					second[1] = firstAna.getMeasurestime()+"/"+firstAna.getMeasures();
				}else{
					second[1] = "暂未处理";
				}
				if(firstAna.getDebug()==0){
					second[2] = "正常";
				}else if(firstAna.getDebug()==1){
					second[2] = "欠压";
				}else if(firstAna.getDebug()==2){
					second[2] = "故障";
				}else if(firstAna.getDebug()==3){
					second[2] = "调校";
				}else if(firstAna.getDebug()==4){
					second[2] = "开机";
				}
				s.setSecond(second);
			}
		}
		return list;
	}
	
	public List<Analoginfo> getHistory(NameTime nameTime){
		return dao.getHistory(nameTime);
	}
	
	public List<HistoryData> getHistorys(NameTime nameTime){
		return dao.getHistorys(nameTime);
	}
	
	public int getcountHis(NameTime nameTime){
		return dao.getcountHis(nameTime);
	}
	
	public  long countLongDvalue(String time1, String time2) throws ParseException {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long longTime1 = format.parse(time1).getTime();
		long longTime2 = format.parse(time2).getTime();
		long time = Math.abs(longTime2-longTime1);
		return time;
	}
	
	public  String countTimeCast(long time) {
		
		int times = (int) (time/1000);
		int hours = (int) Math.floor(times/3600) ;
		int minutes =  (int) (Math.floor(times - hours * 3600)/ 60);
		int seconds = times - hours * 3600  - minutes * 60;
		String timeString1 = null;
		String timeString2 = null;
		
		timeString1 = seconds < 10 ? hours + " 小时 0" + minutes + " 分 0"+ seconds + "秒" 
				: hours + " 小时 0" + minutes + " 分 " + seconds + "秒";
		timeString2 = seconds < 10 ? hours + " 小时 " + minutes + " 分 0"+ seconds + "秒" 
				: hours + " 小时 " + minutes + " 分 " + seconds + "秒";

		String timeString = minutes < 10 ? timeString1 : timeString2;

		return timeString;
	}
}
