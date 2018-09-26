package com.cm.controller;

import com.alibaba.fastjson.JSONObject;
import com.cm.dao.IAreaRuleDao;
import com.cm.entity.*;
import com.cm.security.LoginManage;
import com.cm.security.RedisClient;
import com.cm.service.AdjoinAreaService;
import com.cm.service.AreaCardService;
import com.cm.service.AreaService;
import com.cm.service.DevShortNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import util.StaticUtilMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Scope("prototype")
@Controller
@RequestMapping("/area")
public class AreaController {

	@Autowired
	private ResultObj result;

	@Autowired
	private AreaService areaService;

	@Autowired
	private LoginManage loginManage;

	@Autowired
	private AreaCardService as;
	
	@Autowired
	private IAreaRuleDao arDao;

	@Autowired
	private AdjoinAreaService adjoinAreaService;

	/**
	 * 查询所有区域相关信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@ResponseBody
	public Object getAll() {
		try {
			List<Area> list = areaService.getAll();
			if (list != null && !list.isEmpty()) {
				Map<Integer, List<AreaRule>> areaRuleMap = getAreaRuleMap();
                Map<Integer, Sensor> areaSensorMap = getAreaSensorMap();
                for (Area area : list) {
				    area.setType(900);
					List<AreaRule> areaRuleList = areaRuleMap.get(area.getId());
					if(areaRuleList == null){
						areaRuleList = new ArrayList<AreaRule>();
					}
					area.setAreaRuleList(areaRuleList);
					List<Sensor> list2 = areaService.getSensorByAreaid(area.getId());
					List<Sensor> sensorList = new ArrayList<Sensor>();
					if(StaticUtilMethod.notNullOrEmptyList(list2)){
						for(Sensor sensor : list2){
							if(sensor.getType() != null){
								String shortName = DevShortNameService.getShortName(sensor.getType());
								sensor.setSimpleType(shortName);
							}
							sensorList.add(sensor);
						}
					}
					area.setAllsensors(sensorList);
                    List<Area> areas = area.getAreas();
                    List<Sensor> lsits = new ArrayList<>();
                    if (null != areas && !areas.isEmpty()){
                        for (Area area1 : areas) {
                            List<Sensor> sensors = new ArrayList<>();
                            List<AreaSensor> areaSensors = area1.getAreaSensors();
                            if (null != areaSensors && !areaSensors.isEmpty()){
                                for (AreaSensor areaSensor : areaSensors) {
                                    Sensor sensor = areaSensorMap.get(areaSensor.getId());
                                    if (null != sensor){
                                        sensors.add(sensor);
                                        lsits.add(sensor);
                                    }
                                }
                            }
                            area1.setSensors(sensors);
                        }

                    }
                    area.setSensors(lsits);
                }
			}
			result.put("data", list);
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}

	public Map<Integer, Sensor> getAreaSensorMap(){
	    HashMap<Integer, Sensor> map = new HashMap<>();
        List<Sensor> list = adjoinAreaService.getAllAreaSensor();
        if(StaticUtilMethod.notNullOrEmptyList(list)){
            for (Sensor sensor : list) {
                Sensor sensor1 = map.get(sensor.getId());
                if (null == sensor1){
                    map.put(sensor.getArea_sensor_id(), sensor);
                }
            }
        }
        return map;
    }
	
	public Map<Integer,List<AreaRule>> getAreaRuleMap(){
		Map<Integer,List<AreaRule>> map = new HashMap<Integer,List<AreaRule>>();
		List<AreaRule> allAreaRules = arDao.getAllAreaRules();
		if(StaticUtilMethod.notNullOrEmptyList(allAreaRules)){
			for(AreaRule ar : allAreaRules){
				if(ar.getRuleDev() == null || ar.getRuleDev() == ""){
					continue;
				}
				if(ar.getDev_name() != null){
					ar.setSimpleType(DevShortNameService.getShortName(ar.getDev_name()));
				}
				List<AreaRule> list = map.get(ar.getAreaId());
				if(list == null){
					list = new ArrayList<AreaRule>();
				}
				list.add(ar);
				map.put(ar.getAreaId(), list);
			}
		}
		return map;
	}

	/**
	 * 添加或更新区域相关信息
	 * 
	 * @param area
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addup", method = RequestMethod.POST)
	@ResponseBody
	public Object addup(@RequestBody Area area, HttpServletRequest request) {
		// LogOut.log.debug(area.toString());
		if (!loginManage.isUserLogin(request)) {
			return result.setStatus(-1, "no login");
		}
		Object per = loginManage.checkPermission(request);
		if (null != per)
			return per;

		Jedis j=null;
		try {
			j = RedisClient.getInstance().getJedis();
		} catch (Exception e1) {
			e1.printStackTrace();
			return result.setStatus(-2, e1.getMessage());
		}

		try {
			if (area.getId() > 0) {
                areaService.updateArea(area);

                //设置黑白名单
                areaService.areaWorkerOperation(area);
                // 更新cardreder表
                if (null != area.getCardreders() && !area.getCardreders().isEmpty()){
                    areaService.updateByAreaid(area.getId());
                    areaService.updateCardreder(area.getId(), area.getCardreders());
                }
                String remark = JSONObject.toJSONString(area);
                String operation2 = "修改区域配置";
                loginManage.addLog(request, remark, operation2, 15120);
				// 更新redis表
				RedisClient.getInstance().setAreaTimeout(as);
				RedisClient.getInstance().setAreaMap(as);
				RedisClient.getInstance().setAreaWorker(as);
				RedisClient.getInstance().setAreaName(as);
			} else {
                areaService.addArea(area);

				// 添加读卡器
                if (null != area.getWorkers() && !area.getWorkers().isEmpty()) {
                    // 添加area_worker中间表
                    areaService.addAreaWorker(area.getId(), area.getWorkers());
                }
                // 更新cardreder表areaid字段
                if (null != area.getCardreders() && !area.getCardreders().isEmpty()){
                    areaService.updateCardreder(area.getId(), area.getCardreders());
                }
                String remark = JSONObject.toJSONString(area);
                String operation2 = "增加区域配置";
                loginManage.addLog(request, remark, operation2, 15120);
				// 更新redis表
				RedisClient.getInstance().setAreaTimeout(as);
				RedisClient.getInstance().setAreaMap(as);
				RedisClient.getInstance().setAreaWorker(as);
				RedisClient.getInstance().setAreaName(as);
				j.hset("areaOverman", String.valueOf(area.getId()), "0");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-2, "exception");
		}
		return result.setStatus(0, "ok");
	}

	/**
	 * 删除区域相关信息
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	@ResponseBody
	@Transactional
	public Object deleteArea(@PathVariable int id, HttpServletRequest request) {
		if (!loginManage.isUserLogin(request)) {
			return result.setStatus(-1, "no login");
		}
		Object per = loginManage.checkPermission(request);
		if (null != per)
			return per;
		try {

			areaService.deleteArea(id);
            Area area = areaService.getByid(id);
            String remark = JSONObject.toJSONString(area);
            String operation2 = "删除区域配置";
            loginManage.addLog(request, remark, operation2, 15121);
        } catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}

	/**
	 * 查询未使用的读卡器
	 * 
	 * @param areaid
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/usecreder", method = RequestMethod.POST)
	@ResponseBody
	public Object getCardreder(Integer areaid, HttpServletRequest request) {
		if (!loginManage.isUserLogin(request)) {
			return result.setStatus(-1, "no login");
		}
		try {
			List<Cardreder> cardreders = areaService.getCardreder(areaid);
			result.put("data", cardreders);
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}

	/**
	 * 查询未使用的传感器
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/unusesensor", method = RequestMethod.GET)
	@ResponseBody
	public Object getAllUnuseSensor(HttpServletRequest request) {
		if (!loginManage.isUserLogin(request)) {
			return result.setStatus(-1, "no login");
		}
		try {
			List<Sensor> list = areaService.getUnuseSensors();
			result.put("data", list);
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}

	@RequestMapping(value = "/getDefaultArea", method = RequestMethod.GET)
	@ResponseBody
	public Object getDefaultArea(HttpServletRequest request) {
		if (!loginManage.isUserLogin(request)) {
			return result.setStatus(-1, "no login");
		}
		Area2 a = areaService.getDefaultArea();
		result.put("data", a);
		return result.setStatus(0, "ok");
	}

	@RequestMapping(value = "/updateDefaultArea", method = RequestMethod.POST)
	@ResponseBody
	public Object updateDefaultArea(@RequestBody Area area, HttpServletRequest request) {
		if (!loginManage.isUserLogin(request)) {
			return result.setStatus(-1, "no login");
		}
		if (area.getId() == 1) {
			try {
				areaService.updateDefaultArea(area);
				RedisClient.getInstance().setAreaTimeout(as);
				RedisClient.getInstance().setAreaMap(as);
			} catch (Exception e) {
				e.printStackTrace();
				return result.setStatus(-2, "exception");
			}
		} else {
			return result.setStatus(-2, "参数错误！");
		}

		return result.setStatus(0, "ok");
	}
	
	private static boolean isInteger(String str) {  
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");  
        return pattern.matcher(str).matches();  
    }

    @RequestMapping(value = "/sensor", method = RequestMethod.POST)
    @ResponseBody
    public Object getSensorsByAreaid(@RequestBody Area area, HttpServletRequest request){
        if (!loginManage.isUserLogin(request)){
            return result.setStatus(-1, "no login");
        }
        try{
            List<Sensor> list = adjoinAreaService.getSensorByAreaid(area.getM_area_id(), area.getS_area_id());
            result.put("data", list);
        } catch (Exception e){
            e.printStackTrace();
            return result.setStatus(-4, "exception");
        }
        return result.setStatus(0, "ok");
    }

}
