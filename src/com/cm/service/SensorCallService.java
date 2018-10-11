package com.cm.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.cm.dao.SensorCallDao;
import com.cm.entity.SensorCall;
import com.cm.entity.SwitchSensorCall;
import com.cm.entity.vo.AnaloginfoQuery;
import com.cm.entity.vo.NameTime;
import com.cm.entity.vo.SensorCallVo;

@Service("sensorCallService")
public class SensorCallService {
	
	@Autowired
	private SensorCallDao dao;

	
	
	//模拟量调用显示
	public List<SensorCall> getSensorCall(SensorCallVo callVo) throws ParseException{
		List<SensorCall> calls = new ArrayList<SensorCall>();
		List<AnaloginfoQuery> list = dao.getAnalogAlarm(callVo);
		List<AnaloginfoQuery> list2 = dao.getAnalogPower(callVo);
		String tablename =  "t_analog_statistics_"+getDay();
		List<AnaloginfoQuery> list3 = dao.getAnalogStatis(tablename);
		Map<String, AnaloginfoQuery> map = new HashMap<String, AnaloginfoQuery>();
		Map<String, AnaloginfoQuery> map2 = new HashMap<String, AnaloginfoQuery>();
		for(AnaloginfoQuery l:list2){
			map.put(l.getIp()+l.getSensor_type()+l.getSensorId(), l);
		}
		for(AnaloginfoQuery l:list3){
			String a = l.getIp()+l.getSensor_type()+l.getSensorId();
			map2.put(a, l);
		}
		for(AnaloginfoQuery s : list){
			SensorCall call = new SensorCall();
			call.setId(s.getId());
			call.setSensorkey(s.getIp()+":"+s.getSensorId()+":"+s.getSensor_type());
			call.setAlais(s.getAlais());
			call.setType(s.getSensortype());
			if(s.getPosition()==null){
				call.setPosition("未配置位置"+"/"+s.getAlais()+"/"+s.getSensortype());
			}else{
				call.setPosition(s.getPosition()+"/"+s.getAlais()+"/"+s.getSensortype());
			}
			if(Math.abs(s.getLimit_alarm())==999999&&Math.abs(s.getFloor_alarm())==999999||s.getLimit_alarm()==0&&s.getFloor_alarm()==0||s.getLimit_alarm()==0&&Math.abs(s.getFloor_alarm())==999999||Math.abs(s.getLimit_alarm())==999999&&s.getFloor_alarm()==0){
				call.setLimit_alarm("--/--");
			}else if(Math.abs(s.getLimit_alarm())!=999999&&s.getLimit_alarm()!=0&&Math.abs(s.getFloor_alarm())==999999||Math.abs(s.getLimit_alarm())!=999999&&s.getLimit_alarm()!=0&&Math.abs(s.getFloor_alarm())==0){
				call.setLimit_alarm(s.getLimit_alarm()+s.getUnit()+"/--");
			}else if(Math.abs(s.getLimit_alarm())==999999&&Math.abs(s.getFloor_alarm())!=999999&&s.getFloor_alarm()!=0||Math.abs(s.getLimit_alarm())==0&&Math.abs(s.getFloor_alarm())!=999999&&s.getFloor_alarm()!=0){
				call.setLimit_alarm("--/"+s.getFloor_alarm()+s.getUnit());
			}else{
				call.setLimit_alarm(s.getLimit_alarm()+s.getUnit()+"/"+s.getFloor_alarm()+s.getUnit());
			}
			if(Math.abs(s.getLimit_power())==999999&&Math.abs(s.getFloor_power())==999999||s.getLimit_power()==0&&s.getFloor_power()==0||s.getLimit_power()==0&&Math.abs(s.getFloor_power())==999999||Math.abs(s.getLimit_power())==999999&&s.getFloor_power()==0){
				call.setLimit_power("--/--");
			}else if(Math.abs(s.getLimit_power())!=999999&&s.getLimit_power()!=0&&Math.abs(s.getFloor_power())==999999||Math.abs(s.getLimit_power())!=999999&&s.getLimit_power()!=0&&Math.abs(s.getFloor_power())==0){
				call.setLimit_power(s.getLimit_power()+s.getUnit()+"/--");
			}else if(Math.abs(s.getLimit_power())==999999&&Math.abs(s.getFloor_power())!=999999&&s.getFloor_power()!=0||s.getLimit_power()==0&&Math.abs(s.getFloor_power())!=999999&&s.getFloor_power()!=0){
				call.setLimit_power("--/"+s.getFloor_power()+s.getUnit());
			}else{
				call.setLimit_power(s.getLimit_power()+s.getUnit()+"/"+s.getFloor_power()+s.getUnit());
			}
			if(Math.abs(s.getLimit_repower())==999999&&Math.abs(s.getFloor_repower())==999999||Math.abs(s.getLimit_repower())==0&&Math.abs(s.getFloor_repower())==0||Math.abs(s.getLimit_repower())==0&&Math.abs(s.getFloor_repower())==999999||Math.abs(s.getLimit_repower())==999999&&Math.abs(s.getFloor_repower())==0){
				call.setLimit_repower("--/--");
			}else if(Math.abs(s.getLimit_repower())!=999999&&Math.abs(s.getLimit_repower())!=0&&Math.abs(s.getFloor_repower())==999999||Math.abs(s.getLimit_repower())!=999999&&Math.abs(s.getLimit_repower())!=0&&Math.abs(s.getFloor_repower())==0){
				call.setLimit_repower(s.getLimit_repower()+s.getUnit()+"/--");
			}else if(Math.abs(s.getLimit_repower())==999999&&Math.abs(s.getFloor_repower())!=999999&&Math.abs(s.getFloor_repower())!=0||Math.abs(s.getLimit_repower())==0&&Math.abs(s.getFloor_repower())!=999999&&Math.abs(s.getFloor_repower())!=0){
				call.setLimit_repower("--/"+s.getFloor_repower()+s.getUnit());
			}else{
				call.setLimit_repower(s.getLimit_repower()+s.getUnit()+"/"+s.getFloor_repower()+s.getUnit());
			}
			if(s.getStarttime()!=null&&s.getEndtime()!=null){
				String[] a = {"报警/"+s.getStarttime(),"解除/"+s.getEndtime()};
				call.setAlarmStarttime(a);
			}else{
				String[] a = null;
				call.setAlarmStarttime(a);
			}
			call.setAlarmEndtime(s.getEndtime());
			String k = s.getIp()+s.getSensor_type()+s.getSensorId();
			if(map2.get(k)!=null){
				call.setMaxvalue(map2.get(k).getMaxvalues()+s.getUnit());	
				call.setAvgvalue(map2.get(k).getAvgvalue()+s.getUnit());
				call.setMinvalue(map2.get(k).getMinvalue()+s.getUnit());
				call.setMaxvaluestime(map2.get(k).getMaxtime());
				call.setMinvaluetime(map2.get(k).getMintime());
			}
			if(map.get(k)!=null){
				String[] a = {"断电/"+map.get(k).getStarttime(),"复电/"+map.get(k).getEndtime()};
				call.setPowerStarttime(a);
			}
			calls.add(call);
		}
		
		return calls;
	}
	
	
	//开关量调用显示
	@SuppressWarnings("unchecked")
	public List<SwitchSensorCall> getSwitchSensorCall(SensorCallVo callVo){
		List<AnaloginfoQuery> list = dao.getSwitchSensorCall(callVo);
		List<SwitchSensorCall> calls = new ArrayList<SwitchSensorCall>();
		for(AnaloginfoQuery l : list){
			SwitchSensorCall call = new SwitchSensorCall();
			call.setId(l.getId());
			call.setSensorkey(l.getIp()+":"+l.getSensorId()+":"+l.getSensor_type());
			call.setAlais(l.getAlais());
			call.setType(l.getSensortype());
			Map<Integer, String> powers = JSON.parseObject(l.getAlarmstatus(), Map.class);
			if(l.getSensor_type()==56||l.getSensor_type()==71||l.getAlarm_status()<0){
				call.setAlarmstatus("--");
			}else{
				if(powers.get(l.getAlarm_status())!=null){
					call.setAlarmstatus(powers.get(l.getAlarm_status()));
				}else{
					call.setAlarmstatus("--");
				}
			}
			if(l.getPosition()==null){
				call.setPosition("未配置位置"+"/"+l.getAlais()+"/"+l.getSensortype());
			}else{
				call.setPosition(l.getPosition()+"/"+l.getAlais()+"/"+l.getSensortype());
			}
			if(l.getStarttime()!=null&&l.getEndtime()!=null){
				String[] a = {"报警、断电/"+l.getStarttime(),"解除、复电/"+l.getEndtime()};
				call.setAlarmStarttime(a);
			}else if(l.getStarttime()!=null&&l.getEndtime()==null){
				String[] a = {"报警、断电/"+l.getStarttime()};
				call.setAlarmStarttime(a);
			}else{
				call.setAlarmStarttime(null);
			}
			if(l.getMeasurestime()!=null&&l.getMeasures()!=null){
				call.setMeasuretime(l.getMeasures()+"/"+l.getMeasurestime());
			}
			calls.add(call);
		}
		
		return calls;
	}
	
	
	//模拟量报警显示
	public List<SensorCall> getsensorAlarm(SensorCallVo callVo) throws ParseException{
		List<SensorCall> calls = new ArrayList<SensorCall>();
		List<AnaloginfoQuery> list = dao.getsensorAlarm(callVo);
		String tablename =  "t_analog_statistics_"+getDay();
		List<AnaloginfoQuery> list2 = dao.getAnalogStatis(tablename);
		List<AnaloginfoQuery> list3 = dao.getAnalogPower(callVo);
		Map<String, AnaloginfoQuery> map = new HashMap<String, AnaloginfoQuery>();
		Map<String, AnaloginfoQuery> map2 = new HashMap<String, AnaloginfoQuery>();
		for(AnaloginfoQuery l:list2){
			map2.put(l.getIp()+l.getSensor_type()+l.getSensorId(), l);
		}
		for(AnaloginfoQuery l:list3){
			map.put(l.getIp()+l.getSensor_type()+l.getSensorId(), l);
		}
		for(AnaloginfoQuery s:list){
			SensorCall call = new SensorCall();
			call.setId(s.getId());
			call.setSensorkey(s.getIp()+":"+s.getSensorId()+":"+s.getSensor_type());
			call.setAlais(s.getAlais());
			call.setType(s.getSensortype());
			if(s.getPosition()==null){
				call.setPosition("未配置位置"+"/"+s.getAlais()+"/"+s.getSensortype());
			}else{
				call.setPosition(s.getPosition()+"/"+s.getAlais()+"/"+s.getSensortype());
			}
			if(Math.abs(s.getLimit_alarm())==999999&&Math.abs(s.getFloor_alarm())==999999||s.getLimit_alarm()==0&&s.getFloor_alarm()==0||s.getLimit_alarm()==0&&Math.abs(s.getFloor_alarm())==999999||Math.abs(s.getLimit_alarm())==999999&&s.getFloor_alarm()==0){
				call.setLimit_alarm("--/--");
			}else if(Math.abs(s.getLimit_alarm())!=999999&&s.getLimit_alarm()!=0&&Math.abs(s.getFloor_alarm())==999999||Math.abs(s.getLimit_alarm())!=999999&&s.getLimit_alarm()!=0&&Math.abs(s.getFloor_alarm())==0){
				call.setLimit_alarm(s.getLimit_alarm()+s.getUnit()+"/--");
			}else if(Math.abs(s.getLimit_alarm())==999999&&Math.abs(s.getFloor_alarm())!=999999&&s.getFloor_alarm()!=0||Math.abs(s.getLimit_alarm())==0&&Math.abs(s.getFloor_alarm())!=999999&&s.getFloor_alarm()!=0){
				call.setLimit_alarm("--/"+s.getFloor_alarm()+s.getUnit());
			}else{
				call.setLimit_alarm(s.getLimit_alarm()+s.getUnit()+"/"+s.getFloor_alarm()+s.getUnit());
			}
			if(Math.abs(s.getLimit_power())==999999&&Math.abs(s.getFloor_power())==999999||s.getLimit_power()==0&&s.getFloor_power()==0||s.getLimit_power()==0&&Math.abs(s.getFloor_power())==999999||Math.abs(s.getLimit_power())==999999&&s.getFloor_power()==0){
				call.setLimit_power("--/--");
			}else if(Math.abs(s.getLimit_power())!=999999&&s.getLimit_power()!=0&&Math.abs(s.getFloor_power())==999999||Math.abs(s.getLimit_power())!=999999&&s.getLimit_power()!=0&&Math.abs(s.getFloor_power())==0){
				call.setLimit_power(s.getLimit_power()+s.getUnit()+"/--");
			}else if(Math.abs(s.getLimit_power())==999999&&Math.abs(s.getFloor_power())!=999999&&s.getFloor_power()!=0||s.getLimit_power()==0&&Math.abs(s.getFloor_power())!=999999&&s.getFloor_power()!=0){
				call.setLimit_power("--/"+s.getFloor_power()+s.getUnit());
			}else{
				call.setLimit_power(s.getLimit_power()+s.getUnit()+"/"+s.getFloor_power()+s.getUnit());
			}
			if(Math.abs(s.getLimit_repower())==999999&&Math.abs(s.getFloor_repower())==999999||Math.abs(s.getLimit_repower())==0&&Math.abs(s.getFloor_repower())==0||Math.abs(s.getLimit_repower())==0&&Math.abs(s.getFloor_repower())==999999||Math.abs(s.getLimit_repower())==999999&&Math.abs(s.getFloor_repower())==0){
				call.setLimit_repower("--/--");
			}else if(Math.abs(s.getLimit_repower())!=999999&&Math.abs(s.getLimit_repower())!=0&&Math.abs(s.getFloor_repower())==999999||Math.abs(s.getLimit_repower())!=999999&&Math.abs(s.getLimit_repower())!=0&&Math.abs(s.getFloor_repower())==0){
				call.setLimit_repower(s.getLimit_repower()+s.getUnit()+"/--");
			}else if(Math.abs(s.getLimit_repower())==999999&&Math.abs(s.getFloor_repower())!=999999&&Math.abs(s.getFloor_repower())!=0||Math.abs(s.getLimit_repower())==0&&Math.abs(s.getFloor_repower())!=999999&&Math.abs(s.getFloor_repower())!=0){
				call.setLimit_repower("--/"+s.getFloor_repower()+s.getUnit());
			}else{
				call.setLimit_repower(s.getLimit_repower()+s.getUnit()+"/"+s.getFloor_repower()+s.getUnit());
			}
			if(s.getStarttime()!=null){
				String[] a = {"报警/"+s.getStarttime()};
				call.setAlarmStarttime(a);
			}else{
				String[] a = null;
				call.setAlarmStarttime(a);
			}
			String k = s.getIp()+s.getSensor_type()+s.getSensorId();
			if(map2.get(k)!=null){
				call.setMaxvalue(map2.get(k).getMaxvalues()+s.getUnit());	
				call.setAvgvalue(map2.get(k).getAvgvalue()+s.getUnit());
				call.setMinvalue(map2.get(k).getMinvalue()+s.getUnit());
				call.setMaxvaluestime(map2.get(k).getMaxtime());
				call.setMinvaluetime(map2.get(k).getMintime());			}
			if(map.get(k)!=null){
				String[] a = {"断电/"+map.get(k).getStarttime(),"复电/"+map.get(k).getEndtime()};
				call.setPowerStarttime(a);
			}
			if(s.getMeasures()!=null&&s.getMeasurestime()!=null){
				call.setMeasuretime(s.getMeasures()+"/"+s.getMeasurestime());
			}
			calls.add(call);
			
		}
		
		return calls;
	}
	//开关量报警显示
	@SuppressWarnings("unchecked")
	public List<SwitchSensorCall> getswitchSensorAlarm(SensorCallVo callVo){
		List<SwitchSensorCall> calls = new ArrayList<SwitchSensorCall>();
		List<AnaloginfoQuery> list = dao.getswitchSensorAlarm(callVo);
		for(AnaloginfoQuery l:list){
			SwitchSensorCall call = new SwitchSensorCall();
			call.setId(l.getId());
			call.setSensorkey(l.getIp()+":"+l.getSensorId()+":"+l.getSensor_type());
			call.setAlais(l.getAlais());
			call.setType(l.getSensortype());
			Map<Integer, String> powers = JSON.parseObject(l.getAlarmstatus(), Map.class);
			if(l.getSensor_type()==56||l.getSensor_type()==71||l.getAlarm_status()<0){
				call.setAlarmstatus("--");
			}else{
				if(powers.get(l.getAlarm_status())!=null){
					call.setAlarmstatus(powers.get(l.getAlarm_status()));
				}else{
					call.setAlarmstatus("--");
				}
			}
			if(l.getPosition()==null){
				call.setPosition("未配置位置"+"/"+l.getAlais()+"/"+l.getSensortype());
			}else{
				call.setPosition(l.getPosition()+"/"+l.getAlais()+"/"+l.getSensortype());
			}
			String[] a = {"报警、断电/"+l.getStarttime()}; 
			call.setAlarmStarttime(a);
			if(l.getMeasurestime()!=null&&l.getMeasures()!=null){
				call.setMeasuretime(l.getMeasures()+"/"+l.getMeasurestime());
			}
			calls.add(call);
		}
		return calls;
	}
	
	//模拟量断电显示
	public List<SensorCall> getsensorPower(SensorCallVo callVo){
		List<SensorCall> calls = new ArrayList<SensorCall>();
		List<AnaloginfoQuery> list = dao.getsensorPower(callVo);
		List<AnaloginfoQuery> list2 = dao.getsensorAlarm(callVo);
		Map<String, AnaloginfoQuery> map = new HashMap<String, AnaloginfoQuery>();
		List<AnaloginfoQuery> list4 = dao.getfeedstatus();
		List<AnaloginfoQuery> list3 = dao.getsensorRepower(callVo);
		for(AnaloginfoQuery l:list2){
			map.put(l.getIp()+l.getSensor_type()+l.getSensorId(), l);
		}
		Map<String, List<AnaloginfoQuery>> map2 = new HashMap<String, List<AnaloginfoQuery>>();
		Map<String, List<AnaloginfoQuery>> map3 = new HashMap<String, List<AnaloginfoQuery>>();
		for(AnaloginfoQuery l:list4){
			List<AnaloginfoQuery> queries = map2.get(l.getIp()+l.getSensorId());
			if(queries==null){
				queries = new ArrayList<AnaloginfoQuery>();
				map2.put(l.getIp()+l.getSensorId(), queries);
			}
			queries.add(l);
		}
		for(AnaloginfoQuery l:list3){
			List<AnaloginfoQuery> queries = map3.get(l.getIp()+l.getSensorId());
			if(queries==null){
				queries = new ArrayList<AnaloginfoQuery>();
				map3.put(l.getIp()+l.getSensorId(), queries);
			}
			queries.add(l);
		}
		for(AnaloginfoQuery s:list){
			SensorCall call = new SensorCall();
			call.setId(s.getId());
			call.setSensorkey(s.getIp()+":"+s.getSensorId()+":"+s.getSensor_type());
			call.setAlais(s.getAlais());
			call.setType(s.getSensortype());
			if(s.getPosition()==null){
				call.setPosition("未配置位置"+"/"+s.getAlais()+"/"+s.getSensortype());
			}else{
				call.setPosition(s.getPosition()+"/"+s.getAlais()+"/"+s.getSensortype());
			}
			if(Math.abs(s.getLimit_alarm())==999999&&Math.abs(s.getFloor_alarm())==999999||s.getLimit_alarm()==0&&s.getFloor_alarm()==0||s.getLimit_alarm()==0&&Math.abs(s.getFloor_alarm())==999999||Math.abs(s.getLimit_alarm())==999999&&s.getFloor_alarm()==0){
				call.setLimit_alarm("--/--");
			}else if(Math.abs(s.getLimit_alarm())!=999999&&s.getLimit_alarm()!=0&&Math.abs(s.getFloor_alarm())==999999||Math.abs(s.getLimit_alarm())!=999999&&s.getLimit_alarm()!=0&&Math.abs(s.getFloor_alarm())==0){
				call.setLimit_alarm(s.getLimit_alarm()+s.getUnit()+"/--");
			}else if(Math.abs(s.getLimit_alarm())==999999&&Math.abs(s.getFloor_alarm())!=999999&&s.getFloor_alarm()!=0||Math.abs(s.getLimit_alarm())==0&&Math.abs(s.getFloor_alarm())!=999999&&s.getFloor_alarm()!=0){
				call.setLimit_alarm("--/"+s.getFloor_alarm()+s.getUnit());
			}else{
				call.setLimit_alarm(s.getLimit_alarm()+s.getUnit()+"/"+s.getFloor_alarm()+s.getUnit());
			}
			if(Math.abs(s.getLimit_power())==999999&&Math.abs(s.getFloor_power())==999999||s.getLimit_power()==0&&s.getFloor_power()==0||s.getLimit_power()==0&&Math.abs(s.getFloor_power())==999999||Math.abs(s.getLimit_power())==999999&&s.getFloor_power()==0){
				call.setLimit_power("--/--");
			}else if(Math.abs(s.getLimit_power())!=999999&&s.getLimit_power()!=0&&Math.abs(s.getFloor_power())==999999||Math.abs(s.getLimit_power())!=999999&&s.getLimit_power()!=0&&Math.abs(s.getFloor_power())==0){
				call.setLimit_power(s.getLimit_power()+s.getUnit()+"/--");
			}else if(Math.abs(s.getLimit_power())==999999&&Math.abs(s.getFloor_power())!=999999&&s.getFloor_power()!=0||s.getLimit_power()==0&&Math.abs(s.getFloor_power())!=999999&&s.getFloor_power()!=0){
				call.setLimit_power("--/"+s.getFloor_power()+s.getUnit());
			}else{
				call.setLimit_power(s.getLimit_power()+s.getUnit()+"/"+s.getFloor_power()+s.getUnit());
			}
			if(Math.abs(s.getLimit_repower())==999999&&Math.abs(s.getFloor_repower())==999999||Math.abs(s.getLimit_repower())==0&&Math.abs(s.getFloor_repower())==0||Math.abs(s.getLimit_repower())==0&&Math.abs(s.getFloor_repower())==999999||Math.abs(s.getLimit_repower())==999999&&Math.abs(s.getFloor_repower())==0){
				call.setLimit_repower("--/--");
			}else if(Math.abs(s.getLimit_repower())!=999999&&Math.abs(s.getLimit_repower())!=0&&Math.abs(s.getFloor_repower())==999999||Math.abs(s.getLimit_repower())!=999999&&Math.abs(s.getLimit_repower())!=0&&Math.abs(s.getFloor_repower())==0){
				call.setLimit_repower(s.getLimit_repower()+s.getUnit()+"/--");
			}else if(Math.abs(s.getLimit_repower())==999999&&Math.abs(s.getFloor_repower())!=999999&&Math.abs(s.getFloor_repower())!=0||Math.abs(s.getLimit_repower())==0&&Math.abs(s.getFloor_repower())!=999999&&Math.abs(s.getFloor_repower())!=0){
				call.setLimit_repower("--/"+s.getFloor_repower()+s.getUnit());
			}else{
				call.setLimit_repower(s.getLimit_repower()+s.getUnit()+"/"+s.getFloor_repower()+s.getUnit());
			}
			String k = s.getIp()+s.getSensor_type()+s.getSensorId();
			if(map.get(k)!=null){
				String[] a = {"报警/"+map.get(k).getStarttime()};
				call.setAlarmStarttime(a);
			}else{
				String[] a = null;
				call.setAlarmStarttime(a);
			}
			String[] b = {"断电/"+s.getStarttime()};
			call.setPowerStarttime(b);
			if(s.getMeasures()!=null&&s.getMeasurestime()!=null){
				call.setMeasuretime(s.getMeasures()+"/"+s.getMeasurestime());
			}
			List<AnaloginfoQuery> queries = map2.get(s.getIp()+s.getSensorId());
			List<AnaloginfoQuery> queries2 = map3.get(s.getIp()+s.getSensorId());
			Map<String, AnaloginfoQuery> map4 = new HashMap<String, AnaloginfoQuery>();
			if(queries!=null){
				for(AnaloginfoQuery l:queries){
					AnaloginfoQuery query = map4.get(l.getFeeduid());
					if(query==null){
						query = new AnaloginfoQuery();
						map4.put(l.getFeeduid(), l);
					}
				}
			}
			if(queries2!=null){
				String[] strings = new String[queries2.size()];
				for(int i=0;i<queries2.size();i++){
					AnaloginfoQuery query = map4.get(queries2.get(i).getFeeduid());
					if(query!=null){
						if(query.getPosition()==null){
							strings[i] = "未配置位置"+"/馈电异常/"+query.getStarttime();
						}else{
							strings[i] = query.getPowerposition()+"/馈电异常/"+query.getStarttime();
						}
					}else{
						if(queries2.get(i).getPowerposition()==null){
							strings[i] = "未配置位置"+"/正常/"+s.getStarttime();
						}else{
							strings[i] = queries2.get(i).getPowerposition()+"/正常/"+s.getStarttime();
						}
					}
				}
				call.setFeedstastus(strings);
			}
			calls.add(call);
			
		}
		return calls;
	}
	//开关量断电显示
	@SuppressWarnings("unchecked")
	public List<SwitchSensorCall> getswitchSensorPower(SensorCallVo callVo){
		List<SwitchSensorCall> calls = new ArrayList<SwitchSensorCall>();
		List<AnaloginfoQuery> list = dao.getswitchSensorAlarm(callVo);
		List<AnaloginfoQuery> list4 = dao.getfeedstatus();
		List<AnaloginfoQuery> list3 = dao.getsensorRepower(callVo);
		Map<String, List<AnaloginfoQuery>> map2 = new HashMap<String, List<AnaloginfoQuery>>();
		Map<String, List<AnaloginfoQuery>> map3 = new HashMap<String, List<AnaloginfoQuery>>();
		for(AnaloginfoQuery l:list4){
			List<AnaloginfoQuery> queries = map2.get(l.getIp()+l.getSensorId());
			if(queries==null){
				queries = new ArrayList<AnaloginfoQuery>();
				map2.put(l.getIp()+l.getSensorId(), queries);
			}
			queries.add(l);
		}
		for(AnaloginfoQuery l:list3){
			List<AnaloginfoQuery> queries = map3.get(l.getIp()+l.getSensorId());
			if(queries==null){
				queries = new ArrayList<AnaloginfoQuery>();
				map3.put(l.getIp()+l.getSensorId(), queries);
			}
			queries.add(l);
		}
		for(AnaloginfoQuery l:list){
			SwitchSensorCall call = new SwitchSensorCall();
			call.setId(l.getId());
			call.setSensorkey(l.getIp()+":"+l.getSensorId()+":"+l.getSensor_type());
			call.setAlais(l.getAlais());
			call.setType(l.getSensortype());
			Map<Integer, String> powers = JSON.parseObject(l.getAlarmstatus(), Map.class);
			if(l.getSensor_type()==56||l.getSensor_type()==71||l.getAlarm_status()<0){
				call.setAlarmstatus("--");
			}else{
				if(powers.get(l.getAlarm_status())!=null){
					call.setAlarmstatus(powers.get(l.getAlarm_status()));
				}else{
					call.setAlarmstatus("--");
				}
			}
			if(l.getPosition()==null){
				call.setPosition("未配置位置"+"/"+l.getAlais()+"/"+l.getSensortype());
			}else{
				call.setPosition(l.getPosition()+"/"+l.getAlais()+"/"+l.getSensortype());
			}
			String[] a = {"报警、断电/"+l.getStarttime()}; 
			String[] b = {"断电/"+l.getStarttime()};
			call.setAlarmStarttime(a);
			call.setPowerStarttime(b);;
			if(l.getMeasurestime()!=null&&l.getMeasures()!=null){
				call.setMeasuretime(l.getMeasures()+"/"+l.getMeasurestime());
			}
			List<AnaloginfoQuery> queries = map2.get(l.getIp()+l.getSensorId());
			List<AnaloginfoQuery> queries2 = map3.get(l.getIp()+l.getSensorId());
			Map<String, AnaloginfoQuery> map4 = new HashMap<String, AnaloginfoQuery>();
			if(queries!=null){
				for(AnaloginfoQuery s:queries){
					AnaloginfoQuery query = map4.get(s.getFeeduid());
					if(query==null){
						query = new AnaloginfoQuery();
						map4.put(s.getFeeduid(), s);
					}
				}
			}
			if(queries2!=null){
				String[] strings = new String[queries2.size()];
				for(int i=0;i<queries2.size();i++){
					AnaloginfoQuery query = map4.get(queries2.get(i).getFeeduid());
					if(query!=null){
						if(query.getPowerposition()==null){
							strings[i] = "未配置区域"+"/馈电异常/"+query.getStarttime();
						}else{
							strings[i] = query.getPowerposition()+"/馈电异常/"+query.getStarttime();
						}
					}else{
						if(queries2.get(i).getPosition()==null){
							strings[i] = "未配置位置"+"/正常/"+l.getStarttime();
						}else{
							strings[i] = queries2.get(i).getPowerposition()+"/正常/"+l.getStarttime();
						}
					}
				}
				call.setFeedstastus(strings);
				calls.add(call);
			}
		}
		return calls;
	}
	
	//模拟量馈电馈电异常显示
	public List<SensorCall> getsensorRepower(SensorCallVo callVo){
		List<SensorCall> calls = new ArrayList<SensorCall>();
		List<AnaloginfoQuery> list2 = dao.getsensorAlarm(callVo);
		List<AnaloginfoQuery> list3 = dao.getsensorPower(callVo);
		List<AnaloginfoQuery> list4 = dao.getfeedstatus();
		Map<String, AnaloginfoQuery> map = new HashMap<String, AnaloginfoQuery>();
		for(AnaloginfoQuery l:list2){
			map.put(l.getIp()+l.getSensorId(), l);
		}
		Map<String, AnaloginfoQuery> map3 = new HashMap<String, AnaloginfoQuery>();
		for(AnaloginfoQuery l:list3){
			map3.put(l.getIp()+l.getSensorId(), l);
		}
		for(AnaloginfoQuery l:list4){
			SensorCall call = new SensorCall();
			call.setId(l.getId());
			call.setSensorkey(l.getIp()+":"+l.getSensorId()+":"+l.getSensor_type());
			if(l.getPosition()==null){
				call.setPosition("未配置位置"+"/"+l.getAlais()+"/"+l.getSensortype());
			}else{
				call.setPosition(l.getPosition()+"/"+l.getAlais()+"/"+l.getSensortype());
			}
			if(Math.abs(l.getLimit_alarm())==999999&&Math.abs(l.getFloor_alarm())==999999||l.getLimit_alarm()==0&&l.getFloor_alarm()==0||l.getLimit_alarm()==0&&Math.abs(l.getFloor_alarm())==999999||Math.abs(l.getLimit_alarm())==999999&&l.getFloor_alarm()==0){
				call.setLimit_alarm("--/--");
			}else if(Math.abs(l.getLimit_alarm())!=999999&&l.getLimit_alarm()!=0&&Math.abs(l.getFloor_alarm())==999999||Math.abs(l.getLimit_alarm())!=999999&&l.getLimit_alarm()!=0&&Math.abs(l.getFloor_alarm())==0){
				call.setLimit_alarm(l.getLimit_alarm()+l.getUnit()+"/--");
			}else if(Math.abs(l.getLimit_alarm())==999999&&Math.abs(l.getFloor_alarm())!=999999&&l.getFloor_alarm()!=0||Math.abs(l.getLimit_alarm())==0&&Math.abs(l.getFloor_alarm())!=999999&&l.getFloor_alarm()!=0){
				call.setLimit_alarm("--/"+l.getFloor_alarm()+l.getUnit());
			}else{
				call.setLimit_alarm(l.getLimit_alarm()+l.getUnit()+"/"+l.getFloor_alarm()+l.getUnit());
			}
			if(Math.abs(l.getLimit_power())==999999&&Math.abs(l.getFloor_power())==999999||l.getLimit_power()==0&&l.getFloor_power()==0||l.getLimit_power()==0&&Math.abs(l.getFloor_power())==999999||Math.abs(l.getLimit_power())==999999&&l.getFloor_power()==0){
				call.setLimit_power("--/--");
			}else if(Math.abs(l.getLimit_power())!=999999&&l.getLimit_power()!=0&&Math.abs(l.getFloor_power())==999999||Math.abs(l.getLimit_power())!=999999&&l.getLimit_power()!=0&&Math.abs(l.getFloor_power())==0){
				call.setLimit_power(l.getLimit_power()+l.getUnit()+"/--");
			}else if(Math.abs(l.getLimit_power())==999999&&Math.abs(l.getFloor_power())!=999999&&l.getFloor_power()!=0||l.getLimit_power()==0&&Math.abs(l.getFloor_power())!=999999&&l.getFloor_power()!=0){
				call.setLimit_power("--/"+l.getFloor_power()+l.getUnit());
			}else{
				call.setLimit_power(l.getLimit_power()+l.getUnit()+"/"+l.getFloor_power()+l.getUnit());
			}
			if(Math.abs(l.getLimit_repower())==999999&&Math.abs(l.getFloor_repower())==999999||Math.abs(l.getLimit_repower())==0&&Math.abs(l.getFloor_repower())==0||Math.abs(l.getLimit_repower())==0&&Math.abs(l.getFloor_repower())==999999||Math.abs(l.getLimit_repower())==999999&&Math.abs(l.getFloor_repower())==0){
				call.setLimit_repower("--/--");
			}else if(Math.abs(l.getLimit_repower())!=999999&&Math.abs(l.getLimit_repower())!=0&&Math.abs(l.getFloor_repower())==999999||Math.abs(l.getLimit_repower())!=999999&&Math.abs(l.getLimit_repower())!=0&&Math.abs(l.getFloor_repower())==0){
				call.setLimit_repower(l.getLimit_repower()+l.getUnit()+"/--");
			}else if(Math.abs(l.getLimit_repower())==999999&&Math.abs(l.getFloor_repower())!=999999&&Math.abs(l.getFloor_repower())!=0||Math.abs(l.getLimit_repower())==0&&Math.abs(l.getFloor_repower())!=999999&&Math.abs(l.getFloor_repower())!=0){
				call.setLimit_repower("--/"+l.getFloor_repower()+l.getUnit());
			}else{
				call.setLimit_repower(l.getLimit_repower()+l.getUnit()+"/"+l.getFloor_repower()+l.getUnit());
			}
			if(map.get(l.getIp()+l.getSensorId())!=null){
				String[] a = {"报警/"+map.get(l.getIp()+l.getSensorId()).getStarttime()};
				call.setAlarmStarttime(a);
			}
			if(map3.get(l.getIp()+l.getSensorId())!=null){
				String[] b = {"断电/"+map3.get(l.getIp()+l.getSensorId()).getStarttime()};
				call.setPowerStarttime(b);
			}
			if(l.getPowerposition()==null){
				String[] c = {"未配置区域"+"/馈电异常/"+l.getStarttime()};
				call.setFeedstastus(c);
			}else{
				String[] c = {l.getPowerposition()+"/馈电异常/"+l.getStarttime()};
				call.setFeedstastus(c);
			}
			if(l.getMeasures()!=null&&l.getMeasurestime()!=null){
				call.setMeasuretime(l.getMeasures()+"/"+l.getMeasurestime());
			}
			calls.add(call);
		}
		return calls;
	}
	//开关量馈电馈电异常显示
	@SuppressWarnings("unchecked")
	public List<SwitchSensorCall> getswitchSensorRepower(SensorCallVo callVo){
		List<SwitchSensorCall> calls = new ArrayList<SwitchSensorCall>();
		List<AnaloginfoQuery> list = dao.getswitchSensorAlarm(callVo);
		List<AnaloginfoQuery> list4 = dao.getswitchfeedstatus();
		Map<String, AnaloginfoQuery> map = new HashMap<String, AnaloginfoQuery>();
		for(AnaloginfoQuery l:list){
			map.put(l.getIp()+l.getSensorId(), l);
		}
		for(AnaloginfoQuery l:list4){
			SwitchSensorCall call = new SwitchSensorCall();
			call.setId(l.getId());
			call.setSensorkey(l.getIp()+":"+l.getSensorId()+":"+l.getSensor_type());
			Map<Integer, String> powers = JSON.parseObject(l.getAlarmstatus(), Map.class);
			if(l.getSensor_type()==56||l.getSensor_type()==71||l.getAlarm_status()<0){
				call.setAlarmstatus("--");
			}else{
				if(powers.get(l.getAlarm_status())!=null){
					call.setAlarmstatus(powers.get(l.getAlarm_status()));
				}else{
					call.setAlarmstatus("--");
				}
			}
			if(l.getPosition()==null){
				call.setPosition("未配置位置"+"/"+l.getAlais()+"/"+l.getSensortype());
			}else{
				call.setPosition(l.getPosition()+"/"+l.getAlais()+"/"+l.getSensortype());
			}
			if(map.get(l.getIp()+l.getSensorId())!=null){
				String[] a = {"报警、断电/"+map.get(l.getIp()+l.getSensorId()).getStarttime()};
				String[] b = {"断电/"+map.get(l.getIp()+l.getSensorId()).getStarttime()};
				call.setAlarmStarttime(a);
				call.setPowerStarttime(b);
			}
			if(l.getPowerposition()==null){
				String[] c = {"未配置区域"+"/馈电异常/"+l.getStarttime()};
				call.setFeedstastus(c);
			}else{
				String[] c = {l.getPowerposition()+"/馈电异常/"+l.getStarttime()};
				call.setFeedstastus(c);
			}
			
			if(l.getMeasures()!=null&&l.getMeasurestime()!=null){
				call.setMeasuretime(l.getMeasures()+"/"+l.getMeasurestime());
			}
			calls.add(call);
			
		}
		
		return calls;
	}
	

	
	//设备故障显示
	public List<SensorCall> getfailure(SensorCallVo callVo){
		List<SensorCall> calls = new ArrayList<SensorCall>();
		List<AnaloginfoQuery> list = dao.getfailure(callVo);
		for(AnaloginfoQuery l:list){
			SensorCall a = new SensorCall();
			a.setId(l.getId());
			a.setSensorkey(l.getIp()+":"+l.getSensorId()+":"+l.getSensor_type());
			if(l.getPosition()==null){
				a.setPosition("未配置位置"+"/"+l.getAlais()+"/"+l.getSensortype());
			}else{
				a.setPosition(l.getPosition()+"/"+l.getAlais()+"/"+l.getSensortype());
			}
			if(l.getStatus()==5){
				String[] s ={"通讯中断/"+l.getStarttime()};
				a.setAlarmStarttime(s);
			}
			if(l.getDebug()==1){
				String[] s ={"欠压/"+l.getStarttime()};
				a.setAlarmStarttime(s);
			}else if(l.getDebug()==2){
				String[] s ={"故障/"+l.getStarttime()};
				a.setAlarmStarttime(s);
			}
			if(l.getMeasures()!=null&&l.getMeasurestime()!=null){
				a.setMeasuretime(l.getMeasures()+"/"+l.getMeasurestime());
			}
			calls.add(a);
		}
		return calls;
	}
	
	
	//开关量状态变动显示
	@SuppressWarnings("unchecked")
	public List<SwitchSensorCall> getSwitchStateChange(NameTime nameTime){
		List<AnaloginfoQuery> list = dao.getSwitchStateChange(nameTime);
		List<AnaloginfoQuery> list2 = dao.getSwitchSensorCall(null);
		Map<String, AnaloginfoQuery> map = new HashMap<String,AnaloginfoQuery>();
		for(AnaloginfoQuery l:list2){
			map.put(l.getIp()+":"+l.getSensorId()+":"+l.getSensor_type(), l);
		}
		List<SwitchSensorCall> calls = new ArrayList<SwitchSensorCall>();
		if(list!=null&&list.size()>=2){
			for(int i=0;i<list.size()-1;i++){
				AnaloginfoQuery a = list.get(i);
				AnaloginfoQuery b = list.get(i+1);
				if(a.getAlais().equals(b.getAlais())&&a.getMinvalue()!=b.getMinvalue()){
					SwitchSensorCall call = new SwitchSensorCall();
					AnaloginfoQuery query = map.get(b.getIp()+":"+b.getSensorId()+":"+b.getSensor_type());
					if(query!=null&&query.getStarttime()!=null&&query.getEndtime()!=null){
						String [] strings = {"报警/"+query.getStarttime(),"解除/"+query.getEndtime()};
						call.setAlarmStarttime(strings);
					}else{
						String [] strings = {"--"};
						call.setAlarmStarttime(strings);
					}
					call.setSensorkey(b.getIp()+":"+b.getSensorId()+":"+b.getSensor_type());
					Map<Integer, String> powers = JSON.parseObject(b.getAlarmstatus(), Map.class);
					if(b.getSensor_type()==56||b.getSensor_type()==71||b.getAlarm_status()<0){
						call.setAlarmstatus("--");
					}else{
						if(powers.get(b.getAlarm_status())!=null){
							call.setAlarmstatus(powers.get(b.getAlarm_status()));
						}else{
							call.setAlarmstatus("--");
						}
					}
					if(b.getPosition()==null){
						call.setPosition("未配置位置"+"/"+b.getAlais()+"/"+b.getSensortype());
					}else{
						call.setPosition(b.getPosition()+"/"+b.getAlais()+"/"+b.getSensortype());
					}
					call.setChageTime(powers.get((int)b.getMinvalue())+"/"+b.getStarttime());
					if(b.getSensor_type()==56){
						String [] strings = new String[1];
						String string = "";
						if(b.getPosition()==null){
						 	string = "未配置区域";
							if(b.getFeedback()==0){
								string = string+"/无馈电/"+b.getStarttime();
							}else if(b.getFeedback()==1){
								string = string+"/有馈电/"+b.getStarttime();
							}
						}else{
							string = b.getPosition();
							if(b.getFeedback()==0){
								string = string+"/无馈电/"+b.getStarttime();
							}else if(b.getFeedback()==1){
								string = string+"/有馈电/"+b.getStarttime();
							}
						}
						 strings[0] =string;
						call.setFeedstastus(strings);
					}
					calls.add(call);
				}
			}
		}
		return calls;
	}
	
	private String getDay() throws ParseException{
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy_MM_dd");
		Date day = new Date();
		return format1.format(day);
	}
}
