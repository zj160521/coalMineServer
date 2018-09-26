package com.cm.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cm.dao.EquipmentErrorDao;
import com.cm.entity.vo.CommunicationVo;
import com.cm.entity.vo.CommunicationmentVo;
import com.cm.entity.vo.NameTime;


@Service("equipmentErrorService")
public class EquipmentErrorService {
	
	@Autowired
	private EquipmentErrorDao dao;
	
	public List<CommunicationmentVo> getdata(NameTime nameTime) throws ParseException{
		List<CommunicationVo> list = dao.getSensor(nameTime);
		List<CommunicationVo> list2 = dao.getSwitchSensor(nameTime);
		List<CommunicationVo> list3 = dao.getCardreder(nameTime);
		List<CommunicationVo> list4 = dao.getSubstation(nameTime);
		List<CommunicationVo> list5 = dao.getEquipment(nameTime);
		List<CommunicationVo> list6 = dao.getRadio(nameTime);
		Map<String, CommunicationmentVo> map = new HashMap<String, CommunicationmentVo>();
		List<CommunicationmentVo> list7 = new ArrayList<CommunicationmentVo>();
		long k = 0;
		if(list.size()>0){
			for(CommunicationVo l:list){
				CommunicationmentVo a = map.get(l.getUid());
				if(a==null){
					a = new CommunicationmentVo();
					a.setSensor_id(l.getSensor_id());
					a.setAlais(l.getAlais());
					a.setSensortype(l.getSensortype());
					a.setPosition(l.getPosition());
					map.put(l.getUid(), a);
					list7.add(a);
					k = 0;
				}
				a.setAlerts(a.getAlerts()+1);
				k = a.getTimess();
				k = k+getTime(l.getStarttime(), l.getEndtime(),nameTime.getEndtime());
				a.setTimess(k);
				if(l.getEndtime()==null){
					l.setEndtime("至今");
				}
				String times =  countTimeCast(k);
				a.setTimes(times);
				a.getList().add(l);
			}
		}
		if(list2.size()>0){
			for(CommunicationVo l:list2){
				CommunicationmentVo a = map.get(l.getUid());
				if(a==null){
					a = new CommunicationmentVo();
					a.setSensor_id(l.getSensor_id());
					a.setAlais(l.getAlais());
					a.setSensortype(l.getSensortype());
					a.setPosition(l.getPosition());
					map.put(l.getUid(), a);
					list7.add(a);
					k = 0;
				}
				a.setAlerts(a.getAlerts()+1);
				k = a.getTimess();
				k = k+getTime(l.getStarttime(), l.getEndtime(),nameTime.getEndtime());
				a.setTimess(k);
				if(l.getEndtime()==null){
					l.setEndtime("至今");
				}
				String times =  countTimeCast(k);
				a.setTimes(times);
				a.getList().add(l);
			}
		}
		if(list3.size()>0){
			for(CommunicationVo l:list3){
				CommunicationmentVo a = map.get(l.getUid());
				if(a==null){
					a = new CommunicationmentVo();
					a.setSensor_id(l.getSensor_id());
					a.setAlais(l.getAlais());
					a.setSensortype(l.getSensortype());
					a.setPosition(l.getPosition());
					map.put(l.getUid(), a);
					list7.add(a);
					k = 0;
				}
				a.setAlerts(a.getAlerts()+1);
				k = a.getTimess();
				k = k+getTime(l.getStarttime(), l.getEndtime(),nameTime.getEndtime());
				a.setTimess(k);
				if(l.getEndtime()==null){
					l.setEndtime("至今");
				}
				String times =  countTimeCast(k);
				a.setTimes(times);
				a.getList().add(l);
			}
		}
		if(list4.size()>0){
			for(CommunicationVo l:list4){
				CommunicationmentVo a = map.get(l.getUid());
				if(a==null){
					a = new CommunicationmentVo();
					a.setSensor_id(l.getSensor_id());
					a.setAlais(l.getAlais());
					a.setSensortype(l.getSensortype());
					a.setPosition(l.getPosition());
					map.put(l.getUid(), a);
					list7.add(a);
					k = 0;
				}
				a.setAlerts(a.getAlerts()+1);
				k = a.getTimess();
				k = k+getTime(l.getStarttime(), l.getEndtime(),nameTime.getEndtime());
				a.setTimess(k);
				if(l.getEndtime()==null){
					l.setEndtime("至今");
				}
				String times =  countTimeCast(k);
				a.setTimes(times);
				a.getList().add(l);
			}
		}
		if(list5.size()>0){
			for(CommunicationVo l:list5){
				CommunicationmentVo a = map.get(l.getUid());
				if(a==null){
					a = new CommunicationmentVo();
					a.setSensor_id(l.getSensor_id());
					a.setAlais(l.getAlais());
					a.setSensortype(l.getSensortype());
					a.setPosition(l.getPosition());
					map.put(l.getUid(), a);
					list7.add(a);
					k = 0;
				}
				a.setAlerts(a.getAlerts()+1);
				k = a.getTimess();
				k = k+getTime(l.getStarttime(), l.getEndtime(),nameTime.getEndtime());
				a.setTimess(k);
				if(l.getEndtime()==null){
					l.setEndtime("至今");
				}
				String times =  countTimeCast(k);
				a.setTimes(times);
				a.getList().add(l);
			}
		}
		if(list6.size()>0){
			for(CommunicationVo l:list6){
				CommunicationmentVo a = map.get(l.getUid());
				if(a==null){
					a = new CommunicationmentVo();
					a.setSensor_id(l.getSensor_id());
					a.setAlais(l.getAlais());
					a.setSensortype(l.getSensortype());
					a.setPosition(l.getPosition());
					map.put(l.getUid(), a);
					list7.add(a);
					k = 0;
				}
				a.setAlerts(a.getAlerts()+1);
				k = a.getTimess();
				k = k+getTime(l.getStarttime(), l.getEndtime(),nameTime.getEndtime());
				a.setTimess(k);
				if(l.getEndtime()==null){
					l.setEndtime("至今");
				}
				String times =  countTimeCast(k);
				a.setTimes(times);
				a.getList().add(l);
			}
		}
		
		return list7;
	}
	
	
	private long getTime(String time1,String time2,String time3) throws ParseException{
		long a=0;
			if(time1!=null&&time2!=null){
				a = countLongDvalue(time1, time2);
			}else if(time1!=null&&time2==null){
				a = countLongDvalue(time1, time3);
			}
		
		return a;
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
		int minutes =  (int) (Math.floor(times - hours*3600 )/60);
		int seconds = times - hours*3600  - minutes * 60;
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
