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

import util.UtilMethod;

import com.cm.dao.AnaloginfoDao;
import com.cm.entity.AnalogQuery;
import com.cm.entity.vo.AnalogAllVo;
import com.cm.entity.vo.AnalogQueryVo;
import com.cm.entity.vo.AnaloginfoQuery;
import com.cm.entity.vo.AnalogoutVo;
import com.cm.entity.vo.LongStringVo;
import com.cm.entity.vo.NameTime;

@Service("analoginfoService")
public class AnaloginfoService {
	
	@Autowired
	private AnaloginfoDao dao;
	
	//查询
	public List<AnaloginfoQuery> getall(NameTime nameTime) throws ParseException{
		List<AnaloginfoQuery> list = dao.getall(nameTime);
		for(AnaloginfoQuery l:list){
			AnaloginfoQuery query = getvalue(l);
			if(query!=null){
				l.setMaxvalues(query.getMaxvalues());
				l.setMaxtime(query.getMaxtime());
			}
			if(l.getStarttime()!=null&&l.getEndtime()!=null){
				LongStringVo stringVo = UtilMethod.longToTimeFormat(l.getStarttime(), l.getEndtime());
				l.setTimes(stringVo.getTimCast());
			}else if(l.getStarttime()!=null&&l.getEndtime()==null){
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String time3 =format.format(new Date());
				LongStringVo stringVo = UtilMethod.longToTimeFormat(l.getStarttime(), time3);
				l.setTimes(stringVo.getTimCast());
				l.setEndtime("至今");
			}
		}
		return list;
	}
	public int getcount(NameTime nameTime){
		return dao.getallcount(nameTime);
	}
	
	public List<AnaloginfoQuery> getallAna(NameTime nameTime) throws ParseException{
		List<AnaloginfoQuery> list = dao.getallAna(nameTime);
		for(AnaloginfoQuery l:list){
			AnaloginfoQuery query = getvalue(l);
			if(query!=null){
				l.setMaxvalues(query.getMaxvalues());
				l.setMaxtime(query.getMaxtime());
				l.setAvgvalue(query.getAvgvalue());
			}
			if(l.getStarttime()!=null&&l.getEndtime()!=null){
				l.setTimes(countTimeCast(countLongDvalue(l.getStarttime(), l.getEndtime())));
			}else if(l.getStarttime()!=null&&l.getEndtime()==null){
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String time3 =format.format(new Date());
				l.setTimes(countTimeCast(countLongDvalue(l.getStarttime(), time3)));
			}
		}
		return list;
	}
	
	public List<AnalogoutVo> getAnas(NameTime nameTime){
		List<AnalogoutVo> list = dao.getAnas(nameTime);
		for(AnalogoutVo s:list){
			if(s.getStatus()==2){
				s.setDesp("报警");
			}else if(s.getStatus()==3){
				s.setDesp("断电");
			}else if(s.getStatus()==4){
				s.setDesp("复电");
			}
		}
		return list;
	}
	public AnaloginfoQuery getvalue(AnaloginfoQuery l){
		AnalogQueryVo vo = new AnalogQueryVo();
		vo.setSensor_id(l.getSensorId());
		vo.setIp(l.getIp());
		vo.setType(l.getSensor_type());
		vo.setStartId(l.getStartId());
		vo.setEndId(l.getEndId());
		vo.setStatus(l.getStatus());
		return dao.getvalue(vo);
	}
	
	public List<AnaloginfoQuery> getpowrer(NameTime nameTime) throws ParseException{
		List<AnaloginfoQuery> list = dao.getpower(nameTime);
		List<AnaloginfoQuery> list2 = dao.getDevlink();
		Map<String, List<AnaloginfoQuery>> map = new HashMap<String, List<AnaloginfoQuery>>();
		for(AnaloginfoQuery l:list2){
			List<AnaloginfoQuery> queries = map.get(l.getIp()+l.getSensorId());
			if(queries==null){
				queries = new ArrayList<AnaloginfoQuery>();
				map.put(l.getIp()+l.getSensorId(),queries);
			}
			queries.add(l);
		}
		for(AnaloginfoQuery l:list){
			NameTime nameTime2 = new NameTime();
			nameTime2.setName(l.getIp()+"."+l.getSensorId());
			nameTime2.setStarttime(l.getStarttime());
			nameTime2.setEndtime(l.getEndtime());
			List<AnaloginfoQuery> list3 = dao.getfeed(nameTime2);
			Map<Integer, AnaloginfoQuery> map2 = new HashMap<Integer, AnaloginfoQuery>();
			for(AnaloginfoQuery s:list3){
				AnaloginfoQuery query = map2.get(s.getSensor_id());
				if(query==null){
					query = s;
					map2.put(s.getSensor_id(), query);
				}
			}
			List<AnaloginfoQuery> list4 = map.get(l.getIp()+l.getSensorId());
			if(list4!=null&&list4.size()>0){
				String feedstatus = "";
				for(AnaloginfoQuery k:list4){
					if(k.getPowerposition()==null){
						k.setPowerposition("未配置区域");
					}
					AnaloginfoQuery query = map2.get(k.getFeedId());
					if(k.getPowervalue()==1){
						if(query!=null){
							if(query.getEndtime()==null){
								if(gettimevalue(query.getStarttime(), l.getStarttime())>0){
									feedstatus = feedstatus+"断电/"+k.getPowerposition()+"/异常/"+query.getStarttime()+"~"+l.getEndtime()+",";
								}
								if(gettimevalue(query.getStarttime(), l.getStarttime())<0){
									feedstatus = feedstatus+"断电/"+k.getPowerposition()+"/异常/"+l.getStarttime()+"~"+l.getEndtime()+",";
								}
							}else{
								if(gettimevalue(query.getStarttime(), l.getStarttime())>0){
									if(gettimevalue(query.getEndtime(), l.getEndtime())>0){
										feedstatus = feedstatus+"断电/"+k.getPowerposition()+"/异常/"+query.getStarttime()+"~"+l.getEndtime()+",";
									}else{
										feedstatus = feedstatus+"断电/"+k.getPowerposition()+"/异常/"+query.getStarttime()+"~"+query.getEndtime()+",";
									}
								}else{
									if(gettimevalue(query.getEndtime(), l.getEndtime())>0){
										feedstatus = feedstatus+"断电/"+k.getPowerposition()+"/异常/"+l.getStarttime()+"~"+l.getEndtime()+",";
									}else{
										feedstatus = feedstatus+"断电/"+k.getPowerposition()+"/异常/"+l.getStarttime()+"~"+query.getEndtime()+",";
									}
								}
							}
						}else{
							feedstatus = feedstatus+"断电/"+k.getPowerposition()+"/正常/"+l.getStarttime()+"~"+l.getEndtime()+",";
						}
					}
					if(k.getPowervalue()==0){
						if(query!=null){
							if(query.getEndtime()==null){
								if(gettimevalue(query.getStarttime(), l.getStarttime())>0){
									feedstatus = feedstatus+"复电/"+k.getPowerposition()+"/异常/"+query.getStarttime()+"~"+l.getEndtime()+",";
								}
								if(gettimevalue(query.getStarttime(), l.getStarttime())<0){
									feedstatus = feedstatus+"复电/"+k.getPowerposition()+"/异常/"+l.getStarttime()+"~"+l.getEndtime()+",";
								}
							}else{
								if(gettimevalue(query.getStarttime(), l.getStarttime())>0){
									if(gettimevalue(query.getEndtime(), l.getEndtime())>0){
										feedstatus = feedstatus+"复电/"+k.getPowerposition()+"/异常/"+query.getStarttime()+"~"+l.getEndtime()+",";
									}else{
										feedstatus = feedstatus+"复电/"+k.getPowerposition()+"/异常/"+query.getStarttime()+"~"+query.getEndtime()+",";
									}
								}else{
									if(gettimevalue(query.getEndtime(), l.getEndtime())>0){
										feedstatus = feedstatus+"复电/"+k.getPowerposition()+"/异常/"+l.getStarttime()+"~"+l.getEndtime()+",";
									}else{
										feedstatus = feedstatus+"复电/"+k.getPowerposition()+"/异常/"+l.getStarttime()+"~"+query.getEndtime()+",";
									}
								}
							}
						}else{
							feedstatus = feedstatus+"复电/"+k.getPowerposition()+"/正常/"+l.getStarttime()+"~"+l.getEndtime()+",";
						}
					}
					
				}
				l.setFeedstatus(feedstatus);
			}
			AnaloginfoQuery query = getvalue(l);
			if(query!=null){
				l.setMaxvalues(query.getMaxvalues());
				l.setMaxtime(query.getMaxtime());
				l.setAvgvalue(query.getAvgvalue());
			}
			if(l.getStarttime()!=null&&l.getEndtime()!=null){
				l.setTimes(countTimeCast(countLongDvalue(l.getStarttime(), l.getEndtime())));
			}else if(l.getStarttime()!=null&&l.getEndtime()==null){
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String time3 =format.format(new Date());
				l.setTimes(countTimeCast(countLongDvalue(l.getStarttime(), time3)));
			}
		}
		return list;
	}
	public List<AnaloginfoQuery> getfeed(NameTime nameTime){
		return dao.getfeed(nameTime);
	}
	
	
	public List<AnaloginfoQuery> getfeedError(NameTime nameTime) throws ParseException{
		List<AnaloginfoQuery> list = dao.getfeedError(nameTime);
		for(AnaloginfoQuery l:list){
				l.setFeedstatus("异常");
			if(l.getStarttime()!=null&&l.getEndtime()!=null){
				l.setTimes(countTimeCast(countLongDvalue(l.getStarttime(), l.getEndtime())));
			}else if(l.getStarttime()!=null&&l.getEndtime()==null){
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String time3 =format.format(new Date());
				l.setTimes(countTimeCast(countLongDvalue(l.getStarttime(), time3)));
			}
		}
		return list;
	}
	
	public  long countLongDvalue(String time1, String time2) throws ParseException {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long longTime1 = format.parse(time1).getTime();
		long longTime2 = format.parse(time2).getTime();
		long time = Math.abs(longTime2-longTime1);
		return time;
	}
	
	public long gettimevalue(String time1,String time2) throws ParseException{
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long longTime1 = format.parse(time1).getTime();
		long longTime2 = format.parse(time2).getTime();
		long time = longTime1-longTime2;
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
	
	public List<AnalogQuery> getNullRec(String startTime){
		try {
			return dao.getNullRec(startTime);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<AnalogAllVo> getAllAnaloginfo(String startTime){
		try {
			return dao.getAllAnaloginfo(startTime);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
