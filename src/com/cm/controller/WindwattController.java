package com.cm.controller;


import com.cm.dao.DevLinkDao;
import com.cm.dao.ISwitchDao;
import com.cm.entity.DevAction;
import com.cm.entity.SwitchSensor;
import com.cm.entity.Windwatt;
import com.cm.entity.vo.DevLinkVo;
import com.cm.entity.vo.DevLogicVo;
import com.cm.entity.vo.WindwattVo;
import com.cm.security.LoginManage;
import com.cm.service.WindwattService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Scope("prototype")
@Controller
@RequestMapping("/windwatt")
public class WindwattController {
	
	
	@Autowired
	private ResultObj result;

	@Autowired
	private LoginManage loginManage;
	
	@Autowired
	private WindwattService service;
	
	@Autowired
	private DevLinkDao devLinkDao;
	
	@Autowired
	private ISwitchDao switchDao;
	
	private static final String CUTOUT_ACTION = "cutout";
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/addup", method = RequestMethod.POST)
	@ResponseBody
	public Object addupWindwatt(@RequestBody Windwatt windwatt, HttpServletRequest request) {
		if (!loginManage.isUserLogin(request)) {
			return result.setStatus(-1, "no login");
		}
		Object per = loginManage.checkPermission(request);
		if (null != per)
			return per;
		try {
			if(windwatt.getName()==null||windwatt.getName()==""){
				return result.setStatus(-2, "请输入风瓦电闭锁配置名称！");
			}
			List<WindwattVo> list = windwatt.getList();
			if(windwatt.getId()>0){
				service.updateWindwatt(windwatt);
				for(WindwattVo l:list){
					l.setWindwattId(windwatt.getId());
					service.updateWindwattSensor(l);
				}
				if(windwatt.getId()>0){
					deleteDev(windwatt.getId());
					addDevdata(windwatt);
				}
			}else if(windwatt.getId()==0){
				Windwatt windwatt2 = service.addWindwatt(windwatt);
				windwatt.setId(windwatt2.getId());
				for(WindwattVo l:list){
					l.setWindwattId(windwatt.getId());
				}
				service.addWindwattSensor(list);
				if(windwatt.getId()>0){
					deleteDev(windwatt.getId());
					addDevdata(windwatt);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "添加风瓦电闭锁配置出错！");
		}
		return result.setStatus(0, "ok");
	}
	
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Object deleteWindwatt(@PathVariable int id, HttpServletRequest request) {
		if (!loginManage.isUserLogin(request)) {
			return result.setStatus(-1, "no login");
		}
		Object per = loginManage.checkPermission(request);
		if (null != per)
			return per;
		try {
			service.deleteWindwattSensor(id);
			service.deleteWindwatt(id);
			deleteDev(id);
			
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "删除风瓦电闭锁配置出错！");
		}
		return result.setStatus(0, "ok");
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getall", method = RequestMethod.GET)
	@ResponseBody
	public Object getallWindwatt( HttpServletRequest request) {
		if (!loginManage.isUserLogin(request)) {
			return result.setStatus(-1, "no login");
		}
		
		try {
			List<Windwatt> list = service.getallWindwatt();
			result.put("data", list);
			
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "获取风瓦电闭锁配置出错！");
		}
		return result.setStatus(0, "ok");
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public  void addDevdata(Windwatt windwatt){
		
		List<WindwattVo> list = windwatt.getList();
		
		DevLinkVo group1 = new DevLinkVo();
		group1.setPid(windwatt.getId());
		group1.setAreaId(windwatt.getAreaId());
		group1.setName(windwatt.getName());
		group1.setAlarm(windwatt.getAlarm());
		group1.setIsUse(1);
		
		DevLinkVo group2 = new DevLinkVo();
		group2.setPid(windwatt.getId());
		group2.setAreaId(windwatt.getAreaId());
		group2.setName(windwatt.getName());
		group2.setAlarm(windwatt.getAlarm());
		group2.setIsUse(1);
		
		DevLinkVo group3 = new DevLinkVo();
		group3.setPid(windwatt.getId());
		group3.setAreaId(windwatt.getAreaId());
		group3.setName(windwatt.getName());
		group3.setAlarm(windwatt.getAlarm());
		group3.setIsUse(1);
		
		DevLinkVo group4 = new DevLinkVo();
		group4.setPid(windwatt.getId());
		group4.setAreaId(windwatt.getAreaId());
		group4.setName(windwatt.getName());
		group4.setAlarm(windwatt.getAlarm());
		group4.setIsUse(1);
		
		DevLinkVo group5 = new DevLinkVo();
		group5.setPid(windwatt.getId());
		group5.setAreaId(windwatt.getAreaId());
		group5.setName(windwatt.getName());
		group5.setAlarm(windwatt.getAlarm());
		group5.setIsUse(1);
		List<DevLogicVo> list3 = new ArrayList<DevLogicVo>();
		List<DevAction> list4 = new ArrayList<DevAction>();
		
		Map<String, WindwattVo> map = new HashMap<String, WindwattVo>();
		for(WindwattVo l:list){
			WindwattVo a = map.get(l.getSerialnumber());
			if(a==null){
				map.put(l.getSerialnumber(),l);
			}
		}
		
		WindwattVo a1 = map.get("A1");
		WindwattVo a2 = map.get("A2");
		WindwattVo a3 = map.get("A3");
		WindwattVo b1 = map.get("B1");
		WindwattVo b2 = map.get("B2");
		WindwattVo c1 = map.get("C1");
		SwitchSensor c1_sensor = switchDao.getbyUid(c1.getUid());
		WindwattVo c2 = map.get("C2");
		SwitchSensor c2_sensor = switchDao.getbyUid(c2.getUid());
		WindwattVo c3 = map.get("C3");
		SwitchSensor c3_sensor = switchDao.getbyUid(c3.getUid());
		WindwattVo c4 = map.get("C4");
		SwitchSensor c4_sensor = switchDao.getbyUid(c4.getUid());
		WindwattVo c5 = map.get("C5");
		SwitchSensor c5_sensor = switchDao.getbyUid(c5.getUid());
		WindwattVo c6 = map.get("C6");
		SwitchSensor c6_sensor = switchDao.getbyUid(c6.getUid());
		
		String a1_group2_lgexps = null;
		String a1_group2_rpexps = null;
		String a1_group5_lgexps = null;
		String a1_group5_rpexps = null;
		String a1_group1_lgexps = null;
		String a1_group1_rpexps = null;
		String a2_group2_lgexps = null;
		String a2_group2_rpexps = null;
		String a2_group5_lgexps = null;
		String a2_group5_rpexps = null;
		String a3_group3_lgexps = null;
		String a3_group3_rpexps = null;
		String b1_group4_lgexps = null;
		String b1_group4_rpexps = null;
		String b1_group5_lgexps = null;
		String b1_group5_rpexps = null;
		String b2_group4_lgexps = null;
		String b2_group4_rpexps = null;
		
		if(a1.getUid()!=null&&a1.getUid()!=""){
			a1_group1_lgexps = "("+a1.getUid()+">="+a1.getAlarmvalue()+")";
			a1_group1_rpexps = "("+a1.getUid()+"<"+a1.getAlarmvalue()+")";
			a1_group2_lgexps = "("+a1.getUid()+">="+a1.getPowervalue()+") or ("+a1.getUid()+"_S==5)";
			a1_group2_rpexps = "("+a1.getUid()+"<"+a1.getAlarmvalue()+") and ("+a1.getUid()+"_S==0)";
			a1_group5_lgexps = "("+a1.getUid()+">="+(a1.getAlarmvalue()*3)+")";
			a1_group5_rpexps = "("+a1.getUid()+"<"+a1.getPowervalue()+")";
			group1.setLgcExps(a1_group1_lgexps);
			group1.setRepowerExps(a1_group1_rpexps);
			group2.setLgcExps(a1_group2_lgexps);
			group2.setRepowerExps(a1_group2_rpexps);
			group5.setLgcExps(a1_group5_lgexps);
			group5.setRepowerExps(a1_group5_rpexps);
		}
		if(a2.getUid()!=null&&a2.getUid()!=""){
			a2_group2_lgexps = "("+a2.getUid()+">="+a2.getAlarmvalue()+") or ("+a2.getUid()+"_S==5)";
			a2_group2_rpexps = "("+a2.getUid()+"<"+a2.getAlarmvalue()+") and ("+a2.getUid()+"_S==0)";
			a2_group5_lgexps = "("+a2.getUid()+">="+(a2.getAlarmvalue()*3)+")";
			a2_group5_rpexps = "("+a2.getUid()+"<"+a2.getPowervalue()+")";
			group2.setLgcExps(a2_group2_lgexps);
			group2.setRepowerExps(a2_group2_rpexps);
			group5.setLgcExps(a2_group5_lgexps);
			group5.setRepowerExps(a2_group5_rpexps);
		}
		if(a3.getUid()!=null&&a3.getUid()!=""){
			a3_group3_lgexps = "("+a3.getUid()+">="+a3.getAlarmvalue()+") or ("+a3.getUid()+"_S==5)";
			a3_group3_rpexps = "("+a3.getUid()+"<"+a3.getAlarmvalue()+") and ("+a3.getUid()+"_S==0)";
			group3.setLgcExps(a3_group3_lgexps);
			group3.setRepowerExps(a3_group3_rpexps);
		}
		if(b1.getUid()!=null&&b1.getUid()!=""){
			if((int)b1.getPowervalue()==0){
				b1_group4_lgexps = "("+b1.getUid()+"=="+b1.getPowervalue()+") or ("+b1.getUid()+"_S==5)";
				b1_group4_rpexps = "("+b1.getUid()+"==1"+") and ("+b1.getUid()+"_S==0)";
				b1_group5_lgexps = "("+b1.getUid()+"=="+b1.getPowervalue()+")";
				b1_group5_rpexps = "("+b1.getUid()+"==1"+")";
			}else if((int)b1.getPowervalue()==1){
				b1_group4_lgexps = "("+b1.getUid()+"=="+b1.getPowervalue()+") or ("+b1.getUid()+"_S==5)";
				b1_group4_rpexps = "("+b1.getUid()+"==0"+") and ("+b1.getUid()+"_S==0)";
				b1_group5_lgexps = "("+b1.getUid()+"=="+b1.getPowervalue()+")";
				b1_group5_rpexps = "("+b1.getUid()+"==0"+")";
			}
			group4.setLgcExps(b1_group4_lgexps);
			group4.setRepowerExps(b1_group4_rpexps);
		}
		if(b2.getUid()!=null&&b2.getUid()!=""){
			if((int)b2.getPowervalue()==0){
				b2_group4_lgexps = "("+b2.getUid()+"=="+b2.getPowervalue()+") or ("+b2.getUid()+"_S==5)";
				b2_group4_rpexps = "("+b2.getUid()+"==1"+") and ("+b2.getUid()+"_S==0)";
			}else if((int)b2.getPowervalue()==1){
				b2_group4_lgexps = "("+b2.getUid()+"=="+b2.getPowervalue()+") or ("+b2.getUid()+"_S==5)";
				b2_group4_rpexps = "("+b2.getUid()+"==0"+") and ("+b2.getUid()+"_S==0)";
			}
			group4.setLgcExps(b2_group4_lgexps);
			group4.setRepowerExps(b2_group4_rpexps);
		}
		if(a1.getUid()!=null&&a2.getUid()!=null&&a1.getUid()!=""&&a2.getUid()!=""){
			group2.setLgcExps("["+a1_group2_lgexps+","+a2_group2_lgexps+"].count(True)>=1");
			group2.setRepowerExps(a1_group2_rpexps+" and "+a2_group2_rpexps);
		}
		if(b1.getUid()!=null&&b2.getUid()!=null&&b1.getUid()!=""&&b2.getUid()!=""){
			group4.setLgcExps("["+b1_group4_lgexps+","+b2_group4_lgexps+"].count(True)>=1");
			group4.setRepowerExps(b1_group4_rpexps+" and "+b2_group4_rpexps);
		}
		if(a1.getUid()!=null&&a2.getUid()!=null&&b1.getUid()!=null&&a1.getUid()!=""&&a2.getUid()!=""&&b1.getUid()!=""){
			group5.setLgcExps("("+a1_group5_lgexps+" or "+a2_group5_lgexps+")"+" and "+b1_group5_lgexps);
			group5.setRepowerExps(a1_group5_rpexps+" and "+a2_group5_rpexps+" and "+b1_group4_rpexps);
		}else if(a1.getUid()!=null&&b1.getUid()!=null&&a2.getUid()==null&&a1.getUid()!=""&&b1.getUid()!=""||a1.getUid()!=null&&b1.getUid()!=null&&a2.getUid()==""&&a1.getUid()!=""&&b1.getUid()!=""){
			group5.setLgcExps(a1_group5_lgexps+" and "+b1_group5_lgexps);
			group5.setRepowerExps(a1_group5_rpexps+" and "+b1_group5_rpexps);
		}else if(a2.getUid()!=null&&b1.getUid()!=null&&a1.getUid()==null&&a2.getUid()!=""&&b1.getUid()!=""||a2.getUid()!=null&&b1.getUid()!=null&&a1.getUid()==""&&a2.getUid()!=""&&b1.getUid()!=""){
			group5.setLgcExps(a2_group5_lgexps+" and "+b1_group5_lgexps);
			group5.setRepowerExps(a2_group5_rpexps+" and "+b1_group5_rpexps);
		}else if(a1.getUid()!=null&&a1.getUid()!=""&&a2.getUid()!=null&&a2.getUid()!=""&&b1.getUid()==null||a1.getUid()!=null&&a1.getUid()!=""&&a2.getUid()!=null&&a2.getUid()!=""&&b1.getUid()==""){
			group5.setLgcExps(a1_group5_lgexps+" or "+a2_group5_lgexps);
			group5.setRepowerExps(a1_group5_rpexps+" and "+a2_group5_rpexps);
		}
		
		if(group1.getLgcExps()!=null){
			devLinkDao.addLgcGroup(group1);
			DevLogicVo vo = new DevLogicVo();
			vo.setGroupId(group1.getId());
			vo.setUid(a1.getUid());
			list3.add(vo);
			if(c5.getUid()!=null&&c5.getUid()!=""){
				DevAction action_c5 = new DevAction();
				action_c5.setActDevid(c5_sensor.getId());
				action_c5.setDev(c5_sensor.getAlais());
				action_c5.setAction(CUTOUT_ACTION);
				action_c5.setParam(""+(int)c5.getPowervalue());
				action_c5.setDevType(c5_sensor.getSensor_type());
				action_c5.setIp(c5_sensor.getIpaddr());
				action_c5.setSensorId(c5_sensor.getSensorId());
				action_c5.setUid(c5_sensor.getUid());
				action_c5.setGroupId(group1.getId());
				list4.add(action_c5);
			}
		}
		if(group2.getLgcExps()!=null){
			devLinkDao.addLgcGroup(group2);
			if(a1.getUid()!=null&&a1.getUid()!=""){
				DevLogicVo vo = new DevLogicVo();
				vo.setGroupId(group2.getId());
				vo.setUid(a1.getUid());
				list3.add(vo);
			}
			if(a2.getUid()!=null&&a2.getUid()!=""){
				DevLogicVo vo = new DevLogicVo();
				vo.setGroupId(group2.getId());
				vo.setUid(a2.getUid());
				list3.add(vo);
			}
			if(c5.getUid()!=null&&c5.getUid()!=""){
				DevAction action_c5 = new DevAction();
				action_c5.setActDevid(c5_sensor.getId());
				action_c5.setDev(c5_sensor.getAlais());
				action_c5.setAction(CUTOUT_ACTION);
				action_c5.setParam(""+(int)c5.getPowervalue());
				action_c5.setDevType(c5_sensor.getSensor_type());
				action_c5.setIp(c5_sensor.getIpaddr());
				action_c5.setSensorId(c5_sensor.getSensorId());
				action_c5.setUid(c5_sensor.getUid());
				action_c5.setGroupId(group2.getId());
				list4.add(action_c5);
			}
			if(c1.getUid()!=null&&c1.getUid()!=""){
				DevAction action = new DevAction();
				action.setGroupId(group2.getId());
				action.setActDevid(c1_sensor.getId());
				action.setDev(c1_sensor.getAlais());
				action.setAction(CUTOUT_ACTION);
				action.setParam(""+(int)c1.getPowervalue());
				action.setDevType(c1_sensor.getSensor_type());
				action.setIp(c1_sensor.getIpaddr());
				action.setSensorId(c1_sensor.getSensorId());
				action.setUid(c1_sensor.getUid());
				list4.add(action);
			}
		}
		if(group3.getLgcExps()!=null){
			devLinkDao.addLgcGroup(group3);
			DevLogicVo vo = new DevLogicVo();
			vo.setGroupId(group3.getId());
			vo.setUid(a3.getUid());
			list3.add(vo);
			if(c5.getUid()!=null&&c5.getUid()!=""){
				DevAction action_c5 = new DevAction();
				action_c5.setActDevid(c5_sensor.getId());
				action_c5.setDev(c5_sensor.getAlais());
				action_c5.setAction(CUTOUT_ACTION);
				action_c5.setParam(""+(int)c5.getPowervalue());
				action_c5.setDevType(c5_sensor.getSensor_type());
				action_c5.setIp(c5_sensor.getIpaddr());
				action_c5.setSensorId(c5_sensor.getSensorId());
				action_c5.setUid(c5_sensor.getUid());
				action_c5.setGroupId(group3.getId());
				list4.add(action_c5);
			}
			if(c4.getUid()!=null&&c4.getUid()!=""){
				DevAction action = new DevAction();
				action.setGroupId(group3.getId());
				action.setActDevid(c4_sensor.getId());
				action.setDev(c4_sensor.getAlais());
				action.setAction(CUTOUT_ACTION);
				action.setParam(""+(int)c4.getPowervalue());
				action.setDevType(c4_sensor.getSensor_type());
				action.setIp(c4_sensor.getIpaddr());
				action.setSensorId(c4_sensor.getSensorId());
				action.setUid(c4_sensor.getUid());
				list4.add(action);
			}
		}
		if(group4.getLgcExps()!=null){
			devLinkDao.addLgcGroup(group4);
			if(b1.getUid()!=null&&b1.getUid()!=""){
				DevLogicVo vo = new DevLogicVo();
				vo.setGroupId(group4.getId());
				vo.setUid(b1.getUid());
				list3.add(vo);
			}
			if(b2.getUid()!=null&&b2.getUid()!=""){
				DevLogicVo vo = new DevLogicVo();
				vo.setGroupId(group4.getId());
				vo.setUid(b2.getUid());
				list3.add(vo);
			}
			if(c5.getUid()!=null&&c5.getUid()!=""){
				DevAction action_c5 = new DevAction();
				action_c5.setActDevid(c5_sensor.getId());
				action_c5.setDev(c5_sensor.getAlais());
				action_c5.setAction(CUTOUT_ACTION);
				action_c5.setParam(""+(int)c5.getPowervalue());
				action_c5.setDevType(c5_sensor.getSensor_type());
				action_c5.setIp(c5_sensor.getIpaddr());
				action_c5.setSensorId(c5_sensor.getSensorId());
				action_c5.setUid(c5_sensor.getUid());
				action_c5.setGroupId(group4.getId());
				list4.add(action_c5);
			}
			if(c3.getUid()!=null&&c3.getUid()!=""){
				DevAction action = new DevAction();
				action.setGroupId(group4.getId());
				action.setActDevid(c3_sensor.getId());
				action.setDev(c3_sensor.getAlais());
				action.setAction(CUTOUT_ACTION);
				action.setParam(""+(int)c3.getPowervalue());
				action.setDevType(c3_sensor.getSensor_type());
				action.setIp(c3_sensor.getIpaddr());
				action.setSensorId(c3_sensor.getSensorId());
				action.setUid(c3_sensor.getUid());
				list4.add(action);
			}
		}
		if(group5.getLgcExps()!=null){
			devLinkDao.addLgcGroup(group5);
			if(a1.getUid()!=null&&a1.getUid()!=""){
				DevLogicVo vo = new DevLogicVo();
				vo.setGroupId(group5.getId());
				vo.setUid(a1.getUid());
				list3.add(vo);
			}
			if(a2.getUid()!=null&&a2.getUid()!=""){
				DevLogicVo vo = new DevLogicVo();
				vo.setGroupId(group5.getId());
				vo.setUid(a2.getUid());
				list3.add(vo);
			}
			if(b1.getUid()!=null&&b1.getUid()!=""){
				DevLogicVo vo = new DevLogicVo();
				vo.setGroupId(group5.getId());
				vo.setUid(b1.getUid());
				list3.add(vo);
			}
			if(c5.getUid()!=null&&c5.getUid()!=""){
				DevAction action_c5 = new DevAction();
				action_c5.setActDevid(c5_sensor.getId());
				action_c5.setDev(c5_sensor.getAlais());
				action_c5.setAction(CUTOUT_ACTION);
				action_c5.setParam(""+(int)c5.getPowervalue());
				action_c5.setDevType(c5_sensor.getSensor_type());
				action_c5.setIp(c5_sensor.getIpaddr());
				action_c5.setSensorId(c5_sensor.getSensorId());
				action_c5.setUid(c5_sensor.getUid());
				action_c5.setGroupId(group5.getId());
				list4.add(action_c5);
			}
			if(c1.getUid()!=null&&c1.getUid()!=""){
				DevAction action = new DevAction();
				action.setGroupId(group5.getId());
				action.setActDevid(c1_sensor.getId());
				action.setDev(c1_sensor.getAlais());
				action.setAction(CUTOUT_ACTION);
				action.setParam(""+(int)c1.getPowervalue());
				action.setDevType(c1_sensor.getSensor_type());
				action.setIp(c1_sensor.getIpaddr());
				action.setSensorId(c1_sensor.getSensorId());
				action.setUid(c1_sensor.getUid());
				list4.add(action);
			}
			if(c2.getUid()!=null&&c2.getUid()!=""){
				DevAction action = new DevAction();
				action.setGroupId(group5.getId());
				action.setActDevid(c2_sensor.getId());
				action.setDev(c2_sensor.getAlais());
				action.setAction(CUTOUT_ACTION);
				action.setParam(""+(int)c2.getPowervalue());
				action.setDevType(c2_sensor.getSensor_type());
				action.setIp(c2_sensor.getIpaddr());
				action.setSensorId(c2_sensor.getSensorId());
				action.setUid(c2_sensor.getUid());
				list4.add(action);
			}
			if(c3.getUid()!=null&&c3.getUid()!=""){
				DevAction action = new DevAction();
				action.setGroupId(group5.getId());
				action.setActDevid(c3_sensor.getId());
				action.setDev(c3_sensor.getAlais());
				action.setAction(CUTOUT_ACTION);
				action.setParam(""+(int)c3.getPowervalue());
				action.setDevType(c3_sensor.getSensor_type());
				action.setIp(c3_sensor.getIpaddr());
				action.setSensorId(c3_sensor.getSensorId());
				action.setUid(c3_sensor.getUid());
				list4.add(action);
			}
			if(c6.getUid()!=null&&c6.getUid()!=""){
				DevAction action = new DevAction();
				action.setGroupId(group5.getId());
				action.setActDevid(c6_sensor.getId());
				action.setDev(c6_sensor.getAlais());
				action.setAction(CUTOUT_ACTION);
				action.setParam(""+(int)c6.getPowervalue());
				action.setDevType(c6_sensor.getSensor_type());
				action.setIp(c6_sensor.getIpaddr());
				action.setSensorId(c6_sensor.getSensorId());
				action.setUid(c6_sensor.getUid());
				list4.add(action);
			}
		}
		if(list3!=null&&list3.size()>0){
			devLinkDao.addDevLgc(list3);
		}
		if(list4!=null&&list4.size()>0){
			devLinkDao.addDevAction(list4);
		}
	}
	
	public void deleteDev(int pid){
		devLinkDao.deletelgcgroup(pid);
	}

}
