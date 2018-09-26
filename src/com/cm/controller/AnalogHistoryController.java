package com.cm.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.cm.entity.vo.AnalogParamVo;
import com.cm.entity.vo.AnaloghisVo;
import com.cm.entity.vo.DevVo;
import com.cm.entity.vo.ResultVo;
import com.cm.entity.vo.SensorVo;
import com.cm.security.LoginManage;
import com.cm.service.AnalogHistoryService;
import com.cm.service.SingleCurveInterface;


@Scope("prototype")
@Controller
@RequestMapping("/analogStatement")
public class AnalogHistoryController {

	@Autowired
	private ResultObj result;
	@Autowired
	private LoginManage loginManage;
	@Autowired
	private AnalogHistoryService analogHistoryService;
	private double max = 0;
	private double min = 0;
	private double total = 0 ;
	private double avg = 0;
	
	@RequestMapping(value = "/getHistory", method = RequestMethod.POST)
	@ResponseBody
	public Object getData(
			@RequestBody AnalogParamVo analogParamVo,
			HttpServletRequest request) throws ParseException {
		if(!loginManage.isUserLogin(request))
			return result.setStatus(-1, "no login");
		
		WebApplicationContext springMVCContext = RequestContextUtils.getWebApplicationContext(request);
		SingleCurveInterface service = (SingleCurveInterface) springMVCContext.getBean(analogParamVo.getTypeName());
		ResultObj resultList = service.getData(analogParamVo);
		
		return resultList;
	}
	
	@RequestMapping(value = "/getHistoryRcs", method = RequestMethod.POST)
	@ResponseBody
	public Object getRcs(
			@RequestBody AnalogParamVo analogParamVo,
			HttpServletRequest request) throws ParseException {
		if(!loginManage.isUserLogin(request))
			return result.setStatus(-1, "no login");
		
		Map<String, List<AnaloghisVo>> map = new HashMap<String,List<AnaloghisVo>>();
		
		List<AnaloghisVo> resultList = analogHistoryService.getHistoryRcs(analogParamVo);
		
		for(AnaloghisVo av : resultList){
			String key = av.getIp()+":"+av.getDevid();
			List<AnaloghisVo> list = map.get(key);
			if(list == null){
				list = new ArrayList<AnaloghisVo>();
				list.add(av);
			}else
				list.add(av);
			map.put(key, list);
		}
		
		LinkedList<SensorVo> sensor = analogHistoryService.getAllSensor(analogParamVo.getEqp());
		
		int count = 0;
		for(DevVo dv : analogParamVo.getEqp()){
			analogParamVo.setDevid(dv.getDevid());
			analogParamVo.setIp(dv.getIp());
			
			List<AnaloghisVo> dataList = map.get(dv.getIp()+":"+dv.getDevid());
//			List<AnalogQueryVo> analogQry = analogHistoryService.getAnalogQry(analogParamVo.getDevid(),analogParamVo.getIp(),analogParamVo.getStartTime());
			
			max = 0; min = 0; avg = 0;
			ResultVo rv = new ResultVo();
			if(dataList != null && dataList.size() > 0){
				getMaxMinAvg2(dataList, sensor.get(count), rv);
			}else
				dataList = new ArrayList<AnaloghisVo>();
			
			rv.setMax(max);
			rv.setMin(min);
			rv.setAvg(avg);
			rv.setData(dataList);
			
			result.put(dv.getIp()+":"+dv.getDevid(), rv);
			count++;
		}
		
		return result.setStatus(0, "ok");
	}
	
	public void getMaxMinAvg2(List<AnaloghisVo> resultList, SensorVo sensor,ResultVo rv){
		for(AnaloghisVo af : resultList){
			setAlarm2(af,sensor);
			setData(rv,sensor);
			
			if(af.getAvalue() > max)
				max = af.getAvalue();
			if(min == 0)
				min = af.getAvalue();
			else if(af.getAvalue() < min)
				min = af.getAvalue();
			
			total += af.getAvalue();
		}
		avg = total/resultList.size();
	}
	
	public void setAlarm2(AnaloghisVo af, SensorVo sensor){
		if(af.getAvalue() >= sensor.getLimit_alarm()){
			af.setAlarmStatus("报警");
		}else af.setAlarmStatus("解除");
		
		if(af.getAvalue() >= sensor.getLimit_power())
			af.setPowerStatus("断电");
		else af.setPowerStatus("复电");
	}
	
	public void setData(ResultVo af, SensorVo sensor){
		af.setPosition(sensor.getPosition());
		af.setAlais(sensor.getAlais());
		af.setLimit_warning(sensor.getLimit_alarm());
		af.setLimit_power(sensor.getLimit_power());
		af.setLimit_repower(sensor.getLimit_repower());
		af.setAreaname(sensor.getAreaname());
	}
}
