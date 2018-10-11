package com.cm.service;

import java.text.DateFormat;
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
import com.cm.dao.EnvclassesDao;
import com.cm.dao.SwitchStateChangeDao;
import com.cm.entity.Coalmine;
import com.cm.entity.EnvClasses;
import com.cm.entity.SwitchStateChange;
import com.cm.entity.vo.NameTime;
import com.cm.entity.vo.SwitchStateChangeVo;

@Service("switchStateChangeService")
public class SwitchStateChangeService {
	
	@Autowired
	private SwitchStateChangeDao dao;
	
	@Autowired
	private EnvclassesDao envclassesDao;
	
	@SuppressWarnings("unchecked")
	public List<SwitchStateChangeVo> getdata() throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<EnvClasses> classes = envclassesDao.getAll();
		String tablename = "t_coalMine_"+getDay();
		List<SwitchStateChangeVo> list = new ArrayList<SwitchStateChangeVo>();
		List<Coalmine> list2 = dao.getBasicdata(tablename);
		Map<Integer, List<Coalmine>> map = new HashMap<Integer, List<Coalmine>>();
		for(Coalmine l:list2){
			List<Coalmine> list3 = map.get(l.getDev_id());
			if(list3==null){
				list3 = new ArrayList<Coalmine>();
				map.put(l.getDev_id(), list3);
			}
			list3.add(l);
		}
		for(List<Coalmine> value:map.values()){
			if(value.size()>=2){
				int count = 0;
					for(int i=0;i<value.size()-1;i++){
						int c = (int) value.get(i).getValue();
						int d = (int) value.get(i+1).getValue();
						Coalmine a = value.get(i);
						Coalmine coalmine = value.get(i+1);
						Map<Integer, String> powers = JSON.parseObject(coalmine.getResponse(), Map.class);
						if(a.getType()==56||a.getType()==53){
						if(i==0&&c==0&&a.getStatus()!=5){
							SwitchStateChangeVo changeVo = new SwitchStateChangeVo();
							changeVo.setDev_id(a.getDev_id());
							changeVo.setDevid(a.getDevid());
							changeVo.setFilltime(a.getFilltime());
							changeVo.setIp(a.getIp());
							changeVo.setType(a.getType());
							changeVo.setResponsetime(a.getResponsetime());
							if(classes!=null&&classes.size()>0){
								long starttimelong = format.parse(changeVo.getResponsetime()).getTime();
								String startday = changeVo.getResponsetime().split(" ")[0];
								for(EnvClasses s:classes){
									String start = startday+" "+s.getStart()+":00";
									String end = startday+" "+s.getEnd()+":00";
									long startlong = format.parse(start).getTime();
									long endlong = format.parse(end).getTime();
									if(s.getStatus()==1&&starttimelong>startlong&&starttimelong<=endlong){
										changeVo.setClassname(s.getName());
										changeVo.setClassstart(s.getStart()+":00");
										changeVo.setClassend(s.getEnd()+":00");
									}
									if(s.getStatus()==2&&starttimelong<=startlong||s.getStatus()==2&&starttimelong>endlong){
										changeVo.setClassname(s.getName());
										changeVo.setClassstart(s.getStart()+":00");
										changeVo.setClassend(s.getEnd()+":00");
									}
								}
							}
							changeVo.setValue((int) a.getValue());
							changeVo.setStatechange(powers.get((int)a.getValue()));
							list.add(changeVo);
							count++;
						}
						if(c!=d){
							SwitchStateChangeVo changeVo = new SwitchStateChangeVo();
							changeVo.setDev_id(coalmine.getDev_id());
							changeVo.setDevid(coalmine.getDevid());
							changeVo.setFilltime(coalmine.getFilltime());
							changeVo.setIp(coalmine.getIp());
							changeVo.setType(coalmine.getType());
							changeVo.setResponsetime(coalmine.getResponsetime());
							if(classes!=null&&classes.size()>0){
								long starttimelong = format.parse(changeVo.getResponsetime()).getTime();
								String startday = changeVo.getResponsetime().split(" ")[0];
								for(EnvClasses s:classes){
									String start = startday+" "+s.getStart()+":00";
									String end = startday+" "+s.getEnd()+":00";
									long startlong = format.parse(start).getTime();
									long endlong = format.parse(end).getTime();
									if(s.getStatus()==1&&starttimelong>startlong&&starttimelong<=endlong){
										changeVo.setClassname(s.getName());
										changeVo.setClassstart(s.getStart()+":00");
										changeVo.setClassend(s.getEnd()+":00");
									}
									if(s.getStatus()==2&&starttimelong<=startlong||s.getStatus()==2&&starttimelong>endlong){
										changeVo.setClassname(s.getName());
										changeVo.setClassstart(s.getStart()+":00");
										changeVo.setClassend(s.getEnd()+":00");
									}
								}
							}
							changeVo.setValue((int) coalmine.getValue());
							changeVo.setStatechange(powers.get((int)coalmine.getValue()));
							list.add(changeVo);
							count++;
						}
						if(i==value.size()-2&&count>0&&list.get(list.size()-1>=0?list.size()-1:0).getValue()==0&&coalmine.getResponsetime()!=list.get(list.size()-1>=0?list.size()-1:0).getResponsetime()){
							SwitchStateChangeVo changeVo = new SwitchStateChangeVo();
							changeVo.setDev_id(coalmine.getDev_id());
							changeVo.setDevid(coalmine.getDevid());
							changeVo.setFilltime(coalmine.getFilltime());
							changeVo.setIp(coalmine.getIp());
							changeVo.setType(coalmine.getType());
							changeVo.setResponsetime(coalmine.getResponsetime());
							if(classes!=null&&classes.size()>0){
								long starttimelong = format.parse(changeVo.getResponsetime()).getTime();
								String startday = changeVo.getResponsetime().split(" ")[0];
								for(EnvClasses s:classes){
									String start = startday+" "+s.getStart()+":00";
									String end = startday+" "+s.getEnd()+":00";
									long startlong = format.parse(start).getTime();
									long endlong = format.parse(end).getTime();
									if(s.getStatus()==1&&starttimelong>startlong&&starttimelong<=endlong){
										changeVo.setClassname(s.getName());
										changeVo.setClassstart(s.getStart()+":00");
										changeVo.setClassend(s.getEnd()+":00");
									}
									if(s.getStatus()==2&&starttimelong<=startlong||s.getStatus()==2&&starttimelong>endlong){
										changeVo.setClassname(s.getName());
										changeVo.setClassstart(s.getStart()+":00");
										changeVo.setClassend(s.getEnd()+":00");
									}
								}
							}
							changeVo.setValue(1);
							changeVo.setStatechange(powers.get(changeVo.getValue()));
							list.add(changeVo);
							count = 0;
						}
					}else{
						if(i==0&&c==1&&a.getStatus()!=5){
							SwitchStateChangeVo changeVo = new SwitchStateChangeVo();
							changeVo.setDev_id(a.getDev_id());
							changeVo.setDevid(a.getDevid());
							changeVo.setFilltime(a.getFilltime());
							changeVo.setIp(a.getIp());
							changeVo.setType(a.getType());
							changeVo.setResponsetime(a.getResponsetime());
							if(classes!=null&&classes.size()>0){
								long starttimelong = format.parse(changeVo.getResponsetime()).getTime();
								String startday = changeVo.getResponsetime().split(" ")[0];
								for(EnvClasses s:classes){
									String start = startday+" "+s.getStart()+":00";
									String end = startday+" "+s.getEnd()+":00";
									long startlong = format.parse(start).getTime();
									long endlong = format.parse(end).getTime();
									if(s.getStatus()==1&&starttimelong>startlong&&starttimelong<=endlong){
										changeVo.setClassname(s.getName());
										changeVo.setClassstart(s.getStart()+":00");
										changeVo.setClassend(s.getEnd()+":00");
									}
									if(s.getStatus()==2&&starttimelong<=startlong||s.getStatus()==2&&starttimelong>endlong){
										changeVo.setClassname(s.getName());
										changeVo.setClassstart(s.getStart()+":00");
										changeVo.setClassend(s.getEnd()+":00");
									}
								}
							}
							changeVo.setValue((int) a.getValue());
							changeVo.setStatechange(powers.get((int)a.getValue()));
							list.add(changeVo);
							count++;
						}
						if(c!=d){
							SwitchStateChangeVo changeVo = new SwitchStateChangeVo();
							changeVo.setDev_id(coalmine.getDev_id());
							changeVo.setDevid(coalmine.getDevid());
							changeVo.setFilltime(coalmine.getFilltime());
							changeVo.setIp(coalmine.getIp());
							changeVo.setType(coalmine.getType());
							changeVo.setResponsetime(coalmine.getResponsetime());
							if(classes!=null&&classes.size()>0){
								long starttimelong = format.parse(changeVo.getResponsetime()).getTime();
								String startday = changeVo.getResponsetime().split(" ")[0];
								for(EnvClasses s:classes){
									String start = startday+" "+s.getStart()+":00";
									String end = startday+" "+s.getEnd()+":00";
									long startlong = format.parse(start).getTime();
									long endlong = format.parse(end).getTime();
									if(s.getStatus()==1&&starttimelong>startlong&&starttimelong<=endlong){
										changeVo.setClassname(s.getName());
										changeVo.setClassstart(s.getStart()+":00");
										changeVo.setClassend(s.getEnd()+":00");
									}
									if(s.getStatus()==2&&starttimelong<=startlong||s.getStatus()==2&&starttimelong>endlong){
										changeVo.setClassname(s.getName());
										changeVo.setClassstart(s.getStart()+":00");
										changeVo.setClassend(s.getEnd()+":00");
									}
								}
							}
							changeVo.setValue((int) coalmine.getValue());
							changeVo.setStatechange(powers.get((int)coalmine.getValue()));
							list.add(changeVo);
							count++;
						}
						if(i==value.size()-2&&count>0&&list.get(list.size()-1>0?list.size()-1:0).getValue()==1&&coalmine.getResponsetime()!=list.get(list.size()-1>=0?list.size()-1:0).getResponsetime()){
							SwitchStateChangeVo changeVo = new SwitchStateChangeVo();
							changeVo.setDev_id(coalmine.getDev_id());
							changeVo.setDevid(coalmine.getDevid());
							changeVo.setFilltime(coalmine.getFilltime());
							changeVo.setIp(coalmine.getIp());
							changeVo.setType(coalmine.getType());
							changeVo.setResponsetime(coalmine.getResponsetime());
							if(classes!=null&&classes.size()>0){
								long starttimelong = format.parse(changeVo.getResponsetime()).getTime();
								String startday = changeVo.getResponsetime().split(" ")[0];
								for(EnvClasses s:classes){
									String start = startday+" "+s.getStart()+":00";
									String end = startday+" "+s.getEnd()+":00";
									long startlong = format.parse(start).getTime();
									long endlong = format.parse(end).getTime();
									if(s.getStatus()==1&&starttimelong>startlong&&starttimelong<=endlong){
										changeVo.setClassname(s.getName());
										changeVo.setClassstart(s.getStart()+":00");
										changeVo.setClassend(s.getEnd()+":00");
									}
									if(s.getStatus()==2&&starttimelong<=startlong||s.getStatus()==2&&starttimelong>endlong){
										changeVo.setClassname(s.getName());
										changeVo.setClassstart(s.getStart()+":00");
										changeVo.setClassend(s.getEnd()+":00");
									}
								}
							}
							changeVo.setValue(0);
							changeVo.setStatechange(powers.get(changeVo.getValue()));
							list.add(changeVo);
							count = 0;
						}
					}
				}
			}
		}
		return list;
	}
	
	public int isexisting(SwitchStateChangeVo changeVo){
		return dao.isexisting(changeVo);
	}
	
	public void adddata(List<SwitchStateChangeVo> list){
		dao.add(list);
	}
	
	@SuppressWarnings("unchecked")
	public List<SwitchStateChange> getall(NameTime nameTime) throws ParseException{
		List<SwitchStateChangeVo> list = dao.getall(nameTime);
		List<SwitchStateChange> list2 = new ArrayList<SwitchStateChange>();
		Map<Integer, SwitchStateChange> map = new HashMap<Integer, SwitchStateChange>();
		for(SwitchStateChangeVo l:list){
			SwitchStateChange change = map.get(l.getDev_id());
			if(l.getPosition()==null){
				l.setPosition("未配置位置");
			}
			Map<Integer, String> powers = JSON.parseObject(l.getAlarmstatus(), Map.class);
			l.setStatechange(powers.get(l.getValue()));
			if(change==null){
				change = new SwitchStateChange();
				change.setSensor_id(l.getDev_id());
				change.setAlais(l.getAlais());
				change.setSensortype(l.getSensortype());
				change.setPosition(l.getPosition());
				change.setSensor_type(l.getType());
				
				if(l.getType()==56||l.getType()==53||l.getType()==71||l.getAlarm_status()<0){
					change.setAlarmpower("--");
				}else{
					if(powers.get(l.getAlarm_status())!=null){
						change.setAlarmpower(powers.get(l.getAlarm_status()));
					}else{
						change.setAlarmpower("--");
					}
				}
				map.put(l.getDev_id(), change);
				list2.add(change);
			}
			change.setStatechangecnt(change.getStatechangecnt()+1);
			change.getList().add(l);
		}
		for(SwitchStateChange l:list2){
			List<SwitchStateChangeVo> list3 = l.getList();
			if(list3!=null){
				long a = 0;
				if(l.getSensor_type()==56){
					for(int i=0;i<list3.size()-1;i++){
						if(list3.get(i).getValue()==0){
							a = a+countLongDvalue(list3.get(i).getResponsetime(),list3.get(i+1).getResponsetime());
						}
					}
				}else{
					for(int i=0;i<list3.size()-1;i++){
						if(list3.get(i).getValue()==1){
							a = a+countLongDvalue(list3.get(i).getResponsetime(),list3.get(i+1).getResponsetime());
						}
					}
				}
				l.setTimes(countTimeCast(a));
				if(list3.size()>=1){
					String first= list3.get(0).getStatechange()+"/"+list3.get(0).getResponsetime();
					l.setFirst(first);
				}
				if(list3.size()>=2){
					String second= list3.get(1).getStatechange()+"/"+list3.get(1).getResponsetime();
					l.setSecond(second);
				}
			}
		}
		return list2;
	}
	
	
	private String getDay() throws ParseException{
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy_MM_dd");
		Date day = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(day);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		day = calendar.getTime();
		return format1.format(day);
		
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
