package com.cm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import util.UtilMethod;

import com.cm.dao.EquipmentDao;
import com.cm.dao.IAreaRuleDao;
import com.cm.dao.IBaseinfoDao;
import com.cm.dao.IRadioDao;
import com.cm.dao.ISubstationDao;
import com.cm.dao.ISwitchDao;
import com.cm.entity.AreaRule;
import com.cm.entity.Equipments;
import com.cm.entity.Radio;
import com.cm.entity.Sensor;
import com.cm.entity.Substation;
import com.cm.entity.SwitchSensor;
import com.cm.entity.vo.AllEquipmentVo;
import com.cm.entity.vo.AreaRuleVo;
import com.cm.entity.vo.Ids;
import com.cm.security.LoginManage;

@Scope("prototype")
@Controller
@RequestMapping(value = "/arearule")
public class AreaRuleController {
	@Autowired
	private ResultObj result;
	@Autowired
	private LoginManage loginManage;
	@Autowired
	private IAreaRuleDao dao;
	@Autowired
	private IBaseinfoDao sensorDao;
	@Autowired
	private ISwitchDao switchDao;
	@Autowired
	private IRadioDao radioDao;
	@Autowired
	private EquipmentDao equipmentDao;
	@Autowired
	private ISubstationDao substationDao;
	
	/**
	 * 新增或更新规则
	 * @param arvo 参数对象
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addUp",method=RequestMethod.POST)
	@ResponseBody
	public Object addUp(@RequestBody AreaRuleVo arvo, HttpServletRequest request){
		try {
			//权限验证及日志记录
//			Object per = loginManage.checkPermission(request);
//	        if (per != null)
//	            return per;
			HashMap<String, AllEquipmentVo> equipment = getEquipment();
			if(arvo != null && arvo.getAreaId() > 0 && UtilMethod.notEmptyList(arvo.getRuleList())){
				List<AreaRule> addList = new ArrayList<AreaRule>();
				List<AreaRule> updataList = new ArrayList<AreaRule>();
				List<AreaRule> areaRuleByAreaId = dao.getAreaRuleByAreaId(arvo.getAreaId());
				for(AreaRule ar : arvo.getRuleList()){
					if(ar.getRuleDev() == "" || ar.getRuleDev() == null){
						return result.setStatus(1, "规则名不能为空！");
					}
					ar.setAreaId(arvo.getAreaId());
					if(ar.getId() > 0){
						if(ar.getRuleDevId() != null){
							AllEquipmentVo allEquipmentVo = equipment.get(ar.getRuleDevId());
							if(allEquipmentVo != null){
								ar.setAlais(allEquipmentVo.getAlais());
								ar.setPosition(allEquipmentVo.getPosition());
								ar.setIp(allEquipmentVo.getIp());
								ar.setDev_id(allEquipmentVo.getDevid());
							}
						}
						if(areaRuleByAreaId != null){
							for(AreaRule arl : areaRuleByAreaId){
								if(arl.getRuleDev().equals(ar.getRuleDev()) && arl.getId() != ar.getId()){
									return result.setStatus(1, "规则名：'" + ar.getRuleDev() + "'已存在！");
								}
							}
						}
						updataList.add(ar);
					}else{
						if(areaRuleByAreaId != null){
							for(AreaRule arl : areaRuleByAreaId){
								if(arl.getRuleDev().equals(ar.getRuleDev())){
									return result.setStatus(1, "规则名：'" + ar.getRuleDev() + "'已存在！");
								}
							}
						}
						addList.add(ar);
					}
				}
				if(UtilMethod.notEmptyList(addList)){
					dao.addAreaRules(addList);
				}
				if(UtilMethod.notEmptyList(updataList)){
					dao.updateAreaRules(updataList);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-2, "出错啦！");
		}
		
		return result.setStatus(0, "ok");
	}
	
	@SuppressWarnings("rawtypes")
	public HashMap<String,AllEquipmentVo> getEquipment(){
		HashMap<String,AllEquipmentVo> map = new HashMap<String,AllEquipmentVo>();
		List<Sensor> allSensor2 = sensorDao.getAllSensor2();
		List<SwitchSensor> allSwitchSensor = switchDao.AllSwitchSensor();
		List<Radio> allRadio = radioDao.getAll();
		List<Equipments> allElec = equipmentDao.getAllElec();
		List<Substation> allSubstation = substationDao.getAll();
		if(UtilMethod.notEmptyList(allSensor2)){
			for(Sensor sensor : allSensor2){
				AllEquipmentVo vo = new AllEquipmentVo();
				vo.setAlais(sensor.getAlais());
				vo.setDevid(sensor.getSensorId());
				vo.setIp(sensor.getIpaddr());
				vo.setPosition(sensor.getPosition());
				map.put(sensor.getUid(), vo);
			}
		}
		if(UtilMethod.notEmptyList(allSwitchSensor)){
			for(SwitchSensor sensor : allSwitchSensor){
				AllEquipmentVo vo = new AllEquipmentVo();
				vo.setAlais(sensor.getAlais());
				vo.setDevid(sensor.getSensorId());
				vo.setIp(sensor.getIpaddr());
				vo.setPosition(sensor.getPosition());
				map.put(sensor.getUid(), vo);
			}
		}
		if(UtilMethod.notEmptyList(allRadio)){
			for(Radio sensor : allRadio){
				AllEquipmentVo vo = new AllEquipmentVo();
				vo.setAlais(sensor.getAlais());
				vo.setDevid(sensor.getRadioId());
				vo.setIp(sensor.getIp());
				vo.setPosition(sensor.getPosition());
				map.put(sensor.getUid(), vo);
			}
		}
		if(UtilMethod.notEmptyList(allElec)){
			for(Equipments sensor : allElec){
				AllEquipmentVo vo = new AllEquipmentVo();
				vo.setAlais(sensor.getAlais());
				vo.setDevid(sensor.getDevid());
				vo.setIp(sensor.getIpaddr());
				vo.setPosition(sensor.getPosition());
				map.put(sensor.getUid(), vo);
			}
		}
		if(UtilMethod.notEmptyList(allSubstation)){
			for(Substation sensor : allSubstation){
				AllEquipmentVo vo = new AllEquipmentVo();
				vo.setAlais(sensor.getAlais());
				vo.setDevid(0);
				vo.setIp(sensor.getIpaddr());
				vo.setPosition(sensor.getPosition());
				map.put(sensor.getUid(), vo);
			}
		}
		return map;
	}
	
	/**
	 * 删除区域配置规则
	 * @param ids id列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/delete",method=RequestMethod.POST)
	@ResponseBody
	public Object delete(@RequestBody Ids ids, HttpServletRequest request){
		//权限验证及日志记录
//		Object per = loginManage.checkPermission(request);
//        if (per != null)
//            return per;
		
		try {
			if(UtilMethod.notEmptyList(ids.getIds())){
				dao.deleteAreaRules(ids.getIds());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-2, "出错啦！");
		}
		
		return result.setStatus(0, "ok");
	}
	
	/**
	 * 通过areaID获取区域配置规则
	 * @param arv
	 * @param request
	 * @return AreaRuleVo
	 */
	@RequestMapping(value = "/getByAreaId",method=RequestMethod.POST)
	@ResponseBody
	public Object getByAreaId(@RequestBody AreaRuleVo arv, HttpServletRequest request){
		try {
			if(arv.getAreaId() > 0){
				List<AreaRule> areaRuleByAreaId = dao.getAreaRuleByAreaId(arv.getAreaId());
				if(UtilMethod.notEmptyList(areaRuleByAreaId)){
					setDefaultAllowAndEmphasis(areaRuleByAreaId, arv);
					
					if(arv.getAreaName() == null){
						arv.setAreaName(areaRuleByAreaId.get(0).getAreaname());
					}
					arv.setRuleList(areaRuleByAreaId);
				}
			}
			result.put("data", arv);
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-2, "出错啦！");
		}
		return result.setStatus(0, "ok");
	}
	
	/**
	 * 获取所有区域配置规则
	 * @param request
	 * @return List<AreaRuleVo> 
	 */
	@RequestMapping(value = "/getAll",method=RequestMethod.GET)
	@ResponseBody
	public Object getAll(HttpServletRequest request){
		try {
			List<AreaRule> allAreaRules = dao.getAllAreaRules();
			List<AreaRuleVo> arvoList = new ArrayList<AreaRuleVo>();
			if(UtilMethod.notEmptyList(allAreaRules)){
				HashMap<Integer,List<AreaRule>> listMap = new HashMap<Integer,List<AreaRule>>();
				HashMap<Integer,AreaRule> arMap = new HashMap<Integer,AreaRule>();
				HashMap<Integer,String> nameMap = new HashMap<Integer,String>();
				LinkedHashSet<Integer> areaIdSet = new LinkedHashSet<Integer>();
				for(AreaRule ar : allAreaRules){
					areaIdSet.add(ar.getAreaId());
					nameMap.put(ar.getAreaId(), ar.getAreaname());
					if(UtilMethod.notEmptyStr(ar.getRuleDev())){
						List<AreaRule> list = listMap.get(ar.getAreaId());
						if(list == null){
							list = new ArrayList<AreaRule>();
						}
						list.add(ar);
						listMap.put(ar.getAreaId(), list);
					}else{
						arMap.put(ar.getAreaId(), ar);
					}
				}
				
				for(Integer areaId : areaIdSet){
					AreaRuleVo arvo = new AreaRuleVo();
					arvo.setAreaId(areaId);
					arvo.setAreaName(nameMap.get(areaId));
					List<AreaRule> list = listMap.get(areaId) == null ? new ArrayList<AreaRule>() : listMap.get(areaId);
					if(list.size() == 0){
						list.add(arMap.get(areaId));
					}
					setDefaultAllowAndEmphasis(list, arvo);
					
					arvo.setRuleList(list);
					arvoList.add(arvo);
				}
			}
			result.put("data", arvoList);
			return result.setStatus(0, "ok");
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-2, "出错啦！");
		}
	}
	
	public void setDefaultAllowAndEmphasis(List<AreaRule> list,AreaRuleVo arvo){
		AreaRule firstAreaRule = list.get(0);
		String defaultAllow = (firstAreaRule.getDefault_allow() == 2) ? "限制区域" : "非限制区域";
		arvo.setDefaultAllow(defaultAllow);
		String emphasis = firstAreaRule.getEmphasis() == 2 ? "重点" : "非重点";
		arvo.setEmphasis(emphasis);
	}
}
