package com.cm.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.cm.controller.ResultObj;
import com.cm.controller.strategy.Context;
import com.cm.controller.strategy.StrategyA;
import com.cm.controller.strategy.StrategyB;
import com.cm.entity.AreaAttrib;
import com.cm.entity.AreaSensor;
import com.cm.entity.PositionStrategy;
import com.cm.entity.PositionStrategy2;
import com.cm.entity.Sensor;
import com.cm.entity.SwitchSensor;
import com.cm.entity.vo.All_Senser;
import com.cm.security.LoginManage;
import com.cm.service.BaseinfoService;
import com.cm.service.StategyService;
import com.cm.service.SwitchSensorService;

import util.LogOut;


@Scope("prototype")
@Controller
@RequestMapping("/strategy")
public class StrategyController {

	@Autowired
	private ResultObj result;

	@Autowired
	private LoginManage loginManage;

	@Autowired
	private StrategyA sa;
	
	@Autowired
	private StrategyB sb;
	
	@Autowired
	private StategyService ss;
	
	@Autowired
    private SwitchSensorService switchSensorService;
	
	@Autowired
    private BaseinfoService baseinfoService;
	
	private Context context=null;
	
	@RequestMapping(value = "/getStrategy", method = RequestMethod.POST)
	@ResponseBody
	public Object getStrategy(@RequestBody PositionStrategy2 ps2, HttpServletRequest request) {
		if (!loginManage.isUserLogin(request))
			return result.setStatus(-1, "no login");
		
		context=new Context(sa);
		result.put("data", context.dofilter(ps2));
		
		return result.setStatus(0, "ok");
	}
	
	@RequestMapping(value = "/addAreaPos", method = RequestMethod.POST)
	@ResponseBody
	public Object addAreaPos(@RequestBody PositionStrategy ps, HttpServletRequest request) {
		if (!loginManage.isUserLogin(request))
			return result.setStatus(-1, "no login");
		
		context=new Context(sa);
		try {
			context.addAreaCon(ps);
		} catch (Exception e) {
			e.printStackTrace();
			LogOut.log.info("异常：", e);
			result.setStatus(-2, "错误！");
		}
		String remark = JSONObject.toJSONString(ps);
        String operation2 = "新增区域规则配置："+ps.getArea_type();
		loginManage.addLog(request, remark, operation2, 1519);
		return result.setStatus(0, "ok");
	}
	
	@RequestMapping(value = "/addAreaSenOne", method = RequestMethod.POST)
	@ResponseBody
	public Object addAreaPos(@RequestBody List<LinkedHashMap<String, Object>> as, HttpServletRequest request) {
		if (!loginManage.isUserLogin(request))
			return result.setStatus(-1, "no login");
		
		context=new Context(sb);
		try {
			if (as.size() > 0) {
				AreaSensor ass = new AreaSensor();
				ass.setArea_id((int) as.get(0).get("area_id"));
				ass.setArea_pos_id((int) as.get(0).get("area_pos_id"));
				ss.addAreaSen(ass, as);
			} else {
				return result.setStatus(-2, "参数错误！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			LogOut.log.info("异常：", e);
			return result.setStatus(-2, "传感器添加重复！");
		}
		String remark = JSONObject.toJSONString(as);
        String operation2 = "修改区域传感器规则配置";
		loginManage.addLog(request, remark, operation2, 1518);
		return result.setStatus(0, "ok");
	}
	
	@RequestMapping(value = "/getAreaSen", method = RequestMethod.POST)
	@ResponseBody
	public Object getStrategy(@RequestBody AreaSensor as,HttpServletRequest request) {
		if (!loginManage.isUserLogin(request))
			return result.setStatus(-1, "no login");
		
		context=new Context(sb);
		result.put("data", context.dofilter(as));
		
		return result.setStatus(0, "ok");
	}
	
	@RequestMapping(value = "/getAreaAttrib", method = RequestMethod.POST)
	@ResponseBody
	public Object getAreaAttrib(@RequestBody AreaAttrib as,HttpServletRequest request) {
		if (!loginManage.isUserLogin(request))
			return result.setStatus(-1, "no login");
		if(as.getArea_id()==0){
			return result.setStatus(-2, "缺少参数！");
		}
		result.put("data", ss.getAreaAttrib(as));
		
		return result.setStatus(0, "ok");
	}
	
	@RequestMapping(value = "/addupAreaAttrib", method = RequestMethod.POST)
	@ResponseBody
	public Object addAreaAttrib(@RequestBody List<LinkedHashMap<String, Object>> as,HttpServletRequest request) {
		if (!loginManage.isUserLogin(request))
			return result.setStatus(-1, "no login");
		
		try {
			if(as.size()>0){
				AreaAttrib ass=new AreaAttrib();
				ass.setArea_id((int) as.get(0).get("area_id"));
				ass.setArea_pos_id((int) as.get(0).get("area_pos_id"));
				if((int)as.get(0).get("area_pos_id")>0){
					ss.addAreaAttrib(ass,as);
				}
			}else{
				return result.setStatus(-2, "参数错误！");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			LogOut.log.info("异常：", e);
			return result.setStatus(-2, "错误！");
		}
		String remark = JSONObject.toJSONString(as);
        String operation2 = "修改区域传感器配置规则";
		loginManage.addLog(request, remark, operation2, 1518);
		return result.setStatus(0, "ok");
	}
	
	@RequestMapping(value = "/delStrategy", method = RequestMethod.POST)
	@ResponseBody
	public Object addAreaAttrib(@RequestBody PositionStrategy2 as,HttpServletRequest request) {
		if (!loginManage.isUserLogin(request))
			return result.setStatus(-1, "no login");
		
		if(as.getType_id()!=0){
			return result.setStatus(-2, "非自定义模板不能删除！");
		}
		
		try {
			ss.delStrategy(as);
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-2, "错误！");
		}
		
		return result.setStatus(0, "ok");
	}
	
	@RequestMapping(value = "/getEnable", method = RequestMethod.GET)
	@ResponseBody
	public Object getEnable(HttpServletRequest request) {
		if (!loginManage.isUserLogin(request))
			return result.setStatus(-1, "no login");
		
		All_Senser all = new All_Senser();
		List<Sensor> list3 = new ArrayList<Sensor>();
		List<SwitchSensor> list2 = switchSensorService.AllSwitchSensor();
		List<Sensor> slist = baseinfoService.getAllSensor2();
		for (Sensor sensor : slist) {
            sensor.setPid(100);
            sensor.setList_type(3);
            if (sensor.getSensor_type() == 32 || sensor.getSensor_type() == 33 || sensor.getSensor_type() == 34 || sensor.getSensor_type() == 35 || sensor.getSensor_type() == 70 || sensor.getSensor_type() == 80) {
                sensor.setIsmethane(1);
            }
            if (sensor.getIsDrainage() != 1) {
            	list3.add(sensor);
            }
        }
		
		Set<String> set=new HashSet<String>();
		List<AreaSensor> list=ss.getTAreaSensor();
		for(AreaSensor as:list){
			set.add(as.getUid());
		}
		
		Iterator<Sensor> it=list3.iterator();
		while(it.hasNext()){
			Sensor s=it.next();
			if(set.contains(s.getUid())||s.getIslogical()!=1){
				it.remove();
			}
		}
		
		Iterator<SwitchSensor> it2=list2.iterator();
		while(it2.hasNext()){
			SwitchSensor s=it2.next();
			if(set.contains(s.getUid())||s.getIslogical()!=1){
				it2.remove();
			}
		}
		
		all.setList2(list2);
		all.setList3(list3);
		result.put("data", all);
		return result.setStatus(0, "ok");
	}
}
