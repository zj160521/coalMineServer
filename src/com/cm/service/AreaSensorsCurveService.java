package com.cm.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import util.UtilMethod;

import com.cm.controller.ResultObj;
import com.cm.dao.AreaSensorCurveDao;
import com.cm.entity.vo.AnalogParamVo;
import com.cm.entity.vo.AnalogQueryVo;
import com.cm.entity.vo.AreaSensorVo;
import com.cm.entity.vo.SensorRecsVo;

@Service(value = "AreaSensorsCurveService")
public class AreaSensorsCurveService implements SingleCurveInterface {

	@Autowired
	private ResultObj result;
	
	@Autowired
	private AreaSensorCurveDao theDao;
	
	private int totalPages = 0;
	private int totalRecs = 0;
	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private Map<String,List<AnalogQueryVo>> alarmMap;
	
	/**
	 * 获取区域传感器曲线数据
	 */
	public ResultObj getData(AnalogParamVo analogParamVo) {
		totalPages = 0;
		totalRecs = 0;
		boolean isDetail = analogParamVo.getType() == 1 ? true : false;
		
		List<AreaSensorVo> allSensor = theDao.getAllSensorByArea(analogParamVo.getId());
		if(UtilMethod.notEmptyList(allSensor)){
			List<AreaSensorVo> sensorList = pageHelper(allSensor, analogParamVo.getCur_page(), analogParamVo.getPage_rows());
			if(isDetail){
				Calendar cal = Calendar.getInstance();
				String endTime = df.format(cal.getTime());
				cal.add(Calendar.HOUR, -1);
				String startTime = df.format(cal.getTime());
				List<AnalogQueryVo> alarmRecs = theDao.getAlarmRecs(sensorList, startTime, endTime);
				getAlarmMap(alarmRecs);
				List<SensorRecsVo> sensorRecs = theDao.getSensorRecs(sensorList, startTime);
				Map<String, List<SensorRecsVo>> recsMap = getRecsMap(sensorRecs);
				result.put("data", recsMap);
			}else{
				String starttimeOfDay = UtilMethod.getStarttimeOfDay();
				String endtimeOfDay = UtilMethod.getEndtimeOfDay();
				List<AnalogQueryVo> alarmRecs = theDao.getAlarmRecs(sensorList, starttimeOfDay, endtimeOfDay);
				
				
			}
		}else{
			result.put("data", new ArrayList<>());
		}
		
		result.put("cur_page", analogParamVo.getCur_page());
		result.put("page_rows", analogParamVo.getPage_rows());
		result.put("totalPages", totalPages);
		result.put("totalRecs", totalRecs);
		result.setStatus(0, "ok");
		return result;
	}
	
	public List<AreaSensorVo> pageHelper(List<AreaSensorVo> resultList, int cur_page, int page_rows){
		
		ArrayList<AreaSensorVo> list = new ArrayList<AreaSensorVo>();
		
		int recordNum = (cur_page-1) * page_rows;
		for(int i = 0; i < page_rows; i++){
			if(i + recordNum >= resultList.size()) break;
			AreaSensorVo object = resultList.get(i + recordNum);
			list.add(object);
		}
		totalRecs = resultList.size();
		totalPages = (resultList.size()%page_rows > 0) ? resultList.size()/page_rows + 1 : resultList.size()/page_rows;
		
		return list;
	}
	
	public Map<String,List<AnalogQueryVo>> getAlarmMap(List<AnalogQueryVo> alarmRecs){
		alarmMap = new HashMap<String, List<AnalogQueryVo>>();
		for(AnalogQueryVo aqv : alarmRecs){
			if(Math.abs(aqv.getStatus()) == 4 || Math.abs(aqv.getStatus()) == 3 || Math.abs(aqv.getStatus()) == 2 || aqv.getStatus() == 5){
				String key = aqv.getIp() + ":" + aqv.getSensor_id() + ":" + aqv.getType();
				List<AnalogQueryVo> list = alarmMap.get(key);
				if(list == null){
					list = new ArrayList<AnalogQueryVo>();
				}
				list.add(aqv);
				alarmMap.put(key, list);
			}
		}
		return alarmMap;
	}
	
	public Map<String,List<SensorRecsVo>> getRecsMap(List<SensorRecsVo> sensorRecs){
		Map<String,List<SensorRecsVo>> map = new HashMap<String, List<SensorRecsVo>>();
		for(SensorRecsVo sr : sensorRecs){
			String key = sr.getIp() + ":" + sr.getDevid() + ":" + sr.getType();
			List<AnalogQueryVo> alarmList = alarmMap.get(key);
			if(UtilMethod.notEmptyList(alarmList)){
				for(AnalogQueryVo alarm : alarmList){
					try {
						if(UtilMethod.isMid(sr.getResponsetime(), alarm.getStartTime(), alarm.getEndTime())){
							sr.setIsAlarm(1);
							break;
						}
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			}
			
			List<SensorRecsVo> list = map.get(key);
			if(list == null){
				list = new ArrayList<SensorRecsVo>();
			}
			list.add(sr);
			map.put(key, list);
		}
		return map;
	}
	
}
